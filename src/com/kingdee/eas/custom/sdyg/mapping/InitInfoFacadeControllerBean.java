package com.kingdee.eas.custom.sdyg.mapping;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
import com.kingdee.bos.dao.AbstractObjectCollection;
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
import com.kingdee.eas.basedata.assistant.AccountBankCollection;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
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
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
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
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerGroupDetail;
import com.kingdee.eas.basedata.master.cssp.CustomerGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerKindEnum;
import com.kingdee.eas.basedata.master.cssp.CustomerSaleInfoFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerSaleInfoInfo;
import com.kingdee.eas.basedata.master.cssp.ICustomerCompanyInfo;
import com.kingdee.eas.basedata.master.cssp.ICustomerSaleInfo;
import com.kingdee.eas.basedata.master.cssp.PayInvoiceTypeEnum;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.master.cssp.UsingStatusEnum;
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
import com.kingdee.eas.cp.bc.OperationTypeCollection;
import com.kingdee.eas.cp.bc.OperationTypeFactory;
import com.kingdee.eas.cp.bc.OperationTypeInfo;
import com.kingdee.eas.csinterface.syncdatabase.ConnectionFactory;
import com.kingdee.eas.csinterface.syncdatabase.ConnectionInfo;
import com.kingdee.eas.csinterface.utils.DataBaseType;
import com.kingdee.eas.csinterface.utils.KSQLUtil;
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
import com.kingdee.eas.fi.ar.OtherBillPlanCollection;
import com.kingdee.eas.fi.ar.OtherBillPlanInfo;
import com.kingdee.eas.fi.ar.OtherBillTypeEnum;
import com.kingdee.eas.fi.ar.OtherBillentryCollection;
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
import com.kingdee.eas.framework.EffectedStatusEnum;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.sunny.SyncBizSystemFacadeFactory;
import com.kingdee.eas.sunny.commUtil.mysqlConnectionUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;


public class InitInfoFacadeControllerBean extends AbstractInitInfoFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.sdyg.mapping.InitInfoFacadeControllerBean");
    protected HashMap yingshouMap = new HashMap();
    protected HashMap shoukuanMap = new HashMap();
    protected HashMap yingfuMap = new HashMap();
    protected HashMap zhaiquanYingshouMap = new HashMap();
    protected HashMap zhaiquanShoukuanMap = new HashMap();
    protected HashMap bujiaoMap = new HashMap();
    protected HashMap CompanyOrgUnitMap = new HashMap();
    protected HashMap CurrencyMap = new HashMap();
    protected ICompanyOrgUnit iCompanyOrgUnit;
    protected ICurrency iCurrency;
    public static int QueryByNumber = 0;  		//通过编码查F7
    public static int QueryByName = 1;			//通过名称查F7
    public static int QueryByNumberOrName = 2;	//通过编码或名称查F7
    
    @Override
    protected void _syncDoctor(Context ctx) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._syncDoctor(ctx);
		// 查询数据
		String sql = "select dentist.* from t_dentist dentist  left join t_baseData baseData  on dentist.ID = baseData.ID  where baseData.kdGetMark <> '1' or baseData.kdGetMark is null ";
		mysqlConnectionUtil util = new mysqlConnectionUtil();
		Connection connection = null;
