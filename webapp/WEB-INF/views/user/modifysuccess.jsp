<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<%-- <jsp:include page="/WEB-INF/views/includes/header.jsp" /> --%>
		<c:import url="/WEB-INF/views/includes/header_jstl.jsp"></c:import>
		<div id="content">
			<div id="user">
				<p class="jr-success">
					성공적으로 수정하였습니다.
					<br><br>
					<a href="${pageContext.servletContext.contextPath }/main">목록으로</a>
				</p>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="main"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<%-- <jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" /> --%>
	</div>
</body>
</html>