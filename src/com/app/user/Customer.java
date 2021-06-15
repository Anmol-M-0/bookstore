package com.app.user;

import java.util.ArrayList;

import com.app.shop.Book;

public class Customer extends LoginUser {
	private ArrayList<Book> purchasedBooks = new ArrayList<>();

	public Customer(String email, String password) {
		super(email, password);
	}

	public void purchaseBook(ArrayList<Book> books) {
		purchasedBooks.addAll(books);
	}

	public ArrayList<Book> getPurchasedBooks() {
		return purchasedBooks;
	}

}
