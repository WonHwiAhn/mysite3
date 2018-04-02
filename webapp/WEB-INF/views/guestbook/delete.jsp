<%-- <%@page import="com.cafe24.mysite.repository.GuestbookDAO"%>
<%
	request.setCharacterEncoding("utf-8");
	
	String no = request.getParameter("no");
	String password = request.getParameter("password");
	
	System.out.println("delete.jsp ==> password ==> " + password);
	
	GuestbookDAO dao = new GuestbookDAO();
	
	boolean check = dao.check(no, password);
	
	if(check){
		dao.delete(no);
		response.sendRedirect("${pageContext.servletContext.contextPath }/gb/list");
	}else{
		response.sendRedirect("${pageContext.servletContext.contextPath }/gb/failed");
	}
%> --%>