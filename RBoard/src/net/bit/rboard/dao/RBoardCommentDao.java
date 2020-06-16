package net.bit.rboard.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.bit.rboard.db.DBUtil;
import net.bit.rboard.vo.RBoardCommentVO;
import net.bit.rboard.vo.RBoardVO;

public class RBoardCommentDao {
	// 원본글에 대한 댓글의 개수 구하는 메소드
	public int selectCount(Connection conn, int ref) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "select count(*) from rboard_comment where ref = " + ref;
			rs = stmt.executeQuery(sql);
			rs.next();
			return rs.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
		}
		return 0;
	}
	// 레코드의 내용을 객체에 대입해주는 메소드
	protected RBoardCommentVO makeVo(ResultSet rs) {
		RBoardCommentVO vo = new RBoardCommentVO();
		try {
			vo.setIdx(rs.getInt("idx"));
			vo.setRef(rs.getInt("ref"));
			vo.setName(rs.getString("name"));
			vo.setPassword(rs.getString("password"));
			vo.setContent(rs.getString("content"));
			vo.setWdate(rs.getString("wdate"));
			vo.setIp(rs.getString("ip"));
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}
	// 1개의 레코드를 얻는 메소드
	public RBoardCommentVO selectById(Connection conn, int idx) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from rboard_comment where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if(rs.next())
				return makeVo(rs);
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return null;
	}
	// 수정하는 메소드
	public int update(Connection conn, RBoardCommentVO vo) {
		PreparedStatement pstmt = null;
		try {
			String sql = "update rboard_comment set content = ? where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getContent());
			pstmt.setInt(2, vo.getIdx());
			return pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return 0;
	}
	// 삭제하는 메소드
	public int delete(Connection conn, int idx) {
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from rboard_comment where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			return pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return 0;
	}
	// 레코드 추가하는 메소드
	public int insert(Connection conn, RBoardCommentVO vo) { // 원본글저장
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(
				"insert into rboard_comment(idx, ref, name, password, content, wdate, ip) "
			  + "values(rboard_comment_idx_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1,vo.getRef());
			pstmt.setString(2,vo.getName() );
			pstmt.setString(3,vo.getPassword() );
			pstmt.setString(4,vo.getContent() );
			pstmt.setString(5,vo.getWdate() );
			pstmt.setString(6,vo.getIp());
			return pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return 0;
	}
	// 댓글 목록을 리턴해주는 메소드
	public List<RBoardCommentVO> select(Connection conn, int ref){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from rboard_comment where ref = ?");
			pstmt.setInt(1, ref);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				List<RBoardCommentVO> list = new ArrayList<RBoardCommentVO>();
				do {
					list.add(makeVo(rs));
				} while (rs.next());
				return list;
			} 
		} catch(SQLException e) {
			
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return Collections.emptyList();
	}
}
