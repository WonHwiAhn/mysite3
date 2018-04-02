<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script>
	$(function(){
		$("#btn-checkemail").click(function(){
			console.log("click!");
			var email = $("#email").val();
			console.log(email);
			
			if(email == ""){
				return;
			}
			
			$.ajax({
				url:"${pageContext.servletContext.contextPath }/api/user/checkemail?eamil=" + email,
				type:"get",
				data:"", //ex) email=myroom123@naver.com&
				contentType:"application/json",
				success: function(response){
					console.log(response);
					if(response.result != "success"){
						console.log(response.message);
						return;
					}
					if(response.data == "exist"){
						alert('이미 사용중인 이메일입니다.');
						$("#email").val("").focus();
						return;
					}
					
					$("#img-check").show();
					$("#btn-checkemail").hide();
				},
				error: function(xhr, status, e){
					console.log(status + ":" + e);
				}
			});
			
		});
		console.log("dom loaded");
	});
</script>	
</head>
<body>
	<div id="container">
		<%-- <jsp:include page="/WEB-INF/views/includes/header.jsp" /> --%>
		<c:import url="/WEB-INF/views/includes/header_jstl.jsp"></c:import>
		<div id="content">
			<div id="user">
			<!-- form태그 사용시 유의해야 될 부분은 항상 ModelAttribute를 받는데
				 처음 회원가입에 접근할 때는 GET부분으로 들어가기 때문에 Controller /join GET방식에도 @ModelAttribute를 받아놔야된다. -->
				<form:form
					modelAttribute="userVO"
					id="join-form" 
					name="joinForm" 
					method="POST" 
					action="${pageContext.servletContext.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<!-- <input id="name" name="name" type="text" value=""> 
						 <spring:hasBindErrors name="userVO">
						   <c:if test="${errors.hasFieldErrors('name') }">
						   		<p style="padding:0px; text-align:list; color:#f00">
						        	<strong>${errors.getFieldError( 'name' ).defaultMessage }</strong>
						        </p>
						   </c:if>
						</spring:hasBindErrors>-->
					<form:input path="name"/>
					<p style="padding:0px; font-weight:bold;text-align:center; color:#f00">
						<form:errors path="name"/>
					</p>
					
					<!-- <label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value=""> -->
					<form:input path="email"/>
					<p style="padding:0px; font-weight:bold;text-align:center; color:#f00">
						<form:errors path="email"/>
					</p>
					
					<img id="img-check" style="display:none" src="${pageContext.servletContext.contextPath }/assets/images/check.png" />
					<input type="button" id="btn-checkemail" value="id 중복체크">
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<!-- 조건이 여러개 일 때는 랜덤으로 결과가 나오기 때문에 default설정이 필요함 -->
					<spring:hasBindErrors name="userVO">
						   <c:if test="${errors.hasFieldErrors('password') }">
						   		<p style="padding:0px; text-align:list; color:#f00">
						        	<strong>
						        		<spring:message
						        			code="${errors.getFieldError('password').codes[0]}"
						        			text="${errors.getFieldError('password').defaultMessage}"
						  
						        		/>
						        	</strong>
						        </p>
						   </c:if>
					</spring:hasBindErrors>
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <form:radiobutton path="gender" value="female" />
						<!-- <input type="radio" name="gender" value="female" checked="checked"> -->
						<label>남</label> <form:radiobutton path="gender" value="male" /> 
						<!-- <input type="radio" name="gender" value="male"> -->
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>
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