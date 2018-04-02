package com.cafe24.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor {

	// Handler에 보내기 전에 실행되는 메소드
	// Object메소드는 Handler를 의미 (여러 타입이기 때문에 Object)
	@Override
	public boolean preHandle(HttpServletRequest request, 
							 HttpServletResponse reponse, 
							 Object arg2) throws Exception {
		System.out.println("MyInterceptor:preHandle");
		
		// false를 하면 다음 상황이 진행이 안됨.
		return true;
	}
	

	// 핸들러 끝나고 호출
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("MyInterceptor:postHandle");
	}

	// view까지 다 끝난 후 호출
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("MyInterceptor:afterHandle");
	}

}
