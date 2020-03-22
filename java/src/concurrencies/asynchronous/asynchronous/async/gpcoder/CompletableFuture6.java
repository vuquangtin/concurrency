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
class MailUtil {
    public static String getMailInfo() {
        return "Your email content";
    }
 
    public static boolean sendMail() {
        System.out.println("Send mail: completed");
        return true;
    }
 
    public static void logging() {
        System.out.println("Log: Send mail at " + System.currentTimeMillis());
    }
}
 
public class CompletableFuture6 {
 
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // thenAccept() example
        CompletableFuture.supplyAsync(() -> {
            return MailUtil.getMailInfo();
        }).thenAccept(content -> {
            System.out.println("Mail content: " + content);
        });
 
        // thenRun() example
        CompletableFuture.supplyAsync(() -> {
            return MailUtil.sendMail();
        }).thenRun(() -> {
            MailUtil.logging();
        });
    }
}