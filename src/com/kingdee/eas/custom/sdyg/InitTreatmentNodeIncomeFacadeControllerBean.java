package com.kingdee.eas.custom.sdyg;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.OperationTypeInfo;
import com.kingdee.eas.custom.sdyg.mapping.DoctorInfo;
import com.kingdee.eas.custom.sdyg.mapping.IncomeRatioFactory;
import com.kingdee.eas.custom.sdyg.mapping.IncomeRatioInfo;
import com.kingdee.eas.custom.sdyg.mapping.NodeTypeInfo;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapInfo;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogEntryInfo;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogFactory;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogInfo;
import com.kingdee.eas.custom.sdyg.mapping.TreatmentNodeCodingCollection;
import com.kingdee.eas.custom.sdyg.mapping.TreatmentNodeCodingFactory;
import com.kingdee.eas.custom.sdyg.mapping.TreatmentNodeCodingInfo;
import com.kingdee.eas.custom.sdyg.sdenum.PayTypeEnum;
import com.kingdee.eas.fi.ar.BillStatusEnum;
import com.kingdee.eas.fi.ar.IOtherBill;
import com.kingdee.eas.fi.ar.OtherBillFactory;
import com.kingdee.eas.fi.ar.OtherBillInfo;
import com.kingdee.eas.fi.ar.OtherBillPlanCollection;
import com.kingdee.eas.fi.ar.OtherBillPlanInfo;
import com.kingdee.eas.fi.ar.OtherBillTypeEnum;
import com.kingdee.eas.fi.ar.OtherBillentryCollection;
import com.kingdee.eas.fi.ar.OtherBillentryInfo;
import com.kingdee.eas.fi.ar.verifyStatusEnum;
import com.kingdee.eas.fi.arap.BillRelationOptionEnum;
import com.kingdee.eas.fi.cas.BizTypeEnum;
import com.kingdee.eas.fi.cas.CasRecPayBillTypeEnum;
import com.kingdee.eas.fi.cas.IReceivingBill;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.fm.rpm.BillClaimStatusEnum;
import com.kingdee.eas.fm.ss.SmartType;
import com.kingdee.eas.sunny.commUtil.IOtherBillUtil;
import com.kingdee.eas.sunny.commUtil.InfoF7Util;
import com.kingdee.eas.sunny.commUtil.MysqlQueryRsUtil;
import com.kingdee.eas.sunny.commUtil.mysqlConnectionUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
/** 
 * 
 * @author chens 
 * 正畸治疗
 *
 */
public class InitTreatmentNodeIncomeFacadeControllerBean extends AbstractInitTreatmentNodeIncomeFacadeControllerBean
{
	private static Logger logger = Logger.getLogger("com.kingdee.eas.custom.sdyg.InitTreatmentNodeIncomeFacadeControllerBean");

	private static String strBizIDs = "";
	
