package net.bit.rboard.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.bit.rboard.db.DBUtil;
import net.bit.rboard.vo.RBoardVO;

public class RBoardDao {
	// 전체 개수 구하는 메소드
	public int selectCount(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "select count(*) from rboard";
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
	protected RBoardVO makeVo(ResultSet rs) {
		RBoardVO vo = new RBoardVO();
		try {
			vo.setIdx(rs.getInt("idx"));
			vo.setRef(rs.getInt("ref"));
			vo.setLev(rs.getInt("lev"));
			vo.setSeq(rs.getInt("seq"));
			vo.setName(rs.getString("name"));
			vo.setPassword(rs.getString("password"));
			vo.setTitle(rs.getString("title"));
			vo.setContent(rs.getString("content"));
			vo.setWdate(rs.getString("wdate"));
			vo.setHit(rs.getInt("hit"));
			vo.setIp(rs.getString("ip"));
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}
	// 1개의 레코드를 얻는 메소드
	public RBoardVO selectById(Connection conn, int idx) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from rboard where idx = ?";
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
	// 조회수를 증가하는 메소드
	public void increment(Connection conn, int idx) {
		PreparedStatement pstmt = null;
		try {
			String sql = "update rboard set hit = hit + 1 where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
	}
	// 수정하는 메소드
	public int update(Connection conn, RBoardVO vo) {
		PreparedStatement pstmt = null;
		try {
			String sql = "update rboard set title=?, content=? ";
			sql += " where idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getIdx());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(pstmt);
		}
		return 0;
	}
	// 삭제하는 메소드
	public int delete(Connection conn, int idx) {
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from rboard where idx = ?";
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
	public int insert(Connection conn, RBoardVO vo) { // 원본글저장
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into rboard "
					+ "(idx, ref, lev, seq, name, password, title, content, wdate, hit, ip) "
					+ "values (rboard_idx_seq.NEXTVAL, rboard_idx_seq.CURRVAL, 0, 0, ?, ?, ?, ?, ?, 0, ?)");
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getTitle());
			pstmt.setString(4, vo.getContent());
			pstmt.setString(5, vo.getWdate());
			pstmt.setString(6, vo.getIp());
			return pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return 0;
	}
	// 답변 추가하는 메소드
	public int reply(Connection conn, RBoardVO vo) { // 답변저장
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into rboard "
				+ "(idx, ref, lev, seq, name, password, title, content, wdate, hit, ip) "
				+ "values (rboard_idx_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?)");
			pstmt.setInt(1, vo.getRef());
			pstmt.setInt(2, vo.getLev());
			pstmt.setInt(3, vo.getSeq());
			pstmt.setString(4, vo.getName());
			pstmt.setString(5, vo.getPassword());
			pstmt.setString(6, vo.getTitle());
			pstmt.setString(7, vo.getContent());
			pstmt.setString(8, vo.getWdate());
			pstmt.setString(9, vo.getIp());
			return pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return 0;
	}
	// 페이지당 목록을 리턴해주는 메소드
	public List<RBoardVO> select(Connection conn, int startNo, int endNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
				"select idx, ref, lev, seq, name, password, title, content, wdate, hit, ip from ( "
			  + "    select rownum rnum, idx, ref, lev, seq, name, password, title, content, wdate, hit, ip from ( "
			  + "        select * from rboard m order by ref desc, seq "
			  + "    ) where rownum <= ? "
			  + ") where rnum >= ?");
			pstmt.setInt(1, endNo);
			pstmt.setInt(2, startNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				List<RBoardVO> list = new ArrayList<RBoardVO>();
				do {
					list.add(makeVo(rs));
				} while(rs.next());
				return list;
			}
		} catch(SQLException e) {
			
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return Collections.emptyList();
	}
	// ref와 레벨이 같은놈 중에서 제일큰 seq값을 가져온다.
	public int getMaxSeq(Connection conn, int ref, int lev) {
		return 0;
	}
	// 크거나 같은놈의 seq값을 1씩 모두 증가 시킨다.
	public int incrementSeq(Connection conn, int ref, int seq) {
		PreparedStatement pstmt = null;
		try {
			String sql = "update rboard set seq = seq + 1 where ref = ? and seq >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, seq);
			return pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return 0;
	}
}
