package net.bit.rboard.db;

import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;

public class DBCPProvider {
	public static Connection getConnection() throws SQLException, NamingException {
		Context	initContext = new InitialContext();
		Context envContext = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/Oracle");
		Connection conn = ds.getConnection();
		return conn;
	}
	public static Connection getConnection(String db) throws SQLException, NamingException {
		Context	initContext = new InitialContext();
		Context envContext = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/" + db);
		Connection conn = ds.getConnection();
		return conn;
	}
}
