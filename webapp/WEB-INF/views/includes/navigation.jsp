<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="navigation">
	<ul>
		<c:choose>
			<c:when test='${param.menu == "main"}'>
				<li class="selected"><a href="${pageContext.servletContext.contextPath }/main">안원휘</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/gb">방명록</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/board/list?page=1">게시판</a></li>
			</c:when>
			<c:when test='${param.menu == "gb"}'>
				<li><a href="${pageContext.servletContext.contextPath }/main">안원휘</a></li>
				<li class="selected"><a href="${pageContext.servletContext.contextPath }/gb">방명록</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/board/list?page=1">게시판</a></li>
			</c:when>
			<c:when test='${param.menu == "board"}'>
				<li><a href="${pageContext.servletContext.contextPath }/main">안원휘</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/gb">방명록</a></li>
				<li class="selected"><a href="${pageContext.servletContext.contextPath }/board/list?page=1">게시판</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.servletContext.contextPath }/main">안원휘</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/gb">방명록</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/board/list?page=1">게시판</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>