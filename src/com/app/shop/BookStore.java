package com.app.shop;

import java.util.ArrayList;

import com.app.user.LoginUser;

/*
 * a book store has books and loginusers
 */
public class BookStore {
	ArrayList<Book> bookStash;
	ArrayList<LoginUser> loginUsers;

	public BookStore(ArrayList<Book> books, ArrayList<LoginUser> loginUsers) {
		bookStash = books;
		this.loginUsers = loginUsers;
	}

	public ArrayList<LoginUser> getUsers() {
		return loginUsers;
	}

	public ArrayList<Book> getBookStash() {
		return bookStash;
	}

}
