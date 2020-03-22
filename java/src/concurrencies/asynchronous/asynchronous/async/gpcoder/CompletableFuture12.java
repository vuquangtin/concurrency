package asynchronous.async.gpcoder;
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

public class CompletableFuture12 {
 
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<String> maturityFuture = 
                CompletableFuture.supplyAsync(() -> -1).thenApply(age -> {
            if (age < 0) { throw new IllegalArgumentException("Age can not be negative"); } if (age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        }).handle((res, ex) -> {
            if (ex != null) {
                System.out.println("Oops! We have an exception - " + ex.getMessage());
                return "Unknown!";
            }
            return res;
        });
        System.out.println("Maturity : " + maturityFuture.get());
    }
}