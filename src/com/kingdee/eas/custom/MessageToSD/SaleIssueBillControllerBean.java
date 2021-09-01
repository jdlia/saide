package com.kingdee.eas.custom.MessageToSD;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.orm.ORMCoreException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManager;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialFacade;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.StorageOrgUnitFactory;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.basedata.scm.common.BillTypeFactory;
import com.kingdee.eas.basedata.scm.common.BillTypeInfo;
import com.kingdee.eas.basedata.scm.common.BizTypeFactory;
import com.kingdee.eas.basedata.scm.common.BizTypeInfo;
import com.kingdee.eas.basedata.scm.common.TransactionTypeFactory;
import com.kingdee.eas.basedata.scm.common.TransactionTypeInfo;
import com.kingdee.eas.basedata.scm.im.inv.InvUpdateTypeFactory;
import com.kingdee.eas.basedata.scm.im.inv.InvUpdateTypeInfo;
import com.kingdee.eas.basedata.scm.im.inv.WarehouseFactory;
import com.kingdee.eas.basedata.scm.im.inv.WarehouseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.sdyg.mapping.DoctorCollection;
import com.kingdee.eas.custom.sdyg.mapping.DoctorFactory;
import com.kingdee.eas.custom.sdyg.mapping.DoctorInfo;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapCollection;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapFactory;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapInfo;
import com.kingdee.eas.custom.sdyg.mapping.OthCustomerInfo;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogEntryInfo;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogFactory;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogInfo;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.scm.common.EntryBaseStatusEnum;
import com.kingdee.eas.scm.im.inv.OtherIssueBillEntryInfo;
import com.kingdee.eas.scm.im.inv.OtherIssueBillFactory;
import com.kingdee.eas.scm.im.inv.OtherIssueBillInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * @author jinchunxiang
 * @desc 其他出库单业务单据同步接口
 * @date 2020-11-10
 * 
 */
