package net.bit.rboard.dao;

public class DaoProvider {
	// 싱글톤으로 Dao객체를 제골해주는 클래스
	// 1. 자기자신을 정적멤버로 가진다.
	private static DaoProvider instance = new DaoProvider();
	// 2. 생성자를 private으로 만든다.
	private DaoProvider() { }
	// 3. getInstance메소드를 public으로 만들어 준다.
	public static DaoProvider getInstance() {
		return instance;
	}
	RBoardDao dao = new RBoardDao();
	RBoardCommentDao daoComment = new RBoardCommentDao();
	public RBoardDao getDao() {
		return dao;
	}
	public RBoardCommentDao getCommentDao() {
		return daoComment;
	}
}

