package com.cafe24.mysite.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Auth {
	
	// @Auth(value=)
	// public String value() default "user";
	
	// @Auth(test=)
	// public int test() default 1;
	
	public enum Role {ADMIN, USER}
	
	// @Auth(Role.ADMIN) or @Auth(Role.USER)
	public Role role() default Role.USER;
	

}
