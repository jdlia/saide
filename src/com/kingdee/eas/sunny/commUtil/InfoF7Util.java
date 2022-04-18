package com.kingdee.eas.sunny.commUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PaymentTypeCollection;
import com.kingdee.eas.basedata.assistant.PaymentTypeFactory;
import com.kingdee.eas.basedata.assistant.PaymentTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitCollection;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.cp.bc.ExpenseTypeCollection;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.custom.sdyg.mapping.DoctorCollection;
import com.kingdee.eas.custom.sdyg.mapping.DoctorFactory;
import com.kingdee.eas.custom.sdyg.mapping.DoctorInfo;
import com.kingdee.eas.custom.sdyg.mapping.IncomeRatioCollection;
import com.kingdee.eas.custom.sdyg.mapping.IncomeRatioFactory;
import com.kingdee.eas.custom.sdyg.mapping.IncomeRatioInfo;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapCollection;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapFactory;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapInfo;
import com.kingdee.eas.custom.sdyg.mapping.TreatmentNodeCodingCollection;
import com.kingdee.eas.custom.sdyg.mapping.TreatmentNodeCodingFactory;
import com.kingdee.eas.custom.sdyg.mapping.TreatmentNodeCodingInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;


/**
 * @author chens
 *
 */
public class InfoF7Util {
	
