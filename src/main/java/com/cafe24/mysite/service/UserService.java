package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.UserDAO;
import com.cafe24.mysite.vo.UserVO;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;
	
	public void join(UserVO vo) {
		userDAO.insert(vo);
	}
	
	public UserVO getUser(UserVO vo) {
		if(vo.getPassword() == "" || vo.getPassword() == null)
			return userDAO.get(vo.getNo());
		else {
			//return userDAO.get(vo.getEmail(), vo.getPassword());
			return userDAO.get(vo);
		}
	}
	
	public boolean update(UserVO vo) {
		return userDAO.update(vo);
	}
}
