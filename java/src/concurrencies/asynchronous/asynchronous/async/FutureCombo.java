package asynchronous.async;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */


public class FutureCombo {

    public static void main(String[] args) throws Exception {
        thenApply();
        thenCompose();
        thenAcceptBoth();
        thenCombine();
        allOf();
        anyOf();
        chainOfThenApply();
        exception();
        exceptionUsingHandle();
        Thread.sleep(3000);
    }

    private static void thenApply() {
        CompletableFuture<CompletableFuture<String>> completableFuture =
                CompletableFuture.supplyAsync(() -> "Hello")
                        .thenApply(value -> CompletableFuture.supplyAsync(
                                () -> value + " Knolders! Its thenApply"));
        //Perform operation
    }

    private static void thenCompose() {
        CompletableFuture<String> completableFuture =
                CompletableFuture.supplyAsync(() -> "Hello")
                        .thenCompose(value ->
                                CompletableFuture.supplyAsync(
                                        () -> value + " Knolders! Its thenCompose"));
        completableFuture.thenAccept(System.out::println); // Hello Knolders! Its thenCompose
    }


    private static void thenAcceptBoth() {
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " Knolders! Its thenAcceptBoth"),
                        (value1, value2) -> System.out.println(value1 + value2)); // Hello Knolders! Its thenAcceptBoth
    }

    private static void thenCombine() {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(
                        () -> " Knolders! Its thenCombine"), (value1, value2) -> value1 + value2);
        completableFuture.thenAccept(System.out::println); // Hello Knolders! Its thenCombine
    }

    private static void allOf() {
        CompletableFuture<String> completableFuture1
                = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> completableFuture2
                = CompletableFuture.supplyAsync(() -> "Knolders!");
        CompletableFuture<String> completableFuture3
                = CompletableFuture.supplyAsync(() -> "Its allOf");

        CompletableFuture<Void> combinedFuture
                = CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3);

        combinedFuture.thenApply(v ->
                Stream.of(completableFuture1, completableFuture2, completableFuture3).
                        map(CompletableFuture::join).
                        collect(Collectors.toList()));
        String combined = Stream.of(completableFuture1, completableFuture2, completableFuture3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
        System.out.println(combined); // Hello Knolders! Its allOf
    }

    private static void anyOf() throws Exception {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of Future 1";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of Future 2";
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of Future 3";
        });
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);
        anyOfFuture.thenAccept(System.out::println); // Result of Future 2
    }

    private static void exception() {
        Integer age = -1;
        CompletableFuture<String> exceptionFuture = CompletableFuture.supplyAsync(() -> {
            if (age < 0) {
                throw new IllegalArgumentException("Age can not be negative");
            }
            if (age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        }).exceptionally(ex -> {
            System.out.println("Oops! We have an exception - " + ex.getMessage());
            return "Unknown!";
        });
        exceptionFuture.thenAccept(System.out::println); //Unknown!
    }

    private static void exceptionUsingHandle() {
        Integer age = -1;
        CompletableFuture<String> exceptionFuture = CompletableFuture.supplyAsync(() -> {
            if (age < 0) {
                throw new IllegalArgumentException("Age can not be negative");
            }
            if (age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        }).handle((result, ex) -> {
            if (ex != null) {
                System.out.println("Oops! We have an exception - " + ex.getMessage());
                return "Unknown!";
            }
            return result;
        });
        exceptionFuture.thenAccept(System.out::println); // Unknown!
    }

    private static void chainOfThenApply() {
        CompletableFuture.supplyAsync(() -> {
            // Code which might throw an exception
            return "Some result";
        }).thenApply(result -> "processed result")
          .thenApply(result -> "result after further processing")
          .thenAccept(result -> {
            // do something with the final result
        });
    }
}
