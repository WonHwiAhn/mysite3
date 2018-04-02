package com.cafe24.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.security.Auth;
import com.cafe24.mysite.security.AuthUser;
import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVO;

@Controller

@RequestMapping("/user")
public class UserController {
	/*
	 * 
	 * interceptor를 사용해서 login과 logout을 처리했기 때문에
	 * 굳이 Mapping "/login", "/logout" 부분이 필요하지 않다.
	 * 어짜피 현재는 안씀.
	 * 
	 */
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVO vo) {
		return "user/join";
	}
	
	// BindingResult 안에 Valid 결과가 담겨지게 된다.
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVO vo, BindingResult result) {
		if(result.hasErrors()) {
			//ppt에 Model에 담아서 처리했는데 담지않고 처리가능 이게 정
			/* == > 이렇게 해도 내용이 나오긴 나옴. 하지만 파싱하기에는 좀 복잡함.
			 * List<ObjectError> list = result.getAllErrors();
			
			for(ObjectError error : list) {
				System.out.println("Object Error: " + error);
			}*/
			
			return "user/join";
		}
		//userDAO.insert(vo);
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session, @ModelAttribute UserVO vo, Model model) {
		UserVO authUser = userService.getUser(vo);//userDAO.get(vo.getEmail(), vo.getPassword());
		
		if(authUser == null) {
			model.addAttribute("email", vo.getEmail());
			model.addAttribute("result", "fail");
			return "/user/login";
		}
				
		// 인증처리
		session.setAttribute("authUser", authUser);
		return "redirect:/main";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/main";
	}
	
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(@AuthUser UserVO authUser,
						 Model model, HttpSession session) {
		
		//System.out.println("usercont modify AuthUser ==> " + session.getAttribute("authUser"));
		System.out.println("usercont modify AuthUser ==> " + authUser);
		
		UserVO vo = userService.getUser(authUser.getNo());
		model.addAttribute("vo", vo);
		
		/*접근제어
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/main";
		}*/
		
		return "user/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(HttpSession session, @ModelAttribute UserVO vo, Model model) {
		
		boolean success = userService.update(vo);
		
		System.out.println("sucess ==> " + success);
		
		if(success) {
			session.setAttribute("authUser", userService.getUser(vo));
		}
		/*접근제어
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		
		// 로그인이 안되어있다면 login페이지
		if(authUser == null) {
			model.addAttribute("result", "fail");
			return "/user/login";
		}*/
		
		return "redirect:/user/modifysuccess";
	}
	
	@RequestMapping(value="/modifysuccess", method=RequestMethod.GET)
	public String modifysuccess(HttpSession session) {
		return "/user/modifysuccess";
	}
	
	/*@ExceptionHandler( UserDAOException.class )
	public String handleUserDaoException() {
		
		 * 로그 남기기
		 
		return "error/exception";
	}*/

}
