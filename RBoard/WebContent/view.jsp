<%@page import="java.util.List"%>
<%@page import="net.bit.rboard.service.SelectService"%>
<%@page import="net.bit.rboard.service.SelectCountService"%>
<%@page import="net.bit.rboard.service.SelectByIdService"%>
<%@page import="net.bit.rboard.vo.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%	// 페이지번호 받기
	String t = request.getParameter("page");
	int pageNo = 1;
	if(t != null && !t.equals("") && t.trim().length() != 0) {
		try {
			pageNo = Integer.parseInt(t);
		} catch(Exception e) { }
	}
	// 글번호 받기
	t = request.getParameter("idx");
	int idx = 0;
	if(t != null && !t.equals("") && t.trim().length() != 0) {
		try {
			idx = Integer.parseInt(t);
		} catch(Exception e) { 
			out.println("<script>alert('인덱스 에러!!');");
			out.println("location.href='list.jsp'</script>");
		}
	} else {
		out.println("<script>alert('인덱스 에러!!');");
		out.println("location.href='list.jsp'</script>");
	}
	// 해당 글번호에 글을 가져온다.
	RBoardVO vo = SelectByIdService.getInstance().selectByIdx(idx);
	if(vo == null) {
		out.println("<script>alert('해당글번호 없음!!!');");
		out.println("location.href='list.jsp'</script>");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>답변형게시판 - 내용보기</title>
<script type="text/javascript">
	function commentChk() {
		f = document.commentform;
		if(!f.name.value || f.name.value.trim().length == 0) {
			alert('이름넣어!!!');
			f.name.value = "";
			f.name.focus();
			return false;
		}
		if(!f.password.value || f.password.value.trim().length == 0) {
			alert('암호넣어!!!');
			f.password.value = "";
			f.password.focus();
			return false;
		}
		if(!f.content.value || f.content.value.trim().length == 0) {
			alert('내용넣어!!!');
			f.content.value = "";
			f.content.focus();
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<form method="post" name="form1">
	이름 : <input type="text" name="name" value="<%=vo.getName() %>" readonly="readonly"/><br/>
	작성일 : <input type="text" name="wdate" value="<%=vo.getWdate() %>" readonly="readonly"/><br/>
	IP : <input type="text" name="ip" value="<%=vo.getIp() %>" readonly="readonly"/><br/>
	제목 : <input type="text" name="title" size="80" value="<%=vo.getTitle() %>" readonly="readonly"/><br/>
	내용 : <textarea rows="10" cols="80" name="content" readonly="readonly"><%=vo.getContent() %></textarea><br/>
</form>
	<input type="button" onclick="location.href='list.jsp?page=<%=pageNo %>'" value="리스트로"/>
	<input type="button" onclick="location.href='reply.jsp?page=<%=pageNo %>&idx=<%=vo.getIdx() %>'" value="답변달기"/>
	<input type="button" onclick="location.href='edit.jsp?page=<%=pageNo %>&idx=<%=vo.getIdx() %>'" value="수정하기"/>
	<input type="button" onclick="location.href='delete.jsp?page=<%=pageNo %>&idx=<%=vo.getIdx() %>'" value="삭제하기"/>
	<br/>
<!--  댓글폼 -->
<form method="post" name="commentform" action="commentOk.jsp" onsubmit="return commentChk();">
	<input type="hidden" name="idx" value="<%=vo.getIdx() %>"/>   
	<input type="hidden" name="ref" value="<%=vo.getIdx() %>"/>   
	<input type="hidden" name="page" value="<%=pageNo %>"/>   
	이름 : <input type="text" name="name" />   
	비번 : <input type="password" name="password" /> <br/>
	내용 : <textarea rows="3" cols="50" name="content"></textarea> <br/>
	<input type="submit" value="댓글달기"/>
</form>
<!-- 댓글 리스트 -->
<%
	// 해당글의 댓글 리스트를 얻어온다.
	int cnt = SelectCountService.getInstance().selectCommentCount(vo.getIdx());
	//댓글이 있는 경우에만 댓글목록을 출력한다.
	if(cnt > 0) {
		List<RBoardCommentVO> comment = SelectService.getInstance().selectComment(vo.getIdx());
		RBoardCommentList list = new RBoardCommentList();
		list.setList(comment);
		out.println("<div width='90%' style='border:1px solid gray;'>");
		for(RBoardCommentVO v : list.getList()) {
			out.println("<div width='95%' style='border:1px solid silver;'>");
			out.println("<div width='90%' style='background-color:silver'>");
			out.println("이름:" + v.getName() + "(" + v.getWdate() + ")");			
			out.println("</div>");
			out.println(v.getContent().replace("\n", "<br/>"));			
			out.println("</div>");
		}
		out.println("</div>");
	} else {
		out.println("<div width='90%' style='border:1px solid gray;'>");
		out.println("등록된 댓글이 없습니다.");
		out.println("</div>");
	}
%>
</body>
</html>