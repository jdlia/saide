package com.kingdee.eas.sunny.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK; //import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean; //import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.scm.sd.sale.FeeItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.sdyg.mapping.Feeitem;
import com.kingdee.eas.custom.sdyg.mapping.FeeitemCollection;
import com.kingdee.eas.custom.sdyg.mapping.FeeitemFactory;
import com.kingdee.eas.custom.sdyg.mapping.FeeitemInfo;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapCollection;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapFactory;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapInfo;
import com.kingdee.eas.custom.sdyg.mapping.OthCustomer;
import com.kingdee.eas.custom.sdyg.mapping.OthCustomerCollection;
import com.kingdee.eas.custom.sdyg.mapping.OthCustomerFactory;
import com.kingdee.eas.custom.sdyg.mapping.OthCustomerInfo;
import com.kingdee.eas.sunny.commUtil.mysqlConnectionUtil;
import com.kingdee.eas.sunny.syncbiz.OrgMappingCollection;
import com.kingdee.eas.sunny.syncbiz.OrgMappingEntryFactory;
import com.kingdee.eas.sunny.syncbiz.OrgMappingEntryInfo;
import com.kingdee.eas.sunny.syncbiz.OrgMappingFactory;
import com.kingdee.eas.sunny.syncbiz.OrgMappingInfo;

