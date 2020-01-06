package com.thread.random;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 1.Create the following classes:<br/>
 * a.Author with fields:<br/>
 * <ul>
 * <li>String name</li>
 * <li>short age</li>
 * <li>List<Book> books</li>
 * </ul>
 * b.Book with fields<br/>
 * <ul>
 * <li>String title</li>
 * <li>List<Author> authors</li>
 * <li>int numberOfPages</li>
 * </ul>
 * 2.Create two arrays: Author[] authors and Book[] books. Their elements must
 * use elements from the neighboring array.<br/>
 * 3.Create a stream of books and then:<br/>
 * <ul>
 * <li>check if some/all book(s) have more than 200 pages;</li>
 * <li>find the books with max and min number of pages;</li>
 * <li>filter books with only single author;</li>
 * <li>sort the books by number of pages/title;</li>
 * <li>get list of all titles;</li>
 * <li>print them using forEach method;</li>
 * <li>get distinct list of all authors</li>
 * </ul>
 * 4.Use peek method for debugging intermediate streams during execution the
 * previous task.<br/>
 * 5.Use parallel stream with subtask #3.<br/>
 * 6.Use the Option type for determining the title of the biggest book of some
 * author.<br/>
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class Task {
	public static void main(String[] args) {
		Author[] authors = createAuthors();
		Book[] books = createBooks();
		Arrays.stream(authors).forEach(author -> author
				.setBookList(asList(Arrays.copyOfRange(books, 0, ThreadLocalRandom.current().nextInt(1, 3)))));
		Arrays.stream(books).forEach(book -> book
				.setAuthorList(asList(Arrays.copyOfRange(authors, 0, ThreadLocalRandom.current().nextInt(1, 3)))));

		System.out.println("Books more then 200 pages");
		Arrays.stream(books).filter(book -> book.getNumberOfPages() > 200).forEach(System.out::println);

		System.out.println("Max pages " + Arrays.stream(books).max(comparing(Book::getNumberOfPages)));
		System.out.println("Min pages " + Arrays.stream(books).min(comparing(Book::getNumberOfPages)));

		System.out.println("---Filtering only 1size author books---");
		Arrays.stream(books).filter(book -> book.getAuthorList().size() == 1).forEach(System.out::println);

		System.out.println("---Comparing by pages and title---");
		Arrays.stream(books).parallel().sorted(comparing(Book::getNumberOfPages).thenComparing(Book::getTitle))
				.forEach(System.out::println);

		System.out.println("---Distinct authors---");
		Arrays.stream(books).map(Book::getAuthorList).flatMap(authors1 -> Arrays.stream(authors)).distinct()
				.forEach(System.out::println);

		Arrays.stream(books).max(comparing(Book::getNumberOfPages))
				.ifPresent(book -> System.out.printf("Biggest book title is" + book.getTitle()));
	}

	public static Book[] createBooks() {
		return new Book[] {
				new Book("Funny story of junior java developer", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("Mono2", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("WhereIsMySoul?", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("Docker Under Attack", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("Manager In Action: Definitive edition", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("Lost with Agile", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("Sprint planning before demo", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("Trust me, i know agile", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("How deep is agile hole", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("What have we done to software development", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("My pretty colleague Susana", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("How much is the story", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("Burning burndownchart", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("How to waste your time more effectively", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("Hand deployment in Action", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("Dinosaur driven development", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("How to show your commitment", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("How to love bloody enterprise", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("Monolith in action", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("Kotlin for dummies", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("Clojure for dummies", ThreadLocalRandom.current().nextInt(0, 1001)),
				new Book("How to get mental hospital with Clojure", ThreadLocalRandom.current().nextInt(0, 1001)) };
	}

	public static Author[] createAuthors() {
		return new Author[] { new Author("RedCrocodile", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("YellowCrocodile", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("BlueCrocodile", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("GreenCrocodile", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("WhiteCrocodile", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("PurpleCrocodile", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("DarkGreenCrocodile", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("AmbientCrocodile", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("InfernoCrocodile", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("FireLord", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("Yoda", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("DarthVader", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("Skywalker", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("TechnoTron3017", (short) ThreadLocalRandom.current().nextInt(0, 101)),
				new Author("AlbertEinstein", (short) ThreadLocalRandom.current().nextInt(0, 101)) };
	}
}
