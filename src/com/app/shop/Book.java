package com.app.shop;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import com.app.utils.BookOutOfStockException;

/*
 * a book has 
 * a unique id, quantity, rating, title, category, price, publish date, authors
 * 
 */
public class Book {
	static {
		bookIdGenerator = 100;
		DEFAULT_RATING = 3;
	}
	private static int bookIdGenerator;
	private static final int DEFAULT_RATING;
	private int bookId;
	private int quantity;
	private int rating;
	private String title;
	private BookCategory bookCategory;
	private double price;
	private LocalDate publishDate;
	private Author[] authors;

	public Book(int quantity, int rating, String title, BookCategory bookCategory, double price, LocalDate publishDate,
			Author... authors) {
		super();
		this.quantity = quantity;
		this.rating = ((rating > 1 && rating <= 5) ? rating : DEFAULT_RATING);// rating restricted to between 1 to 5,
																				// (else default rating)
		this.title = title;
		this.bookCategory = bookCategory;
		this.authors = authors;
		this.price = price;
		this.publishDate = publishDate;
		bookId = ++bookIdGenerator;
	}

	/*
	 * the sole purpose of this constructor is to aid the add to cart implementation
	 */
	private Book(Book book) {
		this.quantity = 1;
		this.rating = book.rating;
		this.title = book.title;
		this.bookCategory = book.bookCategory;
		this.authors = book.authors;
		this.price = book.price;
		this.publishDate = book.publishDate;
		this.bookId = book.bookId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return bookId == other.bookId;
	}

	/*
	 * full book description
	 */
	@Override
	public String toString() {
		return "Book ID : " + bookId + ", \nBook Title : " + title + ", \nAuthors : " + Arrays.deepToString(authors)
				+ ", \nBook Category : " + bookCategory + ", Book rating : " + rating + ", \nQuantity : " + quantity
				+ ", Price : " + price + ", \nPublish Date : " + publishDate;
	}

	/*
	 * getBookId(), getPrice(), getTitle(), getAuthorNames() are used for custom
	 * book description
	 */
	public int getBookId() {
		return bookId;
	}

	public double getPrice() {
		return price;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthorNames() {
		return Arrays.deepToString(authors);
	}

	/*
	 * the sole purpose of the methods - addToCart() and removeFromCart() - is to
	 * aid the add to cart implementation
	 */
	public Book addToCart(Book book) throws BookOutOfStockException {
		if (quantity == 0)
			throw new BookOutOfStockException("Sorry user, this book is out of stock.");
		quantity--;
		return new Book(book);
	}

	public void returnBookToStore() {
		quantity++;
	}

	public void addAnotherToCart(Book book) throws BookOutOfStockException {
		if (quantity == 0)
			throw new BookOutOfStockException("Sorry user, this book is out of stock.");
		book.quantity++;
		this.quantity--;
	}

	public int getQuantity() {
		return quantity;
	}

	public void reduceQuantityFromCart() {
		quantity--;
	}


}
