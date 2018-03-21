<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script>
	/* function post(){
		var id = document.getElementsByName('email')[0].value;
		var pw = document.getElementsByName('password')[0].value;
		var url = document.getElementsByName('url')[0].value;
		
		alert(id);
		alert(url);
		
		var form = document.createElement("form");

		form.setAttribute("charset", "UTF-8");
		form.setAttribute("method", "Post"); // Get 또는 Post 입력
		form.setAttribute("action", url);
		
		
	} */
</script>
</head>
<body>
	<div id="container">
		<%-- <jsp:include page="/WEB-INF/views/includes/header.jsp" /> --%>
		<c:import url="/WEB-INF/views/includes/header_jstl.jsp"></c:import>
		<div id="content">
			<div id="user">
				<form id="login-form" name="loginform" method="post" action="${pageContext.servletContext.contextPath }/user/login">
					<%-- <c:set var="url" value="${pageContext.servletContext.contextPath }/user/login" />
					<input type="hidden" name="url" value="${url }"> --%>
					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="${email }" required>
					<label class="block-label" >패스워드</label>
					<input name="password" type="password" value="" required>
					<c:if test='${result eq "fail"}'>
						<p>
							로그인이 실패 했습니다.
						</p>
					</c:if>
					<%-- <% 						
						if("fail".equals(result)){
					%>
						<p>
							로그인이 실패 했습니다.
						</p>
					<%
						}
					%> --%>
					<!-- <input type="submit" value="로그인" onclick="post()"> -->
					<input type="submit" value="로그인">
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