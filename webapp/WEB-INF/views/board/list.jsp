<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header_jstl.jsp"></c:import>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board/search" method="post">
					<input type="hidden" name="page" value="1">
					<input type="text" id="kwd" name="kwd" value="${kwd }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>rnum</th>
						<th>고유번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<!-- page가 음수이거나 총 페이지보다 클 경우에 -->	
					<%-- <c:if test="${totalCount/10+0.9 < page}">
						<c:set var="page" value="${totalCount/10+0.9}" />						
					</c:if>
					<c:if test="${1 > page}">
						<c:set var="page" value="1" />						
					</c:if> --%>
					
					
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status">
						<c:set var="depth" value="${vo.depth}" />
						<tr>
							<td>${vo.rownum}</td>
							<td> ${vo.no }</td>
							<td style="text-align:left;padding-left:${20*depth}px">
								<c:if test="${depth ne 0}">
									<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png" />
								</c:if>
								<!-- 삭제가 안된 경우 -->
								<c:if test="${vo.deleteBool ne 1}">
									<!-- 키워드가 있을 경우 -->
									<c:if test="${kwd ne null }">
										<a href="${pageContext.servletContext.contextPath }/board/view?no=${vo.no }&page=${page}&kwd=${kwd}">${vo.title}</a>
									</c:if>
									<!-- 키워드가 없을 경우 -->
									<c:if test="${kwd eq null }">
										<a href="${pageContext.servletContext.contextPath }/board/view?no=${vo.no }&page=${page}">${vo.title}</a>
									</c:if>
								</c:if>
								<c:if test="${vo.deleteBool eq 1}">
									${vo.title}
								</c:if>
							</td>
							<c:forEach var="userInfo" items="${vo.writer}" varStatus="status">
							<td>${userInfo.value}</td>
							
							<td>${vo.hit }</td>
							<td>${vo.regDate}</td>
							<td>
								<c:if test="${authUser.no eq userInfo.key }">
									<c:if test="${vo.deleteBool ne 1}">
										<a href="${pageContext.servletContext.contextPath }/board/delete?no=${vo.no }&page=${page}" class="del">삭제</a>
									</c:if>
								</c:if>
							</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
			<%-- 	<input type="text" value="${totalCount }"/> --%>
				<div class="pager">
					<ul>
					<fmt:parseNumber var="divPage" value="${page/5.1}" integerOnly="true" />
					<!-- ${5*(page/5.1)+1} -->
					<c:set value="${5*divPage+1}" var="realPage"/>
					<c:if test="${kwd eq null }">
					<!-- c:out으로 page(5)로 나눴을 때 따로 구해서 값 계산 해야될 듯 -->
						<c:if test="${page-1 >= 1 }">
							<li><a href="${pageContext.servletContext.contextPath }/board/list?page=${page-1}">◀</a></li>
						</c:if>
						<c:if test="${page-1 < 1 }">
							<!-- <li><a href="${pageContext.servletContext.contextPath }/board?a=index&page=1">◀</a></li> -->
							<li>◀</li>
						</c:if>
						<c:forEach begin="0" end="4" step="1" var="vo" varStatus="status">
							<c:choose>
								<c:when test="${totalCount/10+0.9 >= realPage + status.index}">
									<c:if test="${realPage + status.index eq page}">
									<li class="selected">${realPage + status.index}</li>
									</c:if>
									<c:if test="${realPage + status.index ne page}">
										<li><a href="${pageContext.servletContext.contextPath }/board/list?page=${realPage + status.index}">${realPage + status.index}</a></li>
									</c:if>
								</c:when>
								<c:otherwise>
									<li>${realPage + status.index}</li>
								</c:otherwise>
							</c:choose>
							<%-- <c:if test="${totalCount/10+0.9 >= realPage + status.index}">
								<c:if test="${realPage + status.index eq page}">
									<li class="selected">${realPage + status.index}</li>
								</c:if>
								<c:if test="${realPage + status.index ne page}">
									<li><a href="${pageContext.servletContext.contextPath }/board?a=index&page=${realPage + status.index}">${realPage + status.index}</a></li>
								</c:if>
							</c:if>
							<c:if test="${totalCount/10+0.9 < realPage + status.index}">
								<li>${realPage + status.index}</li>
							</c:if> --%>
							<%-- <li><a href="${pageContext.servletContext.contextPath }/board?a=index&page=${realPage}">${realPage}</a></li>
							<li><a href="${pageContext.servletContext.contextPath }/board?a=index&page=${realPage+1}">${realPage+1}</a></li>
							<li class="selected">${realPage+2}</li>
							<li><a href="${pageContext.servletContext.contextPath }/board?a=index&page=${realPage+3}">${realPage+3}</a></li>
							<li>${realPage+4}</li> --%>
						</c:forEach>
						<c:if test="${page+1 <= totalCount/10+0.9 }">
							<li><a href="${pageContext.servletContext.contextPath }/board/list?page=${page+1}">▶</a></li>
						</c:if>
						<c:if test="${page+1 > totalCount/10+0.9 }">
							<%-- <li><a href="${pageContext.servletContext.contextPath }/board?a=index&page=${page}">▶</a></li> --%>
							<li>▶</li>
						</c:if>
					</c:if>
					
					<!-- 검색 키워드가 있을 경우 -->
					<c:if test="${kwd ne null }">
						<c:if test="${page-1 >= 1 }">
							<li><a href="${pageContext.servletContext.contextPath }/board?a=search&page=${page-1}&kwd=${kwd}">◀</a></li>
						</c:if>
						<c:if test="${page-1 < 1 }">
							<%-- <li><a href="${pageContext.servletContext.contextPath }/board?a=search&page=1&kwd=${kwd}">◀</a></li> --%>
							<li>◀</li>
						</c:if>
						<c:forEach begin="0" end="4" step="1" var="vo" varStatus="status">
							<c:choose>
								<c:when test="${totalCount/10+0.9 >= realPage + status.index}">
									<c:if test="${realPage + status.index eq page}">
									<li class="selected">${realPage + status.index}</li>
									</c:if>
									<c:if test="${realPage + status.index ne page}">
										<li><a href="${pageContext.servletContext.contextPath }/board?a=search&page=${realPage + status.index}&kwd=${kwd}">${realPage + status.index}</a></li>
									</c:if>
								</c:when>
								<c:otherwise>
									<li>${realPage + status.index}</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${page+1 <= totalCount/10+0.9 }">
							<li><a href="${pageContext.servletContext.contextPath }/board?a=search&page=${page+1}&kwd=${kwd}">▶</a></li>
						</c:if>
						<c:if test="${page+1 > totalCount/10+0.9 }">
							<%-- <li><a href="${pageContext.servletContext.contextPath }/board?a=search&page=${page}&kwd=${kwd}">▶</a></li> --%>
							<li>▶</li>
						</c:if>
					</c:if>
					</ul>
				</div>		
				
				<c:if test="${!empty authUser}">
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board/write?page=${page}" id="new-book">글쓰기</a>
					</div>	
				</c:if>
				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
</body>
</html>