# java.util.concurrent trong lập trình đa luồng
Viết code đa luồng vừa làm việc tốt vừa bảo vệ được các ứng dụng trước các lỗi là khó khăn, đó là lý do mà chúng ta có java.util.concurrent. Tôi sẽ giới thiệu các class của java concurrent: CopyOnWriteArrayList, BlockingQueue, ConcurrentMap đã đáp ứng các yêu cầu lập trình multithread như thế nào

Java concurrent là bổ sung to lớn cho java 5, nhưng nó các nhà phát triển không nhận thấy chúng vì còn đang quan tân đến annotations và generics. Nhưng thực tế java concurrent chứa nhiều class giải quyết có hiệu quả các vấn đề về multithread mà bạn không phải vất vả xử lý nữa. Hãy đọc và tìm hiểu các class trong java.util.concurrent như CopyOnWriteArrayList và BlockingQueue sẽ giúp bạn giải quyết những vấn đề nan giải của lập trình multithread.

Trong java.util.concurrent cung cấp các tiện ích đồng bộ hoá giúp cho việc lập trình multithread dễ dàng hơn. Bài viết này sẽ giới thiệu một số ý tưởng đồng bộ hóa thread ở mức cao hơn so với thread đơn thuần, trong bài viết này sẽ hướng dẫn cơ bản về sử dụng và quản lý multithread tư tưởng giống như

- Ổ khoá: Trong một thời điểm chỉ có 1 thread được chạy
- Cửa chặn: Thread phải chờ đến khi thoả mãn điều kiện nào đó thì mới được chạy


## TimeUnit

Thực chất nó không phải là class của java concurrnet, nó chỉ là kiểu liệt kê(enum) làm cho code dễ đọc hơn rất nhiều. Việc sử dụng TimeUnit giải phóng các nhà phát triển khỏi gánh nặng về mili giây khi sử dụng phương thức và API TimeUnit liệt kê tất cả đơn vị thời gian [DAYS HOURS MICROSECONDS MILLISECONDS MINUTES NANOSECONDS SECONDS] nó xử lý hầu như tất cả các kiểu thời gian mà một nhà phát triển có thể cần đến. Có thể chuyển đổi qua lại các kiểu thời gian rất dễ dàng.

## CopyOnWriteArrayList

Việc tạo bản sao mới của 1 mảng là một hoạt động quá tốn kém về cả chi phí thời gian và bộ nhớ, khi xem xét sử dụng thông thường, các nhà phát triển thường sử dụng ArrayList có đồng bộ để thay thế. Tuy nhiên đó cũng là 1 lựa chọn vì mỗi khi bạn duyệt qua nội dung bản phải đồng bộ hóa tất cả các hoạt động , bao gồm cả việc đọc và viết, để đảm bảo tính nhất quán. Điều này làm cho việc xử lý gặp nhiều khó khăn vì có những tính huống ở nơi có rất nhiều người đang đọc, vài người lại đang sửa đổi nó CopyOnWriteArrayList:

* là 1 class tuyệt vời để giải quyết vấn đề này, tất cả các vấn đề đọc thêm sửa xóa đều được đồng bộ được thực hiện bằng cách tạo 1 bản sao mới của mảng.
* Class này thực hiện sao chép nội bộ các nội dung của nó vào một mảng mới khi có bất kỳ sự thay đổi nào, do đó khi những người đọc truy cập vào các nội dung của mảng sẽ không phải chịu thời gian đồng bộ hóa(bởi vì họ không bao giờ hoạt đông trên dữ liệu thay đổi)
* Các hàm cơ bản
<br/>addIfAbsent(E e): Thêm mới phần tử nếu chưa tồn tại
<br/>remove(Object e): Xóa phần tử nếu tồn tại
<br/>Iterator iterator(): Trả về toàn bộ iterator tất cả element nhưng tất cả đểu ở trên snapshot của list.

## BlockingQueue

Interface BlockingQueue nói rằng nó là 1 hàng đợi có nghĩa là nó được lưu trữ theo thứ tự vào trước ra trước(FIFO). Các mục được chèn theo thứ tự thế nào thì sẽ lấy ra cùng thứ tự đó, nhưng với sự đảm bảo thêm bất kỳ nỗ lực nào để lấy ra một mục từ một hàng đợi rỗng sẽ chặn luồng đang ddowcj gọi cho đến khi nó trở nên sẵn sàng để lấy ra. Tương tự như vậy bất kỳ sự cố gắng nào để chèn một mục vào trong hàng được đã đầy sẽ chặn luồng đang gọi cho đến khi nó sẵn chỗ đê lưu trữ vào hàng đợi

BlockingQueue giải quyết dọn vấn đề làm thế nào để chuyển vùng các mục được thu nhập bởi 1 luồng, đưa sang luồng khác để xử lý mà không phải quan tâm chi tiết đến các vấn để động bộ hóa.

ArrayBlockingQueue là 1 class đảm bảo công bằng giữa luồng đọc và luồng viết quyển truy cập vào trước ra trước.

