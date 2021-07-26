package com.kingdee.eas.sunny.commUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

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
	 * ����SQL��ȡ�м�����ݼ�
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
	/** 
	 * ����SQL�����м������
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void updateRs (Connection conn,String sql) throws SQLException, ClassNotFoundException{
		PreparedStatement pStatement = conn.prepareStatement(sql);
		pStatement.executeUpdate();//TODO ����ʧ��������δ���
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
