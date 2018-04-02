package com.cafe24.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVO;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

	// @Autowired
	// private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) throws Exception {
		
		System.out.println("######## Interceptor Login입니다 #########");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		ApplicationContext ac = 
				WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		
		UserService userService = ac.getBean(UserService.class);
		UserVO vo = new UserVO();
		
		vo.setEmail(email);
		vo.setPassword(password);
		
		UserVO authUser = userService.getUser(vo);
		
		// 만들어진 서비스에 접근하는 것이 아니라 직접 접근해야할 때 사용 (Autowired를 사용하지 않음)
		/*ApplicationContext ac = 
		WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());*/
		
		// 주입받지않고 RootApplication에 생성되지 않았을 경우에 직접 접근해서 사용
		//userService = ac.getBean(UserService.class);
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath());
		
		return false;
		//return super.preHandle(request, response, handler);
	}
	
}
