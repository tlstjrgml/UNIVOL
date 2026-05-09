package com.univol.common.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice // 전체 컨틀로러에 유효할 수 있도록 보조
public class GlobalExceptionHandler {
		@ExceptionHandler(Exception.class) // 특정 예외가 발생했을 때 처리할 메소드 지정
		public String handlerAllException(Exception e, Model model) {
			model.addAttribute("message", e.getMessage());
			return "error/500";
		}
	
}
