<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
			<div id="board">
				<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board">
					<input type = "hidden" name = "a" value="write_res">
					<input type = "hidden" name="no" value="${vo.no }">
					<input type = "hidden" name="page" value="${page }">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글쓰기</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value="RE:${vo.title}"></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content"></textarea>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<c:if test="${kwd eq null}">
							<a href="${pageContext.servletContext.contextPath }/board?a=index&no=${vo.no }&page=${page}">취소</a>					
						</c:if>
						<c:if test="${kwd ne null}">
							<a href="${pageContext.servletContext.contextPath }/board?a=viewSearch&no=${vo.no }&page=${page}&kwd=${kwd}">취소</a>
						</c:if>
						<input type="submit" value="등록">
					</div>
				</form>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
</body>
</html>