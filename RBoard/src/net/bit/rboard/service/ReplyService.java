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

public class ReplyService {
	private static ReplyService instance = new ReplyService();
	private ReplyService() { }
	public static ReplyService getInstance() {
		return instance;
	}
	public int reply(RBoardVO vo) {
		Connection conn = null; 
		try {
			conn = DBCPProvider.getConnection();
			RBoardDao dao = DaoProvider.getInstance().getDao();
			conn.setAutoCommit(false);			// AutoCommit 취소
			// 트랜잭션이란 DB에서 한 번에 실행되야할 명령의 집합을 의미한다.
			// 트랜잭션은 부분 완료를 시키면 안된다. 따라서 tomcatDBCP를 사용할 경우
			// 별도의 설정이 없으면 SQL 명령문 한 개가 실행되면 자동으로 commit이 되므로
			// 2개 이상의 SQL 명령문이 실행되어야 할 경우 AutoCommit을 취소하고 모든
			// 작업이 완료되면 conn.commit()을 실행해서 DB에 SQL 명령문의 처리 결과를
			// 반영하는 것이 바람직하다.
			// lev와 sep를 1 증가시킨다.
			vo.setLev(vo.getLev() + 1);
			vo.setSeq(vo.getSeq() + 1);
			int t = dao.incrementSeq(conn, vo.getRef(), vo.getSeq());
			t = dao.reply(conn, vo);
			conn.commit();
			return t;
		} catch (Exception e) {
			DBUtil.rollback(conn);
		} finally{
			DBUtil.close(conn);
		}
		return 0;
	}
}
