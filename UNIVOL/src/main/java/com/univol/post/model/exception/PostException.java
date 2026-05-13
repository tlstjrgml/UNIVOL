package com.univol.post.model.exception;

public class PostException extends RuntimeException{
	public PostException() {}
	
	public PostException(String msg) {
		super(msg);
	}
}
