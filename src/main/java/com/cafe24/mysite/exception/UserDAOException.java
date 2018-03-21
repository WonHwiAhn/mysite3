package com.cafe24.mysite.exception;

public class UserDAOException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserDAOException() {
		super("UserDAOException Occurs");
	}
	
	public UserDAOException(String message) {
		super(message);
	}
}
