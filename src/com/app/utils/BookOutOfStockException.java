package com.app.utils;

@SuppressWarnings("serial")
public class BookOutOfStockException extends Exception {
	public BookOutOfStockException(String message) {
		super(message);
	}

}
