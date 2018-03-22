<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<%-- <jsp:include page="/WEB-INF/views/includes/header.jsp" /> --%>
		<c:import url="/WEB-INF/views/includes/header_jstl.jsp"></c:import>
		<div id="content">
			<div id="guestbook" class="delete-form">
				<form method="post" action="${pageContext.servletContext.contextPath }/gb/delete">
					<%-- <input type='hidden' name="no" value="<%=request.getParameter("no") %>"> --%>
					<input type='hidden' name="no" value="${param.no }">
					<label>비밀번호</label>
					<input type="password" name="password">
					<input type="submit" value="확인">
				</form>
				<a href="${pageContext.servletContext.contextPath }/gb/list">방명록 리스트</a>
			</div>
		</div>
		<%-- <jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" /> --%>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="gb"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
</body>
</html>