//		ConnectionInfo connectionInfo = ConnectionFactory.getLocalInstance(ctx).getConnectionInfo(" where name = '中间表连接'");
//		String DBtype = connectionInfo.getDBType().getName().toString();
//		DataBaseType dataBaseType = DataBaseType.getInstance(DBtype);
//		String IP = connectionInfo.getIP();
//		String DB = connectionInfo.getInstance();
//		int port = connectionInfo.getPort();
//		String username = connectionInfo.getUsername();
//		String password = connectionInfo.getPassword();
//		try {
//			connection = KSQLUtil.getKSQLConnection(dataBaseType, IP, port, DB,
//						username, password);
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
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
		
		// 循环查询结果
		StringBuffer strIDbuff = new StringBuffer();
		try {
			while (resultSet.next()) {
				String bizID = resultSet.getString("id");
				DoctorCollection col = DoctorFactory.getLocalInstance(ctx).getDoctorCollection(" where bizid ='"+bizID+"'");
				if (col != null && col.size() != 0) {
					continue;
				}
				strIDbuff.append("'");
				strIDbuff.append(bizID);
				strIDbuff.append("',");
				DoctorInfo doctorInfo = new DoctorInfo();
				doctorInfo.setBizid(bizID);//业务系统主键
				doctorInfo.setBiznumber(resultSet.getString("name"));
				doctorInfo.setBizname(resultSet.getString("userName"));
				DoctorFactory.getLocalInstance(ctx).addnew(doctorInfo);
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
    protected void _syncOrgInfo(Context ctx) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._syncOrgInfo(ctx);
    	
    	// 查询数据
		String sql = "select * from t_org org  left join t_basedata baseData  on org.ID = baseData.ID  where baseData.kdGetMark <> '1' or baseData.kdGetMark is null ";
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
		
		
		// 查询是否存在映射表,若不存在则新增一张，若存在获取对象
//		OrgmapCollection col = OrgmapFactory.getLocalInstance(ctx).getOrgmapCollection();
//		if (col != null && col.size() != 0) {
//			info = col.get(0);
//		} else {
//			info = new OrgmapInfo();
//			info.setId(BOSUuid.create(info.getBOSType()));
//			OrgmapFactory.getLocalInstance(ctx).addnew(info);
//		}
		// 循环查询结果创建分录
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
				info.setBizID(bizID);//业务系统主键
				info.setNumber(resultSet.getString("dgtNo"));
				info.setDgtNo(resultSet.getString("dgtNo"));//数字编码
				info.setAlphNo(resultSet.getString("alphNo"));//业务系统字母编码
				info.setCompanyName(resultSet.getString("companyName"));//业务系统门诊名称
				info.setCreateTime(new Timestamp(new Date().getTime()));//创建时间
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
			String sql1 = "update t_basedata set kdGetMark = '1' where ID in ("+strID+")";
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
    protected void _syncFeeitem(Context ctx) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._syncFeeitem(ctx);

		// 查询数据
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
		
		// 循环查询结果
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
				feeInfo.setBizID(bizID);//业务系统主键
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
    protected void _syncOthcustomer(Context ctx) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._syncOthcustomer(ctx);

		// 查询数据
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
		// 循环查询结果
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
				othCustomerInfo.setBizID(bizID);//业务系统主键
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
    protected void _syncCustomer(Context ctx)  {
    	// TODO Auto-generated method stub
//    	super._syncCustomer(ctx);

		// 查询数据
//		String sql = "select cus.* from t_customer cus  left join t_baseData baseData  on cus.ID = baseData.ID  where baseData.kdGetMark <> '1' or baseData.kdGetMark is null limit 1,500 " ;
		String sql = "select cus.* from t_customer cus  left join t_baseData baseData  on cus.ID = baseData.ID  where baseData.kdGetMark <> '1' or baseData.kdGetMark is null " ;
//		String sql = "select cus.* from t_customer cus  left join t_baseData baseData  on cus.ID = baseData.ID  where  cus.recordNo = 'QL10933' " ;
		mysqlConnectionUtil util = new mysqlConnectionUtil();
		System.out.println(sql);
		Connection connection = null;
		try {
			connection = util.createConn();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(sql);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = pStatement.executeQuery();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

    	SyncLogInfo logInfo = new SyncLogInfo();
    	logInfo.setId(BOSUuid.create("59A5EF45"));
    	//日志业务日期
    	logInfo.setBizDate(new Date());
    	//日志创建时间
    	logInfo.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
    	SyncLogEntryInfo logEntryInfo = null;
    	String msg ="";
		// 循环查询结果
		StringBuffer strIDbuff = new StringBuffer();
		try {
			while (resultSet.next()) {
				String number = resultSet.getString("recordNo");
				String bizID = resultSet.getString("ID");
				CustomerCollection col = CustomerFactory.getLocalInstance(ctx).getCustomerCollection(" where usedstatus != '3' and number = '"+number+"'");
//				CustomerInfo isExistscustomerInfo = new CustomerInfo();
//				try {
//					isExistscustomerInfo = CustomerFactory.getLocalInstance(ctx).getCustomerInfo(" where usedstatus != '3' and number = '"+number+"'");
//				} catch (EASBizException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
				if (col != null && col.size() != 0) {
					continue;
				}
//				if(isExistscustomerInfo != null){
//					continue;
//				}
				CustomerInfo customerInfo = new CustomerInfo();
				customerInfo.setNumber(resultSet.getString("recordNo"));
				customerInfo.setName(resultSet.getString("customerName"));
				customerInfo.setInvoiceType(PayInvoiceTypeEnum.getEnum(PayInvoiceTypeEnum.COMMONINVOICE_VALUE));
				customerInfo.setCustomerKind(CustomerKindEnum.getEnum(CustomerKindEnum.COMMON_VALUE));
				customerInfo.setUsedStatus(UsedStatusEnum.getEnum(UsedStatusEnum.UNAPPROVE_VALUE));
				try {
					customerInfo.setBrowseGroup(getCSSPGroupInfoF7(ctx, "患者"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				CustomerGroupDetailInfo customerGroupDetail = new CustomerGroupDetailInfo();
				try {
					customerGroupDetail.setCustomerGroup(getCSSPGroupInfoF7(ctx, "患者"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					customerGroupDetail.setCustomerGroupStandard(getCSSPGroupStandardInfoF7(ctx, "客户分类标准"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				customerGroupDetail.setCustomer(customerInfo);
				customerInfo.getCustomerGroupDetails().add(customerGroupDetail);
				IObjectPK pk = null;
				try {
					pk = CustomerFactory.getLocalInstance(ctx).addnew(customerInfo);
				} catch (EASBizException e1) {
					msg = e1.getMessage();
			    	logEntryInfo = new SyncLogEntryInfo();
					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
					logEntryInfo.setLoginfo(customerInfo.getName()+msg);
					logEntryInfo.setParent(logInfo);
					logInfo.getEntrys().add(logEntryInfo);
				} catch (BOSException e1) {
					msg = e1.getMessage();
			    	logEntryInfo = new SyncLogEntryInfo();
					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
					logEntryInfo.setLoginfo(customerInfo.getName()+msg);
					logEntryInfo.setParent(logInfo);
					logInfo.getEntrys().add(logEntryInfo);
				}
				if(pk != null){
					try {
						CustomerFactory.getLocalInstance(ctx).submit(pk, customerInfo);
						CustomerFactory.getLocalInstance(ctx).approve(pk);
					} catch (EASBizException e) {
						msg = e.getMessage();
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(customerInfo.getName()+msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					} catch (BOSException e) {
						msg = e.getMessage();
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(customerInfo.getName()+msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
					//保存成功后，更新中间表数据
					strIDbuff.append("'");
					strIDbuff.append(bizID);
					strIDbuff.append("',");
					//客户审核完之后进行分配
					CustomerCompanyInfoInfo customerCompanyInfo = new CustomerCompanyInfoInfo();
					customerCompanyInfo.setCU(customerInfo.getCU());
					try {
						customerCompanyInfo.setCompanyOrgUnit(getCompanyOrgUnitInfoF7(ctx, "赛德阳光集团"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						customerCompanyInfo.setSettlementCurrency(getCurrencyInfoF7(ctx,"BB01" ));
					} catch (Exception e) {
						e.printStackTrace();
					}
					customerCompanyInfo.setEffectedStatus(EffectedStatusEnum.getEnum(EffectedStatusEnum.TEMPSTORE_VALUE));
					customerCompanyInfo.setCustomer(customerInfo);
					customerCompanyInfo.setUsingStatus(UsingStatusEnum.getEnum(UsingStatusEnum.USING_VALUE));
					ICustomerCompanyInfo iCustomerCompanyInfo = null;
					try {
						iCustomerCompanyInfo = CustomerCompanyInfoFactory.getLocalInstance(ctx);
					} catch (BOSException e1) {
						e1.printStackTrace();
					}
					try {
						IObjectPK companyInfopk = iCustomerCompanyInfo.save(customerCompanyInfo);
					} catch (EASBizException e1) {
						msg = e1.getMessage();
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(customerInfo.getName()+msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					} catch (BOSException e1) {
						msg = e1.getMessage();
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(customerInfo.getName()+msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
//					iCustomerCompanyInfo.submit(companyInfopk, customerCompanyInfo);
					
					CustomerSaleInfoInfo customerSaleInfoInfo = new CustomerSaleInfoInfo();
					customerSaleInfoInfo.setCU(customerInfo.getCU());
					try {
						customerSaleInfoInfo.setSaleOrgUnit(getSaleOrgUnitInfoF7(ctx,  "赛德阳光集团"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					customerSaleInfoInfo.setEffectedStatus(EffectedStatusEnum.getEnum(EffectedStatusEnum.TEMPSTORE_VALUE));
					customerSaleInfoInfo.setCustomer(customerInfo);
					customerSaleInfoInfo.setSettlementOrgUnit(customerInfo);
					customerSaleInfoInfo.setBillingOrgUnit(customerInfo);
					customerSaleInfoInfo.setDeliverOrgUnit(customerInfo);
					customerSaleInfoInfo.setUsingStatus(UsingStatusEnum.getEnum(UsingStatusEnum.USING_VALUE));
					ICustomerSaleInfo iCustomerSaleInfo = null;
					try {
						iCustomerSaleInfo = CustomerSaleInfoFactory.getLocalInstance(ctx);
					} catch (BOSException e) {
						e.printStackTrace();
					}
					try {
						iCustomerSaleInfo.save(customerSaleInfoInfo);
					} catch (EASBizException e) {
						msg = e.getMessage();
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(customerInfo.getName()+msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					} catch (BOSException e) {
						msg = e.getMessage();
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(customerInfo.getName()+msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
			}
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		//保存msg
		if(logInfo != null){
			try {
				SyncLogFactory.getLocalInstance(ctx).save(logInfo);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
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
    protected void _syncGetmark(Context ctx, String id) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._syncGetmark(ctx, id);

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
    
    @Override
    protected void _initBillInfo(Context ctx,String numbers,String companynumber) {
//    	super._initBillInfo(ctx, numbers);
    	//查询数据
    	System.out.println("进入InitInfoFacadeControllerBean");
    	//收据(正常情况)
//    	StringBuffer sqlbuff = new StringBuffer();
//    	sqlbuff.append("SELECT bill.ID billid,org.companyName billcompany," );
//    	sqlbuff.append("	bill.receiptNo billreceiptno,bill.billDate billdate,bill.recTypeNo billrectypeno,");
//    	sqlbuff.append("	bill.recTypeName billrectypename, bill.account billaccount, bill.bizDate billbizdate,bill.recordCompany billrecordcompany,");
//    	sqlbuff.append("	bill.sd_channel billchannel,bill.charge_id billchargeid,entry2.aramount billtatalpay,entry2.actualpay billtatalactualpay,bill.VIPNo billVipNo,");
//    	sqlbuff.append("	entry.entryID entryID,entry.expenseNo entryexpenseno,entry.expenseName entryexpensename,entry.qty entryqty,entry.ARAmount entryaramount,");
//    	sqlbuff.append("	entry.docNo entrydocno,entry.amount entryamount, entry.type entrytype,entry.VIPRight entryvipright,entry.detailID entrydetailid,entry.receiptNO entryreceiptno,");
//    	sqlbuff.append("	entry.charge_id entrychargeid,entry.charge_detail_id entrychargedetailid,fee.itemNo feeitemNo,oth.bizType othbizType");
//    	sqlbuff.append(" FROM t_receipt bill INNER JOIN t_receipt_ety entry ON bill.ID = entry.ID  " );
//    	sqlbuff.append(" LEFT JOIN ( select id,sum(ARAmount) aramount,sum(amount) actualpay from t_receipt_ety where type = 0 GROUP BY id ) entry2 on bill.ID = entry2.ID  " );
//    	sqlbuff.append("	left join t_org org on bill.company = org.dgtNo " );
//    	sqlbuff.append("	left join t_feeitem fee on entry.expenseNo = fee.id " );
//    	sqlbuff.append("	left join t_othcustomer oth on bill.sd_channel = oth.id " );
//    	sqlbuff.append(" where bill.bizDate BETWEEN  STR_TO_DATE('2021-01-01','%Y-%m-%d') and STR_TO_DATE('2021-01-31','%Y-%m-%d') and (bill.company = '066' or bill.company = '080') and entry2.aramount >0 " );
//    	if(numbers != null && numbers.length() > 0){
//    		sqlbuff.append(" and bill.receiptNo = '"+numbers+"'");
//    	}
//    	if(companynumber != null && companynumber.length() > 0){
//    		sqlbuff.append(" and bill.company = '"+companynumber+"'");
//    	}
    	
    	//收据(历史数据情况)
    	StringBuffer sqlbuff = new StringBuffer();
    	sqlbuff.append("SELECT bill.ID billid,org.companyName billcompany," );
    	sqlbuff.append("	bill.receiptNo billreceiptno,entry.orderdate billdate,bill.recTypeNo billrectypeno,");
    	sqlbuff.append("	bill.recTypeName billrectypename, bill.account billaccount, entry.orderdate billbizdate,bill.recordCompany billrecordcompany,");
    	sqlbuff.append("	bill.sd_channel billchannel,bill.charge_id billchargeid,entry.aramount billtatalpay,entry.amount billtatalactualpay,bill.VIPNo billVipNo,");
    	sqlbuff.append("	entry.entryID entryID,entry.expenseNo entryexpenseno,entry.expenseName entryexpensename,entry.qty entryqty,entry.ARAmount entryaramount,");
    	sqlbuff.append("	entry.docNo entrydocno,entry.amount entryamount, entry.type entrytype,entry.VIPRight entryvipright,entry.detailID entrydetailid,entry.receiptNO entryreceiptno,");
    	sqlbuff.append("	entry.charge_id entrychargeid,entry.charge_detail_id entrychargedetailid,fee.itemNo feeitemNo,oth.bizType othbizType");
    	sqlbuff.append(" FROM t_receipt bill INNER JOIN t_receipt_ety entry ON bill.ID = entry.ID  " );
//    	sqlbuff.append(" LEFT JOIN ( select id,sum(ARAmount) aramount,sum(amount) actualpay from t_receipt_ety where type = 0 GROUP BY id ) entry2 on bill.ID = entry2.ID  " );
    	sqlbuff.append("	left join t_org org on bill.company = org.dgtNo " );
    	sqlbuff.append("	left join t_feeitem fee on entry.expenseNo = fee.id " );
    	sqlbuff.append("	left join t_othcustomer oth on bill.sd_channel = oth.id " );
    	sqlbuff.append(" where entry.type =0 " );
//    	sqlbuff.append(" and entry.orderdate BETWEEN  STR_TO_DATE('2021-01-01','%Y-%m-%d') and STR_TO_DATE('2021-01-31','%Y-%m-%d') and (bill.company = '066' )  " );
    	if(numbers != null && numbers.length() > 0){
    		sqlbuff.append(" and bill.receiptNo = '"+numbers+"'");
    	}
    	if(companynumber != null && companynumber.length() > 0){
    		sqlbuff.append(" and bill.company = '"+companynumber+"'");
    	}
    	
    	//对账单
    	StringBuffer duizhangSqlBuff = new StringBuffer();
    	duizhangSqlBuff.append("select bill.company companyno ,bill.bizDate billdate ,bill.bizNo billno,bill.recAmount billrecamount,");
    	duizhangSqlBuff.append(" bill.actualRec billactualrec,bill.recTypeNo billrectypeno,bill.recTypeName billrectypename,bill.serviceFee billfee,bill.instalmentFee instalmentFee,");
    	duizhangSqlBuff.append(" bill.recordNo billrecordno,org.companyName companyname ,receipt.sd_channel othcustomerid");
    	duizhangSqlBuff.append(" from t_reconciliation bill left join t_org org on bill.company = org.dgtNo ");
    	duizhangSqlBuff.append(" left join t_receipt receipt on bill.bizNo = receipt.receiptNo where 1=1 ");
    	if(numbers != null && numbers.length() > 0){
    		duizhangSqlBuff.append(" and bill.bizNo = '"+numbers+"'");
    	}
    	if(companynumber != null && companynumber.length() > 0){
    		duizhangSqlBuff.append(" and bill.company = '"+companynumber+"'");
    	}
//    	duizhangSqlBuff.append("  and bill.bizDate BETWEEN  STR_TO_DATE('2021-01-01','%Y-%m-%d') and STR_TO_DATE('2021-01-31','%Y-%m-%d') and (bill.company = '066')");
    	
    	//补交情况
    	StringBuffer bjsqlbuff = new StringBuffer();
    	bjsqlbuff.append("SELECT bill.ID billid,org.companyName billcompany," );
    	bjsqlbuff.append("	bill.receiptNo billreceiptno,bill.billDate billdate,bill.recTypeNo billrectypeno,");
    	bjsqlbuff.append("	bill.recTypeName billrectypename, bill.account billaccount, bill.bizDate billbizdate,bill.recordCompany billrecordcompany,");
    	bjsqlbuff.append("	bill.sd_channel billchannel,bill.charge_id billchargeid,entry2.aramount billtatalpay,entry2.actualpay billtatalactualpay,bill.VIPNo billVipNo,");
    	bjsqlbuff.append("	entry.entryID entryID,entry.expenseNo entryexpenseno,entry.expenseName entryexpensename,entry.qty entryqty,entry.ARAmount entryaramount,");
    	bjsqlbuff.append("	entry.docNo entrydocno,entry.amount entryamount, entry.type entrytype,entry.VIPRight entryvipright,entry.detailID entrydetailid,entry.receiptNO entryreceiptno,");
    	bjsqlbuff.append("	entry.charge_id entrychargeid,entry.charge_detail_id entrychargedetailid,fee.itemNo feeitemNo,oth.bizType othbizType");
    	bjsqlbuff.append(" FROM t_receipt bill INNER JOIN t_receipt_ety entry ON bill.ID = entry.ID  " );
    	bjsqlbuff.append(" LEFT JOIN ( select id,sum(ARAmount) aramount,sum(amount) actualpay from t_receipt_ety where type = 1 GROUP BY id ) entry2 on bill.ID = entry2.ID  " );
    	bjsqlbuff.append("	left join t_org org on bill.company = org.dgtNo " );
    	bjsqlbuff.append("	left join t_feeitem fee on entry.expenseNo = fee.id " );
    	bjsqlbuff.append("	left join t_othcustomer oth on bill.sd_channel = oth.id " );
    	bjsqlbuff.append(" where entry2.actualpay >0 " );
//    	bjsqlbuff.append(" and bill.bizDate BETWEEN  STR_TO_DATE('2021-01-01','%Y-%m-%d') and STR_TO_DATE('2021-01-31','%Y-%m-%d') and (bill.company = '066' ) " );
    	if(numbers != null && numbers.length() > 0){
    		bjsqlbuff.append(" and bill.receiptNo = '"+numbers+"'");
    	}
    	if(companynumber != null && companynumber.length() > 0){
    		bjsqlbuff.append(" and bill.company = '"+companynumber+"'");
    	}
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
		PreparedStatement ps = null;
		//收据
    	ResultSet rs = null;
    	//对账
    	ResultSet duizhangrs = null;
    	//补交
    	ResultSet bjrs = null;
    	System.out.println("收据："+sqlbuff.toString());
    	System.out.println("对账单："+duizhangSqlBuff.toString());
    	System.out.println("补交情况："+bjsqlbuff.toString());
    	int i = 0 ;
    	//1.获取收据和对账单的结果集
		try {
			//收据结果集
			rs = getRs(ctx,conn,ps,sqlbuff.toString());
			//对账单结果集
			duizhangrs = getRs(ctx,conn,ps,duizhangSqlBuff.toString());
			//补交结果集
			bjrs = getRs(ctx,conn,ps,bjsqlbuff.toString());
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
		//2.遍历收据的结果集
		try {
			while(rs.next()){
				//获取支付方式
				String stri = "";
				try {
					stri = rs.getString("billrectypeno");
				} catch (SQLException e1) {
					// 中间表中一定存在收费项目，不做处理
					e1.printStackTrace();
				}
				//业务单据编码
				String billNo = rs.getString("billreceiptno");
				i = Integer.parseInt(stri);
				//收费项目编码
				String feeitemNo = rs.getString("feeitemNo");
				//4.2	治疗业务对接流程图（现金/支票/个人转账）   收据-->中间表-->应收单/收款单
				if(i == PayTypeEnum.XIANJIN_VALUE || i == PayTypeEnum.ZHIPIAO_VALUE || i == PayTypeEnum.GERENZHUANZHANG_VALUE
						|| i == PayTypeEnum.YIBAO_VALUE || i == PayTypeEnum.GONGDUIGONG_VALUE || i == PayTypeEnum.BAOXIAN_VALUE
						|| (i == PayTypeEnum.QUDAO_VALUE && "渠道付款".equalsIgnoreCase(rs.getString("othbizType")))
						|| i == PayTypeEnum.ZHIFUBAO_VALUE || i == PayTypeEnum.WEIXIN_VALUE || i == PayTypeEnum.GUONEISHUAKA_VALUE || i == PayTypeEnum.GUOWAISHUAKA_VALUE
						|| i == PayTypeEnum.YUE_VALUE ){
					try {
						getYingshouMap(ctx, rs);
					} catch (Exception e) {
						// 结果集转换到应收的MAP
						msg = "收据编号："+billNo+"   错误信息："+e.getMessage()+"\n";
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
				//4.12	会员卡
				if(i == PayTypeEnum.HUIYUANKA_VALUE ){
					//"999"开头属于会员卡充值流程
					if(feeitemNo.startsWith("999")){
						try {
							getShoukuanMap(ctx, rs);
						} catch (Exception e) {
							msg = "收据编号："+billNo+"   错误信息："+e.getMessage()+"\n";
					    	logEntryInfo = new SyncLogEntryInfo();
							logEntryInfo.setId(BOSUuid.create("3575EC2D"));
							logEntryInfo.setLoginfo(msg);
							logEntryInfo.setParent(logInfo);
							logInfo.getEntrys().add(logEntryInfo);
						}
					}else{
						try {
							getYingshouMap(ctx, rs);
						} catch (Exception e) {
							msg = "收据编号："+billNo+"   错误信息："+e.getMessage()+"\n";
					    	logEntryInfo = new SyncLogEntryInfo();
							logEntryInfo.setId(BOSUuid.create("3575EC2D"));
							logEntryInfo.setLoginfo(msg);
							logEntryInfo.setParent(logInfo);
							logInfo.getEntrys().add(logEntryInfo);
						}
						try {
							getShoukuanMap(ctx, rs);
						} catch (Exception e) {
							msg = "收据编号："+billNo+"   错误信息："+e.getMessage()+"\n";
					    	logEntryInfo = new SyncLogEntryInfo();
							logEntryInfo.setId(BOSUuid.create("3575EC2D"));
							logEntryInfo.setLoginfo(msg);
							logEntryInfo.setParent(logInfo);
							logInfo.getEntrys().add(logEntryInfo);
						}
					}
				}
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		//对账单
		try {
			while(duizhangrs.next()){
				String stri = "";
				try {
					stri = duizhangrs.getString("billrectypeno");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//业务单据编码
				String billNo = duizhangrs.getString("billno");
				i = Integer.parseInt(stri);
				if(i == PayTypeEnum.YIBAO_VALUE || i == PayTypeEnum.GONGDUIGONG_VALUE || i == PayTypeEnum.BAOXIAN_VALUE){
					try {
						getShoukuanMapFromDuizhang(ctx,duizhangrs);
					} catch (Exception e) {
						msg = "对账单编号："+billNo+"   错误信息："+e.getMessage()+"\n";
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}else if(i == PayTypeEnum.QUDAO_VALUE || i == PayTypeEnum.ZHIFUBAO_VALUE || i == PayTypeEnum.WEIXIN_VALUE || i == PayTypeEnum.GUONEISHUAKA_VALUE || i == PayTypeEnum.GUOWAISHUAKA_VALUE){
					try {
						getShoukuanMapFromDuizhang(ctx, duizhangrs);
					} catch (Exception e) {
						msg = "对账单编号："+billNo+"   错误信息："+e.getMessage()+"\n";
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
					try {
						getYingfuMapFromDuizhang(ctx, duizhangrs);
					} catch (Exception e) {
						msg = "对账单编号："+billNo+"   错误信息："+e.getMessage()+"\n";
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}else{
					try {
						getYingfuMapFromDuizhang(ctx, duizhangrs);
					} catch (Exception e) {
						msg = "对账单编号："+billNo+"   错误信息："+e.getMessage()+"\n";
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		//补交
		try {
			while(bjrs.next()){
				//获取支付方式
				String stri = "";
				try {
					stri = bjrs.getString("billrectypeno");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//业务单据编码
				String billNo = bjrs.getString("billreceiptno");
				i = Integer.parseInt(stri);
				//收费项目编码
				String feeitemNo = bjrs.getString("feeitemNo");
				//现金
				if(i == PayTypeEnum.XIANJIN_VALUE ){
					try {
						getShoukuanMap(ctx, bjrs);
					} catch (Exception e) {
						msg = "收据编号："+billNo+"   错误信息："+e.getMessage()+"\n";
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
				//医保/商保/公对公转账
				if(i == PayTypeEnum.YIBAO_VALUE || i == PayTypeEnum.GONGDUIGONG_VALUE || i == PayTypeEnum.BAOXIAN_VALUE ){
					try {
						getBujiaoMap(ctx, bjrs);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//遍历应收Map并保存
		IOtherBill iob = null;
		try {
			iob = OtherBillFactory.getLocalInstance(ctx);
//			iob = OtherBillFactory.getRemoteInstance();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
    	IReceivingBill irb = null;
		try {
			irb = ReceivingBillFactory.getLocalInstance(ctx);
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		if(yingshouMap.size() > 0){
	    	Set pkset = new HashSet();
			for (Iterator localIterator = this.yingshouMap.keySet().iterator(); localIterator.hasNext(); ) 
		    {
		    	Object key = localIterator.next();
		    	//费用项目为全科时，累加全科分录的应收作为整单的应收
		    	BigDecimal yingshouAmount = BigDecimal.ZERO;
		    	OtherBillInfo otherBillInfo = (OtherBillInfo)this.yingshouMap.get(key);
		    	//当获取的应收单分录的费用项目有全科和非全科的费用类型时，只取费用类型为全科的进行累加生成应收
		    	OtherBillentryCollection entryInfoCollection = (OtherBillentryCollection) otherBillInfo.getEntries();
		    	OtherBillPlanInfo planInfo = ((OtherBillPlanCollection) otherBillInfo.getPlans()).get(0);
		    	if(entryInfoCollection != null && entryInfoCollection.size()>0){
		    		for (int j = 0; j < entryInfoCollection.size(); j++) {
		    			OtherBillentryInfo entryInfo = entryInfoCollection.get(j);
		    			OperationTypeInfo operationTypeInfo = null;
						try {
							operationTypeInfo = getOperationTypeInfoF7FromID(ctx,entryInfo.getExpenseItem().getOperationId().toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
		    			if(operationTypeInfo.getName().equals("全科")){
		    				yingshouAmount = yingshouAmount.add(entryInfo.getAmount());
		    			}else{
		    				entryInfoCollection.remove(entryInfo);
		    				j = j-1;
		    			}
					}
		    		if(yingshouAmount == BigDecimal.ZERO){
		    			continue;
		    		}
		    		//金额本位币
		    		otherBillInfo.setTotalAmountLocal(yingshouAmount);
		    		//价税合计（费用金额合计）
		    		otherBillInfo.setTotalTaxAmount(yingshouAmount);
		    		//金额合计
		    		otherBillInfo.setTotalAmount(yingshouAmount);
		    		//未结算本位币金额
		    		otherBillInfo.setUnVerifyAmountLocal(yingshouAmount);
		    		//未结算金额
		    		otherBillInfo.setUnVerifyAmount(yingshouAmount);
		    		//应收（付）金额本位币
		    		otherBillInfo.setAmountLocal(yingshouAmount);
		    		//应收（付）金额
		    		otherBillInfo.setAmount(yingshouAmount);
			    	//应收应付金额
			    	planInfo.setRecievePayAmount(yingshouAmount);
			    	//应收应付金额本位币
			    	planInfo.setRecievePayAmountLocal(yingshouAmount);
			    	//未锁定金额
			    	planInfo.setUnLockAmount(yingshouAmount);
			    	//未锁定金额本位币
			    	planInfo.setUnLockAmountLoc(yingshouAmount);
			    	//未结算金额
			    	planInfo.setUnVerifyAmount(yingshouAmount);
			    	//未结算金额本位币
			    	planInfo.setUnVerifyAmountLocal(yingshouAmount);
		    	}
		    	//保存应收单
		    	IObjectPK pk = null;
				try {
					pk = iob.save(otherBillInfo);
					//提交
					iob.submit(pk);
					//审核
					iob.audit(pk);
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
		    	//下推收款单
		    	if(otherBillInfo.getAbstractName().equalsIgnoreCase("现金") 
		    			|| otherBillInfo.getAbstractName().equalsIgnoreCase("支票")
		    			|| otherBillInfo.getAbstractName().equalsIgnoreCase("个人转账")){
		    		IObjectPK repk = null;
					try {
						repk = createReceivingBill(ctx,pk,false,"AR008AUTO");
					} catch (EASBizException e) {
						msg = "应收单下推收款单出错 "+otherBillInfo.getCompany().getName()+" 错误信息："+e.getMessage()+"\n";
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					} catch (BOSException e) {
						msg = "应收单下推收款单出错 "+otherBillInfo.getCompany().getName()+" 错误信息："+e.getMessage()+"\n";
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
					if(repk != null){
						pkset.add(repk.toString());
					}
		    	}
		    	//下推应收单
		    	if(otherBillInfo.getAbstractName().equalsIgnoreCase("商保") 
		    			|| otherBillInfo.getAbstractName().equalsIgnoreCase("保险")
		    			|| otherBillInfo.getAbstractName().equalsIgnoreCase("公对公转账")
		    			|| otherBillInfo.getAbstractName().equalsIgnoreCase("渠道付款")
		    			|| otherBillInfo.getAbstractName().equalsIgnoreCase("会员卡")){
		    		try {
						IObjectPK botpPK = createOtherBill(ctx,pk,false,"AR012AUTO");
					} catch (EASBizException e) {
						msg = "应收单下推应收单出错 "+otherBillInfo.getNumber()+" 错误信息："+e.getMessage()+"\n";
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					} catch (BOSException e) {
						msg = "应收单下推应收单出错 "+otherBillInfo.getNumber()+" 错误信息："+e.getMessage()+"\n";
				    	logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
		    	}
		    }
	    	//审核收款单
	    	if(pkset.size()>0){
	    		try {
	    			//审核
					irb.audit(pkset);
					//收款
					irb.rec(pkset);
				} catch (EASBizException e) {
					msg = "收款单处理出错   错误信息："+e.getMessage()+"\n";
			    	logEntryInfo = new SyncLogEntryInfo();
					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
					logEntryInfo.setLoginfo(msg);
					logEntryInfo.setParent(logInfo);
					logInfo.getEntrys().add(logEntryInfo);
				} catch (BOSException e) {
					msg = "收款单处理出错   错误信息："+e.getMessage()+"\n";
			    	logEntryInfo = new SyncLogEntryInfo();
					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
					logEntryInfo.setLoginfo(msg);
					logEntryInfo.setParent(logInfo);
					logInfo.getEntrys().add(logEntryInfo);
				}
	    	}
		}
		
		//遍历收款MAP
		if(shoukuanMap.size() > 0){
			Set botppkset = new HashSet();
			for (Iterator localIterator = this.shoukuanMap.keySet().iterator(); localIterator.hasNext(); ) 
		    {
		    	Object key = localIterator.next();
		    	ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo)this.shoukuanMap.get(key);
			    try {
					Set pkset = new HashSet();
			    	//保存收款单
			    	IObjectPK pk = irb.save(receivingBillInfo);
					//提交
			    	irb.submitBill(pk);
			    	pkset.add(pk.toString());
			    	//审核
					irb.audit(pkset);
					//收款
					irb.rec(pkset);
			    	//下推收款单
			    	if(receivingBillInfo.getDescription().equalsIgnoreCase("会员卡")){
			    		IObjectPK botpPK = receiveCreateReceivingBill(ctx,pk,false,"AR36AUTO");
			    		botppkset.add(botpPK);
			    	}
				} catch (EASBizException e) {
					msg = "收款单处理出错   错误信息："+e.getMessage()+"\n";
			    	logEntryInfo = new SyncLogEntryInfo();
					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
					logEntryInfo.setLoginfo(msg);
					logEntryInfo.setParent(logInfo);
					logInfo.getEntrys().add(logEntryInfo);
				} catch (BOSException e) {
					msg = "收款单处理出错   错误信息："+e.getMessage()+"\n";
			    	logEntryInfo = new SyncLogEntryInfo();
					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
					logEntryInfo.setLoginfo(msg);
					logEntryInfo.setParent(logInfo);
					logInfo.getEntrys().add(logEntryInfo);
				}
		    }
	    	//审核收款单
	    	if(botppkset.size()>0){
				try {
		    		//审核
					irb.audit(botppkset);
					//收款
					irb.rec(botppkset);
				} catch (EASBizException e) {
					msg = "收款单处理出错   错误信息："+e.getMessage()+"\n";
			    	logEntryInfo = new SyncLogEntryInfo();
					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
					logEntryInfo.setLoginfo(msg);
					logEntryInfo.setParent(logInfo);
					logInfo.getEntrys().add(logEntryInfo);
				} catch (BOSException e) {
					msg = "收款单处理出错   错误信息："+e.getMessage()+"\n";
			    	logEntryInfo = new SyncLogEntryInfo();
					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
					logEntryInfo.setLoginfo(msg);
					logEntryInfo.setParent(logInfo);
					logInfo.getEntrys().add(logEntryInfo);
				}
	    	}
		}

		//遍历应付
		com.kingdee.eas.fi.ap.IOtherBill yingfu = null;
		try {
			yingfu = com.kingdee.eas.fi.ap.OtherBillFactory.getLocalInstance(ctx);
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(yingfuMap.size() > 0){
			for (Iterator localIterator = this.yingfuMap.keySet().iterator(); localIterator.hasNext(); ) 
		    {
		    	Object key = localIterator.next();
			    try {
			    	//保存应收单
			    	IObjectPK pk = yingfu.save((com.kingdee.eas.fi.ap.OtherBillInfo)this.yingfuMap.get(key));
					//提交
			    	yingfu.submit(pk);
			    	//审核
			    	yingfu.audit(pk);
				} catch (EASBizException e) {
					msg = "应付单处理出错   错误信息："+e.getMessage()+"\n";
			    	logEntryInfo = new SyncLogEntryInfo();
					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
					logEntryInfo.setLoginfo(msg);
					logEntryInfo.setParent(logInfo);
					logInfo.getEntrys().add(logEntryInfo);
				} catch (BOSException e) {
					msg = "应付单处理出错   错误信息："+e.getMessage()+"\n";
			    	logEntryInfo = new SyncLogEntryInfo();
					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
					logEntryInfo.setLoginfo(msg);
					logEntryInfo.setParent(logInfo);
					logInfo.getEntrys().add(logEntryInfo);
				}
		    }
		}

		//遍历补交MAP集合
		if(this.bujiaoMap.size()>0){
	    	Set pkset = new HashSet();
			for (Iterator localIterator = this.bujiaoMap.keySet().iterator(); localIterator.hasNext();) 
		    {
		    	Object key = localIterator.next();
		    	BigDecimal yingshouAmount = BigDecimal.ZERO;
		    	OtherBillInfo otherBillInfo = (OtherBillInfo)this.bujiaoMap.get(key);
		    	OtherBillentryCollection entryInfoCollection = (OtherBillentryCollection) otherBillInfo.getEntries();
		    	OtherBillPlanInfo planInfo = ((OtherBillPlanCollection) otherBillInfo.getPlans()).get(0);
		    	if(entryInfoCollection != null && entryInfoCollection.size()>0){
		    		for (int j = 0; j < entryInfoCollection.size(); j++) {
		    			OtherBillentryInfo entryInfo = entryInfoCollection.get(j);
		    			String chargedetailid = null;
		    			OperationTypeInfo operationTypeInfo = null;
						try {
							operationTypeInfo = getOperationTypeInfoF7FromID(ctx,entryInfo.getExpenseItem().getOperationId().toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
		    			if(operationTypeInfo.getName().equals("全科") && (entryInfo.get("etrcharge_detail_id") != null)){
		    				yingshouAmount = yingshouAmount.add(entryInfo.getAmount());
		    				chargedetailid = entryInfo.get("etrcharge_detail_id").toString();
		    				//更新分录的值
		    				try {
								updateEntryinfo(ctx,entryInfo);
							} catch (Exception e) {
								msg = "补交应收单处理出错   错误信息："+e.getMessage()+"\n";
						    	logEntryInfo = new SyncLogEntryInfo();
								logEntryInfo.setId(BOSUuid.create("3575EC2D"));
								logEntryInfo.setLoginfo(msg);
								logEntryInfo.setParent(logInfo);
								logInfo.getEntrys().add(logEntryInfo);
							}
		    				//获取原单
							OtherBillInfo otherBillInfoY = new  OtherBillInfo();
							try {
								otherBillInfoY = getARotherbillinfo(ctx, chargedetailid);
							} catch (Exception e) {
								msg = "补交应收单处理出错   错误信息："+e.getMessage()+"\n";
						    	logEntryInfo = new SyncLogEntryInfo();
								logEntryInfo.setId(BOSUuid.create("3575EC2D"));
								logEntryInfo.setLoginfo(msg);
								logEntryInfo.setParent(logInfo);
								logInfo.getEntrys().add(logEntryInfo);
							}
		    				//BOTP生成应收
							try {
								IObjectPK botpPK = createOtherBill(ctx,new ObjectUuidPK(otherBillInfoY.getId()),false,"AR012BUJIAO");
								clearEntryinfo(ctx,entryInfo);
							} catch (EASBizException e) {
								msg = "补交应收单处理出错   错误信息："+e.getMessage()+"\n";
						    	logEntryInfo = new SyncLogEntryInfo();
								logEntryInfo.setId(BOSUuid.create("3575EC2D"));
								logEntryInfo.setLoginfo(msg);
								logEntryInfo.setParent(logInfo);
								logInfo.getEntrys().add(logEntryInfo);
							} catch (BOSException e) {
								msg = "补交应收单处理出错   错误信息："+e.getMessage()+"\n";
						    	logEntryInfo = new SyncLogEntryInfo();
								logEntryInfo.setId(BOSUuid.create("3575EC2D"));
								logEntryInfo.setLoginfo(msg);
								logEntryInfo.setParent(logInfo);
								logInfo.getEntrys().add(logEntryInfo);
							} catch (Exception e) {
								msg = "补交应收单处理出错   错误信息："+e.getMessage()+"\n";
						    	logEntryInfo = new SyncLogEntryInfo();
								logEntryInfo.setId(BOSUuid.create("3575EC2D"));
								logEntryInfo.setLoginfo(msg);
								logEntryInfo.setParent(logInfo);
								logInfo.getEntrys().add(logEntryInfo);
							}
		    			}
					}
				}
		    }
		}
	    try {
			closeConn(conn,ps,rs);
		} catch (SQLException e) {
			msg = "关闭中间库连接异常   错误信息："+e.getMessage()+"\n";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		} catch (ClassNotFoundException e) {
			msg = "关闭中间库连接异常   错误信息："+e.getMessage()+"\n";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
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
				e.printStackTrace();
			}
		}
    }
    
    //根据SQL获取中间库数据集
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
     * 根据BOTP规则的编码下推收款单
     * @param ctx
     * @param pk
     * @param isSave
     * @throws BOSException
     * @throws EASBizException
     */
    public static IObjectPK  createReceivingBill(Context ctx, IObjectPK pk, boolean isSave,String botpNumber)
    throws BOSException, EASBizException
    {
    	IOtherBill iob = OtherBillFactory.getLocalInstance(ctx);
    	OtherBillInfo otherBillInfo = iob.getOtherBillInfo(pk);
//    	ObjectUuidPK pk2 = new ObjectUuidPK(pk.toString());
    	//	获取源单值对象
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
    	// 获取可用的BOTP规则
//    	DefineSysEnum defineSys =DefineSysEnum.getEnum(DefineSysEnum.BTP_VALUE); 
    	//收款单BOSTYPE
//    	String receivingBillType = "FA44FD5B";
    	BOTMappingInfo mapping = BOTMappingFactory.getLocalInstance(ctx).getBOTMappingInfo("select *, extRule.* where name='"+ botpNumber +"' ");
//    	BOTMappingInfo mapping = BOTMappingHelper.getMapping(ctx, model, receivingBillType, defineSys);
    	// 获取BOTP引擎服务实例
    	IBTPManager ibtpManager = BTPManagerFactory.getLocalInstance(ctx);
        // 执行BOTP转换
    	BTPTransformResult btpResult = ibtpManager.transform((CoreBillBaseInfo)otherBillInfo, mapping);
    	// 获取转换结果
    	IObjectCollection destCol = btpResult.getBills();
    	
    	ReceivingBillInfo receivingBill = (ReceivingBillInfo)destCol.getObject(0);
    	// 保存生成的目标单据及BOTP关联关系
//    	ibtpManager.saveRelations(receivingBill, btpResult.getBOTRelationCollection());
    	IObjectPK repk = ibtpManager.submitRelations(receivingBill, btpResult.getBOTRelationCollection());
//    	可能会存在提交即审核 需要判断
//		if(!checkBillIsAudit(corebaseInfo)){
//			tarbillBase.getClass().getMethod("audit", IObjectPK.class).invoke(tarbillBase, pk);
//    	}
    	return repk;
    }
    /**
     * 收款单根据BOTP规则的编码下推收款单
     * @param ctx
     * @param pk
     * @param isSave
     * @throws BOSException
     * @throws EASBizException
     */
    public static IObjectPK  receiveCreateReceivingBill(Context ctx, IObjectPK pk, boolean isSave,String botpNumber)
    throws BOSException, EASBizException
    {
    	IReceivingBill iob = ReceivingBillFactory.getLocalInstance(ctx);
    	ReceivingBillInfo receivingBillInfo = iob.getReceivingBillInfo(pk);
//    	ObjectUuidPK pk2 = new ObjectUuidPK(pk.toString());
    	//	获取源单值对象
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
    	// 获取可用的BOTP规则
//    	DefineSysEnum defineSys =DefineSysEnum.getEnum(DefineSysEnum.BTP_VALUE); 
    	//收款单BOSTYPE
//    	String receivingBillType = "FA44FD5B";
    	BOTMappingInfo mapping = BOTMappingFactory.getLocalInstance(ctx).getBOTMappingInfo("select *, extRule.* where name='"+ botpNumber +"' ");
//    	BOTMappingInfo mapping = BOTMappingHelper.getMapping(ctx, model, receivingBillType, defineSys);
    	// 获取BOTP引擎服务实例
    	IBTPManager ibtpManager = BTPManagerFactory.getLocalInstance(ctx);
        // 执行BOTP转换
    	BTPTransformResult btpResult = ibtpManager.transform((CoreBillBaseInfo)receivingBillInfo, mapping);
    	// 获取转换结果
    	IObjectCollection destCol = btpResult.getBills();
    	
    	ReceivingBillInfo receivingBill = (ReceivingBillInfo)destCol.getObject(0);
    	// 保存生成的目标单据及BOTP关联关系
//    	ibtpManager.saveRelations(receivingBill, btpResult.getBOTRelationCollection());
    	IObjectPK repk = ibtpManager.submitRelations(receivingBill, btpResult.getBOTRelationCollection());
//    	可能会存在提交即审核 需要判断
//		if(!checkBillIsAudit(corebaseInfo)){
//			tarbillBase.getClass().getMethod("audit", IObjectPK.class).invoke(tarbillBase, pk);
//    	}
    	return repk;
    }
    /**
     * 根据BOTP规则的编码下推应收单
     * @param ctx
     * @param pk
     * @param isSave
     * @throws BOSException
     * @throws EASBizException
     */
    public static IObjectPK  createOtherBill(Context ctx, IObjectPK pk, boolean isSave,String botpNumber)
    throws BOSException, EASBizException
    {
    	IOtherBill iob = OtherBillFactory.getLocalInstance(ctx);
    	OtherBillInfo otherBillInfo = iob.getOtherBillInfo(pk);
//    	ObjectUuidPK pk2 = new ObjectUuidPK(pk.toString());
    	//	获取源单值对象
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
    	// 获取可用的BOTP规则
//    	DefineSysEnum defineSys =DefineSysEnum.getEnum(DefineSysEnum.BTP_VALUE); 
    	//收款单BOSTYPE
//    	String receivingBillType = "FA44FD5B";
    	BOTMappingInfo mapping = BOTMappingFactory.getLocalInstance(ctx).getBOTMappingInfo("select *, extRule.* where name='"+ botpNumber +"' ");
//    	BOTMappingInfo mapping = BOTMappingHelper.getMapping(ctx, model, receivingBillType, defineSys);
    	// 获取BOTP引擎服务实例
    	IBTPManager ibtpManager = BTPManagerFactory.getLocalInstance(ctx);
        // 执行BOTP转换
    	BTPTransformResult btpResult = ibtpManager.transform((CoreBillBaseInfo)otherBillInfo, mapping);
    	// 获取转换结果
    	IObjectCollection destCol = btpResult.getBills();
    	
//    	ReceivingBillInfo receivingBill = (ReceivingBillInfo)destCol.getObject(0);
    	OtherBillInfo otherBillInfo1 =(OtherBillInfo) destCol.getObject(0);
    	// 保存生成的目标单据及BOTP关联关系
//    	ibtpManager.saveRelations(receivingBill, btpResult.getBOTRelationCollection());
    	IObjectPK repk = ibtpManager.submitRelations(otherBillInfo1, btpResult.getBOTRelationCollection());
//    	可能会存在提交即审核 需要判断
//		if(!checkBillIsAudit(corebaseInfo)){
//			tarbillBase.getClass().getMethod("audit", IObjectPK.class).invoke(tarbillBase, pk);
//    	}
    	return repk;
    }

    //应收MAP
    protected void getYingshouMap (Context ctx,ResultSet rs) throws Exception{
    	//*********表头*************
    	String billid = rs.getString("billid");
    	String entryID = rs.getString("entryID");
    	//两者合在一起做唯一标识
    	String mapid = billid+entryID;
    	//公司
		String billcompany = rs.getString("billcompany");
		//业务单据编码
		String billNo = rs.getString("billreceiptno");
		//单据日期
		Date billdate = rs.getDate("billdate");
		//收费方式编码
		String billrectypeno = rs.getString("billrectypeno");
		int i = Integer.parseInt(billrectypeno);
		//收费方式名称
		String billrectypename = rs.getString("billrectypename");
		//往来户
		String billaccount = rs.getString("billaccount");
		//业务系统付款ID
		String billchargeid = rs.getString("billchargeid");
		//业务日期
		Date billbizdate = rs.getDate("billbizdate");
		//病历归属门诊号
		String billrecordcompany = rs.getString("billrecordcompany");
		//总应付
		BigDecimal billtatalpay = rs.getBigDecimal("billtatalpay");
		//总实付
		BigDecimal billtatalactualpay = rs.getBigDecimal("billtatalactualpay");
		//会员卡号
		String billVipNo = rs.getString("billVipNo");
		//sd_channel  需要转移的客户
		String billchannel = rs.getString("billchannel");
		//--根据中间表客户编码取客户信息--
		CustomerInfo othcustomerInfo = null;
		if(i == PayTypeEnum.YIBAO_VALUE || i == PayTypeEnum.BAOXIAN_VALUE || i == PayTypeEnum.GONGDUIGONG_VALUE
			|| i == PayTypeEnum.QUDAO_VALUE){
			othcustomerInfo = getOthcustomerInfoF7(ctx,billchannel);
		}
		if("会员卡".equalsIgnoreCase(billrectypename)){
			othcustomerInfo = getCustomerInfoF7(ctx,"A10");
		}
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String billbizdateStr = sdf.format(billbizdate);
		int year = Integer.parseInt(billbizdateStr.substring(0,4));
		int month = Integer.parseInt(billbizdateStr.substring(5,7));
		//*********分录*************
		//费用项目编码
		String entryexpenseno = rs.getString("entryexpenseno");
		//费用项目名称
		String entryexpensename = rs.getString("entryexpensename");
		//数量
		BigDecimal entryqty = rs.getBigDecimal("entryqty");
		//应收金额
		BigDecimal entryaramount = rs.getBigDecimal("entryaramount");
		//金额（实收）
		BigDecimal entryamount = rs.getBigDecimal("entryamount");
		//分录收款ID
		String entrychargeid = rs.getString("entrychargeid");
		//分录收款明细ID
		String entrychargedetailid = rs.getString("entrychargedetailid");
		//*********公共*************
		BigDecimal exchangeRate = new BigDecimal(1.00);
		BigDecimal zero = new BigDecimal(0.00);
		BigDecimal one = new BigDecimal(1.00);    	
		OtherBillInfo otherBillInfo = null;
		OrgmapInfo orgmapInfo = getOrgmapInfoF7(ctx, billcompany);
		CompanyOrgUnitInfo companyInfo =  orgmapInfo.getOrg();
    	if(this.yingshouMap.get(mapid) == null ){
    		otherBillInfo = new OtherBillInfo(); 
    		//摘要
    		otherBillInfo.setAbstractName(billrectypename);
//    		getCompanyOrgUnitInfoF7(ctx,billcompany);
    		//财务组织
    		otherBillInfo.setCompany(companyInfo);
    		//管理单元
    		otherBillInfo.setCU(companyInfo.getCU());
    		//业务日期
    		otherBillInfo.setBizDate(billbizdate);
    		//币别
    		otherBillInfo.setCurrency(getCurrencyInfoF7(ctx,"BB01"));
    		//单据日期
    		otherBillInfo.setBillDate(billdate);
    		//汇率
    		otherBillInfo.setExchangeRate(exchangeRate);
    		//单据类型
    		otherBillInfo.setBillType(OtherBillTypeEnum.getEnum(OtherBillTypeEnum.EXPENSEINVOICE_VALUE));
    		//往来类型
    		otherBillInfo.setAsstActType(getAsstActTypeInfoF7(ctx,"客户"));
    		//客户
    		CustomerInfo customerInfo = getCustomerInfoF7(ctx,billaccount);
    		//往来户ID
    		otherBillInfo.setAsstActID(customerInfo.getId().toString());
    		//往来户名称
    		otherBillInfo.setAsstActName(customerInfo.getName());
    		//往来户编码
    		otherBillInfo.setAsstActNumber(customerInfo.getNumber());
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
    		otherBillInfo.setSaleOrg(getSaleOrgUnitInfoF7(ctx,billcompany));
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
    		otherBillInfo.setTotalAmountLocal(billtatalpay);
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
    		otherBillInfo.setTotalTaxAmount(billtatalpay);
    		//税额合计
    		otherBillInfo.setTotalTax(zero);
    		//金额合计
    		otherBillInfo.setTotalAmount(billtatalpay);
    		//最后调汇汇率
    		otherBillInfo.setLastExhangeRate(one);
    		//付款方式
    		otherBillInfo.setPaymentType(getPaymentTypeInfoF7(ctx,"赊销"));
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
    		otherBillInfo.setUnVerifyAmountLocal(billtatalpay);
    		//未结算金额
    		otherBillInfo.setUnVerifyAmount(billtatalpay);
    		//已结算金额本位币
    		otherBillInfo.setVerifyAmountLocal(zero);
    		//已结算金额
    		otherBillInfo.setVerifyAmount(zero);
    		//应收（付）金额本位币
    		otherBillInfo.setAmountLocal(billtatalpay);
    		//应收（付）金额
    		otherBillInfo.setAmount(billtatalpay);
    		//最后修改时间
    		otherBillInfo.setLastUpdateTime(new Timestamp(billbizdate.getTime()));
    		//最后修改者
    		otherBillInfo.setLastUpdateUser(getUserInfoF7(ctx,"user"));
    		//创建时间
    		otherBillInfo.setCreateTime(new Timestamp(billbizdate.getTime()));
    		//创建者
    		otherBillInfo.setCreator(getUserInfoF7(ctx,"user"));
    		
    		otherBillInfo.put("othcustomer",othcustomerInfo);
    		otherBillInfo.put("charge_id",billchargeid);
    		
    		this.yingshouMap.put(mapid, otherBillInfo);

    		//收款计划
	    	OtherBillPlanInfo planInfo = new OtherBillPlanInfo();
	    	//应收应付日期
	    	planInfo.setRecievePayDate(billdate);
	    	//应收应付金额
	    	planInfo.setRecievePayAmount(billtatalpay);
	    	//应收应付金额本位币
	    	planInfo.setRecievePayAmountLocal(billtatalpay);
	    	//未锁定金额
	    	planInfo.setUnLockAmount(billtatalpay);
	    	//未锁定金额本位币
	    	planInfo.setUnLockAmountLoc(billtatalpay);
	    	//已锁定金额
	    	planInfo.setLockAmount(zero);
	    	//已锁定金额本位币
	    	planInfo.setLockAmountLoc(zero);
	    	//未结算金额
	    	planInfo.setUnVerifyAmount(billtatalpay);
	    	//未结算金额本位币
	    	planInfo.setUnVerifyAmountLocal(billtatalpay);
	    	
	    	planInfo.setParent(otherBillInfo);
	    	otherBillInfo.getRecievePlan().add(planInfo);
    	}else{
    		otherBillInfo = (OtherBillInfo) yingshouMap.get(mapid);
    	}
//分录
    	OtherBillentryInfo otherBillentryInfo = new OtherBillentryInfo();
    	CustomerInfo customerInfo = getCustomerInfoF7(ctx,billaccount);
    	//是否完全核销
    	otherBillentryInfo.setIsFullWriteOff(false);
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
    	//收款往来类型
    	otherBillentryInfo.setRecAsstActType(getAsstActTypeInfoF7(ctx,"客户"));
    	//送货客户
    	otherBillentryInfo.setServiceCustomer(customerInfo);
    	//订货客户
    	otherBillentryInfo.setOrderCustomer(customerInfo);
    	//计提坏帐准备金额本位币
    	otherBillentryInfo.setPreparedBadAmountLocal(zero);
    	//计提坏帐准备金额
    	otherBillentryInfo.setPreparedBadAmount(zero);
    	//坏账金额本位币
    	otherBillentryInfo.setBadAmountLocal(zero);
    	//坏账金额
    	otherBillentryInfo.setBadAmount(zero);
    	//未开票申请金额本位币
    	otherBillentryInfo.setUnInvoiceReqAmountLocal(entryaramount);
    	//未开票申请金额
    	otherBillentryInfo.setUnInvoiceReqAmount(entryaramount);
    	//已开票申请金额本位币
    	otherBillentryInfo.setInvoiceReqAmountLocal(zero);
    	//已开票申请金额
    	otherBillentryInfo.setInvoiceReqAmount(zero);
    	//未开票申请基本数量
    	otherBillentryInfo.setUnInvoiceReqBaseQty(entryqty);
    	//未开票申请数量
    	otherBillentryInfo.setUnInvoiceReqQty(entryqty);
    	//已开票申请基本数量
    	otherBillentryInfo.setInvoiceReqBaseQty(zero);
    	//已开票申请数量
    	otherBillentryInfo.setInvoiceReqQty(zero);
    	//零数量
    	otherBillentryInfo.setIsQtyZero(false);
    	//单据日期
    	otherBillentryInfo.setBillDate(billdate);
    	//公司
    	otherBillentryInfo.setCompany(companyInfo.getId().toString());
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
    	otherBillentryInfo.setLocalUnwriteOffAmount(entryaramount);
    	//未核销基本数量
    	otherBillentryInfo.setUnwriteOffBaseQty(entryqty);
    	//已核销本位币金额
    	otherBillentryInfo.setLocalWrittenOffAmount(zero);
    	//已核销基本数量
    	otherBillentryInfo.setWittenOffBaseQty(zero);
    	//历史未核销金额本位币
    	otherBillentryInfo.setHisUnVerifyAmountLocal(zero);
    	//历史未核销金额
    	otherBillentryInfo.setHisUnVerifyAmount(zero);
    	//基本计量单位数量
    	otherBillentryInfo.setBaseQty(entryqty);
    	//辅助数量
    	otherBillentryInfo.setAssistQty(zero);
    	//金额本位币
    	otherBillentryInfo.setAmountLocal(entryaramount);
    	//金额
    	otherBillentryInfo.setAmount(entryaramount);
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
    	otherBillentryInfo.setTaxPrice(entryaramount.divide(entryqty,2,BigDecimal.ROUND_HALF_DOWN));
    	//实际单价
    	otherBillentryInfo.setRealPrice(entryaramount.divide(entryqty,2,BigDecimal.ROUND_HALF_DOWN));
    	//单价
    	otherBillentryInfo.setPrice(entryaramount.divide(entryqty,2,BigDecimal.ROUND_HALF_DOWN));
    	//数量
    	otherBillentryInfo.setQuantity(entryqty);
    	//未锁定金额本位币
    	otherBillentryInfo.setLockUnVerifyAmtLocal(entryaramount);
    	//未锁定金额
    	otherBillentryInfo.setLockUnVerifyAmt(entryaramount);
    	//已锁定金额本位币
    	otherBillentryInfo.setLockVerifyAmtLocal(zero);
    	//已锁定金额
    	otherBillentryInfo.setLockVerifyAmt(zero);
    	//未结算金额本位币
    	otherBillentryInfo.setUnVerifyAmountLocal(entryaramount);
    	//未结算金额
    	otherBillentryInfo.setUnVerifyAmount(entryaramount);
    	//已结算金额本位币
    	otherBillentryInfo.setVerifyAmountLocal(zero);
    	//已结算金额
    	otherBillentryInfo.setVerifyAmount(zero);
    	//应收（付）金额本位币
    	otherBillentryInfo.setRecievePayAmountLocal(entryaramount);
    	//应收（付）金额
    	otherBillentryInfo.setRecievePayAmount(entryaramount);
    	//是否赠品
    	otherBillentryInfo.setIsPresent(false);
    	//费用项目
    	otherBillentryInfo.setExpenseItem(getExpenseTypeInfoF7(ctx,entryexpenseno));
    	//计量单位
//    	otherBillentryInfo.setMeasureUnit(getMeasureUnitInfoF7(ctx,"1000"));
    	//物料名称
//    	otherBillentryInfo.setMaterialName("");
    	//物料
//    	otherBillentryInfo.setMaterial(item);
    	//行类型
//    	otherBillentryInfo.setRowType(item);
//    	otherBillentryInfo.setParent(otherBillInfo);
    	otherBillentryInfo.put("etycharge_id", entrychargeid);
    	otherBillentryInfo.put("etrcharge_detail_id", entrychargedetailid);
    	//表头
    	otherBillentryInfo.setHead(otherBillInfo);
    	otherBillInfo.getEntry().add(otherBillentryInfo);
    }
    
    //收款MAP
    protected void getShoukuanMap (Context ctx,ResultSet rs) throws Exception{
    	//*********表头*************
    	//公司
		String billcompany = rs.getString("billcompany");
		//业务单据编码
		String billNo = rs.getString("billreceiptno");
		//单据日期
		Date billdate = rs.getDate("billdate");
		//收费方式编码
		String billrectypeno = rs.getString("billrectypeno");
		//收费方式名称
		String billrectypename = rs.getString("billrectypename");
		//往来户
		String billaccount = rs.getString("billaccount");
		//业务日期
		Date billbizdate = rs.getDate("billbizdate");
		//病历归属门诊号
		String billrecordcompany = rs.getString("billrecordcompany");
		//总应付
		BigDecimal billtatalpay = rs.getBigDecimal("billtatalpay");
		//总实付
		BigDecimal billtatalactualpay = rs.getBigDecimal("billtatalactualpay");
		//会员卡号
		String billVipNo = rs.getString("billVipNo");
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String billbizdateStr = sdf.format(billbizdate);
		int year = Integer.parseInt(billbizdateStr.substring(0,4));
		int month = Integer.parseInt(billbizdateStr.substring(5,7));
		//*********分录*************
		//费用项目编码
		String entryexpenseno = rs.getString("entryexpenseno");
		//费用项目名称
		String entryexpensename = rs.getString("entryexpensename");
		//数量
		BigDecimal entryqty = rs.getBigDecimal("entryqty");
		//应收金额
		BigDecimal entryaramount = rs.getBigDecimal("entryaramount");
		//金额（实收）
		BigDecimal entryamount = rs.getBigDecimal("entryamount");
		//*********公共*************
		ReceivingBillInfo receivingBillInfo = null;
		CustomerInfo customerInfo = getCustomerInfoF7(ctx,billaccount);
		CustomerInfo othcustomerInfo = null;
		if("会员卡".equalsIgnoreCase(billrectypename)){
			othcustomerInfo = getCustomerInfoF7(ctx,"A10");
		}
		OrgmapInfo orgmapInfo = getOrgmapInfoF7(ctx, billcompany);
		CompanyOrgUnitInfo companyInfo =  orgmapInfo.getOrg();
		AccountBankInfo accountBankInfo = getAccountBankInfoF7(ctx, companyInfo.getId().toString());
		if(this.shoukuanMap.get(billNo) == null ){
			receivingBillInfo = new ReceivingBillInfo(); 
//			receivingBillInfo.setrecBillClaim	收款认领信息	对象	FRecBillClaim
//			receivingBillInfo.setbillClaimStatus	单据认领状态	业务枚举	FBillClaimStatus	是	10
			receivingBillInfo.setBillClaimStatus(BillClaimStatusEnum.getEnum(BillClaimStatusEnum.UNCLAIM_VALUE));
//			receivingBillInfo.setisSmart	是否智能核算	业务枚举	FIsSmart	是	1
			receivingBillInfo.setIsSmart(SmartType.getEnum(SmartType.OPEN_VALUE));
//			receivingBillInfo.setprofitCenter	利润中心	对象	FProfitCenterID		
//			receivingBillInfo.setisHasRefundPay	是否已经生成退款支付单据	布尔	FisHasRefundPay	
			receivingBillInfo.setIsHasRefundPay(false);
//			receivingBillInfo.setisRefundmentPay	是否退款支付	布尔	FisRefundmentPay		
			receivingBillInfo.setIsRefundmentPay(false);
//			receivingBillInfo.setbankCheckStatus	银行未达状态	业务枚举	FBankCheckStatus	
//			receivingBillInfo.setprintCount	打印次数	整数	FPrintCount	是	1
			receivingBillInfo.setPrintCount(1);
//			receivingBillInfo.setpaymentType	付款方式	对象	FPaymentTypeID	是	
			receivingBillInfo.setPaymentType(getPaymentTypeInfoF7(ctx,"赊销"));
//			receivingBillInfo.setSYNBillEntryID	协同单据分录ID	字符串	FSYNBillEntryID		
//			receivingBillInfo.setSYNBillID	协同单据ID	字符串	FSYNBillID		
//			receivingBillInfo.setSYNbillNumber	协同单据编号	字符串	FSYNbillNumber		
//			receivingBillInfo.setSYNbillType	协同单据类型	字符串	FSYNbillType		
//			receivingBillInfo.setoppBgItemId	流入预算项目Id	字符串	FOppBgItemId		
//			receivingBillInfo.setoppBgItemNumber	流入预算项目编码	字符串	FOppBgItemNumber		
//			receivingBillInfo.setoppBgItemName	流入预算项目名称	字符串	FOppBgItemName		
//			receivingBillInfo.setpayAccountBank	实付银行账号	字符串	FPayAccountBank		
//			receivingBillInfo.setoppFpItem	流出计划项目	对象	FOppFpItemID		
//			receivingBillInfo.setisReverseLockAmount	是否反写锁定金额	布尔	FIsReverseLockAmount	是	1
			receivingBillInfo.setIsReverseLockAmount(true);
//			receivingBillInfo.setlastPayerType	原往来户类型	对象	FLastPayerTypeID		
//			receivingBillInfo.setlastPayerName	原往来户名称	字符串	FLastPayerName		
//			receivingBillInfo.setlastPayerNumber	原往来户编号	字符串	FLastPayerNumber		
//			receivingBillInfo.setlastPayerID	原往来户ID	字符串	FLastPayerID		
//			receivingBillInfo.setisPreReturn	单据分录收款类型是否含有预收款或者退预收款	布尔	FIsPreReturn		
//			receivingBillInfo.setisProxyReturn	单据分录收款类型是否含有代收款或者退代收款	布尔	FIsProxyReturn		
//			receivingBillInfo.setisSaleReturn	分录收款类型是否含有销售回款或者退销售回款	布尔	FIsSaleReturn		
//			receivingBillInfo.setrealRecCompany	实际收款公司	对象	FRealRecCompanyID		
//			receivingBillInfo.setrealRecBillID	代理收款实际付款公司	字符串	FRealRecBillID		
//			receivingBillInfo.setsrcRealRecBillID	代理收款源单ID	字符串	FSrcRealRecBillID		
//			receivingBillInfo.setreceivingBillType	收款单类型	业务枚举	FReceivingBillType	是	1
			receivingBillInfo.setReceivingBillType(CasRecPayBillTypeEnum.getEnum(CasRecPayBillTypeEnum.COMMONTYPE_VALUE));
//			receivingBillInfo.setassItems	对方科目核算项目	集合			
//			receivingBillInfo.setpayerAccountBankO	银行付款账户	对象	FPayerAccountBankID		
//			receivingBillInfo.setisRelateRecBook	是否已关联生成应收票据	布尔	FIsRelateRecBook		
//			receivingBillInfo.setrecBillType	收款类型	对象	FRecBillTypeID	是	
			receivingBillInfo.setRecBillType(getReceivingBillTypeInfoF7(ctx,"预收款"));
//			receivingBillInfo.setbgAmount	预算核准金额	数字	FBgAmount		
			receivingBillInfo.setBgAmount(BigDecimal.ZERO);
//			receivingBillInfo.setdeliver	交票人	对象	FDeliverID		
//			receivingBillInfo.setoppInnerAcct	内部付款账户	对象	FOppInnerAcctID		
//			receivingBillInfo.setreceipt	关联进账单	对象	FReceiptId		
//			receivingBillInfo.setisRelateReceipt	是否已关联进账单	布尔	FIsRelateReceipt		
//			receivingBillInfo.setrecDate	收款日期	日期	FRecDate	
			receivingBillInfo.setRecDate(billbizdate);
//			receivingBillInfo.setentries	收款单分录	集合			
//			receivingBillInfo.setpayerAccountBank	付款账号	字符串	FPayerAccountBank		
//			receivingBillInfo.setpayerBank	付款银行	字符串	FPayerAccountNumber		
//			receivingBillInfo.setpayerName	往来户姓名	字符串	FPayerName		
			receivingBillInfo.setPayerName(customerInfo.getName());
//			receivingBillInfo.setpayerNumber	往来户编号	字符串	FPayerNumber	
			receivingBillInfo.setPayerNumber(customerInfo.getNumber());
//			receivingBillInfo.setpayerID	往来户ID	字符串	FPayerID	
			receivingBillInfo.setPayerID(customerInfo.getId().toString());
//			receivingBillInfo.setpayerType	往来户类型	对象	FPayerTypeID	是	
			receivingBillInfo.setPayerType(getAsstActTypeInfoF7(ctx,"客户"));
//			receivingBillInfo.setpayeeAccount	收款科目	对象	FPayeeAccountID	是	
//			receivingBillInfo.setPayeeAccount(getAccountViewInfoF7(ctx,"1001",companyInfo.getId().toString()));
			receivingBillInfo.setPayeeAccount(accountBankInfo.getAccount());
//			receivingBillInfo.setpayeeAccountBank	收款账户	对象	FPayeeAccountBankID		
			receivingBillInfo.setPayeeAccountBank(accountBankInfo);
//			receivingBillInfo.setpayeeBank	收款银行	对象	FPayeeBankID
			receivingBillInfo.setPayeeBank(accountBankInfo.getBank());
//			receivingBillInfo.setactRecLocAmtVc	实收本位币金额累计核销	数字	FActRecLocAmtVc	
			receivingBillInfo.setActRecLocAmtVc(BigDecimal.ZERO);
//			receivingBillInfo.setactRecLocAmt	实收本位币金额合计	数字	FActRecLocAmt	是	
			receivingBillInfo.setActRecLocAmt(billtatalactualpay);
//			receivingBillInfo.setactRecAmtVc	实收金额累计核销	数字	FActRecAmtVc	
			receivingBillInfo.setActRecAmtVc(BigDecimal.ZERO);
//			receivingBillInfo.setactRecAmt	实收金额合计	数字	FActRecAmt	是	
			receivingBillInfo.setActRecAmt(billtatalactualpay);
//			receivingBillInfo.setrecType	收款类型（现在开始禁用）	业务枚举	FRecType		
//			receivingBillInfo.setverifyStatus	结算状态	业务枚举	FverifyStatus	是	1
			receivingBillInfo.setVerifyStatus(com.kingdee.eas.fi.cas.verifyStatusEnum.getEnum(com.kingdee.eas.fi.cas.verifyStatusEnum.SOME_VERIFIED_VALUE));
//			receivingBillInfo.setisPreVerify	是否应付单预结算锁住的单	布尔	FIsPreVerify	
			receivingBillInfo.setIsPreVerify(false);
//			receivingBillInfo.setisDiffCurSettlement	异币种关联标识	布尔	FIsDiffCurSettlement		
//			receivingBillInfo.setbgCtrlAmt	预算控制金额	数字	FBgCtrlAmt	是	
			receivingBillInfo.setBgCtrlAmt(billtatalactualpay);
//			receivingBillInfo.setbankCheckFlag	对账标识码-用于智能对账反写	字符串	FBankCheckFlag		
//			receivingBillInfo.setmbgName	辅助维度名称	字符串	FMbgname		
//			receivingBillInfo.setmbgNumber	辅助维度编码	字符串	FMbgnumber		
//			receivingBillInfo.setfundFlowItem	资金流量项目	对象	FFundFlowItemID		
//			receivingBillInfo.setpcaVouchered	是否生成利润中心凭证	布尔	FpcaVouchered		
//			receivingBillInfo.setcostCenter	成本中心	对象	FCostCenterID		
//			receivingBillInfo.setsubSettDate	提交结算中心日期	日期	FSubSettDate		
//			receivingBillInfo.setoutBgItemName	流出预算项目名称	字符串	FOutBgItemName		
//			receivingBillInfo.setoutBgItemNumber	流出预算项目编码	字符串	FOutBgItemNumber		
//			receivingBillInfo.setoutBgItemId	流出预算项目ID	字符串	FOutBgItemID		
//			receivingBillInfo.setcontractNum	合同编号	字符串	FContractNumber		
//			receivingBillInfo.setisCoopBuild	是否协同生成	布尔	FIsCoopBuild		
//			receivingBillInfo.setBillDate_SourceBill	来源单日期	日期	FBillDate_SourceBill		
//			receivingBillInfo.setAsstActTypeID_SourceBill	来源单往来类型	字符串	FAsstActTypeID_SourceBill		
//			receivingBillInfo.setAsstActID_SourceBill	来源单往来户	字符串	FAsstActID_SourceBill		
//			receivingBillInfo.setPersonID_SourceBill	来源单人员	字符串	FPersonID_SourceBill		
//			receivingBillInfo.setAdminOrgUnitId_SourceBill	来源单部门	字符串	FAdminOrgUnitId_SourceBill		
//			receivingBillInfo.setisImpFromGL	是否从总账引入的数据	布尔	FIsImpFromGL		
//			receivingBillInfo.setmixEntryVerify	混合收付款类型分录是否符合相应结算条件	整数	FMixEntryVerify		
//			receivingBillInfo.setisAppointVoucher	是否已生成指定凭证	布尔	FIsAppointVoucher		
//			receivingBillInfo.setaccepterDate	结算单受理日期	日期	FAccepterDate		
//			receivingBillInfo.setaccepter	结算单受理人	字符串	FAccepter		
//			receivingBillInfo.setapprover	一级审核人	对象	FApproverID		
//			receivingBillInfo.setapproveDate	一级审核日期	日期	FApproveDate		
//			receivingBillInfo.setunVerifiedAmtLoc	未结算金额本位币合计	数字	FUnVerifiedAmtLoc	是	
			receivingBillInfo.setUnVerifiedAmtLoc(billtatalactualpay);
//			receivingBillInfo.setverifiedAmtLoc	已结算金额本位币合计	数字	FVerifiedAmtLoc		
			receivingBillInfo.setVerifiedAmtLoc(BigDecimal.ZERO);
//			receivingBillInfo.setunVerifiedAmt	未结算金额合计	数字	FUnVerifiedAmt	是	
			receivingBillInfo.setUnVerifiedAmt(billtatalactualpay);
//			receivingBillInfo.setverifiedAmt	已结算金额合计	数字	FVerifiedAmt		
			receivingBillInfo.setVerifiedAmt(BigDecimal.ZERO);
//			receivingBillInfo.setvoucherNumber	凭证编号	字符串	FVoucherNumber		
//			receivingBillInfo.setvoucher	凭证ID	对象	FVoucherID		
//			receivingBillInfo.setvoucherType	凭证字	对象	FVoucherTypeID		
//			receivingBillInfo.setisNeedVoucher	对账方式	布尔	FIsNeedVoucher	是	1
			receivingBillInfo.setIsNeedVoucher(true);
//			receivingBillInfo.setisTransOtherBill	是否转预收转预付	布尔	FIsTransOtherBill		
			receivingBillInfo.setIsTransOtherBill(false);
//			receivingBillInfo.setisCtrlOppAcct	控制对方科目	布尔	FIsCtrlOppAcct		
//			receivingBillInfo.setfeeType	收付类别	对象	FFeeTypeID		
//			receivingBillInfo.setisTransBill	是否转销单据	布尔	FIsTransBill		
//			receivingBillInfo.setisRedBill	是否是红字单据	布尔	FIsRedBill
			receivingBillInfo.setIsRedBill(false);
//			receivingBillInfo.setbizType	业务种类	对象	FBizTypeID		
//			receivingBillInfo.setisBookRL	是否登记银行日记账	布尔	FIsBookRL		
//			receivingBillInfo.setprojectManager	项目经理	对象	FProjectManagerID		
//			receivingBillInfo.setproject	项目	对象	FProjectID		
//			receivingBillInfo.setcontractBillId	合同Id	字符串	FContractBillID		
//			receivingBillInfo.setcapitalAmount	大写金额	字符串	FCapitalAmount		
//			receivingBillInfo.setdayaccount	日记账余额	数字	FDayaccount		
//			receivingBillInfo.setcontractNo	合同编号	字符串	FContractNo		
//			receivingBillInfo.setsummary	款项说明	字符串	FSummary		
//			receivingBillInfo.setconceit	打回意见	字符串	FConceit		
//			receivingBillInfo.setaccessoryAmt	附件数	整数	FAccessoryAmt		
//			receivingBillInfo.setperson	人员	对象	FPersonId		
//			receivingBillInfo.setadminOrgUnit	部门	对象	FAdminOrgUnitId		
//			receivingBillInfo.setlocalAmt	应收（付）本位币金额	数字	FLocalAmount	是	
			receivingBillInfo.setLocalAmt(billtatalactualpay);
//			receivingBillInfo.setamount	应收（付）金额	数字	FAmount	是	
			receivingBillInfo.setAmount(billtatalactualpay);
//			receivingBillInfo.setisImport	是否导入	布尔	FIsImport	
			receivingBillInfo.setIsImport(false);
//			receivingBillInfo.setfundType	收付款方式	业务枚举	FFundType	
			receivingBillInfo.setFundType(BizTypeEnum.getEnum(BizTypeEnum.CASH_VALUE));
//			receivingBillInfo.setsettlementStatus	集中结算状态	业务枚举	FSettlementStatus	是	10
			receivingBillInfo.setSettlementStatus(SettlementStatusEnum.getEnum(SettlementStatusEnum.UNSUBMIT_VALUE));
//			receivingBillInfo.setbillStatus	单据状态	业务枚举	FBillStatus	是	10
			receivingBillInfo.setBillStatus(com.kingdee.eas.fi.cas.BillStatusEnum.getEnum(com.kingdee.eas.fi.cas.BillStatusEnum.AUDITED_VALUE));
//			receivingBillInfo.setfiVouchered	是否已生成凭证	布尔	FFiVouchered		
//			receivingBillInfo.setisInitializeBill	是否初始化单据	布尔	FIsInitializeBill		
//			receivingBillInfo.setaccountant	会计	对象	FAccountantID		
//			receivingBillInfo.setcashier	出纳	对象	FCashierID		
//			receivingBillInfo.setauditDate	审核日期	日期	FAuditDate		
//			receivingBillInfo.setsettleBizType	结算类型	业务枚举	FSettleBizType		
//			receivingBillInfo.setisCommitSettle	是否提交结算中心	布尔	FIsCommitSettle		
			receivingBillInfo.setIsCommitSettle(false);
//			receivingBillInfo.setfpItem	计划项目	对象	FFpItemID		
//			receivingBillInfo.setoppAccount	对方科目	对象	FOppAccountID		
//			receivingBillInfo.setsettlementNumber	结算号	字符串	FSettlementNumber		
//			receivingBillInfo.setsettlementType	结算方式	对象	FSettlementTypeID	是
			receivingBillInfo.setSettlementType(getSettlementTypeInfoF7(ctx,"现金"));
//			receivingBillInfo.setlastExhangeRate	最后调汇汇率	数字	FLastExhangeRate
			receivingBillInfo.setLastExhangeRate(BigDecimal.ONE);
//			receivingBillInfo.setisExchanged	是否已经调汇	布尔	FIsExchanged
			receivingBillInfo.setIsExchanged(false);
//			receivingBillInfo.setexchangeRate	汇率	数字	FExchangeRate	是	1
			receivingBillInfo.setExchangeRate(BigDecimal.ONE);
//			receivingBillInfo.setcurrency	币别	对象	FCurrencyID	是	dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC
			receivingBillInfo.setCurrency(getCurrencyInfoF7(ctx,"BB01"));
//			receivingBillInfo.setsourceType	业务系统	业务枚举	FSourceType	是	100
			receivingBillInfo.setSourceType(SourceTypeEnum.getEnum(SourceTypeEnum.AR_VALUE));
//			receivingBillInfo.setsourceSysType	来源系统	业务枚举	FSourceSysType	是	100
			receivingBillInfo.setSourceSysType(SourceTypeEnum.getEnum(SourceTypeEnum.AR_VALUE));
//			receivingBillInfo.setcompany	公司	对象	FCompanyID	是	
			receivingBillInfo.setCompany(companyInfo);
//			receivingBillInfo.setsourceFunction	来源功能	字符串	FSourceFunction		
//			receivingBillInfo.setsourceBillId	原始单据ID	字符串	FSourceBillID		
//			receivingBillInfo.setauditor	审核人	对象	FAuditorID		
//			receivingBillInfo.sethasEffected	是否曾经生效	布尔	FHasEffected		
//			receivingBillInfo.setdescription	参考信息	字符串	FDescription	
			receivingBillInfo.setDescription(billrectypename);
//			receivingBillInfo.sethandler	经手人	对象	FHandlerID		
//			receivingBillInfo.setbizDate	业务日期	日期	FBizDate	是	
			receivingBillInfo.setBizDate(billbizdate);
//			receivingBillInfo.setnumber	单据编号	字符串	FNumber	是	
//			receivingBillInfo.setCU	控制单元	对象	FControlUnitID	是	
			receivingBillInfo.setCU(companyInfo.getCU());
//			receivingBillInfo.setlastUpdateTime	最后修改时间	日期	FLastUpdateTime		
//			receivingBillInfo.setlastUpdateUser	最后修改者	对象	FLastUpdateUserID		
//			receivingBillInfo.setcreateTime	创建时间	日期	FCreateTime	是	
			receivingBillInfo.setCreateTime(new Timestamp(billbizdate.getTime()));
//			receivingBillInfo.setcreator	创建者	对象	FCreatorID	是	
//			receivingBillInfo.setid	ID	BOSUUID	FID	是	
			receivingBillInfo.put("othcustomer",othcustomerInfo);
			this.shoukuanMap.put(billNo, receivingBillInfo);
		}else{
			receivingBillInfo = (ReceivingBillInfo) this.shoukuanMap.get(billNo);
		}
		ReceivingBillEntryInfo receivingBillEntryInfo = new ReceivingBillEntryInfo();
//		receivingBillEntryInfo.setarPrintBillEntry	应收清单分录	字符串	FArPrintBillEntryId		
//		receivingBillEntryInfo.setarPrintBill	应收清单	字符串	FArPrintBillId		
//		receivingBillEntryInfo.setoppBgItemName	流入预算项目名称	字符串	FOppBgItemName		
//		receivingBillEntryInfo.setoppBgItemNumber	流入预算项目编码	字符串	FOppBgItemNumber		
//		receivingBillEntryInfo.setoppBgItemId	流入预算项目Id	字符串	FOppBgItemId		
//		receivingBillEntryInfo.setmatchedAmountLoc	matchedAmountLoc	数字	FMatchedAmountLoc	
		receivingBillEntryInfo.setMatchedAmountLoc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setmatchedAmount	已匹配金额	数字	FMatchedAmount	
		receivingBillEntryInfo.setMatchedAmount(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrecBillType	分录收款类型	对象	FRecBillTypeID	是	
		receivingBillEntryInfo.setRecBillType(getReceivingBillTypeInfoF7(ctx,"预收款"));
//		receivingBillEntryInfo.setassItemsEntries	核算项目组合	集合			
//		receivingBillEntryInfo.setexpenseType	费用项目	对象	FExpenseTypeID		
//		receivingBillEntryInfo.setreceiptID	关联生成收据的ID	BOSUUID	FReceiptID		
//		receivingBillEntryInfo.setreceiptNumber	收据号	字符串	FReceiptNumber		
//		receivingBillEntryInfo.setcustomerBillNum	客方单据号	字符串	FCustomerBillNum		
//		receivingBillEntryInfo.setbizBillNumber	业务单据号	字符串	FBizBillNumber		
//		receivingBillEntryInfo.setbizDate	交易日期	日期	FBizDate		
		receivingBillEntryInfo.setBizDate(billbizdate);
//		receivingBillEntryInfo.setreceivingBill	收款单头	集合	FReceivingBillID	是	
//		receivingBillEntryInfo.setbgCtrlAmt	预算控制金额	数字	FBgCtrlAmt	是	
		receivingBillEntryInfo.setBgCtrlAmt(entryamount);
//		receivingBillEntryInfo.setmbgName	辅助维度名称	字符串	FMbgName		
//		receivingBillEntryInfo.setmbgNumber	辅助维度编码	字符串	FMbgNumber		
//		receivingBillEntryInfo.setfundFlowItem	资金流量项目	对象	FFundFlowItemID		
//		receivingBillEntryInfo.settrackNumber	跟踪号	对象	FTrackNumberID		
//		receivingBillEntryInfo.setproject	项目号	对象	FProjectID		
//		receivingBillEntryInfo.setoutBgItemName	流出预算项目名称	字符串	FOutBgItemName		
//		receivingBillEntryInfo.setoutBgItemNumber	流出预算项目编码	字符串	FOutBgItemNumber		
//		receivingBillEntryInfo.setoutBgItemId	流出预算项目ID 	字符串	FOutBgItemId		
//		receivingBillEntryInfo.setcostCenter	成本中心	对象	FCostCenterID		
//		receivingBillEntryInfo.setcontractEntryID	合同单据分录ID	字符串	FContractEntryID		
//		receivingBillEntryInfo.setcontractBillID	合同单据ID	字符串	FContractBillID		
//		receivingBillEntryInfo.setcontractEntrySeq	合同行号	整数	FContractEntrySeq		
//		receivingBillEntryInfo.setcontractNum	合同编号	字符串	FContractNumber		
//		receivingBillEntryInfo.setotherBillTransAsstTypeId	转预收转预付往来户类型	对象	FOtherBillTransAsstTypeId		
//		receivingBillEntryInfo.setfpItem	计划项目	对象	FFpItemID		
//		receivingBillEntryInfo.setoppAccount	对方科目	对象	FOppAccountID		
//		receivingBillEntryInfo.setsourceBillAsstActID	源单往来户id	字符串	FSourceBillAsstActID		
//		receivingBillEntryInfo.setcurrency	币别	对象	FCurrencyId	
		receivingBillEntryInfo.setCurrency(getCurrencyInfoF7(ctx,"BB01"));
//		receivingBillEntryInfo.settrackNumbet	跟踪号	字符串	FTrackNumber		
//		receivingBillEntryInfo.setcoreBillEntrySeq	核心单据分录行号	整数	FCoreBillEntrySeq		
//		receivingBillEntryInfo.setcoreBillNumber	核心单据号	字符串	FCoreBillNumber		
//		receivingBillEntryInfo.setcoreBillEntryId	核心单据分录Id	字符串	FCoreBillEntryId		
//		receivingBillEntryInfo.setcoreBillId	核心单据Id	字符串	FCoreBillId		
//		receivingBillEntryInfo.setcoreBillType	核心单据类型	对象	FCoreBillTypeId		
//		receivingBillEntryInfo.sethisUnVcLocAmount	历史未核销金额本位币	数字	FHisUnVcLocAmount		
		receivingBillEntryInfo.setHisUnVcLocAmount(BigDecimal.ZERO);
//		receivingBillEntryInfo.sethisUnVcAmount	历史未核销金额	数字	FHisUnVcAmount	
		receivingBillEntryInfo.setHisUnVcAmount(BigDecimal.ZERO);
//		receivingBillEntryInfo.setvcStatus	核销状态	业务枚举	FVcStatus	是	
//		receivingBillEntryInfo.setsourceBillEntryId	源单据分录ID	字符串	FSourceBillEntryId		
//		receivingBillEntryInfo.setsourceBillId	源单据ID	字符串	FSourceBillId		
//		receivingBillEntryInfo.setunLockLocAmt	未锁定本位币金额	数字	FUnLockLocAmt	是	
		receivingBillEntryInfo.setUnLockLocAmt(entryamount);
//		receivingBillEntryInfo.setunLockAmt	未锁定金额	数字	FUnLockAmt	是	
		receivingBillEntryInfo.setUnLockAmt(entryamount);
//		receivingBillEntryInfo.setlockLocAmt	锁定本位币金额	数字	FLockLocAmt		
		receivingBillEntryInfo.setLockLocAmt(BigDecimal.ZERO);
//		receivingBillEntryInfo.setlockAmt	锁定金额	数字	FLockAmt	
		receivingBillEntryInfo.setLockAmt(BigDecimal.ZERO);
//		receivingBillEntryInfo.setremark	备注	字符串	FRemark		
//		receivingBillEntryInfo.setactualLocAmtVc	实收（付）本位币金额累计核销	数字	FActualLocAmtVc		
		receivingBillEntryInfo.setActualLocAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setactualLocAmt	实收（付）本位币金额	数字	FActualLocAmt	是	
		receivingBillEntryInfo.setActualLocAmt(entryamount);
//		receivingBillEntryInfo.setactualAmtVc	实收（付）金额累计核销	数字	FActualAmtVc	
		receivingBillEntryInfo.setActualAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setactualAmt	实收（付）金额	数字	FActualAmt	是
		receivingBillEntryInfo.setActualAmt(entryamount);
//		receivingBillEntryInfo.setrebateLocAmtVc	折扣本位币金额累计核销	数字	FRebateLocAmtVc	
		receivingBillEntryInfo.setRebateLocAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrebateLocAmt	折扣本位币金额	数字	FRebateLocAmt	
		receivingBillEntryInfo.setRebateLocAmt(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrebateAmtVc	折扣金额累计核销	数字	FRebateAmtVc
		receivingBillEntryInfo.setRebateAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrebate	现金折扣	数字	FRebate	
		receivingBillEntryInfo.setRebate(BigDecimal.ZERO);
//		receivingBillEntryInfo.setunVerifyExgRateLoc	未结算调汇本位币金额	数字	FUnVerifyExgRateLoc		
		receivingBillEntryInfo.setUnVerifyExgRateLoc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setunVcLocAmount	应收（付）未核销本位币金额	数字	FUnVcLocAmount	是	
		receivingBillEntryInfo.setUnVcLocAmount(entryamount);
//		receivingBillEntryInfo.setunVcAmount	应收（付）未核销金额	数字	FUnVcAmount	是	
		receivingBillEntryInfo.setUnVcAmount(entryamount);
//		receivingBillEntryInfo.setlocalAmtVc	应收（付）本位币累计核销	数字	FLocalAmtVc	
		receivingBillEntryInfo.setLocalAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setlocalAmt	应收（付）本位币金额	数字	FLocalAmount	是	
		receivingBillEntryInfo.setLocalAmt(entryamount);
//		receivingBillEntryInfo.setamountVc	应收（付）金额累计核销	数字	FAmountVc	
		receivingBillEntryInfo.setAmountVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setamount	应收（付）金额	数字	FAmount	是	
		receivingBillEntryInfo.setAmount(entryamount);
//		receivingBillEntryInfo.setseq	单据分录序列号	整数	FSeq		
//		receivingBillEntryInfo.setid	ID	BOSUUID	FID		

		receivingBillEntryInfo.setReceivingBill(receivingBillInfo);
		receivingBillInfo.getEntries().add(receivingBillEntryInfo);
    }
    //补交结果集放入MAP
    protected void getBujiaoMap (Context ctx,ResultSet rs) throws Exception{
    	//*********表头*************
    	//公司
		String billcompany = rs.getString("billcompany");
		//业务单据编码
		String billNo = rs.getString("billreceiptno");
		//单据日期
		Date billdate = rs.getDate("billdate");
		//收费方式编码
		String billrectypeno = rs.getString("billrectypeno");
		int i = Integer.parseInt(billrectypeno);
		//收费方式名称
		String billrectypename = rs.getString("billrectypename");
		//往来户
		String billaccount = rs.getString("billaccount");
		//业务系统付款ID
		String billchargeid = rs.getString("billchargeid");
		//业务日期
		Date billbizdate = rs.getDate("billbizdate");
		//病历归属门诊号
		String billrecordcompany = rs.getString("billrecordcompany");
		//总应付
//		BigDecimal billtatalpay = rs.getBigDecimal("billtatalpay");
		BigDecimal billtatalpay = rs.getBigDecimal("billtatalactualpay");
		//总实付
		BigDecimal billtatalactualpay = rs.getBigDecimal("billtatalactualpay");
		//会员卡号
		String billVipNo = rs.getString("billVipNo");
		//sd_channel  需要转移的客户
		String billchannel = rs.getString("billchannel");
		//--根据中间表客户编码取客户信息--
		CustomerInfo othcustomerInfo = null;
		if(i == PayTypeEnum.YIBAO_VALUE || i == PayTypeEnum.BAOXIAN_VALUE || i == PayTypeEnum.GONGDUIGONG_VALUE
			|| i == PayTypeEnum.QUDAO_VALUE){
			othcustomerInfo = getOthcustomerInfoF7(ctx,billchannel);
		}
		if("会员卡".equalsIgnoreCase(billrectypename)){
			othcustomerInfo = getCustomerInfoF7(ctx,"A10");
		}
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String billbizdateStr = sdf.format(billbizdate);
		int year = Integer.parseInt(billbizdateStr.substring(0,4));
		int month = Integer.parseInt(billbizdateStr.substring(5,7));
		//*********分录*************
		//费用项目编码
		String entryexpenseno = rs.getString("entryexpenseno");
		//费用项目名称
		String entryexpensename = rs.getString("entryexpensename");
		//数量
		BigDecimal entryqty = rs.getBigDecimal("entryqty");
		//应收金额
//		BigDecimal entryaramount = rs.getBigDecimal("entryaramount");
		BigDecimal entryaramount = rs.getBigDecimal("entryamount");
		//金额（实收）
		BigDecimal entryamount = rs.getBigDecimal("entryamount");
		//分录收款ID
		String entrychargeid = rs.getString("entrychargeid");
		//分录收款明细ID
		String entrychargedetailid = rs.getString("entrychargedetailid");
		//*********公共*************
		BigDecimal exchangeRate = new BigDecimal(1.00);
		BigDecimal zero = new BigDecimal(0.00);
		BigDecimal one = new BigDecimal(1.00);    	
		OtherBillInfo otherBillInfo = null;
		OrgmapInfo orgmapInfo = getOrgmapInfoF7(ctx, billcompany);
		CompanyOrgUnitInfo companyInfo =  orgmapInfo.getOrg();
    	if(this.bujiaoMap.get(billNo) == null ){
    		otherBillInfo = new OtherBillInfo(); 
    		//摘要
    		otherBillInfo.setAbstractName(billrectypename);
//    		getCompanyOrgUnitInfoF7(ctx,billcompany);
    		//财务组织
    		otherBillInfo.setCompany(companyInfo);
    		//管理单元
    		otherBillInfo.setCU(companyInfo.getCU());
    		//业务日期
    		otherBillInfo.setBizDate(billbizdate);
    		//币别
    		otherBillInfo.setCurrency(getCurrencyInfoF7(ctx,"BB01"));
    		//单据日期
    		otherBillInfo.setBillDate(billdate);
    		//汇率
    		otherBillInfo.setExchangeRate(exchangeRate);
    		//单据类型
    		otherBillInfo.setBillType(OtherBillTypeEnum.getEnum(OtherBillTypeEnum.EXPENSEINVOICE_VALUE));
    		//往来类型
    		otherBillInfo.setAsstActType(getAsstActTypeInfoF7(ctx,"客户"));
    		//客户
    		CustomerInfo customerInfo = getCustomerInfoF7(ctx,billaccount);
    		//往来户ID
    		otherBillInfo.setAsstActID(customerInfo.getId().toString());
    		//往来户名称
    		otherBillInfo.setAsstActName(customerInfo.getName());
    		//往来户编码
    		otherBillInfo.setAsstActNumber(customerInfo.getNumber());
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
    		otherBillInfo.setSaleOrg(getSaleOrgUnitInfoF7(ctx,billcompany));
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
    		otherBillInfo.setTotalAmountLocal(billtatalpay);
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
    		otherBillInfo.setTotalTaxAmount(billtatalpay);
    		//税额合计
    		otherBillInfo.setTotalTax(zero);
    		//金额合计
    		otherBillInfo.setTotalAmount(billtatalpay);
    		//最后调汇汇率
    		otherBillInfo.setLastExhangeRate(one);
    		//付款方式
    		otherBillInfo.setPaymentType(getPaymentTypeInfoF7(ctx,"赊销"));
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
    		otherBillInfo.setUnVerifyAmountLocal(billtatalpay);
    		//未结算金额
    		otherBillInfo.setUnVerifyAmount(billtatalpay);
    		//已结算金额本位币
    		otherBillInfo.setVerifyAmountLocal(zero);
    		//已结算金额
    		otherBillInfo.setVerifyAmount(zero);
    		//应收（付）金额本位币
    		otherBillInfo.setAmountLocal(billtatalpay);
    		//应收（付）金额
    		otherBillInfo.setAmount(billtatalpay);
    		//最后修改时间
    		otherBillInfo.setLastUpdateTime(new Timestamp(billbizdate.getTime()));
    		//最后修改者
    		otherBillInfo.setLastUpdateUser(getUserInfoF7(ctx,"user"));
    		//创建时间
    		otherBillInfo.setCreateTime(new Timestamp(billbizdate.getTime()));
    		//创建者
    		otherBillInfo.setCreator(getUserInfoF7(ctx,"user"));
    		
    		otherBillInfo.put("othcustomer",othcustomerInfo);
    		otherBillInfo.put("charge_id",billchargeid);
    		
    		this.bujiaoMap.put(billNo, otherBillInfo);

    		//收款计划
	    	OtherBillPlanInfo planInfo = new OtherBillPlanInfo();
	    	//应收应付日期
	    	planInfo.setRecievePayDate(billdate);
	    	//应收应付金额
	    	planInfo.setRecievePayAmount(billtatalpay);
	    	//应收应付金额本位币
	    	planInfo.setRecievePayAmountLocal(billtatalpay);
	    	//未锁定金额
	    	planInfo.setUnLockAmount(billtatalpay);
	    	//未锁定金额本位币
	    	planInfo.setUnLockAmountLoc(billtatalpay);
	    	//已锁定金额
	    	planInfo.setLockAmount(zero);
	    	//已锁定金额本位币
	    	planInfo.setLockAmountLoc(zero);
	    	//未结算金额
	    	planInfo.setUnVerifyAmount(billtatalpay);
	    	//未结算金额本位币
	    	planInfo.setUnVerifyAmountLocal(billtatalpay);
	    	
	    	planInfo.setParent(otherBillInfo);
	    	otherBillInfo.getRecievePlan().add(planInfo);
    	}else{
    		otherBillInfo = (OtherBillInfo) bujiaoMap.get(billNo);
    	}
//分录
    	OtherBillentryInfo otherBillentryInfo = new OtherBillentryInfo();
    	CustomerInfo customerInfo = getCustomerInfoF7(ctx,billaccount);
    	//是否完全核销
    	otherBillentryInfo.setIsFullWriteOff(false);
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
    	//收款往来类型
    	otherBillentryInfo.setRecAsstActType(getAsstActTypeInfoF7(ctx,"客户"));
    	//送货客户
    	otherBillentryInfo.setServiceCustomer(customerInfo);
    	//订货客户
    	otherBillentryInfo.setOrderCustomer(customerInfo);
    	//计提坏帐准备金额本位币
    	otherBillentryInfo.setPreparedBadAmountLocal(zero);
    	//计提坏帐准备金额
    	otherBillentryInfo.setPreparedBadAmount(zero);
    	//坏账金额本位币
    	otherBillentryInfo.setBadAmountLocal(zero);
    	//坏账金额
    	otherBillentryInfo.setBadAmount(zero);
    	//未开票申请金额本位币
    	otherBillentryInfo.setUnInvoiceReqAmountLocal(entryaramount);
    	//未开票申请金额
    	otherBillentryInfo.setUnInvoiceReqAmount(entryaramount);
    	//已开票申请金额本位币
    	otherBillentryInfo.setInvoiceReqAmountLocal(zero);
    	//已开票申请金额
    	otherBillentryInfo.setInvoiceReqAmount(zero);
    	//未开票申请基本数量
    	otherBillentryInfo.setUnInvoiceReqBaseQty(entryqty);
    	//未开票申请数量
    	otherBillentryInfo.setUnInvoiceReqQty(entryqty);
    	//已开票申请基本数量
    	otherBillentryInfo.setInvoiceReqBaseQty(zero);
    	//已开票申请数量
    	otherBillentryInfo.setInvoiceReqQty(zero);
    	//零数量
    	otherBillentryInfo.setIsQtyZero(false);
    	//单据日期
    	otherBillentryInfo.setBillDate(billdate);
    	//公司
    	otherBillentryInfo.setCompany(companyInfo.getId().toString());
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
    	otherBillentryInfo.setLocalUnwriteOffAmount(entryaramount);
    	//未核销基本数量
    	otherBillentryInfo.setUnwriteOffBaseQty(entryqty);
    	//已核销本位币金额
    	otherBillentryInfo.setLocalWrittenOffAmount(zero);
    	//已核销基本数量
    	otherBillentryInfo.setWittenOffBaseQty(zero);
    	//历史未核销金额本位币
    	otherBillentryInfo.setHisUnVerifyAmountLocal(zero);
    	//历史未核销金额
    	otherBillentryInfo.setHisUnVerifyAmount(zero);
    	//基本计量单位数量
    	otherBillentryInfo.setBaseQty(entryqty);
    	//辅助数量
    	otherBillentryInfo.setAssistQty(zero);
    	//金额本位币
    	otherBillentryInfo.setAmountLocal(entryaramount);
    	//金额
    	otherBillentryInfo.setAmount(entryaramount);
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
    	otherBillentryInfo.setTaxPrice(entryaramount.divide(entryqty,2,BigDecimal.ROUND_HALF_DOWN));
    	//实际单价
    	otherBillentryInfo.setRealPrice(entryaramount.divide(entryqty,2,BigDecimal.ROUND_HALF_DOWN));
    	//单价
    	otherBillentryInfo.setPrice(entryaramount.divide(entryqty,2,BigDecimal.ROUND_HALF_DOWN));
    	//数量
    	otherBillentryInfo.setQuantity(entryqty);
    	//未锁定金额本位币
    	otherBillentryInfo.setLockUnVerifyAmtLocal(entryaramount);
    	//未锁定金额
    	otherBillentryInfo.setLockUnVerifyAmt(entryaramount);
    	//已锁定金额本位币
    	otherBillentryInfo.setLockVerifyAmtLocal(zero);
    	//已锁定金额
    	otherBillentryInfo.setLockVerifyAmt(zero);
    	//未结算金额本位币
    	otherBillentryInfo.setUnVerifyAmountLocal(entryaramount);
    	//未结算金额
    	otherBillentryInfo.setUnVerifyAmount(entryaramount);
    	//已结算金额本位币
    	otherBillentryInfo.setVerifyAmountLocal(zero);
    	//已结算金额
    	otherBillentryInfo.setVerifyAmount(zero);
    	//应收（付）金额本位币
    	otherBillentryInfo.setRecievePayAmountLocal(entryaramount);
    	//应收（付）金额
    	otherBillentryInfo.setRecievePayAmount(entryaramount);
    	//是否赠品
    	otherBillentryInfo.setIsPresent(false);
    	//费用项目
    	otherBillentryInfo.setExpenseItem(getExpenseTypeInfoF7(ctx,entryexpenseno));
    	//计量单位
//    	otherBillentryInfo.setMeasureUnit(getMeasureUnitInfoF7(ctx,"1000"));
    	//物料名称
//    	otherBillentryInfo.setMaterialName("");
    	//物料
//    	otherBillentryInfo.setMaterial(item);
    	//行类型
//    	otherBillentryInfo.setRowType(item);
//    	otherBillentryInfo.setParent(otherBillInfo);
    	otherBillentryInfo.put("etycharge_id", entrychargeid);
    	otherBillentryInfo.put("etrcharge_detail_id", entrychargedetailid);
    	otherBillentryInfo.put("entryothcustomer",othcustomerInfo);
    	//表头
    	otherBillentryInfo.setHead(otherBillInfo);
    	otherBillInfo.getEntry().add(otherBillentryInfo);
    }
    // 对账单 --> 收款MAP
    protected void getShoukuanMapFromDuizhang (Context ctx,ResultSet rs) throws Exception{
    	//*********表头*************
    	//公司
		String billcompany = rs.getString("companyname");
		//业务单据编码
		String billNo = rs.getString("billno");
		//单据日期
		Date billdate = rs.getDate("billdate");
		//收费方式编码
		String billrectypeno = rs.getString("billrectypeno");
		int i = Integer.parseInt(billrectypeno);
		//收费方式名称
		String billrectypename = rs.getString("billrectypename");
		//往来户   病历号
		String billaccount = rs.getString("billrecordno");
		//业务日期
		Date billbizdate = rs.getDate("billdate");
		//收款(应收)总金额
		BigDecimal billtatalpay = rs.getBigDecimal("billrecamount");
		//实际收款总金额
		BigDecimal billtatalactualpay = rs.getBigDecimal("billactualrec");
		//手续费
		BigDecimal billfee = rs.getBigDecimal("billfee");
		//分期手续费
		BigDecimal instalmentFee = rs.getBigDecimal("instalmentFee");
		//客户转移
		String billchannel = rs.getString("othcustomerid");
		//--根据中间表客户编码取客户信息--
//		CustomerInfo othcustomerInfo = getOthcustomerInfoF7(ctx,billchannel);
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String billbizdateStr = sdf.format(billbizdate);
		int year = Integer.parseInt(billbizdateStr.substring(0,4));
		int month = Integer.parseInt(billbizdateStr.substring(5,7));
		//*********分录*************
		//*********公共*************
		ReceivingBillInfo receivingBillInfo = null;
		CustomerInfo customerInfo = null;
		if(i == PayTypeEnum.ZHIFUBAO_VALUE || i == PayTypeEnum.WEIXIN_VALUE || i == PayTypeEnum.GUONEISHUAKA_VALUE || i == PayTypeEnum.GUOWAISHUAKA_VALUE){
			customerInfo = getCustomerInfoF7(ctx,billaccount);
		}else{
			customerInfo = getOthcustomerInfoF7(ctx,billchannel);
		}
		OrgmapInfo orgmapInfo = getOrgmapInfoF7(ctx, billcompany);
		CompanyOrgUnitInfo companyInfo =  orgmapInfo.getOrg();
		AccountBankInfo accountBankInfo = getAccountBankInfoF7(ctx, companyInfo.getId().toString());
		if(this.shoukuanMap.get(billNo) == null ){
			receivingBillInfo = new ReceivingBillInfo(); 
//			receivingBillInfo.setrecBillClaim	收款认领信息	对象	FRecBillClaim
//			receivingBillInfo.setbillClaimStatus	单据认领状态	业务枚举	FBillClaimStatus	是	10
			receivingBillInfo.setBillClaimStatus(BillClaimStatusEnum.getEnum(BillClaimStatusEnum.UNCLAIM_VALUE));
//			receivingBillInfo.setisSmart	是否智能核算	业务枚举	FIsSmart	是	1
			receivingBillInfo.setIsSmart(SmartType.getEnum(SmartType.OPEN_VALUE));
//			receivingBillInfo.setprofitCenter	利润中心	对象	FProfitCenterID		
//			receivingBillInfo.setisHasRefundPay	是否已经生成退款支付单据	布尔	FisHasRefundPay	
			receivingBillInfo.setIsHasRefundPay(false);
//			receivingBillInfo.setisRefundmentPay	是否退款支付	布尔	FisRefundmentPay		
			receivingBillInfo.setIsRefundmentPay(false);
//			receivingBillInfo.setbankCheckStatus	银行未达状态	业务枚举	FBankCheckStatus	
//			receivingBillInfo.setprintCount	打印次数	整数	FPrintCount	是	1
			receivingBillInfo.setPrintCount(1);
//			receivingBillInfo.setpaymentType	付款方式	对象	FPaymentTypeID	是	
			receivingBillInfo.setPaymentType(getPaymentTypeInfoF7(ctx,"赊销"));
//			receivingBillInfo.setSYNBillEntryID	协同单据分录ID	字符串	FSYNBillEntryID		
//			receivingBillInfo.setSYNBillID	协同单据ID	字符串	FSYNBillID		
//			receivingBillInfo.setSYNbillNumber	协同单据编号	字符串	FSYNbillNumber		
//			receivingBillInfo.setSYNbillType	协同单据类型	字符串	FSYNbillType		
//			receivingBillInfo.setoppBgItemId	流入预算项目Id	字符串	FOppBgItemId		
//			receivingBillInfo.setoppBgItemNumber	流入预算项目编码	字符串	FOppBgItemNumber		
//			receivingBillInfo.setoppBgItemName	流入预算项目名称	字符串	FOppBgItemName		
//			receivingBillInfo.setpayAccountBank	实付银行账号	字符串	FPayAccountBank		
//			receivingBillInfo.setoppFpItem	流出计划项目	对象	FOppFpItemID		
//			receivingBillInfo.setisReverseLockAmount	是否反写锁定金额	布尔	FIsReverseLockAmount	是	1
			receivingBillInfo.setIsReverseLockAmount(true);
//			receivingBillInfo.setlastPayerType	原往来户类型	对象	FLastPayerTypeID		
//			receivingBillInfo.setlastPayerName	原往来户名称	字符串	FLastPayerName		
//			receivingBillInfo.setlastPayerNumber	原往来户编号	字符串	FLastPayerNumber		
//			receivingBillInfo.setlastPayerID	原往来户ID	字符串	FLastPayerID		
//			receivingBillInfo.setisPreReturn	单据分录收款类型是否含有预收款或者退预收款	布尔	FIsPreReturn		
//			receivingBillInfo.setisProxyReturn	单据分录收款类型是否含有代收款或者退代收款	布尔	FIsProxyReturn		
//			receivingBillInfo.setisSaleReturn	分录收款类型是否含有销售回款或者退销售回款	布尔	FIsSaleReturn		
//			receivingBillInfo.setrealRecCompany	实际收款公司	对象	FRealRecCompanyID		
//			receivingBillInfo.setrealRecBillID	代理收款实际付款公司	字符串	FRealRecBillID		
//			receivingBillInfo.setsrcRealRecBillID	代理收款源单ID	字符串	FSrcRealRecBillID		
//			receivingBillInfo.setreceivingBillType	收款单类型	业务枚举	FReceivingBillType	是	1
			receivingBillInfo.setReceivingBillType(CasRecPayBillTypeEnum.getEnum(CasRecPayBillTypeEnum.COMMONTYPE_VALUE));
//			receivingBillInfo.setassItems	对方科目核算项目	集合			
//			receivingBillInfo.setpayerAccountBankO	银行付款账户	对象	FPayerAccountBankID		
//			receivingBillInfo.setisRelateRecBook	是否已关联生成应收票据	布尔	FIsRelateRecBook		
//			receivingBillInfo.setrecBillType	收款类型	对象	FRecBillTypeID	是	
			receivingBillInfo.setRecBillType(getReceivingBillTypeInfoF7(ctx,"预收款"));
//			receivingBillInfo.setbgAmount	预算核准金额	数字	FBgAmount	
			receivingBillInfo.setBgAmount(BigDecimal.ZERO);	
//			receivingBillInfo.setdeliver	交票人	对象	FDeliverID		
//			receivingBillInfo.setoppInnerAcct	内部付款账户	对象	FOppInnerAcctID		
//			receivingBillInfo.setreceipt	关联进账单	对象	FReceiptId		
//			receivingBillInfo.setisRelateReceipt	是否已关联进账单	布尔	FIsRelateReceipt		
//			receivingBillInfo.setrecDate	收款日期	日期	FRecDate	
			receivingBillInfo.setRecDate(billbizdate);
//			receivingBillInfo.setentries	收款单分录	集合			
//			receivingBillInfo.setpayerAccountBank	付款账号	字符串	FPayerAccountBank		
//			receivingBillInfo.setpayerBank	付款银行	字符串	FPayerAccountNumber		
//			receivingBillInfo.setpayerName	往来户姓名	字符串	FPayerName		
			receivingBillInfo.setPayerName(customerInfo.getName());
//			receivingBillInfo.setpayerNumber	往来户编号	字符串	FPayerNumber	
			receivingBillInfo.setPayerNumber(customerInfo.getNumber());
//			receivingBillInfo.setpayerID	往来户ID	字符串	FPayerID	
			receivingBillInfo.setPayerID(customerInfo.getId().toString());
//			receivingBillInfo.setpayerType	往来户类型	对象	FPayerTypeID	是	
			receivingBillInfo.setPayerType(getAsstActTypeInfoF7(ctx,"客户"));
//			receivingBillInfo.setpayeeAccount	收款科目	对象	FPayeeAccountID	是	
//			receivingBillInfo.setPayeeAccount(getAccountViewInfoF7(ctx,"1001",companyInfo.getId().toString()));
			receivingBillInfo.setPayeeAccount(accountBankInfo.getAccount());
//			receivingBillInfo.setpayeeAccountBank	收款账户	对象	FPayeeAccountBankID		
			receivingBillInfo.setPayeeAccountBank(accountBankInfo);
//			receivingBillInfo.setpayeeBank	收款银行	对象	FPayeeBankID
			receivingBillInfo.setPayeeBank(accountBankInfo.getBank());
//			receivingBillInfo.setactRecLocAmtVc	实收本位币金额累计核销	数字	FActRecLocAmtVc
			receivingBillInfo.setActRecLocAmtVc(BigDecimal.ZERO);		
//			receivingBillInfo.setactRecLocAmt	实收本位币金额合计	数字	FActRecLocAmt	是	
			receivingBillInfo.setActRecLocAmt(billtatalactualpay);
//			receivingBillInfo.setactRecAmtVc	实收金额累计核销	数字	FActRecAmtVc
			receivingBillInfo.setActRecAmtVc(BigDecimal.ZERO);		
//			receivingBillInfo.setactRecAmt	实收金额合计	数字	FActRecAmt	是	
			receivingBillInfo.setActRecAmt(billtatalactualpay);
//			receivingBillInfo.setrecType	收款类型（现在开始禁用）	业务枚举	FRecType		
//			receivingBillInfo.setverifyStatus	结算状态	业务枚举	FverifyStatus	是	1
			receivingBillInfo.setVerifyStatus(com.kingdee.eas.fi.cas.verifyStatusEnum.getEnum(com.kingdee.eas.fi.cas.verifyStatusEnum.SOME_VERIFIED_VALUE));
//			receivingBillInfo.setisPreVerify	是否应付单预结算锁住的单	布尔	FIsPreVerify	
			receivingBillInfo.setIsPreVerify(false);
//			receivingBillInfo.setisDiffCurSettlement	异币种关联标识	布尔	FIsDiffCurSettlement		
//			receivingBillInfo.setbgCtrlAmt	预算控制金额	数字	FBgCtrlAmt	是	
			receivingBillInfo.setBgCtrlAmt(billtatalactualpay);
//			receivingBillInfo.setbankCheckFlag	对账标识码-用于智能对账反写	字符串	FBankCheckFlag		
//			receivingBillInfo.setmbgName	辅助维度名称	字符串	FMbgname		
//			receivingBillInfo.setmbgNumber	辅助维度编码	字符串	FMbgnumber		
//			receivingBillInfo.setfundFlowItem	资金流量项目	对象	FFundFlowItemID		
//			receivingBillInfo.setpcaVouchered	是否生成利润中心凭证	布尔	FpcaVouchered		
//			receivingBillInfo.setcostCenter	成本中心	对象	FCostCenterID		
//			receivingBillInfo.setsubSettDate	提交结算中心日期	日期	FSubSettDate		
//			receivingBillInfo.setoutBgItemName	流出预算项目名称	字符串	FOutBgItemName		
//			receivingBillInfo.setoutBgItemNumber	流出预算项目编码	字符串	FOutBgItemNumber		
//			receivingBillInfo.setoutBgItemId	流出预算项目ID	字符串	FOutBgItemID		
//			receivingBillInfo.setcontractNum	合同编号	字符串	FContractNumber		
//			receivingBillInfo.setisCoopBuild	是否协同生成	布尔	FIsCoopBuild		
//			receivingBillInfo.setBillDate_SourceBill	来源单日期	日期	FBillDate_SourceBill		
//			receivingBillInfo.setAsstActTypeID_SourceBill	来源单往来类型	字符串	FAsstActTypeID_SourceBill		
//			receivingBillInfo.setAsstActID_SourceBill	来源单往来户	字符串	FAsstActID_SourceBill		
//			receivingBillInfo.setPersonID_SourceBill	来源单人员	字符串	FPersonID_SourceBill		
//			receivingBillInfo.setAdminOrgUnitId_SourceBill	来源单部门	字符串	FAdminOrgUnitId_SourceBill		
//			receivingBillInfo.setisImpFromGL	是否从总账引入的数据	布尔	FIsImpFromGL		
//			receivingBillInfo.setmixEntryVerify	混合收付款类型分录是否符合相应结算条件	整数	FMixEntryVerify		
//			receivingBillInfo.setisAppointVoucher	是否已生成指定凭证	布尔	FIsAppointVoucher		
//			receivingBillInfo.setaccepterDate	结算单受理日期	日期	FAccepterDate		
//			receivingBillInfo.setaccepter	结算单受理人	字符串	FAccepter		
//			receivingBillInfo.setapprover	一级审核人	对象	FApproverID		
//			receivingBillInfo.setapproveDate	一级审核日期	日期	FApproveDate		
//			receivingBillInfo.setunVerifiedAmtLoc	未结算金额本位币合计	数字	FUnVerifiedAmtLoc	是	
			receivingBillInfo.setUnVerifiedAmtLoc(billtatalactualpay);
//			receivingBillInfo.setverifiedAmtLoc	已结算金额本位币合计	数字	FVerifiedAmtLoc		
			receivingBillInfo.setVerifiedAmtLoc(BigDecimal.ZERO);
//			receivingBillInfo.setunVerifiedAmt	未结算金额合计	数字	FUnVerifiedAmt	是	
			receivingBillInfo.setUnVerifiedAmt(billtatalactualpay);
//			receivingBillInfo.setverifiedAmt	已结算金额合计	数字	FVerifiedAmt		
			receivingBillInfo.setVerifiedAmt(BigDecimal.ZERO);
//			receivingBillInfo.setvoucherNumber	凭证编号	字符串	FVoucherNumber		
//			receivingBillInfo.setvoucher	凭证ID	对象	FVoucherID		
//			receivingBillInfo.setvoucherType	凭证字	对象	FVoucherTypeID		
//			receivingBillInfo.setisNeedVoucher	对账方式	布尔	FIsNeedVoucher	是	1
			receivingBillInfo.setIsNeedVoucher(true);
//			receivingBillInfo.setisTransOtherBill	是否转预收转预付	布尔	FIsTransOtherBill		
			receivingBillInfo.setIsTransOtherBill(false);
//			receivingBillInfo.setisCtrlOppAcct	控制对方科目	布尔	FIsCtrlOppAcct		
//			receivingBillInfo.setfeeType	收付类别	对象	FFeeTypeID		
//			receivingBillInfo.setisTransBill	是否转销单据	布尔	FIsTransBill		
//			receivingBillInfo.setisRedBill	是否是红字单据	布尔	FIsRedBill
			receivingBillInfo.setIsRedBill(false);
//			receivingBillInfo.setbizType	业务种类	对象	FBizTypeID		
//			receivingBillInfo.setisBookRL	是否登记银行日记账	布尔	FIsBookRL		
//			receivingBillInfo.setprojectManager	项目经理	对象	FProjectManagerID		
//			receivingBillInfo.setproject	项目	对象	FProjectID		
//			receivingBillInfo.setcontractBillId	合同Id	字符串	FContractBillID		
//			receivingBillInfo.setcapitalAmount	大写金额	字符串	FCapitalAmount		
//			receivingBillInfo.setdayaccount	日记账余额	数字	FDayaccount		
//			receivingBillInfo.setcontractNo	合同编号	字符串	FContractNo		
//			receivingBillInfo.setsummary	款项说明	字符串	FSummary		
//			receivingBillInfo.setconceit	打回意见	字符串	FConceit		
//			receivingBillInfo.setaccessoryAmt	附件数	整数	FAccessoryAmt		
//			receivingBillInfo.setperson	人员	对象	FPersonId		
//			receivingBillInfo.setadminOrgUnit	部门	对象	FAdminOrgUnitId		
//			receivingBillInfo.setlocalAmt	应收（付）本位币金额	数字	FLocalAmount	是	
			receivingBillInfo.setLocalAmt(billtatalactualpay);
//			receivingBillInfo.setamount	应收（付）金额	数字	FAmount	是	
			receivingBillInfo.setAmount(billtatalactualpay);
//			receivingBillInfo.setisImport	是否导入	布尔	FIsImport	
			receivingBillInfo.setIsImport(false);
//			receivingBillInfo.setfundType	收付款方式	业务枚举	FFundType	
			receivingBillInfo.setFundType(BizTypeEnum.getEnum(BizTypeEnum.CASH_VALUE));
//			receivingBillInfo.setsettlementStatus	集中结算状态	业务枚举	FSettlementStatus	是	10
			receivingBillInfo.setSettlementStatus(SettlementStatusEnum.getEnum(SettlementStatusEnum.UNSUBMIT_VALUE));
//			receivingBillInfo.setbillStatus	单据状态	业务枚举	FBillStatus	是	10
			receivingBillInfo.setBillStatus(com.kingdee.eas.fi.cas.BillStatusEnum.getEnum(com.kingdee.eas.fi.cas.BillStatusEnum.AUDITED_VALUE));
//			receivingBillInfo.setfiVouchered	是否已生成凭证	布尔	FFiVouchered		
//			receivingBillInfo.setisInitializeBill	是否初始化单据	布尔	FIsInitializeBill		
//			receivingBillInfo.setaccountant	会计	对象	FAccountantID		
//			receivingBillInfo.setcashier	出纳	对象	FCashierID		
//			receivingBillInfo.setauditDate	审核日期	日期	FAuditDate		
//			receivingBillInfo.setsettleBizType	结算类型	业务枚举	FSettleBizType		
//			receivingBillInfo.setisCommitSettle	是否提交结算中心	布尔	FIsCommitSettle		
			receivingBillInfo.setIsCommitSettle(false);
//			receivingBillInfo.setfpItem	计划项目	对象	FFpItemID		
//			receivingBillInfo.setoppAccount	对方科目	对象	FOppAccountID		
//			receivingBillInfo.setsettlementNumber	结算号	字符串	FSettlementNumber		
//			receivingBillInfo.setsettlementType	结算方式	对象	FSettlementTypeID	是
			receivingBillInfo.setSettlementType(getSettlementTypeInfoF7(ctx,"现金"));
//			receivingBillInfo.setlastExhangeRate	最后调汇汇率	数字	FLastExhangeRate
			receivingBillInfo.setLastExhangeRate(BigDecimal.ONE);
//			receivingBillInfo.setisExchanged	是否已经调汇	布尔	FIsExchanged
			receivingBillInfo.setIsExchanged(false);
//			receivingBillInfo.setexchangeRate	汇率	数字	FExchangeRate	是	1
			receivingBillInfo.setExchangeRate(BigDecimal.ONE);
//			receivingBillInfo.setcurrency	币别	对象	FCurrencyID	是	dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC
			receivingBillInfo.setCurrency(getCurrencyInfoF7(ctx,"BB01"));
//			receivingBillInfo.setsourceType	业务系统	业务枚举	FSourceType	是	100
			receivingBillInfo.setSourceType(SourceTypeEnum.getEnum(SourceTypeEnum.AR_VALUE));
//			receivingBillInfo.setsourceSysType	来源系统	业务枚举	FSourceSysType	是	100
			receivingBillInfo.setSourceSysType(SourceTypeEnum.getEnum(SourceTypeEnum.AR_VALUE));
//			receivingBillInfo.setcompany	公司	对象	FCompanyID	是	
			receivingBillInfo.setCompany(companyInfo);
//			receivingBillInfo.setsourceFunction	来源功能	字符串	FSourceFunction		
//			receivingBillInfo.setsourceBillId	原始单据ID	字符串	FSourceBillID		
//			receivingBillInfo.setauditor	审核人	对象	FAuditorID		
//			receivingBillInfo.sethasEffected	是否曾经生效	布尔	FHasEffected		
//			receivingBillInfo.setdescription	参考信息	字符串	FDescription	
			receivingBillInfo.setDescription(billrectypename);
//			receivingBillInfo.sethandler	经手人	对象	FHandlerID		
//			receivingBillInfo.setbizDate	业务日期	日期	FBizDate	是	
			receivingBillInfo.setBizDate(billbizdate);
//			receivingBillInfo.setnumber	单据编号	字符串	FNumber	是	
//			receivingBillInfo.setCU	控制单元	对象	FControlUnitID	是	
			receivingBillInfo.setCU(companyInfo.getCU());
//			receivingBillInfo.setlastUpdateTime	最后修改时间	日期	FLastUpdateTime		
//			receivingBillInfo.setlastUpdateUser	最后修改者	对象	FLastUpdateUserID		
//			receivingBillInfo.setcreateTime	创建时间	日期	FCreateTime	是	
			receivingBillInfo.setCreateTime(new Timestamp(billbizdate.getTime()));
//			receivingBillInfo.setcreator	创建者	对象	FCreatorID	是	
//			receivingBillInfo.setid	ID	BOSUUID	FID	是	
			this.shoukuanMap.put(billNo, receivingBillInfo);
		}else{
			receivingBillInfo = (ReceivingBillInfo) this.shoukuanMap.get(billNo);
		}
		ReceivingBillEntryInfo receivingBillEntryInfo = new ReceivingBillEntryInfo();
//		receivingBillEntryInfo.setarPrintBillEntry	应收清单分录	字符串	FArPrintBillEntryId		
//		receivingBillEntryInfo.setarPrintBill	应收清单	字符串	FArPrintBillId		
//		receivingBillEntryInfo.setoppBgItemName	流入预算项目名称	字符串	FOppBgItemName		
//		receivingBillEntryInfo.setoppBgItemNumber	流入预算项目编码	字符串	FOppBgItemNumber		
//		receivingBillEntryInfo.setoppBgItemId	流入预算项目Id	字符串	FOppBgItemId		
//		receivingBillEntryInfo.setmatchedAmountLoc	matchedAmountLoc	数字	FMatchedAmountLoc	
		receivingBillEntryInfo.setMatchedAmountLoc(BigDecimal.ZERO);	
//		receivingBillEntryInfo.setmatchedAmount	已匹配金额	数字	FMatchedAmount	
		receivingBillEntryInfo.setMatchedAmount(BigDecimal.ZERO);	
//		receivingBillEntryInfo.setrecBillType	分录收款类型	对象	FRecBillTypeID	是	
		receivingBillEntryInfo.setRecBillType(getReceivingBillTypeInfoF7(ctx,"预收款"));
//		receivingBillEntryInfo.setassItemsEntries	核算项目组合	集合			
//		receivingBillEntryInfo.setexpenseType	费用项目	对象	FExpenseTypeID		
//		receivingBillEntryInfo.setreceiptID	关联生成收据的ID	BOSUUID	FReceiptID		
//		receivingBillEntryInfo.setreceiptNumber	收据号	字符串	FReceiptNumber		
//		receivingBillEntryInfo.setcustomerBillNum	客方单据号	字符串	FCustomerBillNum		
//		receivingBillEntryInfo.setbizBillNumber	业务单据号	字符串	FBizBillNumber		
//		receivingBillEntryInfo.setbizDate	交易日期	日期	FBizDate		
		receivingBillEntryInfo.setBizDate(billbizdate);
//		receivingBillEntryInfo.setreceivingBill	收款单头	集合	FReceivingBillID	是	
//		receivingBillEntryInfo.setbgCtrlAmt	预算控制金额	数字	FBgCtrlAmt	是	
		receivingBillEntryInfo.setBgCtrlAmt(billtatalactualpay);
//		receivingBillEntryInfo.setmbgName	辅助维度名称	字符串	FMbgName		
//		receivingBillEntryInfo.setmbgNumber	辅助维度编码	字符串	FMbgNumber		
//		receivingBillEntryInfo.setfundFlowItem	资金流量项目	对象	FFundFlowItemID		
//		receivingBillEntryInfo.settrackNumber	跟踪号	对象	FTrackNumberID		
//		receivingBillEntryInfo.setproject	项目号	对象	FProjectID		
//		receivingBillEntryInfo.setoutBgItemName	流出预算项目名称	字符串	FOutBgItemName		
//		receivingBillEntryInfo.setoutBgItemNumber	流出预算项目编码	字符串	FOutBgItemNumber		
//		receivingBillEntryInfo.setoutBgItemId	流出预算项目ID 	字符串	FOutBgItemId		
//		receivingBillEntryInfo.setcostCenter	成本中心	对象	FCostCenterID		
//		receivingBillEntryInfo.setcontractEntryID	合同单据分录ID	字符串	FContractEntryID		
//		receivingBillEntryInfo.setcontractBillID	合同单据ID	字符串	FContractBillID		
//		receivingBillEntryInfo.setcontractEntrySeq	合同行号	整数	FContractEntrySeq		
//		receivingBillEntryInfo.setcontractNum	合同编号	字符串	FContractNumber		
//		receivingBillEntryInfo.setotherBillTransAsstTypeId	转预收转预付往来户类型	对象	FOtherBillTransAsstTypeId		
//		receivingBillEntryInfo.setfpItem	计划项目	对象	FFpItemID		
//		receivingBillEntryInfo.setoppAccount	对方科目	对象	FOppAccountID		
//		receivingBillEntryInfo.setsourceBillAsstActID	源单往来户id	字符串	FSourceBillAsstActID		
//		receivingBillEntryInfo.setcurrency	币别	对象	FCurrencyId	
		receivingBillEntryInfo.setCurrency(getCurrencyInfoF7(ctx,"BB01"));
//		receivingBillEntryInfo.settrackNumbet	跟踪号	字符串	FTrackNumber		
//		receivingBillEntryInfo.setcoreBillEntrySeq	核心单据分录行号	整数	FCoreBillEntrySeq		
//		receivingBillEntryInfo.setcoreBillNumber	核心单据号	字符串	FCoreBillNumber		
//		receivingBillEntryInfo.setcoreBillEntryId	核心单据分录Id	字符串	FCoreBillEntryId		
//		receivingBillEntryInfo.setcoreBillId	核心单据Id	字符串	FCoreBillId		
//		receivingBillEntryInfo.setcoreBillType	核心单据类型	对象	FCoreBillTypeId		
//		receivingBillEntryInfo.sethisUnVcLocAmount	历史未核销金额本位币	数字	FHisUnVcLocAmount			
		receivingBillEntryInfo.setHisUnVcLocAmount(BigDecimal.ZERO);	
//		receivingBillEntryInfo.sethisUnVcAmount	历史未核销金额	数字	FHisUnVcAmount	
		receivingBillEntryInfo.setHisUnVcAmount(BigDecimal.ZERO);
//		receivingBillEntryInfo.setvcStatus	核销状态	业务枚举	FVcStatus	是	
//		receivingBillEntryInfo.setsourceBillEntryId	源单据分录ID	字符串	FSourceBillEntryId		
//		receivingBillEntryInfo.setsourceBillId	源单据ID	字符串	FSourceBillId		
//		receivingBillEntryInfo.setunLockLocAmt	未锁定本位币金额	数字	FUnLockLocAmt	是	
		receivingBillEntryInfo.setUnLockLocAmt(billtatalactualpay);
//		receivingBillEntryInfo.setunLockAmt	未锁定金额	数字	FUnLockAmt	是	
		receivingBillEntryInfo.setUnLockAmt(billtatalactualpay);
//		receivingBillEntryInfo.setlockLocAmt	锁定本位币金额	数字	FLockLocAmt		
		receivingBillEntryInfo.setLockLocAmt(BigDecimal.ZERO);
//		receivingBillEntryInfo.setlockAmt	锁定金额	数字	FLockAmt	
		receivingBillEntryInfo.setLockAmt(BigDecimal.ZERO);
//		receivingBillEntryInfo.setremark	备注	字符串	FRemark		
//		receivingBillEntryInfo.setactualLocAmtVc	实收（付）本位币金额累计核销	数字	FActualLocAmtVc		
		receivingBillEntryInfo.setActualLocAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setactualLocAmt	实收（付）本位币金额	数字	FActualLocAmt	是	
		receivingBillEntryInfo.setActualLocAmt(billtatalactualpay);
//		receivingBillEntryInfo.setactualAmtVc	实收（付）金额累计核销	数字	FActualAmtVc	
		receivingBillEntryInfo.setActualAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setactualAmt	实收（付）金额	数字	FActualAmt	是
		receivingBillEntryInfo.setActualAmt(billtatalactualpay);
//		receivingBillEntryInfo.setrebateLocAmtVc	折扣本位币金额累计核销	数字	FRebateLocAmtVc	
		receivingBillEntryInfo.setRebateLocAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrebateLocAmt	折扣本位币金额	数字	FRebateLocAmt	
		receivingBillEntryInfo.setRebateLocAmt(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrebateAmtVc	折扣金额累计核销	数字	FRebateAmtVc
		receivingBillEntryInfo.setRebateAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setrebate	现金折扣	数字	FRebate	
		receivingBillEntryInfo.setRebate(BigDecimal.ZERO);
//		receivingBillEntryInfo.setunVerifyExgRateLoc	未结算调汇本位币金额	数字	FUnVerifyExgRateLoc		
		receivingBillEntryInfo.setUnVerifyExgRateLoc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setunVcLocAmount	应收（付）未核销本位币金额	数字	FUnVcLocAmount	是	
		receivingBillEntryInfo.setUnVcLocAmount(billtatalpay);
//		receivingBillEntryInfo.setunVcAmount	应收（付）未核销金额	数字	FUnVcAmount	是	
		receivingBillEntryInfo.setUnVcAmount(billtatalpay);
//		receivingBillEntryInfo.setlocalAmtVc	应收（付）本位币累计核销	数字	FLocalAmtVc	
		receivingBillEntryInfo.setLocalAmtVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setlocalAmt	应收（付）本位币金额	数字	FLocalAmount	是	
		receivingBillEntryInfo.setLocalAmt(billtatalpay);
//		receivingBillEntryInfo.setamountVc	应收（付）金额累计核销	数字	FAmountVc	
		receivingBillEntryInfo.setAmountVc(BigDecimal.ZERO);
//		receivingBillEntryInfo.setamount	应收（付）金额	数字	FAmount	是	
		receivingBillEntryInfo.setAmount(billtatalpay);
//		receivingBillEntryInfo.setseq	单据分录序列号	整数	FSeq		
//		receivingBillEntryInfo.setid	ID	BOSUUID	FID		

		receivingBillEntryInfo.setReceivingBill(receivingBillInfo);
		receivingBillInfo.getEntries().add(receivingBillEntryInfo);
    }
    
    // 对账单 --> 应付MAP
    protected void getYingfuMapFromDuizhang (Context ctx,ResultSet rs) throws Exception{
    	//*********表头*************
    	//公司
		String billcompany = rs.getString("companyname");
		//业务单据编码
		String billNo = rs.getString("billno");
		//单据日期
		Date billdate = rs.getDate("billdate");
		//收费方式编码
		String billrectypeno = rs.getString("billrectypeno");
		int i = Integer.parseInt(billrectypeno);
		//收费方式名称
		String billrectypename = rs.getString("billrectypename");
		//往来户   病历号
		String billaccount = rs.getString("billrecordno");
		//业务日期
		Date billbizdate = rs.getDate("billdate");
		//收款(应收)总金额
		BigDecimal billtatalpay = rs.getBigDecimal("billrecamount");
		//实际收款总金额
		BigDecimal billtatalactualpay = rs.getBigDecimal("billactualrec");
		//手续费
		BigDecimal billfee = rs.getBigDecimal("billfee");
		//分期手续费
		BigDecimal instalmentFee = rs.getBigDecimal("instalmentFee");
		//手续费客户
//		String otherCustomerName = rs.getString("othcustomername");
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String billbizdateStr = sdf.format(billbizdate);
		int year = Integer.parseInt(billbizdateStr.substring(0,4));
		int month = Integer.parseInt(billbizdateStr.substring(5,7));
		//*********分录*************
		//*********公共*************
		com.kingdee.eas.fi.ap.OtherBillInfo otherBillInfo = null;
//		CustomerInfo customerInfo = getCustomerInfoF7(ctx,billaccount);
		OrgmapInfo orgmapInfo = getOrgmapInfoF7(ctx, billcompany);
		CompanyOrgUnitInfo companyInfo =  orgmapInfo.getOrg();
		String billchannel = rs.getString("othcustomerid");
		//--根据中间表客户编码取客户信息--
//		CustomerInfo othcustomerInfo = getOthcustomerInfoF7(ctx,billchannel);
		CustomerInfo customerInfo = null;
		if(i == PayTypeEnum.ZHIFUBAO_VALUE || i == PayTypeEnum.WEIXIN_VALUE || i == PayTypeEnum.GUONEISHUAKA_VALUE || i == PayTypeEnum.GUOWAISHUAKA_VALUE){
			customerInfo = getCustomerInfoF7(ctx,billaccount);
		}else{
			customerInfo = getOthcustomerInfoF7(ctx,billchannel);
		}
		if(this.yingfuMap.get(billNo) == null ){
			otherBillInfo = new com.kingdee.eas.fi.ap.OtherBillInfo(); 
//    		otherBillInfo.setinvoiceCode	发票代码	字符串	FInvoiceCode	0
//    		otherBillInfo.setinvoiceNumber	发票号码	字符串	FInvoiceNumber	0
//    		otherBillInfo.setinvoiceType	发票类型	业务枚举	FInvoiceType	-1
//    		otherBillInfo.setisMatchGenerate	发票匹配	布尔	FIsMatchGenerate	0
//    		otherBillInfo.setprePayBillEntryIDs	预付单分录ID串	字符串	FPrePayBillEntryIDs	0
//    		otherBillInfo.setprePayAmountString	预锁定金额明细	字符串	FPrePayAmountString	0
//    		otherBillInfo.setthisApAmount	本次应付金额	数字	FthisApAmount	88
    		otherBillInfo.setThisApAmount(billfee);
//    		otherBillInfo.setprePayAmount	预付金额	数字	FPrePayAmount	0
//    		otherBillInfo.setprePayBillNumber	预付款单编号（可支持多选）	字符串	FPrePayBillNumber	0
//    		otherBillInfo.setpayerAmountLoc	付款金额本位币	数字	FPayerAmountLoc	88
    		otherBillInfo.setPayerAmountLoc(billfee);
//    		otherBillInfo.setpayerAmount	付款金额	数字	FPayerAmount	88
    		otherBillInfo.setPayerAmount(billfee);
//    		otherBillInfo.setpayerExchangeRate	付款汇率	数字	FPayerExchangeRate	1
    		otherBillInfo.setPayerExchangeRate(BigDecimal.ONE);
//    		otherBillInfo.setpayerCurrency	付款币别	对象	FPayerCurrencyID	dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC
    		otherBillInfo.setPayerCurrency(getCurrencyInfoF7(ctx, "BB01"));
//    		otherBillInfo.setrecAccountBank	收款账户	字符串	FRecAccountBank	11050161620000000011
//    		otherBillInfo.setrecBank	收款银行	字符串	FRecBank	中国建设银行北京金源支行
//    		otherBillInfo.setpayerAccountBank	付款账户	对象	FPayerAccountBankID	0
//    		otherBillInfo.setpayerBank	付款银行	对象	FPayerBankID	0
//    		otherBillInfo.setisCreatedByApElecInvoice	发票匹配	布尔	FIsCreatedByApElecInvoice	0
//    		otherBillInfo.setprofitCenter	利润中心	对象	FProfitCenterID	0
//    		otherBillInfo.setbillType_SourceBill	来源单单据类型	业务枚举	FBillType_SourceBill	0
//    		otherBillInfo.setpurchaseGroup	采购组	对象	FPurchaseGroupID	0
//    		otherBillInfo.setbillType	单据类型	业务枚举	FBillType	201
    		otherBillInfo.setBillType(OtherBillType.getEnum(OtherBillType.OTHERPAY_VALUE));
//    		otherBillInfo.setpayPlan	付款计划	集合		#N/A
//    		otherBillInfo.setexpApportion	分摊明细	集合		#N/A
//    		otherBillInfo.setentry	分录	集合		#N/A
//    		otherBillInfo.setpurOrg	采购组织	对象	FPurOrgID	8i0AAAAATvzM567U
    		otherBillInfo.setPurOrg(getPurchaseOrgUnitInfoF7(ctx, billcompany));
//    		otherBillInfo.setverifyStatus	结算状态	业务枚举	FverifyStatus	1
    		otherBillInfo.setVerifyStatus(verifyStatusEnum.getEnum(verifyStatusEnum.UNVERIFY_VALUE));
//    		otherBillInfo.setisDiffCurrency	异币种标识	布尔	FIsDiffCurrency	0
//    		otherBillInfo.setpcaVouchered	是否生成利润中心凭证	布尔	FpcaVouchered	0
//    		otherBillInfo.settotalTaxLocal	税额本位币	数字	FTotalTaxLocal	0
//    		otherBillInfo.settotalAmountLocal	金额本位币	数字	FTotalAmountLocal	88
    		otherBillInfo.setTotalAmountLocal(billfee);
//    		otherBillInfo.setisTransPoint	是否转移指定	布尔	FIsTransPoint	0
//    		otherBillInfo.setisTransOtherBill	是否转应收应付	布尔	FIsTransOtherBill	0
//    		otherBillInfo.setbillRelationOption	整单关联算法	业务枚举	FBillRelationOption	0
//    		otherBillInfo.setcostCenter	成本中心	对象	FCostCenterID	0
//    		otherBillInfo.setisBizBill	是否业务应收应付单据	布尔	FIsBizBill	0
//    		otherBillInfo.setcontractNum	合同编号	字符串	FContractNumber	0
//    		otherBillInfo.setisGenCoopBill	是否生成协同单据	布尔	FIsGenCoopBill	0
//    		otherBillInfo.setisCoopBuild	是否协同生成	布尔	FIsCoopBuild	0
//    		otherBillInfo.setisSplitBill	是否拆单单据	布尔	FIsSplitBill	0
//    		otherBillInfo.setAsstActTypeID_SourceBill	来源单往来类型	字符串	FAsstActTypeID_SourceBill	0
//    		otherBillInfo.setBillDate_SourceBill	来源单日期	日期	FBillDate_SourceBill	0
//    		otherBillInfo.setAsstActID_SourceBill	来源单往来户	字符串	FAsstActID_SourceBill	0
//    		otherBillInfo.setPersonID_SourceBill	来源单人员	字符串	FPersonID_SourceBill	0
//    		otherBillInfo.setAdminOrgUnitId_SourceBill	来源单部门	字符串	FAdminOrgUnitId_SourceBill	0
//    		otherBillInfo.setisImpFromGL	是否从总账引入的数据	布尔	FIsImpFromGL	0
//    		otherBillInfo.setpriceSource	价格来源	业务枚举	FPriceSource	101
    		otherBillInfo.setPriceSource(PriceSourceEnum.getEnum(PriceSourceEnum.PRICEFROMCOREBILL_VALUE));
//    		otherBillInfo.setisAppointVoucher	是否已生成指定凭证	布尔	FIsAppointVoucher	0
//    		otherBillInfo.setisNeedVoucher	对账方式	布尔	FIsNeedVoucher	1
    		otherBillInfo.setIsNeedVoucher(true);
//    		otherBillInfo.setisPriceWithoutTax	是否价外税	布尔	FIsPriceWithoutTax	1
    		otherBillInfo.setIsPriceWithoutTax(true);
//    		otherBillInfo.setisInTax	是否含税	布尔	FIsInTax	1
    		otherBillInfo.setIsInTax(true);
//    		otherBillInfo.setisSCMBill	是否物流的单据	布尔	FIsSCMBill	0
//    		otherBillInfo.setperiod	业务日期所在期间	整数	FPeriod	11
    		otherBillInfo.setPeriod(month);
//    		otherBillInfo.setyear	业务日期所在年度	整数	FYear	2020
    		otherBillInfo.setYear(year);
//    		otherBillInfo.setredBlueType	是否红字发票	布尔	FRedBlueType	0
//    		otherBillInfo.setvoucherType	凭证字	对象	FVoucherTypeID	0
//    		otherBillInfo.setisInitializeBill	是否期初单据	布尔	FIsInitializeBill	0
//    		otherBillInfo.setisExchanged	是否已经调汇	布尔	FIsExchanged	0
//    		otherBillInfo.settotalTaxAmount	价税合计（费用金额合计）	数字	FTotalTaxAmount	0
//    		otherBillInfo.settotalTax	税额合计	数字	FTotalTax	0
//    		otherBillInfo.settotalAmount	金额合计	数字	FTotalAmount	88
    		otherBillInfo.setTotalAmount(billfee);
//    		otherBillInfo.setlastExhangeRate	最后调汇汇率	数字	FLastExhangeRate	1
    		otherBillInfo.setLastExhangeRate(BigDecimal.ONE);
//    		otherBillInfo.setpayCondition	收（付）款条件	对象	FPayConditionId	0
//    		otherBillInfo.setpaymentType	付款方式	对象	FPaymentTypeID	2fa35444-5a23-43fb-99ee-6d4fa5f260da6BCA0AB5
    		otherBillInfo.setPaymentType(getPaymentTypeInfoF7(ctx,"赊购"));
//    		otherBillInfo.setbizType	业务类型	对象	FBizTypeID	0
//    		otherBillInfo.setisImportBill	是否是导入单据	布尔	FIsImportBill	0
//    		otherBillInfo.setisAllowanceBill	是否折让单据	布尔	FIsAllowanceBill	0
//    		otherBillInfo.setisTransBill	是否转移生成单据	布尔	FIsTransBill	0
//    		otherBillInfo.setaccountant	会计	对象	FAccountantID	0
//    		otherBillInfo.setisReverseBill	是否为冲销单据	布尔	FIsReverseBill	0
//    		otherBillInfo.setisReversed	是否已被冲销	布尔	FIsReversed	0
//    		otherBillInfo.setcashDiscount	现金折扣	对象	FCashDiscountID	0
//    		otherBillInfo.setsourceBillType	来源单据类型	业务枚举	FSourceBillType	-1
//    		otherBillInfo.setvoucherNumber	凭证编号	字符串	FVoucherNumber	0
//    		otherBillInfo.setvoucher	凭证	对象	FVoucherID	0
//    		otherBillInfo.setfiVouchered	是否已生成凭证	布尔	FFiVouchered	0
//    		otherBillInfo.setauditDate	审核日期	日期	FAuditDate	0
//    		otherBillInfo.setunVerifyAmountLocal	未结算本位币金额	数字	FUnVerifyAmountLocal	88
    		otherBillInfo.setUnVerifyAmountLocal(billfee);
//    		otherBillInfo.setunVerifyAmount	未结算金额	数字	FUnVerifyAmount	88
    		otherBillInfo.setUnVerifyAmount(billfee);
//    		otherBillInfo.setverifyAmountLocal	已结算金额本位币	数字	FVerifyAmountLocal	0
//    		otherBillInfo.setverifyAmount	已结算金额	数字	FVerifyAmount	0
//    		otherBillInfo.setabstractName	摘要	字符串	FAbstractName	0
    		otherBillInfo.setAbstractName(billrectypename);
//    		otherBillInfo.setamountLocal	应收（付）金额本位币	数字	FAmountLocal	88
    		otherBillInfo.setAmountLocal(billfee);
//    		otherBillInfo.setamount	应收（付）金额	数字	FAmount	88
    		otherBillInfo.setAmount(billfee);
//    		otherBillInfo.setsettleType	结算方式	对象	FSettleTypeID	0
//    		otherBillInfo.setexchangeRate	汇率	数字	FExchangeRate	1
    		otherBillInfo.setExchangeRate(BigDecimal.ONE);
//    		otherBillInfo.setperson	业务员	对象	FPersonId	0
//    		otherBillInfo.setadminOrgUnit	部门	对象	FAdminOrgUnitID	0
//    		otherBillInfo.setcurrency	币别	对象	FCurrencyID	dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC
    		otherBillInfo.setCurrency(getCurrencyInfoF7(ctx, "BB01"));
//    		otherBillInfo.setbillStatus	单据状态	业务枚举	FBillStatus	1
    		otherBillInfo.setBillStatus(BillStatusEnum.getEnum(BillStatusEnum.SAVE_VALUE));
//    		otherBillInfo.setasstActName	往来户名称	字符串	FAsstActName	#N/A  customerInfo
    		otherBillInfo.setAsstActName(customerInfo.getName());
//    		otherBillInfo.setasstActNumber	往来户编码	字符串	FAsstActNumber	C002
    		otherBillInfo.setAsstActNumber(customerInfo.getNumber());
//    		otherBillInfo.setasstActID	往来户ID	字符串	FAsstActID	8i0AAAABDsC/DAQO
    		otherBillInfo.setAsstActID(customerInfo.getId().toString());
//    		otherBillInfo.setasstActType	往来类型	对象	FAsstActTypeID	YW3xsAEJEADgAAUWwKgTB0c4VZA=
    		otherBillInfo.setAsstActType(getAsstActTypeInfoF7(ctx,"客户"));
//    		otherBillInfo.setbillDate	单据日期	日期	FBillDate	2020-11-25
    		otherBillInfo.setBillDate(billdate);
//    		otherBillInfo.setcompany	公司	对象	FCompanyID	8i0AAAAATvzM567U
    		otherBillInfo.setCompany(companyInfo);
//    		otherBillInfo.setsourceFunction	来源功能	字符串	FSourceFunction	0
//    		otherBillInfo.setsourceBillId	原始单据ID	字符串	FSourceBillID	0
//    		otherBillInfo.setauditor	审核人	对象	FAuditorID	0
//    		otherBillInfo.sethasEffected	是否曾经生效	布尔	FHasEffected	0
//    		otherBillInfo.setdescription	参考信息	字符串	FDescription	0
//    		otherBillInfo.sethandler	经手人	对象	FHandlerID	0
//    		otherBillInfo.setbizDate	业务日期	日期	FBizDate	2020-11-25
    		otherBillInfo.setBizDate(billbizdate);
//    		otherBillInfo.setnumber	单据编号	字符串	FNumber	AP2020000009
//    		otherBillInfo.setCU	控制单元	对象	FControlUnitID	00000000-0000-0000-0000-000000000000CCE7AED4
    		otherBillInfo.setCU(companyInfo.getCU());
//    		otherBillInfo.setlastUpdateTime	最后修改时间	日期	FLastUpdateTime	0
//    		otherBillInfo.setlastUpdateUser	最后修改者	对象	FLastUpdateUserID	0
//    		otherBillInfo.setcreateTime	创建时间	日期	FCreateTime	2020-11-25 10:10:27
    		otherBillInfo.setCreateTime(new Timestamp(billdate.getTime()));
//    		otherBillInfo.setcreator	创建者	对象	FCreatorID	256c221a-0106-1000-e000-10d7c0a813f413B7DE7F
//    		otherBillInfo.setid	ID	BOSUUID	FID	8i0AAAACKrZI2jpx

			this.yingfuMap.put(billNo, otherBillInfo);
		}else{
			otherBillInfo = (com.kingdee.eas.fi.ap.OtherBillInfo) this.yingfuMap.get(billNo);
		}
		//分录
		com.kingdee.eas.fi.ap.OtherBillentryInfo otherBillentryInfo = new com.kingdee.eas.fi.ap.OtherBillentryInfo();
//		otherBillentryInfo.setpayableDate	应付日期	日期	FPayableDate	0
//		otherBillentryInfo.setrequestedPayAmt	已申请付款金额	数字	FRequestedPayAmt	0
//		otherBillentryInfo.setunApportionAmount	未分摊本位币金额	数字	FUnApportionAmount	88
		otherBillentryInfo.setUnApportionAmount(billfee);
//		otherBillentryInfo.setapportionAmount	已分摊本位币金额	数字	FApportionAmount	0
//		otherBillentryInfo.setisFullWriteOff	是否完全核销	布尔	FIsFullWriteOff	0
//		otherBillentryInfo.setapPrintBillEntry	应付清单分录	对象	FApPrintBillEntryID	0
//		otherBillentryInfo.setapPrintBill	应付清单	对象	FApPrintBillID	0
//		otherBillentryInfo.sethead	单据头	集合	FParentID	8i0AAAACKrZI2jpx
//		otherBillentryInfo.setunInvoiceReqAmountLocal	未开票申请金额本位币	数字	FUnInvoiceReqAmountLocal	88
		otherBillentryInfo.setUnInvoiceReqAmountLocal(billfee);
//		otherBillentryInfo.setunInvoiceReqAmount	未开票申请金额	数字	FUnInvoiceReqAmount	88
		otherBillentryInfo.setUnInvoiceReqAmount(billfee);
//		otherBillentryInfo.setinvoiceReqAmountLocal	已开票申请金额本位币	数字	FInvoiceReqAmountLocal	0
//		otherBillentryInfo.setinvoiceReqAmount	已开票申请金额	数字	FInvoiceReqAmount	0
//		otherBillentryInfo.setunInvoiceReqBaseQty	未开票申请基本数量	数字	FUnInvoiceReqBaseQty	1
		otherBillentryInfo.setUnInvoiceReqBaseQty(BigDecimal.ONE);
//		otherBillentryInfo.setunInvoiceReqQty	未开票申请数量	数字	FUnInvoiceReqQty	1
		otherBillentryInfo.setUnInvoiceReqQty(BigDecimal.ONE);
//		otherBillentryInfo.setinvoiceReqBaseQty	已开票申请基本数量	数字	FInvoiceReqBaseQty	0
//		otherBillentryInfo.setinvoiceReqQty	已开票申请数量	数字	FInvoiceReqQty	0
//		otherBillentryInfo.setisQtyZero	零数量	布尔	FIsQtyZero	0
//		otherBillentryInfo.setfundFlowItem	资金流量项目	对象	FFundFlowItemID	0
//		otherBillentryInfo.setcostCenter	分录成本中心	对象	FCostCenterID	0
//		otherBillentryInfo.setmaterialModel	规格型号	字符串	FMaterialModel	0
//		otherBillentryInfo.setlot	批次	字符串	FLot	0
//		otherBillentryInfo.setbillDate	单据日期	日期	FBillDate	2020-11-25
		otherBillentryInfo.setBillDate(billdate);
//		otherBillentryInfo.setcompany	公司	字符串	FCompanyID	8i0AAAAATvzM567U
		otherBillentryInfo.setCompany(companyInfo.getId().toString());
//		otherBillentryInfo.setreversedBaseQty	已冲回基本数量	数字	FReversedBaseQty	0
//		otherBillentryInfo.settrackNumberzc	跟踪号	对象	FTrackNumberzcID	0
//		otherBillentryInfo.setproject	项目号	对象	FProjectID	0
//		otherBillentryInfo.setrecSendOrgUnit	收货（发货）组织	对象	FRecSendOrgUnitID	0
//		otherBillentryInfo.setcontractEntryID	合同单据分录ID	字符串	FContractEntryID	0
//		otherBillentryInfo.setcontractBillID	合同单据ID	字符串	FContractBillID	0
//		otherBillentryInfo.setcontractEntrySeq	合同行号	字符串	FContractEntrySeq	0
//		otherBillentryInfo.setcontractNum	合同编号	字符串	FContractNumber	0
//		otherBillentryInfo.setapportionAmtLocal	已分摊金额本位币	数字	FApportionAmtLocal	0
//		otherBillentryInfo.setlockVerifyQty	已锁定数量	数字	FLockVerifyQty	0
//		otherBillentryInfo.setverifyQty	已结算金额	数字	FVerifyQty	0
//		otherBillentryInfo.setisInvoiced	是否已开完票	布尔	FIsInvoiced	0
//		otherBillentryInfo.setinvoicedAmt	已开发票金额	数字	FInvoicedAmt	0
//		otherBillentryInfo.setinvoicedBaseQty	已开发票基本数量	数字	FInvoicedBaseQty	0
//		otherBillentryInfo.setinvoiceNumber	发票编号	字符串	FInvoiceNumber	0
//		otherBillentryInfo.setlocalUnwriteOffAmount	未核销本位币金额	数字	FLocalUnwriteOffAmount	88
		otherBillentryInfo.setLocalUnwriteOffAmount(billfee);
//		otherBillentryInfo.setunwriteOffBaseQty	未核销基本数量	数字	FUnwriteOffBaseQty	1
		otherBillentryInfo.setUnwriteOffBaseQty(BigDecimal.ONE);
//		otherBillentryInfo.setlocalWrittenOffAmount	已核销本位币金额	数字	FLocalWrittenOffAmount	0
//		otherBillentryInfo.setwittenOffBaseQty	已核销基本数量	数字	FWrittenOffBaseQty	0
//		otherBillentryInfo.settrackNumber	文本跟踪号	字符串	FTrackNumber	0
//		otherBillentryInfo.setcoreBillEntrySeq	核心单据分录行号	整数	FCoreBillEntrySeq	0
//		otherBillentryInfo.setcoreBillNumber	核心单据编号	字符串	FCoreBillNumber	0
//		otherBillentryInfo.setcoreBillEntryId	核心单据分录ID	字符串	FCoreBillEntryId	0
//		otherBillentryInfo.setcoreBillId	核心单据ID	字符串	FCoreBillId	0
//		otherBillentryInfo.setcoreBillType	核心单据类型	对象	FCoreBillTypeID	0
//		otherBillentryInfo.setsourceBillEntryId	源单据分录id	字符串	FSourceBillEntryId	0
//		otherBillentryInfo.setsourceBillId	源单id	字符串	FSourceBillId	0
//		otherBillentryInfo.setoppAccount	对方科目	对象	FOppAccountID	0
//		otherBillentryInfo.setaccount	应收（应付）科目	对象	FAccountID	0
//		otherBillentryInfo.sethisUnVerifyAmountLocal	历史未核销金额本位币	数字	FHisUnVerifyAmountLocal	0
//		otherBillentryInfo.sethisUnVerifyAmount	历史未核销金额	数字	FHisUnVerifyAmount	0
//		otherBillentryInfo.setsourceBillAsstActID	源单往来户id	字符串	FSourceBillAsstActID	0
//		otherBillentryInfo.setbaseQty	基本计量单位数量	数字	FBaseQty	1
		otherBillentryInfo.setBaseQty(BigDecimal.ONE);
//		otherBillentryInfo.setbaseUnit	基本计量单位	对象	FBaseUnitID	0
//		otherBillentryInfo.setassistQty	辅助数量	数字	FAssistQty	0
//		otherBillentryInfo.setassistUnit	辅助单位	对象	FAssistUnitID	0
//		otherBillentryInfo.setamountLocal	金额本位币	数字	FAmountLocal	88
		otherBillentryInfo.setAmountLocal(billfee);
//		otherBillentryInfo.setamount	金额	数字	FAmount	88
		otherBillentryInfo.setAmount(billfee);
//		otherBillentryInfo.settaxAmountLocal	税额本位币	数字	FTaxAmountLocal	0
//		otherBillentryInfo.settaxAmount	税额	数字	FTaxAmount	0
//		otherBillentryInfo.settaxRate	税率	数字	FTaxRate	0
//		otherBillentryInfo.setdiscountAmountLocal	折扣额本位币	数字	FDiscountAmountLocal	0
//		otherBillentryInfo.setdiscountAmount	折扣额	数字	FDiscountAmount	0
//		otherBillentryInfo.setdiscountRate	单位折扣	数字	FDiscountRate	0
//		otherBillentryInfo.setdiscountType	折扣方式	业务枚举	FDiscountType	-1
//		otherBillentryInfo.setactualPrice	实际含税单价	数字	FActualPrice	88
		otherBillentryInfo.setActualPrice(billfee);
//		otherBillentryInfo.settaxPrice	含税单价	数字	FTaxPrice	88
		otherBillentryInfo.setTaxPrice(billfee);
//		otherBillentryInfo.setrealPrice	实际单价	数字	FRealPrice	88
		otherBillentryInfo.setRealPrice(billfee);
//		otherBillentryInfo.setprice	单价	数字	FPrice	88
		otherBillentryInfo.setPrice(billfee);
//		otherBillentryInfo.setquantity	数量	数字	FQuantity	1
		otherBillentryInfo.setQuantity(BigDecimal.ONE);
//		otherBillentryInfo.setremark	备注	字符串	FRemark	0
//		otherBillentryInfo.setlockUnVerifyAmtLocal	未锁定金额本位币	数字	FLockUnVerifyAmtLocal	88
		otherBillentryInfo.setLockUnVerifyAmtLocal(billfee);
//		otherBillentryInfo.setlockUnVerifyAmt	未锁定金额	数字	FLockUnVerifyAmt	88
		otherBillentryInfo.setLockUnVerifyAmt(billfee);
//		otherBillentryInfo.setlockVerifyAmtLocal	已锁定金额本位币	数字	FLockVerifyAmtLocal	0
//		otherBillentryInfo.setlockVerifyAmt	已锁定金额	数字	FLockVerifyAmt	0
//		otherBillentryInfo.setunVerifyAmountLocal	未结算金额本位币	数字	FUnVerifyAmountLocal	88
		otherBillentryInfo.setUnVerifyAmountLocal(billfee);
//		otherBillentryInfo.setunVerifyAmount	未结算金额	数字	FUnVerifyAmount	88
		otherBillentryInfo.setUnVerifyAmount(billfee);
//		otherBillentryInfo.setverifyAmountLocal	已结算金额本位币	数字	FVerifyAmountLocal	0
//		otherBillentryInfo.setverifyAmount	已结算金额	数字	FVerifyAmount	0
//		otherBillentryInfo.setrecievePayAmountLocal	应收（付）金额本位币	数字	FRecievePayAmountLocal	88
		otherBillentryInfo.setRecievePayAmountLocal(billfee);
//		otherBillentryInfo.setrecievePayAmount	应收（付）金额	数字	FRecievePayAmount	88
		otherBillentryInfo.setRecievePayAmount(billfee);
//		otherBillentryInfo.setisPresent	是否赠品	布尔	FIsPresent	0
//		otherBillentryInfo.setexpenseItem	费用项目	对象	FExpenseItemID	8i0AAAAA3nR45LyU
		otherBillentryInfo.setExpenseItem(getExpenseTypeInfoF7FromNumber(ctx,"FY002001"));
//		otherBillentryInfo.setassistProperty	辅助属性	对象	FAssistPropertyID	0
//		otherBillentryInfo.setmeasureUnit	计量单位	对象	FMeasureUnitID	0
//		otherBillentryInfo.setmaterialName	物料名称	字符串	FMaterialName	0
//		otherBillentryInfo.setmaterial	物料	对象	FMaterialID	0
//		otherBillentryInfo.setrowType	行类型	对象	FRowTypeId	0
//		otherBillentryInfo.setseq	单据分录序列号	整数	FSeq	1
//		otherBillentryInfo.setid	ID	BOSUUID	FID	8i0AAAACKrftTbyB

		otherBillentryInfo.setHead(otherBillInfo);
		otherBillInfo.getEntry().add(otherBillentryInfo);
    }
    
    //根据名称或编码获取基础数据F7
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
    	MsgBox.showInfo("未找到对应的基础资料");
        e.printStackTrace();
      }
      return dataInfo;
    }

	/**
	 * 公司F7	CompanyOrgUnitInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected CompanyOrgUnitInfo getCompanyOrgUnitInfoF7(Context ctx, String name) throws Exception {
		CompanyOrgUnitInfo companyOrgUnitInfo = new CompanyOrgUnitInfo();
		try {
			CompanyOrgUnitCollection companyOrgUnitCollection = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection(getViewInfo1(name));
			if ((companyOrgUnitCollection  != null) && (companyOrgUnitCollection.size() > 0)) {
				companyOrgUnitInfo = companyOrgUnitCollection.get(0);
			} else {
				throw new Exception("公司："+name+"未在EAS中找到对应的公司");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return companyOrgUnitInfo;
	}
	
	/**
	 * 财务组织映射表取公司F7	CompanyOrgUnitInfo
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
				throw new Exception("公司："+number+"未在EAS中找到对应的公司");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return orgmapInfo;
	}
	
	/**
	 * 费用项目映射表取费用项目F7	CompanyOrgUnitInfo
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
				throw new Exception("费用项目："+id+"未在EAS映射表中找到对应的费用项目");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return feeitemInfo;
	}
	
	
	
	/**
	 * 结算方式F7	 SettlementTypeInfo
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
				throw new Exception("公司："+name+"未在EAS中找到对应的公司");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return settlementTypeInfo;
	}
	/**
	 * 销售组织	SaleOrgUnitInfo
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
				throw new Exception("销售组织："+number+"未找到对应的销售组织");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return saleOrgUnitInfo;
	}
	
	/**
	 * 采购组织	SaleOrgUnitInfo
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
				throw new Exception("采购组织："+number+"未找到对应的采购组织");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return purchaseOrgUnitInfo;
	}
	/**
	 * 辅助核算项目--往来类型
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
				throw new Exception("未找到辅助核算项目");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return asstActTypeInfo;
	}
	/**
	 * 收款科目
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception
	 */
	protected AccountViewInfo getAccountViewInfoF7(Context ctx, String number,String companyid) throws Exception {
		AccountViewInfo accountViewInfo = new AccountViewInfo();
		try {
			AccountViewCollection accountViewCollection = AccountViewFactory.getLocalInstance(ctx).getAccountViewCollection(" where number ='"+number+"' and companyid ='"+companyid+"'");
			if ((accountViewCollection  != null) && (accountViewCollection.size() > 0)) {
				accountViewInfo = accountViewCollection.get(0);
			} else {
				throw new Exception("未找到科目");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return accountViewInfo;
	}
	/**
	 * 银行账号收支户
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception
	 */
	protected AccountBankInfo getAccountBankInfoF7(Context ctx,String companyid) throws Exception {
		AccountBankInfo accountBankInfo = new AccountBankInfo();
		try {
			AccountBankCollection accountBankCollection = AccountBankFactory.getLocalInstance(ctx).getAccountBankCollection(" where ACCOUNTTYPE = 0 and company ='"+companyid+"'");
			if ((accountBankCollection  != null) && (accountBankCollection.size() > 0)) {
				accountBankInfo = accountBankCollection.get(0);
			} else {
				throw new Exception("未找到银行账号信息");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return accountBankInfo;
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
	protected EntityViewInfo getViewInfoByid(String id) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.EQUALS));
		evi.setFilter(filter);
		return evi;
	}
	/**
	 * 币别F7	CurrencyInfo
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
				throw new Exception("EAS中未找到对应的币别，请检查。");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return currencyInfo;
	}
	/**
	 * 客户分组F7	cSSPGroupInfo
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
				throw new Exception("EAS中未找到分组。");
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
				throw new Exception("EAS中未找到分组。");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return cSSPGroupStandardInfo;
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
	
	/**
	 * 映射表取客户F7		CustomerInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 */
	protected CustomerInfo getOthcustomerInfoF7(Context ctx, String id) throws Exception {
		CustomerInfo customerInfo = new CustomerInfo();
		OthCustomerInfo othCustomerInfo = new OthCustomerInfo();
//		try {
			String sql =" /*dialect*/select cfcustomer1id from CT_MAP_OthCustomer where cfbizid = '"+id+"'";
			  IRowSet rowSet = DbUtil.executeQuery(ctx, sql);
			  List  list = new ArrayList();
			  while (rowSet.next()) {
			   list.add(rowSet.getString("cfcustomer1id"));
			  }
			  if((list.size()>0) && (list.get(0) != null)){
//				  OthCustomerCollection othCustomerCollection = OthCustomerFactory.getLocalInstance(ctx).getOthCustomerCollection(getViewInfoByid(list.get(0).toString()));
//					if ((othCustomerCollection  != null) && (othCustomerCollection.size() > 0)) {
//						othCustomerInfo = OthCustomerFactory.getLocalInstance(ctx).getOthCustomerInfo();
//						othCustomerInfo = othCustomerCollection.get(0);
//						customerInfo = othCustomerInfo.getCustomer1();
						customerInfo = CustomerFactory.getLocalInstance(ctx).getCustomerInfo("where id = '"+list.get(0).toString()+"'");
			}else {
				throw new Exception("EAS中未找到对应的客户的关联渠道客户，请检查。");
			}
//		} catch (BOSException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (EASBizException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return customerInfo;
	}
	/**
	 * 客户F7		CustomerInfo
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
				throw new Exception("EAS中未找到对应的客户，请检查。");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}
	/**
	 * 付款方式F7		CustomerInfo
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
				throw new Exception("未找到付款方式");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return paymentTypeInfo;
	}
	
	/**
	 * 收款类型F7		ReceivingBillTypeInfo
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
				throw new Exception("未找到收款类型");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return receivingBillTypeInfo;
	}
	/**
	 * 用户F7		UserInfo
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
				throw new Exception("EAS未找到对应的用户，请检查。");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	/**
	 * 计量单位F7		MeasureUnitInfo
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
				throw new Exception("EAS中未找到对应的计量单位，请检查。");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return meamsureUnitInfo;
	}
	/**
	 * 费用项目映射表表取费用项目F7		ExpenseTypeInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected ExpenseTypeInfo getExpenseTypeInfoF7(Context ctx, String id) throws Exception {
		
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
			throw new Exception("EAS中未找到对应的费用项目，请检查。");
		}
//		FeeitemInfo feeitemInfo = new FeeitemInfo();
//		try {
//			FeeitemCollection feeitemCollection = FeeitemFactory.getLocalInstance(ctx).getFeeitemCollection( " where bizid = '"+id+"'");
//			if ((feeitemCollection  != null) && (feeitemCollection.size() > 0)) {
//				feeitemInfo = feeitemCollection.get(0);
//			} else {
//				throw new Exception("费用项目："+id+"未在EAS映射表中找到对应的费用项目");
//			}
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
		
		return expenseTypeInfo;
	}
	
	/**
	 * 费用项目F7		ExpenseTypeInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected ExpenseTypeInfo getExpenseTypeInfoF7FromNumber(Context ctx, String number) throws Exception {
		ExpenseTypeInfo expenseTypeInfo = new ExpenseTypeInfo();
		try {
			ExpenseTypeCollection expenseTypeCollection = ExpenseTypeFactory.getLocalInstance(ctx).getExpenseTypeCollection(getViewInfo(number));
			if ((expenseTypeCollection  != null) && (expenseTypeCollection.size() > 0)) {
				expenseTypeInfo = expenseTypeCollection.get(0);
			}else {
				throw new Exception("费用项目未找到");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return expenseTypeInfo;
	}
	/**
	 * 费用项目的类型F7		OperationTypeInfo
	 * @param ctx
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	protected OperationTypeInfo getOperationTypeInfoF7FromID(Context ctx, String id) throws Exception {
		OperationTypeInfo operationTypeInfo = new OperationTypeInfo();
		try {
			OperationTypeCollection operationTypeCollection = OperationTypeFactory.getLocalInstance(ctx).getOperationTypeCollection(" where id ='"+id+"'");
			if ((operationTypeCollection  != null) && (operationTypeCollection.size() > 0)) {
				operationTypeInfo = operationTypeCollection.get(0);
			}else {
				throw new Exception("费用项目类型未找到");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return operationTypeInfo;
	}
 
	protected void updateEntryinfo(Context ctx, OtherBillentryInfo entryInfo) throws Exception {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo = (CustomerInfo) entryInfo.get("entryothcustomer");
		String sql =" /*dialect*/ update T_AR_OtherBillentry set cfisbujiao =1,cfbujiaoamount = "+entryInfo.getAmount()+",cfentryothcustomer = '"+customerInfo.getId().toString() +"' where cfetrcharge_detail_id ='"+entryInfo.get("etrcharge_detail_id")+"'";
		System.out.println("更新补交应收单分录"+sql.toString());
		DbUtil.execute(ctx, sql);
	}
	protected void clearEntryinfo(Context ctx, OtherBillentryInfo entryInfo) throws Exception {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo = (CustomerInfo) entryInfo.get("entryothcustomer");
		String sql =" /*dialect*/ update T_AR_OtherBillentry set cfisbujiao =0,cfbujiaoamount = 0,cfentryothcustomer = '' where cfetrcharge_detail_id ='"+entryInfo.get("etrcharge_detail_id")+"'";
		System.out.println("清除补交应收单分录"+sql.toString());
		DbUtil.execute(ctx, sql);
	}
	/**
	 * 通过应收单分录的明细收费ID找应收单
	 * @param ctx
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected OtherBillInfo getARotherbillinfo(Context ctx, String id) throws Exception {
		OtherBillInfo otherBillInfo = new OtherBillInfo();
		String sql =" /*dialect*/ select bill.FID fid from T_AR_OtherBill bill INNER JOIN T_AR_OtherBillentry ent on bill.FID = ent.FPARENTID where ent.cfetrcharge_detail_id = '"+id+"'";
		  IRowSet rowSet = DbUtil.executeQuery(ctx, sql);
		  List  list = new ArrayList();
		  while (rowSet.next()) {
		   list.add(rowSet.getString("fid"));
		  }
		  if((list.size()>0) && (list.get(0) != null)){
			  otherBillInfo = OtherBillFactory.getLocalInstance(ctx).getOtherBillInfo("where id = '"+list.get(0).toString()+"'");
		}else {
			throw new Exception("EAS中未找到对应的客户的关联渠道客户，请检查。");
		}
		return otherBillInfo;
	}
	
	
	
}