public class SaleIssueBillControllerBean extends AbstractSaleIssueBillControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.custom.MessageToSD.SaleIssueBillControllerBean");

	@Override
	protected String _addSaleIssueBillInfo(Context ctx, String dataInfo)
			throws BOSException {
		// TODO Auto-generated method stub
		System.out.println("进入_addSaleIssueBillInfo方法---Start");
		System.out.println("dataInfo  "+dataInfo);
		JSONObject datajsonDataInfo = JSONObject.parseObject(dataInfo);
		//正式
		JSONObject jsonDataInfo = JSONObject.parseObject(datajsonDataInfo.getString("dataInfo"));
		//本地
//		JSONObject jsonDataInfo = JSONObject.parseObject(dataInfo);
//		System.out.println("jsonDataInfo  "+jsonDataInfo);
		String bizNumber = jsonDataInfo.getString("bizNumber");//业务系统单据编码
//		System.out.println("业务系统单据编码:"+bizNumber);
		String bizDate = jsonDataInfo.getString("bizDate");//业务日期
//		System.out.println("业务日期:"+bizDate);
		String bizOrg = jsonDataInfo.getString("bizOrg");//门诊编码
//		System.out.println("门诊编码:"+bizOrg);
		String doctor = jsonDataInfo.getString("doctor");//医生编码
//		System.out.println("医生编码:"+doctor);
		String customer = jsonDataInfo.getString("customer");//患者编码
//		System.out.println("患者编码:"+customer);
		JSONArray entry = jsonDataInfo.getJSONArray("entry");//业务系统明细分录
		//JSONObject entry = jsonDataInfo.getJSONObject("entry");//业务系统明细分录
		//JSONObject entryId = entry.getJSONObject("entryId");//分录id业务系统明细分录ID
		String entryId = "";//业务系统明细分录ID
		String materialNum = "";//物料编码
		String flot = "";//物料批号
		String qty ="";//数量
		//新增出库单
		OtherIssueBillInfo otherIssueBillInfo = null;
		BigDecimal zero = new BigDecimal(0.00);
		BigDecimal one = new BigDecimal(1.00);
		
		Timestamp data = new Timestamp(new Date().getTime());
    	
		otherIssueBillInfo = new OtherIssueBillInfo(); 
		//T_IM_OtherIssueBill   OTO-A10-202011001
		String number = "user";
		UserInfo userInfo = null;
		try {
			//人员
			userInfo = UserFactory.getLocalInstance(ctx).getUserInfo(" where number = '"+number+"'");
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String cuFid = "00000000-0000-0000-0000-000000000000CCE7AED4";
		//AdminOrgUnitInfo adminOrgUnitInfo = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(cuFid));
		CtrlUnitInfo ctrlUnitInfo = null;
		try {
			ctrlUnitInfo = CtrlUnitFactory.getLocalInstance(ctx).getCtrlUnitInfo(" where id = '"+cuFid+"'");
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//业务系统编码
		otherIssueBillInfo.put("bizNumber", bizNumber);
		//业务日期
		otherIssueBillInfo.setBizDate(data);
		//创建者
		otherIssueBillInfo.setCreator(userInfo);
		//最后修改者
		otherIssueBillInfo.setLastUpdateUser(userInfo);
		//创建时间
		otherIssueBillInfo.setCreateTime(data);
		//最后修改时间
		otherIssueBillInfo.setLastUpdateTime(data);
		//控制单元
		otherIssueBillInfo.setCU(ctrlUnitInfo);
		//是否曾经生效
		otherIssueBillInfo.setHasEffected(false);
		//摘要：暂存客户编码+医生编码
		otherIssueBillInfo.setDescription(customer+":"+doctor);
		//业务类型
		String typeId = "Nz878AEgEADgAABMwKg/GiQHQ1w=";
		BizTypeInfo bizTypeInfo = null;
		try {
			bizTypeInfo = BizTypeFactory.getLocalInstance(ctx).getBizTypeInfo(" where id = '"+typeId+"'");
		} catch (EASBizException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		otherIssueBillInfo.setBizType(bizTypeInfo);
		//单据状态
		otherIssueBillInfo.setBaseStatus(BillBaseStatusEnum.TEMPORARILYSAVED);
		//业务年度
		Date dd=new Date();	
		String msg = "";
		//格式化
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sim1=new SimpleDateFormat("yyyyMMdd");
		int day = Integer.parseInt(sim1.format(dd).toString());
		String year = sim.format(dd).substring(0, 4);
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(dd); 
		otherIssueBillInfo.setYear(Integer.parseInt(year));
		//业务期间
		otherIssueBillInfo.setPeriod(calendar.get(Calendar.MONTH) + 1);
		//库存组织
//		String adminFid = "8i0AAAAATvzM567U";
		OrgmapInfo orgmapInfo = null;
		try {
			orgmapInfo = getOrgmapInfoF7(ctx, bizOrg);//TODO 该方法若返回null，则170行报错。且该方法抛出异常，但此处并未对异常进行处理。
		} catch (Exception e1) {
			msg = "错误信息："+e1.getMessage();
			e1.printStackTrace();
		}
		
		CompanyOrgUnitInfo companyInfo = null;
		StorageOrgUnitInfo storageOrgUnitInfo = null;
		if(orgmapInfo != null){
			companyInfo =  orgmapInfo.getOrg();
			try {
				storageOrgUnitInfo = StorageOrgUnitFactory.getLocalInstance(ctx).getStorageOrgUnitInfo(new ObjectUuidPK(orgmapInfo.getOrg().getId()));
			} catch (EASBizException e1) {
				msg = "错误信息："+e1.getMessage();
				e1.printStackTrace();
			}
		}
		otherIssueBillInfo.setStorageOrgUnit(storageOrgUnitInfo);
		//总数量
		otherIssueBillInfo.setTotalQty(zero);
		//总金额
		otherIssueBillInfo.setTotalAmount(zero);
		//是否生成凭证
		otherIssueBillInfo.setFiVouchered(false);
		//总标准成本
		otherIssueBillInfo.setTotalStandardCost(zero);
		//总实际成本
		otherIssueBillInfo.setTotalActualCost(zero);
		//是否冲销
		otherIssueBillInfo.setIsReversed(false);
		//事物类型DawAAAAPoCGwCNyn
		String tid = "DawAAAAPoCGwCNyn";
		TransactionTypeInfo transactionTypeInfo = null;
		try {
			transactionTypeInfo = TransactionTypeFactory.getLocalInstance(ctx).getTransactionTypeInfo(" where id = '"+tid+"'");
		} catch (EASBizException e1) {
			msg = "错误信息："+e1.getMessage();
			e1.printStackTrace();
		}
		otherIssueBillInfo.setTransactionType(transactionTypeInfo);
		//是否初始化单
		otherIssueBillInfo.setIsInitBill(false);
		//月
		String month = year + (calendar.get(Calendar.MONTH) + 1);
		otherIssueBillInfo.setMonth(Integer.parseInt(month));
		//日
		otherIssueBillInfo.setDay(day);
		//单据类型
		BillTypeInfo billTypeInfo = null;
		String billtypeid ="50957179-0105-1000-e000-0177c0a812fd463ED552";
		try {
			billTypeInfo = BillTypeFactory.getLocalInstance(ctx).getBillTypeInfo(" where id ='"+billtypeid+"'");
		} catch (EASBizException e2) {
			msg = "错误信息："+e2.getMessage();
			e2.printStackTrace();
		}
		otherIssueBillInfo.setBillType(billTypeInfo);
		//单据编号
//		AdminOrgUnitInfo currentAdminUnit = ContextUtil.getCurrentAdminUnit(ctx);
//		System.out.println("单据编号--开始生成");
		CodingRuleManager codingRuleManage = new CodingRuleManager();
		ICodingRuleManager ic = CodingRuleManagerFactory.getLocalInstance(ctx);
		String billnumber = "";
		if(orgmapInfo != null){
			try {
				billnumber = ic.getNumber(otherIssueBillInfo, orgmapInfo.getOrg().getId().toString());
			} catch (EASBizException e) {
				msg = "查询出错，错误信息：" + e + "请联系管理员！";
				e.printStackTrace();
			}
		}
		otherIssueBillInfo.setNumber(billnumber);
//		System.out.println("单据编号--结束");
		List<Map<String, Object>> accountList = new ArrayList<Map<String, Object>>();
		Map<String, Object> entrys = new HashMap<String, Object>();
		boolean isSave = true;
		if (entry.size() > 0) {
			for (int i = 0; i < entry.size(); i++) {
				JSONObject datas = (JSONObject)entry.get(i);
				entryId = datas.getString("entryId");
				materialNum = datas.getString("materialNum");
				MaterialInfo materialInfo = null;
				try {
					materialInfo = getMaterialInfoF7(ctx,materialNum);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				flot = datas.getString("flot");
				qty = datas.getString("qty");
				//基本计量单位
				MeasureUnitInfo unitInfo = new MeasureUnitInfo();
				//辅助计量单位
				MeasureUnitInfo assistUnitInfo = new MeasureUnitInfo();
				try {
					unitInfo = MeasureUnitFactory.getLocalInstance(ctx).getMeasureUnitInfo(new ObjectUuidPK(materialInfo.getBaseUnit().getId()));
				} catch (EASBizException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				int qtyprecision = unitInfo.getQtyPrecision();
				int xsws = 0;
//				2.0001
				if(qty.indexOf(".") > 0) {
					xsws = qty.length()-1-qty.indexOf(".");
				}
				if(qtyprecision - xsws <0) {
					isSave = false;
				}
				//分录
				OtherIssueBillEntryInfo otherIssueBillEntry = new OtherIssueBillEntryInfo();
				otherIssueBillEntry.setId(BOSUuid.create("F56602D7"));
				//辅助计量单位换算系统
				otherIssueBillEntry.setAssCoefficient(zero);
				//基本状态
				otherIssueBillEntry.setBaseStatus(EntryBaseStatusEnum.NULL);
				//未关联数量
				otherIssueBillEntry.setAssociateQty(new BigDecimal(qty));
				//库存组织
				otherIssueBillEntry.setStorageOrgUnit(storageOrgUnitInfo);
				//财务组织
//				String companyId = "8i0AAAAATvzM567U";
//				CompanyOrgUnitInfo companyOrgUnitInfo = null;
//				try {
//					companyOrgUnitInfo = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(" where id = '"+companyId+"'");
//				} catch (EASBizException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				otherIssueBillEntry.setCompanyOrgUnit(companyInfo);
				//批号
				otherIssueBillEntry.setLot(flot);
				//冲销数量
				otherIssueBillEntry.setReverseQty(zero);
				//退货数量
				otherIssueBillEntry.setReturnBaseQty(zero);
				//单价
				otherIssueBillEntry.setPrice(zero);
				//金额
				otherIssueBillEntry.setAmount(zero);
				//单位标准成本
				otherIssueBillEntry.setUnitStandardCost(zero);
				//标准成本
				otherIssueBillEntry.setStandardCost(zero);
				//单位实际成本
				otherIssueBillEntry.setUnitActualCost(zero);
				//实际成本
				otherIssueBillEntry.setActualCost(zero);
				//是否增品
				otherIssueBillEntry.setIsPresent(false);
				//其他出库单单头
				otherIssueBillEntry.setParent(otherIssueBillInfo);
				//退货基本数量
				otherIssueBillEntry.setReturnBaseQty(zero);
				//冲销基本数量
				otherIssueBillEntry.setReverseBaseQty(zero);
				//基本单位实际成本
				otherIssueBillEntry.setBaseUnitActualcost(zero);
				//业务日期
				otherIssueBillEntry.setBizDate(data);
				//更新类型
				String InvId = "8r0AAAAEaOnC73rf";
				InvUpdateTypeInfo invUpdateTypeInfo = null;
				try {
					invUpdateTypeInfo = InvUpdateTypeFactory.getLocalInstance(ctx).getInvUpdateTypeInfo(" where id = '"+InvId+"'");
				} catch (EASBizException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				otherIssueBillEntry.setInvUpdateType(invUpdateTypeInfo);
				//vmi未结算基本数量
				otherIssueBillEntry.setUnVmiSettleBaseQty(new BigDecimal(qty));
				//vmi累计结算基本数量
				otherIssueBillEntry.setTotalVmiSettleBaseQty(zero);
				//物料
				otherIssueBillEntry.setMaterial(materialInfo);
				//计量单位
				otherIssueBillEntry.setUnit(materialInfo.getBaseUnit());
				//基本计量单位
				otherIssueBillEntry.setBaseUnit(materialInfo.getBaseUnit());
				//辅助计量单位
				otherIssueBillEntry.setAssistUnit(materialInfo.getAssistUnit());
				//数量
				otherIssueBillEntry.setQty(new BigDecimal(qty));
				//基本数量
				otherIssueBillEntry.setBaseQty(new BigDecimal(qty));
				//辅助数量
				BigDecimal assistQty = BigDecimal.ZERO;
				
				if(materialInfo.getAssistUnit() != null){
					try {
						assistUnitInfo =  MeasureUnitFactory.getLocalInstance(ctx).getMeasureUnitInfo(new ObjectUuidPK(materialInfo.getAssistUnit().getId()));
					} catch (EASBizException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(unitInfo.isIsBaseUnit()){
						assistQty = new BigDecimal(qty).divide(assistUnitInfo.getCoefficient(),assistUnitInfo.getQtyPrecision(),BigDecimal.ROUND_HALF_UP);
					}else{
						assistQty = new BigDecimal(qty).multiply(unitInfo.getCoefficient()).setScale(unitInfo.getQtyPrecision(),BigDecimal.ROUND_HALF_UP);
					}
				}
				materialInfo.getBaseUnit().getCoefficient();
				otherIssueBillEntry.setAssistQty(assistQty);
				//仓库
				WarehouseInfo warehouseInfo = null;
				try {
					warehouseInfo = getWarehouseInfo(ctx, storageOrgUnitInfo.getId().toString(), materialInfo.getId().toString(), flot);
				} catch (Exception e1) {
					msg = "错误信息：" + e1.getMessage();
					e1.printStackTrace();
				}
				otherIssueBillEntry.setWarehouse(warehouseInfo);
				//生产日期
				Date mfg = null;
				try {
					mfg = getmfgdate(ctx, storageOrgUnitInfo.getId().toString(), materialInfo.getId().toString(), flot);
				} catch (Exception e) {
					e.printStackTrace();
				}
				otherIssueBillEntry.setMfg(mfg);
				//到期日期
				Date exp = null;
				try {
					exp = getexpdate(ctx,  storageOrgUnitInfo.getId().toString(), materialInfo.getId().toString(), flot);
				} catch (Exception e) {
					e.printStackTrace();
				}
				otherIssueBillEntry.setExp(exp);
				//客户
				try {
					otherIssueBillEntry.setCustomer(getCustomerInfoF7(ctx,customer));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//医生
				try {
					otherIssueBillEntry.put("doctor",getDoctorInfoF7(ctx,doctor).getPerson());
				} catch (ORMCoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				otherIssueBillEntry.setParent(otherIssueBillInfo);
				otherIssueBillInfo.getEntry().add(otherIssueBillEntry);

				entrys.put("entryId", entryId);
				entrys.put("easEntryId", otherIssueBillEntry.getId().toString());
				accountList.add(entrys);
			}
		}
		IObjectPK addnew = null;
		Map<String, Object> responseBody = new HashMap<String, Object>();
		Boolean succ = true;
		if(isSave) {
			try {
				addnew = OtherIssueBillFactory.getLocalInstance(ctx).addnew(otherIssueBillInfo);
				OtherIssueBillFactory.getLocalInstance(ctx).submit(addnew.toString());
				if(addnew != null){
					succ = true;
					msg = "出库单传输成功！";
				}
			} catch (EASBizException e) {
				succ = false;
				msg = "错误信息：" + e.getMessage();
				try {
					OtherIssueBillFactory.getLocalInstance(ctx).delete(addnew);
				} catch (EASBizException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}else {
			succ = false;
			msg = "出库数量与计量单位精度不一致，请检查！";
		}
		//返回
		Map<String, Object> date = new HashMap<String, Object>();
		date.put("bizNumber", bizNumber);
		date.put("easNumber", billnumber);//EAS单据编码
		date.put("entry", accountList);//单据明细
//		accountList.add(entrys);
		responseBody.put("succ", succ);
		responseBody.put("msg", msg);
		responseBody.put("data", date);
		// 返回responseBody
		String request = JSON.toJSONString(responseBody);
		System.out.println("_addSaleIssueBillInfo方法---END");
		return request;
	}
	@Override
	protected void _delSaleIssueBillInfo(Context ctx, IObjectPK pkID)
			throws BOSException, EASBizException {
		System.out.println("进入SaleIssueBillControllerBean-_delSaleIssueBillInfo");
//		OtherIssueBillInfo otherIssueBillInfo = OtherIssueBillFactory.getRemoteInstance().getOtherIssueBillInfo(pkID);
		OtherIssueBillInfo otherIssueBillInfo = OtherIssueBillFactory.getLocalInstance(ctx).getOtherIssueBillInfo(pkID);
		
		if(otherIssueBillInfo.get("bizNumber") != null){
			String bizNumber = otherIssueBillInfo.get("bizNumber").toString();
			System.out.println("业务系统编码"+bizNumber);
			//调用业务系统的删除接口
			String url ="http://192.168.8.151:8388/matused/delete";			
			String res = HttpClientUtil.sendGet(url, "bizNumber="+bizNumber);
			SyncLogInfo logInfo = new SyncLogInfo();
	    	logInfo.setId(BOSUuid.create("59A5EF45"));//日志业务日期
	    	logInfo.setBizDate(new Date());
	    	//日志创建时间
	    	logInfo.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
	    	logInfo.setDescription("其他出库单删除操作");
	    	SyncLogEntryInfo logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo("金蝶编号:"+otherIssueBillInfo.getNumber()+" 预约系统ID:"+bizNumber +" 结果:"+res);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
			if(logInfo != null){
				try {
					SyncLogFactory.getLocalInstance(ctx).save(logInfo);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	  * 财务组织映射表取公司F7 CompanyOrgUnitInfo
	  * @param ctx
	  * @param number
	  * @return
	  * @throws Exception 
	  */
	 protected OrgmapInfo getOrgmapInfoF7(Context ctx, String number) throws Exception {
	  OrgmapInfo orgmapInfo = new OrgmapInfo();
	  try {
	   OrgmapCollection orgmapCollection = OrgmapFactory.getLocalInstance(ctx).getOrgmapCollection( " where dgtNo = '"+number+"'");
	   if ((orgmapCollection  != null) && (orgmapCollection.size() > 0)) {
	    orgmapInfo = orgmapCollection.get(0);
	   } else {
	    throw new Exception("公司："+number+"未在EAS中找到对应的公司");
	   }
	  } catch (BOSException e) {
	   e.printStackTrace();
	  }
	  return orgmapInfo;
	 }
	/**
	  * 医生映射表取员工F7 
	  * @param ctx
	  * @param number
	  * @return
	  * @throws Exception 
	  */
	 protected DoctorInfo getDoctorInfoF7(Context ctx, String number) throws Exception {
	  DoctorInfo doctorInfo = new DoctorInfo();
	  try {
		  	DoctorCollection doctorCollection = DoctorFactory.getLocalInstance(ctx).getDoctorCollection( " where bizid = '"+number+"'");
	   if ((doctorCollection  != null) && (doctorCollection.size() > 0)) {
		   doctorInfo = doctorCollection.get(0);
	   } else {
	    throw new Exception("医生编码："+number+"未在EAS中找到对应的员工");
	   }
	  } catch (BOSException e) {
	   e.printStackTrace();
	  }
	  return doctorInfo;
	 }

	/**
	 * 物料F7		MaterialInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected MaterialInfo getMaterialInfoF7(Context ctx, String number) throws Exception {
		MaterialInfo materialInfo = new MaterialInfo();
		if (number != null) {
			if (number.length() > 0) {
				try {
					MaterialCollection materialCollection = MaterialFactory.getLocalInstance(ctx).getMaterialCollection(getViewInfo(number));
					if ((materialCollection  != null) && (materialCollection.size() > 0)) {
						materialInfo = materialCollection.get(0);
					} else {
						throw new Exception("EAS中未找到对应的物料："+ number +"，请检查。");
					}
				} catch (BOSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return materialInfo;
	}
	
	/**
	 * 获取库存组织	
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 */
	protected WarehouseInfo getWarehouseInfo(Context ctx, String storageid ,String materialid ,String lot) throws Exception {
		WarehouseInfo warehouseInfo = new WarehouseInfo();
		String warehouseInfoID =null;
		StringBuffer sql = new StringBuffer();
		sql.append(" /*dialect*/select FWAREHOUSEID from t_im_inventory where  FCURSTOREQTY > 0 and FSTORAGEORGUNITID = '"+storageid+"' and FMATERIALID ='" +materialid+"'");
		if(lot != null && lot.length() >0){
			sql.append("  and FLOT ='"+lot+"'"); 
		}else{
			sql.append("  and FLOT ='YNi0IQEOEADgBT3mfwAAAcznrtQ='"); 
		}
		IRowSet rowSet = DbUtil.executeQuery(ctx, sql.toString());
		List  list = new ArrayList();
		while (rowSet.next()) {
			list.add(rowSet.getString("FWAREHOUSEID"));
		}
		if((list.size()>0) && (list.get(0) != null)){
			warehouseInfo = WarehouseFactory.getLocalInstance(ctx).getWarehouseInfo("where id = '"+list.get(0).toString()+"'");
		}else {
			throw new Exception("EAS中未找到对应的仓库");
		}
		return warehouseInfo;
	}
	
	/**
	 * 物料的生产日期
	 * @param ctx
	 * @param 库存组织ID 物料ID  批次 
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 */
	protected Date getmfgdate(Context ctx, String storageid ,String materialid ,String lot) throws Exception {
		Date mfg = null;
		String sql =" /*dialect*/select fmfg from T_IM_DateOfMinDurability  where  FSTORAGEORGUNITID = '"+storageid+"' and FMATERIALID ='" +materialid+"' and FLOT ='"+lot+"'";
		IRowSet rowSet = DbUtil.executeQuery(ctx, sql);
		if (rowSet.next()) {
			mfg = rowSet.getDate("fmfg");
		}
		return mfg;
	}
	/**
	 * 物料的到期日期
	 * @param ctx
	 * @param 库存组织ID 物料ID  批次 
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 */
	protected Date getexpdate(Context ctx, String storageid ,String materialid ,String lot) throws Exception {
		Date exp = null;
		String sql =" /*dialect*/select fexp from T_IM_DateOfMinDurability  where  FSTORAGEORGUNITID = '"+storageid+"' and FMATERIALID ='" +materialid+"' and FLOT ='"+lot+"'";
		IRowSet rowSet = DbUtil.executeQuery(ctx, sql);
		if (rowSet.next()) {
			exp = rowSet.getDate("fexp");
		}
		return exp;
	}
	/**
	 * 客户F7		CustomerInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected CustomerInfo getCustomerInfoF7(Context ctx, String number) throws Exception {
		CustomerInfo customerInfo = new CustomerInfo();
		try {
			CustomerCollection customerCollection = CustomerFactory.getLocalInstance(ctx).getCustomerCollection(getViewInfo(number));
			if ((customerCollection  != null) && (customerCollection.size() > 0)) {
				customerInfo = customerCollection.get(0);
			}else {
				throw new Exception("EAS中未找到对应的客户，请检查。");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}
	protected EntityViewInfo getViewInfo(String number) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		evi.setFilter(filter);
		return evi;
	}

}