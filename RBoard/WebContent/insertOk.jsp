<%@page import="net.bit.rboard.service.InsertService"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="board" class="net.bit.rboard.vo.RBoardVO">
	<jsp:setProperty property="*" name="board"/>
</jsp:useBean>
<%
	// 저장을 한다.
	board.setIp(request.getRemoteAddr());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	board.setWdate(sdf.format(new Date()));
	// out.println(board + "<br/>");
	if(InsertService.getInstance().insert(board) == 0)
		out.println("저장되지 않았습니다");
	else
		out.println("성공적으로 저장되었습니다");
	response.sendRedirect("list.jsp");
%>
</body>
</html>