	/**
	 * ������֯ӳ���ȡ��˾F7	CompanyOrgUnitInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	public static OrgmapInfo getOrgmapInfoF7(Context ctx, String number) throws Exception {
		OrgmapInfo orgmapInfo = new OrgmapInfo();
		try {
			OrgmapCollection orgmapCollection = OrgmapFactory.getLocalInstance(ctx).getOrgmapCollection( " where dgtno = '"+number+"'");
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
	 * �ұ�F7	CurrencyInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	public static CurrencyInfo getCurrencyInfoF7(Context ctx, String number) throws Exception {
		CurrencyInfo currencyInfo = new CurrencyInfo();
		try {
			CurrencyCollection currencyCollection = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(getViewInfo(number));
			if ((currencyCollection  != null) && (currencyCollection.size() > 0)) {
				currencyInfo = currencyCollection.get(0);
			}else {
				throw new Exception("EAS��δ�ҵ���Ӧ�ıұ����顣");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return currencyInfo;
	}
	
	/**
	 * ����������Ŀ--��������
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public static AsstActTypeInfo getAsstActTypeInfoF7(Context ctx, String number) throws Exception {
		AsstActTypeInfo asstActTypeInfo = new AsstActTypeInfo();
		try {
			AsstActTypeCollection asstActTypeCollection = AsstActTypeFactory.getLocalInstance(ctx).getAsstActTypeCollection(getViewInfo1(number));
			if ((asstActTypeCollection  != null) && (asstActTypeCollection.size() > 0)) {
				asstActTypeInfo = asstActTypeCollection.get(0);
			} else {
				throw new Exception("δ�ҵ�����������Ŀ");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return asstActTypeInfo;
	}
    
	/**
	 * �ͻ�F7		CustomerInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	public static CustomerInfo getCustomerInfoF7(Context ctx, String number) throws Exception {
		CustomerInfo customerInfo = new CustomerInfo();
		try {
			CustomerCollection customerCollection = CustomerFactory.getLocalInstance(ctx).getCustomerCollection(getViewInfo(number));
			if ((customerCollection  != null) && (customerCollection.size() > 0)) {
				customerInfo = customerCollection.get(0);
			}else {
				throw new Exception("EAS��δ�ҵ�������Ϊ"+number+"�Ŀͻ������顣");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}
	
	 /**
	   * ҽ��ӳ���ȡԱ��F7 
	   * @param ctx
	   * @param number
	   * @return
	   * @throws Exception 
	   */
	public static DoctorInfo getDoctorInfoF7(Context ctx, String number) throws Exception {
	   DoctorInfo doctorInfo = new DoctorInfo();
	   try {
	     DoctorCollection doctorCollection = DoctorFactory.getLocalInstance(ctx).getDoctorCollection( " where bizid = '"+number+"'");
	   if (!doctorCollection.isEmpty()) {
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
	   * ��ԱF7 
	   * @param ctx
	   * @param number
	   * @return
	   * @throws Exception 
	   */
	public static PersonInfo getPersonInfoF7(Context ctx, String number) throws Exception {
		PersonInfo personInfo = new PersonInfo();
	   try {
		   PersonCollection personCollection = PersonFactory.getLocalInstance(ctx).getPersonCollection( " where id = '"+number+"'");
	    if ((personCollection.size()>0) && (personCollection.get(0) != null)) {
	    	personInfo = personCollection.get(0);
	    } else {
	     throw new Exception("ҽ��ID��"+number+"δ��EAS���ҵ���Ӧ����Ա");
	    }
	   } catch (BOSException e) {
	    e.printStackTrace();
	   }
	   return personInfo;
	  }
	
	/**
	 * ������֯	SaleOrgUnitInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	public static SaleOrgUnitInfo getSaleOrgUnitInfoF7(Context ctx, String number) throws Exception {
		SaleOrgUnitInfo saleOrgUnitInfo = new SaleOrgUnitInfo();
		try {
			SaleOrgUnitCollection saleOrgUnitCollection = SaleOrgUnitFactory.getLocalInstance(ctx).getSaleOrgUnitCollection(getViewInfo1(number));
			if ((saleOrgUnitCollection  != null) && (saleOrgUnitCollection.size() > 0)) {
				saleOrgUnitInfo = saleOrgUnitCollection.get(0);
			} else {
				throw new Exception("������֯��"+number+"δ�ҵ���Ӧ��������֯");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return saleOrgUnitInfo;
	}
	
	/**
	 * ���ʽF7		CustomerInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	public static PaymentTypeInfo getPaymentTypeInfoF7(Context ctx, String name) throws Exception {
		PaymentTypeInfo paymentTypeInfo = new PaymentTypeInfo();
		try {
			PaymentTypeCollection PaymentTypeCollection = PaymentTypeFactory.getLocalInstance(ctx).getPaymentTypeCollection(getViewInfo1(name));
			if ((PaymentTypeCollection  != null) && (PaymentTypeCollection.size() > 0)) {
				paymentTypeInfo = PaymentTypeCollection.get(0);
			}else {
				throw new Exception("δ�ҵ����ʽ");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return paymentTypeInfo;
	}
	/**
	 * �û�F7		UserInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	public static UserInfo getUserInfoF7(Context ctx, String number) throws Exception {
		UserInfo userInfo = new UserInfo();
		try {
			UserCollection userCollection = UserFactory.getLocalInstance(ctx).getUserCollection(getViewInfo(number));
			if ((userCollection  != null) && (userCollection.size() > 0)) {
				userInfo = userCollection.get(0);
			}else {
				throw new Exception("EASδ�ҵ���Ӧ���û������顣");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return userInfo;
	}
	/**
	 * ������ĿF7		ExpenseTypeInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	public static ExpenseTypeInfo getExpenseTypeInfoF7(Context ctx, String id) throws Exception {
		
		ExpenseTypeInfo expenseTypeInfo = new ExpenseTypeInfo();
		String sql =" /*dialect*/select CFFYLXID fylxid from CT_MAP_Feeitem where cfbizid = '"+id+"'";
		IRowSet rowSet = DbUtil.executeQuery(ctx, sql);
		List  list = new ArrayList();
		while (rowSet.next()) {
		   list.add(rowSet.getString("fylxid"));
		}
		if((list.size()>0) && (list.get(0) != null)){
			 expenseTypeInfo =  ExpenseTypeFactory.getLocalInstance(ctx).getExpenseTypeInfo("where id = '"+list.get(0).toString()+"'");
		}else {
			throw new Exception("EAS��δ�ҵ���Ӧ�ķ�����Ŀ�����顣ҵ��ϵͳ������ĿID��"+id);
		}
//		FeeitemInfo feeitemInfo = new FeeitemInfo();
//		try {
//			FeeitemCollection feeitemCollection = FeeitemFactory.getLocalInstance(ctx).getFeeitemCollection( " where bizid = '"+id+"'");
//			if ((feeitemCollection  != null) && (feeitemCollection.size() > 0)) {
//				feeitemInfo = feeitemCollection.get(0);
//			} else {
//				throw new Exception("������Ŀ��"+id+"δ��EASӳ������ҵ���Ӧ�ķ�����Ŀ");
//			}
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
		
		return expenseTypeInfo;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param companyid    ��˾ID
	 * @param systemModel  ϵͳ���ƣ�Ӧ��ϵͳ������ϵͳ
	 * @return 
	 */
	public static Date getBeginDate(Context ctx, String companyid, int systemModel ) throws Exception{
		Date beginDate = null;
		String sql =" /*dialect*/ select per.FBEGINDATE FBEGINDATE from T_BD_SystemStatusCtrol ctr "+
					" left join t_bd_period per on ctr.FSTARTPERIODID = per.FID "+ 
					" left join T_BD_SystemStatus sta on ctr.FSYSTEMSTATUSID = sta.FID "+
					" where ctr.FCOMPANYID = '"+companyid+"'";
		//Ӧ��ϵͳ 12 
		if(systemModel == 12){
			sql += " and sta.FID ='e45c1988-00fd-1000-e000-35d8c0a8200d02A5514C'";
		}
		IRowSet rowSet = DbUtil.executeQuery(ctx, sql);
		while (rowSet.next()) {
			beginDate = rowSet.getDate("FBEGINDATE");
		}
		if(beginDate == null ){
			throw new Exception("EAS�й�˾��Ӧ�������ڼ�δ���á�");
		}
		return beginDate;
	}
	
	public static BigDecimal getEASAmount(Context ctx,String expenseIdAndNode) throws Exception{
		BigDecimal EASAmount = new BigDecimal(0.00);;
		String sql = " /*dialect*/ select nvl(sum(FAMOUNT),0) amount FROM T_AR_OtherBill where cfzljd ='"+expenseIdAndNode+"' ";
		IRowSet rowSet = DbUtil.executeQuery(ctx, sql);
		while (rowSet.next()) {
			EASAmount = rowSet.getBigDecimal("amount");
		}
		return EASAmount;
		
	}
	 /**
	  * ������ĿF7  ExpenseTypeInfo
	  * @param ctx
	  * @param number
	  * @return
	  * @throws Exception 
	  */
	public static ExpenseTypeInfo getExpenseTypeInfoF7FromNumber(Context ctx, String number) throws Exception {
	  ExpenseTypeInfo expenseTypeInfo = new ExpenseTypeInfo();
	  try {
	   ExpenseTypeCollection expenseTypeCollection = ExpenseTypeFactory.getLocalInstance(ctx).getExpenseTypeCollection(getViewInfo(number));
	   if ((expenseTypeCollection  != null) && (expenseTypeCollection.size() > 0)) {
	    expenseTypeInfo = expenseTypeCollection.get(0);
	   }else {
	    throw new Exception("������Ŀδ�ҵ�");
	   }
	  } catch (BOSException e) {
	   e.printStackTrace();
	  }
	  return expenseTypeInfo;
	 }
	public static IRowSet getIncomeRatioInfoInfoF7(Context ctx) throws Exception {
		  
		  IncomeRatioInfo incomeRatioInfo = new IncomeRatioInfo();
		  StringBuffer sqlbuff = new StringBuffer();
	      sqlbuff.append("select t1.CFSYSTEMNODETYPECODE sysNodeCode ,t1.CFSYSTEMTREATMENTTYPECODE,");
	      sqlbuff.append("t3.FNUMBER easTreatmentCode,t4.FNUMBER easNodeCode");
	      sqlbuff.append(" from CT_MAP_TreatmentNodeCoding t1");
	      sqlbuff.append(" left join CT_MAP_IncomeRatio t2 on t1.CFRATIOID =t2.FID ");
	      sqlbuff.append(" left join CT_MAP_TreatmentType t3 on t2.CFTREATMENTTYPENAM = t3.FID");
	      sqlbuff.append(" left join CT_MAP_NodeType t4 on t2.CFNODETYPENAMEID = t4.FID");
		  IRowSet rowSet = DbUtil.executeQuery(ctx, sqlbuff.toString());
//		  List  list = new ArrayList();
//		  while (rowSet.next()) {
//		     list.add(rowSet.getString("fylxid"));
//		  }
//		  if((list.size()>0) && (list.get(0) != null)){
//		    incomeRatioInfo =  IncomeRatioFactory.getLocalInstance(ctx).getIncomeRatioInfo("where id = '"+list.get(0).toString()+"'");
//		  }else {
//		   throw new Exception("EAS��δ�ҵ���Ӧ�ķ�����Ŀ�����顣");
//		  }
		  return rowSet;
		 }
	/**
	 * �������������ͺͽڵ� �����ƽڵ����ӳ�����ȡ����F7	IncomeRatioInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	public static TreatmentNodeCodingCollection getTreatmentNodeCodingInfoF7(Context ctx, String feeTypeDetail,String node) throws Exception {
//		TreatmentNodeCodingInfo treatmentNodeCodingInfo = new TreatmentNodeCodingInfo();
		TreatmentNodeCodingCollection treatmentNodeCodingCollection  = new TreatmentNodeCodingCollection();
//		TreatmentNodeCodingInfo treatmentNodeCodingInfo = treatmentNodeCodingCollection.get(0);
		try {
			if(feeTypeDetail.equals("")&&node.equals("")){
				 treatmentNodeCodingCollection = TreatmentNodeCodingFactory.getLocalInstance(ctx).getTreatmentNodeCodingCollection();
			}else{
				 treatmentNodeCodingCollection = TreatmentNodeCodingFactory.getLocalInstance(ctx).getTreatmentNodeCodingCollection( " where systemTreatmentTypeCode = '"+feeTypeDetail+"'and systemNodeTypeCode = '"+node+"'");

			}
			if ((treatmentNodeCodingCollection  != null) && (treatmentNodeCodingCollection.size() > 0)) {
//				treatmentNodeCodingInfo = treatmentNodeCodingCollection.get(0);
			} else {
				throw new Exception("���������ͱ��룺"+feeTypeDetail+"�ͽڵ���룺"+node+"δ��EAS���ҵ���Ӧ�ı���");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return treatmentNodeCodingCollection;
	}
	
	protected static EntityViewInfo getViewInfo(String number) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		evi.setFilter(filter);
		return evi;
	}
	
	protected static EntityViewInfo getViewInfo1(String name) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", name, CompareType.EQUALS));
		evi.setFilter(filter);
		return evi;
	}
	

}
