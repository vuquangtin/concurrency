package asynchronous.async.byexample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class HandleOffer2 {

	final static int TIME_TO_UPDATE_STOREAGE = 5000;
	final static int TIME_TO_UPDATE_TELESALE = 5000;
	final static int AMOUNT = 0;

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		System.out.println("Start processor");
		long startTime = System.currentTimeMillis();
		CompletableFuture<Integer> updateAmountInStorage = CompletableFuture.supplyAsync(() -> {
			System.out.println("updateStoreage is running in a other thread.");
			updateStoreage(TIME_TO_UPDATE_STOREAGE);
			return AMOUNT;
		});

		// tính tiền cho telesale
		System.out.println("updateTelesale is running... in main thread");
		updateTelesale(TIME_TO_UPDATE_TELESALE);
		System.out.println("Số lượng hàng còn trong kho :" + updateAmountInStorage.get() + "ms");
		System.out.println("Hoàn thành process trong :" + (System.currentTimeMillis() - startTime));
	}

	static void updateStoreage(int time) {
		try {
			Thread.sleep(time);
			System.out.println("Đã giảm số lượng hàng trong kho thành công ");
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	static void updateTelesale(int time) {
		try {
			Thread.sleep(time);
			System.out.println("Đã cộng tiền cho telesale thành công ");
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}