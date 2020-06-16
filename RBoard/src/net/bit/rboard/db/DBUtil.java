package net.bit.rboard.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	// Connection을 닫는 메소드
	public static void close(Connection conn) {
		try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// Statement를 닫는 메소드
	public static void close(Statement stmt) {
		try {
			if(stmt != null) stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// PreparedStatement를 닫는 메소드
	public static void close(PreparedStatement pstmt) {
		try {
			if(pstmt != null) pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// ResultSet을 닫는 메소드
	public static void close(ResultSet rs) {
		try {
			if(rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// rollBack을 하는 메소드
	public static void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
