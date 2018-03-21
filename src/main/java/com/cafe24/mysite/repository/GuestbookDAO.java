package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestbookVO;

@Repository
public class GuestbookDAO {
	public List<GuestbookVO> getList(){
		List<GuestbookVO> list = new ArrayList<GuestbookVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "SELECT no, name, password, " + 
						 "content, reg_date " + 
						 "FROM guestbook " +
						 "ORDER BY no desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GuestbookVO vo = new GuestbookVO();
				
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setPassword(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setRegDate(rs.getString(5));
				
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
	
	public boolean insert(GuestbookVO vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "insert into guestbook " + 
					"values(null, ?, ?, ?, " + 
					"date_format(now(), '%Y-%m-%d %H:%i:%s'))";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());
			int count = pstmt.executeUpdate();
			
			result = (count == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				
			}
		}
		
		return result;
	}
	
	/////// 비밀번호 체크
	public boolean check(String no, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "SELECT password FROM guestbook WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();
			
			String conPass = "";
			
			while(rs.next()) {
				conPass = rs.getString(1);
			}
			System.out.println("conPass == >" + conPass);
			System.out.println("bool == >" + (conPass.equals(password)));
			
			return (conPass.equals(password));
			
			//result = (count == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				
			}
		}
		
		return false;
	}
	
	/////// 삭제
	public boolean delete(String no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "DELETE FROM guestbook WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, no);
			int count = pstmt.executeUpdate();
			
			return (count == 1);
			
			//result = (count == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				
			}
		}
		
		return false;
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
