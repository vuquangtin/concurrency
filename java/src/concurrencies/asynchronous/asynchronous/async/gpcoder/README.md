# Lập trình đa luồng với CompletableFuture trong Java 8

Trong bài viết Lập trình đa luồng với Callable và Future trong Java, tôi đã giới thiệu với các bạn về đối tượng Future trong Java. Khi sử dụng phương thức get() của đối tượng Future, chương trình của chúng ta sẽ bị block cho đến khi tất cả các tác vụ hoàn thành. Từ phiên bản Java 8 đã giới thiệu một tính năng mới giúp chúng ta giải quyết vấn đề này đó chính là CompletableFuture.

Trong phần tiếp theo của bài viết này, chúng ta cùng tìm hiểu về tính năng của CompletableFuture và một số ví dụ sử dụng nó.

## CompletableFuture là gì?
CompletableFuture được sử dụng cho lập trình bất đồng bộ trong Java. Lập trình bất đồng bộ là cách viết code không chặn bằng cách chạy một tác vụ trên một Thread riêng biệt khác với luồng chính và thông báo cho luồng chính về tiến trình, hoàn thành hoặc lỗi của nó.

Bằng cách này, chương trình chính không bị chặn / chờ đợi để hoàn thành nhiệm vụ và nó có thể thực thi các tác vụ khác song song. Các xử lý song song này cải thiện đáng kể hiệu suất của các chương trình của bạn.

## So sánh Future vs CompletableFuture
Một Future được sử dụng như là một tham chiếu đến kết quả của một tác vụ bất đồng bộ. Nó cung cấp một phương thức isDone() để kiểm tra xem việc tính toán có được thực hiện hay không, và phương thức get() để lấy kết quả của phép tính khi nó được thực hiện.

Như đã giới thiệu trong bài viết Lập trình đa luồng với Callable và Future trong Java , Future cung cấp các API rất tốt cho việc lập trình bất đồng bộ. Tuy nhiên nó có một vài hạn chế sau:

* Không thể quản lý kết quả trả về của Future. Ví dụ: khi main thread hoàn thành, chúng ta cũng mong muốn hoàn thành Future bằng gán cho nó một giá trị mặc định. Chúng ta không thể làm được điều này với Future.
* Không thể thực hiện thêm hành động nào đối với kết quả của Future mà không bị chặn:
* -->Future không thông báo cho về việc nó hoàn thành. Nó cung cấp phương thức get() để nhận kết quả. Tuy nhiên, phương thức này chặn main thread xử lý tác vụ khác cho đến khi kết quả có sẵn.
* --> Không thể thêm callback function khi Future hoàn thành.
* Nhiều Future không thể xử lý dây chuyền (chain). Ví dụ chương trình của chúng ta có nhiều tác vụ, khi tác vụ này hoành thành thì tác vụ khác xử lý. Với Future chúng ta không thể thực hiện:  Future1 -> Future2 -> Future3 -> …
* Không thể kết hợp nhiều Future với nhau: Giả sử chúng ta có 10 Future khác nhau, chúng ta mong muốn khi tất cả chúng hoàn thành thì sẽ thực thi tiếp 1 Future khác. Chúng ta không thể làm điều này với Future.
* Không hỗ trợ xử lý ngoại lệ (Exception).

Với Java 8, cung cấp một API mới giải quyết tất cả các giới hạn trên của Future đó chính là CompletableFuture.

CompletableFuture Implement các Interface Future và CompletionStage. Nó cung cấp nhiều các phương thức tiện lợi để: create, chain và combine nhiều Future, xử lý ngoại lệ, … Trong phần tiếp theo chúng ta sẽ lần lượt tìm hiểu các phương thức này qua các ví dụ.

```java
public class CompletableFuture<T> implements Future<T>, CompletionStage<T> {}
```

## CompletionStage là gì?
Một CompletionStage là một lời hứa (Promise). Nó hứa hẹn rằng việc tính toán cuối cùng sẽ được thực hiện.

