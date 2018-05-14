package com.cafe24.mysite.controller;


import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVO;
import com.cafe24.mysite.vo.UserVO;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	// 키워드 없이 전체 리스트 검색
	@RequestMapping(path= {"list", ""})
	public String getList(@RequestParam int page,
						  @RequestParam(value="kwd", required=false, defaultValue="MONIBUYBGTF") String keyword, 
						  Model model){
		
		if(!"MONIBUYBGTF".equals(keyword)) {
			model.addAttribute("list", boardService.getList(keyword, page));
			model.addAttribute("kwd", keyword);
		}else {
			model.addAttribute("list", boardService.getList(page));
		}
		
		model.addAttribute("pageMap", boardService.calcPage(page, boardService.totalCount()));
		
		return "board/list2";
	}
	
	// 키워드 있을 때 전체 리스트 검색
	/*@RequestMapping("search")
	public String getList(@RequestParam int page,
						  @RequestParam("kwd") String keyword,
						  Model model) {
		
		model.addAttribute("kwd", keyword);
		model.addAttribute("list", boardService.getList(keyword, page));
		model.addAttribute("pageMap", boardService.calcPage(page, boardService.totalCount()));
		
		return "board/list";
	}*/
	
	// 게시글 작성 페이지로 이동
	@RequestMapping(value="write", method=RequestMethod.GET)
	public String write(@RequestParam int page, Model model) {
		model.addAttribute("page", page);
		return "/board/write";
	}
	
	// 게시글 작성
	@RequestMapping(value="write", method=RequestMethod.POST)
	public String write(@RequestParam int page,
						@ModelAttribute BoardVO vo,
						HttpSession session,
						Model model) {
		
		UserVO authUser = (UserVO)session.getAttribute("authUser");
		
		if(authUser != null) {
			HashMap<Long, String> writer = new HashMap<Long, String>();
			writer.put(authUser.getNo(), "");
			
			vo.setWriter(writer);
			boardService.insert(vo);
		}
		
		return "redirect:/board/list?page="+page;
	}
	
	// 게시글 삭제 (내꺼만 가능)
	@RequestMapping("delete")
	public String delete(@RequestParam Long no,
						 @RequestParam int page) {
		boardService.delete(no);
		
		return "redirect:/board/list?page="+page;
	}
	
	// 게시글 보기 (로그인 안해도 가능하고 다른 사람것도 볼 수 있음), 검색어가 있는 경우
	@RequestMapping(value="view", method=RequestMethod.GET)
	public String view(
					   @RequestParam int page,
					   @RequestParam(value="kwd", required=false, defaultValue="") String keyword,
					   @ModelAttribute BoardVO vo,
					   Model model) {
		
		System.out.println("키워드가 있을 때 view페이지 ==> " + keyword);
		
		BoardVO boardInfo = boardService.get(vo.getNo(), true);
		
		model.addAttribute("no", vo.getNo());
		model.addAttribute("page", page);
		model.addAttribute("kwd", keyword);
		model.addAttribute("vo", boardInfo);
		
		return "/board/view";
	}
	
	// 게시글 보기 (로그인 안해도 가능하고 다른 사람것도 볼 수 있음), 검색어 없을 경우
	/*@RequestMapping(value="view", method=RequestMethod.GET)
	public String view(
					   @RequestParam int page,
					   @ModelAttribute BoardVO vo,
					   Model model) {
		
		System.out.println("키워드가 없을 때 view페이지 ==> ");
		
		BoardVO boardInfo = boardService.get(vo.getNo(), true);
		
		model.addAttribute("no", vo.getNo());
		model.addAttribute("page", page);
		model.addAttribute("vo", boardInfo);
		
		return "/board/view";
	}*/
	
	// 게시글 수정 (내가 올린 글만)
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public String modify(@RequestParam Long no,
					     @RequestParam int page,
					     @RequestParam(value="kwd", required=false, defaultValue="") String keyword,
					     @ModelAttribute BoardVO vo,
					     HttpSession session,
					     Model model) {
		
		UserVO authUser = (UserVO)session.getAttribute("authUser");
		
		if(authUser != null) {
			BoardVO boardInfo = boardService.get(no, false);
			
			model.addAttribute("no", no);
			model.addAttribute("page", page);
			model.addAttribute("kwd", keyword);
			model.addAttribute("vo", boardInfo);
			
			return "/board/modify";
		}
		
		return "/board/list?page="+1;
	}
	
	// 게시글 수정 (내가 올린 글만)
	/*@RequestMapping(value="modify", method=RequestMethod.GET)
	public String modify(@RequestParam Long no,
					     @RequestParam int page,
					     @RequestParam("kwd") String keyword,
					     @ModelAttribute BoardVO vo,
					     HttpSession session,
					     Model model) {
		
		System.out.println("keyword == > " + keyword);
		
		UserVO authUser = (UserVO)session.getAttribute("authUser");
		
		if(authUser != null) {
			
			BoardVO boardInfo = boardService.get(no, false);
			
			model.addAttribute("kwd", keyword);
			model.addAttribute("no", no);
			model.addAttribute("page", page);
			model.addAttribute("vo", boardInfo);
			
			return "/board/modify";
		}
		
		return "/board/list?page="+1;
	}*/
	
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(@RequestParam Long no,
						 @RequestParam int page,
						 @RequestParam(value="kwd", required=false, defaultValue="") String keyword,
						 @ModelAttribute BoardVO vo) {
		
		boardService.update(vo);
		
		return "redirect:/board/search?page="+page+"&kwd="+keyword;
	}
	
	
}
