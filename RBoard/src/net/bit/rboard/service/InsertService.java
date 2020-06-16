package net.bit.rboard.service;

import java.sql.*;

import javax.naming.*;

import net.bit.rboard.dao.DaoProvider;
import net.bit.rboard.dao.RBoardCommentDao;
import net.bit.rboard.dao.RBoardDao;
import net.bit.rboard.db.DBCPProvider;
import net.bit.rboard.db.DBUtil;
import net.bit.rboard.vo.RBoardCommentVO;
import net.bit.rboard.vo.RBoardVO;

public class InsertService {
	private static InsertService instance = new InsertService();
	private InsertService() { }
	public static InsertService getInstance() {
		return instance;
	}
	public int insert(RBoardVO vo) {
		Connection conn = null;
		try {
			conn = DBCPProvider.getConnection();
			RBoardDao dao = DaoProvider.getInstance().getDao();
			return dao.insert(conn, vo);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
		return 0;
	}
	public int insertComment(RBoardCommentVO vo) {
		Connection conn = null;
		try {
			conn = DBCPProvider.getConnection();
			RBoardCommentDao dao = DaoProvider.getInstance().getCommentDao();
			return dao.insert(conn, vo);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
		return 0;
	}
}
