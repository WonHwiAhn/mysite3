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
	
	
	public List<GuestbookVO> getList() {
		return guestbookDAO.getList();
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
}
