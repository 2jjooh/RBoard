<%@page import="net.bit.rboard.service.*"%>
<%@page import="net.bit.rboard.vo.*"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	// 페이지번호 받기
	String t = request.getParameter("page");
	int pageNo = 1;
	if(t != null && !t.equals("") && t.trim().length() != 0){
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
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="board"  class="net.bit.rboard.vo.RBoardVO">
	<jsp:setProperty property="*" name="board"/>
</jsp:useBean>
<%
	// 수정하기 수행
	if(UpdateService.getInstance().update(board) == 0) {
		out.println("<script>alert('수정실패!!!');</script>");
	}
	out.println("<script>");
	out.println("location.href='view.jsp?page=" + pageNo + "&idx=" + idx + "'</script>");
%>
</body>
</html>