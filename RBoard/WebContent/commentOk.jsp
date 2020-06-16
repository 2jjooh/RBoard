<%@page import="net.bit.rboard.service.InsertService"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	//페이지번호 받기
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
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="board" class="net.bit.rboard.vo.RBoardCommentVO">
	<jsp:setProperty property="*" name="board"/>
</jsp:useBean>
<%
	// 저장을 한다.
	board.setIp(request.getRemoteAddr());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	board.setWdate(sdf.format(new Date()));
	if(InsertService.getInstance().insertComment(board) == 0)
		out.println("저장되지 않았습니다");
	else
		out.println("성공적으로 저장되었습니다");
	response.sendRedirect("view.jsp?page=" + pageNo + "&idx=" + idx);
%>
</body>
</html>