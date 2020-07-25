package running.threads.in.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class SequenceUsenewSingleThreadExecutor {
	public static void main(String[] args) {
		//mainNewSingleThreadExecutor();
		//mainNewFixedThreadPool();
		//mainNewCachedThreadPool();
		try {
			mainCallable();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			mainMyCallable();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void mainInvokeAny() throws InterruptedException, ExecutionException {        
	    // Khai báo một Thread Pool thông qua newSingleThreadExecutor() của Executors
	    ExecutorService executorService = Executors.newSingleThreadExecutor();
	    List<Callable<String>> listCallable = new ArrayList<Callable<String>>(); 
	    // Khởi tạo danh sách các Callable
	         
	    for (int i = 1; i <= 5; i++) {
	        final int _i = i;
	        // Khởi tạo từng Callable
	        listCallable.add(new Callable<String>() {
	 
	            @Override
	            public String call() throws Exception {
	                // Trả về kết quả ở mỗi Callable
	                return "Callable " + _i;
	            }
	        });
	    }
	         
	    // Callable nào kết thúc ở đây cũng sẽ dừng luôn Thread Pool
	    String result = executorService.invokeAny(listCallable);
	    System.out.println("Result: " + result);
	         
	    // Phương thức này đã nói ở trên đây rồi
	    executorService.shutdown();
	}
	public static void mainNewSingleThreadExecutor() {
		// Khai báo một Thread Pool thông qua newSingleThreadExecutor() của
		// Executors
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		// Khai báo 10 Runnable, và "quăng" chúng vào Thread Pool vừa khai báo
		for (int i = 1; i <= 10; i++) {
			MyStringRunnable myRunnable = new MyStringRunnable("Runnable " + i);
			executorService.execute(myRunnable);
		}

		// Phương thức này sẽ được nói sau ở ExecutorService
		executorService.shutdown();
	}

	public static void mainNewFixedThreadPool() {
		// Khai báo một Thread Pool thông qua newFixedThreadPool(5) của
		// Executors.
		// Thread Pool này cho phép thực thi cùng một lúc 5 Thread
		ExecutorService executorService = Executors.newFixedThreadPool(5);

		// Khai báo 10 Runnable, và "quăng" chúng vào Thread Pool vừa khai báo
		for (int i = 1; i <= 10; i++) {
			MyStringRunnable myRunnable = new MyStringRunnable("Runnable " + i);
			executorService.execute(myRunnable);
		}

		// Phương thức này sẽ được nói sau ở ExecutorService
		executorService.shutdown();

	}

	public static void mainNewCachedThreadPool() {
		// Khai báo một Thread Pool thông qua newCachedThreadPool của Executors.
		ExecutorService executorService = Executors.newCachedThreadPool();

		// Khai báo 10 Runnable, và "quăng" chúng vào Thread Pool vừa khai báo
		for (int i = 1; i <= 10; i++) {
			MyStringRunnable myRunnable = new MyStringRunnable("Runnable " + i);
			executorService.execute(myRunnable);
		}

		// Phương thức này sẽ được nói sau ở ExecutorService
		executorService.shutdown();
	}
	public static void mainCallable() throws InterruptedException {        
	    ExecutorService executorService = Executors.newFixedThreadPool(5);
	    List<Future> listFuture = new ArrayList<Future>(); 
	    // Khởi tạo danh sách các Future
	         
	    for (int i = 1; i <= 10; i++) {
	    	MyStringRunnable myRunnable = new MyStringRunnable("Runnable " + i);
	        // Bước này chúng ta dùng submit() thay vì execute()
	        Future future = executorService.submit(myRunnable);
	        listFuture.add(future); // Từng Future sẽ quản lý một Runnable
	    }
	     
	    for (Future future : listFuture) {
	        try {
	            // Khi Thread nào kết thúc, get() của Future tương ứng sẽ trả về null
	            System.out.println(future.get());
	        } catch (ExecutionException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	         
	    // Phương thức này đã nói ở trên đây rồi
	    executorService.shutdown();
	}
	public static void mainMyCallable() throws InterruptedException {        
	    ExecutorService executorService = Executors.newFixedThreadPool(5);
	    List<Future<String>> listFuture = new ArrayList<Future<String>>(); 
	    // Khởi tạo danh sách các Future
	         
	    for (int i = 1; i <= 10; i++) {
	        // Dùng Callable thay cho Runnable
	        MyCallable myCallable = new MyCallable("Callable " + i);
	             
	        Future<String> future = executorService.submit(myCallable);
	        listFuture.add(future); // Từng Future sẽ quản lý một Callable
	    }
	         
	    for (Future future : listFuture) {
	        try {
	            // Khi Thread nào kết thúc, get() của Future tương ứng sẽ trả về kết quả mà Callable return
	            System.out.println(future.get() + " kết thúc");
	        } catch (ExecutionException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	         
	    // Phương thức này đã nói ở trên đây rồi
	    executorService.shutdown();
	}
}

class MyStringRunnable implements Runnable {

	// Tên của Runnable, giúp chúng ta phân biệt Runnable nào đang thực thi
	// trong Thread Pool
	private String name;

	public MyStringRunnable(String name) {
		// Khởi tạo Runnable với biến name truyền vào
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println(name + " đang thực thi...");

		// Giả lập thời gian chạy của Runnable mất 2 giây
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(name + " kết thúc.");
	}

}
class MyCallable implements Callable<String> {
	 
    // Tên của Callable, giúp chúng ta phân biệt Runnable nào đang thực thi trong
    // Thread Pool
    private String name;
 
    public MyCallable(String name) {
        // Khởi tạo Callable với biến name truyền vào
        this.name = name;
    }
 
    @Override
    public String call() throws Exception {
        System.out.println(name + " đang thực thi...");
         
        // Giả lập thời gian chạy của Callable mất 2 giây
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        // Trả kết quả về là một kiểu String
        return name;
    }
}
