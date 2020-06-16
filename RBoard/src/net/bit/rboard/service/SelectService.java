package net.bit.rboard.service;

import java.sql.*;
import java.util.*;

import javax.naming.*;

import net.bit.rboard.dao.DaoProvider;
import net.bit.rboard.dao.RBoardCommentDao;
import net.bit.rboard.dao.RBoardDao;
import net.bit.rboard.db.DBCPProvider;
import net.bit.rboard.db.DBUtil;
import net.bit.rboard.vo.RBoardCommentVO;
import net.bit.rboard.vo.RBoardVO;

public class SelectService {
	private static SelectService instance = new SelectService();
	private SelectService() { }
	public static SelectService getInstance() {
		return instance;
	}
	public List<RBoardVO> select(int pageNo) {
		Connection conn = null;
		try {
			conn = DBCPProvider.getConnection();
			RBoardDao dao = DaoProvider.getInstance().getDao();
			int currentPage = pageNo;
			int totalCount = SelectCountService.getInstance().selectCount();
			int pageSize = 10;
			int totalPage = (totalCount-1) / pageSize + 1;
			if(currentPage > totalPage) currentPage = totalPage;
			int startNo = (currentPage-1) * pageSize + 1;
			int endNo = startNo + pageSize -1;
			if(endNo > totalCount) endNo = totalCount;
			List<RBoardVO> list = dao.select(conn, startNo, endNo);
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
		return null;
	}
	public List<RBoardCommentVO> selectComment(int ref) {
		Connection conn = null;
		try {
			conn = DBCPProvider.getConnection();
			RBoardCommentDao dao = DaoProvider.getInstance().getCommentDao();
			List<RBoardCommentVO> list = dao.select(conn, ref);
			return list;
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
