package net.bit.rboard.vo;
// 답변형 게시판의 댓글에 사용할 Value Object
public class RBoardCommentVO {
	private int idx;			// 입력되는 순서대로 부여되는 일련번호
	private int ref;			// 게시글, 답변글의 글번호
	private String name;		// 댓글 작성자 이름
	private String password;	// 댓글 비밀번호
	private String content;		// 댓글 내용
	private String wdate;		// 댓글 작성일
	private String ip;			// 댓글 작성자 IP
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
