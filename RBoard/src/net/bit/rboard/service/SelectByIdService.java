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

public class SelectByIdService {
	private static SelectByIdService instance = new SelectByIdService();
	private SelectByIdService() { }
	public static SelectByIdService getInstance() {
		return instance;
	}
	// 지정 글번호의 글을 가져오는 서비스
	public RBoardVO selectByIdx(int idx) {
		Connection conn = null;
		try {
			conn = DBCPProvider.getConnection();
			RBoardDao dao = DaoProvider.getInstance().getDao();
			// 조회수를 증가하는 메소드 호출
			dao.increment(conn, idx);
			return dao.selectById(conn, idx);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
		return null;
	}
	// 지정 Comment 글번호의 글을 가져오는 서비스
	public RBoardCommentVO selectByIdxComment(int idx) {
		Connection conn = null;
		try {
			conn = DBCPProvider.getConnection();
			RBoardCommentDao dao = DaoProvider.getInstance().getCommentDao();
			return dao.selectById(conn, idx);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
		return null;
	}
}
