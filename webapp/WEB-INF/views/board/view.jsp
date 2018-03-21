<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("newLine", "\n");
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header_jstl.jsp"></c:import>
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title}</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(vo.content, newLine, "<br>")}
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<!-- 키워드가 없을 경우에 -->
					<c:if test="${kwd eq null }">
						<a href="${pageContext.servletContext.contextPath }/board/list?page=${page} ">글목록</a>
						<!-- 로그인이 되어 있을 경우에 -->
						<c:if test="${!empty authUser}">
							<c:forEach var="userInfo" items="${vo.writer}" varStatus="status">
								<!-- 게시글 작성자와 로그인 사용자가 다를 경우 -->
								<c:if test="${authUser.no ne userInfo.key }">
									<a href="${pageContext.servletContext.contextPath }/board/write?no=${vo.no}&page=${page}&kwd=${kwd}">답글작성</a>
								</c:if>
								<!-- 게시글 작성자와 로그인 사용자가 같을 경우 -->
								<c:if test="${authUser.no eq userInfo.key }">
									<a href="${pageContext.servletContext.contextPath }/board/modify?no=${vo.no }&page=${page}&kwd=${kwd}">글수정</a>
								</c:if>
							</c:forEach>
						</c:if>
					</c:if>
					<!-- 키워드가 있을 경우에 -->
					<c:if test="${kwd ne null }">
						<a href="${pageContext.servletContext.contextPath }/board/list?page=${page}&kwd=${kwd } ">글목록</a>
					<c:if test="${!empty authUser}">
						<c:forEach var="userInfo" items="${vo.writer}" varStatus="status">
							<c:if test="${authUser.no ne userInfo.key }">
								<a href="${pageContext.servletContext.contextPath }/board/write?no=${vo.no}&page=${page}">답글작성</a>
							</c:if>
							<c:if test="${authUser.no eq userInfo.key }">
								<a href="${pageContext.servletContext.contextPath }/board/modify?no=${vo.no }&page=${page}&kwd=${kwd}">글수정</a>
								<%-- <a href="${pageContext.servletContext.contextPath }/board/modify?no=${vo.no }&page=${page}">글수정</a> --%>
							</c:if>
						</c:forEach>
					</c:if>
					</c:if>
					<%-- <c:if test="${!empty authUser}"> --%>
					<%-- <c:forEach var="userInfo" items="${vo.writer}" varStatus="status">
						<c:if test="${authUser.no ne userInfo.key }">
							<a href="${pageContext.servletContext.contextPath }/board?a=write_resform&no=${vo.no}&page=${page}">답글작성</a>
						</c:if>
						<c:if test="${authUser.no eq userInfo.key }">
							<a href="${pageContext.servletContext.contextPath }/board?a=modifyform&no=${vo.no }&page=${page}">글수정</a>
						</c:if>
					</c:forEach> --%>
					<%-- </c:if> --%>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
</body>
</html>