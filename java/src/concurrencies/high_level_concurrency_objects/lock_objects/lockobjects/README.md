# Locks
Gói java.util.concurrency.locks cung cấp một cơ chế đồng bộ hóa có khả năng mở rộng và linh động hơn so với từ khóa synchronized. Trong bài viết này, chúng ta sẽ tìm hiểu 3 interface quan trọng của gói này, đó là:

1. java.util.concurrent.locks.Lock
2. java.util.concurrent.locks.ReadWriteLock
3. java.util.concurrent.locks.Condition

## Lock
### Nguyên lý hoạt động

Sẽ có một khóa (lock) cung cấp độc quyền quyền truy cập vào một tài nguyên được chia sẻ: các thread muốn truy cập tài nguyên này thì trước tiên cần phải acquire khóa. Tại một thời điểm, chỉ có duy nhất một thread mới được acquire khóa. Để acquire khóa, chúng ta có thể sử dụng các phương thức sau của Lock:

* void lock(): thread sẽ block cho đến khi có một khóa đã sẵn sàng cho thread acquire.
* boolean tryLock(): thread sẽ không bị block, trả luôn về true nếu tại thời điểm gọi tryLock() có một khóa đã sẵn sàng cho thread acquire.
* boolean tryLock(long timeout, TimeUnit timeUnit): tương tự như lock(), nhưng có đặt thêm timeout.
Sau khi thread đã truy cập xong tài nguyên, nó cần phải release khóa thông qua phương thức unlock().

```java
Lock myLock = ...;
myLock.lock();
try {
    // access the resource protected by this lock
} finally {
    myLock.unlock();
}
```

Lớp java.util.concurrent.locks.ReentrantLock là một implementation của Lock. Lớp này có một hàm khởi tạo: ReentrantLock(boolean fair). Trong đó, nếu fair bằng true thì các thread sẽ truy cập tài nguyên theo thứ tự FIFO: thread nào acquire khóa trước thì sẽ được truy cập tài nguyên trước (nếu khóa đã sẵn sàng).

### Ví dụ

Chúng ta sẽ áp dụng ReentrantLock cho ví dụ Counter.

Counter.java

```java
public class Counter {

    private int count;

    public Counter() {
        count = 0;
    }

    public void increment() {
        count++;
    }

    public int get() {
        return count;
    }

}
```
MyThread.java

```java
import java.util.concurrent.locks.ReentrantLock;

public class MyThread implements Runnable {

    private Counter counter;
    private ReentrantLock lock;

    public MyThread(Counter counter, ReentrantLock lock) {
        this.counter = counter;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            // Critical section
            counter.increment();
        } finally {
            lock.unlock();
        }
    }

}
```

Test.java

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    private static final int THREADS = 5;

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        ReentrantLock lock = new ReentrantLock(true);

        ExecutorService executorService = Executors.newFixedThreadPool(THREADS);
        for (int i = 0; i < THREADS; i++) {
            executorService.submit(new MyThread(counter, lock));
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println(counter.get());
    }

}
```

## ReadWriteLock
### Nguyên lý hoạt động

Khác với Lock, ReadWriteLock duy trì một cặp khóa: một khóa chỉ dành cho thao tác đọc và một khóa chỉ dành cho thao tác ghi. Khóa đọc có thể đưọc acquire đồng thời bởi nhiều thread miễn là các thread này đang không acquire khóa ghi. Nhưng khóa ghi thì tại một thời điểm chỉ có duy nhất một thread được acquire mà thôi.

ReadWriteLock có 2 phương thức là:

* Lock readLock(): trả về khóa đọc
* Lock writeLock(): trả về khóa ghi

Lớp java.util.concurrent.locks.ReentrantReadWriteLock là một implementation của ReadWriteLock:

* Lock ReentrantReadWriteLock.ReadLock: nested class đại diện cho khóa đọc
* Lock ReentrantReadWriteLock.WriteLock: nested class đại diện cho khóa ghi
* ReentrantReadWriteLock(boolean fair): tương tự như ReentrantLock(boolean fair).

ReentrantReadWriteLock có thể được dùng để tăng performance khi lập trình concurrent với các Collection có đặc điểm:

* Số lượng phần tử lớn.
* Được truy cập bởi nhiều thread đọc hơn là các thread ghi.
* Không đòi hỏi nhiều chi phí cho đồng bộ hóa.
Ví dụ

Chúng ta sẽ áp dụng ReentrantReadWriteLock để xây dựng một concurrent TreeMap:

```java
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyDictionary {

    private Map<String, String> map;
    private ReentrantReadWriteLock.ReadLock readLock;
    private ReentrantReadWriteLock.WriteLock writeLock;

    public MyDictionary(ReentrantReadWriteLock.ReadLock readLock, ReentrantReadWriteLock.WriteLock writeLock) {
        this.map = new TreeMap<>();
        this.readLock = readLock;
        this.writeLock = writeLock;
    }

    public String get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public Set<String> allKeys() {
        readLock.lock();
        try {
            return map.keySet();
        } finally {
            readLock.unlock();
        }
    }

    public String put(String key, String value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public void clear() {
        writeLock.lock();
        try {
            map.clear();
        } finally {
            writeLock.unlock();
        }
    }

}
```

## Condition
### Nguyên lý hoạt động

>Interface java.util.concurrent.locks.Condition đóng gói 3 phương thức giám sát của lớp Object: wait(), notify() và notifyAll() vào trong cùng một đối tượng:

* Hành động await: tương ứng với wait
* Hành động signal: tương ứng với notify

Một instance của Condition bị ràng buộc với một Lock. Để khởi tạo một instance của Condition, chúng ta sử dụng phương thức newCondition() của Lock.

Các phương thức thường dùng của Condition:

* void await(): block thread cho đến khi Condition nhận được signal hoặc thread bị ngắt.
* boolean await(long time, TimeUnit unit): block thread cho đến khi Condition nhận được signal hoặc thread bị ngắt hoặc quá timeout.
* void signal(): đánh thức thread đang đợi.
* void signalAll(): đánh thức tất cả các thread đang đợi.

So với 3 phương thức giám sát của Object thì chúng ta có thể thấy việc sử dụng Condition mạnh mẽ và linh hoạt hơn.

Ví dụ

Chúng ta sẽ áp dụng Condition để xây dựng một concurrent Stack:

```java
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyStack {

    private static final int CAPACITY = 5;

    private Stack<Integer> stack;
    private ReentrantLock lock;
    private Condition emptyCondition;
    private Condition fullCondition;

    public MyStack(ReentrantLock lock) {
        this.stack = new Stack<>();
        this.lock = lock;
        this.emptyCondition = lock.newCondition();
        this.fullCondition = lock.newCondition();
    }

    public void push(Integer item) throws InterruptedException {
        try {
            lock.lock();

            while (stack.size() == CAPACITY) {
                // Stack is full now, can not push ...
                fullCondition.await();
            }

            stack.push(item);

            // After pushing, stack is not empty
            emptyCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public Integer pop() throws InterruptedException {
        try {
            lock.lock();

            while (stack.size() == 0) {
                // Stack is empty now, can not pop ...
                emptyCondition.await();
            }

            Integer item = stack.pop();

            // After popping, stack is not full
            fullCondition.signal();

            return item;
        } finally {
            lock.unlock();
        }
    }
}
```