    /** 
     * 初始化正畸表数据
     */
    @Override
    protected void _initTreatmentInfo(Context ctx) throws BOSException,EASBizException {
    	// TODO Auto-generated method stub
    	// super._initTreatmentInfo(ctx);

    	System.out.println("进入InitTreatmentNodeIncomeFacadeControllerBean");
    	//[下文代码cd代表初戴 ,bcq代表保持器(结束)]
    	String cdnodes = "";
    	String bcqnodes = "";
    	String sysNodeCode = "";
    	String sysTrestmentCode = "";
    	String easNodeTypeCode ="";
    	String cdFeeTypeDetails = "";
    	String bcqFeeTypeDetails = "";
    	StringBuffer sqlbuff = new StringBuffer();
    	TreatmentNodeCodingCollection treatmentNodeCodingCollection = new TreatmentNodeCodingCollection();
    	@SuppressWarnings("unused")
		TreatmentNodeCodingInfo treatmentNodeCodingInfo = new TreatmentNodeCodingInfo();
		try {
			//查询系统的节点类型编码和治疗类型编码
			sqlbuff = new StringBuffer();
			sqlbuff.append("select t1.CFSYSTEMNODETYPECODE sysNodeCode ,t1.CFSYSTEMTREATMENTTYPECODE sysTrestmentCode,");
			sqlbuff.append("t3.FNUMBER easTreatmentCode,t4.FNUMBER easNodeCode");
			sqlbuff.append(" from CT_MAP_TreatmentNodeCoding t1");
			sqlbuff.append(" left join CT_MAP_TreatmentType t3 on t1.CFTrestmentTypeID = t3.FID");
			sqlbuff.append(" left join CT_MAP_NodeType t4 on t1.CFNodeTypeID = t4.FID where t3.FNUMBER = '001'");
			IRowSet incomeRation = DbUtil.executeQuery(ctx, sqlbuff.toString());   
		    while(incomeRation.next()){
		    	easNodeTypeCode = incomeRation.getString("easNodeCode");
				sysNodeCode = incomeRation.getString("sysNodeCode");
				sysTrestmentCode = incomeRation.getString("sysTrestmentCode");
				if(easNodeTypeCode.equals("A01")){
					//系统中“初戴”的节点类型编码和治疗类型编码
					cdnodes = cdnodes+"'"+sysNodeCode+"',";
					cdFeeTypeDetails = cdFeeTypeDetails+"'"+sysTrestmentCode+"',"; 
				}else if(easNodeTypeCode.equals("A02")){
					//系统中“保持器(结束)”的节点类型编码和治疗类型编码
					bcqnodes = bcqnodes+"'"+sysNodeCode+"',";
					bcqFeeTypeDetails = bcqFeeTypeDetails+"'"+sysTrestmentCode+"',"; 

				}  
			}
	    	cdnodes = cdnodes.substring(0,cdnodes.length()-1);
	    	bcqnodes = bcqnodes.substring(0,bcqnodes.length()-1);
	    	cdFeeTypeDetails = cdFeeTypeDetails.substring(0,cdFeeTypeDetails.length()-1);
	    	bcqFeeTypeDetails = bcqFeeTypeDetails.substring(0,bcqFeeTypeDetails.length()-1);
		} catch (Exception e2){
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}    		

    	//查询正畸中间表数据t_ortho   todo 2021.07.08 去掉 t.isHandled IS NULL   and t.company in ('066','080') and t.bizDate != STR_TO_DATE  and t.bizDate >= STR_TO_DATE('2020-01-01 00:00:00','%Y-%m-%d %H:%i:%s')
		sqlbuff = new StringBuffer();
		/**
		sqlbuff.append("select t.* ,t1.bizDate cdBizDate,t2.bizDate bcqBizDate,t1.node cdNode,t2.node bcqNode,t1.feeTypeDetail cdfeeTypeDetail,t2.feeTypeDetail bcqfeeTypeDetail,");
		sqlbuff.append(" t3.specialAmount jmjSpecialAmount ,t3.bizDate jmjBizDate,t1.company cdCompany ,t2.company bcqCompany,chudai.discountAmount chudaiamount,jieshu.discountAmount jieshuamount");  
		sqlbuff.append(" ,chudai.expenseNo chudaiexpenseNo ,jieshu.expenseNo jieshuexpenseNo");  
		sqlbuff.append(" FROM ( select * from t_ortho t where  t.isHandled != '1' AND ( t.company <> '' OR t.isSpecial = '1') and t.bizDate is not null and t.docNo !='' )t ");
		sqlbuff.append(" left join ( select * from t_ortho  where isSpecial != '1' and feeTypeDetail in("+cdFeeTypeDetails+") and node in ("+cdnodes+") and company <> '' ) t1 on t.expenseID = t1.expenseID");
	    sqlbuff.append(" left join ( select * from t_ortho  where isSpecial != '1' and feeTypeDetail in("+bcqFeeTypeDetails+") and node in ("+bcqnodes+") and company <> '' ) t2 on t.expenseID = t2.expenseID");
	    sqlbuff.append(" left join ( select * from t_ortho  where isSpecial = '1' ) t3  on t.expenseID = t3.expenseID");
	    sqlbuff.append(" left join  (select expenseNo,discountAmount,feeagreementid from t_agreement where expenseName like '%戴%') chudai on t.feeagreementid = chudai.feeagreementid ");
	    sqlbuff.append(" left join  (select expenseNo,discountAmount,feeagreementid from t_agreement where expenseName like '%复%') jieshu on t.feeagreementid = jieshu.feeagreementid ORDER BY t.bizDate asc");
	    System.out.println("正畸中间表SQL："+sqlbuff.toString());
**/
		sqlbuff.append("select * from (select t.* ,t1.bizDate cdBizDate,t2.bizDate bcqBizDate,t1.node cdNode,t2.node bcqNode," +
				"t1.feeTypeDetail cdfeeTypeDetail,t2.feeTypeDetail bcqfeeTypeDetail, t3.specialAmount jmjSpecialAmount ," +
				"t3.bizDate jmjBizDate,t1.company cdCompany ,t2.company bcqCompany,chudai.discountAmount chudaiamount," +
				"jieshu.discountAmount jieshuamount ,chudai.expenseNo chudaiexpenseNo ,jieshu.expenseNo jieshuexpenseNo " +
				"FROM ( select * from t_ortho t where (t.company <> '' OR t.isSpecial = '1') and (t.isHandled is null or t.isHandled = '0')  and t.bizDate is not null and t.docNo !='' )t  " +
				"left join ( select * from t_ortho  where isSpecial != '1' and feeTypeDetail in('4','3','5','2','2','1','2') " +
				"and node in ('firstWear','firstWear','firstWear','firstWearHalf1','firstWear','firstWear','firstWearHalf2') " +
				"and company <> '' ) t1 on t.expenseID = t1.expenseID left join ( select * from t_ortho  " +
				"where isSpecial != '1' and feeTypeDetail in('3','2','1','4','5') " +
				"and node in ('finish','finish','finish','finish','finish') and company <> '' ) t2 " +
				"on t.expenseID = t2.expenseID left join ( select * from t_ortho  where isSpecial = '1' ) t3  " +
				"on t.expenseID = t3.expenseID left join  (select expenseNo,discountAmount,feeagreementid from t_agreement where expenseName like '%戴%') chudai " +
				"on t.feeagreementid = chudai.feeagreementid  left join  (select expenseNo,discountAmount,feeagreementid from t_agreement where expenseName like '%复%') jieshu " +
				"on t.feeagreementid = jieshu.feeagreementid ORDER BY t.bizDate asc ) a ");
    	String msg = "";
    	mysqlConnectionUtil sqlutil = new mysqlConnectionUtil();
    	Connection conn = null;
    	SyncLogInfo logInfo = new SyncLogInfo();
    	logInfo.setId(BOSUuid.create("59A5EF45"));
    	//日志业务日期
    	logInfo.setBizDate(new Date());
    	//日志创建时间
    	logInfo.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
    	SyncLogEntryInfo logEntryInfo = null;
    	
    	try {
			conn = sqlutil.createConn();
		} catch (ClassNotFoundException e1) {
			msg = "错误信息：连接中间库异常。";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		} catch (SQLException e1) {
			msg = "错误信息：连接中间库异常。";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		}
		
		//获取正畸中间表结果集
    	ResultSet rs = null;
    	PreparedStatement ps = null;
		try {
			//收据结果集
			rs = MysqlQueryRsUtil.getRs(ctx,conn,ps,sqlbuff.toString());
		} catch (SQLException e1) {
			msg = "错误信息：获取结果集失败。";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		} catch (ClassNotFoundException e1) {
			msg = "错误信息：获取结果集失败。";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		}
		
		//遍历结果集开始，封装成应收单
		try {
			while(rs.next()){
				try{
					//取出正畸中间表所有字段
					//主表id
					String bizID = rs.getString("ID");
					//是否特殊减免
					String isSpecial = rs.getString("isSpecial");
					//业务日期
					Date bizDate = null;
					if(rs.getString("bizDate")!=null){
						if(isSpecial.equals("1")){
							if(rs.getString("bizDate").equalsIgnoreCase("0000-00-00 00:00:00")){
								if(rs.getTimestamp("cdBizDate") != null){
									bizDate = rs.getTimestamp("cdBizDate");
								}else{
									msg = "中间表ID："+bizID+" 没有业务日期和初戴日期！";
							    	logEntryInfo = new SyncLogEntryInfo();
									logEntryInfo.setId(BOSUuid.create("3575EC2D"));
									logEntryInfo.setLoginfo(msg);
									logEntryInfo.setParent(logInfo);
									logInfo.getEntrys().add(logEntryInfo);
									continue;
								}
							}
						}else{
							bizDate = rs.getTimestamp("bizDate");
						}
					}else{
						msg = "中间表ID："+bizID+" 没有业务日期！";
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
						continue;
					}
					
					DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String billbizdateStr = "";
					int year = 0;
					int month = 0;
					billbizdateStr = sdf.format(bizDate);
					year = Integer.parseInt(billbizdateStr.substring(0,4));
					month = Integer.parseInt(billbizdateStr.substring(5,7));
	
					//门诊
					String company = rs.getString("company");
					//初戴门诊
					String cdCompany = rs.getString("cdCompany");
					//保持器（结束）门诊
					String bcqCompany = rs.getString("bcqCompany");
					//治疗类型
					String feeType = rs.getString("feeType");
					//治疗子类型
					String feeTypeDetail = rs.getString("feeTypeDetail");
					//病历号
					String account = rs.getString("account");
					//治疗过程ID
					String expenseID = rs.getString("expenseID");
					//节点
					String node =rs.getString("node");
					//医生
					String docNo = rs.getString("docNo");
					DoctorInfo doctorInfo = null;
					
					try {
						doctorInfo = InfoF7Util.getDoctorInfoF7(ctx, docNo);
					} catch (Exception e) {
						msg = "中间表ID：" + bizID +" "+ e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
						continue;
					}
					
					//医生级别编码
					String levelId = "";
					if(doctorInfo.getStafflevel().getId()!= null ){
						levelId = doctorInfo.getStafflevel().getId().toString();
					}
					if(levelId == "" || levelId == null){
						msg = "中间表ID：" + bizID +" 的医生未设置对应的级别！";
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
						continue;
					}
						
					//费用金额1
					BigDecimal amountOne = rs.getBigDecimal("amountOne")==null?new BigDecimal("0"):rs.getBigDecimal("amountOne");
					//费用金额2
					BigDecimal amountTwo = rs.getBigDecimal("amountTwo")==null?new BigDecimal("0"):rs.getBigDecimal("amountTwo");
					//费用金额3
					BigDecimal amountThree = rs.getBigDecimal("amountThree")==null?new BigDecimal("0"):rs.getBigDecimal("amountThree");
					//特殊减免金额
					BigDecimal specialAmount = rs.getBigDecimal("specialAmount")==null?new BigDecimal("0"):rs.getBigDecimal("specialAmount");
					//费用同意书中初戴金额
					BigDecimal chudaiAmount = rs.getBigDecimal("chudaiamount")==null?new BigDecimal("0"):rs.getBigDecimal("chudaiamount");
					//费用同意书中结束金额
					BigDecimal jieshuAmount = rs.getBigDecimal("jieshuamount")==null?new BigDecimal("0"):rs.getBigDecimal("jieshuamount");
					//总金额
					BigDecimal totalPay = new BigDecimal("0");
					//初戴业务日期
					Date cdBizDate = rs.getTimestamp("cdBizDate");
					//节点
					String cdNode = rs.getString("cdNode");
					//初戴治疗子类型
					String cdFeeTypeDetail = rs.getString("cdFeeTypeDetail");
					//保持器业务日期
					Date bcqBizDate = rs.getTimestamp("bcqBizDate");
					String billBcqBizDateStr = "";
					if(bcqBizDate != null){
						billBcqBizDateStr = sdf.format(bcqBizDate);
					}	
					//保持器节点
					String bcqNode = rs.getString("bcqNode");
					//保持器治疗子类型
					String bcqFeeTypeDetail = rs.getString("bcqFeeTypeDetail");
					//特殊减免金额
					BigDecimal jmjSpecialAmount = rs.getBigDecimal("jmjSpecialAmount")==null?new BigDecimal("0"):rs.getBigDecimal("jmjSpecialAmount");
					//特殊减免金的业务日期
					Date jmjBizDate = rs.getTimestamp("jmjBizDate");
				
					//3.2将字段分别封装到表头、收款计划、分录对象中
					BigDecimal exchangeRate = new BigDecimal(1.00);
					BigDecimal zero = new BigDecimal(0.00);
					BigDecimal one = new BigDecimal(1.00);
					BigDecimal negativeOne = new BigDecimal("-1"); 
					OtherBillInfo otherBillInfo = null;
	                //表头					
		    		otherBillInfo = new OtherBillInfo();     		
			        //处理company为null情况下的相关字段
					OrgmapInfo orgmapInfo = null;
					CompanyOrgUnitInfo companyInfo = null;
		    		//管理单元
		    		otherBillInfo.setCU(null);
		    		
					if(company!=null && !company.equals("")){
						try {
							orgmapInfo = InfoF7Util.getOrgmapInfoF7(ctx, company);
						} catch (Exception e) {
							msg = e.getMessage();
							logEntryInfo = new SyncLogEntryInfo();
							logEntryInfo.setId(BOSUuid.create("3575EC2D"));
							logEntryInfo.setLoginfo(msg);
							logEntryInfo.setParent(logInfo);
							logInfo.getEntrys().add(logEntryInfo);
							continue;
						}
						companyInfo =  orgmapInfo.getOrg();
						//管理单元
			    		otherBillInfo.setCU(companyInfo.getCU());
					}
					
					//财务组织
		    		otherBillInfo.setCompany(companyInfo);
		    		//根据财务组织找应收的启用期间
		    		Date beginDate = null;
		    		try {
		    			if(isSpecial.equals("1")){
		    				beginDate = bizDate;
		    			}else{
		    				beginDate = InfoF7Util.getBeginDate(ctx, companyInfo.getId().toString(), 12);
		    			}
					} catch (Exception e4) {
						msg = e4.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
						continue;
					}
					
		    		//业务日期
		    		otherBillInfo.setBizDate(bizDate);
		    		//币别
		    		try {
						otherBillInfo.setCurrency(InfoF7Util.getCurrencyInfoF7(ctx,"BB01"));
					} catch (Exception e) {
						e.printStackTrace();
						
					}
		    		//单据日期
		    		otherBillInfo.setBillDate(bizDate);
		    		//汇率
		    		otherBillInfo.setExchangeRate(exchangeRate);
		    		//单据类型
		    		otherBillInfo.setBillType(OtherBillTypeEnum.getEnum(OtherBillTypeEnum.EXPENSEINVOICE_VALUE));
			    	//往来类型
		    		try {
						otherBillInfo.setAsstActType(InfoF7Util.getAsstActTypeInfoF7(ctx,"客户"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
		    		//客户
		    		CustomerInfo customer = null;
					try {
						customer = InfoF7Util.getCustomerInfoF7(ctx,account);
			    		//往来户ID
			    		otherBillInfo.setAsstActID(customer.getId().toString());
			    		//往来户名称
			    		otherBillInfo.setAsstActName(customer.getName());
			    		//往来户编码
			    		otherBillInfo.setAsstActNumber(customer.getNumber());
					} catch (Exception e) {
						msg = e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
						continue;
					}
					
					otherBillInfo.setDescription(bizID);
		    		//是否已生成开票申请单（机动车）
		    		otherBillInfo.setIsVehicleInvoiceReq(false);
		    		//不参与坏账计提
		    		otherBillInfo.setIsNotJoinBadAccount(false);
		    		//是否已生成开票申请单（增值税）
		    		otherBillInfo.setIsMakeInvoiceReq(false);
		    		//是否已生成开票单
		    		otherBillInfo.setIsMakeInvoiced(false);
		    		//是否已开发票
		    		otherBillInfo.setIsInvoiced(false);
		    		//已开发票金额
		    		otherBillInfo.setInvoicedAmt(zero);
		    		//电子发票生成
		    		otherBillInfo.setIsCreatedByArElecInvoice(false);
		    		//坏账金额本位币
		    		otherBillInfo.setTotalBadAmountLocal(zero);
		    		//坏账金额
		    		otherBillInfo.setTotalBadAmount(zero);
		    		//销售组织
		    		//otherBillInfo.setSaleOrg(InfoF7Util.getSaleOrgUnitInfoF7(ctx,company));
		    		//结算状态
		    		otherBillInfo.setVerifyStatus(verifyStatusEnum.getEnum(verifyStatusEnum.UNVERIFY_VALUE));
		    		//异币种标识
		    		otherBillInfo.setIsDiffCurrency(false);
		    		//是否生成利润中心凭证
		    		otherBillInfo.setPcaVouchered(false);
		    		//税额本位币
		    		otherBillInfo.setTotalTaxLocal(zero);
		    		//单据状态
		    		otherBillInfo.setBillStatus(BillStatusEnum.getEnum(BillStatusEnum.SAVE_VALUE));
		    		//金额本位币	    		
		    		otherBillInfo.setTotalAmountLocal(totalPay);
		    		//是否转移指定
		    		otherBillInfo.setIsTransPoint(false);
		    		//是否转应收应付
		    		otherBillInfo.setIsTransOtherBill(false);
		    		//整单关联算法
		    		otherBillInfo.setBillRelationOption(BillRelationOptionEnum.getEnum(BillRelationOptionEnum.NULL_VALUE));
		    		//是否业务应收应付单据
		    		otherBillInfo.setIsBizBill(false);
		    		//是否生成协同单据
		    		otherBillInfo.setIsGenCoopBill(false);
		    		//是否协同生成
		    		otherBillInfo.setIsCoopBuild(false);
		    		//是否拆单单据
		    		otherBillInfo.setIsSplitBill(false);
		    		//是否从总账引入的数据
		    		otherBillInfo.setIsImpFromGL(false);
		    		//是否已生成指定凭证
		    		otherBillInfo.setIsAppointVoucher(false);
		    		//对账方式
		    		otherBillInfo.setIsNeedVoucher(true);
		    		//是否价外税
		    		otherBillInfo.setIsPriceWithoutTax(true);
		    		//是否含税
		    		otherBillInfo.setIsInTax(true);
		    		//是否物流的单据
		    		otherBillInfo.setIsSCMBill(false);
		    		//业务日期所在期间
		    		otherBillInfo.setPeriod(month);
		    		//业务日期所在年度
		    		otherBillInfo.setYear(year);
		    		//是否红字发票
		    		otherBillInfo.setRedBlueType(false);
		    		//是否期初单据
		    		otherBillInfo.setIsInitializeBill(false);
		    		//是否已经调汇
		    		otherBillInfo.setIsExchanged(false);
		    		//价税合计（费用金额合计）
		    		otherBillInfo.setTotalTaxAmount(totalPay);
		    		//税额合计
		    		otherBillInfo.setTotalTax(zero);
		    		//金额合计
		    		otherBillInfo.setTotalAmount(totalPay);
		    		//最后调汇汇率
		    		otherBillInfo.setLastExhangeRate(one);
		    		//付款方式
		    		try {
						otherBillInfo.setPaymentType(InfoF7Util.getPaymentTypeInfoF7(ctx,"赊销"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					//人员todo 暂放
		    		try {
		    			String personId = InfoF7Util.getDoctorInfoF7(ctx,docNo).getPerson().getId().toString();
		    			PersonInfo personInfo = InfoF7Util.getPersonInfoF7(ctx,personId);
						otherBillInfo.setPerson(personInfo);
					} catch (Exception e3) {
						msg = "中间表医生ID" + docNo +  " : " + e3.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
						continue;
					}
		    		//是否是导入单据
		    		otherBillInfo.setIsImportBill(false);
		    		//是否折让单据
		    		otherBillInfo.setIsAllowanceBill(false);
		    		//是否转移生成单据
		    		otherBillInfo.setIsTransBill(false);
		    		//是否为冲销单据
		    		otherBillInfo.setIsReverseBill(false);
		    		//是否已被冲销
		    		otherBillInfo.setIsReversed(false);
		    		//是否已生成凭证
		    		otherBillInfo.setFiVouchered(false);
		    		//未结算本位币金额
		    		otherBillInfo.setUnVerifyAmountLocal(totalPay);
		    		//未结算金额
		    		otherBillInfo.setUnVerifyAmount(totalPay);
		    		//已结算金额本位币
		    		otherBillInfo.setVerifyAmountLocal(zero);
		    		//已结算金额
		    		otherBillInfo.setVerifyAmount(zero);
		    		//应收（付）金额本位币
		    		otherBillInfo.setAmountLocal(totalPay);
		    		//应收（付）金额
		    		otherBillInfo.setAmount(totalPay);
		    		//最后修改时间
		    		otherBillInfo.setLastUpdateTime(new Timestamp(bizDate.getTime()));
		    		//最后修改者
		    		try {
						otherBillInfo.setLastUpdateUser(InfoF7Util.getUserInfoF7(ctx,"user"));
					} catch (Exception e2) {
						e2.printStackTrace();
					}
		    		//创建时间
		    		otherBillInfo.setCreateTime(new Timestamp(bizDate.getTime()));
		    		//创建者
		    		try {
						otherBillInfo.setCreator(InfoF7Util.getUserInfoF7(ctx,"user"));
					} catch (Exception e) {
						e.printStackTrace();
					}	    		
					//收款计划
			    	OtherBillPlanInfo planInfo = new OtherBillPlanInfo();
			    	//应收应付日期
			    	planInfo.setRecievePayDate(bizDate);
			    	//应收应付金额
			    	planInfo.setRecievePayAmount(totalPay);
			    	//应收应付金额本位币
			    	planInfo.setRecievePayAmountLocal(totalPay);
			    	//未锁定金额
			    	planInfo.setUnLockAmount(totalPay);
			    	//未锁定金额本位币
			    	planInfo.setUnLockAmountLoc(totalPay);
			    	//已锁定金额
			    	planInfo.setLockAmount(zero);
			    	//已锁定金额本位币
			    	planInfo.setLockAmountLoc(zero);
			    	//未结算金额
			    	planInfo.setUnVerifyAmount(totalPay);
			    	//未结算金额本位币
			    	planInfo.setUnVerifyAmountLocal(totalPay);
			    	
			    	planInfo.setParent(otherBillInfo);
			    	otherBillInfo.getRecievePlan().add(planInfo);
			    	//分录
			    	OtherBillentryInfo otherBillentryInfo = new OtherBillentryInfo();
			    	CustomerInfo customerInfo = null;
					try {
						customerInfo = InfoF7Util.getCustomerInfoF7(ctx,account);
				    	//送货客户名称
				    	otherBillentryInfo.setSerCustName(customerInfo.getName());
				    	//送货客户编码
				    	otherBillentryInfo.setSerCustNumber(customerInfo.getNumber());
				    	//订货客户名称
				    	otherBillentryInfo.setOrdCustName(customerInfo.getName());
				    	//订货客户编码
				    	otherBillentryInfo.setOrdCustNumber(customerInfo.getNumber());
				    	//收款客户名称
				    	otherBillentryInfo.setRecAsstActName(customerInfo.getName());
				    	//收款客户编码
				    	otherBillentryInfo.setRecAsstActNumber(customerInfo.getNumber());
				    	//收款客户ID
				    	otherBillentryInfo.setRecAsstActID(customerInfo.getId().toString());
				    	//送货客户
				    	otherBillentryInfo.setServiceCustomer(customerInfo);
				    	//订货客户
				    	otherBillentryInfo.setOrderCustomer(customerInfo);
					} catch (Exception e) {
						msg = e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
			    	//是否完全核销
			    	otherBillentryInfo.setIsFullWriteOff(false);
			    	//收款往来类型
			    	try {
						otherBillentryInfo.setRecAsstActType(InfoF7Util.getAsstActTypeInfoF7(ctx,"客户"));
					} catch (Exception e) {
						e.printStackTrace();
					}
			    	//计提坏帐准备金额本位币
			    	otherBillentryInfo.setPreparedBadAmountLocal(zero);
			    	//计提坏帐准备金额
			    	otherBillentryInfo.setPreparedBadAmount(zero);
			    	//坏账金额本位币
			    	otherBillentryInfo.setBadAmountLocal(zero);
			    	//坏账金额
			    	otherBillentryInfo.setBadAmount(zero);
			    	//未开票申请金额本位币
			    	otherBillentryInfo.setUnInvoiceReqAmountLocal(totalPay);
			    	//未开票申请金额
			    	otherBillentryInfo.setUnInvoiceReqAmount(totalPay);
			    	//已开票申请金额本位币
			    	otherBillentryInfo.setInvoiceReqAmountLocal(zero);
			    	//已开票申请金额
			    	otherBillentryInfo.setInvoiceReqAmount(zero);
			    	//未开票申请基本数量
			    	otherBillentryInfo.setUnInvoiceReqBaseQty(one);
			    	//未开票申请数量
			    	otherBillentryInfo.setUnInvoiceReqQty(one);
			    	//已开票申请基本数量
			    	otherBillentryInfo.setInvoiceReqBaseQty(zero);
			    	//已开票申请数量
			    	otherBillentryInfo.setInvoiceReqQty(zero);
			    	//零数量
			    	otherBillentryInfo.setIsQtyZero(false);
			    	//单据日期
			    	otherBillentryInfo.setBillDate(bizDate);
			    	//公司
				    String bosUuid ="";
			    	if(companyInfo!=null){
			    		bosUuid = companyInfo.getId().toString();
			    	}
			    	otherBillentryInfo.setCompany(bosUuid);
			    	//已冲回基本数量
			    	otherBillentryInfo.setReversedBaseQty(zero);
			    	//已分摊金额本位币
			    	otherBillentryInfo.setApportionAmtLocal(zero);
			    	//已锁定数量
			    	otherBillentryInfo.setLockVerifyQty(zero);
			    	//已结算金额
			    	otherBillentryInfo.setVerifyQty(zero);
			    	//是否已开完票
			    	otherBillentryInfo.setIsInvoiced(false);
			    	//已开发票金额
			    	otherBillentryInfo.setInvoicedAmt(zero);
			    	//已开发票基本数量
			    	otherBillentryInfo.setInvoicedBaseQty(zero);
			    	//未核销本位币金额
			    	otherBillentryInfo.setLocalUnwriteOffAmount(totalPay);
			    	//未核销基本数量
			    	otherBillentryInfo.setUnwriteOffBaseQty(one);
			    	//已核销本位币金额
			    	otherBillentryInfo.setLocalWrittenOffAmount(zero);
			    	//已核销基本数量
			    	otherBillentryInfo.setWittenOffBaseQty(zero);
			    	//历史未核销金额本位币
			    	otherBillentryInfo.setHisUnVerifyAmountLocal(zero);
			    	//历史未核销金额
			    	otherBillentryInfo.setHisUnVerifyAmount(zero);
			    	//基本计量单位数量
			    	otherBillentryInfo.setBaseQty(one);
			    	//辅助数量
			    	otherBillentryInfo.setAssistQty(zero);
			    	//金额本位币
			    	otherBillentryInfo.setAmountLocal(totalPay);
			    	//金额
			    	otherBillentryInfo.setAmount(totalPay);
			    	//税额本位币
			    	otherBillentryInfo.setTaxAmountLocal(zero);
			    	//税额
			    	otherBillentryInfo.setTaxAmount(zero);
			    	//税率
			    	otherBillentryInfo.setTaxRate(zero);
			    	//折扣额本位币
			    	otherBillentryInfo.setDiscountAmountLocal(zero);
			    	//折扣额
			    	otherBillentryInfo.setDiscountAmount(zero);
			    	//单位折扣
			    	otherBillentryInfo.setDiscountRate(zero);
			    	//含税单价
			    	otherBillentryInfo.setTaxPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_DOWN));
			    	//实际单价
			    	otherBillentryInfo.setRealPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_DOWN));
			    	//单价
			    	otherBillentryInfo.setPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_DOWN));
			    	//数量
			    	
			    	if(isSpecial.equals("1")){
			    		otherBillentryInfo.setQuantity(negativeOne);
			    	}else{
			    		otherBillentryInfo.setQuantity(one);
			    	}
			    	
			    	//未锁定金额本位币
			    	otherBillentryInfo.setLockUnVerifyAmtLocal(totalPay);
			    	//未锁定金额
			    	otherBillentryInfo.setLockUnVerifyAmt(totalPay);
			    	//已锁定金额本位币
			    	otherBillentryInfo.setLockVerifyAmtLocal(zero);
			    	//已锁定金额
			    	otherBillentryInfo.setLockVerifyAmt(zero);
			    	//未结算金额本位币
			    	otherBillentryInfo.setUnVerifyAmountLocal(totalPay);
			    	//未结算金额
			    	otherBillentryInfo.setUnVerifyAmount(totalPay);
			    	//已结算金额本位币
			    	otherBillentryInfo.setVerifyAmountLocal(zero);
			    	//已结算金额
			    	otherBillentryInfo.setVerifyAmount(zero);
			    	//应收（付）金额本位币
			    	otherBillentryInfo.setRecievePayAmountLocal(totalPay);
			    	//应收（付）金额
			    	otherBillentryInfo.setRecievePayAmount(totalPay);
			    	//是否赠品
			    	otherBillentryInfo.setIsPresent(false);
			    	//费用项目 todo
			    	try {
						otherBillentryInfo.setExpenseItem(InfoF7Util.getExpenseTypeInfoF7FromNumber(ctx,"ZJ0001"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//计量单位
					//otherBillentryInfo.setMeasureUnit(getMeasureUnitInfoF7(ctx,"1000"));
					//物料名称
					//otherBillentryInfo.setMaterialName("");
					//物料
					//otherBillentryInfo.setMaterial(item);
					//行类型 
					//otherBillentryInfo.setRowType(item);
					//otherBillentryInfo.setParent(otherBillInfo);
					//表头
			    	otherBillentryInfo.setHead(otherBillInfo);
			    	otherBillInfo.getEntry().add(otherBillentryInfo);
			    	
		    		//金额本位币
	    			//是否生成应收单
	    			int isCreatIOtherBill = 1;
	    			//比例
	    			BigDecimal ratio = new BigDecimal("0");
		    		//获取比例
			    	sqlbuff = new StringBuffer();
					sqlbuff.append("select t1.CFSYSTEMNODETYPECODE sysNodeCode ,t1.CFSYSTEMTREATMENTTYPECODE sysTrestmentCode,");
					sqlbuff.append("t2.cfratio ratio,t4.FNUMBER easNodeCode");
					sqlbuff.append(" from CT_MAP_TreatmentNodeCoding t1");
					sqlbuff.append(" left join CT_MAP_IncomeRatio t2 on t1.CFTrestmentTypeID =t2.CFTreatmentTypeNam and t1.CFNodeTypeID = t2.CFNodeTypeNameID  and t2.CFDoctorLevelID = '"+levelId+"'");
					sqlbuff.append(" left join CT_MAP_TreatmentType t3 on t1.CFTrestmentTypeID = t3.FID");
					sqlbuff.append(" left join CT_MAP_NodeType t4 on t1.CFNodeTypeID = t4.FID where t3.FNUMBER = '001'");

					if(isSpecial.equals("1")){
						//特殊减免金  	    				     				
						//特殊减免金对应的日期bizDate 初戴对应的日期cdBizDate 保持器对应的日期bcqBizDate
						if(cdBizDate==null){
							//1.cdBizDate为null 不处理
							isCreatIOtherBill=0;
	    			     }else if(cdBizDate.getTime()>bizDate.getTime()){
	    			    	 //2.初戴前  
	    			    	 IRowSet incomeRation = DbUtil.executeQuery(ctx, getSql(sqlbuff,cdNode,cdFeeTypeDetail));
	    					 while(incomeRation.next()){
	    						 ratio = new BigDecimal(incomeRation.getString("ratio")).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP);
	    					 }
	    				     totalPay = specialAmount.multiply(ratio).setScale(2,BigDecimal.ROUND_HALF_UP);
	    				     //计算基础
	    				     otherBillInfo.put("jsjc", specialAmount);
	    				     //重新给company相关的字段赋值

		    				 try {
								 orgmapInfo = InfoF7Util.getOrgmapInfoF7(ctx, cdCompany);
							 }catch(Exception e) {
								 msg = e.getMessage();
								 logEntryInfo = new SyncLogEntryInfo();
								 logEntryInfo.setId(BOSUuid.create("3575EC2D"));
							 	 logEntryInfo.setLoginfo(msg);
								 logEntryInfo.setParent(logInfo);
								 logInfo.getEntrys().add(logEntryInfo);
								 continue;
							 }
		    				 companyInfo =  orgmapInfo.getOrg();
	    		    		 //财务组织
	    		    		 otherBillInfo.setCompany(companyInfo);
	    		    		 //管理单元
	    		    		 otherBillInfo.setCU(companyInfo.getCU());
	    			    	 //公司
	    		    		 otherBillInfo.getEntry().get(0).setCompany(companyInfo.getId().toString());	    				 
	    			     }else if (cdBizDate.getTime()<=bizDate.getTime()){
	    			    	 //初戴后 
    				         if(bcqBizDate==null||bizDate.getTime()<bcqBizDate.getTime()){
    				        	 //3.bizDate<bcqBizDate 
    				        	 //4.bcqBizDate 为null
	    						 //70%
	    				         IRowSet incomeRation = DbUtil.executeQuery(ctx, getSql(sqlbuff,cdNode,cdFeeTypeDetail));
		    					 while(incomeRation.next()){
		    						 ratio = new BigDecimal(incomeRation.getString("ratio")).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP);
		    					 }
		    					 totalPay = specialAmount.multiply(ratio).setScale(2,BigDecimal.ROUND_HALF_UP);
		    				     //计算基础
		    				     otherBillInfo.put("jsjc", specialAmount);
		    				     //重新给company相关的字段赋值
			    				 try {
									orgmapInfo = InfoF7Util.getOrgmapInfoF7(ctx, cdCompany);
								 } catch (Exception e) {
									msg = e.getMessage();
									logEntryInfo = new SyncLogEntryInfo();
									logEntryInfo.setId(BOSUuid.create("3575EC2D"));
									logEntryInfo.setLoginfo(msg);
									logEntryInfo.setParent(logInfo);
									logInfo.getEntrys().add(logEntryInfo);
									continue;
								 }
			    				 companyInfo =  orgmapInfo.getOrg();
		    		    		 //财务组织
		    		    		 otherBillInfo.setCompany(companyInfo);
		    		    		 //管理单元
		    		    		 otherBillInfo.setCU(companyInfo.getCU());
		    			    	 //公司
		    		    		 otherBillentryInfo.setCompany(companyInfo.getId().toString());	

    				         }else{
    				        	 //5.bizDate>=bcqBizDate
    				        	 //100%
	    						 totalPay = specialAmount.multiply(new BigDecimal("1").setScale(2,BigDecimal.ROUND_HALF_UP));
		    				     //计算基础
		    				     otherBillInfo.put("jsjc", specialAmount);
		    				     //重新给company相关的字段赋值
			    				 try {
									orgmapInfo = InfoF7Util.getOrgmapInfoF7(ctx, bcqCompany);
			    				 }catch (Exception e) {
									msg = e.getMessage();
									logEntryInfo = new SyncLogEntryInfo();
									logEntryInfo.setId(BOSUuid.create("3575EC2D"));
									logEntryInfo.setLoginfo(msg);
									logEntryInfo.setParent(logInfo);
									logInfo.getEntrys().add(logEntryInfo);
									continue;
			    				 }
			    				 companyInfo =  orgmapInfo.getOrg();
		    		    		 //财务组织
		    		    		 otherBillInfo.setCompany(companyInfo);
		    		    		 //管理单元
		    		    		 otherBillInfo.setCU(companyInfo.getCU());
		    			    	 //公司
		    		    		 otherBillInfo.getEntry().get(0).setCompany(companyInfo.getId().toString());
    				         }	    					 
    				     }
					}else if(isSpecial.equals("0")){
						//非特殊减免金
				    	//医生级别todo
						IRowSet incomeRation = DbUtil.executeQuery(ctx, getSql(sqlbuff,node,feeTypeDetail));
						while(incomeRation.next()){
				    		easNodeTypeCode = incomeRation.getString("easNodeCode");
				    		String ratioStr = incomeRation.getString("ratio");
							ratio = new BigDecimal(incomeRation.getString("ratio")).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP);
						}
						
		    			if(easNodeTypeCode.equals("A01")){
		    				//初戴	
		    				totalPay = ((amountOne.add(amountTwo)).add(amountThree)).multiply(ratio).setScale(2,BigDecimal.ROUND_HALF_UP);
		   				    //计算基础
		   				    otherBillInfo.put("jsjc", (amountOne.add(amountTwo)).add(amountThree));
							//治疗阶段
							otherBillInfo.put("zljd",expenseID + ":初戴");
		    			}else if(easNodeTypeCode.equals("A02")){
		    				//保持器
							otherBillInfo.put("zljd",expenseID + ":结束");		    				
		    				//获取初戴的总金额
							/**
							 * 
		    				BigDecimal cdTotalPay = new BigDecimal("0");
		    		    	StringBuffer cdSqlbuff = new StringBuffer();
		    		    	cdSqlbuff.append(" select * from t_ortho where expenseID = '"+expenseID+"' and node = '"+node+"'" );	    				
		    		    	ResultSet cdrs = null;
		    				try {
		    					//收据结果集
		    					cdrs = MysqlQueryRsUtil.getRs(ctx,conn,ps,cdSqlbuff.toString());
		    				} catch (SQLException e1) {
		    					msg = "错误信息：获取结果集失败。";
		    			    	logEntryInfo = new SyncLogEntryInfo();
		    					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
		    					logEntryInfo.setLoginfo(msg);
		    					logEntryInfo.setParent(logInfo);
		    					logInfo.getEntrys().add(logEntryInfo);
		    				} catch (ClassNotFoundException e1) {
		    					msg = "错误信息：获取结果集失败。";
		    			    	logEntryInfo = new SyncLogEntryInfo();
		    					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
		    					logEntryInfo.setLoginfo(msg);
		    					logEntryInfo.setParent(logInfo);
		    					logInfo.getEntrys().add(logEntryInfo);
		    				}
		    				while(cdrs.next()){
			    				//费用金额1
		    					BigDecimal cdAmountOne = cdrs.getBigDecimal("amountOne")==null?new BigDecimal("0"):cdrs.getBigDecimal("amountOne");
			    				//费用金额2
		    					BigDecimal cdAmountTwo = cdrs.getBigDecimal("amountTwo")==null?new BigDecimal("0"):cdrs.getBigDecimal("amountTwo");
			    				//费用金额3
		    					BigDecimal cdAmountThree = cdrs.getBigDecimal("amountThree")==null?new BigDecimal("0"):cdrs.getBigDecimal("amountThree");
		    					//（金额1+2+3）*（1-节点比例）
			    				cdTotalPay = (cdAmountOne.add(cdAmountTwo).add(cdAmountThree)).multiply((new BigDecimal("1").subtract(ratio))).setScale(2,BigDecimal.ROUND_HALF_UP);
		    				}
		    				//总金额
		    				totalPay = ((amountOne.add(amountTwo)).add(amountThree)).subtract(cdTotalPay);
							*/
							//更改获取结束总金额的逻辑   金额1+金额2+金额3 - EAS中初戴的总金额
							BigDecimal EASAmount = zero;
							try {
								EASAmount = InfoF7Util.getEASAmount(ctx,expenseID + ":初戴");
							} catch (Exception e) {
		    					msg = "错误信息：获取EAS中初戴总金额失败。";
		    			    	logEntryInfo = new SyncLogEntryInfo();
		    					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
		    					logEntryInfo.setLoginfo(msg);
		    					logEntryInfo.setParent(logInfo);
		    					logInfo.getEntrys().add(logEntryInfo);
							}
							totalPay = ((amountOne.add(amountTwo)).add(amountThree)).subtract(EASAmount);
		   				    //计算基础
		   				    otherBillInfo.put("jsjc", (amountOne.add(amountTwo)).add(amountThree));
		    				//生成保持器特殊减免金额应收单
		    				if(jmjBizDate !=null && jmjBizDate.getTime()<bizDate.getTime()){
								incomeRation = DbUtil.executeQuery(ctx, getSql(sqlbuff,bcqNode,bcqFeeTypeDetail));
								while(incomeRation.next()){
									ratio = new BigDecimal(incomeRation.getString("ratio")).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP);
								}
								BigDecimal jmjTotalPay = jmjSpecialAmount.multiply(ratio).setScale(2,BigDecimal.ROUND_HALF_UP);
								OtherBillInfo jmjOtherBillInfo =(OtherBillInfo)otherBillInfo.clone();
								jmjOtherBillInfo.getEntry().get(0).setQuantity(negativeOne);
								jmjOtherBillInfo.setId(BOSUuid.create(jmjOtherBillInfo.getBOSType()));
								creatIOtherBill(ctx,jmjOtherBillInfo,jmjTotalPay,logInfo,bizDate,beginDate);
		    				}
		    			}
					}
					
					//替换totalPay，应收单保存、提交、审核
					if(isCreatIOtherBill==1){
						//当初戴存在正畸费用同意书时取正畸费用同意书初戴金额和费用项目
						if(node != null && !node.equals("")){
							if(node.startsWith("first") && chudaiAmount.compareTo(zero)!=0){
								totalPay = chudaiAmount;
								try {
									otherBillentryInfo.setExpenseItem(InfoF7Util.getExpenseTypeInfoF7(ctx,rs.getString("chudaiexpenseNo")));
									//是否有治疗同意书
									otherBillInfo.put("zltys",true);
								} catch (Exception e) {
									msg = e.getMessage();
									logEntryInfo = new SyncLogEntryInfo();
									logEntryInfo.setId(BOSUuid.create("3575EC2D"));
									logEntryInfo.setLoginfo(msg);
									logEntryInfo.setParent(logInfo);
									logInfo.getEntrys().add(logEntryInfo);
								}
							}
							if(node.startsWith("finish") && jieshuAmount.compareTo(zero)!=0){
								totalPay = jieshuAmount;
								try {
		    		    		otherBillentryInfo.setExpenseItem(InfoF7Util.getExpenseTypeInfoF7(ctx,rs.getString("jieshuexpenseNo")));
								//是否有治疗同意书
								otherBillInfo.put("zltys",true);
								} catch (Exception e) {
									msg = e.getMessage();
									logEntryInfo = new SyncLogEntryInfo();
									logEntryInfo.setId(BOSUuid.create("3575EC2D"));
									logEntryInfo.setLoginfo(msg);
									logEntryInfo.setParent(logInfo);
									logInfo.getEntrys().add(logEntryInfo);
								}
							}
						}
						creatIOtherBill(ctx,otherBillInfo,totalPay,logInfo,bizDate,beginDate); 
					}
				}catch (Exception e){
					msg = e.getMessage();
					logEntryInfo = new SyncLogEntryInfo();
					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
					logEntryInfo.setLoginfo(msg);
					logEntryInfo.setParent(logInfo);
					logInfo.getEntrys().add(logEntryInfo);
				}
			}
			
		} catch (NumberFormatException e1){
			// TODO Auto-generated catch block
			msg = e1.getMessage();
			logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			msg = e1.getMessage();
			logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		} 
		//遍历rs结束
		
		//将已生成应收单的数据更新为已处理（即isHandled=1）
		String sql = "update t_ortho set isHandled ='1' where id in("+strBizIDs.substring(0, strBizIDs.length()-1)+")";
		System.out.println("=====更新sql语句："+sql+"");
  		try {
			MysqlQueryRsUtil.updateRs(conn, sql);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//关闭连接
		try {
			mysqlConnectionUtil.close(conn, ps, rs);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//保存msg
		if(logInfo != null){
			try {
				SyncLogFactory.getLocalInstance(ctx).save(logInfo);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				System.out.println("---saveLogInfo"+e.getMessage()+"------------------------");
				e.printStackTrace();
			}
		}
    }
 
    /** 
     * 应收单保存、提交、审核
     * @param ctx
     * @param otherBillInfo
     * @param totalPay
     * @param logInfo
     * @param bizDate
     * @param beginDate
     * @throws BOSException 
     * @throws EASBizException 
     */
    public void creatIOtherBill(Context ctx,OtherBillInfo otherBillInfo , BigDecimal totalPay,SyncLogInfo logInfo,Date bizDate,Date beginDate ){
    	
    	SyncLogEntryInfo logEntryInfo  = null;
    	String msg = "";
    	
    	//替换替换totalPay
    	BigDecimal one = new BigDecimal(1.00); 
    	BigDecimal negativeOne = new BigDecimal("-1"); 
    	BigDecimal negativeTotalPay = new BigDecimal("0"); 
    	
   		//如果为特殊减免金
    	//分录
    	//含税单价
    	otherBillInfo.getEntry().get(0).setTaxPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_UP));
    	//实际单价
    	otherBillInfo.getEntry().get(0).setRealPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_UP));
    	//单价
    	otherBillInfo.getEntry().get(0).setPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_UP)); 		
    	if(otherBillInfo.getEntry().get(0).getQuantity().compareTo(negativeOne)==0){
    		totalPay =  totalPay.multiply(negativeOne.setScale(2,BigDecimal.ROUND_HALF_UP));
    	}

    	//表头
    	otherBillInfo.setTotalAmountLocal(totalPay);
		//价税合计（费用金额合计）
		otherBillInfo.setTotalTaxAmount(totalPay);
		//金额合计
		otherBillInfo.setTotalAmount(totalPay);
		//未结算本位币金额
		otherBillInfo.setUnVerifyAmountLocal(totalPay);
		//未结算金额
		otherBillInfo.setUnVerifyAmount(totalPay);
		//应收（付）金额本位币
		otherBillInfo.setAmountLocal(totalPay);
		//应收（付）金额
		otherBillInfo.setAmount(totalPay);
		//收款计划
		//应收应付金额
		otherBillInfo.getRecievePlan().get(0).setRecievePayAmount(totalPay);
		//应收应付金额本位币
		otherBillInfo.getRecievePlan().get(0).setRecievePayAmountLocal(totalPay);
		//未锁定金额
		otherBillInfo.getRecievePlan().get(0).setUnLockAmount(totalPay);
		//未锁定金额本位币
		otherBillInfo.getRecievePlan().get(0).setUnLockAmountLoc(totalPay);
		//未结算金额
		otherBillInfo.getRecievePlan().get(0).setUnVerifyAmount(totalPay);
		//未结算金额本位币
		otherBillInfo.getRecievePlan().get(0).setUnVerifyAmountLocal(totalPay);
		//分录
		//未开票申请金额本位币
		otherBillInfo.getEntry().get(0).setUnInvoiceReqAmountLocal(totalPay);
		//未开票申请金额
		otherBillInfo.getEntry().get(0).setUnInvoiceReqAmount(totalPay);
		//未核销本位币金额
		otherBillInfo.getEntry().get(0).setLocalUnwriteOffAmount(totalPay);
		//金额本位币
		otherBillInfo.getEntry().get(0).setAmountLocal(totalPay);
    	//金额
    	otherBillInfo.getEntry().get(0).setAmount(totalPay);
    	//未锁定金额本位币
    	otherBillInfo.getEntry().get(0).setLockUnVerifyAmtLocal(totalPay);
    	//未锁定金额
    	otherBillInfo.getEntry().get(0).setLockUnVerifyAmt(totalPay);
    	//未结算金额本位币
    	otherBillInfo.getEntry().get(0).setUnVerifyAmountLocal(totalPay);
    	//未结算金额
    	otherBillInfo.getEntry().get(0).setUnVerifyAmount(totalPay);
    	//应收（付）金额本位币
    	otherBillInfo.getEntry().get(0).setRecievePayAmountLocal(totalPay);
    	//应收（付）金额
    	otherBillInfo.getEntry().get(0).setRecievePayAmount(totalPay);
		//应收单保存、提交、审核
		try {
			IOtherBillUtil.creatIOtherBill(ctx, otherBillInfo, totalPay,logInfo,bizDate,beginDate);
			strBizIDs += "'"+otherBillInfo.getDescription()+"',";
		} catch (EASBizException e) {
			msg = "生成应收单出错  "+otherBillInfo.getCompany().getName()+ " 错误信息："+e.getMessage()+"\n";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		} catch (BOSException e) {
			msg = "生成应收单出错  "+otherBillInfo.getCompany().getName()+ " 错误信息："+e.getMessage()+"\n";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		}
		
	}
    
    
	/** 
	 * 拼接sql
	 * @param strBuff
	 * @param node
	 * @param feeTypeDetail
	 * @return
	 */
    public String getSql (StringBuffer strBuff,String node ,String feeTypeDetail){
	    if(feeTypeDetail!=null&&!feeTypeDetail.equals("")){
	    	strBuff.append(" and t1.CFSYSTEMTREATMENTTYPECODE = '"+feeTypeDetail+"'");
	    }
	    if(node!=null&&!node.equals("")){
	    	strBuff.append(" and t1.CFSYSTEMNODETYPECODE = '"+node+"'");
	    }
		return strBuff.toString();
    }
    
}