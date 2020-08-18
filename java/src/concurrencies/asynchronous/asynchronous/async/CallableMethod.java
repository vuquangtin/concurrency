package asynchronous.async;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class CallableMethod {

    public static void main(String[] args) throws Exception {
        runAsync();
        supplyAsync();
        thenAccept();
        thenApply();
        chainOfCallbacks();
        thenRun();

        Thread.sleep(10000);
    }

    private static void runAsync() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("Running asynchronous task in parallel");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    private static void supplyAsync() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "This is the result of the asynchronous computation";
        });
        future.thenAccept(System.out::println);
    }

    private static void thenAccept() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Knolders!");
        CompletableFuture<Void> result
                = completableFuture.thenAccept(value -> System.out.println("Hello " + value));
    }

    private static void thenApply() throws Exception{
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Knolders!";
        });

        CompletableFuture<String> result = completableFuture.thenApply(name -> "Hello " + name);
        result.thenAccept(System.out::println);
    }


    private static void chainOfCallbacks() {
        CompletableFuture<Void> result = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Knolders!";
        }).thenApply(name -> "Hello " + name)
          .thenApply(greeting -> greeting + " Welcome to Knoldus Inc!")
          .thenAccept(System.out::println);
    }

    private static void thenRun() {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Knolders!");
        CompletableFuture<Void> result
                = completableFuture.thenRun(() -> System.out.println("Example with thenRun()."));
    }
}