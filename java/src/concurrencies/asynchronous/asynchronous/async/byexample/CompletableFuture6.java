package asynchronous.async.byexample;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

class Book {
    String bookId;

    public Book(String bookId) {
        this.bookId = bookId;
    }
}

class BookService {
    public static Book getBookDetails(String bookId) {
        return new Book(bookId);
    }
}

class CreditBookService {
    public static Double getCreditRating(Book book) {
        return Double.parseDouble(book.bookId);
    }
}

class Util {
    public static CompletableFuture<Book> getBooksDetail(String bookId) {
        return CompletableFuture.supplyAsync(() -> {
            return BookService.getBookDetails(bookId);
        });
    }

    public static CompletableFuture<Double> getCredit(Book book) {
        return CompletableFuture.supplyAsync(() -> {
            return CreditBookService.getCreditRating(book);
        });
    }
}

public class CompletableFuture6 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Using thenCompose()
        CompletableFuture<Double> flattened = Util.getBooksDetail("1")
                .thenCompose(book ->
                        Util.getCredit(book)
                );
        System.out.println(flattened.get());
    }
}