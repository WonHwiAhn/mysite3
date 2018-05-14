package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.GuestbookDAO;
import com.cafe24.mysite.vo.GuestbookVO;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookDAO guestbookDAO; 
	
	// 전체 리스트 구하기
	public List<GuestbookVO> getList() {
		return guestbookDAO.getList();
	}
	
	// 번호에 따라 리스트 구하기 (ajax땜시 만듬 ㅠ.ㅠ)
	public List<GuestbookVO> getList(Long no) {
		return guestbookDAO.getList(no);
	}
	
	
	public boolean insert(GuestbookVO vo) {
		return guestbookDAO.insert(vo);
	}
	
	public boolean delete(String no) {
		return guestbookDAO.delete(no);
	}
	
	public boolean check(String no, String password) {
		return guestbookDAO.check(no, password);
	}
	
	public GuestbookVO insertMessage(GuestbookVO guestbookVO) {
		GuestbookVO vo = null;
		boolean count = guestbookDAO.insert(guestbookVO);
		
		if(count) {
			vo = guestbookDAO.get(guestbookVO.getNo());
		}
		
		return vo;
	}
	
	public boolean deleteMessage(GuestbookVO vo) {
		return guestbookDAO.delete(vo);
	}
}
