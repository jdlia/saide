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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapInfo;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogEntryInfo;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogFactory;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogInfo;
import com.kingdee.eas.custom.sdyg.mapping.TreatmentNodeCodingCollection;
import com.kingdee.eas.custom.sdyg.mapping.TreatmentNodeCodingInfo;
import com.kingdee.eas.fi.ar.BillStatusEnum;
import com.kingdee.eas.fi.ar.OtherBillInfo;
import com.kingdee.eas.fi.ar.OtherBillPlanInfo;
import com.kingdee.eas.fi.ar.OtherBillTypeEnum;
import com.kingdee.eas.fi.ar.OtherBillentryInfo;
import com.kingdee.eas.fi.ar.verifyStatusEnum;
import com.kingdee.eas.fi.arap.BillRelationOptionEnum;
import com.kingdee.eas.sunny.commUtil.IOtherBillUtil;
import com.kingdee.eas.sunny.commUtil.InfoF7Util;
import com.kingdee.eas.sunny.commUtil.MysqlQueryRsUtil;
import com.kingdee.eas.sunny.commUtil.mysqlConnectionUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
/** 
 * 
 * @author chens 
 * ��ֲ�Ĵ�����ʱע�͵�-2021.04.02
 *
 */
public class InitPlanInfoFacadeControllerBean extends AbstractInitPlanInfoFacadeControllerBean
{
   /* private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.sdyg.InitPlanInfoFacadeControllerBean");
    
    @Override
    protected void _InitPlanInfoFacade(Context ctx) throws BOSException,
	EASBizException {
    	
    	System.out.println("����InitPlanInfoFacadeControllerBean");
	   	//0.��ѯ���ƽڵ����ӳ���   cd-����ֲ�� bcq-����
		String cdnodes = "";
		String bcqnodes = "";
		String sysNodeCode = "";
		String sysTrestmentCode = "";
		String easNodeTypeCode ="";
		String easTreatmentTypeCode = "";
		String cdFeeTypeDetails = "";
		String bcqFeeTypeDetails = "";
		TreatmentNodeCodingCollection treatmentNodeCodingCollection = new TreatmentNodeCodingCollection();
		TreatmentNodeCodingInfo treatmentNodeCodingInfo = new TreatmentNodeCodingInfo();
		try {
		     StringBuffer sqlbuff = new StringBuffer();
		      sqlbuff.append("select t1.CFSYSTEMNODETYPECODE sysNodeCode ,t1.CFSYSTEMTREATMENTTYPECODE sysTrestmentCode,");
		      sqlbuff.append("t2.cfratio ratio,t3.FNUMBER easTreatmentCode,t4.FNUMBER easNodeCode");
		      sqlbuff.append(" from CT_MAP_TreatmentNodeCoding t1");
		      sqlbuff.append(" left join CT_MAP_IncomeRatio t2 on t1.CFRATIOID =t2.FID ");
		      sqlbuff.append(" left join CT_MAP_TreatmentType t3 on t2.CFTREATMENTTYPENAM = t3.FID");
		      sqlbuff.append(" left join CT_MAP_NodeType t4 on t2.CFNODETYPENAMEID = t4.FID where t3.FNUMBER = '002'");
	//		IRowSet incomeRation = InfoF7Util.getIncomeRatioInfoInfoF7(ctx);	    	
			IRowSet incomeRation = DbUtil.executeQuery(ctx, sqlbuff.toString());   	
	//    	for (int j=0;j<incomeRation.size();j++){
			while(incomeRation.next()){
	
	    		easNodeTypeCode = incomeRation.getString("easNodeCode");
	    		easTreatmentTypeCode = incomeRation.getString("easTreatmentCode");
	    		sysNodeCode = incomeRation.getString("sysNodeCode");
	    		sysTrestmentCode = incomeRation.getString("sysTrestmentCode");
	    		if(easTreatmentTypeCode.equals("002")&&easNodeTypeCode.equals("B01")){
	    			//����="��ֲ"&&�ڵ�="����ֲ��"
	    			cdnodes = cdnodes+"'"+sysNodeCode+"',";
	    			cdFeeTypeDetails = cdFeeTypeDetails+"'"+sysTrestmentCode+"',"; 
	    		}else if(easTreatmentTypeCode.equals("002")&&easNodeTypeCode.equals("B02")){
	    			//����="��ֲ"&&�ڵ�="����"
	    			bcqnodes = bcqnodes+"'"+sysNodeCode+"',";
	    			bcqFeeTypeDetails = bcqFeeTypeDetails+"'"+sysTrestmentCode+"',"; 
	
	    		}
	    	}
	    	cdnodes = cdnodes.substring(0,cdnodes.length()-1);
	    	bcqnodes = bcqnodes.substring(0,bcqnodes.length()-1);
	    	cdFeeTypeDetails = cdFeeTypeDetails.substring(0,cdFeeTypeDetails.length()-1);
	    	bcqFeeTypeDetails = bcqFeeTypeDetails.substring(0,bcqFeeTypeDetails.length()-1);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}    		
	
	
	
		//1.��ѯ��ֲ�м������
		StringBuffer sqlbuff = new StringBuffer();
		sqlbuff.append("select t.* ,t1.bizDate cdBizDate,t2.bizDate bcqBizDate,t1.node cdNode,t2.node bcqNode,t1.feeTypeDetail cdfeeTypeDetail,t2.feeTypeDetail bcqfeeTypeDetail,");
		sqlbuff.append("t3.specialAmount jmjSpecialAmount ,t3.bizDate jmjBizDate,t1.company cdCompany ,t2.company bcqCompany from t_raise t");        
		sqlbuff.append(" left join ( select * from t_raise  where isSpecial != '1' and feeTypeDetail in("+cdFeeTypeDetails+") and node in ("+cdnodes+") and feeType = '��ֲ' and company <> '' ) t1  on t.expenseID = t1.expenseID and t.toothPosition = t1.toothPosition");
	    sqlbuff.append(" left join ( select * from t_raise  where isSpecial != '1' and feeTypeDetail in("+bcqFeeTypeDetails+") and node in ("+bcqnodes+") and feeType = '��ֲ' and company <> '' ) t2 on t.expenseID = t2.expenseID and t.toothPosition = t2.toothPosition");
	    sqlbuff.append(" left join ( select * from t_raise  where isSpecial = '1' and feeType = '��ֲ') t3  on t.expenseID = t3.expenseID and t.toothPosition = t3.toothPosition");
	    sqlbuff.append(" where t.isHandled is null and t.feeType = '��ֲ' " );
	    sqlbuff.append(" and (t.company <> '' OR t.isSpecial = '1')");
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
		
		//2.��ȡ����ֲ���м������
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
		
		//3.�������������װ��Ӧ�յ�
		try {
			while(rs.next()){
				
				//3.1ȡ����ֲ�м�������ֶ�
				//����id
				//ҵ������
				Date bizDate = rs.getTimestamp("bizDate");
				DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String billbizdateStr = "";
				if(bizDate != null){
					billbizdateStr = sdf.format(bizDate);
				}	
				int year = Integer.parseInt(billbizdateStr.substring(0,4));
				int month = Integer.parseInt(billbizdateStr.substring(5,7));
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
				String node = rs.getString("node");
				//ҽ��
				String docNo = rs.getString("docNo");
				//���ý��1
				BigDecimal amountOne = rs.getBigDecimal("amountOne")==null?new BigDecimal("0"):rs.getBigDecimal("amountOne");
				//���ý��2
				BigDecimal amountTwo = rs.getBigDecimal("amountTwo")==null?new BigDecimal("0"):rs.getBigDecimal("amountTwo");
				//���ý��3
				BigDecimal amountThree = rs.getBigDecimal("amountThree")==null?new BigDecimal("0"):rs.getBigDecimal("amountThree");
				//���������
				BigDecimal specialAmount = rs.getBigDecimal("specialAmount")==null?new BigDecimal("0"):rs.getBigDecimal("specialAmount");
				//�Ƿ��������
				String isSpecial = rs.getString("isSpecial");
				//�ܽ��
				BigDecimal totalPay = new BigDecimal("0");
				//��ֲҵ������
				Date cdBizDate = rs.getTimestamp("cdBizDate");
				//�ڵ�
				String cdNode = rs.getString("cdNode");
				//��ֲ����������
				String cdFeeTypeDetail = rs.getString("cdFeeTypeDetail");
				//����ҵ������
				Date bcqBizDate = rs.getTimestamp("bcqBizDate");
				String billBcqBizDateStr = "";
				if(bcqBizDate != null){
					billBcqBizDateStr = sdf.format(bcqBizDate);
				}	
				//���ڽڵ�
				String bcqNode = rs.getString("bcqNode");
				//��������������
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
					orgmapInfo = InfoF7Util.getOrgmapInfoF7(ctx, company);
					companyInfo =  orgmapInfo.getOrg();
					//����Ԫ
		    		otherBillInfo.setCU(companyInfo.getCU());
				}
				//������֯
	    		otherBillInfo.setCompany(companyInfo);
	    		//ҵ������
	    		otherBillInfo.setBizDate(bizDate);
	    		//�ұ�
	    		otherBillInfo.setCurrency(InfoF7Util.getCurrencyInfoF7(ctx,"BB01"));
	    		//��������
	    		otherBillInfo.setBillDate(bizDate);
	    		//����
	    		otherBillInfo.setExchangeRate(exchangeRate);
	    		//��������
	    		otherBillInfo.setBillType(OtherBillTypeEnum.getEnum(OtherBillTypeEnum.EXPENSEINVOICE_VALUE));
	    		//��������
	    		otherBillInfo.setAsstActType(InfoF7Util.getAsstActTypeInfoF7(ctx,"�ͻ�"));
	    		//�ͻ�
	    		CustomerInfo customer = InfoF7Util.getCustomerInfoF7(ctx,account);
	    		//������ID
	    		otherBillInfo.setAsstActID(customer.getId().toString());
	    		//����������
	    		otherBillInfo.setAsstActName(customer.getName());
	    		//����������
	    		otherBillInfo.setAsstActNumber(customer.getNumber());
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
	//    		otherBillInfo.setSaleOrg(InfoF7Util.getSaleOrgUnitInfoF7(ctx,company));
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
	    		otherBillInfo.setPaymentType(InfoF7Util.getPaymentTypeInfoF7(ctx,"����"));
	    		//��Աtodo �ݷ�
	//    		otherBillInfo.setPerson(InfoF7Util.getDoctorInfoF7(ctx,docNo).getPerson());
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
	    		otherBillInfo.setLastUpdateUser(InfoF7Util.getUserInfoF7(ctx,"user"));
	    		//����ʱ��
	    		otherBillInfo.setCreateTime(new Timestamp(bizDate.getTime()));
	    		//������
	    		otherBillInfo.setCreator(InfoF7Util.getUserInfoF7(ctx,"user"));	    		
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
		    	CustomerInfo customerInfo = InfoF7Util.getCustomerInfoF7(ctx,account);
		    	//�Ƿ���ȫ����
		    	otherBillentryInfo.setIsFullWriteOff(false);
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
		    	//�տ���������
		    	otherBillentryInfo.setRecAsstActType(InfoF7Util.getAsstActTypeInfoF7(ctx,"�ͻ�"));
		    	//�ͻ��ͻ�
		    	otherBillentryInfo.setServiceCustomer(customerInfo);
		    	//�����ͻ�
		    	otherBillentryInfo.setOrderCustomer(customerInfo);
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
		    	//������Ŀ o
		    	otherBillentryInfo.setExpenseItem(InfoF7Util.getExpenseTypeInfoF7FromNumber(ctx,"ZJ0001"));
		    	//������λ
	//	    	otherBillentryInfo.setMeasureUnit(getMeasureUnitInfoF7(ctx,"1000"));
		    	//��������
	//	    	otherBillentryInfo.setMaterialName("");
		    	//����
	//	    	otherBillentryInfo.setMaterial(item);
		    	//������ 
	//	    	otherBillentryInfo.setRowType(item);
	//	    	otherBillentryInfo.setParent(otherBillInfo);
		    	//��ͷ
		    	otherBillentryInfo.setHead(otherBillInfo);
		    	otherBillInfo.getEntry().add(otherBillentryInfo);
		    	
	    		//��λ��
	    		//��ȡ�������ͺͽڵ��Ӧ�ı�����eas���������ͱ����eas�Ľڵ����ͱ���
			   sqlbuff = new StringBuffer();
			      sqlbuff.append("select t1.CFSYSTEMNODETYPECODE sysNodeCode ,t1.CFSYSTEMTREATMENTTYPECODE sysTrestmentCode,");
			      sqlbuff.append("t2.cfratio ratio,t3.FNUMBER easTreatmentCode,t4.FNUMBER easNodeCode");
			      sqlbuff.append(" from CT_MAP_TreatmentNodeCoding t1");
			      sqlbuff.append(" left join CT_MAP_IncomeRatio t2 on t1.CFRATIOID =t2.FID ");
			      sqlbuff.append(" left join CT_MAP_TreatmentType t3 on t2.CFTREATMENTTYPENAM = t3.FID");
			      sqlbuff.append(" left join CT_MAP_NodeType t4 on t2.CFNODETYPENAMEID = t4.FID where t3.FNUMBER = '002'");  
				IRowSet incomeRation = DbUtil.executeQuery(ctx, getSql(sqlbuff,node,feeTypeDetail));
		    	BigDecimal ratio = new BigDecimal("0");
	//	    	IRowSet incomeRation = MysqlQueryRsUtil.getIncomeRation(ctx, bcqFeeTypeDetail, bcqNode);
				while(incomeRation.next()){
	
		    		easNodeTypeCode = incomeRation.getString("easNodeCode");
		    		easTreatmentTypeCode = incomeRation.getString("easTreatmentCode");
		    		String ratioStr = incomeRation.getString("ratio");
					ratio = new BigDecimal(incomeRation.getString("ratio")).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_DOWN);
				}
	
    			//�Ƿ�����Ӧ�յ�
    			int isCreatIOtherBill = 1;
	    		if(easTreatmentTypeCode.equals("002")){
	    			 if(isSpecial.equals("1")){	    				 
	    				 //�������� 
		    			 //���������Ӧ������bizDate ������Ӧ������cdBizDate ���ڶ�Ӧ������bcqBizDate
	    			     if(cdBizDate==null){
	    			    	//1.cdBizDateΪnull ������
	    			    	 isCreatIOtherBill=0;
	    			     }else if(cdBizDate.getTime()>bizDate.getTime()){ 
		    				 //1.����ֲ��ǰ bizDate>cdBizDate
	    					 incomeRation = DbUtil.executeQuery(ctx, getSql(sqlbuff,cdNode,cdFeeTypeDetail));
	    					 while(incomeRation.next()){
	    						 ratio = new BigDecimal(incomeRation.getString("ratio")).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_DOWN);
	    					 }
	    				     totalPay = specialAmount.multiply(ratio.setScale(2,BigDecimal.ROUND_HALF_UP));
	    				     
	    				     //���¸�company��ص��ֶθ�ֵ
		    				 orgmapInfo = InfoF7Util.getOrgmapInfoF7(ctx, cdCompany);
		    				 companyInfo =  orgmapInfo.getOrg();
	    		    		 //������֯
	    		    		 otherBillInfo.setCompany(companyInfo);
	    		    		 //����Ԫ
	    		    		 otherBillInfo.setCU(companyInfo.getCU());
	    			    	 //��˾
	    		    		 otherBillInfo.getEntry().get(0).setCompany(companyInfo.getId().toString());	    				 
	    			    }else if (cdBizDate.getTime()<=bizDate.getTime()){
	    					//����ֲ��� 
	    					 if(bizDate.getTime()<bcqBizDate.getTime()||bizDate==null){
	    						//3.bizDate<bcqBizDate 
	    						//4.bcqBizDate Ϊnull
	    						 //70%
		    					 incomeRation = DbUtil.executeQuery(ctx, getSql(sqlbuff,cdNode,cdFeeTypeDetail));
		    					 while(incomeRation.next()){
		    						 ratio = new BigDecimal(incomeRation.getString("ratio")).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_DOWN);
		    					 }
		    				     totalPay = specialAmount.multiply(ratio.setScale(2,BigDecimal.ROUND_HALF_UP));
		    				     
		    				     //���¸�company��ص��ֶθ�ֵ
			    				 orgmapInfo = InfoF7Util.getOrgmapInfoF7(ctx, cdCompany);
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
	    						 
		    				     //���¸�company��ص��ֶθ�ֵ
			    				 orgmapInfo = InfoF7Util.getOrgmapInfoF7(ctx, bcqCompany);
			    				 companyInfo =  orgmapInfo.getOrg();
		    		    		 //������֯
		    		    		 otherBillInfo.setCompany(companyInfo);
		    		    		 //����Ԫ
		    		    		 otherBillInfo.setCU(companyInfo.getCU());
		    			    	 //��˾
		    		    		 otherBillInfo.getEntry().get(0).setCompany(companyInfo.getId().toString());	
	    					 }	    					 
	    				 }
		    		}else if(isSpecial.equals("0") && easNodeTypeCode.equals("B01")){
	    				//����ֲ��
	    				totalPay = ((amountOne.add(amountTwo)).add(amountThree)).multiply(ratio.setScale(2,BigDecimal.ROUND_HALF_UP));
	    			}else if(isSpecial.equals("0") && easNodeTypeCode.equals("B02")){
	    				//����
	    				
	    				//��ȡ����ֲ����ܽ��
	    				BigDecimal cdTotalPay = new BigDecimal("0");
	    		    	StringBuffer cdSqlbuff = new StringBuffer();
	    		    	cdSqlbuff.append(" select * from t_raise where expenseID = '"+expenseID+"' and node = '"+node+"'" );	    				
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
		    				cdTotalPay = (cdAmountOne.add(cdAmountTwo).add(cdAmountThree)).multiply((new BigDecimal("1").subtract(ratio)).setScale(2,BigDecimal.ROUND_HALF_UP));
	    				}
	    				//�ܽ��
	    				totalPay = ((amountOne.add(amountTwo)).add(amountThree)).subtract(cdTotalPay);
	    				//���ɴ������������Ӧ�յ�
	    				if(jmjBizDate.getTime()<bizDate.getTime()){	    					 
	    					 incomeRation = DbUtil.executeQuery(ctx, getSql(sqlbuff,bcqNode,bcqFeeTypeDetail));
	    					 while(incomeRation.next()){
	    						 ratio = new BigDecimal(incomeRation.getString("ratio")).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_DOWN);
	    					 }
	    					 BigDecimal jmjTotalPay = jmjSpecialAmount.multiply(ratio.setScale(2,BigDecimal.ROUND_HALF_UP));
	    					OtherBillInfo jmjOtherBillInfo =(OtherBillInfo)otherBillInfo.clone();
	    					jmjOtherBillInfo.getEntry().get(0).setQuantity(negativeOne);
	    					jmjOtherBillInfo.setId(BOSUuid.create(jmjOtherBillInfo.getBOSType()));
	    					
	    					creatIOtherBill(ctx,jmjOtherBillInfo,jmjTotalPay,logInfo);
	    				}
	    		}
	    	}
	    		if(isCreatIOtherBill==1){
				//4.�滻totalPay����Ӧ�յ����桢�ύ�����
	    		creatIOtherBill(ctx,otherBillInfo,totalPay,logInfo  ); 
	    		}
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
		} catch (NumberFormatException e1){
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public void creatIOtherBill(Context ctx,OtherBillInfo otherBillInfo , BigDecimal totalPay,SyncLogInfo logInfo ){
    	//�滻�滻totalPay
    	BigDecimal one = new BigDecimal(1.00); 
    	BigDecimal negativeOne = new BigDecimal("-1"); 
    	BigDecimal negativeTotalPay = new BigDecimal("0"); 
    	
   		//���Ϊ��������
//��¼
    	//��˰����
    	otherBillInfo.getEntry().get(0).setTaxPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_DOWN));
    	//ʵ�ʵ���
    	otherBillInfo.getEntry().get(0).setRealPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_DOWN));
    	//����
    	otherBillInfo.getEntry().get(0).setPrice(totalPay.divide(one,2,BigDecimal.ROUND_HALF_DOWN)); 		
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
		IOtherBillUtil.creatIOtherBill(ctx, otherBillInfo, totalPay,logInfo); 
		
	}
	
	public String getSql (StringBuffer strBuff,String node ,String feeTypeDetail){
	    if(feeTypeDetail!=null&&!feeTypeDetail.equals("")){
	    	strBuff.append(" and t1.CFSYSTEMTREATMENTTYPECODE = '"+feeTypeDetail+"'");
	    }
	    if(node!=null&&!node.equals("")){
	    	strBuff.append(" and t1.CFSYSTEMNODETYPECODE = '"+node+"'");
	    }
		return strBuff.toString();
		
	}
		*/
						
	
	
	
	
	
	}