```java
package producerconsumer.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {

	public static void main(String[] args) throws Exception {

		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1024);

		Producer producer = new Producer(queue);
		Consumer consumer = new Consumer(queue);

		new Thread(producer).start();
		new Thread(consumer).start();

		Thread.sleep(2345);
	}
}

class Producer implements Runnable {

	protected BlockingQueue<String> queue = null;

	public Producer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			queue.put("100");
			Thread.sleep(1000);
			queue.put("200");
			Thread.sleep(1000);
			queue.put("300");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Consumer implements Runnable {

	protected BlockingQueue<String> queue = null;

	public Consumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			System.out.println(queue.take());
			System.out.println(queue.take());
			System.out.println(queue.take());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
```

Trên là ví dụ về việc đọc và ghi ở 2 luống khác biệt hoàn toàn. trong thực tế khi làm việc với dữ liệu và luồng nhiều thì dùng offer để đẩy queue vào sẽ nhanh hơn

## ConcurrentMap

Map chứa đựng một lỗi xảy ra lỗi giữa đọc và ghi key vào map, nếu như trước thường phải sử dụng synchonized ở hàm để đồng bộ xử lý dữ liệu. Nhưng với ConcurrentMap thì giải quyết dễ dàng

Khi một map được truy cập từ nhiều luồng thường phổ biến là sử dụng containsKey() hoặc get() để tìm hiểu xem key đã có mặt trước đó không trước khi lưu trữ. Nhưng một khi map đã đồng bộ hóa thì một luồng có thể lẻn vào và nắm quyển điều khiển map. Vấn đề là khóa đồng thời get() và put() dẫn đến kết quả khác nhau tùy thuộc vào luồng nào đến trước

Nếu 2 luồng cùng gọi 1 phương thức chính xác tại 1 thời điểm, một luồng sẽ kiểm tra và sau đó một luồng sẽ đặt giá trị, làm mất đi giá trị của luồng 1. Nhưng với concurrentMap thì hỗ trợ 1 số phương thức để làm 2 việc dưới 1 khóa duy nhất. ví dụ hàm putIfAbsent(), đầu tiên nó sẽ kiểm tra key có chưa, sau đó chỉ đặt nếu key chưa tồn tại trong map

```java
// ConcurrentMap
   V putIfAbsent(K key, V value)

   // HashMap
   if (!map.containsKey(key))
       return map.put(key, value);
   else
       return map.get(key);
```
## SynchronousQueues

Một hàng được có chặn trong đó mỗi hoạt động chèn vào phải chờ 1 hoạt động gỡ bỏ tương ứng bởi một luồng khác và ngược lại. Một SynchronousQueue không có bất kỳ dung lượng nào, thậm chí ngay cả dung lượng = 1

Về cơ bản, SynchronousQueue là một việc triển khai thực hiện khác của BlockingQueue nói trên. Nó cung cấp cho chúng ta một cách rất gọn nhẹ để trao đổi các phần tử đơn lẻ từ một luồng này sang luồng khác.

```java
package synchronizers;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {

	public static void main(String[] args) {
		final SynchronousQueue<String> queue = new SynchronousQueue<String>();

		// start publisher thread
		new Thread(new Runnable() {

			@Override
			public void run() {
				String event = "SYNCHRONOUS_EVENT";
				String another_event = "ANOTHER_EVENT";
				try {
					queue.put(event);
					System.out.printf("[%s] published event : %s %n", Thread.currentThread().getName(), event);

					queue.put(another_event);
					System.out.printf("[%s] published event : %s %n", Thread.currentThread().getName(), another_event);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		// start consumer thread
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String event = queue.take();
					// thread will block here
					System.out.printf("[%s] consumed event : %s %n", Thread.currentThread().getName(), event);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
```

Ouput:
```java
    [Thread-0] published event : SYNCHRONOUS_EVENT
    [Thread-1] consumed event : SYNCHRONOUS_EVENT
```

## Semaphore

Trong một hệ thống, không phải là hiếm các trường hợp khi các nhà phát triển cần phải điều tiết số lượng các yêu cầu đang được mở đối với một tài nguyên cụ thể. Thực tế, đôi khi việc điều tiết có thể cải thiện thông qua một hệ thống bằng cách giảm số lượng tranh chấp giành tài nguyên cụ thể đó. Mặc dù chắc chắn có thể viết mã bằng tay, thì việc sử dụng lớp semaphore còn dễ dàng hơn, do semaphore sẽ lo việc điều tiết cho bạn

```java
import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            final Random rand = new Random();
            final Semaphore available = new Semaphore(3);
            int count = 0;

            public void run() {
                int time = rand.nextInt(15);
                int num = count++;

                try {
                    available.acquire();

                    System.out.println("Executing " + "long-running action for " + time + " seconds... #" + num);

                    Thread.sleep(time * 1000);

                    System.out.println("Done with #" + num + "!");

                    available.release();
                } catch (InterruptedException intEx) {
                    intEx.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 10; i++)
            new Thread(r).start();
    }
}
```

