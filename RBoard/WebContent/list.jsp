<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="net.bit.rboard.service.*"%>
<%@page import="net.bit.rboard.vo.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>답변형게시판 목록보기</title>
</head>
<body>
<%
	// 페이지번호 받기
	String t = request.getParameter("page");
	int pageNo = 1;
	if(t != null && !t.equals("") && t.trim().length() != 0) {
		try {
			pageNo = Integer.parseInt(t);
		} catch(Exception e) { }
	}
	// 해당 페이지의 목록을 table에서 얻어오기
	List<RBoardVO> list = SelectService.getInstance().select(pageNo);
	// 얻어온 내용을 한 페이지 분량을 기억할 RBoardList 객체에 대입
	RBoardList board = new RBoardList();
	board.setList(list, pageNo);
%>
<table width="90%" border="1" align="center">
	<tr>
		<th colspan="5"><h2>게시판 목록보기</h2></th>
	</tr>
	<tr>
		<td colspan="5" align="right">
			<%=board.getTotalCount()+"개" %>
			(<%=board.getCurrentPage()+"/" + board.getTotalPage() %>Page)
		</td>
	</tr>
	<tr>
		<th>번호</th>
		<th>작성자</th>
		<th>제목</th>
		<th>작성일</th>
		<th>조회수</th>
	</tr>
<%
	for(RBoardVO tt : board.getList()) {
		out.println("<tr>");
		out.println("<td align='center'>" + tt.getIdx() + "</td>");
		out.println("<td align='center'>" + tt.getName() + "</td>");
		out.println("<td>");
		if(tt.getLev() > 0) {
			for(int i=0 ; i<tt.getLev() ; i++) {
				out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			out.println("Re : ");
		}
		out.println("<a href='view.jsp?page=" + pageNo + "&idx=" + tt.getIdx() + "'>");
		out.println(tt.getTitle() + "</a>");
		// 댓글 개수를 출력하자
		int x = SelectCountService.getInstance().selectCommentCount(tt.getIdx());
		out.println("(" + x + ")</td>");
		out.println("<td align='center'>");
		out.println(tt.getWdate().substring(2,10));
		//out.println(tt.getWdate());
		out.println("</td>");
		out.println("<td align='center'>" + tt.getHit() + "</td>");
		out.println("</tr>");
	}
%>
	<tr>
		<td colspan="5" align="center">
<%
	// 이전처리
	if(board.getStartPage() > 10){
		out.println("[<a href='list.jsp?page="+ (board.getStartPage()-1) +"'이전</a>]");
	}
	// 페이지처리
	for(int i=board.getStartPage() ; i<=board.getEndPage() ; i++) {
		if(i != board.getCurrentPage())
			out.println("[<a href='list.jsp?page="+ i +"'>" + i + "</a>] ");
		else
			out.println("[" + i + "] ");
	}
	// 이후처리
	if(board.getEndPage() < board.getTotalPage()){
		out.println("[<a href='list.jsp?page="+ (board.getEndPage()+1) +"'이전</a>]");
	}
%>
		</td>
	</tr>
	<tr>
		<td colspan="5" align="right">
			<input type="button" onclick="location.href='insertform.jsp'" value="쓰기"/>
		</td>
	</tr>
</table>
</body>
</html>