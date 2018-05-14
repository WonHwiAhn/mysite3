<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
	<link href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/css/lightbox.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/lightbox.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
</head>
<body>
<div id="header">
	<h1><a href="${pageContext.servletContext.contextPath }">MySite</a></h1>
	<ul>
		<!-- <li>님 안녕하세요 ^^;</li> -->
		
		<c:choose>
			<c:when test="${empty authUser}">
				<li><a href="${pageContext.servletContext.contextPath }/user/join">회원가입</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/user/login">로그인</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.servletContext.contextPath }/user/logout">로그아웃</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/user/modify">회원정보수정</a></li>
				<li>환영합니다! ${authUser.name}님!</li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>
<div id="container">