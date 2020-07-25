package threadpools.concretepage;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class BookReader implements Runnable {
	private String bookName;

	public BookReader(String bookName) {
		this.bookName = bookName;
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println("Reading book: " + bookName);
		}
	}
}