CompletionStage cung cấp nhiều phương thức khác nhau cho phép khai báo các callback sẽ được thực hiện khi hoàn thành. Bằng cách này, chúng ta có thể xây dựng hệ thống theo cách không bị chặn (block).

## Khởi tạo CompletableFuture
### Khởi tạo CompletableFuture

```java
package asynchronous.async.gpcoder;

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
public class CompletableFuture1 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		CompletableFuture<String> completableFuture = new CompletableFuture<>();

		System.out.println("Manually complete");
		completableFuture.complete(computeSomething());

		System.out.print("Get the result: ");
		String result = completableFuture.get();
		System.out.println(result);
	}

	public static String computeSomething() {
		try {
			System.out.println("Computing ... ");
			Thread.sleep(3000);
			System.out.println("Compute completed ... ");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Future's Result";
	}
}
```

Output của chương trình:

```text
Manually complete
Computing ... 
Compute completed ... 
Get the result: Future's Result
```

Trong ví dụ trên, chúng ta thực hiện:

* Khởi tạo CompletableFuture không tham số.
* Phương thức complete() : cho phép quản lý kết quả trả về. Lưu ý: nếu không xác định kết quả trả về mà thực hiện phương thức get(), chương trình sẽ block mãi mãi.
* Phương thức get() : được sử dụng để lấy kết quả trả về. Phương thức này block main thread cho tới khi có kết quả trả về.

Ngoài phương thức get(), chúng ta có thể sử dụng phương thức getNow(valueIfAbsent) để lấy kết quả ngay lập tức (không phải trả cho tới khi hoàn thành Future): nếu có kết quả thì phương thức này sẽ trả về giá trị kết quả, nếu không có sẽ trả về giá trị mặc định được truyền vào (valueIfAbsent).

###Chạy bất đồng bộ với runAsync() và không cần kết quả trả về

Cú pháp:

```java
public static CompletableFuture<Void> runAsync(Runnable runnable);
```

Nếu muốn chạy một số tác vụ nền bất đồng bộ và không muốn trả lại bất kỳ thứ gì từ tác vụ, thì có thể sử dụng phương thức CompletableFuture.runAsync(). Phương thức này chấp nhận một đối tượng Runnable và trả về CompletableFuture<Void>.

```java
package asynchronous.async.gpcoder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class CompletableFuture2 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Run a task specified by a Runnable Object asynchronously.");
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			System.out.println("It is runnig in a separate thread than the main thread.");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			System.out.println("Completed");
		});

		System.out.println("It is also running... ");
		// Block and wait for the future to complete
		future.get();
		System.out.println("Done!!!");
	}
}

```

### Chạy bất đồng bộ với supplyAsync() và cần nhận kết quả trả về

Cú pháp:

```java
public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier);
```

* CompletableFuture.supplyAsync() hoạt động tương tự như CompletableFuture.runAsync() nhưng có kết quả trả về. 
* CompletableFuture.supplyAsync() chấp nhận một đối số Supplier và trả về CompletableFuture<T>.

```java
package asynchronous.async.gpcoder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class CompletableFuture3 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Run a task specified by a Runnable Object asynchronously.");
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			System.out.println("It is runnig in a separate thread than the main thread.");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Completed";
		});

		System.out.println("It is also running... ");
		// Block and wait for the future to complete
		System.out.println("Result: " + future.get());
		System.out.println("Done!!!");
	}
}
```

## Chuyển đổi và thao tác trên một CompletableFuture
Phương thức CompletableFuture.get() là chặn (blocking), nó đợi cho đến khi Future được trả về kết quả sau khi hoàn thành.

CompletableFuture cung cấp phương thức cho phép đính kèm một phương thức khác, phương thức này được gọi lại (callback) khi Future hoàn thành. Bằng cách đó, chúng ta sẽ không cần phải đợi kết quả, và chúng ta có thể viết logic cần được thực thi sau khi hoàn thành tương lai bên trong hàm gọi lại của chúng ta.

Một số phương thức được sử dụng để callback là: thenApply(), thenAccept() và thenRun().

