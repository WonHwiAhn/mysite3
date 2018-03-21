package com.cafe24.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.exception.UserDAOException;
import com.cafe24.mysite.repository.UserDAO;
import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVO;

@Controller

@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute UserVO vo) {
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
	
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(HttpSession session) {
		/*접근제어*/
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/main";
		}
		
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
