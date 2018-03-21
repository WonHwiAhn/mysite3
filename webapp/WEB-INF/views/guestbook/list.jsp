<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 

<%@ page contentType="text/html;charset=UTF-8" %>
<%
	pageContext.setAttribute("newLine", "\n");
%>
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
			<div id="guestbook">
				<form action="${pageContext.servletContext.contextPath }/gb/add" method="post">
					<table>
						<tr>
						
							<td>이름: </td><td><input type="text" name="name"></td>
							<td>패스워드: </td><td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align="center"><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
					
				</form>
				<ul>
					<li>
					<c:set var="count" value="${fn:length(list) }" />
					<%-- <c:set var="newLine" value="\n"/> --%>
					<c:forEach items="${list }" var="vo" varStatus="status">
					
						<table>
							<tr>
								<%-- <td>[${status.index } : ${status.count}]</td> --%>
								<td>[${count - status.count + 1}]</td>
								<td>${vo.name }</td>
								<td>${vo.regDate }</td>
								<td><a href="${pageContext.servletContext.contextPath }/gb/delete?no=${vo.no}">삭제</a></td>
							</tr>
							<tr>
								<td colspan=4>
									${fn:replace(vo.content, newLine, "<br>")}
								</td>
							</tr>
						</table>
						
					</c:forEach>
						
						<br>
					</li>
				</ul>
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