### Sử dụng thenApply()
Chúng ta có thể sử dụng phương thức thenApply() để xử lý và chuyển đổi kết quả của một CompletableFuture khi nó hoàn thành. Nó lấy một Function<T, R> làm đối số. Function<T, R> là một Functional Interface chấp nhận đối số kiểu T và tạo kết quả kiểu R.

Có thể gọi thenApply() nhiều lần để tạo thành một chuỗi xử lý (chain).

```java
CompletableFuture.supplyAsync(supplier).thenApply().thenApply()....;
```
Ví dụ:

```java
package asynchronous.async.gpcoder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

class MathUtil {
	public static int times(int number, int times) {
		return number * times;
	}

	public static int squared(int number) {
		return number * number;
	}

	public static boolean isEven(int number) {
		return number % 2 == 0;
	}
}

public class CompletableFuture5 {

	public static final int NUMBER = 5;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// Create a CompletableFuture
		CompletableFuture<Integer> times2 = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return MathUtil.times(NUMBER, 2);
		});

		// Attach a callback to the Future using thenApply()
		CompletableFuture<Boolean> greetingFuture = times2.thenApply(n -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return MathUtil.squared(n);
		})
				// Chaining multiple callbacks
				.thenApply(n -> {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return MathUtil.isEven(n);
				});

		// Block and get the result of the future.
		System.out.println(greetingFuture.get()); // true
	}
}
```

### Sử dụng thenAccept() và thenRun()

Nếu không muốn trả lại bất cứ kết quả gì từ hàm gọi lại và chỉ muốn chạy một đoạn mã sau khi hoàn thành Future, thì chúng ta có thể sử dụng các phương thức thenAccept() và thenRun(). Những phương thức này là Consumer và thường được sử dụng như lần gọi lại cuối cùng trong chuỗi (chain) gọi lại.

* CompletableFuture.thenAccept() chấp nhận đối số Consumer<T> và trả về CompletableFuture<Void>. Nó có quyền truy cập vào kết quả của  CompletableFuture mà nó được đính kèm (attach).
* CompletableFuture.thenRun() chấp nhận đối số là một Runable và trả về CompletableFuture<Void>. Nó không thể truy cập kết quả của  CompletableFuture mà nó được đính kèm (attach).

Ví dụ:

```java
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
```

## Kết hợp hai CompletableFutures với nhau
### Kết hợp hai Future phụ thuộc sử dụng thenCompose()
Nếu hàm gọi lại (callback) trả về một CompletableFuture thì hãy sử dụng thenCompose() để có một kết quả phẳng (flattern) từ CompletableFuture.

Giả sử bạn muốn tìm thông tin chi tiết về người dùng từ User API và từ thông tin user có được, bạn muốn lấy Credit rating của nó ở một API service khác. Hãy xem ví dụ sau đây:

```java
package asynchronous.async.gpcoder;

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

class User {
	String userId;

	public User(String userId) {
		this.userId = userId;
	}
}

class UserService {
	public static User getUserDetails(String userId) {
		return new User(userId);
	}
}

class CreditRatingService {
	public static Double getCreditRating(User user) {
		return Double.parseDouble(user.userId);
	}
}

class ApiUtil {
	public static CompletableFuture<User> getUsersDetail(String userId) {
		return CompletableFuture.supplyAsync(() -> {
			return UserService.getUserDetails(userId);
		});
	}

	public static CompletableFuture<Double> getCreditRating(User user) {
		return CompletableFuture.supplyAsync(() -> {
			return CreditRatingService.getCreditRating(user);
		});
	}
}

public class CompletableFuture7 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// Using thenCompose()
		CompletableFuture<Double> flattened = ApiUtil.getUsersDetail("1")
				.thenCompose(user -> ApiUtil.getCreditRating(user));
		System.out.println(flattened.get()); // 1.0
	}
}
```

Đến đây một số bạn có thể sẽ thắc mắc tại sao không dùng thenApply()? Đơn giản là vì phương thức thenApply() trả về một CompletableFuture<T>, T là một giá trị nhận được từ kết quả của supplyAsync(), như trong ví dụ này nó sẽ trả về CompletableFuture<CompletableFuture<Double>>. Để có thể nhận được trực tiếp CompletableFuture<Double> chúng ta cần sử dụng phương thức thenCompose().

