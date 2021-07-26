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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.bot.BOTMappingCollection;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.DefineSysEnum;
import com.kingdee.bos.metadata.bot.app.BOTMappingHelper;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;
import com.kingdee.eas.base.btp.IBTPManager;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.assistant.MeasureUnitCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.assistant.PaymentTypeCollection;
import com.kingdee.eas.basedata.assistant.PaymentTypeFactory;
import com.kingdee.eas.basedata.assistant.PaymentTypeInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeCollection;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerGroupDetail;
import com.kingdee.eas.basedata.master.cssp.CustomerGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerKindEnum;
import com.kingdee.eas.basedata.master.cssp.PayInvoiceTypeEnum;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitCollection;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitCollection;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.ExpenseTypeCollection;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.custom.sdyg.mapping.FeeitemCollection;
import com.kingdee.eas.custom.sdyg.mapping.FeeitemFactory;
import com.kingdee.eas.custom.sdyg.mapping.FeeitemInfo;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapCollection;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapEntry;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapEntryInfo;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapFactory;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapInfo;
import com.kingdee.eas.custom.sdyg.mapping.OthCustomerCollection;
import com.kingdee.eas.custom.sdyg.mapping.OthCustomerFactory;
import com.kingdee.eas.custom.sdyg.mapping.OthCustomerInfo;
import com.kingdee.eas.custom.sdyg.sdenum.PayTypeEnum;
import com.kingdee.eas.fi.ap.OtherBill;
import com.kingdee.eas.fi.ap.OtherBillType;
import com.kingdee.eas.fi.ap.OtherBillentry;
import com.kingdee.eas.fi.ar.BillStatusEnum;
import com.kingdee.eas.fi.ar.IOtherBill;
import com.kingdee.eas.fi.ar.OtherBillFactory;
import com.kingdee.eas.fi.ar.OtherBillInfo;
import com.kingdee.eas.fi.ar.OtherBillPlanInfo;
import com.kingdee.eas.fi.ar.OtherBillTypeEnum;
import com.kingdee.eas.fi.ar.OtherBillentryInfo;
import com.kingdee.eas.fi.ar.verifyStatusEnum;
import com.kingdee.eas.fi.arap.BillRelationOptionEnum;
import com.kingdee.eas.fi.arap.PriceSourceEnum;
import com.kingdee.eas.fi.cas.BizTypeEnum;
import com.kingdee.eas.fi.cas.CasRecPayBillTypeEnum;
import com.kingdee.eas.fi.cas.IReceivingBill;
import com.kingdee.eas.fi.cas.ReceivingBill;
import com.kingdee.eas.fi.cas.ReceivingBillEntry;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.ReceivingBillTypeCollection;
import com.kingdee.eas.fi.cas.ReceivingBillTypeFactory;
import com.kingdee.eas.fi.cas.ReceivingBillTypeInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.fm.rpm.BillClaimStatusEnum;
import com.kingdee.eas.fm.ss.SmartType;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.sunny.SyncBizSystemFacadeFactory;
import com.kingdee.eas.sunny.commUtil.mysqlConnectionUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;


public class InitBillInfoFacadeControllerBean extends AbstractInitBillInfoFacadeControllerBean
{
    private static Logger logger = Logger.getLogger("com.kingdee.eas.custom.sdyg.InitBillInfoFacadeControllerBean");
    protected HashMap yingshouMap = new HashMap();
    protected HashMap shoukuanMap = new HashMap();
    protected HashMap yingfuMap = new HashMap();
    protected HashMap CompanyOrgUnitMap = new HashMap();
    protected HashMap CurrencyMap = new HashMap();
    protected ICompanyOrgUnit iCompanyOrgUnit;
    protected ICurrency iCurrency;
    public static int QueryByNumber = 0;  		//ͨ�������F7
    public static int QueryByName = 1;			//ͨ�����Ʋ�F7
    public static int QueryByNumberOrName = 2;	//ͨ����������Ʋ�F7
    
