package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVO;
import com.cafe24.mysite.vo.UserVO;

@Repository
public class BoardDAO {	
	// 게시판 삭제 쿼리
	public boolean delete(Long no) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board set title='삭제된 글입니다.', delete_bool=1 where no=?";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			int count = pstmt.executeUpdate();
		
			result = (count==1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 게시판 답글 쿼리
	public boolean resInsert(BoardVO vo, Long no) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		
		String userNo = vo.getWriter().keySet().toString().replace("[", "");
		userNo = userNo.replace("]", "");
		
		try {
			BoardVO originVO = null;
			conn = getConnection();
			
			// 먼저 기존의 번호로 정보를 가져옴
			String getOriginInfoSql = "SELECT A.no as no, title, B.no, B.name as writer, " +
									  "content, hit, reg_date, " + 
							          "group_no, order_no, depth " + 
							          "FROM board A, users B " + 
							          "WHERE B.no = A.writer " + 
							          "AND A.no = ?";
			
			pstmt = conn.prepareStatement(getOriginInfoSql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				originVO = new BoardVO();
				originVO.setNo(rs.getLong(1));
				originVO.setGroupNo(rs.getInt(8));
				originVO.setOrderNo(rs.getInt(9));
				originVO.setDepth(rs.getInt(10));
			}
			
			// 기존의 group_no와 order_no의 값 조정이 필요.
			String updateSql = "UPDATE board " + 
							   "SET order_no = order_no + 1 " + 
							   "WHERE group_no = ? AND order_no > ?";
			pstmt1 = conn.prepareStatement(updateSql);
			
			pstmt1.setInt(1, originVO.getGroupNo());
			pstmt1.setInt(2, originVO.getOrderNo());
			
			int count = pstmt1.executeUpdate();
			
			result = (count==1);
			
			// 모든 쿼리가 완료된 후 insert
			//if(result) {
				String insertSql = "insert into board " + 
							       "(no, title, writer, content, " + 
							       "hit, reg_date, group_no, order_no, depth) " + 
							       "values(null, ?, ?, ?, 0, now(), " + 
							       "?, ?, ?)";
				
				pstmt2 = conn.prepareStatement(insertSql);
				
				pstmt2.setString(1, vo.getTitle());
				pstmt2.setString(2, userNo);
				pstmt2.setString(3, vo.getContent());
				pstmt2.setInt(4, originVO.getGroupNo());
				pstmt2.setInt(5, originVO.getOrderNo()+1);
				pstmt2.setInt(6, originVO.getDepth()+1);
				
				int count1 = pstmt2.executeUpdate();
				
				result = (count1==1);
			//}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
		
	// 게시판 update 쿼리
	public boolean update(BoardVO vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board " + 
						 "set title=?, content=? " + 
						 "where no=?";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
			
			int count = pstmt.executeUpdate();
		
			result = (count==1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 게시판 insert 쿼리
	public boolean insert(BoardVO vo) {
		boolean result = false;
		String userNo = vo.getWriter().keySet().toString().replace("[", "");
		userNo = userNo.replace("]", "");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "insert into board " + 
						 "(no, title, writer, content, " + 
						 "hit, reg_date, group_no, order_no, depth) " + 
						 "values(null, ?, ?, ?, 0, now(), " + 
						 "IFNULL((select max(group_no+1) as group_no from board A), 1), 1, 0)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, userNo);
			pstmt.setString(3, vo.getContent());
			
			System.out.println(vo.getTitle());
			System.out.println(vo.getWriter());
			
			int count = pstmt.executeUpdate();
			if((count==1)) {
				System.out.println("Board Inser 성공");
			}else {
				System.out.println("Board Inser 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	// 1개 게시판의 정보 가져오기
	public BoardVO get(Long no, boolean hit) {
		BoardVO result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		HashMap<Long, String> map = new HashMap<Long, String>();
		
		try {
			conn = getConnection();
			
			if(hit) {
				String uSql = "update board set hit = hit + 1 where no = ?";
				
				pstmt = conn.prepareStatement(uSql);
				
				pstmt.setLong(1, no);
				
				pstmt.executeUpdate();
			}
			
			
			String sql = "SELECT A.no as no, title, B.no as user_no, B.name as name, " +
					     "content, hit, reg_date,  group_no, order_no, depth " + 
						 "FROM board A, users B "+
						 "WHERE A.writer = B.no " + 
						 "  AND A.no=?";
			pstmt1 = conn.prepareStatement(sql);
			
			pstmt1.setLong(1, no);
			
			rs = pstmt1.executeQuery();
			
			if(rs.next()) {
				result = new BoardVO();
				result.setNo(rs.getLong(1));
				result.setTitle(rs.getString(2));
				map.put(rs.getLong(3), rs.getString(4));
				result.setWriter(map);
				result.setContent(rs.getString(5));
				result.setHit(rs.getInt(6));
				result.setRegDate(rs.getString(7));
				result.setGroupNo(rs.getInt(8));
				result.setOrderNo(rs.getInt(9));
				result.setDepth(rs.getInt(10));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt1.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	// 게시판 리스트 가져오기 [키워드로 찾기]
		public List<BoardVO> getList(String keyword, int page){
			List<BoardVO> list = new ArrayList<BoardVO>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			HashMap<Long, String> map;
			
			try {
				conn = getConnection();
				
				String sql = "SELECT @rownum:=@rownum+1 AS rownum, B.* " + 
							 "FROM (SELECT @rownum:=0) A, (SELECT A.no as no, title, B.no as user_no, B.name as writer,content, hit, reg_date, " + 
							 "group_no, order_no, depth, delete_bool " + 
							 "FROM board A, users B " + 
							 "WHERE B.no = A.writer" + 
							 "  AND A.title LIKE '%"+ keyword +"%' " +
							 "ORDER BY group_no, order_no desc) B " + 
							 "ORDER BY rownum desc limit ?, 10";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, (page-1) * 10);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					BoardVO vo = new BoardVO();
					map = new HashMap<Long, String>();
					vo.setRownum(rs.getInt(1));
					vo.setNo(rs.getLong(2));
					vo.setTitle(rs.getString(3));
					map.put(rs.getLong(4), rs.getString(5));
					vo.setWriter(map);
					vo.setHit(rs.getInt(7));
					vo.setRegDate(rs.getString(8));
					vo.setGroupNo(rs.getInt(9));
					vo.setOrderNo(rs.getInt(10));
					vo.setDepth(rs.getInt(11));
					vo.setDeleteBool(rs.getInt(12));
					
					list.add(vo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				}catch(SQLException e) {
					
				}
			}
			return list;
		}
	
	// 게시판 리스트 가져오기
	public List<BoardVO> getList(int page){
		List<BoardVO> list = new ArrayList<BoardVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HashMap<Long, String> map;
		
		try {	
			conn = getConnection();
			
			String sql = "SELECT @rownum:=@rownum+1 AS rownum, B.* " + 
						 "FROM (SELECT @rownum:=0) A, (SELECT A.no as no, title, B.no as bno, B.name as writer,content, hit, reg_date, " + 
						 "	   group_no, order_no, depth, delete_bool " + 
						 "FROM board A, users B " + 
						 "WHERE B.no = A.writer " + 
						 "ORDER BY group_no, order_no desc) B " + 
						 "ORDER BY rownum desc limit ?, 10";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (page-1) * 10);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO vo = new BoardVO();
				map = new HashMap<Long, String>();
				
				vo.setRownum(rs.getInt(1));
				vo.setNo(rs.getLong(2));
				vo.setTitle(rs.getString(3));
				map.put(rs.getLong(4), rs.getString(5));
				vo.setWriter(map);
				vo.setHit(rs.getInt(7));
				vo.setRegDate(rs.getString(8));
				vo.setGroupNo(rs.getInt(9));
				vo.setOrderNo(rs.getInt(10));
				vo.setDepth(rs.getInt(11));
				vo.setDeleteBool(rs.getInt(12));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				
			}
		}
		return list;
	}
	
	// 페이징 쿼리
	// 게시판 리스트 가져오기
	public int getPage(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalWriteCount = 0;
		
		try {			
			conn = getConnection();
			
			String totalSql = "SELECT count(*) as total FROM board";
			
			pstmt = conn.prepareStatement(totalSql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				totalWriteCount = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				
			}
		}
		return totalWriteCount;
	}
	
	//키워드가 있을 때 총 개수
	public int getPage(String keyword){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalWriteCount = 0;
		
		try {			
			conn = getConnection();
			
			String totalSql = "SELECT count(*) as total FROM board where title like '%"+keyword+"%'";
			
			pstmt = conn.prepareStatement(totalSql);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				totalWriteCount = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				
			}
		}
		return totalWriteCount;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
		
		return conn;
	}
}
