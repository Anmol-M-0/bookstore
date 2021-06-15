package shop_tester;

import static com.app.utils.ShopUtils.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.app.shop.Book;
import com.app.shop.BookStore;
import com.app.user.Admin;
import com.app.user.Customer;
import com.app.user.LoginUser;
import com.app.utils.BookOutOfStockException;
import com.app.utils.InvalidBookIdException;
import com.app.utils.InvalidLoginException;

/*
 * as far as possible i have tried to write a self documenting code
 * however comments have been provided
 */
public class Tester {
	public static void main(String[] args) {
		/*
		 * an object representing a bookstore. BookStore has users, and a user has an
		 * id, password associated with their account
		 */
		BookStore bookShop = new BookStore(populateStash(), populateUsers());
		/*
		 * login interface 5 customers and 1 admin login has been loaded. customer
		 * interface has been implemented sample customer email id and password : Email
		 * : neil@gmail.com Password : password@123
		 */
		try (Scanner sc = new Scanner(System.in)) {
			System.out.print("*Login*\nEnter email: ");
			String email = sc.next();
			System.out.print("Enter password : ");
			LoginUser user = validateLogin(bookShop.getUsers(), new LoginUser(email, sc.next()));
			System.out.println("Login successful!");
			if (user instanceof Admin) {
				System.out.println("Admin Login Successful. \nLogging out now.\nExiting.");
				System.exit(0);
			}
			/*
			 * loading the interface, since the customer has logged in
			 */
			int bookId;
			String yn;
			boolean exit = false;
			ArrayList<Book> bookStash = new ArrayList<>(bookShop.getBookStash());
			ArrayList<Book> cart = new ArrayList<>(); // cart
			while (!exit) {
				try {
					System.out.println("____________________________________\nEnter the following options: \n 1. List all books"
							+ "\n 2. Add a book to cart\n 3. Remove book from the cart\n 4. Show cart"
							+ "\n 5. Checkout\n 0. Logout and exit immediately");
					int key = sc.nextInt();
					switch (key) {
					/*
					 * list all books interface
					 */
					case 1:
						System.out.println("Here's the list of all avaliable books : ");
						showAllBooks(bookStash);
						break;
					/*
					 * add to cart interface
					 */
					case 2:
						System.out.println("Enter the Book ID to add the book to your cart : ");
						bookId = sc.nextInt();
						addToCart(cart, bookStash, bookId);
						break;
					/*
					 * remove from cart interface
					 */
					case 3:
						System.out.println("Enter the Book ID to remove the book from your cart :");
						bookId = sc.nextInt();
						removeFromCart(cart, bookStash, bookId);
						break;
					/*
					 * show the cart content
					 */
					case 4:
					case 5:
						System.out.println("Here are the contents in your cart : ");
						showAllBooks(cart);
						if (key == 4)// if the user only wants to see the contents...
							break;
						/*
						 * checkout interface
						 */
						System.out.print("Checkout: \nYour bill is : " + calculateBill(cart));
						System.out.println("\nDo you want to continue?y/n");
						yn = sc.next();
						if (!(yn.equals("y") || yn.equals("yes")))
							break;
						checkout(cart, (Customer) user);
						System.out.println("Books purchased : ");
						showAllBooks(((Customer) user).getPurchasedBooks());
						/*
						 * logout interface
						 */
					case 0:
						System.out.println("Logging out.");
						exit = true;
						break;
					default:
						System.err.println("Invalid Option, here are the valid options: ");
						break;
					}
				} catch (BookOutOfStockException e) {
					System.out.println("==========\n" + e.getMessage() + "\n==========");
				} catch (InvalidBookIdException e) {
					System.err.println("==========\n" + e.getMessage() + "\n==========");
				} catch (InputMismatchException e) {
					System.err.println("There was a mismatch in input, logging out and exiting.");
					System.exit(0);
				} catch (Exception e) {
					System.err.println("There was an error, logging out and exiting.");
					e.printStackTrace();
					System.exit(0);
				}

			}

			System.out.println("Logged out, exiting.");

		} catch (InvalidLoginException e) {
			System.err.println("==========\n" + e.getMessage() + "\n==========");
			System.out.println("*exiting*");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("*exiting*");
		}

	}

}
