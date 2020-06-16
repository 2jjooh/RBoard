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


public class UpdateService {
	private static UpdateService instance = new UpdateService();
	private UpdateService() { }
	public static UpdateService getInstance() {
		return instance;
	}
	public int update(RBoardVO vo) {
		Connection conn = null;
		try {
			conn = DBCPProvider.getConnection();
			RBoardDao dao = DaoProvider.getInstance().getDao();
			RBoardVO originalVo = dao.selectById(conn, vo.getIdx());
			if(originalVo.getPassword().equals(vo.getPassword()))
				return dao.update(conn, vo);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
		return 0;
	}
	public int updateComment(RBoardCommentVO vo) {
		Connection conn = null;
		try {
			conn = DBCPProvider.getConnection();
			RBoardCommentDao dao = DaoProvider.getInstance().getCommentDao();
			RBoardCommentVO originalVo = dao.selectById(conn, vo.getIdx());
			if(originalVo.getPassword().equals(vo.getPassword()))
				return dao.update(conn, vo);
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
