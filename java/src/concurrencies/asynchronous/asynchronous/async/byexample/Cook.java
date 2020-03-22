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
public class Cook {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        CompletableFuture<String> cookingMeat = new CompletableFuture<>();
        CompletableFuture<String> cookingRice = cookingRice();

		System.out.println("Làm bữa tối");
        cookingMeat.complete(cookingMeat());
        String meat = cookingMeat.get();
        String rice = cookingRice.getNow("Gạo");
        System.out.println("Ăn tối với: " + meat + " và " + rice);
    }

    public static CompletableFuture<String> cookingRice() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Nấu cơm ... ");
                Thread.sleep(9000);
                System.out.println("Nấu cơm xong");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Cơm";
        });
    }

    public static String cookingMeat() {
        try {
            System.out.println("Rang thịt ... ");
            Thread.sleep(3000);
            System.out.println("Rang xong");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Thịt rang";
    }
}