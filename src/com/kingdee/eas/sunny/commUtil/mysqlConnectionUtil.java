package com.kingdee.eas.sunny.commUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.Context;

public class mysqlConnectionUtil {

	/**
	 * �м������
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection createConn() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
//		String url = "jdbc:mysql://61.135.215.38:10168/mysql?serverTimezone=GMT%2B8";
		//���Ի���134
//		String url = "jdbc:mysql://192.168.8.134:3306/sunny";
		//��ʽ����125
		String url = "jdbc:mysql://192.168.8.125:1212/sunny";
		String user = "root";
		String password = "sunny123";
		Connection connections = DriverManager.getConnection(url, user, password);
		return connections;
	}

	/**
	 * ���ݲ�ѯ(����)
	 * 
	 * @param query
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return Set
	 */
	public static ResultSet query(String query) throws SQLException, ClassNotFoundException {
		Connection connection = createConn();
		PreparedStatement pStatement = connection.prepareStatement(query);
		ResultSet resultSet = pStatement.executeQuery();
//		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//		int column = resultSetMetaData.getColumnCount();
//		Set rsltSet = new HashSet();
//		while (resultSet.next()) {
//			for (int i = 1; i <= column; i++) {
//				rsltSet.add(resultSet.getObject(i));
//			}
//		}
		resultSet.close();
		pStatement.close();
		connection.close();
		return resultSet;
	}
	
	public ResultSet onlyquery(Context ctx,Connection conn,PreparedStatement ps,String query) throws SQLException, ClassNotFoundException {
		ps = conn.prepareStatement(query);
		ResultSet resultSet = ps.executeQuery();
		return resultSet;
	}
	
	/**
	 * �ر�����
	 * @param ctx
	 * @param conn
	 * @param ps
	 * @param rs
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void close(Connection conn,PreparedStatement ps,ResultSet rs) throws SQLException, ClassNotFoundException {
		if(rs != null){
			rs.close();
		}
		if(ps != null){
			ps.close();
		}
		if(conn != null){
			conn.close();
		}
	}
	
	/**
	 * ���ݸ���
	 * 
	 * @param strMark
	 *            biz:����ҵ���base�����»�����
	 * @param pramSet
	 *            ��������Ĭ�ϰ����м���ֶ�˳��
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void update(String strMark, Set pramSet) throws SQLException, ClassNotFoundException {
		Connection connection = createConn();
		String sql = "";
		for (Object item : pramSet) {
			if ("biz".equalsIgnoreCase(strMark)) {
				Map pramMap = (Map) item;
				sql = "update t_bizData set";
				if (pramMap.containsKey("kdId"))
					sql = sql + "kdId = ?";
				if (pramMap.containsKey("kdEntryId"))
					sql = sql + "kdEntryId = ?";
				if (pramMap.containsKey("kdGetMark"))
					sql = sql + "kdGetMark = ?";
				if (pramMap.containsKey("kdEndMark"))
					sql = sql + "kdEndMark = ?";
				if (pramMap.containsKey("kdEndDate"))
					sql = sql + "kdEndDate = ?";
				if (pramMap.containsKey("kdGetAbandonMark"))
					sql = sql + "kdGetAbandonMark = ?";
				if (pramMap.containsKey("kdAbandonEndMark"))
					sql = sql + "kdAbandonEndMark = ?";
				if (pramMap.containsKey("kdAbandonEndDate"))
					sql = sql + "kdAbandonEndDate = ?";
				sql = sql + "where ID = ?";
				if (pramMap.containsKey("entryId"))
					sql = sql + "and entryId = ?";
			} else if ("base".equalsIgnoreCase(strMark)) {
				Map pramMap = (Map) item;
				sql = "update t_bizData set";
				if (pramMap.containsKey("kdId"))
					sql = sql + "kdId = ?";
				if (pramMap.containsKey("kdGetMark"))
					sql = sql + "kdGetMark = ?";
				if (pramMap.containsKey("kdEndMark"))
					sql = sql + "kdEndMark = ?";
				if (pramMap.containsKey("kdEndDate"))
					sql = sql + "kdEndDate = ?";
				if (pramMap.containsKey("kdGetAbandonMark"))
					sql = sql + "kdGetAbandonMark = ?";
				if (pramMap.containsKey("kdAbandonEndMark"))
					sql = sql + "kdAbandonEndMark = ?";
				if (pramMap.containsKey("kdAbandonEndDate"))
					sql = sql + "kdAbandonEndDate = ?";
				sql = sql + "where ID = ?";
			} else
				return;
		}
		PreparedStatement pStatement = connection.prepareStatement(sql);
		pStatement.executeUpdate();//TODO ����ʧ��������δ���
		pStatement.close();
		connection.close();
	}
}