### Kết hợp hai Future độc lập bằng cách sử dụng thenCombine()

Trong khi thenCompose() được sử dụng để kết hợp hai Future trong đó các Future phụ thuộc lẫn nhau, thenCombine() được sử dụng khi muốn hai Future chạy độc lập và làm một cái gì đó sau khi cả hai được hoàn thành.

Ví dụ:

```java
package asynchronous.async.gpcoder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class CompletableFuture8 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Retrieve weight: ");
		CompletableFuture<Double> weightInKgFuture = CompletableFuture.supplyAsync(() -> {
			System.out.println("Retrieving weight...");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			System.out.println("Retrieving weight: Completed!");
			return 65.0;
		});

		System.out.println("Retrieve height: ");
		CompletableFuture<Double> heightInCmFuture = CompletableFuture.supplyAsync(() -> {
			System.out.println("Retrieving height...");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			System.out.println("Retrieving height: Completed!");
			return 177.8;
		});

		System.out.println("Calculating BMI: ");
		CompletableFuture<Double> combinedFuture = weightInKgFuture.thenCombine(heightInCmFuture,
				(weightInKg, heightInCm) -> {
					System.out.println("Calculating BMI: Completed!");
					Double heightInMeter = heightInCm / 100;
					return weightInKg / (heightInMeter * heightInMeter);
				});

		System.out.println("Your BMI is - " + combinedFuture.get());
	}
}

```

## Kết hợp nhiều CompletableFutures với nhau
Chúng ta sử dụng thenCompose() và thenCombine() để kết hợp hai Future với nhau. Nếu bạn muốn kết hợp nhiều số lượng Future hơn nữa, chúng ta có thể sử dụng phương thức CompletableFuture.allOf() hoặc CompletableFuture.anyOf().

### Sử dụng CompletableFuture.allOf()
CompletableFuture.allOf() được sử dụng khi cần thực hiện một danh sách các tác vụ song song và làm điều gì đó sau khi tất cả chúng hoàn tất.

Giả sử chúng ta muốn tải xuống nội dung của 100 trang web khác nhau của trang web, khi download xong cần thông báo số lượng trang đã được download. Chúng ta có thể thực hiện thao tác này bất đồng bộ để tiết kiệm thời gian.

Code xử lý như sau:

```java
package asynchronous.async.gpcoder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class CompletableFuture9 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// A list of 100 web page links
		List<String> webPageLinks = Arrays.asList( //
				"https://www.google.com.vn/", "https://vnexpress.net/", "https://gpcoder.com/");

		// Download contents of all the web pages asynchronously
		List<CompletableFuture<String>> pageContentFutures = webPageLinks.stream()
				.map(webPageLink -> downloadWebPage(webPageLink)).collect(Collectors.toList());

		// Create a combined Future using allOf()
		CompletableFuture<Void> allFutures = CompletableFuture
				.allOf(pageContentFutures.toArray(new CompletableFuture[pageContentFutures.size()]));

		// When all the Futures are completed, call `future.join()` to get their
		// results
		// and collect the results in a list
		CompletableFuture<List<String>> allPageContentsFuture = allFutures.thenApply(v -> {
			return pageContentFutures.stream().map(pageContentFuture -> pageContentFuture.join())
					.collect(Collectors.toList());
		});

		// Count the number of web pages having the "CompletableFuture" keyword.
		CompletableFuture<Long> countFuture = allPageContentsFuture.thenApply(pageContents -> {
			return pageContents.stream().filter(pageContent -> pageContent.contains("CompletableFuture")).count();
		});

		System.out.println("Number of Web Pages having CompletableFuture keyword: " + countFuture.get());
	}

	public static CompletableFuture<String> downloadWebPage(String pageLink) {
		return CompletableFuture.supplyAsync(() -> {
			System.out.println("Downloading: " + pageLink);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Code to download and return the web page's content
			return "CompletableFuture Completed";
		});
	}
}
```

