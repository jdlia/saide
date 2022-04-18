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
 * @desc �������ⵥҵ�񵥾�ͬ���ӿ�
 * @date 2020-11-10
 * 
 */
public class SaleIssueBillControllerBean extends AbstractSaleIssueBillControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.custom.MessageToSD.SaleIssueBillControllerBean");

	@Override
	protected String _addSaleIssueBillInfo(Context ctx, String dataInfo)
			throws BOSException {
		// TODO Auto-generated method stub
		System.out.println("����_addSaleIssueBillInfo����---Start");
		System.out.println("dataInfo  "+dataInfo);
		JSONObject datajsonDataInfo = JSONObject.parseObject(dataInfo);
		//��ʽ
		JSONObject jsonDataInfo = JSONObject.parseObject(datajsonDataInfo.getString("dataInfo"));
		//����
//		JSONObject jsonDataInfo = JSONObject.parseObject(dataInfo);
//		System.out.println("jsonDataInfo  "+jsonDataInfo);
		String bizNumber = jsonDataInfo.getString("bizNumber");//ҵ��ϵͳ���ݱ���
//		System.out.println("ҵ��ϵͳ���ݱ���:"+bizNumber);
		String bizDate = jsonDataInfo.getString("bizDate");//ҵ������
//		System.out.println("ҵ������:"+bizDate);
		String bizOrg = jsonDataInfo.getString("bizOrg");//�������
//		System.out.println("�������:"+bizOrg);
		String doctor = jsonDataInfo.getString("doctor");//ҽ������
//		System.out.println("ҽ������:"+doctor);
		String customer = jsonDataInfo.getString("customer");//���߱���
//		System.out.println("���߱���:"+customer);
		JSONArray entry = jsonDataInfo.getJSONArray("entry");//ҵ��ϵͳ��ϸ��¼
		//JSONObject entry = jsonDataInfo.getJSONObject("entry");//ҵ��ϵͳ��ϸ��¼
		//JSONObject entryId = entry.getJSONObject("entryId");//��¼idҵ��ϵͳ��ϸ��¼ID
		String entryId = "";//ҵ��ϵͳ��ϸ��¼ID
		String materialNum = "";//���ϱ���
		String flot = "";//��������
		String qty ="";//����
		//�������ⵥ
		OtherIssueBillInfo otherIssueBillInfo = null;
		BigDecimal zero = new BigDecimal(0.00);
		BigDecimal one = new BigDecimal(1.00);
		
		Timestamp data = new Timestamp(new Date().getTime());
    	
		otherIssueBillInfo = new OtherIssueBillInfo(); 
		//T_IM_OtherIssueBill   OTO-A10-202011001
		String number = "user";
		UserInfo userInfo = null;
		try {
			//��Ա
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
		//ҵ��ϵͳ����
		otherIssueBillInfo.put("bizNumber", bizNumber);
		//ҵ������
		otherIssueBillInfo.setBizDate(data);
		//������
		otherIssueBillInfo.setCreator(userInfo);
		//����޸���
		otherIssueBillInfo.setLastUpdateUser(userInfo);
		//����ʱ��
		otherIssueBillInfo.setCreateTime(data);
		//����޸�ʱ��
		otherIssueBillInfo.setLastUpdateTime(data);
		//���Ƶ�Ԫ
		otherIssueBillInfo.setCU(ctrlUnitInfo);
		//�Ƿ�������Ч
		otherIssueBillInfo.setHasEffected(false);
		//ժҪ���ݴ�ͻ�����+ҽ������
		otherIssueBillInfo.setDescription(customer+":"+doctor);
		//ҵ������
		String typeId = "Nz878AEgEADgAABMwKg/GiQHQ1w=";
		BizTypeInfo bizTypeInfo = null;
		try {
			bizTypeInfo = BizTypeFactory.getLocalInstance(ctx).getBizTypeInfo(" where id = '"+typeId+"'");
		} catch (EASBizException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		otherIssueBillInfo.setBizType(bizTypeInfo);
		//����״̬
		otherIssueBillInfo.setBaseStatus(BillBaseStatusEnum.TEMPORARILYSAVED);
		//ҵ�����
		Date dd=new Date();	
		String msg = "";
		//��ʽ��
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sim1=new SimpleDateFormat("yyyyMMdd");
		int day = Integer.parseInt(sim1.format(dd).toString());
		String year = sim.format(dd).substring(0, 4);
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(dd); 
		otherIssueBillInfo.setYear(Integer.parseInt(year));
		//ҵ���ڼ�
		otherIssueBillInfo.setPeriod(calendar.get(Calendar.MONTH) + 1);
		//�����֯
//		String adminFid = "8i0AAAAATvzM567U";
		OrgmapInfo orgmapInfo = null;
		try {
			orgmapInfo = getOrgmapInfoF7(ctx, bizOrg);//TODO �÷���������null����170�б����Ҹ÷����׳��쳣�����˴���δ���쳣���д���
		} catch (Exception e1) {
			msg = "������Ϣ��"+e1.getMessage();
			e1.printStackTrace();
		}
		
		CompanyOrgUnitInfo companyInfo = null;
		StorageOrgUnitInfo storageOrgUnitInfo = null;
		if(orgmapInfo != null){
			companyInfo =  orgmapInfo.getOrg();
			try {
				storageOrgUnitInfo = StorageOrgUnitFactory.getLocalInstance(ctx).getStorageOrgUnitInfo(new ObjectUuidPK(orgmapInfo.getOrg().getId()));
			} catch (EASBizException e1) {
				msg = "������Ϣ��"+e1.getMessage();
				e1.printStackTrace();
			}
		}
		otherIssueBillInfo.setStorageOrgUnit(storageOrgUnitInfo);
		//������
		otherIssueBillInfo.setTotalQty(zero);
		//�ܽ��
		otherIssueBillInfo.setTotalAmount(zero);
		//�Ƿ�����ƾ֤
		otherIssueBillInfo.setFiVouchered(false);
		//�ܱ�׼�ɱ�
		otherIssueBillInfo.setTotalStandardCost(zero);
		//��ʵ�ʳɱ�
		otherIssueBillInfo.setTotalActualCost(zero);
		//�Ƿ����
		otherIssueBillInfo.setIsReversed(false);
		//��������DawAAAAPoCGwCNyn
		String tid = "DawAAAAPoCGwCNyn";
		TransactionTypeInfo transactionTypeInfo = null;
		try {
			transactionTypeInfo = TransactionTypeFactory.getLocalInstance(ctx).getTransactionTypeInfo(" where id = '"+tid+"'");
		} catch (EASBizException e1) {
			msg = "������Ϣ��"+e1.getMessage();
			e1.printStackTrace();
		}
		otherIssueBillInfo.setTransactionType(transactionTypeInfo);
		//�Ƿ��ʼ����
		otherIssueBillInfo.setIsInitBill(false);
		//��
		String month = year + (calendar.get(Calendar.MONTH) + 1);
		otherIssueBillInfo.setMonth(Integer.parseInt(month));
		//��
		otherIssueBillInfo.setDay(day);
		//��������
		BillTypeInfo billTypeInfo = null;
		String billtypeid ="50957179-0105-1000-e000-0177c0a812fd463ED552";
		try {
			billTypeInfo = BillTypeFactory.getLocalInstance(ctx).getBillTypeInfo(" where id ='"+billtypeid+"'");
		} catch (EASBizException e2) {
			msg = "������Ϣ��"+e2.getMessage();
			e2.printStackTrace();
		}
		otherIssueBillInfo.setBillType(billTypeInfo);
		//���ݱ��
//		AdminOrgUnitInfo currentAdminUnit = ContextUtil.getCurrentAdminUnit(ctx);
//		System.out.println("���ݱ��--��ʼ����");
		CodingRuleManager codingRuleManage = new CodingRuleManager();
		ICodingRuleManager ic = CodingRuleManagerFactory.getLocalInstance(ctx);
		String billnumber = "";
		if(orgmapInfo != null){
			try {
				billnumber = ic.getNumber(otherIssueBillInfo, orgmapInfo.getOrg().getId().toString());
			} catch (EASBizException e) {
				msg = "��ѯ����������Ϣ��" + e + "����ϵ����Ա��";
				e.printStackTrace();
			}
		}
		otherIssueBillInfo.setNumber(billnumber);
//		System.out.println("���ݱ��--����");
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
				//����������λ
				MeasureUnitInfo unitInfo = new MeasureUnitInfo();
				//����������λ
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
				//��¼
				OtherIssueBillEntryInfo otherIssueBillEntry = new OtherIssueBillEntryInfo();
				otherIssueBillEntry.setId(BOSUuid.create("F56602D7"));
				//����������λ����ϵͳ
				otherIssueBillEntry.setAssCoefficient(zero);
				//����״̬
				otherIssueBillEntry.setBaseStatus(EntryBaseStatusEnum.NULL);
				//δ��������
				otherIssueBillEntry.setAssociateQty(new BigDecimal(qty));
				//�����֯
				otherIssueBillEntry.setStorageOrgUnit(storageOrgUnitInfo);
				//������֯
//				String companyId = "8i0AAAAATvzM567U";
//				CompanyOrgUnitInfo companyOrgUnitInfo = null;
//				try {
//					companyOrgUnitInfo = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(" where id = '"+companyId+"'");
//				} catch (EASBizException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				otherIssueBillEntry.setCompanyOrgUnit(companyInfo);
				//����
				otherIssueBillEntry.setLot(flot);
				//��������
				otherIssueBillEntry.setReverseQty(zero);
				//�˻�����
				otherIssueBillEntry.setReturnBaseQty(zero);
				//����
				otherIssueBillEntry.setPrice(zero);
				//���
				otherIssueBillEntry.setAmount(zero);
				//��λ��׼�ɱ�
				otherIssueBillEntry.setUnitStandardCost(zero);
				//��׼�ɱ�
				otherIssueBillEntry.setStandardCost(zero);
				//��λʵ�ʳɱ�
				otherIssueBillEntry.setUnitActualCost(zero);
				//ʵ�ʳɱ�
				otherIssueBillEntry.setActualCost(zero);
				//�Ƿ���Ʒ
				otherIssueBillEntry.setIsPresent(false);
				//�������ⵥ��ͷ
				otherIssueBillEntry.setParent(otherIssueBillInfo);
				//�˻���������
				otherIssueBillEntry.setReturnBaseQty(zero);
				//������������
				otherIssueBillEntry.setReverseBaseQty(zero);
				//������λʵ�ʳɱ�
				otherIssueBillEntry.setBaseUnitActualcost(zero);
				//ҵ������
				otherIssueBillEntry.setBizDate(data);
				//��������
				String InvId = "8r0AAAAEaOnC73rf";
				InvUpdateTypeInfo invUpdateTypeInfo = null;
				try {
					invUpdateTypeInfo = InvUpdateTypeFactory.getLocalInstance(ctx).getInvUpdateTypeInfo(" where id = '"+InvId+"'");
				} catch (EASBizException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				otherIssueBillEntry.setInvUpdateType(invUpdateTypeInfo);
				//vmiδ�����������
				otherIssueBillEntry.setUnVmiSettleBaseQty(new BigDecimal(qty));
				//vmi�ۼƽ����������
				otherIssueBillEntry.setTotalVmiSettleBaseQty(zero);
				//����
				otherIssueBillEntry.setMaterial(materialInfo);
				//������λ
				otherIssueBillEntry.setUnit(materialInfo.getBaseUnit());
				//����������λ
				otherIssueBillEntry.setBaseUnit(materialInfo.getBaseUnit());
				//����������λ
				otherIssueBillEntry.setAssistUnit(materialInfo.getAssistUnit());
				//����
				otherIssueBillEntry.setQty(new BigDecimal(qty));
				//��������
				otherIssueBillEntry.setBaseQty(new BigDecimal(qty));
				//��������
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
				//�ֿ�
				WarehouseInfo warehouseInfo = null;
				try {
					warehouseInfo = getWarehouseInfo(ctx, storageOrgUnitInfo.getId().toString(), materialInfo.getId().toString(), flot);
				} catch (Exception e1) {
					msg = "������Ϣ��" + e1.getMessage();
					e1.printStackTrace();
				}
				otherIssueBillEntry.setWarehouse(warehouseInfo);
				//��������
				Date mfg = null;
				try {
					mfg = getmfgdate(ctx, storageOrgUnitInfo.getId().toString(), materialInfo.getId().toString(), flot);
				} catch (Exception e) {
					e.printStackTrace();
				}
				otherIssueBillEntry.setMfg(mfg);
				//��������
				Date exp = null;
				try {
					exp = getexpdate(ctx,  storageOrgUnitInfo.getId().toString(), materialInfo.getId().toString(), flot);
				} catch (Exception e) {
					e.printStackTrace();
				}
				otherIssueBillEntry.setExp(exp);
				//�ͻ�
				try {
					otherIssueBillEntry.setCustomer(getCustomerInfoF7(ctx,customer));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//ҽ��
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
					msg = "���ⵥ����ɹ���";
				}
			} catch (EASBizException e) {
				succ = false;
				msg = "������Ϣ��" + e.getMessage();
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
			msg = "���������������λ���Ȳ�һ�£����飡";
		}
		//����
		Map<String, Object> date = new HashMap<String, Object>();
		date.put("bizNumber", bizNumber);
		date.put("easNumber", billnumber);//EAS���ݱ���
		date.put("entry", accountList);//������ϸ
//		accountList.add(entrys);
		responseBody.put("succ", succ);
		responseBody.put("msg", msg);
		responseBody.put("data", date);
		// ����responseBody
		String request = JSON.toJSONString(responseBody);
		System.out.println("_addSaleIssueBillInfo����---END");
		return request;
	}
	@Override
	protected void _delSaleIssueBillInfo(Context ctx, IObjectPK pkID)
			throws BOSException, EASBizException {
		System.out.println("����SaleIssueBillControllerBean-_delSaleIssueBillInfo");
//		OtherIssueBillInfo otherIssueBillInfo = OtherIssueBillFactory.getRemoteInstance().getOtherIssueBillInfo(pkID);
		OtherIssueBillInfo otherIssueBillInfo = OtherIssueBillFactory.getLocalInstance(ctx).getOtherIssueBillInfo(pkID);
		
		if(otherIssueBillInfo.get("bizNumber") != null){
			String bizNumber = otherIssueBillInfo.get("bizNumber").toString();
			System.out.println("ҵ��ϵͳ����"+bizNumber);
			//����ҵ��ϵͳ��ɾ���ӿ�
			String url ="http://192.168.8.151:8388/matused/delete";			
			String res = HttpClientUtil.sendGet(url, "bizNumber="+bizNumber);
			SyncLogInfo logInfo = new SyncLogInfo();
	    	logInfo.setId(BOSUuid.create("59A5EF45"));//��־ҵ������
	    	logInfo.setBizDate(new Date());
	    	//��־����ʱ��
	    	logInfo.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
	    	logInfo.setDescription("�������ⵥɾ������");
	    	SyncLogEntryInfo logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo("������:"+otherIssueBillInfo.getNumber()+" ԤԼϵͳID:"+bizNumber +" ���:"+res);
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
	  * ������֯ӳ���ȡ��˾F7 CompanyOrgUnitInfo
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
	    throw new Exception("��˾��"+number+"δ��EAS���ҵ���Ӧ�Ĺ�˾");
	   }
	  } catch (BOSException e) {
	   e.printStackTrace();
	  }
	  return orgmapInfo;
	 }
	/**
	  * ҽ��ӳ���ȡԱ��F7 
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
	    throw new Exception("ҽ�����룺"+number+"δ��EAS���ҵ���Ӧ��Ա��");
	   }
	  } catch (BOSException e) {
	   e.printStackTrace();
	  }
	  return doctorInfo;
	 }

	/**
	 * ����F7		MaterialInfo
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
						throw new Exception("EAS��δ�ҵ���Ӧ�����ϣ�"+ number +"�����顣");
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
	 * ��ȡ�����֯	
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
			throw new Exception("EAS��δ�ҵ���Ӧ�Ĳֿ�");
		}
		return warehouseInfo;
	}
	
	/**
	 * ���ϵ���������
	 * @param ctx
	 * @param �����֯ID ����ID  ���� 
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
	 * ���ϵĵ�������
	 * @param ctx
	 * @param �����֯ID ����ID  ���� 
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
	 * �ͻ�F7		CustomerInfo
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
				throw new Exception("EAS��δ�ҵ���Ӧ�Ŀͻ������顣");
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