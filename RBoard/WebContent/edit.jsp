<%@page import="net.bit.rboard.service.SelectByIdService"%>
<%@page import="net.bit.rboard.vo.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%	// 페이지번호 받기
	String t = request.getParameter("page");
	int pageNo = 1;
	if(t != null && !t.equals("") && t.trim().length() != 0) {
		try {
			pageNo = Integer.parseInt(t);
		} catch(Exception e){ }
	}
	// 글번호 받기
	t = request.getParameter("idx");
	int idx = 0;
	if(t!=null && !t.equals("") && t.trim().length() != 0) {
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
<script type="text/javascript">
	function chk(){
		f = document.form1;
		if(!f.password.value || f.password.value.trim().length == 0) {
			alert('암호넣어!!!');
			f.password.value = "";
			f.password.focus();
			return false;
		}
		if(!f.title.value || f.title.value.trim().length == 0) {
			alert('제목넣어!!!');
			f.title.value = "";
			f.title.focus();
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

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>답변형게시판 - 수정하기</title>
</head>
<body>
<form method="post" name="form1" action="editok.jsp?page=<%=pageNo %>&idx=<%=vo.getIdx() %>">
	이름 : <input type="text" name="name" value="<%=vo.getName() %>" readonly="readonly"/><br/>
	암호 : <input type="password" name="password"/><br/>
	제목 : <input type="text" name="title" size="80" value="<%=vo.getTitle() %>"/><br/>
	내용 : <textarea rows="10" cols="80" name="content"><%=vo.getContent() %></textarea><br/>
	<input type="submit" value="수정하기" />
	<input type="button" value="돌아가기" onclick="location.href='view.jsp?page=<%=pageNo %>&idx=<%=vo.getIdx() %>'"/>
</form>
</body>
</html>