Kết quả thực hiện:
```java
Executing long-running action for 7 seconds... #0
Executing long-running action for 14 seconds... #1
Executing long-running action for 7 seconds... #2
Done with #0!
Executing long-running action for 12 seconds... #3
Done with #2!
Executing long-running action for 9 seconds... #4
Done with #1!
Executing long-running action for 0 seconds... #5
Done with #5!
Executing long-running action for 14 seconds... #6
Done with #4!
Executing long-running action for 14 seconds... #7
Done with #3!
Executing long-running action for 6 seconds... #8
Done with #8!
Executing long-running action for 12 seconds... #9
Done with #6!
Done with #7!
Done with #9!
```
Mặc dù 10 Thread trong ví dụ này đang chạy(bạn có thể kiểm tra bằng lệnh jstack dựa vào tiến trình Java đang chạy SemaphoreDemo nhưng chỉ có 3 luồng đang hoạt động. Bảy thread khác đang được giữ trong ngăn cho đến khi một trong các bộ đếm của semaphore được giải phóng

## CountDownLatch

Nếu semaphore là lớp được thiết kế để cho phép các luồng vào, mỗi cái một lần thì countdownlatch là cổng xuất phát của cuộc đua ngựa. Lớp này nắm giữ tất cả các luồng ở trong ngăn của nó cho đến khi có một điều kiện cụ thể được đáp ứng thì lúc đó nó sẽ giải phóng tất cả cùng một lúc

```java
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String args[]) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(4);
        Worker first = new Worker(1000, latch, "WORKER-1");
        Worker second = new Worker(2000, latch, "WORKER-2");
        Worker third = new Worker(3000, latch, "WORKER-3");
        Worker fourth = new Worker(4000, latch, "WORKER-4");

        first.start();
        second.start();
        third.start();
        fourth.start();

        // Main thread will wait until all thread finished
        latch.await();

        System.out.println(Thread.currentThread().getName() + " has finished");
    }
}

class Worker extends Thread {
    private int delay;
    private CountDownLatch latch;

    public Worker(int delay, CountDownLatch latch, String name) {
        super(name);
        this.delay = delay;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
            latch.countDown();
            System.out.println(Thread.currentThread().getName() + " has finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```
Kết quả thực hiện:

```java
WORKER-1 has finished
WORKER-2 has finished
WORKER-3 has finished
WORKER-4 has finished
main has finished
Trong ví dụ trên CountDownLatch có nhiệm vụ đảm bảo cho toàn bộ các worker làm việc rồi thì mới cho bắt đầu chương trình. Giống như trong tạo 1 chương trình có nhiều service hoặc nhiều thread. Để đảm bảo toàn bộ thread đã khởi tạo xong thì chương trình mới khởi động. Nếu không dùng CountDownLatch thì kết quả sẽ như sau

main has finished
WORKER-1 has finished
WORKER-2 has finished
WORKER-3 has finished
WORKER-4 has finished
```

## Executor

Trong các ví dụ trên có một thiếu sót, đó là chúng buộc bạn phải trực tiếp tạo các đối tượng Thread. Đây là cách làm mang lại rắc rối và tăng bộ nhớ. Nên việc sử dụng lại các Thread hiện có tốt hơn rất nhiều so với tạo ra 1 thread mới. Java cung cấp ExecutorService mô hình hoá việc tạo thread như là 1 dịch vụ có thể được điều khiển chung.

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(new Runnable() {
            public void run() {
                System.out.println("Asynchronous task");
            }
        });

        executorService.shutdown();
    }
}
```

## ScheduledExecutorServices

Dùng ExecutorService thật là tuyệt vời, nhưng một số thread cần phải thực hiện có lịch trình, chẳng hạn như việc thi hành 1 thread ở trong một thời gian xác định hoặc tại 1 thời điểm cụ thể thì chúng ta sử dụng ScheduledExecutorServices nó là mở rộng của ExecutorServices

Ví dụ nếu mục tiêu của bạn là tạo ra một lệnh heartbeat để ping 5s/lần thì ScheduledExecutorServices sẽ làm cho nó đơn giản như sau:

```java
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Heartbeat {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        Runnable pinger = new Runnable() {
            public void run() {
                System.out.println("PING!");
            }
        };
        ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(pinger, 5, 5, TimeUnit.SECONDS);

        Thread.sleep(20000);

        scheduledFuture.cancel(true);
    }
}
```

Không còn phiền phức với Thread, cũng không phiền phức với những gì phải làm nếu người dùng muốn huỷ nhịp tim đó, không cần phải quan tâm đến thread chạy khi nào, bạn chỉ cần để lại chi tiết lịch trình cho ScheduledExecutorServices. Bỗng nhiên bạn muốn huỷ nó đi cũng rất đơn giản bạn chỉ cần gọi phương thức cancel

## Kết luận:

Gói java.util.concurrent chứa nhiều tiện ích tốt hơn là mở rộng ra ngoài các bộ sưu tập, đặc biệt là trong các gói .locks và .atomic. Hãy đi sâu vào và bạn sẽ tìm thấy các cấu trúc điều khiển có ích như CyclicBarier và nhiều hơn nữa ...