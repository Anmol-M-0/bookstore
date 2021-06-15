package com.app.utils;

@SuppressWarnings("serial")
public class InvalidBookIdException extends Exception {
	public InvalidBookIdException(String message) {
		super(message);
	}
}
