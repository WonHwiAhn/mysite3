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

				<form id="join-form" name="modifyForm" method="POST" action="${pageContext.servletContext.contextPath }/user/modify">
					<input type="hidden" name="no" value=${authUser.no } />
					
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="${authUser.name}">
					
					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="${authUser.email }" readonly style="background-color: #e2e2e2;">
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						
						<c:choose>
							<c:when test='${authUser.gender == "female"}'>
								<label>여</label> 
								<input type="radio" name="gender" value="female" checked="checked">
								
								<label>남</label> 
								<input type="radio" name="gender" value="male">
							</c:when>
							
							<c:when test='${authUser.gender == "male"}'>
								<label>여</label> 
								<input type="radio" name="gender" value="female">
								
								<label>남</label> 
								<input type="radio" name="gender" value="male" checked="checked">
							</c:when>
						</c:choose>
					</fieldset>
					
					<input type="submit" value="수정하기">
					
				</form>
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