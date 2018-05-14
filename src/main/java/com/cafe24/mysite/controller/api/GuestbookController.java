package com.cafe24.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.repository.GuestbookDAO;
import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVO;

@Controller("guestbookAPIController")
@RequestMapping("/api/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@ResponseBody
	@RequestMapping("/list")
	public JSONResult list(@RequestParam(value="", required=true, defaultValue="") Long no) {
		System.out.println("list no ==> " + no);
		//List<GuestbookVO> list = guestbookDao.getList();
		List<GuestbookVO> list = guestbookService.getList(no);
		System.out.println("list guestbooklist ==> " + list);
		//return list;
		return JSONResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public JSONResult insert(@RequestBody GuestbookVO vo) {
		System.out.println("model vo ==> " +  vo);
		GuestbookVO guestbookVO = guestbookService.insertMessage(vo);
		System.out.println("model guestbookVO ==> " +  guestbookVO);
		return JSONResult.success(guestbookVO);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public JSONResult list(@ModelAttribute GuestbookVO vo) {
		System.out.println("delete guestbookvo ==> " + vo);
		boolean result = guestbookService.deleteMessage(vo); // 현재 service dao에 no값 받아서 처리하는 것 밖에 없음
		return JSONResult.success(result ? vo.getNo() : -1);
		//return JSONResult.success(list);
	}
}