    @Override
    protected void _syncOrg(Context ctx) throws BOSException {
		// ��ѯ����
		String sql = "select * from t_org org  left join t_baseData baseData  on org.ID = baseData.ID  where baseData.kdGetMark <> '1' or baseData.kdGetMark is null ";
		System.out.println(sql.toString());
		mysqlConnectionUtil util = new mysqlConnectionUtil();
		Connection connection = null;
		try {
			connection = util.createConn();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = pStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// ��ѯ�Ƿ����ӳ���,��������������һ�ţ������ڻ�ȡ����
//		OrgmapCollection col = OrgmapFactory.getLocalInstance(ctx).getOrgmapCollection();
//		if (col != null && col.size() != 0) {
//			info = col.get(0);
//		} else {
//			info = new OrgmapInfo();
//			info.setId(BOSUuid.create(info.getBOSType()));
//			OrgmapFactory.getLocalInstance(ctx).addnew(info);
//		}
		// ѭ����ѯ���������¼
		StringBuffer strIDbuff = new StringBuffer();
		try {
			while (resultSet.next()) {
				String bizID = resultSet.getString("ID");
				OrgmapCollection col = OrgmapFactory.getLocalInstance(ctx).getOrgmapCollection(" where bizID ='"+bizID+"'");
				if (col != null && col.size() != 0) {
					continue;
				}
				strIDbuff.append("'");
				strIDbuff.append(bizID);
				strIDbuff.append("',");
				OrgmapInfo info = new OrgmapInfo();
				info.setBizID(bizID);//ҵ��ϵͳ����
				info.setNumber(resultSet.getString("dgtNo"));
				info.setDgtNo(resultSet.getString("dgtNo"));//���ֱ���
				info.setAlphNo(resultSet.getString("alphNo"));//ҵ��ϵͳ��ĸ����
				info.setCompanyName(resultSet.getString("companyName"));//ҵ��ϵͳ��������
				info.setCreateTime(new Timestamp(new Date().getTime()));//����ʱ��
				info.setId(BOSUuid.create(info.getBOSType()));
				
				OrgmapEntryInfo entryInfo = new OrgmapEntryInfo();
				entryInfo.setId(BOSUuid.create(info.getBOSType()));
				entryInfo.setParent(info);			
				info.getEntrys().add(entryInfo);
				
				OrgmapFactory.getLocalInstance(ctx).addnew(info);
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(strIDbuff.toString().length() > 1){
			String strID = strIDbuff.toString().substring(0, strIDbuff.length()-1);
			String sql1 = "update t_baseData set kdGetMark = '1' where ID in ("+strID+")";
			PreparedStatement pStatement1 = null;
			try {
				pStatement1 = connection.prepareStatement(sql1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pStatement1.executeUpdate(sql1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pStatement1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    @Override
    protected void _syncFeeitem(Context ctx) throws BOSException {
		// ��ѯ����
		String sql = "select fee.* from t_feeItem fee  left join t_baseData baseData  on fee.ID = baseData.ID  where baseData.kdGetMark <> '1' or baseData.kdGetMark is null ";
		mysqlConnectionUtil util = new mysqlConnectionUtil();
		Connection connection = null;
		try {
			connection = util.createConn();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = pStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ѭ����ѯ���
		StringBuffer strIDbuff = new StringBuffer();
		try {
			while (resultSet.next()) {
				String bizID = resultSet.getString("ID");
				FeeitemCollection col = FeeitemFactory.getLocalInstance(ctx).getFeeitemCollection(" where bizID ='"+bizID+"'");
				if (col != null && col.size() != 0) {
					continue;
				}
				strIDbuff.append("'");
				strIDbuff.append(bizID);
				strIDbuff.append("',");
				FeeitemInfo feeInfo = new FeeitemInfo();
				feeInfo.setBizID(bizID);//ҵ��ϵͳ����
				feeInfo.setItemName(resultSet.getString("itemName"));
				feeInfo.setItemNo(resultSet.getString("itemNo"));
				feeInfo.setUseCompany(resultSet.getString("useCompany"));
				feeInfo.setCreateTime(new Timestamp(new Date().getTime()));
				FeeitemFactory.getLocalInstance(ctx).addnew(feeInfo);
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(strIDbuff.toString().length() > 1){
			String strID = strIDbuff.toString().substring(0, strIDbuff.length()-1);
			String sql1 = "update t_baseData set kdGetMark = '1' where ID in ("+strID+")";
			PreparedStatement pStatement1 = null;
			try {
				pStatement1 = connection.prepareStatement(sql1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pStatement1.executeUpdate(sql1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pStatement1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    }
    
    @Override
    protected void _syncOthcustomer(Context ctx) throws BOSException {
		// ��ѯ����
		String sql = "select oth.* from t_othcustomer oth  left join t_baseData baseData  on oth.ID = baseData.ID  where baseData.kdGetMark <> '1' or baseData.kdGetMark is null  ";
		mysqlConnectionUtil util = new mysqlConnectionUtil();
		Connection connection = null;
		try {
			connection = util.createConn();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = pStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ѭ����ѯ���
		StringBuffer strIDbuff = new StringBuffer();
		try {
			while (resultSet.next()) {
				String bizID = resultSet.getString("ID");
				OthCustomerCollection col = OthCustomerFactory.getLocalInstance(ctx).getOthCustomerCollection(" where bizID ='"+bizID+"'");
				if (col != null && col.size() != 0) {
					continue;
				}
				strIDbuff.append("'");
				strIDbuff.append(bizID);
				strIDbuff.append("',");
				OthCustomerInfo othCustomerInfo = new OthCustomerInfo();
				othCustomerInfo.setBizID(bizID);//ҵ��ϵͳ����
				othCustomerInfo.setBizNumber(resultSet.getString("number"));
				othCustomerInfo.setBizName(resultSet.getString("name"));
				othCustomerInfo.setBizType(resultSet.getString("type"));
				othCustomerInfo.setBizTypeName(resultSet.getString("bizType"));
				othCustomerInfo.setBizDirection(resultSet.getString("direction"));
				othCustomerInfo.setCreateTime(new Timestamp(new Date().getTime()));
				OthCustomerFactory.getLocalInstance(ctx).addnew(othCustomerInfo);
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(strIDbuff.toString().length() > 1){
			String strID = strIDbuff.toString().substring(0, strIDbuff.length()-1);
			String sql1 = "update t_baseData set kdGetMark = '1' where ID in ("+strID+")";
			PreparedStatement pStatement1 = null;
			try {
				pStatement1 = connection.prepareStatement(sql1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pStatement1.executeUpdate(sql1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pStatement1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    @Override
    protected void _syncCustomer(Context ctx) throws BOSException {
		// ��ѯ����
		String sql = "select cus.* from t_customer cus  left join t_baseData baseData  on cus.ID = baseData.ID  where baseData.kdGetMark <> '1' or baseData.kdGetMark is null " ;
		mysqlConnectionUtil util = new mysqlConnectionUtil();
		Connection connection = null;
		try {
			connection = util.createConn();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(sql);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = pStatement.executeQuery();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// ѭ����ѯ���
		StringBuffer strIDbuff = new StringBuffer();
		try {
			while (resultSet.next()) {
				String number = resultSet.getString("recordNo");
				String bizID = resultSet.getString("ID");
				CustomerCollection col = CustomerFactory.getLocalInstance(ctx).getCustomerCollection(" where number = '"+number+"'");
				if (col != null && col.size() != 0) {
					continue;
				}
				strIDbuff.append("'");
				strIDbuff.append(bizID);
				strIDbuff.append("',");
				CustomerInfo customerInfo = new CustomerInfo();
				customerInfo.setNumber(resultSet.getString("recordNo"));
				customerInfo.setName(resultSet.getString("customerName"));
				customerInfo.setInvoiceType(PayInvoiceTypeEnum.getEnum(PayInvoiceTypeEnum.COMMONINVOICE_VALUE));
				customerInfo.setCustomerKind(CustomerKindEnum.getEnum(CustomerKindEnum.COMMON_VALUE));
				customerInfo.setUsedStatus(UsedStatusEnum.getEnum(UsedStatusEnum.UNAPPROVE_VALUE));
				try {
					customerInfo.setBrowseGroup(getCSSPGroupInfoF7(ctx, "����"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				CustomerGroupDetailInfo customerGroupDetail = new CustomerGroupDetailInfo();
				try {
					customerGroupDetail.setCustomerGroup(getCSSPGroupInfoF7(ctx, "����"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					customerGroupDetail.setCustomerGroupStandard(getCSSPGroupStandardInfoF7(ctx, "�ͻ������׼"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			customerGroupDetail.setCustomerGroupFullName("����");
				customerGroupDetail.setCustomer(customerInfo);
				customerInfo.getCustomerGroupDetails().add(customerGroupDetail);
				CustomerFactory.getLocalInstance(ctx).addnew(customerInfo);
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(strIDbuff.toString().length() > 1){
			String strID = strIDbuff.toString().substring(0, strIDbuff.length()-1);
			String sql1 = "update t_baseData set kdGetMark = '1' where ID in ("+strID+")";
			PreparedStatement pStatement1 = null;
			try {
				pStatement1 = connection.prepareStatement(sql1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pStatement1.executeUpdate(sql1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pStatement1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    protected void _initBillInfo(Context ctx) {
    	System.out.println("����InitBillInfoFacadeControllerBean");
    	//�վ�
    	StringBuffer sqlbuff = new StringBuffer();
    	sqlbuff.append("SELECT bill.ID billid,org.companyName billcompany," );
    	sqlbuff.append("	bill.receiptNo billreceiptno,bill.billDate billdate,bill.recTypeNo billrectypeno,");
    	sqlbuff.append("	bill.recTypeName billrectypename, bill.account billaccount, bill.bizDate billbizdate,bill.recordCompany billrecordcompany,");
    	sqlbuff.append("	bill.sd_channel billchannel,bill.totalPay billtatalpay,bill.totalActualPay billtatalactualpay,bill.VIPNo billVipNo,");
    	sqlbuff.append("	entry.entryID entryID,entry.expenseNo entryexpenseno,entry.expenseName entryexpensename,entry.qty entryqty,entry.ARAmount entryaramount,");
    	sqlbuff.append("	entry.docNo entrydocno,entry.amount entryamount, entry.type entrytype,entry.VIPRight entryvipright,entry.detailID entrydetailid,entry.receiptNO entryreceiptno,");
    	sqlbuff.append("	fee.itemNo feeitemNo");
    	sqlbuff.append(" FROM t_receipt bill INNER JOIN t_receipt_ety entry ON bill.ID = entry.ID  " );
    	sqlbuff.append("	left join t_org org on bill.company = org.dgtNo " );
    	sqlbuff.append("	left join t_feeitem fee on entry.expenseNo = fee.id " );
//    	sqlbuff.append(" where bill.receiptNo = '20200521080000001354'");
    	//���˵�
    	StringBuffer duizhangSqlBuff = new StringBuffer();
    	duizhangSqlBuff.append("select bill.company companyno ,bill.bizDate billdate ,bill.bizNo billno,bill.recAmount billrecamount,");
    	duizhangSqlBuff.append(" bill.actualRec billactualrec,bill.recTypeNo billrectypeno,bill.recTypeName billrectypename,bill.serviceFee billfee,bill.instalmentFee instalmentFee,");
    	duizhangSqlBuff.append(" bill.recordNo billrecordno,org.companyName companyname ");
    	duizhangSqlBuff.append(" from t_reconciliation bill left join t_org org on bill.company = org.dgtNo ");
//    	duizhangSqlBuff.append(" where bill.company = '20200805081000000178' ");
    	
    	mysqlConnectionUtil sqlutil = new mysqlConnectionUtil();
    	Connection conn = null;
    	try {
			conn = sqlutil.createConn();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		PreparedStatement ps = null;
		//�վ�
    	ResultSet rs = null;
    	//����
    	ResultSet duizhangrs = null;
    	System.out.println(sqlbuff.toString());
    	int i = 0 ;
    	try {
    		//�վ�
			rs = getRs(ctx,conn,ps,sqlbuff.toString());
			while(rs.next()){
				//��ȡ֧����ʽ
				i = Integer.parseInt(rs.getString("billrectypeno"));
				//�շ���Ŀ����
				String feeitemNo = rs.getString("feeitemNo");
		    	//4.2	����ҵ��Խ�����ͼ���ֽ�/֧Ʊ/����ת�ˣ�   �վ�-->�м��-->Ӧ�յ�/�տ
		    	if(i == PayTypeEnum.XIANJIN_VALUE || i == PayTypeEnum.ZHIPIAO_VALUE){
		    		getYingshouMap(ctx, rs);
		    		getShoukuanMap(ctx, rs);
		    	}
		    	//4.5	����ҵ��Խ���˵����ҽ��/�̱�/���Թ�ת�ˣ�
		    	if(i == PayTypeEnum.YIBAO_VALUE || i == PayTypeEnum.GONGDUIGONG_VALUE || i == PayTypeEnum.BAOXIAN_VALUE){
		    		getYingshouMap(ctx, rs);
		    		// TODO: ծȨת��-->Ӧ�յ�
		    		
		    	}
		    	//4.7	����ҵ��Խ���˵��������-�󸶿
		    	if(i == PayTypeEnum.QUDAO_VALUE ){
		    		getYingshouMap(ctx, rs);
		    		// TODO: ծȨת��-->Ӧ�յ�
		    	}
		    	//4.8	֧������ˢ����΢�ŶԽ�����ͼ
		    	if(i == PayTypeEnum.ZHIFUBAO_VALUE || i == PayTypeEnum.WEIXIN_VALUE || i == PayTypeEnum.GUONEISHUAKA_VALUE || i == PayTypeEnum.GUOWAISHUAKA_VALUE){
		    		getYingshouMap(ctx, rs);
		    	}
		    	//4.10	���֧����ʽ�Խ�����ͼ
		    	if(i == PayTypeEnum.YUE_VALUE ){
		    		getYingshouMap(ctx, rs);
		    	}
		    	//4.12	��Ա��
		    	if(i == PayTypeEnum.HUIYUANKA_VALUE ){
		    		//"999"��ͷ���ڻ�Ա����ֵ����
		    		if(feeitemNo.startsWith("999")){
		    			getShoukuanMap(ctx, rs);
		    		}else{
		    			getYingshouMap(ctx, rs);
			    		// TODO: ծȨת��-->Ӧ�յ�
		    			getShoukuanMap(ctx, rs);
			    		// TODO: ծȨת��-->�տ
		    		}
		    	}
			}
			//���˵�
			duizhangrs = getRs(ctx,conn,ps,duizhangSqlBuff.toString());
			while(duizhangrs.next()){
				i = Integer.parseInt(rs.getString("billrectypeno"));
				if(i == PayTypeEnum.YIBAO_VALUE || i == PayTypeEnum.GONGDUIGONG_VALUE || i == PayTypeEnum.BAOXIAN_VALUE){
					getShoukuanMapFromDuizhang(ctx,duizhangrs);
		    	}else if(i == PayTypeEnum.QUDAO_VALUE || i == PayTypeEnum.ZHIFUBAO_VALUE || i == PayTypeEnum.WEIXIN_VALUE || i == PayTypeEnum.GUONEISHUAKA_VALUE || i == PayTypeEnum.GUOWAISHUAKA_VALUE){
					getShoukuanMapFromDuizhang(ctx, duizhangrs);
					getYingfuMapFromDuizhang(ctx, duizhangrs);
		    	}else{
		    		getYingfuMapFromDuizhang(ctx, duizhangrs);
		    	}
			}
			
			//����Ӧ��Map������
			IOtherBill iob = OtherBillFactory.getLocalInstance(ctx);
			if(yingshouMap.size() > 0){
				for (Iterator localIterator = this.yingshouMap.keySet().iterator(); localIterator.hasNext(); ) 
			    {
			    	Object key = localIterator.next();
				    try {
				    	//����Ӧ�յ�
				    	IObjectPK pk = iob.save((OtherBillInfo)this.yingshouMap.get(key));
						//�ύ
				    	iob.submit(pk);
				    	//���
				    	iob.audit(pk);
				    	//�����տ
//				    	if(((OtherBillInfo)this.yingshouMap.get(key)).getAbstractName().equalsIgnoreCase("�ֽ�")){
//				    		createReceivingBill(ctx,pk,false,"AR008copy");
//				    	}
					} catch (EASBizException e) {
						e.printStackTrace();
					}
			    }
			}
			//�����տ�MAP
			IReceivingBill irb = ReceivingBillFactory.getLocalInstance(ctx);
			if(shoukuanMap.size() > 0){
				Set set = new HashSet();
				for (Iterator localIterator = this.shoukuanMap.keySet().iterator(); localIterator.hasNext(); ) 
			    {
			    	Object key = localIterator.next();
				    try {
				    	//����Ӧ�յ�
				    	IObjectPK pk = irb.save((ReceivingBillInfo)this.shoukuanMap.get(key));
						//�ύ
				    	irb.submitBill(pk);
				    	set.add(pk);
					} catch (EASBizException e) {
						e.printStackTrace();
					}
			    }
		    	//���
				irb.audit(set);
			}
			//����Ӧ��
			com.kingdee.eas.fi.ap.IOtherBill yingfu = com.kingdee.eas.fi.ap.OtherBillFactory.getLocalInstance(ctx);
			if(yingfuMap.size() > 0){
				for (Iterator localIterator = this.yingfuMap.keySet().iterator(); localIterator.hasNext(); ) 
			    {
			    	Object key = localIterator.next();
				    try {
				    	//����Ӧ�յ�
				    	IObjectPK pk = yingfu.save((com.kingdee.eas.fi.ap.OtherBillInfo)this.shoukuanMap.get(key));
						//�ύ
				    	yingfu.submit(pk);
				    	//���
				    	yingfu.audit(pk);
					} catch (EASBizException e) {
						e.printStackTrace();
					}
			    }
			}
		    closeConn(conn,ps,rs);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				closeConn(conn,ps,rs);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			try {
				closeConn(conn,ps,rs);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	try {
//			super._initBillInfo(ctx);
//		} catch (BOSException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
    
    //����SQL��ȡ�м�����ݼ�
    protected ResultSet getRs (Context ctx,Connection conn,PreparedStatement ps,String query) throws SQLException, ClassNotFoundException{
    	ResultSet rs =null;
    	mysqlConnectionUtil sqlutil = new mysqlConnectionUtil();
    	rs = sqlutil.onlyquery(ctx,conn,ps,query);
		return rs;
    }
    
    protected void closeConn (Connection conn,PreparedStatement ps,ResultSet rs) throws SQLException, ClassNotFoundException{
    	mysqlConnectionUtil sqlutil = new mysqlConnectionUtil();
    	mysqlConnectionUtil.close(conn, ps, rs);
    }
    
    /**
     * ����BOTP����ı������Ƶ���
     * @param ctx
     * @param pk
     * @param isSave
     * @throws BOSException
     * @throws EASBizException
     */
    public static void createReceivingBill(Context ctx, IObjectPK pk, boolean isSave,String botpNumber)
    throws BOSException, EASBizException
    {
    	IOtherBill iob = OtherBillFactory.getLocalInstance(ctx);
    	OtherBillInfo otherBillInfo = iob.getOtherBillInfo(pk);
//    	ObjectUuidPK pk2 = new ObjectUuidPK(pk.toString());
    	//	��ȡԴ��ֵ����
//    	IObjectValue model = null;
//    	CoreBillBaseController controller = (CoreBillBaseController)EJBFactory.getBusinessController(ctx, pk2.getObjectType());
//    	try {
//    		CoreBillBaseCollection coll = controller.getCoreBillBaseCollection(ctx);
//    	    if(coll.size()>0)
//	    		model = coll.getObject(0);
//	    	else
//	    		throw new BOSException();
//    	} catch (RemoteException e) {
//    		throw new BOSException(e);
//    	}
    	// ��ȡ���õ�BOTP����
//    	DefineSysEnum defineSys =DefineSysEnum.getEnum(DefineSysEnum.BTP_VALUE); 
    	//�տBOSTYPE
//    	String receivingBillType = "FA44FD5B";
    	BOTMappingInfo mapping = BOTMappingFactory.getLocalInstance(ctx).getBOTMappingInfo("select *, extRule.* where name='"+ botpNumber +"' ");
//    	BOTMappingInfo mapping = BOTMappingHelper.getMapping(ctx, model, receivingBillType, defineSys);
    	// ��ȡBOTP�������ʵ��
    	IBTPManager ibtpManager = BTPManagerFactory.getLocalInstance(ctx);
        // ִ��BOTPת��
    	BTPTransformResult btpResult = ibtpManager.transform((CoreBillBaseInfo)otherBillInfo, mapping);
    	// ��ȡת�����
    	IObjectCollection destCol = btpResult.getBills();
    	
    	ReceivingBillInfo receivingBill = (ReceivingBillInfo)destCol.getObject(0);
    	// �������ɵ�Ŀ�굥�ݼ�BOTP������ϵ
//    	ibtpManager.saveRelations(receivingBill, btpResult.getBOTRelationCollection());
    	ibtpManager.submitRelations(receivingBill, btpResult.getBOTRelationCollection());
//    	���ܻ�����ύ����� ��Ҫ�ж�
//		if(!checkBillIsAudit(corebaseInfo)){
//			tarbillBase.getClass().getMethod("audit", IObjectPK.class).invoke(tarbillBase, pk);
//    	}
    }

    //Ӧ��MAP
    protected void getYingshouMap (Context ctx,ResultSet rs) throws Exception{
    	//*********��ͷ*************
    	//��˾
		String billcompany = rs.getString("billcompany");
		//ҵ�񵥾ݱ���
		String billNo = rs.getString("billreceiptno");
		//��������
		Date billdate = rs.getDate("billdate");
		//�շѷ�ʽ����
		String billrectypeno = rs.getString("billrectypeno");
		//�շѷ�ʽ����
		String billrectypename = rs.getString("billrectypename");
		//������
		String billaccount = rs.getString("billaccount");
		//ҵ������
		Date billbizdate = rs.getDate("billbizdate");
		//�������������
		String billrecordcompany = rs.getString("billrecordcompany");
		//��Ӧ��
		BigDecimal billtatalpay = rs.getBigDecimal("billtatalpay");
		//��ʵ��
		BigDecimal billtatalactualpay = rs.getBigDecimal("billtatalactualpay");
		//��Ա����
		String billVipNo = rs.getString("billVipNo");
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String billbizdateStr = sdf.format(billbizdate);
		int year = Integer.parseInt(billbizdateStr.substring(0,4));
		int month = Integer.parseInt(billbizdateStr.substring(5,7));
		//*********��¼*************
		//������Ŀ����
		String entryexpenseno = rs.getString("entryexpenseno");
		//������Ŀ����
		String entryexpensename = rs.getString("entryexpensename");
		//����
		BigDecimal entryqty = rs.getBigDecimal("entryqty");
		//Ӧ�ս��
		BigDecimal entryaramount = rs.getBigDecimal("entryaramount");
		//��ʵ�գ�
		BigDecimal entryamount = rs.getBigDecimal("entryamount");
		//*********����*************
		BigDecimal exchangeRate = new BigDecimal(1.00);
		BigDecimal zero = new BigDecimal(0.00);
		BigDecimal one = new BigDecimal(1.00);    	
		OtherBillInfo otherBillInfo = null;
		OrgmapInfo orgmapInfo = getOrgmapInfoF7(ctx, billcompany);
		CompanyOrgUnitInfo companyInfo =  orgmapInfo.getOrg();
    	if(this.yingshouMap.get(billNo) == null ){
    		otherBillInfo = new OtherBillInfo(); 
    		//ժҪ
    		otherBillInfo.setAbstractName(billrectypename);
//    		getCompanyOrgUnitInfoF7(ctx,billcompany);
    		//������֯
    		otherBillInfo.setCompany(companyInfo);
    		//����Ԫ
    		otherBillInfo.setCU(companyInfo.getCU());
    		//ҵ������
    		otherBillInfo.setBizDate(billbizdate);
    		//�ұ�
    		otherBillInfo.setCurrency(getCurrencyInfoF7(ctx,"BB01"));
    		//��������
    		otherBillInfo.setBillDate(billdate);
    		//����
    		otherBillInfo.setExchangeRate(exchangeRate);
    		//��������
    		otherBillInfo.setBillType(OtherBillTypeEnum.getEnum(OtherBillTypeEnum.OTHERRECEIVABLE_VALUE));
    		//��������
    		otherBillInfo.setAsstActType(getAsstActTypeInfoF7(ctx,"�ͻ�"));
    		//--�����м��ͻ�����ȡ�ͻ���Ϣ--
    		CustomerInfo customerInfo = getCustomerInfoF7(ctx,billaccount);
    		//������ID
    		otherBillInfo.setAsstActID(customerInfo.getId().toString());
    		//����������
    		otherBillInfo.setAsstActName(customerInfo.getName());
    		//����������
    		otherBillInfo.setAsstActNumber(customerInfo.getNumber());
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
    		otherBillInfo.setSaleOrg(getSaleOrgUnitInfoF7(ctx,billcompany));
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
    		otherBillInfo.setTotalAmountLocal(billtatalpay);
    		//�Ƿ�ת��ָ��
    		otherBillInfo.setIsTransPoint(false);
    		//�Ƿ�תӦ��Ӧ��
    		otherBillInfo.setIsTransOtherBill(false);
    		//���������㷨
    		otherBillInfo.setBillRelationOption(BillRelationOptionEnum.getEnum(BillRelationOptionEnum.NULL_VALUE));
    		//�Ƿ�ҵ��Ӧ��Ӧ������
    		otherBillInfo.setIsBizBill(true);
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
    		otherBillInfo.setTotalTaxAmount(billtatalpay);
    		//˰��ϼ�
    		otherBillInfo.setTotalTax(zero);
    		//���ϼ�
    		otherBillInfo.setTotalAmount(billtatalpay);
    		//���������
    		otherBillInfo.setLastExhangeRate(one);
    		//���ʽ
    		otherBillInfo.setPaymentType(getPaymentTypeInfoF7(ctx,"����"));
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
    		otherBillInfo.setUnVerifyAmountLocal(billtatalpay);
    		//δ������
    		otherBillInfo.setUnVerifyAmount(billtatalpay);
    		//�ѽ����λ��
    		otherBillInfo.setVerifyAmountLocal(zero);
    		//�ѽ�����
    		otherBillInfo.setVerifyAmount(zero);
    		//Ӧ�գ�������λ��
    		otherBillInfo.setAmountLocal(billtatalpay);
    		//Ӧ�գ��������
    		otherBillInfo.setAmount(billtatalpay);
    		//����޸�ʱ��
    		otherBillInfo.setLastUpdateTime(new Timestamp(billbizdate.getTime()));
    		//����޸���
    		otherBillInfo.setLastUpdateUser(getUserInfoF7(ctx,"user"));
    		//����ʱ��
    		otherBillInfo.setCreateTime(new Timestamp(billbizdate.getTime()));
    		//������
    		otherBillInfo.setCreator(getUserInfoF7(ctx,"user"));
    		
    		this.yingshouMap.put(billNo, otherBillInfo);
    	}else{
    		otherBillInfo = (OtherBillInfo) yingshouMap.get(billNo);
    	}
//��¼
    	OtherBillentryInfo otherBillentryInfo = new OtherBillentryInfo();
    	CustomerInfo customerInfo = getCustomerInfoF7(ctx,billaccount);
    	//�Ƿ���ȫ����
    	otherBillentryInfo.setIsFullWriteOff(false);
    	//�ͻ��ͻ�����
    	otherBillentryInfo.setSerCustName(customerInfo.getName());
    	//�ͻ��ͻ�����
    	otherBillentryInfo.setSerCustNumber(customerInfo.getNumber());
    	//�����ͻ�����
    	otherBillentryInfo.setOrdCustName(customerInfo.getName());
    	//�����ͻ�����
    	otherBillentryInfo.setOrdCustNumber(customerInfo.getName());
    	//�տ�ͻ�����
    	otherBillentryInfo.setRecAsstActName(customerInfo.getName());
    	//�տ�ͻ�����
    	otherBillentryInfo.setRecAsstActNumber(customerInfo.getNumber());
    	//�տ�ͻ�ID
    	otherBillentryInfo.setRecAsstActID(customerInfo.getId().toString());
    	//�տ���������
    	otherBillentryInfo.setRecAsstActType(getAsstActTypeInfoF7(ctx,"�ͻ�"));
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
    	otherBillentryInfo.setUnInvoiceReqAmountLocal(entryaramount);
    	//δ��Ʊ������
    	otherBillentryInfo.setUnInvoiceReqAmount(entryaramount);
    	//�ѿ�Ʊ�����λ��
    	otherBillentryInfo.setInvoiceReqAmountLocal(zero);
    	//�ѿ�Ʊ������
    	otherBillentryInfo.setInvoiceReqAmount(zero);
    	//δ��Ʊ�����������
    	otherBillentryInfo.setUnInvoiceReqBaseQty(entryqty);
    	//δ��Ʊ��������
    	otherBillentryInfo.setUnInvoiceReqQty(entryqty);
    	//�ѿ�Ʊ�����������
    	otherBillentryInfo.setInvoiceReqBaseQty(zero);
    	//�ѿ�Ʊ��������
    	otherBillentryInfo.setInvoiceReqQty(zero);
    	//������
    	otherBillentryInfo.setIsQtyZero(false);
    	//��������
    	otherBillentryInfo.setBillDate(billdate);
    	//��˾
    	otherBillentryInfo.setCompany(companyInfo.getId().toString());
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
    	otherBillentryInfo.setLocalUnwriteOffAmount(entryaramount);
    	//δ������������
    	otherBillentryInfo.setUnwriteOffBaseQty(entryqty);
    	//�Ѻ�����λ�ҽ��
    	otherBillentryInfo.setLocalWrittenOffAmount(zero);
    	//�Ѻ�����������
    	otherBillentryInfo.setWittenOffBaseQty(zero);
    	//��ʷδ������λ��
    	otherBillentryInfo.setHisUnVerifyAmountLocal(zero);
    	//��ʷδ�������
    	otherBillentryInfo.setHisUnVerifyAmount(zero);
    	//����������λ����
    	otherBillentryInfo.setBaseQty(entryqty);
    	//��������
    	otherBillentryInfo.setAssistQty(zero);
    	//��λ��
    	otherBillentryInfo.setAmountLocal(entryaramount);
    	//���
    	otherBillentryInfo.setAmount(entryaramount);
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
    	otherBillentryInfo.setTaxPrice(entryaramount.divide(entryqty));
    	//ʵ�ʵ���
    	otherBillentryInfo.setRealPrice(entryaramount.divide(entryqty));
    	//����
    	otherBillentryInfo.setPrice(entryaramount.divide(entryqty));
    	//����
    	otherBillentryInfo.setQuantity(entryqty);
    	//δ������λ��
    	otherBillentryInfo.setLockUnVerifyAmtLocal(entryaramount.subtract(entryamount));
    	//δ�������
    	otherBillentryInfo.setLockUnVerifyAmt(entryaramount.subtract(entryamount));
    	//��������λ��
    	otherBillentryInfo.setLockVerifyAmtLocal(entryamount);
    	//���������
    	otherBillentryInfo.setLockVerifyAmt(entryamount);
    	//δ�����λ��
    	otherBillentryInfo.setUnVerifyAmountLocal(entryaramount);
    	//δ������
    	otherBillentryInfo.setUnVerifyAmount(entryaramount);
    	//�ѽ����λ��
    	otherBillentryInfo.setVerifyAmountLocal(zero);
    	//�ѽ�����
    	otherBillentryInfo.setVerifyAmount(zero);
    	//Ӧ�գ�������λ��
    	otherBillentryInfo.setRecievePayAmountLocal(entryaramount);
    	//Ӧ�գ��������
    	otherBillentryInfo.setRecievePayAmount(entryaramount);
    	//�Ƿ���Ʒ
    	otherBillentryInfo.setIsPresent(false);
    	//������Ŀ
    	otherBillentryInfo.setExpenseItem(getExpenseTypeInfoF7(ctx,entryexpenseno));
    	//������λ
//    	otherBillentryInfo.setMeasureUnit(getMeasureUnitInfoF7(ctx,"1000"));
    	//��������
//    	otherBillentryInfo.setMaterialName("");
    	//����
//    	otherBillentryInfo.setMaterial(item);
    	//������
//    	otherBillentryInfo.setRowType(item);
//    	otherBillentryInfo.setParent(otherBillInfo);
    	//��ͷ
    	otherBillentryInfo.setHead(otherBillInfo);
    	otherBillInfo.getEntry().add(otherBillentryInfo);
//�տ�ƻ�
    	OtherBillPlanInfo planInfo = new OtherBillPlanInfo();
    	//Ӧ��Ӧ������
    	planInfo.setRecievePayDate(billdate);
    	//Ӧ��Ӧ�����
    	planInfo.setRecievePayAmount(entryaramount);
    	//Ӧ��Ӧ����λ��
    	planInfo.setRecievePayAmountLocal(entryaramount);
    	//δ�������
    	planInfo.setUnLockAmount(entryaramount.subtract(entryamount));
    	//δ������λ��
    	planInfo.setUnLockAmountLoc(entryaramount.subtract(entryamount));
    	//���������
    	planInfo.setLockAmount(entryamount);
    	//��������λ��
    	planInfo.setLockAmountLoc(entryamount);
    	//δ������
    	planInfo.setUnVerifyAmount(entryaramount);
    	//δ�����λ��
    	planInfo.setUnVerifyAmountLocal(entryaramount);
    	
    	planInfo.setParent(otherBillInfo);
    	otherBillInfo.getRecievePlan().add(planInfo);
    	
    }
    
    //�տ�MAP
    protected void getShoukuanMap (Context ctx,ResultSet rs) throws Exception{
    	//*********��ͷ*************
    	//��˾
		String billcompany = rs.getString("billcompany");
		//ҵ�񵥾ݱ���
		String billNo = rs.getString("billreceiptno");
		//��������
		Date billdate = rs.getDate("billdate");
		//�շѷ�ʽ����
		String billrectypeno = rs.getString("billrectypeno");
		//�շѷ�ʽ����
		String billrectypename = rs.getString("billrectypename");
		//������
		String billaccount = rs.getString("billaccount");
		//ҵ������
		Date billbizdate = rs.getDate("billbizdate");
		//�������������
		String billrecordcompany = rs.getString("billrecordcompany");
		//��Ӧ��
		BigDecimal billtatalpay = rs.getBigDecimal("billtatalpay");
		//��ʵ��
		BigDecimal billtatalactualpay = rs.getBigDecimal("billtatalactualpay");
		//��Ա����
		String billVipNo = rs.getString("billVipNo");
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String billbizdateStr = sdf.format(billbizdate);
		int year = Integer.parseInt(billbizdateStr.substring(0,4));
		int month = Integer.parseInt(billbizdateStr.substring(5,7));
		//*********��¼*************
		//������Ŀ����
		String entryexpenseno = rs.getString("entryexpenseno");
		//������Ŀ����
		String entryexpensename = rs.getString("entryexpensename");
		//����
		BigDecimal entryqty = rs.getBigDecimal("entryqty");
		//Ӧ�ս��
		BigDecimal entryaramount = rs.getBigDecimal("entryaramount");
		//��ʵ�գ�
		BigDecimal entryamount = rs.getBigDecimal("entryamount");
		//*********����*************
		ReceivingBillInfo receivingBillInfo = null;
		CustomerInfo customerInfo = getCustomerInfoF7(ctx,billaccount);
		OrgmapInfo orgmapInfo = getOrgmapInfoF7(ctx, billcompany);
		CompanyOrgUnitInfo companyInfo =  orgmapInfo.getOrg();
		if(this.shoukuanMap.get(billNo) == null ){
			receivingBillInfo = new ReceivingBillInfo(); 
//			receivingBillInfo.setrecBillClaim	�տ�������Ϣ	����	FRecBillClaim
//			receivingBillInfo.setbillClaimStatus	��������״̬	ҵ��ö��	FBillClaimStatus	��	10
			receivingBillInfo.setBillClaimStatus(BillClaimStatusEnum.getEnum(BillClaimStatusEnum.UNCLAIM_VALUE));
//			receivingBillInfo.setisSmart	�Ƿ����ܺ���	ҵ��ö��	FIsSmart	��	1
			receivingBillInfo.setIsSmart(SmartType.getEnum(SmartType.OPEN_VALUE));
//			receivingBillInfo.setprofitCenter	��������	����	FProfitCenterID		
//			receivingBillInfo.setisHasRefundPay	�Ƿ��Ѿ������˿�֧������	����	FisHasRefundPay	
			receivingBillInfo.setIsHasRefundPay(false);
//			receivingBillInfo.setisRefundmentPay	�Ƿ��˿�֧��	����	FisRefundmentPay		
			receivingBillInfo.setIsRefundmentPay(false);
//			receivingBillInfo.setbankCheckStatus	����δ��״̬	ҵ��ö��	FBankCheckStatus	
//			receivingBillInfo.setprintCount	��ӡ����	����	FPrintCount	��	1
			receivingBillInfo.setPrintCount(1);
//			receivingBillInfo.setpaymentType	���ʽ	����	FPaymentTypeID	��	
			receivingBillInfo.setPaymentType(getPaymentTypeInfoF7(ctx,"����"));
//			receivingBillInfo.setSYNBillEntryID	Эͬ���ݷ�¼ID	�ַ���	FSYNBillEntryID		
//			receivingBillInfo.setSYNBillID	Эͬ����ID	�ַ���	FSYNBillID		
//			receivingBillInfo.setSYNbillNumber	Эͬ���ݱ��	�ַ���	FSYNbillNumber		
//			receivingBillInfo.setSYNbillType	Эͬ��������	�ַ���	FSYNbillType		
//			receivingBillInfo.setoppBgItemId	����Ԥ����ĿId	�ַ���	FOppBgItemId		
//			receivingBillInfo.setoppBgItemNumber	����Ԥ����Ŀ����	�ַ���	FOppBgItemNumber		
//			receivingBillInfo.setoppBgItemName	����Ԥ����Ŀ����	�ַ���	FOppBgItemName		
//			receivingBillInfo.setpayAccountBank	ʵ�������˺�	�ַ���	FPayAccountBank		
//			receivingBillInfo.setoppFpItem	�����ƻ���Ŀ	����	FOppFpItemID		
//			receivingBillInfo.setisReverseLockAmount	�Ƿ�д�������	����	FIsReverseLockAmount	��	1
			receivingBillInfo.setIsReverseLockAmount(true);
//			receivingBillInfo.setlastPayerType	ԭ����������	����	FLastPayerTypeID		
//			receivingBillInfo.setlastPayerName	ԭ����������	�ַ���	FLastPayerName		
//			receivingBillInfo.setlastPayerNumber	ԭ���������	�ַ���	FLastPayerNumber		
//			receivingBillInfo.setlastPayerID	ԭ������ID	�ַ���	FLastPayerID		
//			receivingBillInfo.setisPreReturn	���ݷ�¼�տ������Ƿ���Ԥ�տ������Ԥ�տ�	����	FIsPreReturn		
//			receivingBillInfo.setisProxyReturn	���ݷ�¼�տ������Ƿ��д��տ�����˴��տ�	����	FIsProxyReturn		
//			receivingBillInfo.setisSaleReturn	��¼�տ������Ƿ������ۻؿ���������ۻؿ�	����	FIsSaleReturn		
//			receivingBillInfo.setrealRecCompany	ʵ���տ˾	����	FRealRecCompanyID		
//			receivingBillInfo.setrealRecBillID	�����տ�ʵ�ʸ��˾	�ַ���	FRealRecBillID		
//			receivingBillInfo.setsrcRealRecBillID	�����տ�Դ��ID	�ַ���	FSrcRealRecBillID		
//			receivingBillInfo.setreceivingBillType	�տ����	ҵ��ö��	FReceivingBillType	��	1
			receivingBillInfo.setReceivingBillType(CasRecPayBillTypeEnum.getEnum(CasRecPayBillTypeEnum.COMMONTYPE_VALUE));
//			receivingBillInfo.setassItems	�Է���Ŀ������Ŀ	����			
//			receivingBillInfo.setpayerAccountBankO	���и����˻�	����	FPayerAccountBankID		
//			receivingBillInfo.setisRelateRecBook	�Ƿ��ѹ�������Ӧ��Ʊ��	����	FIsRelateRecBook		
//			receivingBillInfo.setrecBillType	�տ�����	����	FRecBillTypeID	��	
			receivingBillInfo.setRecBillType(getReceivingBillTypeInfoF7(ctx,"Ԥ�տ�"));
//			receivingBillInfo.setbgAmount	Ԥ���׼���	����	FBgAmount		
//			receivingBillInfo.setdeliver	��Ʊ��	����	FDeliverID		
//			receivingBillInfo.setoppInnerAcct	�ڲ������˻�	����	FOppInnerAcctID		
//			receivingBillInfo.setreceipt	�������˵�	����	FReceiptId		
//			receivingBillInfo.setisRelateReceipt	�Ƿ��ѹ������˵�	����	FIsRelateReceipt		
//			receivingBillInfo.setrecDate	�տ�����	����	FRecDate	
			receivingBillInfo.setRecDate(billbizdate);
//			receivingBillInfo.setentries	�տ��¼	����			
//			receivingBillInfo.setpayerAccountBank	�����˺�	�ַ���	FPayerAccountBank		
//			receivingBillInfo.setpayerBank	��������	�ַ���	FPayerAccountNumber		
//			receivingBillInfo.setpayerName	����������	�ַ���	FPayerName		
			receivingBillInfo.setPayerName(customerInfo.getName());
//			receivingBillInfo.setpayerNumber	���������	�ַ���	FPayerNumber	
			receivingBillInfo.setPayerNumber(customerInfo.getNumber());
//			receivingBillInfo.setpayerID	������ID	�ַ���	FPayerID	
			receivingBillInfo.setPayerID(customerInfo.getId().toString());
//			receivingBillInfo.setpayerType	����������	����	FPayerTypeID	��	
			receivingBillInfo.setPayerType(getAsstActTypeInfoF7(ctx,"�ͻ�"));
//			receivingBillInfo.setpayeeAccount	�տ��Ŀ	����	FPayeeAccountID	��	
//			receivingBillInfo.setpayeeAccountBank	�տ��˻�	����	FPayeeAccountBankID		
//			receivingBillInfo.setpayeeBank	�տ�����	����	FPayeeBankID		
//			receivingBillInfo.setactRecLocAmtVc	ʵ�ձ�λ�ҽ���ۼƺ���	����	FActRecLocAmtVc		
//			receivingBillInfo.setactRecLocAmt	ʵ�ձ�λ�ҽ��ϼ�	����	FActRecLocAmt	��	
			receivingBillInfo.setActRecLocAmt(billtatalactualpay);
//			receivingBillInfo.setactRecAmtVc	ʵ�ս���ۼƺ���	����	FActRecAmtVc		
//			receivingBillInfo.setactRecAmt	ʵ�ս��ϼ�	����	FActRecAmt	��	
			receivingBillInfo.setActRecAmt(billtatalactualpay);
//			receivingBillInfo.setrecType	�տ����ͣ����ڿ�ʼ���ã�	ҵ��ö��	FRecType		
//			receivingBillInfo.setverifyStatus	����״̬	ҵ��ö��	FverifyStatus	��	1
			receivingBillInfo.setVerifyStatus(com.kingdee.eas.fi.cas.verifyStatusEnum.getEnum(com.kingdee.eas.fi.cas.verifyStatusEnum.SOME_VERIFIED_VALUE));
//			receivingBillInfo.setisPreVerify	�Ƿ�Ӧ����Ԥ������ס�ĵ�	����	FIsPreVerify	
			receivingBillInfo.setIsPreVerify(false);
//			receivingBillInfo.setisDiffCurSettlement	����ֹ�����ʶ	����	FIsDiffCurSettlement		
//			receivingBillInfo.setbgCtrlAmt	Ԥ����ƽ��	����	FBgCtrlAmt	��	
			receivingBillInfo.setBgCtrlAmt(billtatalactualpay);
//			receivingBillInfo.setbankCheckFlag	���˱�ʶ��-�������ܶ��˷�д	�ַ���	FBankCheckFlag		
//			receivingBillInfo.setmbgName	����ά������	�ַ���	FMbgname		
//			receivingBillInfo.setmbgNumber	����ά�ȱ���	�ַ���	FMbgnumber		
//			receivingBillInfo.setfundFlowItem	�ʽ�������Ŀ	����	FFundFlowItemID		
//			receivingBillInfo.setpcaVouchered	�Ƿ�������������ƾ֤	����	FpcaVouchered		
//			receivingBillInfo.setcostCenter	�ɱ�����	����	FCostCenterID		
//			receivingBillInfo.setsubSettDate	�ύ������������	����	FSubSettDate		
//			receivingBillInfo.setoutBgItemName	����Ԥ����Ŀ����	�ַ���	FOutBgItemName		
//			receivingBillInfo.setoutBgItemNumber	����Ԥ����Ŀ����	�ַ���	FOutBgItemNumber		
//			receivingBillInfo.setoutBgItemId	����Ԥ����ĿID	�ַ���	FOutBgItemID		
//			receivingBillInfo.setcontractNum	��ͬ���	�ַ���	FContractNumber		
//			receivingBillInfo.setisCoopBuild	�Ƿ�Эͬ����	����	FIsCoopBuild		
//			receivingBillInfo.setBillDate_SourceBill	��Դ������	����	FBillDate_SourceBill		
//			receivingBillInfo.setAsstActTypeID_SourceBill	��Դ����������	�ַ���	FAsstActTypeID_SourceBill		
//			receivingBillInfo.setAsstActID_SourceBill	��Դ��������	�ַ���	FAsstActID_SourceBill		
//			receivingBillInfo.setPersonID_SourceBill	��Դ����Ա	�ַ���	FPersonID_SourceBill		
//			receivingBillInfo.setAdminOrgUnitId_SourceBill	��Դ������	�ַ���	FAdminOrgUnitId_SourceBill		
//			receivingBillInfo.setisImpFromGL	�Ƿ���������������	����	FIsImpFromGL		
//			receivingBillInfo.setmixEntryVerify	����ո������ͷ�¼�Ƿ������Ӧ��������	����	FMixEntryVerify		
//			receivingBillInfo.setisAppointVoucher	�Ƿ�������ָ��ƾ֤	����	FIsAppointVoucher		
//			receivingBillInfo.setaccepterDate	���㵥��������	����	FAccepterDate		
//			receivingBillInfo.setaccepter	���㵥������	�ַ���	FAccepter		
//			receivingBillInfo.setapprover	һ�������	����	FApproverID		
//			receivingBillInfo.setapproveDate	һ���������	����	FApproveDate		
//			receivingBillInfo.setunVerifiedAmtLoc	δ�����λ�Һϼ�	����	FUnVerifiedAmtLoc	��	
			receivingBillInfo.setUnVerifiedAmtLoc(billtatalactualpay);
//			receivingBillInfo.setverifiedAmtLoc	�ѽ����λ�Һϼ�	����	FVerifiedAmtLoc		
			receivingBillInfo.setVerifiedAmtLoc(BigDecimal.ZERO);
//			receivingBillInfo.setunVerifiedAmt	δ������ϼ�	����	FUnVerifiedAmt	��	
			receivingBillInfo.setUnVerifiedAmt(billtatalactualpay);
//			receivingBillInfo.setverifiedAmt	�ѽ�����ϼ�	����	FVerifiedAmt		
			receivingBillInfo.setVerifiedAmt(BigDecimal.ZERO);
//			receivingBillInfo.setvoucherNumber	ƾ֤���	�ַ���	FVoucherNumber		
//			receivingBillInfo.setvoucher	ƾ֤ID	����	FVoucherID		
//			receivingBillInfo.setvoucherType	ƾ֤��	����	FVoucherTypeID		
//			receivingBillInfo.setisNeedVoucher	���˷�ʽ	����	FIsNeedVoucher	��	1
			receivingBillInfo.setIsNeedVoucher(true);
//			receivingBillInfo.setisTransOtherBill	�Ƿ�תԤ��תԤ��	����	FIsTransOtherBill		
			receivingBillInfo.setIsTransOtherBill(false);
//			receivingBillInfo.setisCtrlOppAcct	���ƶԷ���Ŀ	����	FIsCtrlOppAcct		
//			receivingBillInfo.setfeeType	�ո����	����	FFeeTypeID		
//			receivingBillInfo.setisTransBill	�Ƿ�ת������	����	FIsTransBill		
//			receivingBillInfo.setisRedBill	�Ƿ��Ǻ��ֵ���	����	FIsRedBill
			receivingBillInfo.setIsRedBill(false);
//			receivingBillInfo.setbizType	ҵ������	����	FBizTypeID		
//			receivingBillInfo.setisBookRL	�Ƿ�Ǽ������ռ���	����	FIsBookRL		
//			receivingBillInfo.setprojectManager	��Ŀ����	����	FProjectManagerID		
//			receivingBillInfo.setproject	��Ŀ	����	FProjectID		
//			receivingBillInfo.setcontractBillId	��ͬId	�ַ���	FContractBillID		
//			receivingBillInfo.setcapitalAmount	��д���	�ַ���	FCapitalAmount		
//			receivingBillInfo.setdayaccount	�ռ������	����	FDayaccount		
//			receivingBillInfo.setcontractNo	��ͬ���	�ַ���	FContractNo		
//			receivingBillInfo.setsummary	����˵��	�ַ���	FSummary		
//			receivingBillInfo.setconceit	������	�ַ���	FConceit		
//			receivingBillInfo.setaccessoryAmt	������	����	FAccessoryAmt		
//			receivingBillInfo.setperson	��Ա	����	FPersonId		
//			receivingBillInfo.setadminOrgUnit	����	����	FAdminOrgUnitId		
//			receivingBillInfo.setlocalAmt	Ӧ�գ�������λ�ҽ��	����	FLocalAmount	��	
			receivingBillInfo.setLocalAmt(billtatalactualpay);
//			receivingBillInfo.setamount	Ӧ�գ��������	����	FAmount	��	
			receivingBillInfo.setAmount(billtatalactualpay);
//			receivingBillInfo.setisImport	�Ƿ���	����	FIsImport	
			receivingBillInfo.setIsImport(false);
//			receivingBillInfo.setfundType	�ո��ʽ	ҵ��ö��	FFundType	
			receivingBillInfo.setFundType(BizTypeEnum.getEnum(BizTypeEnum.CASH_VALUE));
//			receivingBillInfo.setsettlementStatus	���н���״̬	ҵ��ö��	FSettlementStatus	��	10
			receivingBillInfo.setSettlementStatus(SettlementStatusEnum.getEnum(SettlementStatusEnum.UNSUBMIT_VALUE));
//			receivingBillInfo.setbillStatus	����״̬	ҵ��ö��	FBillStatus	��	10
			receivingBillInfo.setBillStatus(com.kingdee.eas.fi.cas.BillStatusEnum.getEnum(com.kingdee.eas.fi.cas.BillStatusEnum.AUDITED_VALUE));
//			receivingBillInfo.setfiVouchered	�Ƿ�������ƾ֤	����	FFiVouchered		
//			receivingBillInfo.setisInitializeBill	�Ƿ��ʼ������	����	FIsInitializeBill		
//			receivingBillInfo.setaccountant	���	����	FAccountantID		
//			receivingBillInfo.setcashier	����	����	FCashierID		
//			receivingBillInfo.setauditDate	�������	����	FAuditDate		
//			receivingBillInfo.setsettleBizType	��������	ҵ��ö��	FSettleBizType		
//			receivingBillInfo.setisCommitSettle	�Ƿ��ύ��������	����	FIsCommitSettle		
			receivingBillInfo.setIsCommitSettle(false);
//			receivingBillInfo.setfpItem	�ƻ���Ŀ	����	FFpItemID		
//			receivingBillInfo.setoppAccount	�Է���Ŀ	����	FOppAccountID		
//			receivingBillInfo.setsettlementNumber	�����	�ַ���	FSettlementNumber		
//			receivingBillInfo.setsettlementType	���㷽ʽ	����	FSettlementTypeID	��
			receivingBillInfo.setSettlementType(getSettlementTypeInfoF7(ctx,"�ֽ�"));
//			receivingBillInfo.setlastExhangeRate	���������	����	FLastExhangeRate
			receivingBillInfo.setLastExhangeRate(BigDecimal.ONE);
//			receivingBillInfo.setisExchanged	�Ƿ��Ѿ�����	����	FIsExchanged
			receivingBillInfo.setIsExchanged(false);
//			receivingBillInfo.setexchangeRate	����	����	FExchangeRate	��	1
			receivingBillInfo.setExchangeRate(BigDecimal.ONE);
//			receivingBillInfo.setcurrency	�ұ�	����	FCurrencyID	��	dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC
			receivingBillInfo.setCurrency(getCurrencyInfoF7(ctx,"BB01"));
//			receivingBillInfo.setsourceType	ҵ��ϵͳ	ҵ��ö��	FSourceType	��	100
			receivingBillInfo.setSourceType(SourceTypeEnum.getEnum(SourceTypeEnum.AR_VALUE));
//			receivingBillInfo.setsourceSysType	��Դϵͳ	ҵ��ö��	FSourceSysType	��	100
			receivingBillInfo.setSourceSysType(SourceTypeEnum.getEnum(SourceTypeEnum.AR_VALUE));
//			receivingBillInfo.setcompany	��˾	����	FCompanyID	��	
			receivingBillInfo.setCompany(companyInfo);
//			receivingBillInfo.setsourceFunction	��Դ����	�ַ���	FSourceFunction		
//			receivingBillInfo.setsourceBillId	ԭʼ����ID	�ַ���	FSourceBillID		
//			receivingBillInfo.setauditor	�����	����	FAuditorID		
//			receivingBillInfo.sethasEffected	�Ƿ�������Ч	����	FHasEffected		
//			receivingBillInfo.setdescription	�ο���Ϣ	�ַ���	FDescription		
//			receivingBillInfo.sethandler	������	����	FHandlerID		
//			receivingBillInfo.setbizDate	ҵ������	����	FBizDate	��	
			receivingBillInfo.setBizDate(billbizdate);
//			receivingBillInfo.setnumber	���ݱ��	�ַ���	FNumber	��	
//			receivingBillInfo.setCU	���Ƶ�Ԫ	����	FControlUnitID	��	
			receivingBillInfo.setCU(companyInfo.getCU());
//			receivingBillInfo.setlastUpdateTime	����޸�ʱ��	����	FLastUpdateTime		
//			receivingBillInfo.setlastUpdateUser	����޸���	����	FLastUpdateUserID		
//			receivingBillInfo.setcreateTime	����ʱ��	����	FCreateTime	��	
			receivingBillInfo.setCreateTime(new Timestamp(billbizdate.getTime()));
//			receivingBillInfo.setcreator	������	����	FCreatorID	��	
//			receivingBillInfo.setid	ID	BOSUUID	FID	��	
			this.shoukuanMap.put(billNo, receivingBillInfo);
		}else{
			receivingBillInfo = (ReceivingBillInfo) this.shoukuanMap.get(billNo);
		}
		ReceivingBillEntryInfo receivingBillEntryInfo = new ReceivingBillEntryInfo();
//		receivingBillEntryInfo.setarPrintBillEntry	Ӧ���嵥��¼	�ַ���	FArPrintBillEntryId		
//		receivingBillEntryInfo.setarPrintBill	Ӧ���嵥	�ַ���	FArPrintBillId		
//		receivingBillEntryInfo.setoppBgItemName	����Ԥ����Ŀ����	�ַ���	FOppBgItemName		
//		receivingBillEntryInfo.setoppBgItemNumber	����Ԥ����Ŀ����	�ַ���	FOppBgItemNumber		
//		receivingBillEntryInfo.setoppBgItemId	����Ԥ����ĿId	�ַ���	FOppBgItemId		
//		receivingBillEntryInfo.setmatchedAmountLoc	matchedAmountLoc	����	FMatchedAmountLoc		
//		receivingBillEntryInfo.setmatchedAmount	��ƥ����	����	FMatchedAmount		
//		receivingBillEntryInfo.setrecBillType	��¼�տ�����	����	FRecBillTypeID	��	
		receivingBillEntryInfo.setRecBillType(getReceivingBillTypeInfoF7(ctx,"Ԥ�տ�"));
//		receivingBillEntryInfo.setassItemsEntries	������Ŀ���	����			
//		receivingBillEntryInfo.setexpenseType	������Ŀ	����	FExpenseTypeID		
//		receivingBillEntryInfo.setreceiptID	���������վݵ�ID	BOSUUID	FReceiptID		
//		receivingBillEntryInfo.setreceiptNumber	�վݺ�	�ַ���	FReceiptNumber		
//		receivingBillEntryInfo.setcustomerBillNum	�ͷ����ݺ�	�ַ���	FCustomerBillNum		
//		receivingBillEntryInfo.setbizBillNumber	ҵ�񵥾ݺ�	�ַ���	FBizBillNumber		
//		receivingBillEntryInfo.setbizDate	��������	����	FBizDate		
		receivingBillEntryInfo.setBizDate(billbizdate);
//		receivingBillEntryInfo.setreceivingBill	�տͷ	����	FReceivingBillID	��	
//		receivingBillEntryInfo.setbgCtrlAmt	Ԥ����ƽ��	����	FBgCtrlAmt	��	
		receivingBillEntryInfo.setBgCtrlAmt(entryaramount);
//		receivingBillEntryInfo.setmbgName	����ά������	�ַ���	FMbgName		
//		receivingBillEntryInfo.setmbgNumber	����ά�ȱ���	�ַ���	FMbgNumber		
//		receivingBillEntryInfo.setfundFlowItem	�ʽ�������Ŀ	����	FFundFlowItemID		
//		receivingBillEntryInfo.settrackNumber	���ٺ�	����	FTrackNumberID		
//		receivingBillEntryInfo.setproject	��Ŀ��	����	FProjectID		
//		receivingBillEntryInfo.setoutBgItemName	����Ԥ����Ŀ����	�ַ���	FOutBgItemName		
//		receivingBillEntryInfo.setoutBgItemNumber	����Ԥ����Ŀ����	�ַ���	FOutBgItemNumber		
//		receivingBillEntryInfo.setoutBgItemId	����Ԥ����ĿID 	�ַ���	FOutBgItemId		
//		receivingBillEntryInfo.setcostCenter	�ɱ�����	����	FCostCenterID		
//		receivingBillEntryInfo.setcontractEntryID	��ͬ���ݷ�¼ID	�ַ���	FContractEntryID		
//		receivingBillEntryInfo.setcontractBillID	��ͬ����ID	�ַ���	FContractBillID		
//		receivingBillEntryInfo.setcontractEntrySeq	��ͬ�к�	����	FContractEntrySeq		
//		receivingBillEntryInfo.setcontractNum	��ͬ���	�ַ���	FContractNumber		
//		receivingBillEntryInfo.setotherBillTransAsstTypeId	תԤ��תԤ������������	����	FOtherBillTransAsstTypeId		
//		receivingBillEntryInfo.setfpItem	�ƻ���Ŀ	����	FFpItemID		
//		receivingBillEntryInfo.setoppAccount	�Է���Ŀ	����	FOppAccountID		
//		receivingBillEntryInfo.setsourceBillAsstActID	Դ��������id	�ַ���	FSourceBillAsstActID		
//		receivingBillEntryInfo.setcurrency	�ұ�	����	FCurrencyId	
		receivingBillEntryInfo.setCurrency(getCurrencyInfoF7(ctx,"BB01"));
//		receivingBillEntryInfo.settrackNumbet	���ٺ�	�ַ���	FTrackNumber		
//		receivingBillEntryInfo.setcoreBillEntrySeq	���ĵ��ݷ�¼�к�	����	FCoreBillEntrySeq		
//		receivingBillEntryInfo.setcoreBillNumber	���ĵ��ݺ�	�ַ���	FCoreBillNumber		
//		receivingBillEntryInfo.setcoreBillEntryId	���ĵ��ݷ�¼Id	�ַ���	FCoreBillEntryId		
//		receivingBillEntryInfo.setcoreBillId	���ĵ���Id	�ַ���	FCoreBillId		
//		receivingBillEntryInfo.setcoreBillType	���ĵ�������	����	FCoreBillTypeId		
//		receivingBillEntryInfo.sethisUnVcLocAmount	��ʷδ������λ��	����	FHisUnVcLocAmount		
//		receivingBillEntryInfo.sethisUnVcAmount	��ʷδ�������	����	FHisUnVcAmount		
//		receivingBillEntryInfo.setvcStatus	����״̬	ҵ��ö��	FVcStatus	��	
//		receivingBillEntryInfo.setsourceBillEntryId	Դ���ݷ�¼ID	�ַ���	FSourceBillEntryId		
//		receivingBillEntryInfo.setsourceBillId	Դ����ID	�ַ���	FSourceBillId		
//		receivingBillEntryInfo.setunLockLocAmt	δ������λ�ҽ��	����	FUnLockLocAmt	��	
		receivingBillEntryInfo.setUnLockLocAmt(BigDecimal.ZERO);
//		receivingBillEntryInfo.setunLockAmt	δ�������	����	FUnLockAmt	��	
		receivingBillEntryInfo.setUnLockAmt(BigDecimal.ZERO);
//		receivingBillEntryInfo.setlockLocAmt	������λ�ҽ��	����	FLockLocAmt		
		receivingBillEntryInfo.setLockLocAmt(entryaramount);
//		receivingBillEntryInfo.setlockAmt	�������	����	FLockAmt	
		receivingBillEntryInfo.setLockAmt(entryaramount);
//		receivingBillEntryInfo.setremark	��ע	�ַ���	FRemark		
//		receivingBillEntryInfo.setactualLocAmtVc	ʵ�գ�������λ�ҽ���ۼƺ���	����	FActualLocAmtVc		
		receivingBillEntryInfo.setActualLocAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setactualLocAmt	ʵ�գ�������λ�ҽ��	����	FActualLocAmt	��	
		receivingBillEntryInfo.setActualLocAmt(entryaramount);
//		receivingBillEntryInfo.setactualAmtVc	ʵ�գ���������ۼƺ���	����	FActualAmtVc	
		receivingBillEntryInfo.setActualAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setactualAmt	ʵ�գ��������	����	FActualAmt	��
		receivingBillEntryInfo.setActualAmt(entryaramount);
//		receivingBillEntryInfo.setrebateLocAmtVc	�ۿ۱�λ�ҽ���ۼƺ���	����	FRebateLocAmtVc	
		receivingBillEntryInfo.setRebateLocAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrebateLocAmt	�ۿ۱�λ�ҽ��	����	FRebateLocAmt	
		receivingBillEntryInfo.setRebateLocAmt(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrebateAmtVc	�ۿ۽���ۼƺ���	����	FRebateAmtVc
		receivingBillEntryInfo.setRebateAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrebate	�ֽ��ۿ�	����	FRebate	
		receivingBillEntryInfo.setRebate(BigDecimal.ZERO);
//		receivingBillEntryInfo.setunVerifyExgRateLoc	δ������㱾λ�ҽ��	����	FUnVerifyExgRateLoc		
		receivingBillEntryInfo.setUnVerifyExgRateLoc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setunVcLocAmount	Ӧ�գ�����δ������λ�ҽ��	����	FUnVcLocAmount	��	
		receivingBillEntryInfo.setUnVcLocAmount(entryaramount);
//		receivingBillEntryInfo.setunVcAmount	Ӧ�գ�����δ�������	����	FUnVcAmount	��	
		receivingBillEntryInfo.setUnVcAmount(entryaramount);
//		receivingBillEntryInfo.setlocalAmtVc	Ӧ�գ�������λ���ۼƺ���	����	FLocalAmtVc	
		receivingBillEntryInfo.setLocalAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setlocalAmt	Ӧ�գ�������λ�ҽ��	����	FLocalAmount	��	
		receivingBillEntryInfo.setLocalAmt(entryaramount);
//		receivingBillEntryInfo.setamountVc	Ӧ�գ���������ۼƺ���	����	FAmountVc	
		receivingBillEntryInfo.setAmountVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setamount	Ӧ�գ��������	����	FAmount	��	
		receivingBillEntryInfo.setAmount(entryaramount);
//		receivingBillEntryInfo.setseq	���ݷ�¼���к�	����	FSeq		
//		receivingBillEntryInfo.setid	ID	BOSUUID	FID		

		receivingBillEntryInfo.setReceivingBill(receivingBillInfo);
		receivingBillInfo.getEntries().add(receivingBillEntryInfo);
    }
    
    // ���˵� --> �տ�MAP
    protected void getShoukuanMapFromDuizhang (Context ctx,ResultSet rs) throws Exception{
    	//*********��ͷ*************
    	//��˾
		String billcompany = rs.getString("companyname");
		//ҵ�񵥾ݱ���
		String billNo = rs.getString("billno");
		//��������
		Date billdate = rs.getDate("billdate");
		//�շѷ�ʽ����
		String billrectypeno = rs.getString("billrectypeno");
		//�շѷ�ʽ����
		String billrectypename = rs.getString("billrectypename");
		//������   ������
		String billaccount = rs.getString("billrecordno");
		//ҵ������
		Date billbizdate = rs.getDate("billdate");
		//�տ�(Ӧ��)�ܽ��
		BigDecimal billtatalpay = rs.getBigDecimal("billrecamount");
		//ʵ���տ��ܽ��
		BigDecimal billtatalactualpay = rs.getBigDecimal("billactualrec");
		//������
		BigDecimal billfee = rs.getBigDecimal("billfee");
		//����������
		BigDecimal instalmentFee = rs.getBigDecimal("instalmentFee");
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String billbizdateStr = sdf.format(billbizdate);
		int year = Integer.parseInt(billbizdateStr.substring(0,4));
		int month = Integer.parseInt(billbizdateStr.substring(5,7));
		//*********��¼*************
		//*********����*************
		ReceivingBillInfo receivingBillInfo = null;
		CustomerInfo customerInfo = getCustomerInfoF7(ctx,billaccount);
		OrgmapInfo orgmapInfo = getOrgmapInfoF7(ctx, billcompany);
		CompanyOrgUnitInfo companyInfo =  orgmapInfo.getOrg();
		if(this.shoukuanMap.get(billNo) == null ){
			receivingBillInfo = new ReceivingBillInfo(); 
//			receivingBillInfo.setrecBillClaim	�տ�������Ϣ	����	FRecBillClaim
//			receivingBillInfo.setbillClaimStatus	��������״̬	ҵ��ö��	FBillClaimStatus	��	10
			receivingBillInfo.setBillClaimStatus(BillClaimStatusEnum.getEnum(BillClaimStatusEnum.UNCLAIM_VALUE));
//			receivingBillInfo.setisSmart	�Ƿ����ܺ���	ҵ��ö��	FIsSmart	��	1
			receivingBillInfo.setIsSmart(SmartType.getEnum(SmartType.OPEN_VALUE));
//			receivingBillInfo.setprofitCenter	��������	����	FProfitCenterID		
//			receivingBillInfo.setisHasRefundPay	�Ƿ��Ѿ������˿�֧������	����	FisHasRefundPay	
			receivingBillInfo.setIsHasRefundPay(false);
//			receivingBillInfo.setisRefundmentPay	�Ƿ��˿�֧��	����	FisRefundmentPay		
			receivingBillInfo.setIsRefundmentPay(false);
//			receivingBillInfo.setbankCheckStatus	����δ��״̬	ҵ��ö��	FBankCheckStatus	
//			receivingBillInfo.setprintCount	��ӡ����	����	FPrintCount	��	1
			receivingBillInfo.setPrintCount(1);
//			receivingBillInfo.setpaymentType	���ʽ	����	FPaymentTypeID	��	
			receivingBillInfo.setPaymentType(getPaymentTypeInfoF7(ctx,"����"));
//			receivingBillInfo.setSYNBillEntryID	Эͬ���ݷ�¼ID	�ַ���	FSYNBillEntryID		
//			receivingBillInfo.setSYNBillID	Эͬ����ID	�ַ���	FSYNBillID		
//			receivingBillInfo.setSYNbillNumber	Эͬ���ݱ��	�ַ���	FSYNbillNumber		
//			receivingBillInfo.setSYNbillType	Эͬ��������	�ַ���	FSYNbillType		
//			receivingBillInfo.setoppBgItemId	����Ԥ����ĿId	�ַ���	FOppBgItemId		
//			receivingBillInfo.setoppBgItemNumber	����Ԥ����Ŀ����	�ַ���	FOppBgItemNumber		
//			receivingBillInfo.setoppBgItemName	����Ԥ����Ŀ����	�ַ���	FOppBgItemName		
//			receivingBillInfo.setpayAccountBank	ʵ�������˺�	�ַ���	FPayAccountBank		
//			receivingBillInfo.setoppFpItem	�����ƻ���Ŀ	����	FOppFpItemID		
//			receivingBillInfo.setisReverseLockAmount	�Ƿ�д�������	����	FIsReverseLockAmount	��	1
			receivingBillInfo.setIsReverseLockAmount(true);
//			receivingBillInfo.setlastPayerType	ԭ����������	����	FLastPayerTypeID		
//			receivingBillInfo.setlastPayerName	ԭ����������	�ַ���	FLastPayerName		
//			receivingBillInfo.setlastPayerNumber	ԭ���������	�ַ���	FLastPayerNumber		
//			receivingBillInfo.setlastPayerID	ԭ������ID	�ַ���	FLastPayerID		
//			receivingBillInfo.setisPreReturn	���ݷ�¼�տ������Ƿ���Ԥ�տ������Ԥ�տ�	����	FIsPreReturn		
//			receivingBillInfo.setisProxyReturn	���ݷ�¼�տ������Ƿ��д��տ�����˴��տ�	����	FIsProxyReturn		
//			receivingBillInfo.setisSaleReturn	��¼�տ������Ƿ������ۻؿ���������ۻؿ�	����	FIsSaleReturn		
//			receivingBillInfo.setrealRecCompany	ʵ���տ˾	����	FRealRecCompanyID		
//			receivingBillInfo.setrealRecBillID	�����տ�ʵ�ʸ��˾	�ַ���	FRealRecBillID		
//			receivingBillInfo.setsrcRealRecBillID	�����տ�Դ��ID	�ַ���	FSrcRealRecBillID		
//			receivingBillInfo.setreceivingBillType	�տ����	ҵ��ö��	FReceivingBillType	��	1
			receivingBillInfo.setReceivingBillType(CasRecPayBillTypeEnum.getEnum(CasRecPayBillTypeEnum.COMMONTYPE_VALUE));
//			receivingBillInfo.setassItems	�Է���Ŀ������Ŀ	����			
//			receivingBillInfo.setpayerAccountBankO	���и����˻�	����	FPayerAccountBankID		
//			receivingBillInfo.setisRelateRecBook	�Ƿ��ѹ�������Ӧ��Ʊ��	����	FIsRelateRecBook		
//			receivingBillInfo.setrecBillType	�տ�����	����	FRecBillTypeID	��	
			receivingBillInfo.setRecBillType(getReceivingBillTypeInfoF7(ctx,"Ԥ�տ�"));
//			receivingBillInfo.setbgAmount	Ԥ���׼���	����	FBgAmount		
//			receivingBillInfo.setdeliver	��Ʊ��	����	FDeliverID		
//			receivingBillInfo.setoppInnerAcct	�ڲ������˻�	����	FOppInnerAcctID		
//			receivingBillInfo.setreceipt	�������˵�	����	FReceiptId		
//			receivingBillInfo.setisRelateReceipt	�Ƿ��ѹ������˵�	����	FIsRelateReceipt		
//			receivingBillInfo.setrecDate	�տ�����	����	FRecDate	
			receivingBillInfo.setRecDate(billbizdate);
//			receivingBillInfo.setentries	�տ��¼	����			
//			receivingBillInfo.setpayerAccountBank	�����˺�	�ַ���	FPayerAccountBank		
//			receivingBillInfo.setpayerBank	��������	�ַ���	FPayerAccountNumber		
//			receivingBillInfo.setpayerName	����������	�ַ���	FPayerName		
			receivingBillInfo.setPayerName(customerInfo.getName());
//			receivingBillInfo.setpayerNumber	���������	�ַ���	FPayerNumber	
			receivingBillInfo.setPayerNumber(customerInfo.getNumber());
//			receivingBillInfo.setpayerID	������ID	�ַ���	FPayerID	
			receivingBillInfo.setPayerID(customerInfo.getId().toString());
//			receivingBillInfo.setpayerType	����������	����	FPayerTypeID	��	
			receivingBillInfo.setPayerType(getAsstActTypeInfoF7(ctx,"�ͻ�"));
//			receivingBillInfo.setpayeeAccount	�տ��Ŀ	����	FPayeeAccountID	��	
//			receivingBillInfo.setpayeeAccountBank	�տ��˻�	����	FPayeeAccountBankID		
//			receivingBillInfo.setpayeeBank	�տ�����	����	FPayeeBankID		
//			receivingBillInfo.setactRecLocAmtVc	ʵ�ձ�λ�ҽ���ۼƺ���	����	FActRecLocAmtVc		
//			receivingBillInfo.setactRecLocAmt	ʵ�ձ�λ�ҽ��ϼ�	����	FActRecLocAmt	��	
			receivingBillInfo.setActRecLocAmt(billtatalactualpay);
//			receivingBillInfo.setactRecAmtVc	ʵ�ս���ۼƺ���	����	FActRecAmtVc		
//			receivingBillInfo.setactRecAmt	ʵ�ս��ϼ�	����	FActRecAmt	��	
			receivingBillInfo.setActRecAmt(billtatalactualpay);
//			receivingBillInfo.setrecType	�տ����ͣ����ڿ�ʼ���ã�	ҵ��ö��	FRecType		
//			receivingBillInfo.setverifyStatus	����״̬	ҵ��ö��	FverifyStatus	��	1
			receivingBillInfo.setVerifyStatus(com.kingdee.eas.fi.cas.verifyStatusEnum.getEnum(com.kingdee.eas.fi.cas.verifyStatusEnum.SOME_VERIFIED_VALUE));
//			receivingBillInfo.setisPreVerify	�Ƿ�Ӧ����Ԥ������ס�ĵ�	����	FIsPreVerify	
			receivingBillInfo.setIsPreVerify(false);
//			receivingBillInfo.setisDiffCurSettlement	����ֹ�����ʶ	����	FIsDiffCurSettlement		
//			receivingBillInfo.setbgCtrlAmt	Ԥ����ƽ��	����	FBgCtrlAmt	��	
			receivingBillInfo.setBgCtrlAmt(billtatalactualpay);
//			receivingBillInfo.setbankCheckFlag	���˱�ʶ��-�������ܶ��˷�д	�ַ���	FBankCheckFlag		
//			receivingBillInfo.setmbgName	����ά������	�ַ���	FMbgname		
//			receivingBillInfo.setmbgNumber	����ά�ȱ���	�ַ���	FMbgnumber		
//			receivingBillInfo.setfundFlowItem	�ʽ�������Ŀ	����	FFundFlowItemID		
//			receivingBillInfo.setpcaVouchered	�Ƿ�������������ƾ֤	����	FpcaVouchered		
//			receivingBillInfo.setcostCenter	�ɱ�����	����	FCostCenterID		
//			receivingBillInfo.setsubSettDate	�ύ������������	����	FSubSettDate		
//			receivingBillInfo.setoutBgItemName	����Ԥ����Ŀ����	�ַ���	FOutBgItemName		
//			receivingBillInfo.setoutBgItemNumber	����Ԥ����Ŀ����	�ַ���	FOutBgItemNumber		
//			receivingBillInfo.setoutBgItemId	����Ԥ����ĿID	�ַ���	FOutBgItemID		
//			receivingBillInfo.setcontractNum	��ͬ���	�ַ���	FContractNumber		
//			receivingBillInfo.setisCoopBuild	�Ƿ�Эͬ����	����	FIsCoopBuild		
//			receivingBillInfo.setBillDate_SourceBill	��Դ������	����	FBillDate_SourceBill		
//			receivingBillInfo.setAsstActTypeID_SourceBill	��Դ����������	�ַ���	FAsstActTypeID_SourceBill		
//			receivingBillInfo.setAsstActID_SourceBill	��Դ��������	�ַ���	FAsstActID_SourceBill		
//			receivingBillInfo.setPersonID_SourceBill	��Դ����Ա	�ַ���	FPersonID_SourceBill		
//			receivingBillInfo.setAdminOrgUnitId_SourceBill	��Դ������	�ַ���	FAdminOrgUnitId_SourceBill		
//			receivingBillInfo.setisImpFromGL	�Ƿ���������������	����	FIsImpFromGL		
//			receivingBillInfo.setmixEntryVerify	����ո������ͷ�¼�Ƿ������Ӧ��������	����	FMixEntryVerify		
//			receivingBillInfo.setisAppointVoucher	�Ƿ�������ָ��ƾ֤	����	FIsAppointVoucher		
//			receivingBillInfo.setaccepterDate	���㵥��������	����	FAccepterDate		
//			receivingBillInfo.setaccepter	���㵥������	�ַ���	FAccepter		
//			receivingBillInfo.setapprover	һ�������	����	FApproverID		
//			receivingBillInfo.setapproveDate	һ���������	����	FApproveDate		
//			receivingBillInfo.setunVerifiedAmtLoc	δ�����λ�Һϼ�	����	FUnVerifiedAmtLoc	��	
			receivingBillInfo.setUnVerifiedAmtLoc(billtatalactualpay);
//			receivingBillInfo.setverifiedAmtLoc	�ѽ����λ�Һϼ�	����	FVerifiedAmtLoc		
			receivingBillInfo.setVerifiedAmtLoc(BigDecimal.ZERO);
//			receivingBillInfo.setunVerifiedAmt	δ������ϼ�	����	FUnVerifiedAmt	��	
			receivingBillInfo.setUnVerifiedAmt(billtatalactualpay);
//			receivingBillInfo.setverifiedAmt	�ѽ�����ϼ�	����	FVerifiedAmt		
			receivingBillInfo.setVerifiedAmt(BigDecimal.ZERO);
//			receivingBillInfo.setvoucherNumber	ƾ֤���	�ַ���	FVoucherNumber		
//			receivingBillInfo.setvoucher	ƾ֤ID	����	FVoucherID		
//			receivingBillInfo.setvoucherType	ƾ֤��	����	FVoucherTypeID		
//			receivingBillInfo.setisNeedVoucher	���˷�ʽ	����	FIsNeedVoucher	��	1
			receivingBillInfo.setIsNeedVoucher(true);
//			receivingBillInfo.setisTransOtherBill	�Ƿ�תԤ��תԤ��	����	FIsTransOtherBill		
			receivingBillInfo.setIsTransOtherBill(false);
//			receivingBillInfo.setisCtrlOppAcct	���ƶԷ���Ŀ	����	FIsCtrlOppAcct		
//			receivingBillInfo.setfeeType	�ո����	����	FFeeTypeID		
//			receivingBillInfo.setisTransBill	�Ƿ�ת������	����	FIsTransBill		
//			receivingBillInfo.setisRedBill	�Ƿ��Ǻ��ֵ���	����	FIsRedBill
			receivingBillInfo.setIsRedBill(false);
//			receivingBillInfo.setbizType	ҵ������	����	FBizTypeID		
//			receivingBillInfo.setisBookRL	�Ƿ�Ǽ������ռ���	����	FIsBookRL		
//			receivingBillInfo.setprojectManager	��Ŀ����	����	FProjectManagerID		
//			receivingBillInfo.setproject	��Ŀ	����	FProjectID		
//			receivingBillInfo.setcontractBillId	��ͬId	�ַ���	FContractBillID		
//			receivingBillInfo.setcapitalAmount	��д���	�ַ���	FCapitalAmount		
//			receivingBillInfo.setdayaccount	�ռ������	����	FDayaccount		
//			receivingBillInfo.setcontractNo	��ͬ���	�ַ���	FContractNo		
//			receivingBillInfo.setsummary	����˵��	�ַ���	FSummary		
//			receivingBillInfo.setconceit	������	�ַ���	FConceit		
//			receivingBillInfo.setaccessoryAmt	������	����	FAccessoryAmt		
//			receivingBillInfo.setperson	��Ա	����	FPersonId		
//			receivingBillInfo.setadminOrgUnit	����	����	FAdminOrgUnitId		
//			receivingBillInfo.setlocalAmt	Ӧ�գ�������λ�ҽ��	����	FLocalAmount	��	
			receivingBillInfo.setLocalAmt(billtatalactualpay);
//			receivingBillInfo.setamount	Ӧ�գ��������	����	FAmount	��	
			receivingBillInfo.setAmount(billtatalactualpay);
//			receivingBillInfo.setisImport	�Ƿ���	����	FIsImport	
			receivingBillInfo.setIsImport(false);
//			receivingBillInfo.setfundType	�ո��ʽ	ҵ��ö��	FFundType	
			receivingBillInfo.setFundType(BizTypeEnum.getEnum(BizTypeEnum.CASH_VALUE));
//			receivingBillInfo.setsettlementStatus	���н���״̬	ҵ��ö��	FSettlementStatus	��	10
			receivingBillInfo.setSettlementStatus(SettlementStatusEnum.getEnum(SettlementStatusEnum.UNSUBMIT_VALUE));
//			receivingBillInfo.setbillStatus	����״̬	ҵ��ö��	FBillStatus	��	10
			receivingBillInfo.setBillStatus(com.kingdee.eas.fi.cas.BillStatusEnum.getEnum(com.kingdee.eas.fi.cas.BillStatusEnum.AUDITED_VALUE));
//			receivingBillInfo.setfiVouchered	�Ƿ�������ƾ֤	����	FFiVouchered		
//			receivingBillInfo.setisInitializeBill	�Ƿ��ʼ������	����	FIsInitializeBill		
//			receivingBillInfo.setaccountant	���	����	FAccountantID		
//			receivingBillInfo.setcashier	����	����	FCashierID		
//			receivingBillInfo.setauditDate	�������	����	FAuditDate		
//			receivingBillInfo.setsettleBizType	��������	ҵ��ö��	FSettleBizType		
//			receivingBillInfo.setisCommitSettle	�Ƿ��ύ��������	����	FIsCommitSettle		
			receivingBillInfo.setIsCommitSettle(false);
//			receivingBillInfo.setfpItem	�ƻ���Ŀ	����	FFpItemID		
//			receivingBillInfo.setoppAccount	�Է���Ŀ	����	FOppAccountID		
//			receivingBillInfo.setsettlementNumber	�����	�ַ���	FSettlementNumber		
//			receivingBillInfo.setsettlementType	���㷽ʽ	����	FSettlementTypeID	��
			receivingBillInfo.setSettlementType(getSettlementTypeInfoF7(ctx,"�ֽ�"));
//			receivingBillInfo.setlastExhangeRate	���������	����	FLastExhangeRate
			receivingBillInfo.setLastExhangeRate(BigDecimal.ONE);
//			receivingBillInfo.setisExchanged	�Ƿ��Ѿ�����	����	FIsExchanged
			receivingBillInfo.setIsExchanged(false);
//			receivingBillInfo.setexchangeRate	����	����	FExchangeRate	��	1
			receivingBillInfo.setExchangeRate(BigDecimal.ONE);
//			receivingBillInfo.setcurrency	�ұ�	����	FCurrencyID	��	dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC
			receivingBillInfo.setCurrency(getCurrencyInfoF7(ctx,"BB01"));
//			receivingBillInfo.setsourceType	ҵ��ϵͳ	ҵ��ö��	FSourceType	��	100
			receivingBillInfo.setSourceType(SourceTypeEnum.getEnum(SourceTypeEnum.AR_VALUE));
//			receivingBillInfo.setsourceSysType	��Դϵͳ	ҵ��ö��	FSourceSysType	��	100
			receivingBillInfo.setSourceSysType(SourceTypeEnum.getEnum(SourceTypeEnum.AR_VALUE));
//			receivingBillInfo.setcompany	��˾	����	FCompanyID	��	
			receivingBillInfo.setCompany(companyInfo);
//			receivingBillInfo.setsourceFunction	��Դ����	�ַ���	FSourceFunction		
//			receivingBillInfo.setsourceBillId	ԭʼ����ID	�ַ���	FSourceBillID		
//			receivingBillInfo.setauditor	�����	����	FAuditorID		
//			receivingBillInfo.sethasEffected	�Ƿ�������Ч	����	FHasEffected		
//			receivingBillInfo.setdescription	�ο���Ϣ	�ַ���	FDescription		
//			receivingBillInfo.sethandler	������	����	FHandlerID		
//			receivingBillInfo.setbizDate	ҵ������	����	FBizDate	��	
			receivingBillInfo.setBizDate(billbizdate);
//			receivingBillInfo.setnumber	���ݱ��	�ַ���	FNumber	��	
//			receivingBillInfo.setCU	���Ƶ�Ԫ	����	FControlUnitID	��	
			receivingBillInfo.setCU(companyInfo.getCU());
//			receivingBillInfo.setlastUpdateTime	����޸�ʱ��	����	FLastUpdateTime		
//			receivingBillInfo.setlastUpdateUser	����޸���	����	FLastUpdateUserID		
//			receivingBillInfo.setcreateTime	����ʱ��	����	FCreateTime	��	
			receivingBillInfo.setCreateTime(new Timestamp(billbizdate.getTime()));
//			receivingBillInfo.setcreator	������	����	FCreatorID	��	
//			receivingBillInfo.setid	ID	BOSUUID	FID	��	
			this.shoukuanMap.put(billNo, receivingBillInfo);
		}else{
			receivingBillInfo = (ReceivingBillInfo) this.shoukuanMap.get(billNo);
		}
		ReceivingBillEntryInfo receivingBillEntryInfo = new ReceivingBillEntryInfo();
//		receivingBillEntryInfo.setarPrintBillEntry	Ӧ���嵥��¼	�ַ���	FArPrintBillEntryId		
//		receivingBillEntryInfo.setarPrintBill	Ӧ���嵥	�ַ���	FArPrintBillId		
//		receivingBillEntryInfo.setoppBgItemName	����Ԥ����Ŀ����	�ַ���	FOppBgItemName		
//		receivingBillEntryInfo.setoppBgItemNumber	����Ԥ����Ŀ����	�ַ���	FOppBgItemNumber		
//		receivingBillEntryInfo.setoppBgItemId	����Ԥ����ĿId	�ַ���	FOppBgItemId		
//		receivingBillEntryInfo.setmatchedAmountLoc	matchedAmountLoc	����	FMatchedAmountLoc		
//		receivingBillEntryInfo.setmatchedAmount	��ƥ����	����	FMatchedAmount		
//		receivingBillEntryInfo.setrecBillType	��¼�տ�����	����	FRecBillTypeID	��	
		receivingBillEntryInfo.setRecBillType(getReceivingBillTypeInfoF7(ctx,"Ԥ�տ�"));
//		receivingBillEntryInfo.setassItemsEntries	������Ŀ���	����			
//		receivingBillEntryInfo.setexpenseType	������Ŀ	����	FExpenseTypeID		
//		receivingBillEntryInfo.setreceiptID	���������վݵ�ID	BOSUUID	FReceiptID		
//		receivingBillEntryInfo.setreceiptNumber	�վݺ�	�ַ���	FReceiptNumber		
//		receivingBillEntryInfo.setcustomerBillNum	�ͷ����ݺ�	�ַ���	FCustomerBillNum		
//		receivingBillEntryInfo.setbizBillNumber	ҵ�񵥾ݺ�	�ַ���	FBizBillNumber		
//		receivingBillEntryInfo.setbizDate	��������	����	FBizDate		
		receivingBillEntryInfo.setBizDate(billbizdate);
//		receivingBillEntryInfo.setreceivingBill	�տͷ	����	FReceivingBillID	��	
//		receivingBillEntryInfo.setbgCtrlAmt	Ԥ����ƽ��	����	FBgCtrlAmt	��	
		receivingBillEntryInfo.setBgCtrlAmt(billtatalactualpay);
//		receivingBillEntryInfo.setmbgName	����ά������	�ַ���	FMbgName		
//		receivingBillEntryInfo.setmbgNumber	����ά�ȱ���	�ַ���	FMbgNumber		
//		receivingBillEntryInfo.setfundFlowItem	�ʽ�������Ŀ	����	FFundFlowItemID		
//		receivingBillEntryInfo.settrackNumber	���ٺ�	����	FTrackNumberID		
//		receivingBillEntryInfo.setproject	��Ŀ��	����	FProjectID		
//		receivingBillEntryInfo.setoutBgItemName	����Ԥ����Ŀ����	�ַ���	FOutBgItemName		
//		receivingBillEntryInfo.setoutBgItemNumber	����Ԥ����Ŀ����	�ַ���	FOutBgItemNumber		
//		receivingBillEntryInfo.setoutBgItemId	����Ԥ����ĿID 	�ַ���	FOutBgItemId		
//		receivingBillEntryInfo.setcostCenter	�ɱ�����	����	FCostCenterID		
//		receivingBillEntryInfo.setcontractEntryID	��ͬ���ݷ�¼ID	�ַ���	FContractEntryID		
//		receivingBillEntryInfo.setcontractBillID	��ͬ����ID	�ַ���	FContractBillID		
//		receivingBillEntryInfo.setcontractEntrySeq	��ͬ�к�	����	FContractEntrySeq		
//		receivingBillEntryInfo.setcontractNum	��ͬ���	�ַ���	FContractNumber		
//		receivingBillEntryInfo.setotherBillTransAsstTypeId	תԤ��תԤ������������	����	FOtherBillTransAsstTypeId		
//		receivingBillEntryInfo.setfpItem	�ƻ���Ŀ	����	FFpItemID		
//		receivingBillEntryInfo.setoppAccount	�Է���Ŀ	����	FOppAccountID		
//		receivingBillEntryInfo.setsourceBillAsstActID	Դ��������id	�ַ���	FSourceBillAsstActID		
//		receivingBillEntryInfo.setcurrency	�ұ�	����	FCurrencyId	
		receivingBillEntryInfo.setCurrency(getCurrencyInfoF7(ctx,"BB01"));
//		receivingBillEntryInfo.settrackNumbet	���ٺ�	�ַ���	FTrackNumber		
//		receivingBillEntryInfo.setcoreBillEntrySeq	���ĵ��ݷ�¼�к�	����	FCoreBillEntrySeq		
//		receivingBillEntryInfo.setcoreBillNumber	���ĵ��ݺ�	�ַ���	FCoreBillNumber		
//		receivingBillEntryInfo.setcoreBillEntryId	���ĵ��ݷ�¼Id	�ַ���	FCoreBillEntryId		
//		receivingBillEntryInfo.setcoreBillId	���ĵ���Id	�ַ���	FCoreBillId		
//		receivingBillEntryInfo.setcoreBillType	���ĵ�������	����	FCoreBillTypeId		
//		receivingBillEntryInfo.sethisUnVcLocAmount	��ʷδ������λ��	����	FHisUnVcLocAmount		
//		receivingBillEntryInfo.sethisUnVcAmount	��ʷδ�������	����	FHisUnVcAmount		
//		receivingBillEntryInfo.setvcStatus	����״̬	ҵ��ö��	FVcStatus	��	
//		receivingBillEntryInfo.setsourceBillEntryId	Դ���ݷ�¼ID	�ַ���	FSourceBillEntryId		
//		receivingBillEntryInfo.setsourceBillId	Դ����ID	�ַ���	FSourceBillId		
//		receivingBillEntryInfo.setunLockLocAmt	δ������λ�ҽ��	����	FUnLockLocAmt	��	
		receivingBillEntryInfo.setUnLockLocAmt(BigDecimal.ZERO);
//		receivingBillEntryInfo.setunLockAmt	δ�������	����	FUnLockAmt	��	
		receivingBillEntryInfo.setUnLockAmt(BigDecimal.ZERO);
//		receivingBillEntryInfo.setlockLocAmt	������λ�ҽ��	����	FLockLocAmt		
		receivingBillEntryInfo.setLockLocAmt(billtatalactualpay);
//		receivingBillEntryInfo.setlockAmt	�������	����	FLockAmt	
		receivingBillEntryInfo.setLockAmt(billtatalactualpay);
//		receivingBillEntryInfo.setremark	��ע	�ַ���	FRemark		
//		receivingBillEntryInfo.setactualLocAmtVc	ʵ�գ�������λ�ҽ���ۼƺ���	����	FActualLocAmtVc		
		receivingBillEntryInfo.setActualLocAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setactualLocAmt	ʵ�գ�������λ�ҽ��	����	FActualLocAmt	��	
		receivingBillEntryInfo.setActualLocAmt(billtatalactualpay);
//		receivingBillEntryInfo.setactualAmtVc	ʵ�գ���������ۼƺ���	����	FActualAmtVc	
		receivingBillEntryInfo.setActualAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setactualAmt	ʵ�գ��������	����	FActualAmt	��
		receivingBillEntryInfo.setActualAmt(billtatalactualpay);
//		receivingBillEntryInfo.setrebateLocAmtVc	�ۿ۱�λ�ҽ���ۼƺ���	����	FRebateLocAmtVc	
		receivingBillEntryInfo.setRebateLocAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrebateLocAmt	�ۿ۱�λ�ҽ��	����	FRebateLocAmt	
		receivingBillEntryInfo.setRebateLocAmt(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrebateAmtVc	�ۿ۽���ۼƺ���	����	FRebateAmtVc
		receivingBillEntryInfo.setRebateAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrebate	�ֽ��ۿ�	����	FRebate	
		receivingBillEntryInfo.setRebate(BigDecimal.ZERO);
//		receivingBillEntryInfo.setunVerifyExgRateLoc	δ������㱾λ�ҽ��	����	FUnVerifyExgRateLoc		
		receivingBillEntryInfo.setUnVerifyExgRateLoc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setunVcLocAmount	Ӧ�գ�����δ������λ�ҽ��	����	FUnVcLocAmount	��	
		receivingBillEntryInfo.setUnVcLocAmount(billtatalpay);
//		receivingBillEntryInfo.setunVcAmount	Ӧ�գ�����δ�������	����	FUnVcAmount	��	
		receivingBillEntryInfo.setUnVcAmount(billtatalpay);
//		receivingBillEntryInfo.setlocalAmtVc	Ӧ�գ�������λ���ۼƺ���	����	FLocalAmtVc	
		receivingBillEntryInfo.setLocalAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setlocalAmt	Ӧ�գ�������λ�ҽ��	����	FLocalAmount	��	
		receivingBillEntryInfo.setLocalAmt(billtatalpay);
//		receivingBillEntryInfo.setamountVc	Ӧ�գ���������ۼƺ���	����	FAmountVc	
		receivingBillEntryInfo.setAmountVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setamount	Ӧ�գ��������	����	FAmount	��	
		receivingBillEntryInfo.setAmount(billtatalpay);
//		receivingBillEntryInfo.setseq	���ݷ�¼���к�	����	FSeq		
//		receivingBillEntryInfo.setid	ID	BOSUUID	FID		

		receivingBillEntryInfo.setReceivingBill(receivingBillInfo);
		receivingBillInfo.getEntries().add(receivingBillEntryInfo);
    }
    
    // ���˵� --> Ӧ��MAP
    protected void getYingfuMapFromDuizhang (Context ctx,ResultSet rs) throws Exception{
    	//*********��ͷ*************
    	//��˾
		String billcompany = rs.getString("companyname");
		//ҵ�񵥾ݱ���
		String billNo = rs.getString("billno");
		//��������
		Date billdate = rs.getDate("billdate");
		//�շѷ�ʽ����
		String billrectypeno = rs.getString("billrectypeno");
		//�շѷ�ʽ����
		String billrectypename = rs.getString("billrectypename");
		//������   ������
		String billaccount = rs.getString("billrecordno");
		//ҵ������
		Date billbizdate = rs.getDate("billdate");
		//�տ�(Ӧ��)�ܽ��
		BigDecimal billtatalpay = rs.getBigDecimal("billrecamount");
		//ʵ���տ��ܽ��
		BigDecimal billtatalactualpay = rs.getBigDecimal("billactualrec");
		//������
		BigDecimal billfee = rs.getBigDecimal("billfee");
		//����������
		BigDecimal instalmentFee = rs.getBigDecimal("instalmentFee");
		//�����ѿͻ�
		String otherCustomerName = rs.getString("othcustomername");
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String billbizdateStr = sdf.format(billbizdate);
		int year = Integer.parseInt(billbizdateStr.substring(0,4));
		int month = Integer.parseInt(billbizdateStr.substring(5,7));
		//*********��¼*************
		//*********����*************
		com.kingdee.eas.fi.ap.OtherBillInfo otherBillInfo = null;
		CustomerInfo customerInfo = getCustomerInfoF7FromName(ctx,otherCustomerName);
		OrgmapInfo orgmapInfo = getOrgmapInfoF7(ctx, billcompany);
		CompanyOrgUnitInfo companyInfo =  orgmapInfo.getOrg();
		if(this.yingfuMap.get(billNo) == null ){
			otherBillInfo = new com.kingdee.eas.fi.ap.OtherBillInfo(); 
//    		otherBillInfo.setinvoiceCode	��Ʊ����	�ַ���	FInvoiceCode	0
//    		otherBillInfo.setinvoiceNumber	��Ʊ����	�ַ���	FInvoiceNumber	0
//    		otherBillInfo.setinvoiceType	��Ʊ����	ҵ��ö��	FInvoiceType	-1
//    		otherBillInfo.setisMatchGenerate	��Ʊƥ��	����	FIsMatchGenerate	0
//    		otherBillInfo.setprePayBillEntryIDs	Ԥ������¼ID��	�ַ���	FPrePayBillEntryIDs	0
//    		otherBillInfo.setprePayAmountString	Ԥ���������ϸ	�ַ���	FPrePayAmountString	0
//    		otherBillInfo.setthisApAmount	����Ӧ�����	����	FthisApAmount	88
    		otherBillInfo.setThisApAmount(billfee);
//    		otherBillInfo.setprePayAmount	Ԥ�����	����	FPrePayAmount	0
//    		otherBillInfo.setprePayBillNumber	Ԥ�����ţ���֧�ֶ�ѡ��	�ַ���	FPrePayBillNumber	0
//    		otherBillInfo.setpayerAmountLoc	�����λ��	����	FPayerAmountLoc	88
    		otherBillInfo.setPayerAmountLoc(billfee);
//    		otherBillInfo.setpayerAmount	������	����	FPayerAmount	88
    		otherBillInfo.setPayerAmount(billfee);
//    		otherBillInfo.setpayerExchangeRate	�������	����	FPayerExchangeRate	1
    		otherBillInfo.setPayerExchangeRate(BigDecimal.ONE);
//    		otherBillInfo.setpayerCurrency	����ұ�	����	FPayerCurrencyID	dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC
    		otherBillInfo.setPayerCurrency(getCurrencyInfoF7(ctx, "BB01"));
//    		otherBillInfo.setrecAccountBank	�տ��˻�	�ַ���	FRecAccountBank	11050161620000000011
//    		otherBillInfo.setrecBank	�տ�����	�ַ���	FRecBank	�й��������б�����Դ֧��
//    		otherBillInfo.setpayerAccountBank	�����˻�	����	FPayerAccountBankID	0
//    		otherBillInfo.setpayerBank	��������	����	FPayerBankID	0
//    		otherBillInfo.setisCreatedByApElecInvoice	��Ʊƥ��	����	FIsCreatedByApElecInvoice	0
//    		otherBillInfo.setprofitCenter	��������	����	FProfitCenterID	0
//    		otherBillInfo.setbillType_SourceBill	��Դ����������	ҵ��ö��	FBillType_SourceBill	0
//    		otherBillInfo.setpurchaseGroup	�ɹ���	����	FPurchaseGroupID	0
//    		otherBillInfo.setbillType	��������	ҵ��ö��	FBillType	201
    		otherBillInfo.setBillType(OtherBillType.getEnum(OtherBillType.OTHERPAY_VALUE));
//    		otherBillInfo.setpayPlan	����ƻ�	����		#N/A
//    		otherBillInfo.setexpApportion	��̯��ϸ	����		#N/A
//    		otherBillInfo.setentry	��¼	����		#N/A
//    		otherBillInfo.setpurOrg	�ɹ���֯	����	FPurOrgID	8i0AAAAATvzM567U
    		otherBillInfo.setPurOrg(getPurchaseOrgUnitInfoF7(ctx, billcompany));
//    		otherBillInfo.setverifyStatus	����״̬	ҵ��ö��	FverifyStatus	1
    		otherBillInfo.setVerifyStatus(verifyStatusEnum.getEnum(verifyStatusEnum.UNVERIFY_VALUE));
//    		otherBillInfo.setisDiffCurrency	����ֱ�ʶ	����	FIsDiffCurrency	0
//    		otherBillInfo.setpcaVouchered	�Ƿ�������������ƾ֤	����	FpcaVouchered	0
//    		otherBillInfo.settotalTaxLocal	˰�λ��	����	FTotalTaxLocal	0
//    		otherBillInfo.settotalAmountLocal	��λ��	����	FTotalAmountLocal	88
    		otherBillInfo.setTotalAmountLocal(billfee);
//    		otherBillInfo.setisTransPoint	�Ƿ�ת��ָ��	����	FIsTransPoint	0
//    		otherBillInfo.setisTransOtherBill	�Ƿ�תӦ��Ӧ��	����	FIsTransOtherBill	0
//    		otherBillInfo.setbillRelationOption	���������㷨	ҵ��ö��	FBillRelationOption	0
//    		otherBillInfo.setcostCenter	�ɱ�����	����	FCostCenterID	0
//    		otherBillInfo.setisBizBill	�Ƿ�ҵ��Ӧ��Ӧ������	����	FIsBizBill	0
//    		otherBillInfo.setcontractNum	��ͬ���	�ַ���	FContractNumber	0
//    		otherBillInfo.setisGenCoopBill	�Ƿ�����Эͬ����	����	FIsGenCoopBill	0
//    		otherBillInfo.setisCoopBuild	�Ƿ�Эͬ����	����	FIsCoopBuild	0
//    		otherBillInfo.setisSplitBill	�Ƿ�𵥵���	����	FIsSplitBill	0
//    		otherBillInfo.setAsstActTypeID_SourceBill	��Դ����������	�ַ���	FAsstActTypeID_SourceBill	0
//    		otherBillInfo.setBillDate_SourceBill	��Դ������	����	FBillDate_SourceBill	0
//    		otherBillInfo.setAsstActID_SourceBill	��Դ��������	�ַ���	FAsstActID_SourceBill	0
//    		otherBillInfo.setPersonID_SourceBill	��Դ����Ա	�ַ���	FPersonID_SourceBill	0
//    		otherBillInfo.setAdminOrgUnitId_SourceBill	��Դ������	�ַ���	FAdminOrgUnitId_SourceBill	0
//    		otherBillInfo.setisImpFromGL	�Ƿ���������������	����	FIsImpFromGL	0
//    		otherBillInfo.setpriceSource	�۸���Դ	ҵ��ö��	FPriceSource	101
    		otherBillInfo.setPriceSource(PriceSourceEnum.getEnum(PriceSourceEnum.PRICEFROMCOREBILL_VALUE));
//    		otherBillInfo.setisAppointVoucher	�Ƿ�������ָ��ƾ֤	����	FIsAppointVoucher	0
//    		otherBillInfo.setisNeedVoucher	���˷�ʽ	����	FIsNeedVoucher	1
    		otherBillInfo.setIsNeedVoucher(true);
//    		otherBillInfo.setisPriceWithoutTax	�Ƿ����˰	����	FIsPriceWithoutTax	1
    		otherBillInfo.setIsPriceWithoutTax(true);
//    		otherBillInfo.setisInTax	�Ƿ�˰	����	FIsInTax	1
    		otherBillInfo.setIsInTax(true);
//    		otherBillInfo.setisSCMBill	�Ƿ������ĵ���	����	FIsSCMBill	0
//    		otherBillInfo.setperiod	ҵ�����������ڼ�	����	FPeriod	11
    		otherBillInfo.setPeriod(month);
//    		otherBillInfo.setyear	ҵ�������������	����	FYear	2020
    		otherBillInfo.setYear(year);
//    		otherBillInfo.setredBlueType	�Ƿ���ַ�Ʊ	����	FRedBlueType	0
//    		otherBillInfo.setvoucherType	ƾ֤��	����	FVoucherTypeID	0
//    		otherBillInfo.setisInitializeBill	�Ƿ��ڳ�����	����	FIsInitializeBill	0
//    		otherBillInfo.setisExchanged	�Ƿ��Ѿ�����	����	FIsExchanged	0
//    		otherBillInfo.settotalTaxAmount	��˰�ϼƣ����ý��ϼƣ�	����	FTotalTaxAmount	0
//    		otherBillInfo.settotalTax	˰��ϼ�	����	FTotalTax	0
//    		otherBillInfo.settotalAmount	���ϼ�	����	FTotalAmount	88
    		otherBillInfo.setTotalAmount(billfee);
//    		otherBillInfo.setlastExhangeRate	���������	����	FLastExhangeRate	1
    		otherBillInfo.setLastExhangeRate(BigDecimal.ONE);
//    		otherBillInfo.setpayCondition	�գ�����������	����	FPayConditionId	0
//    		otherBillInfo.setpaymentType	���ʽ	����	FPaymentTypeID	2fa35444-5a23-43fb-99ee-6d4fa5f260da6BCA0AB5
    		otherBillInfo.setPaymentType(getPaymentTypeInfoF7(ctx,"�޹�"));
//    		otherBillInfo.setbizType	ҵ������	����	FBizTypeID	0
//    		otherBillInfo.setisImportBill	�Ƿ��ǵ��뵥��	����	FIsImportBill	0
//    		otherBillInfo.setisAllowanceBill	�Ƿ����õ���	����	FIsAllowanceBill	0
//    		otherBillInfo.setisTransBill	�Ƿ�ת�����ɵ���	����	FIsTransBill	0
//    		otherBillInfo.setaccountant	���	����	FAccountantID	0
//    		otherBillInfo.setisReverseBill	�Ƿ�Ϊ��������	����	FIsReverseBill	0
//    		otherBillInfo.setisReversed	�Ƿ��ѱ�����	����	FIsReversed	0
//    		otherBillInfo.setcashDiscount	�ֽ��ۿ�	����	FCashDiscountID	0
//    		otherBillInfo.setsourceBillType	��Դ��������	ҵ��ö��	FSourceBillType	-1
//    		otherBillInfo.setvoucherNumber	ƾ֤���	�ַ���	FVoucherNumber	0
//    		otherBillInfo.setvoucher	ƾ֤	����	FVoucherID	0
//    		otherBillInfo.setfiVouchered	�Ƿ�������ƾ֤	����	FFiVouchered	0
//    		otherBillInfo.setauditDate	�������	����	FAuditDate	0
//    		otherBillInfo.setunVerifyAmountLocal	δ���㱾λ�ҽ��	����	FUnVerifyAmountLocal	88
    		otherBillInfo.setUnVerifyAmountLocal(billfee);
//    		otherBillInfo.setunVerifyAmount	δ������	����	FUnVerifyAmount	88
    		otherBillInfo.setUnVerifyAmount(billfee);
//    		otherBillInfo.setverifyAmountLocal	�ѽ����λ��	����	FVerifyAmountLocal	0
//    		otherBillInfo.setverifyAmount	�ѽ�����	����	FVerifyAmount	0
//    		otherBillInfo.setabstractName	ժҪ	�ַ���	FAbstractName	0
    		otherBillInfo.setAbstractName(billrectypename);
//    		otherBillInfo.setamountLocal	Ӧ�գ�������λ��	����	FAmountLocal	88
    		otherBillInfo.setAmountLocal(billfee);
//    		otherBillInfo.setamount	Ӧ�գ��������	����	FAmount	88
    		otherBillInfo.setAmount(billfee);
//    		otherBillInfo.setsettleType	���㷽ʽ	����	FSettleTypeID	0
//    		otherBillInfo.setexchangeRate	����	����	FExchangeRate	1
    		otherBillInfo.setExchangeRate(BigDecimal.ONE);
//    		otherBillInfo.setperson	ҵ��Ա	����	FPersonId	0
//    		otherBillInfo.setadminOrgUnit	����	����	FAdminOrgUnitID	0
//    		otherBillInfo.setcurrency	�ұ�	����	FCurrencyID	dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC
    		otherBillInfo.setCurrency(getCurrencyInfoF7(ctx, "BB01"));
//    		otherBillInfo.setbillStatus	����״̬	ҵ��ö��	FBillStatus	1
    		otherBillInfo.setBillStatus(BillStatusEnum.getEnum(BillStatusEnum.SAVE_VALUE));
//    		otherBillInfo.setasstActName	����������	�ַ���	FAsstActName	#N/A  customerInfo
    		otherBillInfo.setAsstActName(customerInfo.getName());
//    		otherBillInfo.setasstActNumber	����������	�ַ���	FAsstActNumber	C002
    		otherBillInfo.setAsstActNumber(customerInfo.getNumber());
//    		otherBillInfo.setasstActID	������ID	�ַ���	FAsstActID	8i0AAAABDsC/DAQO
    		otherBillInfo.setAsstActID(customerInfo.getId().toString());
//    		otherBillInfo.setasstActType	��������	����	FAsstActTypeID	YW3xsAEJEADgAAUWwKgTB0c4VZA=
    		otherBillInfo.setAsstActType(getAsstActTypeInfoF7(ctx,"�ͻ�"));
//    		otherBillInfo.setbillDate	��������	����	FBillDate	2020-11-25
    		otherBillInfo.setBillDate(billdate);
//    		otherBillInfo.setcompany	��˾	����	FCompanyID	8i0AAAAATvzM567U
    		otherBillInfo.setCompany(companyInfo);
//    		otherBillInfo.setsourceFunction	��Դ����	�ַ���	FSourceFunction	0
//    		otherBillInfo.setsourceBillId	ԭʼ����ID	�ַ���	FSourceBillID	0
//    		otherBillInfo.setauditor	�����	����	FAuditorID	0
//    		otherBillInfo.sethasEffected	�Ƿ�������Ч	����	FHasEffected	0
//    		otherBillInfo.setdescription	�ο���Ϣ	�ַ���	FDescription	0
//    		otherBillInfo.sethandler	������	����	FHandlerID	0
//    		otherBillInfo.setbizDate	ҵ������	����	FBizDate	2020-11-25
    		otherBillInfo.setBizDate(billbizdate);
//    		otherBillInfo.setnumber	���ݱ��	�ַ���	FNumber	AP2020000009
//    		otherBillInfo.setCU	���Ƶ�Ԫ	����	FControlUnitID	00000000-0000-0000-0000-000000000000CCE7AED4
    		otherBillInfo.setCU(companyInfo.getCU());
//    		otherBillInfo.setlastUpdateTime	����޸�ʱ��	����	FLastUpdateTime	0
//    		otherBillInfo.setlastUpdateUser	����޸���	����	FLastUpdateUserID	0
//    		otherBillInfo.setcreateTime	����ʱ��	����	FCreateTime	2020-11-25 10:10:27
    		otherBillInfo.setCreateTime(new Timestamp(billdate.getTime()));
//    		otherBillInfo.setcreator	������	����	FCreatorID	256c221a-0106-1000-e000-10d7c0a813f413B7DE7F
//    		otherBillInfo.setid	ID	BOSUUID	FID	8i0AAAACKrZI2jpx

			this.yingfuMap.put(billNo, otherBillInfo);
		}else{
			otherBillInfo = (com.kingdee.eas.fi.ap.OtherBillInfo) this.shoukuanMap.get(billNo);
		}
		//��¼
		com.kingdee.eas.fi.ap.OtherBillentryInfo otherBillentryInfo = new com.kingdee.eas.fi.ap.OtherBillentryInfo();
//		otherBillentryInfo.setpayableDate	Ӧ������	����	FPayableDate	0
//		otherBillentryInfo.setrequestedPayAmt	�����븶����	����	FRequestedPayAmt	0
//		otherBillentryInfo.setunApportionAmount	δ��̯��λ�ҽ��	����	FUnApportionAmount	88
		otherBillentryInfo.setUnApportionAmount(billfee);
//		otherBillentryInfo.setapportionAmount	�ѷ�̯��λ�ҽ��	����	FApportionAmount	0
//		otherBillentryInfo.setisFullWriteOff	�Ƿ���ȫ����	����	FIsFullWriteOff	0
//		otherBillentryInfo.setapPrintBillEntry	Ӧ���嵥��¼	����	FApPrintBillEntryID	0
//		otherBillentryInfo.setapPrintBill	Ӧ���嵥	����	FApPrintBillID	0
//		otherBillentryInfo.sethead	����ͷ	����	FParentID	8i0AAAACKrZI2jpx
//		otherBillentryInfo.setunInvoiceReqAmountLocal	δ��Ʊ�����λ��	����	FUnInvoiceReqAmountLocal	88
		otherBillentryInfo.setUnInvoiceReqAmountLocal(billfee);
//		otherBillentryInfo.setunInvoiceReqAmount	δ��Ʊ������	����	FUnInvoiceReqAmount	88
		otherBillentryInfo.setUnInvoiceReqAmount(billfee);
//		otherBillentryInfo.setinvoiceReqAmountLocal	�ѿ�Ʊ�����λ��	����	FInvoiceReqAmountLocal	0
//		otherBillentryInfo.setinvoiceReqAmount	�ѿ�Ʊ������	����	FInvoiceReqAmount	0
//		otherBillentryInfo.setunInvoiceReqBaseQty	δ��Ʊ�����������	����	FUnInvoiceReqBaseQty	1
		otherBillentryInfo.setUnInvoiceReqBaseQty(BigDecimal.ONE);
//		otherBillentryInfo.setunInvoiceReqQty	δ��Ʊ��������	����	FUnInvoiceReqQty	1
		otherBillentryInfo.setUnInvoiceReqQty(BigDecimal.ONE);
//		otherBillentryInfo.setinvoiceReqBaseQty	�ѿ�Ʊ�����������	����	FInvoiceReqBaseQty	0
//		otherBillentryInfo.setinvoiceReqQty	�ѿ�Ʊ��������	����	FInvoiceReqQty	0
//		otherBillentryInfo.setisQtyZero	������	����	FIsQtyZero	0
//		otherBillentryInfo.setfundFlowItem	�ʽ�������Ŀ	����	FFundFlowItemID	0
//		otherBillentryInfo.setcostCenter	��¼�ɱ�����	����	FCostCenterID	0
//		otherBillentryInfo.setmaterialModel	����ͺ�	�ַ���	FMaterialModel	0
//		otherBillentryInfo.setlot	����	�ַ���	FLot	0
//		otherBillentryInfo.setbillDate	��������	����	FBillDate	2020-11-25
		otherBillentryInfo.setBillDate(billdate);
//		otherBillentryInfo.setcompany	��˾	�ַ���	FCompanyID	8i0AAAAATvzM567U
		otherBillentryInfo.setCompany(companyInfo.getId().toString());
//		otherBillentryInfo.setreversedBaseQty	�ѳ�ػ�������	����	FReversedBaseQty	0
//		otherBillentryInfo.settrackNumberzc	���ٺ�	����	FTrackNumberzcID	0
//		otherBillentryInfo.setproject	��Ŀ��	����	FProjectID	0
//		otherBillentryInfo.setrecSendOrgUnit	�ջ�����������֯	����	FRecSendOrgUnitID	0
//		otherBillentryInfo.setcontractEntryID	��ͬ���ݷ�¼ID	�ַ���	FContractEntryID	0
//		otherBillentryInfo.setcontractBillID	��ͬ����ID	�ַ���	FContractBillID	0
//		otherBillentryInfo.setcontractEntrySeq	��ͬ�к�	�ַ���	FContractEntrySeq	0
//		otherBillentryInfo.setcontractNum	��ͬ���	�ַ���	FContractNumber	0
//		otherBillentryInfo.setapportionAmtLocal	�ѷ�̯��λ��	����	FApportionAmtLocal	0
//		otherBillentryInfo.setlockVerifyQty	����������	����	FLockVerifyQty	0
//		otherBillentryInfo.setverifyQty	�ѽ�����	����	FVerifyQty	0
//		otherBillentryInfo.setisInvoiced	�Ƿ��ѿ���Ʊ	����	FIsInvoiced	0
//		otherBillentryInfo.setinvoicedAmt	�ѿ���Ʊ���	����	FInvoicedAmt	0
//		otherBillentryInfo.setinvoicedBaseQty	�ѿ���Ʊ��������	����	FInvoicedBaseQty	0
//		otherBillentryInfo.setinvoiceNumber	��Ʊ���	�ַ���	FInvoiceNumber	0
//		otherBillentryInfo.setlocalUnwriteOffAmount	δ������λ�ҽ��	����	FLocalUnwriteOffAmount	88
		otherBillentryInfo.setLocalUnwriteOffAmount(billfee);
//		otherBillentryInfo.setunwriteOffBaseQty	δ������������	����	FUnwriteOffBaseQty	1
		otherBillentryInfo.setUnwriteOffBaseQty(BigDecimal.ONE);
//		otherBillentryInfo.setlocalWrittenOffAmount	�Ѻ�����λ�ҽ��	����	FLocalWrittenOffAmount	0
//		otherBillentryInfo.setwittenOffBaseQty	�Ѻ�����������	����	FWrittenOffBaseQty	0
//		otherBillentryInfo.settrackNumber	�ı����ٺ�	�ַ���	FTrackNumber	0
//		otherBillentryInfo.setcoreBillEntrySeq	���ĵ��ݷ�¼�к�	����	FCoreBillEntrySeq	0
//		otherBillentryInfo.setcoreBillNumber	���ĵ��ݱ��	�ַ���	FCoreBillNumber	0
//		otherBillentryInfo.setcoreBillEntryId	���ĵ��ݷ�¼ID	�ַ���	FCoreBillEntryId	0
//		otherBillentryInfo.setcoreBillId	���ĵ���ID	�ַ���	FCoreBillId	0
//		otherBillentryInfo.setcoreBillType	���ĵ�������	����	FCoreBillTypeID	0
//		otherBillentryInfo.setsourceBillEntryId	Դ���ݷ�¼id	�ַ���	FSourceBillEntryId	0
//		otherBillentryInfo.setsourceBillId	Դ��id	�ַ���	FSourceBillId	0
//		otherBillentryInfo.setoppAccount	�Է���Ŀ	����	FOppAccountID	0
//		otherBillentryInfo.setaccount	Ӧ�գ�Ӧ������Ŀ	����	FAccountID	0
//		otherBillentryInfo.sethisUnVerifyAmountLocal	��ʷδ������λ��	����	FHisUnVerifyAmountLocal	0
//		otherBillentryInfo.sethisUnVerifyAmount	��ʷδ�������	����	FHisUnVerifyAmount	0
//		otherBillentryInfo.setsourceBillAsstActID	Դ��������id	�ַ���	FSourceBillAsstActID	0
//		otherBillentryInfo.setbaseQty	����������λ����	����	FBaseQty	1
		otherBillentryInfo.setBaseQty(BigDecimal.ONE);
//		otherBillentryInfo.setbaseUnit	����������λ	����	FBaseUnitID	0
//		otherBillentryInfo.setassistQty	��������	����	FAssistQty	0
//		otherBillentryInfo.setassistUnit	������λ	����	FAssistUnitID	0
//		otherBillentryInfo.setamountLocal	��λ��	����	FAmountLocal	88
		otherBillentryInfo.setAmountLocal(billfee);
//		otherBillentryInfo.setamount	���	����	FAmount	88
		otherBillentryInfo.setAmount(billfee);
//		otherBillentryInfo.settaxAmountLocal	˰�λ��	����	FTaxAmountLocal	0
//		otherBillentryInfo.settaxAmount	˰��	����	FTaxAmount	0
//		otherBillentryInfo.settaxRate	˰��	����	FTaxRate	0
//		otherBillentryInfo.setdiscountAmountLocal	�ۿ۶λ��	����	FDiscountAmountLocal	0
//		otherBillentryInfo.setdiscountAmount	�ۿ۶�	����	FDiscountAmount	0
//		otherBillentryInfo.setdiscountRate	��λ�ۿ�	����	FDiscountRate	0
//		otherBillentryInfo.setdiscountType	�ۿ۷�ʽ	ҵ��ö��	FDiscountType	-1
//		otherBillentryInfo.setactualPrice	ʵ�ʺ�˰����	����	FActualPrice	88
		otherBillentryInfo.setActualPrice(billfee);
//		otherBillentryInfo.settaxPrice	��˰����	����	FTaxPrice	88
		otherBillentryInfo.setTaxPrice(billfee);
//		otherBillentryInfo.setrealPrice	ʵ�ʵ���	����	FRealPrice	88
		otherBillentryInfo.setRealPrice(billfee);
//		otherBillentryInfo.setprice	����	����	FPrice	88
		otherBillentryInfo.setPrice(billfee);
//		otherBillentryInfo.setquantity	����	����	FQuantity	1
		otherBillentryInfo.setQuantity(BigDecimal.ONE);
//		otherBillentryInfo.setremark	��ע	�ַ���	FRemark	0
//		otherBillentryInfo.setlockUnVerifyAmtLocal	δ������λ��	����	FLockUnVerifyAmtLocal	88
		otherBillentryInfo.setLockUnVerifyAmtLocal(billfee);
//		otherBillentryInfo.setlockUnVerifyAmt	δ�������	����	FLockUnVerifyAmt	88
		otherBillentryInfo.setLockUnVerifyAmt(billfee);
//		otherBillentryInfo.setlockVerifyAmtLocal	��������λ��	����	FLockVerifyAmtLocal	0
//		otherBillentryInfo.setlockVerifyAmt	���������	����	FLockVerifyAmt	0
//		otherBillentryInfo.setunVerifyAmountLocal	δ�����λ��	����	FUnVerifyAmountLocal	88
		otherBillentryInfo.setUnVerifyAmountLocal(billfee);
//		otherBillentryInfo.setunVerifyAmount	δ������	����	FUnVerifyAmount	88
		otherBillentryInfo.setUnVerifyAmount(billfee);
//		otherBillentryInfo.setverifyAmountLocal	�ѽ����λ��	����	FVerifyAmountLocal	0
//		otherBillentryInfo.setverifyAmount	�ѽ�����	����	FVerifyAmount	0
//		otherBillentryInfo.setrecievePayAmountLocal	Ӧ�գ�������λ��	����	FRecievePayAmountLocal	88
		otherBillentryInfo.setRecievePayAmountLocal(billfee);
//		otherBillentryInfo.setrecievePayAmount	Ӧ�գ��������	����	FRecievePayAmount	88
		otherBillentryInfo.setRecievePayAmount(billfee);
//		otherBillentryInfo.setisPresent	�Ƿ���Ʒ	����	FIsPresent	0
//		otherBillentryInfo.setexpenseItem	������Ŀ	����	FExpenseItemID	8i0AAAAA3nR45LyU
		otherBillentryInfo.setExpenseItem(getExpenseTypeInfoF7(ctx,"FY002001"));
//		otherBillentryInfo.setassistProperty	��������	����	FAssistPropertyID	0
//		otherBillentryInfo.setmeasureUnit	������λ	����	FMeasureUnitID	0
//		otherBillentryInfo.setmaterialName	��������	�ַ���	FMaterialName	0
//		otherBillentryInfo.setmaterial	����	����	FMaterialID	0
//		otherBillentryInfo.setrowType	������	����	FRowTypeId	0
//		otherBillentryInfo.setseq	���ݷ�¼���к�	����	FSeq	1
//		otherBillentryInfo.setid	ID	BOSUUID	FID	8i0AAAACKrftTbyB

		otherBillentryInfo.setHead(otherBillInfo);
		otherBillInfo.getEntry().add(otherBillentryInfo);
		
		
    }
    
    //�������ƻ�����ȡ��������F7
    protected DataBaseInfo getBaseDataByNumber(String value,IDataBase idb, HashMap dataMap, int findMothed)
    {
      if (value == null)
        return null;
      DataBaseInfo dataInfo = null;
      if (dataMap != null)
      {
        dataInfo = (DataBaseInfo)dataMap.get(value);
        if (dataInfo != null)
          return dataInfo;
      }
      try {
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        if (findMothed == 2)
        {
          filter.appendFilterItem("number", value);
          filter.appendFilterItem("name", value);
          filter.setMaskString("#0 or #1");
        }
        else if (findMothed == 1) {
          filter.appendFilterItem("name", value);
        } else {
          filter.appendFilterItem("number", value);
        }
        view.setFilter(filter);
        DataBaseCollection coll = idb.getDataBaseCollection(view);
        if (coll.size() > 0)
        {
          dataInfo = coll.get(0);
          if (dataMap != null)
            dataMap.put(value, dataInfo);
        }
      }
      catch (Exception e)
      {
    	MsgBox.showInfo("δ�ҵ���Ӧ�Ļ�������");
        e.printStackTrace();
      }
      return dataInfo;
    }

	/**
	 * ��˾F7	CompanyOrgUnitInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected CompanyOrgUnitInfo getCompanyOrgUnitInfoF7(Context ctx, String number) throws Exception {
		CompanyOrgUnitInfo companyOrgUnitInfo = new CompanyOrgUnitInfo();
		try {
			CompanyOrgUnitCollection companyOrgUnitCollection = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection(getViewInfo1(number));
			if ((companyOrgUnitCollection  != null) && (companyOrgUnitCollection.size() > 0)) {
				companyOrgUnitInfo = companyOrgUnitCollection.get(0);
			} else {
				throw new Exception("��˾��"+number+"δ��EAS���ҵ���Ӧ�Ĺ�˾");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return companyOrgUnitInfo;
	}
	
	/**
	 * ������֯ӳ���ȡ��˾F7	CompanyOrgUnitInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected OrgmapInfo getOrgmapInfoF7(Context ctx, String number) throws Exception {
		OrgmapInfo orgmapInfo = new OrgmapInfo();
		try {
			OrgmapCollection orgmapCollection = OrgmapFactory.getLocalInstance(ctx).getOrgmapCollection( " where companyname = '"+number+"'");
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
	 * ������Ŀӳ���ȡ������ĿF7	CompanyOrgUnitInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected FeeitemInfo getFeeitemInfoF7(Context ctx, String id) throws Exception {
		FeeitemInfo feeitemInfo = new FeeitemInfo();
		try {
			FeeitemCollection feeitemCollection = FeeitemFactory.getLocalInstance(ctx).getFeeitemCollection( " where bizid = '"+id+"'");
			if ((feeitemCollection  != null) && (feeitemCollection.size() > 0)) {
				feeitemInfo = feeitemCollection.get(0);
			} else {
				throw new Exception("������Ŀ��"+id+"δ��EASӳ������ҵ���Ӧ�ķ�����Ŀ");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return feeitemInfo;
	}
	
	
	
	/**
	 * ���㷽ʽF7	 SettlementTypeInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected SettlementTypeInfo getSettlementTypeInfoF7(Context ctx, String name) throws Exception {
		SettlementTypeInfo settlementTypeInfo = new SettlementTypeInfo();
		try {
			SettlementTypeCollection companyOrgUnitCollection = SettlementTypeFactory.getLocalInstance(ctx).getSettlementTypeCollection(getViewInfo1(name));
			if ((companyOrgUnitCollection  != null) && (companyOrgUnitCollection.size() > 0)) {
				settlementTypeInfo = companyOrgUnitCollection.get(0);
			} else {
				throw new Exception("��˾��"+name+"δ��EAS���ҵ���Ӧ�Ĺ�˾");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return settlementTypeInfo;
	}
	/**
	 * ������֯	SaleOrgUnitInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected SaleOrgUnitInfo getSaleOrgUnitInfoF7(Context ctx, String number) throws Exception {
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
	 * �ɹ���֯	SaleOrgUnitInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected PurchaseOrgUnitInfo getPurchaseOrgUnitInfoF7(Context ctx, String number) throws Exception {
		PurchaseOrgUnitInfo purchaseOrgUnitInfo = new PurchaseOrgUnitInfo();
		try {
			PurchaseOrgUnitCollection purchaseOrgUnitCollection = PurchaseOrgUnitFactory.getLocalInstance(ctx).getPurchaseOrgUnitCollection(getViewInfo1(number));
			if ((purchaseOrgUnitCollection  != null) && (purchaseOrgUnitCollection.size() > 0)) {
				purchaseOrgUnitInfo = purchaseOrgUnitCollection.get(0);
			} else {
				throw new Exception("�ɹ���֯��"+number+"δ�ҵ���Ӧ�Ĳɹ���֯");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return purchaseOrgUnitInfo;
	}
	/**
	 * ����������Ŀ--��������
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception
	 */
	protected AsstActTypeInfo getAsstActTypeInfoF7(Context ctx, String number) throws Exception {
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
	
	protected EntityViewInfo getViewInfo(String number) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		evi.setFilter(filter);
		return evi;
	}
	
	protected EntityViewInfo getViewInfo1(String name) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", name, CompareType.EQUALS));
		evi.setFilter(filter);
		return evi;
	}
	/**
	 * �ұ�F7	CurrencyInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected CurrencyInfo getCurrencyInfoF7(Context ctx, String number) throws Exception {
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
	 * �ͻ�����F7	cSSPGroupInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected CSSPGroupInfo getCSSPGroupInfoF7(Context ctx, String number) throws Exception {
		CSSPGroupInfo cSSPGroupInfo = new CSSPGroupInfo();
		try {
			CSSPGroupCollection cSSPGroupCollection = CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection(getViewInfo1(number));
			if ((cSSPGroupCollection  != null) && (cSSPGroupCollection.size() > 0)) {
				cSSPGroupInfo = cSSPGroupCollection.get(0);
			}else {
				throw new Exception("EAS��δ�ҵ����顣");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return cSSPGroupInfo;
	}
	protected CSSPGroupStandardInfo getCSSPGroupStandardInfoF7(Context ctx, String number) throws Exception {
		CSSPGroupStandardInfo cSSPGroupStandardInfo = new CSSPGroupStandardInfo();
		try {
			CSSPGroupStandardCollection CSSPGroupStandardCollection = CSSPGroupStandardFactory.getLocalInstance(ctx).getCSSPGroupStandardCollection(getViewInfo1(number));
			if ((CSSPGroupStandardCollection  != null) && (CSSPGroupStandardCollection.size() > 0)) {
				cSSPGroupStandardInfo = CSSPGroupStandardCollection.get(0);
			}else {
				throw new Exception("EAS��δ�ҵ����顣");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return cSSPGroupStandardInfo;
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
	/**
	 * �ͻ�F7		CustomerInfo
	 * @param ctx
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	protected CustomerInfo getCustomerInfoF7FromName(Context ctx, String name) throws Exception {
		CustomerInfo customerInfo = new CustomerInfo();
		try {
			CustomerCollection customerCollection = CustomerFactory.getLocalInstance(ctx).getCustomerCollection(getViewInfo1(name));
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
	/**
	 * ���ʽF7		CustomerInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected PaymentTypeInfo getPaymentTypeInfoF7(Context ctx, String name) throws Exception {
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
	 * �տ�����F7		ReceivingBillTypeInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected ReceivingBillTypeInfo getReceivingBillTypeInfoF7(Context ctx, String name) throws Exception {
		ReceivingBillTypeInfo receivingBillTypeInfo = new ReceivingBillTypeInfo();
		try {
			ReceivingBillTypeCollection receivingBillTypeCollection = ReceivingBillTypeFactory.getLocalInstance(ctx).getReceivingBillTypeCollection(getViewInfo1(name));
			if ((receivingBillTypeCollection  != null) && (receivingBillTypeCollection.size() > 0)) {
				receivingBillTypeInfo = receivingBillTypeCollection.get(0);
			}else {
				throw new Exception("δ�ҵ��տ�����");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return receivingBillTypeInfo;
	}
	/**
	 * �û�F7		UserInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected UserInfo getUserInfoF7(Context ctx, String number) throws Exception {
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
	 * ������λF7		MeasureUnitInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected MeasureUnitInfo getMeasureUnitInfoF7(Context ctx, String number) throws Exception {
		MeasureUnitInfo meamsureUnitInfo = new MeasureUnitInfo();
		try {
			MeasureUnitCollection measureUnitCollection = MeasureUnitFactory.getLocalInstance(ctx).getMeasureUnitCollection(getViewInfo(number));
			if ((measureUnitCollection  != null) && (measureUnitCollection.size() > 0)) {
				meamsureUnitInfo = measureUnitCollection.get(0);
			}else {
				throw new Exception("EAS��δ�ҵ���Ӧ�ļ�����λ�����顣");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return meamsureUnitInfo;
	}
	/**
	 * ������ĿF7		ExpenseTypeInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected ExpenseTypeInfo getExpenseTypeInfoF7(Context ctx, String id) throws Exception {
		
		FeeitemInfo feeitemInfo = new FeeitemInfo();
		try {
			FeeitemCollection feeitemCollection = FeeitemFactory.getLocalInstance(ctx).getFeeitemCollection( " where bizid = '"+id+"'");
			if ((feeitemCollection  != null) && (feeitemCollection.size() > 0)) {
				feeitemInfo = feeitemCollection.get(0);
			} else {
				throw new Exception("������Ŀ��"+id+"δ��EASӳ������ҵ���Ӧ�ķ�����Ŀ");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		ExpenseTypeInfo expenseTypeInfo = new ExpenseTypeInfo();
		try {
			ExpenseTypeCollection expenseTypeCollection = ExpenseTypeFactory.getLocalInstance(ctx).getExpenseTypeCollection(getViewInfo(feeitemInfo.getExpenseNumber()));
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
	
	@Override
	public void syncGetmark(Context ctx, String id) throws BOSException {
		mysqlConnectionUtil util = new mysqlConnectionUtil();
		Connection connection = null;
		try {
			connection = util.createConn();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String sql = "update t_baseData set kdGetMark = '0' where ID = '"+id+"'";
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			pStatement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			pStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}