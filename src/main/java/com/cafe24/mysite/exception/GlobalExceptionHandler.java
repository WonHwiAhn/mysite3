package com.cafe24.mysite.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public void handlerException(
								 HttpServletRequest request,
								 HttpServletResponse response,
								 Exception e
								 ) throws ServletException, IOException {
		// 1. 로깅
		StringWriter errors = new StringWriter();
		
		// 예가 toString으로 빠져버려서 정확한 에러가 안나오던거임.
		e.printStackTrace(new PrintWriter(errors));
		
		request.setAttribute("errors", errors.toString());
		
		// 한번 더 찍어주면 상세한 에러를 볼 수 있음
		e.printStackTrace();
		
		// 2. 사과
		request.
		getRequestDispatcher("/WEB-INF/views/error/exception.jsp").
		forward(request, response);
	}
}
