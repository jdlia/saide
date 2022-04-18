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
 * ��������
 *
 */
public class InitTreatmentNodeIncomeFacadeControllerBean extends AbstractInitTreatmentNodeIncomeFacadeControllerBean
{
	private static Logger logger = Logger.getLogger("com.kingdee.eas.custom.sdyg.InitTreatmentNodeIncomeFacadeControllerBean");

	private static String strBizIDs = "";
	
    /** 
     * ��ʼ������������
     */
    @Override
    protected void _initTreatmentInfo(Context ctx) throws BOSException,EASBizException {
    	// TODO Auto-generated method stub
    	// super._initTreatmentInfo(ctx);

    	System.out.println("����InitTreatmentNodeIncomeFacadeControllerBean");
    	//[���Ĵ���cd������� ,bcq��������(����)]
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
			//��ѯϵͳ�Ľڵ����ͱ�����������ͱ���
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
					//ϵͳ�С��������Ľڵ����ͱ�����������ͱ���
					cdnodes = cdnodes+"'"+sysNodeCode+"',";
					cdFeeTypeDetails = cdFeeTypeDetails+"'"+sysTrestmentCode+"',"; 
				}else if(easNodeTypeCode.equals("A02")){
					//ϵͳ�С�������(����)���Ľڵ����ͱ�����������ͱ���
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

    	//��ѯ�����м������t_ortho   todo 2021.07.08 ȥ�� t.isHandled IS NULL   and t.company in ('066','080') and t.bizDate != STR_TO_DATE  and t.bizDate >= STR_TO_DATE('2020-01-01 00:00:00','%Y-%m-%d %H:%i:%s')
		sqlbuff = new StringBuffer();
		/**
		sqlbuff.append("select t.* ,t1.bizDate cdBizDate,t2.bizDate bcqBizDate,t1.node cdNode,t2.node bcqNode,t1.feeTypeDetail cdfeeTypeDetail,t2.feeTypeDetail bcqfeeTypeDetail,");
		sqlbuff.append(" t3.specialAmount jmjSpecialAmount ,t3.bizDate jmjBizDate,t1.company cdCompany ,t2.company bcqCompany,chudai.discountAmount chudaiamount,jieshu.discountAmount jieshuamount");  
		sqlbuff.append(" ,chudai.expenseNo chudaiexpenseNo ,jieshu.expenseNo jieshuexpenseNo");  
		sqlbuff.append(" FROM ( select * from t_ortho t where  t.isHandled != '1' AND ( t.company <> '' OR t.isSpecial = '1') and t.bizDate is not null and t.docNo !='' )t ");
		sqlbuff.append(" left join ( select * from t_ortho  where isSpecial != '1' and feeTypeDetail in("+cdFeeTypeDetails+") and node in ("+cdnodes+") and company <> '' ) t1 on t.expenseID = t1.expenseID");
	    sqlbuff.append(" left join ( select * from t_ortho  where isSpecial != '1' and feeTypeDetail in("+bcqFeeTypeDetails+") and node in ("+bcqnodes+") and company <> '' ) t2 on t.expenseID = t2.expenseID");
	    sqlbuff.append(" left join ( select * from t_ortho  where isSpecial = '1' ) t3  on t.expenseID = t3.expenseID");
	    sqlbuff.append(" left join  (select expenseNo,discountAmount,feeagreementid from t_agreement where expenseName like '%��%') chudai on t.feeagreementid = chudai.feeagreementid ");
	    sqlbuff.append(" left join  (select expenseNo,discountAmount,feeagreementid from t_agreement where expenseName like '%��%') jieshu on t.feeagreementid = jieshu.feeagreementid ORDER BY t.bizDate asc");
	    System.out.println("�����м��SQL��"+sqlbuff.toString());
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
				"on t.expenseID = t3.expenseID left join  (select expenseNo,discountAmount,feeagreementid from t_agreement where expenseName like '%��%') chudai " +
				"on t.feeagreementid = chudai.feeagreementid  left join  (select expenseNo,discountAmount,feeagreementid from t_agreement where expenseName like '%��%') jieshu " +
				"on t.feeagreementid = jieshu.feeagreementid ORDER BY t.bizDate asc ) a ");
    	String msg = "";
    	mysqlConnectionUtil sqlutil = new mysqlConnectionUtil();
    	Connection conn = null;
    	SyncLogInfo logInfo = new SyncLogInfo();
    	logInfo.setId(BOSUuid.create("59A5EF45"));
    	//��־ҵ������
    	logInfo.setBizDate(new Date());
    	//��־����ʱ��
    	logInfo.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
    	SyncLogEntryInfo logEntryInfo = null;
    	
    	try {
			conn = sqlutil.createConn();
		} catch (ClassNotFoundException e1) {
			msg = "������Ϣ�������м���쳣��";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		} catch (SQLException e1) {
			msg = "������Ϣ�������м���쳣��";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		}
		
		//��ȡ�����м������
    	ResultSet rs = null;
    	PreparedStatement ps = null;
		try {
			//�վݽ����
			rs = MysqlQueryRsUtil.getRs(ctx,conn,ps,sqlbuff.toString());
		} catch (SQLException e1) {
			msg = "������Ϣ����ȡ�����ʧ�ܡ�";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		} catch (ClassNotFoundException e1) {
			msg = "������Ϣ����ȡ�����ʧ�ܡ�";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		}
		
		//�����������ʼ����װ��Ӧ�յ�
		try {
			while(rs.next()){
				try{
					//ȡ�������м�������ֶ�
					//����id
					String bizID = rs.getString("ID");
					//�Ƿ��������
					String isSpecial = rs.getString("isSpecial");
					//ҵ������
					Date bizDate = null;
					if(rs.getString("bizDate")!=null){
						if(isSpecial.equals("1")){
							if(rs.getString("bizDate").equalsIgnoreCase("0000-00-00 00:00:00")){
								if(rs.getTimestamp("cdBizDate") != null){
									bizDate = rs.getTimestamp("cdBizDate");
								}else{
									msg = "�м��ID��"+bizID+" û��ҵ�����ںͳ������ڣ�";
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
						msg = "�м��ID��"+bizID+" û��ҵ�����ڣ�";
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
	
					//����
					String company = rs.getString("company");
					//��������
					String cdCompany = rs.getString("cdCompany");
					//������������������
					String bcqCompany = rs.getString("bcqCompany");
					//��������
					String feeType = rs.getString("feeType");
					//����������
					String feeTypeDetail = rs.getString("feeTypeDetail");
					//������
					String account = rs.getString("account");
					//���ƹ���ID
					String expenseID = rs.getString("expenseID");
					//�ڵ�
					String node =rs.getString("node");
					//ҽ��
					String docNo = rs.getString("docNo");
					DoctorInfo doctorInfo = null;
					
					try {
						doctorInfo = InfoF7Util.getDoctorInfoF7(ctx, docNo);
					} catch (Exception e) {
						msg = "�м��ID��" + bizID +" "+ e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
						continue;
					}
					
					//ҽ���������
					String levelId = "";
					if(doctorInfo.getStafflevel().getId()!= null ){
						levelId = doctorInfo.getStafflevel().getId().toString();
					}
					if(levelId == "" || levelId == null){
						msg = "�м��ID��" + bizID +" ��ҽ��δ���ö�Ӧ�ļ���";
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
						continue;
					}
						
					//���ý��1
					BigDecimal amountOne = rs.getBigDecimal("amountOne")==null?new BigDecimal("0"):rs.getBigDecimal("amountOne");
					//���ý��2
					BigDecimal amountTwo = rs.getBigDecimal("amountTwo")==null?new BigDecimal("0"):rs.getBigDecimal("amountTwo");
					//���ý��3
					BigDecimal amountThree = rs.getBigDecimal("amountThree")==null?new BigDecimal("0"):rs.getBigDecimal("amountThree");
					//���������
					BigDecimal specialAmount = rs.getBigDecimal("specialAmount")==null?new BigDecimal("0"):rs.getBigDecimal("specialAmount");
					//����ͬ�����г������
					BigDecimal chudaiAmount = rs.getBigDecimal("chudaiamount")==null?new BigDecimal("0"):rs.getBigDecimal("chudaiamount");
					//����ͬ�����н������
					BigDecimal jieshuAmount = rs.getBigDecimal("jieshuamount")==null?new BigDecimal("0"):rs.getBigDecimal("jieshuamount");
					//�ܽ��
					BigDecimal totalPay = new BigDecimal("0");
					//����ҵ������
					Date cdBizDate = rs.getTimestamp("cdBizDate");
					//�ڵ�
					String cdNode = rs.getString("cdNode");
					//��������������
					String cdFeeTypeDetail = rs.getString("cdFeeTypeDetail");
					//������ҵ������
					Date bcqBizDate = rs.getTimestamp("bcqBizDate");
					String billBcqBizDateStr = "";
					if(bcqBizDate != null){
						billBcqBizDateStr = sdf.format(bcqBizDate);
					}	
					//�������ڵ�
					String bcqNode = rs.getString("bcqNode");
					//����������������
					String bcqFeeTypeDetail = rs.getString("bcqFeeTypeDetail");
					//���������
					BigDecimal jmjSpecialAmount = rs.getBigDecimal("jmjSpecialAmount")==null?new BigDecimal("0"):rs.getBigDecimal("jmjSpecialAmount");
					//���������ҵ������
					Date jmjBizDate = rs.getTimestamp("jmjBizDate");
				
					//3.2���ֶηֱ��װ����ͷ���տ�ƻ�����¼������
					BigDecimal exchangeRate = new BigDecimal(1.00);
					BigDecimal zero = new BigDecimal(0.00);
					BigDecimal one = new BigDecimal(1.00);
					BigDecimal negativeOne = new BigDecimal("-1"); 
					OtherBillInfo otherBillInfo = null;
	                //��ͷ					
		    		otherBillInfo = new OtherBillInfo();     		
			        //����companyΪnull����µ�����ֶ�
					OrgmapInfo orgmapInfo = null;
					CompanyOrgUnitInfo companyInfo = null;
		    		//����Ԫ
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
						//����Ԫ
			    		otherBillInfo.setCU(companyInfo.getCU());
					}
					
					//������֯
		    		otherBillInfo.setCompany(companyInfo);
		    		//���ݲ�����֯��Ӧ�յ������ڼ�
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
					
		    		//ҵ������
		    		otherBillInfo.setBizDate(bizDate);
		    		//�ұ�
		    		try {
						otherBillInfo.setCurrency(InfoF7Util.getCurrencyInfoF7(ctx,"BB01"));
					} catch (Exception e) {
						e.printStackTrace();
						
					}
		    		//��������
		    		otherBillInfo.setBillDate(bizDate);
		    		//����
		    		otherBillInfo.setExchangeRate(exchangeRate);
		    		//��������
		    		otherBillInfo.setBillType(OtherBillTypeEnum.getEnum(OtherBillTypeEnum.EXPENSEINVOICE_VALUE));
			    	//��������
		    		try {
						otherBillInfo.setAsstActType(InfoF7Util.getAsstActTypeInfoF7(ctx,"�ͻ�"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
		    		//�ͻ�
		    		CustomerInfo customer = null;
					try {
						customer = InfoF7Util.getCustomerInfoF7(ctx,account);
			    		//������ID
			    		otherBillInfo.setAsstActID(customer.getId().toString());
			    		//����������
			    		otherBillInfo.setAsstActName(customer.getName());
			    		//����������
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
		    		//�Ƿ������ɿ�Ʊ���뵥����������
		    		otherBillInfo.setIsVehicleInvoiceReq(false);
		    		//�����뻵�˼���
		    		otherBillInfo.setIsNotJoinBadAccount(false);
		    		//�Ƿ������ɿ�Ʊ���뵥����ֵ˰��
		    		otherBillInfo.setIsMakeInvoiceReq(false);
		    		//�Ƿ������ɿ�Ʊ��
		    		otherBillInfo.setIsMakeInvoiced(false);
		    		//�Ƿ��ѿ���Ʊ
		    		otherBillInfo.setIsInvoiced(false);
		    		//�ѿ���Ʊ���
		    		otherBillInfo.setInvoicedAmt(zero);
		    		//���ӷ�Ʊ����
		    		otherBillInfo.setIsCreatedByArElecInvoice(false);
		    		//���˽�λ��
		    		otherBillInfo.setTotalBadAmountLocal(zero);
		    		//���˽��
		    		otherBillInfo.setTotalBadAmount(zero);
		    		//������֯
		    		//otherBillInfo.setSaleOrg(InfoF7Util.getSaleOrgUnitInfoF7(ctx,company));
		    		//����״̬
		    		otherBillInfo.setVerifyStatus(verifyStatusEnum.getEnum(verifyStatusEnum.UNVERIFY_VALUE));
		    		//����ֱ�ʶ
		    		otherBillInfo.setIsDiffCurrency(false);
		    		//�Ƿ�������������ƾ֤
		    		otherBillInfo.setPcaVouchered(false);
		    		//˰�λ��
		    		otherBillInfo.setTotalTaxLocal(zero);
		    		//����״̬
		    		otherBillInfo.setBillStatus(BillStatusEnum.getEnum(BillStatusEnum.SAVE_VALUE));
		    		//��λ��	    		
		    		otherBillInfo.setTotalAmountLocal(totalPay);
		    		//�Ƿ�ת��ָ��
		    		otherBillInfo.setIsTransPoint(false);
		    		//�Ƿ�תӦ��Ӧ��
		    		otherBillInfo.setIsTransOtherBill(false);
		    		//���������㷨
		    		otherBillInfo.setBillRelationOption(BillRelationOptionEnum.getEnum(BillRelationOptionEnum.NULL_VALUE));
		    		//�Ƿ�ҵ��Ӧ��Ӧ������
		    		otherBillInfo.setIsBizBill(false);
		    		//�Ƿ�����Эͬ����
		    		otherBillInfo.setIsGenCoopBill(false);
		    		//�Ƿ�Эͬ����
		    		otherBillInfo.setIsCoopBuild(false);
		    		//�Ƿ�𵥵���
		    		otherBillInfo.setIsSplitBill(false);
		    		//�Ƿ���������������
		    		otherBillInfo.setIsImpFromGL(false);
		    		//�Ƿ�������ָ��ƾ֤
		    		otherBillInfo.setIsAppointVoucher(false);
		    		//���˷�ʽ
		    		otherBillInfo.setIsNeedVoucher(true);
		    		//�Ƿ����˰
		    		otherBillInfo.setIsPriceWithoutTax(true);
		    		//�Ƿ�˰
		    		otherBillInfo.setIsInTax(true);
		    		//�Ƿ������ĵ���
		    		otherBillInfo.setIsSCMBill(false);
		    		//ҵ�����������ڼ�
		    		otherBillInfo.setPeriod(month);
		    		//ҵ�������������
		    		otherBillInfo.setYear(year);
		    		//�Ƿ���ַ�Ʊ
		    		otherBillInfo.setRedBlueType(false);
		    		//�Ƿ��ڳ�����
		    		otherBillInfo.setIsInitializeBill(false);
		    		//�Ƿ��Ѿ�����
		    		otherBillInfo.setIsExchanged(false);
		    		//��˰�ϼƣ����ý��ϼƣ�
		    		otherBillInfo.setTotalTaxAmount(totalPay);
		    		//˰��ϼ�
		    		otherBillInfo.setTotalTax(zero);
		    		//���ϼ�
		    		otherBillInfo.setTotalAmount(totalPay);
		    		//���������
		    		otherBillInfo.setLastExhangeRate(one);
		    		//���ʽ
		    		try {
						otherBillInfo.setPaymentType(InfoF7Util.getPaymentTypeInfoF7(ctx,"����"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					//��Աtodo �ݷ�
		    		try {
		    			String personId = InfoF7Util.getDoctorInfoF7(ctx,docNo).getPerson().getId().toString();
		    			PersonInfo personInfo = InfoF7Util.getPersonInfoF7(ctx,personId);
						otherBillInfo.setPerson(personInfo);
					} catch (Exception e3) {
						msg = "�м��ҽ��ID" + docNo +  " : " + e3.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
						continue;
					}
		    		//�Ƿ��ǵ��뵥��
		    		otherBillInfo.setIsImportBill(false);
		    		//�Ƿ����õ���
		    		otherBillInfo.setIsAllowanceBill(false);
		    		//�Ƿ�ת�����ɵ���
		    		otherBillInfo.setIsTransBill(false);
		    		//�Ƿ�Ϊ��������
		    		otherBillInfo.setIsReverseBill(false);
		    		//�Ƿ��ѱ�����
		    		otherBillInfo.setIsReversed(false);
		    		//�Ƿ�������ƾ֤
		    		otherBillInfo.setFiVouchered(false);
		    		//δ���㱾λ�ҽ��
		    		otherBillInfo.setUnVerifyAmountLocal(totalPay);
		    		//δ������
		    		otherBillInfo.setUnVerifyAmount(totalPay);
		    		//�ѽ����λ��
		    		otherBillInfo.setVerifyAmountLocal(zero);
		    		//�ѽ�����
		    		otherBillInfo.setVerifyAmount(zero);
		    		//Ӧ�գ�������λ��
		    		otherBillInfo.setAmountLocal(totalPay);
		    		//Ӧ�գ��������
		    		otherBillInfo.setAmount(totalPay);
		    		//����޸�ʱ��
		    		otherBillInfo.setLastUpdateTime(new Timestamp(bizDate.getTime()));
		    		//����޸���
		    		try {
						otherBillInfo.setLastUpdateUser(InfoF7Util.getUserInfoF7(ctx,"user"));
					} catch (Exception e2) {
						e2.printStackTrace();
					}
		    		//����ʱ��
		    		otherBillInfo.setCreateTime(new Timestamp(bizDate.getTime()));
		    		//������
		    		try {
						otherBillInfo.setCreator(InfoF7Util.getUserInfoF7(ctx,"user"));
					} catch (Exception e) {
						e.printStackTrace();
					}	    		
					//�տ�ƻ�
			    	OtherBillPlanInfo planInfo = new OtherBillPlanInfo();
			    	//Ӧ��Ӧ������
			    	planInfo.setRecievePayDate(bizDate);
			    	//Ӧ��Ӧ�����
			    	planInfo.setRecievePayAmount(totalPay);
			    	//Ӧ��Ӧ����λ��
			    	planInfo.setRecievePayAmountLocal(totalPay);
			    	//δ�������
			    	planInfo.setUnLockAmount(totalPay);
			    	//δ������λ��
			    	planInfo.setUnLockAmountLoc(totalPay);
			    	//���������
			    	planInfo.setLockAmount(zero);
			    	//��������λ��
			    	planInfo.setLockAmountLoc(zero);
			    	//δ������
			    	planInfo.setUnVerifyAmount(totalPay);
			    	//δ�����λ��
			    	planInfo.setUnVerifyAmountLocal(totalPay);
			    	
			    	planInfo.setParent(otherBillInfo);
			    	otherBillInfo.getRecievePlan().add(planInfo);
			    	//��¼
			    	OtherBillentryInfo otherBillentryInfo = new OtherBillentryInfo();
			    	CustomerInfo customerInfo = null;
					try {
						customerInfo = InfoF7Util.getCustomerInfoF7(ctx,account);
				    	//�ͻ��ͻ�����
				    	otherBillentryInfo.setSerCustName(customerInfo.getName());
				    	//�ͻ��ͻ�����
				    	otherBillentryInfo.setSerCustNumber(customerInfo.getNumber());
				    	//�����ͻ�����
				    	otherBillentryInfo.setOrdCustName(customerInfo.getName());
				    	//�����ͻ�����
				    	otherBillentryInfo.setOrdCustNumber(customerInfo.getNumber());
				    	//�տ�ͻ�����
				    	otherBillentryInfo.setRecAsstActName(customerInfo.getName());
				    	//�տ�ͻ�����
				    	otherBillentryInfo.setRecAsstActNumber(customerInfo.getNumber());
				    	//�տ�ͻ�ID
				    	otherBillentryInfo.setRecAsstActID(customerInfo.getId().toString());
				    	//�ͻ��ͻ�
				    	otherBillentryInfo.setServiceCustomer(customerInfo);
				    	//�����ͻ�
				    	otherBillentryInfo.setOrderCustomer(customerInfo);
					} catch (Exception e) {
						msg = e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
			    	//�Ƿ���ȫ����
			    	otherBillentryInfo.setIsFullWriteOff(false);
			    	//�տ���������
			    	try {
						otherBillentryInfo.setRecAsstActType(InfoF7Util.getAsstActTypeInfoF7(ctx,"�ͻ�"));
					} catch (Exception e) {
						e.printStackTrace();
					}
			    	//���ỵ��׼����λ��
			    	otherBillentryInfo.setPreparedBadAmountLocal(zero);
			    	//���ỵ��׼�����
			    	otherBillentryInfo.setPreparedBadAmount(zero);
			    	//���˽�λ��
			    	otherBillentryInfo.setBadAmountLocal(zero);
			    	//���˽��
			    	otherBillentryInfo.setBadAmount(zero);
			    	//δ��Ʊ�����λ��
			    	otherBillentryInfo.setUnInvoiceReqAmountLocal(totalPay);
			    	//δ��Ʊ������
			    	otherBillentryInfo.setUnInvoiceReqAmount(totalPay);
			    	//�ѿ�Ʊ�����λ��
			    	otherBillentryInfo.setInvoiceReqAmountLocal(zero);
			    	//�ѿ�Ʊ������
			    	otherBillentryInfo.setInvoiceReqAmount(zero);
			    	//δ��Ʊ�����������
			    	otherBillentryInfo.setUnInvoiceReqBaseQty(one);
			    	//δ��Ʊ��������
			    	otherBillentryInfo.setUnInvoiceReqQty(one);
			    	//�ѿ�Ʊ�����������
			    	otherBillentryInfo.setInvoiceReqBaseQty(zero);
			    	//�ѿ�Ʊ��������
			    	otherBillentryInfo.setInvoiceReqQty(zero);
			    	//������
			    	otherBillentryInfo.setIsQtyZero(false);
			    	//��������
			    	otherBillentryInfo.setBillDate(bizDate);
			    	//��˾
				    String bosUuid ="";
			    	if(companyInfo!=null){
			    		bosUuid = companyInfo.getId().toString();
			    	}
			    	otherBillentryInfo.setCompany(bosUuid);
			    	//�ѳ�ػ�������
			    	otherBillentryInfo.setReversedBaseQty(zero);
			    	//�ѷ�̯��λ��
			    	otherBillentryInfo.setApportionAmtLocal(zero);
			    	//����������
			    	otherBillentryInfo.setLockVerifyQty(zero);
			    	//�ѽ�����
			    	otherBillentryInfo.setVerifyQty(zero);
			    	//�Ƿ��ѿ���Ʊ
			    	otherBillentryInfo.setIsInvoiced(false);
			    	//�ѿ���Ʊ���
			    	otherBillentryInfo.setInvoicedAmt(zero);
			    	//�ѿ���Ʊ��������
			    	otherBillentryInfo.setInvoicedBaseQty(zero);
			    	//δ������λ�ҽ��
			    	otherBillentryInfo.setLocalUnwriteOffAmount(totalPay);
			    	//δ������������
			    	otherBillentryInfo.setUnwriteOffBaseQty(one);
			    	//�Ѻ�����λ�ҽ��
			    	otherBillentryInfo.setLocalWrittenOffAmount(zero);
			    	//�Ѻ�����������
			    	otherBillentryInfo.setWittenOffBaseQty(zero);
			    	//��ʷδ������λ��
			    	otherBillentryInfo.setHisUnVerifyAmountLocal(zero);
			    	//��ʷδ�������
			    	otherBillentryInfo.setHisUnVerifyAmount(zero);
			    	//����������λ����
			    	otherBillentryInfo.setBaseQty(one);
			    	//��������
			    	otherBillentryInfo.setAssistQty(zero);
			    	//��λ��
			    	otherBillentryInfo.setAmountLocal(totalPay);
			    	//���
			    	otherBillentryInfo.setAmount(totalPay);
			    	//˰�λ��
			    	otherBillentryInfo.setTaxAmountLocal(zero);
			    	//˰��
			    	otherBillentryInfo.setTaxAmount(zero);
			    	//˰��
			    	otherBillentryInfo.setTaxRate(zero);
			    	//�ۿ۶λ��
			    	otherBillentryInfo.setDiscountAmountLocal(zero);
			    	//�ۿ۶�
			    	otherBillentryInfo.setDiscountAmount(zero);
			    	//��λ�ۿ�
			    	otherBillentryInfo.setDiscountRate(zero);
			    	//��˰����
			    	otherBillentryInfo.setTaxPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_DOWN));
			    	//ʵ�ʵ���
			    	otherBillentryInfo.setRealPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_DOWN));
			    	//����
			    	otherBillentryInfo.setPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_DOWN));
			    	//����
			    	
			    	if(isSpecial.equals("1")){
			    		otherBillentryInfo.setQuantity(negativeOne);
			    	}else{
			    		otherBillentryInfo.setQuantity(one);
			    	}
			    	
			    	//δ������λ��
			    	otherBillentryInfo.setLockUnVerifyAmtLocal(totalPay);
			    	//δ�������
			    	otherBillentryInfo.setLockUnVerifyAmt(totalPay);
			    	//��������λ��
			    	otherBillentryInfo.setLockVerifyAmtLocal(zero);
			    	//���������
			    	otherBillentryInfo.setLockVerifyAmt(zero);
			    	//δ�����λ��
			    	otherBillentryInfo.setUnVerifyAmountLocal(totalPay);
			    	//δ������
			    	otherBillentryInfo.setUnVerifyAmount(totalPay);
			    	//�ѽ����λ��
			    	otherBillentryInfo.setVerifyAmountLocal(zero);
			    	//�ѽ�����
			    	otherBillentryInfo.setVerifyAmount(zero);
			    	//Ӧ�գ�������λ��
			    	otherBillentryInfo.setRecievePayAmountLocal(totalPay);
			    	//Ӧ�գ��������
			    	otherBillentryInfo.setRecievePayAmount(totalPay);
			    	//�Ƿ���Ʒ
			    	otherBillentryInfo.setIsPresent(false);
			    	//������Ŀ todo
			    	try {
						otherBillentryInfo.setExpenseItem(InfoF7Util.getExpenseTypeInfoF7FromNumber(ctx,"ZJ0001"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//������λ
					//otherBillentryInfo.setMeasureUnit(getMeasureUnitInfoF7(ctx,"1000"));
					//��������
					//otherBillentryInfo.setMaterialName("");
					//����
					//otherBillentryInfo.setMaterial(item);
					//������ 
					//otherBillentryInfo.setRowType(item);
					//otherBillentryInfo.setParent(otherBillInfo);
					//��ͷ
			    	otherBillentryInfo.setHead(otherBillInfo);
			    	otherBillInfo.getEntry().add(otherBillentryInfo);
			    	
		    		//��λ��
	    			//�Ƿ�����Ӧ�յ�
	    			int isCreatIOtherBill = 1;
	    			//����
	    			BigDecimal ratio = new BigDecimal("0");
		    		//��ȡ����
			    	sqlbuff = new StringBuffer();
					sqlbuff.append("select t1.CFSYSTEMNODETYPECODE sysNodeCode ,t1.CFSYSTEMTREATMENTTYPECODE sysTrestmentCode,");
					sqlbuff.append("t2.cfratio ratio,t4.FNUMBER easNodeCode");
					sqlbuff.append(" from CT_MAP_TreatmentNodeCoding t1");
					sqlbuff.append(" left join CT_MAP_IncomeRatio t2 on t1.CFTrestmentTypeID =t2.CFTreatmentTypeNam and t1.CFNodeTypeID = t2.CFNodeTypeNameID  and t2.CFDoctorLevelID = '"+levelId+"'");
					sqlbuff.append(" left join CT_MAP_TreatmentType t3 on t1.CFTrestmentTypeID = t3.FID");
					sqlbuff.append(" left join CT_MAP_NodeType t4 on t1.CFNodeTypeID = t4.FID where t3.FNUMBER = '001'");

					if(isSpecial.equals("1")){
						//��������  	    				     				
						//���������Ӧ������bizDate ������Ӧ������cdBizDate ��������Ӧ������bcqBizDate
						if(cdBizDate==null){
							//1.cdBizDateΪnull ������
							isCreatIOtherBill=0;
	    			     }else if(cdBizDate.getTime()>bizDate.getTime()){
	    			    	 //2.����ǰ  
	    			    	 IRowSet incomeRation = DbUtil.executeQuery(ctx, getSql(sqlbuff,cdNode,cdFeeTypeDetail));
	    					 while(incomeRation.next()){
	    						 ratio = new BigDecimal(incomeRation.getString("ratio")).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP);
	    					 }
	    				     totalPay = specialAmount.multiply(ratio).setScale(2,BigDecimal.ROUND_HALF_UP);
	    				     //�������
	    				     otherBillInfo.put("jsjc", specialAmount);
	    				     //���¸�company��ص��ֶθ�ֵ

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
	    		    		 //������֯
	    		    		 otherBillInfo.setCompany(companyInfo);
	    		    		 //����Ԫ
	    		    		 otherBillInfo.setCU(companyInfo.getCU());
	    			    	 //��˾
	    		    		 otherBillInfo.getEntry().get(0).setCompany(companyInfo.getId().toString());	    				 
	    			     }else if (cdBizDate.getTime()<=bizDate.getTime()){
	    			    	 //������ 
    				         if(bcqBizDate==null||bizDate.getTime()<bcqBizDate.getTime()){
    				        	 //3.bizDate<bcqBizDate 
    				        	 //4.bcqBizDate Ϊnull
	    						 //70%
	    				         IRowSet incomeRation = DbUtil.executeQuery(ctx, getSql(sqlbuff,cdNode,cdFeeTypeDetail));
		    					 while(incomeRation.next()){
		    						 ratio = new BigDecimal(incomeRation.getString("ratio")).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP);
		    					 }
		    					 totalPay = specialAmount.multiply(ratio).setScale(2,BigDecimal.ROUND_HALF_UP);
		    				     //�������
		    				     otherBillInfo.put("jsjc", specialAmount);
		    				     //���¸�company��ص��ֶθ�ֵ
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
		    		    		 //������֯
		    		    		 otherBillInfo.setCompany(companyInfo);
		    		    		 //����Ԫ
		    		    		 otherBillInfo.setCU(companyInfo.getCU());
		    			    	 //��˾
		    		    		 otherBillentryInfo.setCompany(companyInfo.getId().toString());	

    				         }else{
    				        	 //5.bizDate>=bcqBizDate
    				        	 //100%
	    						 totalPay = specialAmount.multiply(new BigDecimal("1").setScale(2,BigDecimal.ROUND_HALF_UP));
		    				     //�������
		    				     otherBillInfo.put("jsjc", specialAmount);
		    				     //���¸�company��ص��ֶθ�ֵ
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
		    		    		 //������֯
		    		    		 otherBillInfo.setCompany(companyInfo);
		    		    		 //����Ԫ
		    		    		 otherBillInfo.setCU(companyInfo.getCU());
		    			    	 //��˾
		    		    		 otherBillInfo.getEntry().get(0).setCompany(companyInfo.getId().toString());
    				         }	    					 
    				     }
					}else if(isSpecial.equals("0")){
						//����������
				    	//ҽ������todo
						IRowSet incomeRation = DbUtil.executeQuery(ctx, getSql(sqlbuff,node,feeTypeDetail));
						while(incomeRation.next()){
				    		easNodeTypeCode = incomeRation.getString("easNodeCode");
				    		String ratioStr = incomeRation.getString("ratio");
							ratio = new BigDecimal(incomeRation.getString("ratio")).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP);
						}
						
		    			if(easNodeTypeCode.equals("A01")){
		    				//����	
		    				totalPay = ((amountOne.add(amountTwo)).add(amountThree)).multiply(ratio).setScale(2,BigDecimal.ROUND_HALF_UP);
		   				    //�������
		   				    otherBillInfo.put("jsjc", (amountOne.add(amountTwo)).add(amountThree));
							//���ƽ׶�
							otherBillInfo.put("zljd",expenseID + ":����");
		    			}else if(easNodeTypeCode.equals("A02")){
		    				//������
							otherBillInfo.put("zljd",expenseID + ":����");		    				
		    				//��ȡ�������ܽ��
							/**
							 * 
		    				BigDecimal cdTotalPay = new BigDecimal("0");
		    		    	StringBuffer cdSqlbuff = new StringBuffer();
		    		    	cdSqlbuff.append(" select * from t_ortho where expenseID = '"+expenseID+"' and node = '"+node+"'" );	    				
		    		    	ResultSet cdrs = null;
		    				try {
		    					//�վݽ����
		    					cdrs = MysqlQueryRsUtil.getRs(ctx,conn,ps,cdSqlbuff.toString());
		    				} catch (SQLException e1) {
		    					msg = "������Ϣ����ȡ�����ʧ�ܡ�";
		    			    	logEntryInfo = new SyncLogEntryInfo();
		    					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
		    					logEntryInfo.setLoginfo(msg);
		    					logEntryInfo.setParent(logInfo);
		    					logInfo.getEntrys().add(logEntryInfo);
		    				} catch (ClassNotFoundException e1) {
		    					msg = "������Ϣ����ȡ�����ʧ�ܡ�";
		    			    	logEntryInfo = new SyncLogEntryInfo();
		    					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
		    					logEntryInfo.setLoginfo(msg);
		    					logEntryInfo.setParent(logInfo);
		    					logInfo.getEntrys().add(logEntryInfo);
		    				}
		    				while(cdrs.next()){
			    				//���ý��1
		    					BigDecimal cdAmountOne = cdrs.getBigDecimal("amountOne")==null?new BigDecimal("0"):cdrs.getBigDecimal("amountOne");
			    				//���ý��2
		    					BigDecimal cdAmountTwo = cdrs.getBigDecimal("amountTwo")==null?new BigDecimal("0"):cdrs.getBigDecimal("amountTwo");
			    				//���ý��3
		    					BigDecimal cdAmountThree = cdrs.getBigDecimal("amountThree")==null?new BigDecimal("0"):cdrs.getBigDecimal("amountThree");
		    					//�����1+2+3��*��1-�ڵ������
			    				cdTotalPay = (cdAmountOne.add(cdAmountTwo).add(cdAmountThree)).multiply((new BigDecimal("1").subtract(ratio))).setScale(2,BigDecimal.ROUND_HALF_UP);
		    				}
		    				//�ܽ��
		    				totalPay = ((amountOne.add(amountTwo)).add(amountThree)).subtract(cdTotalPay);
							*/
							//���Ļ�ȡ�����ܽ����߼�   ���1+���2+���3 - EAS�г������ܽ��
							BigDecimal EASAmount = zero;
							try {
								EASAmount = InfoF7Util.getEASAmount(ctx,expenseID + ":����");
							} catch (Exception e) {
		    					msg = "������Ϣ����ȡEAS�г����ܽ��ʧ�ܡ�";
		    			    	logEntryInfo = new SyncLogEntryInfo();
		    					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
		    					logEntryInfo.setLoginfo(msg);
		    					logEntryInfo.setParent(logInfo);
		    					logInfo.getEntrys().add(logEntryInfo);
							}
							totalPay = ((amountOne.add(amountTwo)).add(amountThree)).subtract(EASAmount);
		   				    //�������
		   				    otherBillInfo.put("jsjc", (amountOne.add(amountTwo)).add(amountThree));
		    				//���ɱ��������������Ӧ�յ�
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
					
					//�滻totalPay��Ӧ�յ����桢�ύ�����
					if(isCreatIOtherBill==1){
						//������������������ͬ����ʱȡ��������ͬ����������ͷ�����Ŀ
						if(node != null && !node.equals("")){
							if(node.startsWith("first") && chudaiAmount.compareTo(zero)!=0){
								totalPay = chudaiAmount;
								try {
									otherBillentryInfo.setExpenseItem(InfoF7Util.getExpenseTypeInfoF7(ctx,rs.getString("chudaiexpenseNo")));
									//�Ƿ�������ͬ����
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
								//�Ƿ�������ͬ����
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
		//����rs����
		
		//��������Ӧ�յ������ݸ���Ϊ�Ѵ�����isHandled=1��
		String sql = "update t_ortho set isHandled ='1' where id in("+strBizIDs.substring(0, strBizIDs.length()-1)+")";
		System.out.println("=====����sql��䣺"+sql+"");
  		try {
			MysqlQueryRsUtil.updateRs(conn, sql);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//�ر�����
		try {
			mysqlConnectionUtil.close(conn, ps, rs);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//����msg
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
     * Ӧ�յ����桢�ύ�����
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
    	
    	//�滻�滻totalPay
    	BigDecimal one = new BigDecimal(1.00); 
    	BigDecimal negativeOne = new BigDecimal("-1"); 
    	BigDecimal negativeTotalPay = new BigDecimal("0"); 
    	
   		//���Ϊ��������
    	//��¼
    	//��˰����
    	otherBillInfo.getEntry().get(0).setTaxPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_UP));
    	//ʵ�ʵ���
    	otherBillInfo.getEntry().get(0).setRealPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_UP));
    	//����
    	otherBillInfo.getEntry().get(0).setPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_UP)); 		
    	if(otherBillInfo.getEntry().get(0).getQuantity().compareTo(negativeOne)==0){
    		totalPay =  totalPay.multiply(negativeOne.setScale(2,BigDecimal.ROUND_HALF_UP));
    	}

    	//��ͷ
    	otherBillInfo.setTotalAmountLocal(totalPay);
		//��˰�ϼƣ����ý��ϼƣ�
		otherBillInfo.setTotalTaxAmount(totalPay);
		//���ϼ�
		otherBillInfo.setTotalAmount(totalPay);
		//δ���㱾λ�ҽ��
		otherBillInfo.setUnVerifyAmountLocal(totalPay);
		//δ������
		otherBillInfo.setUnVerifyAmount(totalPay);
		//Ӧ�գ�������λ��
		otherBillInfo.setAmountLocal(totalPay);
		//Ӧ�գ��������
		otherBillInfo.setAmount(totalPay);
		//�տ�ƻ�
		//Ӧ��Ӧ�����
		otherBillInfo.getRecievePlan().get(0).setRecievePayAmount(totalPay);
		//Ӧ��Ӧ����λ��
		otherBillInfo.getRecievePlan().get(0).setRecievePayAmountLocal(totalPay);
		//δ�������
		otherBillInfo.getRecievePlan().get(0).setUnLockAmount(totalPay);
		//δ������λ��
		otherBillInfo.getRecievePlan().get(0).setUnLockAmountLoc(totalPay);
		//δ������
		otherBillInfo.getRecievePlan().get(0).setUnVerifyAmount(totalPay);
		//δ�����λ��
		otherBillInfo.getRecievePlan().get(0).setUnVerifyAmountLocal(totalPay);
		//��¼
		//δ��Ʊ�����λ��
		otherBillInfo.getEntry().get(0).setUnInvoiceReqAmountLocal(totalPay);
		//δ��Ʊ������
		otherBillInfo.getEntry().get(0).setUnInvoiceReqAmount(totalPay);
		//δ������λ�ҽ��
		otherBillInfo.getEntry().get(0).setLocalUnwriteOffAmount(totalPay);
		//��λ��
		otherBillInfo.getEntry().get(0).setAmountLocal(totalPay);
    	//���
    	otherBillInfo.getEntry().get(0).setAmount(totalPay);
    	//δ������λ��
    	otherBillInfo.getEntry().get(0).setLockUnVerifyAmtLocal(totalPay);
    	//δ�������
    	otherBillInfo.getEntry().get(0).setLockUnVerifyAmt(totalPay);
    	//δ�����λ��
    	otherBillInfo.getEntry().get(0).setUnVerifyAmountLocal(totalPay);
    	//δ������
    	otherBillInfo.getEntry().get(0).setUnVerifyAmount(totalPay);
    	//Ӧ�գ�������λ��
    	otherBillInfo.getEntry().get(0).setRecievePayAmountLocal(totalPay);
    	//Ӧ�գ��������
    	otherBillInfo.getEntry().get(0).setRecievePayAmount(totalPay);
		//Ӧ�յ����桢�ύ�����
		try {
			IOtherBillUtil.creatIOtherBill(ctx, otherBillInfo, totalPay,logInfo,bizDate,beginDate);
			strBizIDs += "'"+otherBillInfo.getDescription()+"',";
		} catch (EASBizException e) {
			msg = "����Ӧ�յ�����  "+otherBillInfo.getCompany().getName()+ " ������Ϣ��"+e.getMessage()+"\n";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		} catch (BOSException e) {
			msg = "����Ӧ�յ�����  "+otherBillInfo.getCompany().getName()+ " ������Ϣ��"+e.getMessage()+"\n";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		}
		
	}
    
    
	/** 
	 * ƴ��sql
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