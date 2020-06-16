package net.bit.rboard.service;

import java.sql.*;

import javax.naming.*;

import net.bit.rboard.dao.*;
import net.bit.rboard.db.*;

public class SelectCountService {
	private static SelectCountService instance = new SelectCountService();
	private SelectCountService() { }
	public static SelectCountService getInstance() {
		return instance;
	}
	// 레코드 전체개수를 구한다.
	public int selectCount() {
		Connection conn = null;
		try {
			conn = DBCPProvider.getConnection();
			RBoardDao dao = DaoProvider.getInstance().getDao();
			return dao.selectCount(conn);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
		return 0;
	}
	// comment 개수를 구한다.
	public int selectCommentCount(int ref) {
		Connection conn = null;
		try {
			conn = DBCPProvider.getConnection();
			RBoardCommentDao dao = DaoProvider.getInstance().getCommentDao();
			return dao.selectCount(conn, ref);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
		return 0;
	}
}
