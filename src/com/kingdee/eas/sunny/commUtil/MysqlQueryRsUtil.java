package com.kingdee.eas.sunny.commUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @author chens
 *
 */
public class MysqlQueryRsUtil {


	/**
	 * 根据SQL获取中间库数据集
	 * @param ctx
	 * @param conn
	 * @param ps
	 * @param query
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static ResultSet getRs (Context ctx,Connection conn,PreparedStatement ps,String query) throws SQLException, ClassNotFoundException{
    	ResultSet rs =null;
    	mysqlConnectionUtil sqlutil = new mysqlConnectionUtil();
    	rs = sqlutil.onlyquery(ctx,conn,ps,query);
		return rs;
    }
	public static IRowSet getIncomeRation (Context ctx,String query) throws SQLException, ClassNotFoundException{
		IRowSet incomeRation = null;
		try {
			 incomeRation = DbUtil.executeQuery(ctx, query);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return incomeRation;
    }
}
