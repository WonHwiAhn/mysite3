<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="/WEB-INF/views/includes/header_jstl.jsp" />

	<script>
	/**************************************************************/
	/*********            jQuery plugin       *********************/
	/**************************************************************/
	(function($){ // $ jQuery 변수임을 나타냄 만약 a로 받으면 a(this) 이런식으로 써줌.
		$.fn.hello = function(){
			var $element = $(this); 
			console.log($(this));
			console.log($element.attr("id")+":hello~");
		}
	})(jQuery);
	
	/**************************************************************/
	/*********            dialog 속성                      *********************/
	/**************************************************************/
	
	
		// using ejs template
		var ejsListItem = new EJS({
			url:"${pageContext.request.contextPath }/assets/js/ejs/template/listitem.ejs"
		});
		
		var startNo = 0;
		var isEnd = false;
		
		var messageBox = function(title, message){
			$("#dialog-message").attr("title", title);
			$("#dialog-message p").text(message);
			$( "#dialog-message" ).dialog({
			    modal: true,
			    buttons: {
			      	"확인": function() {
			        $( this ).dialog( "close" );
			       	}
			    },
			    close: callback || function(){ //null주면 에러이기 때문에 callback을 넣어둠.
			    	console.log('닫기');
			    }
		    });
		}
		
		var render = function(mode, vo){
			var html = ejsListItem.render(vo);
					/* 모듈화 시켰지만 이것도 보기 싫어서 template을 사용해서 페이지에서 아예 뺴버림
					"<li data-no='" + vo.no + "'>"+
					"<strong>" + vo.name + "</strong>"+
					"<p>" + vo.content.replace(/\n/gi, "<br>") + "</p>"+
					"<strong></strong>"+
					"<a href='#' data-no='" + vo.no  + "' class='del-ajax'>삭제</a> "+
					"</li>"; */
			// href을 #처리해놓으면 스크롤이 밑으로 내려갔을 때 맨 위로 올라가는 현상이 발생한다.
			// 그래서 #으로 걸어놨을 경우 맨위로 안가게 click이벤트에서 막아줘야된다.32번째줄 참고
			// 그럼에도 불구하고 href를 사용하는 이유는 의미상 사용하는 것
			
			if(mode==true){
				$("#list-guestbook").prepend(html);
			}else{
				$('#list-guestbook').append(html);
			}
			// --> 연관배열로 사용가능
			// $("#list-guestbook")[mode ? "prepend":"append"](html);
		}
		
		var fetchList = function(){
			if (isEnd == true) {
				return;
			}
			
			console.log($("#list-guestbook li").last().data("no"));
			
			var startNo = $("#list-guestbook li").last().data("no") || 0;
			
			
			console.log('startNo ==> ' + startNo);
			/**************************************************************/
			/*********            list ajax            *********************/
			/**************************************************************/
			
			$.ajax({
				url : "/mysite3/api/guestbook/list?no=" + startNo,
				type : "get",
				//async:true : 비동기 상태
				// async:false : success 동기화상태 success가 다돌고 밖으로 나감.
				dataType : "json",
				success : function(response) {
					console.log(response);
					if (response.result != "success") {
						console.log(response.message);
						return;
					}
					
					// 끝 감지
					if (response.data.length < 5) {
						isEnd = true;
						$("#btn-fetch").prop("disabled", true);
						//$("#btn-fetch").attr("disabled", "disabled");
					}

					//render
					$.each(response.data, function(index, vo) {
						console.log(response.data);
						render(false, vo);
					});
					/* $.each(reponse.data, function(index, vo){
						var html = 
							"<li data-no='" + vo.no + "'>"+
							"<strong>" + vo.name + "</strong>"+
							"<p>" + vo.content.replace(/\n/gi, "<br>") + "</p>"+
							"<strong></strong>"+
							"<a href='' data-no='" + vo.no  + "' class='del-ajax'>삭제</a> "+
							"</li>";
							
						$('#list-guestbook').append(html); 
					});*/

					/* var length = response.data.length;
					if (length > 0) {
						startNo = response.data[length - 1].no;
					} */

				},
				error:function(xhr){
					console.log('failed');
				}
			});
		}
		
		$(function(){
			// 삭제 시 비밀번호 입력 모달 다이얼로그
			var deleteDialog = $( "#dialog-delete-form" ).dialog({
				autoOpen:false,
				modal:true,
				buttons : {
					"삭제" : function() {
						var password = $('#password-delete').val();
						var no = $('#hidden-no').val();
						console.log(password + ' : ' + no);
						
						// 삭제 ajax
						$.ajax({
							url: "/mysite3/api/guestbook/delete",
							type: "post",
							dataType:"json",
							data:"no="+no + "&password="+password,
							success: function(response){
								
								if(response.result == "fail"){
									console.log(response.message);
									return ;
								}
								
								if(response.result == -1){
									$('.validateTips.normal').hide();
									$('.validateTips.error').show();
									$('#password-delete').val('');
									return ;
								}
								
								$('#list-guestbook li[data-no='+response.data+']').remove();
								deleteDialog.dialog("close");
								
								console.log(response);
							}
						});
					},
					"취소" : function() {
						deleteDialog.dialog("close");
					}
				},
				close:function(){
					$('#password-delete').val('');
					$('#hidden-no').val('');
					$('.validateTips.normal').show();
					$('.validateTips.error').hide();
					console.log('erer');
				}
			});

			// Live Event Listener  태그 안에 복잡하게 속성을 추가하는 것보다 훨 간편해보임
			$(document).on("click", "#list-guestbook li a", function(event) {

				event.preventDefault();
				console.log('this == > ' + $(this).data('no'));
				var no = $(this).data("no");
				$('#hidden-no').val(no);
				deleteDialog.dialog("open");
				var no = $(this).data("no");
				console.log(this);
				console.log(no);
			});

			// form으로 submit을 먹임
			// 풀어쓰면 add-form안에서 submit이 일어날 경우는 이쪽으로 빠지게 됨.
			$("#add-form").submit(function() {
				event.preventDefault();

				// WAY 2. 
				var queryString = $(this).serialize();
				console.log(queryString);
				// WAY 1. "name="+$("#input-name").val() + "&password=" ...;

				var data = {};
				$.each($(this).serializeArray(), function(index, o) {
					data[o.name] = o.value;
					console.log(o);
				});

				if (data["name"] == '') {
					messageBox("메세지 등록", "이름이 비어있습니다.");

					$("#input-name").focus();
					return;
				}

				if (data["password"] == '') {
					messageBox("메세지 등록", "비밀번호가 비어있습니다.", function() {
						$("#input-password").focus(); // 이렇게 포커스를 줌으로 모달창이 꺼졌을 때 포커스가 패스워드로 가게된다.
					});

					return;
				}

				if (data["content"] == '') {
					messageBox("메세지 등록", "내용이 비어있습니다.", function() {
						$("#tx-content").focus();
					});
					return;
				}

				console.log(data);

				console.log($(this).serializeArray());
				
				/**************************************************************/
				/*********            삽입 ajax            *********************/
				/**************************************************************/
				$.ajax({
					url : "/mysite3/api/guestbook/insert",
					type : "post",
					dataType : "json",
					contentType : "application/json",
					data : JSON.stringify(data),
					success : function(response) {
						console.log(response);
						render(true, response.data);
						$("#add-form").get(0).reset();
					}
				});
			});

			$("#btn-fetch").click(function() {
				fetchList();
			});
			
			/**************************************************************/
			/*********            스크롤 처리            *********************/
			/**************************************************************/
			
			$(window).scroll(function(){
				var $window = $(this);
				var scrollTop = $window.scrollTop();
				var windowHeight = $window.height();
				var documentHeight = $(document).height();
				
				console.log(scrollTop + ":" + windowHeight + ":" + documentHeight);
				
				// 100을 더 해준 이유는 완전 바닥까지 갔을 때 데이터를 불러오는게 아니라 어느정도 내려가면 데이터를 불러오기 위함.
				// scrollbar의 thumb(스크롤 안에 네모 모양의 버튼?)가 바닥 전 100px까지 도달
				if(scrollTop + windowHeight + 100 > documentHeight) {
					fetchList();
				}
			});
			
			// 최초 리스트 가져오기
			fetchList();
			
			// plugin test
			$('#container').hello();
		});
	</script>
	
	<div id="container">
		<div id="content">
			<div id="guestbook">
				<h1>
					 <a href="${pageContext.request.contextPath }/gb/ajax">
					    <img src="${pageContext.request.contextPath }/assets/images/guestbook.png" alt="logo" width="30px" height="30px"/> 방명록
					  </a>
				</h1>
				<form id="add-form" action="" method="post">
					<input type="text" name="name" id="input-name" placeholder="이름">
					<input type="password" name="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" name="content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" id="input-submit" />
				</form>
				<br>
				<hr>
				
				<ul id="list-guestbook">
					<!--  <li data-no='1'>
						<strong>지나가다가</strong>
						<p>
							별루입니다.<br>
							비번:1234 -,.-
						</p>
						<strong></strong>
						<a href='' data-no='1' class="del-ajax">삭제</a> 
					</li> -->
					
					<!--<li data-no=''>
						<strong>둘리</strong>
						<p>
							안녕하세요<br>
							홈페이지가 개 굿 입니다.
						</p>
						<strong></strong>
						<a href='' data-no='' class="del-ajax">삭제</a> 
					</li>

					<li data-no=''>
						<strong>주인</strong>
						<p>
							아작스 방명록 입니다.<br>
							테스트~
						</p>
						<strong></strong>
						<a href='' data-no='' class="del-ajax">삭제</a> 
					</li> -->
				</ul>
			</div>
			
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="테스트" style="display:none">
  				<p>테스트 입니다.</p>
			</div>
			<input type="button" id="btn-fetch" value="가져오기">		
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>