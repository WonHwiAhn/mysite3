package com.cafe24.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVO;

@Controller
@RequestMapping("/gb")
public class GuestbookController {
	
	@Autowired
	GuestbookService guestbookService;
	
	@RequestMapping(path = {"list", ""})
	public String index(Model model) {
		List<GuestbookVO> list = guestbookService.getList();
		model.addAttribute("list", list);
		
		return "guestbook/list";
	}
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	public String add(@ModelAttribute GuestbookVO vo, Model model) {
		guestbookService.insert(vo);
		
		return "redirect:/gb/list";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete() {
		return "guestbook/deleteform";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public String delete(@RequestParam String password, @RequestParam String no) {
		if(guestbookService.check(no, password)) {
			guestbookService.delete(no);
			return "redirect:/gb/list";
		}
		
		return "/guestbook/failed";
	}
}
