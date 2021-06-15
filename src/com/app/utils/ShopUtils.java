package com.app.utils;

import static com.app.shop.BookCategory.FICTION;
import static com.app.shop.BookCategory.FINANCE;
import static com.app.shop.BookCategory.MEDITATION;
import static com.app.shop.BookCategory.SCIENCE;
import static com.app.shop.BookCategory.YOGA;
import static java.time.LocalDate.parse;

import java.util.ArrayList;
import java.util.Arrays;

import com.app.shop.Author;
import com.app.shop.Book;
import com.app.user.Admin;
import com.app.user.Customer;
import com.app.user.LoginUser;

public interface ShopUtils {

	static ArrayList<Book> populateStash() {// sample hardcoded bookstash

		Author sv = new Author("Swami Vivekananda");
		Author jkr = new Author("J. K. Rowling");
		Author ndt = new Author("Neil Degrasse Tyson");
		Author bksi = new Author("B.K.S. Iyengar");
		ArrayList<Book> stash = new ArrayList<>();
		stash.add(new Book(10, 5, "Harry Potter and the Philosopher's Stone", FICTION, 1000, parse("1997-05-03"), jkr));
		stash.add(new Book(10, 5, "Harry Potter and the Chamber of Secrets", FICTION, 1000, parse("1998-05-06"), jkr));
		stash.add(new Book(10, 5, "Harry Potter and the Prisoner of Azkaban", FICTION, 1000, parse("1999-05-07"), jkr));
		stash.add(new Book(10, 5, "Harry Potter and the Goblet of Fire", FICTION, 1000, parse("2000-05-01"), jkr));
		stash.add(
				new Book(10, 5, "Harry Potter and the Order of the Phoenix ", FICTION, 1000, parse("2003-05-10"), jkr));
		stash.add(new Book(10, 5, "Harry Potter and the Half-Blood Prince", FICTION, 1000, parse("2005-05-03"), jkr));
		stash.add(new Book(10, 5, "Harry Potter and the Deathly Hallows", FICTION, 1000, parse("2007-05-18"), jkr));
		stash.add(new Book(5, 5, "Astrophysics for People in a Hurry", SCIENCE, 350, parse("2017-06-02"), ndt));
		stash.add(new Book(10, 5, "Letters from an Astrophysicist", SCIENCE, 443, parse("2019-11-12"), ndt));
		stash.add(new Book(5, 5, "Astrophysics for Young People in a Hurry", SCIENCE, 728, parse("2019-03-22"), ndt));
		stash.add(new Book(5, 5, "Death by Black Hole – And Other Cosmic Quandaries: And Other Cosmic Quandries",
				SCIENCE, 956, parse("2014-09-12"), ndt));
		stash.add(new Book(5, 4,
				"Just Visiting This Planet: Merlin Answers More Questions About Everything Under the Sun, Moon, and Stars",
				SCIENCE, 899, parse("1998-07-13"), ndt));
		stash.add(new Book(3, 5, "The Psychology of Money", FINANCE, 310, parse("2020-09-18"),
				new Author("Morgan Housel")));
		stash.add(new Book(3, 5, "Think & Grow Rich", FINANCE, 98, parse("2014-12-01"), new Author("Napoleon Hill")));
		stash.add(new Book(3, 5, "The Richest Man in Babylon", FINANCE, 96, parse("2018-08-01"),
				new Author("George S. Clason")));
		stash.add(new Book(3, 5, "The Barefoot Investor: The Only Money Guide You'll Ever Need", FINANCE, 523,
				parse("2018-10-20"), new Author("Scott Pape")));
		stash.add(new Book(3, 5, "Patanjali’s Yoga Sutras", MEDITATION, 113, parse("2019-10-01"), sv));
		stash.add(new Book(3, 5, "Meditation and Its Methods", MEDITATION, 173, parse("2018-06-20"), sv));
		stash.add(new Book(3, 5, "Yoga The Path to Holistic Health: The Definitive Step-by-Step Guide", YOGA, 1290,
				parse("2018-11-01"), bksi));
		stash.add(new Book(1, 5, "Light on Yoga: The Classic Guide to Yoga by the World's Foremost Authority", YOGA,
				274, parse("2006-03-20"), bksi));
		return stash;
	}

	static ArrayList<LoginUser> populateUsers() {// sample hardcoded loginusers: 5 customers and an admin
		return new ArrayList<>(Arrays.asList(new Customer("neil@gmail.com", "password@123"),
				new Customer("swami@gmail.com", "pass@456"), new Customer("napoleon@gamil.com", "napo@789"),
				new Customer("rowling@gmail.com", "rowling@123"), new Customer("hallden@gmail.com", "123@password"),
				new Admin("admin", "admin@123")));
	}

	/*
	 * validate login utility
	 */
	static LoginUser validateLogin(ArrayList<LoginUser> all, LoginUser user) throws InvalidLoginException {
		for (LoginUser e : all) {
			if (!e.equals(user)) {
				continue;
			}
			if (!e.validateLogin(user)) {
				throw new InvalidLoginException("Invalid password! Exiting.");
			}
			return e;
		}
		throw new InvalidLoginException("Invalid Login! Exiting.");
	}

	/*
	 * cart utilities : add to cart if a book is already present, increase the
	 * quantity by 1
	 */
	static void addToCart(ArrayList<Book> cart, ArrayList<Book> bookStash, int bookId)
			throws BookOutOfStockException, InvalidBookIdException {
		validateBookId(bookId);
		for (Book book : bookStash) {
			if (book.getBookId() == bookId) {
				if (cart.contains(book)) {
					book.addAnotherToCart(cart.get(cart.indexOf(book)));
				} else {
					cart.add(book.addToCart(book));
				}
				System.out.println("A book has been added to cart : " + book.getTitle());
				return;
			}
		}
		throw new InvalidBookIdException("Invalid book id. The book specified by this id does not exist.");
	}

	/*
	 * cart utilities : remove from cart - if the book is already present, only
	 * reduce it's quantity by 1
	 */
	static void removeFromCart(ArrayList<Book> cart, ArrayList<Book> bookStash, int bookId)
			throws InvalidBookIdException {
		validateBookId(bookId);
		for (Book e : cart) {
			if (e.getBookId() == bookId) {
				if (e.getQuantity() > 1) {
					e.reduceQuantityFromCart();
					System.out.println("A book has been removed from cart : \n" + e.getTitle());
				} else {
					System.out.println("A book has been removed from cart : \n" + e.getTitle());
					cart.remove(e);
				}
				(bookStash.get(bookStash.indexOf(e))).returnBookToStore();
				return;

			}
		}
		throw new InvalidBookIdException("Invalid book id. The specified book does not exist in the cart.");
	}

	/*
	 * checkout utilities
	 */
	static double calculateBill(ArrayList<Book> cart) {
		double price = 0;
		for (Book e : cart) {
			price += (e.getPrice() * e.getQuantity());
		}

		return price;
	}

	static void checkout(ArrayList<Book> cart, Customer user) {
		user.purchaseBook(cart);
		cart.clear();
	}

	/*
	 * show all books utility
	 */
	static void showAllBooks(ArrayList<Book> books) {
		books.forEach(e -> System.out.println("____________________________________\n" + e));
	}

	/*
	 * utility to validate book id
	 */
	static void validateBookId(int bookId) throws InvalidBookIdException {
		if (bookId < 100) {
			throw new InvalidBookIdException("Invalid book id.");
		}
	}

}
