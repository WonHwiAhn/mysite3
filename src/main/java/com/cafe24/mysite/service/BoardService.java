package com.cafe24.mysite.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.BoardDAO;
import com.cafe24.mysite.vo.BoardVO;

@Service
public class BoardService {
	
	@Autowired
	private BoardDAO boardDAO;
	private int page;
	
	//====== 전체 리스트 관련 쿼리 ======== 
	// 전체리스트 (검색어x)
	public List<BoardVO> getList(int page){
		return boardDAO.getList(page);
	}
	
	// 키워드 있는 경우 전체 리스트
	public List<BoardVO> getList(String keyword, int page){
		return boardDAO.getList(keyword, page);
	}
	
	//====== 게시글 정보 관련 쿼리 ======== 
	// 1개의 게시물에 대한 정보
	public BoardVO get(Long no, boolean hit) {
		return boardDAO.get(no, hit);
	}
	
	// 게시글 작성
	public boolean insert(BoardVO vo) {
		return boardDAO.insert(vo);
	}
	
	// 게시글 삭제
	public boolean delete(Long no) {
		return boardDAO.delete(no);
	}
	
	// 게시글 수정
	public boolean update(BoardVO vo) {
		return boardDAO.update(vo);
	}
	
	//====== 페이징 관련 쿼리 ======== 
	// 전체 게시물의 총 수 구하는 메소드 (검색어 없을 경우에)
	public int totalCount() {
		return boardDAO.getPage();
	}
	
	// 전체 게시물의 총 수 구하는 메소드 (검색어 있을 경우에)
	public int totalCount(String keyword) {
		return boardDAO.getPage(keyword);
	}
	
	public HashMap<String, Integer> calcPage(int page, int totalCount) {
		int divPage = (int)(page / 5.1);
		int realPage = (int)(5*divPage+1);
		int endPage = (int)(totalCount/10+0.9);
		int startPage = page-1;
		
		HashMap<String, Integer> map =
							new HashMap<String, Integer>();
		
		map.put("page", page);
		map.put("realPage", realPage);
		map.put("endPage", endPage);
		map.put("startPage", startPage);
		
		return map;
	}
}