Giải thích:

* Đầu tiên, chúng ta tạo phương thức downloadWebPage() : để download nội dung của 1 trang web. Phương thức này được xử lý bất đồng bộ sử dụng CompletableFuture.
* Chúng ta lặp qua danh sách các trang webPageLinks và gọi phương thức download bất đồng bộ cho mỗi trang. Kết quả downlaod được lưu vào danh sách Future pageContentFutures.
* Tiếp theo chúng ta kết hợp tất cả các Future vào một Future khác là allFutures thông qua phương thức CompletableFuture.allOf().
* Sau đó, tạo một callback để xử lý kết quả khi tất cả Future đã hoàn thành thông qua phương thức allFutures.thenApply().
* Do phương thức CompletableFuture.allOf() trả kết quả là CompletableFuture<Void>, để có thể lấy được kết quả chúng ta cần duyệt qua danh sách kết quả Future ban đầu pageContentFutures và gọi phương thức future.join() để lấy kết quả. Danh sách kết quả được lưu trong một Future allPageContentsFuture.
* Tiếp tục tạo một callback để nhận thông báo khi đã lấy được danh sách kết quả allPageContentsFuture. Khi nhận được thông báo, chúng ta duyệt qua tất cả các phần tử để đếm số lượng các trang đã được download.

Lưu ý: Phương thức future.join() tương tự như phương thức future.get(). Tuy nhiên nó có chút khác biệt:

* Phương thức future.join() sẽ trả về giá trị kết quả khi hoàn thành hoặc ném một ngoại lệ (unchecked exception) nếu CompletableFuture throw exception.
* Phương thức future.get() chờ đợi cho tới khi Thread hoàn thành, và sau đó trả về kết quả của nó.

### Sử dụng CompletableFuture.anyOf()
CompletableFuture.anyOf() được sử dụng khi cần thực hiện một danh sách các tác vụ song song và làm điều gì đó khi bất kỳ tác vụ nào đó hoàn thành.

Ví dụ:

```java
package asynchronous.async.gpcoder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class CompletableFuture10 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			System.out.println("Future1 running ...");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Result of Future 1";
		});

		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			System.out.println("Future2 running ...");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Result of Future 2";
		});

		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
			System.out.println("Future3 running ...");
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Result of Future 3";
		});

		System.out.println("Combine futures with anyOf");
		CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);

		System.out.println(anyOfFuture.get());
	}
}
```

Trong ví dụ trên, phương thức anyOfFuture() được hoàn thành khi bất kỳ Future nào được hoàn thành. Phương thức future2 được hoàn thành sớm nhất và kết quả nhận được là Result of Future 2 .

## Hủy bỏ CompletableFuture như thế nào?
Trong một vài trường hợp, chúng ta muốn hủy bỏ một CompletableFuture đang chạy. Khi đó, chúng ta có thể đặt một Flag để kiểm tra hủy bỏ CompletableFuture như sau:

```java
package asynchronous.async.gpcoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
 
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class CompletableFuture14 {
 
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<String> list = new ArrayList<>();
 
        AtomicBoolean cancelled = new AtomicBoolean(false);
 
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            while (true) {
                if (cancelled.get()) {
                    System.out.println("cancelled");
                    return list.size();
                }
                if (!list.isEmpty()) {
                    return list.size();
                }
            }
        });
 
        TimeUnit.SECONDS.sleep(3);
 
        try {
            future.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            cancelled.set(true);
        }
        System.out.println(future.get());
    }
}
```

## Sử dụng Executor với CompletableFuture
Cho đến bây giờ tất cả các callbacks của chúng ta đã được thực hiện trên cùng một luồng với Thread gọi chúng. Chúng ta có thể tạo một Executor và truyền nó tới các phương thức có tên với hậu tố là Async như: runAsync() , supplyAsync() , … để cho phép chúng thực hiện các tác vụ trong một Thread Pool độc lập.

Nếu không xác định Executor cho các phương thức Async, mặc định CompletableFuture sử dụng ForkJoinPool.commonPool() để thực thi các Thread độc lập.