public class SyncBizSystemFacadeControllerBean extends AbstractSyncBizSystemFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.sunny.app.SyncBizSystemFacadeControllerBean");

	/**
	 * 同步基础资料
	 */
	@Override
	protected void _syncBaseData(Context ctx) throws BOSException, EASBizException {
		super._syncBaseData(ctx);
		try {
			syncOrg1(ctx);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		try {
//			syncCustomer(ctx);
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			syncExpItem(ctx);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * 同步收据
	 */
	@Override
	protected void _syncReceipt(Context ctx) throws BOSException, EASBizException {
		super._syncReceipt(ctx);
		mysqlConnectionUtil util = new mysqlConnectionUtil();
		try {
			util.query("select * from t_receipt");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 同步对账单
	 */
	@Override
	protected void _syncReconciliation(Context ctx) throws BOSException, EASBizException {
		super._syncReconciliation(ctx);
	}

	/**
	 * 同步费用项目
	 * 
	 * @param ctx
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void syncExpItem(Context ctx) throws ClassNotFoundException, SQLException, BOSException, EASBizException {
		// 查询数据
		String sql = "select fee.* from t_feeItem fee  left join t_baseData baseData  on fee.ID = baseData.ID  where baseData.kdGetMark <> '1' or baseData.kdGetMark is null ";
		mysqlConnectionUtil util = new mysqlConnectionUtil();
		Connection connection = util.createConn();
		PreparedStatement pStatement = connection.prepareStatement(sql);
		ResultSet resultSet = pStatement.executeQuery();
		FeeitemInfo feeInfo = null;
		// 循环查询结果
		StringBuffer strIDbuff = new StringBuffer();
		while (resultSet.next()) {
			String bizID = resultSet.getString("ID");
			FeeitemCollection col = FeeitemFactory.getLocalInstance(ctx).getFeeitemCollection(" where bizID ='"+bizID+"'");
			if (col != null && col.size() != 0) {
				continue;
			}
			strIDbuff.append(bizID);
			strIDbuff.append(".");
			feeInfo.setBizID(bizID);//业务系统主键
			feeInfo.setItemName(resultSet.getString("itemName"));
			feeInfo.setItemNo(resultSet.getString("itemNo"));
			feeInfo.setUseCompany(resultSet.getString("useCompany"));
			feeInfo.setCreateTime(new Timestamp(new Date().getTime()));
			FeeitemFactory.getLocalInstance(ctx).addnew(feeInfo);
		}
		String strID = strIDbuff.toString().substring(0, strIDbuff.length()-1);
		if(strID.length() > 1){
			String sql1 = "update t_baseData set kdGetMark = '1' where ID in ('"+strID+")";
			PreparedStatement pStatement1 = connection.prepareStatement(sql1);
			pStatement1.execute(sql1);
		}
		resultSet.close();
		pStatement.close();
		connection.close();
	}

	/**
	 * 同步客户（患者、渠道、保险）
	 * 
	 * @param ctx
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void syncCustomer(Context ctx) throws ClassNotFoundException, SQLException, BOSException, EASBizException {
		// 查询数据
		String sql = "select oth.* from t_othcustomer oth  left join t_baseData baseData  on oth.ID = baseData.ID  where baseData.kdGetMark <> '1' or baseData.kdGetMark is null  ";
		mysqlConnectionUtil util = new mysqlConnectionUtil();
		Connection connection = util.createConn();
		PreparedStatement pStatement = connection.prepareStatement(sql);
		ResultSet resultSet = pStatement.executeQuery();
		OthCustomerInfo othCustomerInfo = null;
		// 循环查询结果
		StringBuffer strIDbuff = new StringBuffer();
		while (resultSet.next()) {
			String bizID = resultSet.getString("ID");
			OthCustomerCollection col = OthCustomerFactory.getLocalInstance(ctx).getOthCustomerCollection(" where bizID ='"+bizID+"'");
			if (col != null && col.size() != 0) {
				continue;
			}
			strIDbuff.append(bizID);
			strIDbuff.append(".");
			othCustomerInfo.setBizID(bizID);//业务系统主键
			othCustomerInfo.setBizNumber(resultSet.getString("number"));
			othCustomerInfo.setBizName(resultSet.getString("name"));
			othCustomerInfo.setBizType(resultSet.getString("type"));
			othCustomerInfo.setBizTypeName(resultSet.getString("bizType"));
			othCustomerInfo.setBizDirection(resultSet.getString("direction"));
			othCustomerInfo.setCreateTime(new Timestamp(new Date().getTime()));
			OthCustomerFactory.getLocalInstance(ctx).addnew(othCustomerInfo);
		}
		String strID = strIDbuff.toString().substring(0, strIDbuff.length()-1);
		if(strID.length() > 1){
			String sql1 = "update t_baseData set kdGetMark = '1' where ID in ('"+strID+")";
			PreparedStatement pStatement1 = connection.prepareStatement(sql1);
			pStatement1.execute(sql1);
		}
		resultSet.close();
		pStatement.close();
		connection.close();
	
	}

	/**
	 * 同步门诊（组织机构）</br> 新增组织映射表单据，当新增门诊组织时，插入单据中，客户自行配对映射数据。
	 * 
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void syncOrg(Context ctx) throws EASBizException, BOSException, SQLException, ClassNotFoundException {
		// 查询数据
		String sql = "select * from t_org org  left join t_baseData baseData  on org.ID = baseData.ID  where baseData.kdGetMark <> '1'";
		mysqlConnectionUtil util = new mysqlConnectionUtil();
		Connection connection = util.createConn();
		PreparedStatement pStatement = connection.prepareStatement(sql);
		ResultSet resultSet = pStatement.executeQuery();
		OrgMappingInfo info = null;
		// 查询是否存在映射表,若不存在则新增一张，若存在获取对象
		OrgMappingCollection col = OrgMappingFactory.getLocalInstance(ctx).getOrgMappingCollection();
		if (col != null && col.size() != 0) {
			info = col.get(0);
		} else {
			info = new OrgMappingInfo();
			info.setId(BOSUuid.create(info.getBOSType()));
			OrgMappingFactory.getLocalInstance(ctx).addnew(info);
		}
		// 循环查询结果创建分录
		while (resultSet.next()) {
			OrgMappingEntryInfo etyInfo = new OrgMappingEntryInfo();
			etyInfo.setId(BOSUuid.create(etyInfo.getBOSType()));
			etyInfo.setBizID(resultSet.getString("ID"));//业务系统主键
			etyInfo.setDgtNo(resultSet.getString("dgtNo"));//数字编码
			etyInfo.setAlphNo(resultSet.getString("alphNo"));//业务系统字母编码
			etyInfo.setCompanyName(resultSet.getString("companyName"));//业务系统门诊名称
			etyInfo.setCreateData(new Date());//创建时间
			etyInfo.setParent(info);
			OrgMappingEntryFactory.getLocalInstance(ctx).addnew(etyInfo);
		}
		resultSet.close();
		pStatement.close();
		connection.close();
	}
	
	/**
	 * 同步门诊（组织机构）</br> 新增组织映射表单据，当新增门诊组织时，插入单据中，客户自行配对映射数据。
	 * 
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void syncOrg1(Context ctx) throws EASBizException, BOSException, SQLException, ClassNotFoundException {
		// 查询数据
		String sql = "select * from t_org org  left join t_baseData baseData  on org.ID = baseData.ID  where baseData.kdGetMark <> '1' or baseData.kdGetMark is null ";
		mysqlConnectionUtil util = new mysqlConnectionUtil();
		Connection connection = util.createConn();
		PreparedStatement pStatement = connection.prepareStatement(sql);
		ResultSet resultSet = pStatement.executeQuery();
		OrgmapInfo info = new OrgmapInfo();
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
		while (resultSet.next()) {
			String bizID = resultSet.getString("ID");
			OrgmapCollection col = OrgmapFactory.getLocalInstance(ctx).getOrgmapCollection(" where bizID ='"+bizID+"'");
			if (col != null && col.size() != 0) {
				continue;
			}
			strIDbuff.append(bizID);
			strIDbuff.append(".");
			info.setBizID(bizID);//业务系统主键
			info.setDgtNo(resultSet.getString("dgtNo"));//数字编码
			info.setAlphNo(resultSet.getString("alphNo"));//业务系统字母编码
			info.setCompanyName(resultSet.getString("companyName"));//业务系统门诊名称
			info.setCreateTime(new Timestamp(new Date().getTime()));//创建时间
			OrgmapFactory.getLocalInstance(ctx).addnew(info);
		}
		String strID = strIDbuff.toString().substring(0, strIDbuff.length()-1);
		if(strID.length() > 1){
			String sql1 = "update t_baseData set kdGetMark = '1' where ID in ('"+strID+")";
			PreparedStatement pStatement1 = connection.prepareStatement(sql1);
			pStatement1.execute(sql1);
		}
		resultSet.close();
		pStatement.close();
		connection.close();
	}
}