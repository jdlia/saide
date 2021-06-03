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
	 * 中间库链接
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection createConn() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
//		String url = "jdbc:mysql://61.135.215.38:10168/mysql?serverTimezone=GMT%2B8";
		//测试环境134
//		String url = "jdbc:mysql://192.168.8.134:3306/sunny";
		//正式环境125
		String url = "jdbc:mysql://192.168.8.125:1212/sunny";
		String user = "root";
		String password = "sunny123";
		Connection connections = DriverManager.getConnection(url, user, password);
		return connections;
	}

	/**
	 * 数据查询(废弃)
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
	 * 关闭连接
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
	 * 数据更新
	 * 
	 * @param strMark
	 *            biz:更新业务表，base：更新基础表
	 * @param pramSet
	 *            参数集合默认按照中间表字段顺序
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
		pStatement.executeUpdate();//TODO 更新失败数据如何处理
		pStatement.close();
		connection.close();
	}
}
