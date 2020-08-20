# CountDownLatch

>[await đợi cho đến khi countdown=0. chúng ta có thể đặt số lượng await với số lượng không giới hạn trong các Thread cần trờ chứ ko phải await=countdown]

## Nguyên lý hoạt động

>Lớp CountDownLatch cho phép chúng ta bắt đầu thực hiện một thread X ngay sau khi tất cả các thread A1, A2, A3, ... đều đã hoàn thành.

>CountDownLatch sử dụng một biến đếm nội bộ. Khác với Semaphore, biến đếm này chỉ giảm mà không tăng và khi giảm đến 0 thì sẽ dừng. Giá trị khởi tạo của biến đếm được truyền vào từ hàm khởi tạo: CountDownLatch(int count).

Mỗi khi một thread A1, A2, A3, ... hoàn thành, phương thức countDown() sẽ được gọi để giảm biến đếm đi 1. Bên cạnh đó, phương thức await() được gọi để block cho đến khi biến đếm giảm xuống còn 0. Khi đó, thread X chính thức được bắt đầu.

Ví dụ:

MainTask.java

```java
public class MainTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Start main task...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done main task!");
    }

}
```

SubTask.java

```java
import java.util.concurrent.CountDownLatch;

public class SubTask implements Runnable {

    private CountDownLatch countDownLatch;

    public SubTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void run() {
        System.out.println("Start sub task...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done sub task!");
        countDownLatch.countDown();
    }

}
```

Test.java

```java
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new SubTask(countDownLatch));
        executorService.submit(new SubTask(countDownLatch));
        executorService.submit(new SubTask(countDownLatch));

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(countDownLatch.getCount());
        executorService.submit(new MainTask());

        executorService.shutdown();
    }

}
```

Kết quả:

```java
Start sub task...
Start sub task...
Start sub task...
Done sub task!
Done sub task!
Done sub task!
0
Start main task...
Done main task!
```