Ví dụ:

```java
package asynchronous.async.gpcoder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class CompletableFuture4 {

	public static final int CORE_POOL_SIZE = 0;

	public static final int MAXIMUM_POOL_SIZE = 10;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// create the tracking thread pool with 10 threads
		final AtomicLong count = new AtomicLong(0);
		final ThreadPoolExecutor pool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread t = new Thread(r);
						String threadName = "gp-" + count.getAndIncrement();
						t.setName(threadName);
						return t;
					}
				});

		CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
			System.out.println("Execute supplyAsync");
			sleep(1);
			return "Welcome to gpcoder.com";
		}, pool).thenApplyAsync(msg -> {
			System.out.println("Execute thenApplyAsync");
			sleep(2);
			return msg.length();
		}, pool).thenAcceptAsync(n -> {
			System.out.println("Execute thenAcceptAsync: " + n);
			sleep(2);
		}, pool).thenRunAsync(() -> {
			System.out.println("Done!!!");
			sleep(2);
		});

		future.get();

		System.out.println("----------------------------------");
		System.out.println("Total Completed Task Count = " + pool.getCompletedTaskCount());
		System.out.println("Total Task Count = " + pool.getTaskCount());
		System.out.println("----------------------------------");
	}

	private static void sleep(int second) {
		try {
			TimeUnit.SECONDS.sleep(second);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

```

Để tiện theo dõi, trong ví dụ trên tôi sử dụng ThreadPoolExecutor để có thể giám sát được số lượng task được thực thi trong Thread Pool. Như bạn thấy, có 3 phương thức được gọi Async nên ta có tổng số lượng task được thực thi trong Thread Pool là 3.

## Xử lý ngoại lệ CompletableFuture
Giả sử chúng ta có CompletableFuture Chain như sau:

```java
CompletableFuture.supplyAsync(() -> {
    // Code which might throw an exception
    return "Some result";
}).thenApply(result -> {
    return "processed result";
}).thenApply(result -> {
    return "result after further processing";
}).thenAccept(result -> {
    // do something with the final result
});
```

Khi có ngoại lệ xảy ra tại bất kỳ phương thức nào, thì callback xử lý kế tiếp sẽ không được gọi và Future sẽ giải quyết với trường hợp ngoại lệ xảy ra.

CompletableFuture cung cấp một vài cách xử lý ngoại lệ như sau:

### Sử dụng phương thức callback exceptionally()
Sử dụng exceptionally() để xử lý ngoại lệ khi có lỗi xảy ra tại bất kỳ phương thức nào trong CompletableFuture Chain.

Ví dụ:

```java
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

public class CompletableFuture11 {
 
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<String> maturityFuture = 
                CompletableFuture.supplyAsync(() -> -1).thenApply(age -> {
            if (age < 0) { throw new IllegalArgumentException("Age can not be negative"); } if (age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        }).exceptionally(ex -> {
            System.out.println("Oops! We have an exception - " + ex.getMessage());
            return "Unknown!";
        });
        System.out.println("Maturity : " + maturityFuture.get());
    }
}
```

### Sử dụng phương thức xử lý ngoại lệ chung handle()
Sử dụng handle() để xử lý ngoại lệ hoặc kết quả của CompletableFuture. Phương thức này luôn được gọi, cho dù có ngoại lệ xảy ra hay không

Ví dụ:

```java
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
```

### Sử dụng phương thức xử lý ngoại lệ chung whenComplete()
Phương thức whenComplete() cũng tương tự handle(), ngoại trừ nó không cung cấp kết quả trả về.

Ví dụ:

```java
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

public class CompletableFuture13 {
 
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<String> maturityFuture = 
                CompletableFuture.supplyAsync(() -> -1).thenApply(age -> {
            if (age < 0) { throw new IllegalArgumentException("Age can not be negative"); } if (age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        }).whenComplete((res, ex) -> {
            if (ex != null) {
                System.out.println("Oops! We have an exception - " + ex.getMessage());
            }
        });
    }
}
```

