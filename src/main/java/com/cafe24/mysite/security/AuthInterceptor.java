package com.cafe24.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.vo.UserVO;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, 
							 HttpServletResponse response, 
							 Object handler)
			throws Exception {
		
		
		// 1. handler 종류 확인
		// false면 다른 Handler가 있다는 뜻. 그러므로 그 쪽으로 흐름을 준다.
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		
		// 2. casting
		System.out.println("AuthInterceptor Handler ==> " + handler);
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		// 3. @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		
		// 4. Method에 @Auth가 없는 경우
		if(auth == null) {
			return true;
		}
		
		// 5. @Auth가 붙어 있는 경우, 인증여부 체크
		HttpSession session = request.getSession();
		
		if(session == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 6. 접근허가
		return true;
		
		
		/*return super.preHandle(request, response, handler);*/
	}
	
}
