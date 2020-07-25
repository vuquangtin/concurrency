# Tìm hiểu lập trình bất đồng bộ với CompletableFuture trong java qua ví dụ.
## Các method cơ bản trong CompletableFuture
>CompletableFuture được sử dụng cho lập trình bất đồng bộ trong Java. Bằng cách này, chương trình chính không bị chặn / chờ đợi để hoàn thành nhiệm vụ và nó có thể thực thi các tác vụ khác song song.

## So sánh CompletableFuture với Future
Future ra mắt từ java 5 và nó cung cấp các API rất tốt cho việc lập trình bất đồng bộ. Tuy nhiên nó có một vài hạn chế sau:

* Không thể quản lý kết quả trả về của Future. Ví dụ: khi một main thread đã hoàn thành xong việc, chúng ta cũng mong muốn Future trả ra luôn kết quả nếu chưa xong thì lấy mặc định. Nhưng chúng ta không thể làm được điều này với Future.
* Không thể tạo các xử lý chain với Future.
* Các Future không có sự tương tác lẫn nhau. VD khi có 5 Future được hoàn thành chúng ta muốn xử lý kết quả khi cả 5 cũng xong.
Không hỗ trợ xử lý exception
* Từ Java 8 chúng ta có thể sử dụng CompletableFuture để xử lý các hạn chế trên.

Từ Java 8 chúng ta có thể sử dụng CompletableFuture để xử lý các hạn chế trên.

## complete() và Get()
Ví dụ khi bạn muốn vừa nấu cơm vừa rang thịt:

```java
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
```

Output:

```text
Làm bữa tối
Rang thịt ... 
Nấu cơm ... 
Rang xong
Ăn tối với: Thịt rang và Gạo
```

Sau khi rang thịt xong bạn muốn lấy cơm ra ăn luôn nhưng vì nấu cơm mất nhiều thời gian hơn nên chỉ có gạo mà thôi

Trong ví dụ trên, chúng ta đã thực hiện:

* Method complete() : Truyền vào công việc cần hoàn thành.
* Method get() : được sử dụng để lấy kết quả trả về. Phương thức này block main thread cho tới khi có kết quả trả về.
* Lưu ý: nếu không dùng complete() để xác định kết quả trả về mà thực hiện phương thức get() thì chương trình sẽ bị block mãi mãi.
* Ngoài phương thức get(), chúng ta có thể sử dụng phương thức getNow(valueIfAbsent) để lấy kết quả ngay lập tức (không phải chờ cho tới khi hoàn thành Future): nếu có kết quả thì phương thức này sẽ trả về giá trị kết quả, nếu không có sẽ trả về giá trị mặc định được truyền vào

## Chạy bất đồng bộ với runAsync() và supplyAsync
CompletableFuture.supplyAsync() và CompletableFuture.runAsync() đều hoạt động tương tự nhau nhưng CompletableFuture.supplyAsync() thì có kết quả trả về.

### runAsync()

Nếu muốn chạy một số task bất đồng bộ và không muốn trả về kết quả, thì có thể sử dụng phương thức CompletableFuture.runAsync(). Phương thức này chấp nhận một đối tượng Runnable và trả về CompletableFuture<Void>

Ví dụ một processor cần xử lý các đơn hàng đã bán được:
* Kiểm tra đơn hàng ở kho nào rồi update lại số lượng của hàng trong kho.
* Tính tiền cho telesale đã bán được hàng.


```java
public class HandleOffer {

	final static int TIME_TO_UPDATE_STOREAGE = 5000;
	final static int TIME_TO_UPDATE_TELESALE = 5000;

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		System.out.println("Start processor");
		long startTime = System.currentTimeMillis();
		CompletableFuture<Void> updateAmountInStorage = CompletableFuture.runAsync(() -> {
			System.out.println("updateStoreage is running in a other thread.");
			updateStoreage(TIME_TO_UPDATE_STOREAGE);
			System.out.println("Done task 1!!!");
		});

		// tính tiền cho telesale
		System.out.println("updateTelesale is running... in main thread");
		updateTelesale(TIME_TO_UPDATE_TELESALE);
		System.out.println("Hoàn thành process trong :" + (System.currentTimeMillis() - startTime) + "ms");
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
```

Output:
```text
Start processor
updateTelesale is running... in main thread
updateStoreage is running in a other thread.
Đã cộng tiền cho telesale thành công 
Đã giảm số lượng hàng trong kho thành công 
Hoàn thành process trong :5087ms
```

Ở ví dụ trên việc update storeage hay update telesale trong model đều mất mỗi việc 5s nhưng khi chạy bất đồng bộ thì thời gian hoàn thành chỉ là 5087ms giống như làm 1 việc vậy.

### supplyAsync()
Cách hoạt động của supplyAsync() cũng giống hệt runAsync() nhưng cần truyền vào đối số Supplier và kết quả trả về là một CompletableFuture<T>.

Vẫn ví dụ về xử lý đơn hàng đã bán được ở trên nhưng lần này có thêm 1 việc sau khi cập nhập lại số lượng hàng trong kho đó là chúng ta phải thông báo lại số lượng hàng trong kho.

```java
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
```

Output:
```text
Start processor
updateTelesale is running... in main thread
updateStoreage is running in a other thread.
Đã cộng tiền cho telesale thành công 
Đã giảm số lượng hàng trong kho thành công 
Số lượng hàng còn trong kho :0
Hoàn thành process trong :5087ms
```

## Chuyển đổi và thao tác trên CompletableFuture

### thenApply(), THENACCEPT() VÀ THENRUN()
Chúng ta có thể sử dụng phương thức thenApply() để xử lý và chuyển đổi kết quả của một CompletableFuture khi nó hoàn thành.

Nếu không muốn trả ra kết quả từ callback mà chỉ muốn chạy xử lý sau khi hoàn thành Future, thì chúng ta có thể sử dụng các phương thức thenAccept() và thenRun().

* thenApply() cần truyền vào một callback Function<U, T> trả ra kiểu dữ liệu U và nhận vào T.
* thenAccept() cần truyền vào Consumer<T> và trả về CompletableFuture<Void>. Nó có thể lấy kết quả của CompletableFuture gọi nó.
* thenRun() cần truyền vào Runable và trả về CompletableFuture<Void>. Nó không thể lấy kết quả của CompletableFuture gọi nó.

Tiếp tục với bài toán đơn hàng, Vậy là sau khi kiểm tra hàng đã được đóng gói và sẵn sàng giao hàng chúng ta cần:

* Thông báo với shiper đến lấy hàng.
* Gửi tin nhắn tới khách hàng chuấn bị nhận hàng.

```java
public class HandleDelivery {

	final static boolean READY_DELIVERY = true;
	final static int TIME_TO_CHECK_READY_DELIVERY = 5000;
	final static int TIME_TO_CALL_SHIPPER = 5000;
	final static int TIME_TO_SEND_MESSAGE = 5000;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Start processor");
		long startTime = System.currentTimeMillis();
		CompletableFuture<Boolean> checkReadyDelivery = CompletableFuture.supplyAsync(() -> {
			execute("checkReadyDelivery", TIME_TO_CHECK_READY_DELIVERY);
			return READY_DELIVERY;
		});

		CompletableFuture<String> callShipper = checkReadyDelivery.thenApply(readyDelivery -> {
			if (readyDelivery) {
				execute("callShipper", TIME_TO_CALL_SHIPPER);
				return "thành công";
			} else {
				return "thất bại";
			}
		});
		CompletableFuture<Void> sendMessageToCustomer = checkReadyDelivery.thenAccept(readyDelivery -> {
			if (readyDelivery) {
				execute("sendMessageToCustomer", TIME_TO_SEND_MESSAGE);
			}
		});
		String status = callShipper.get();
		System.out.println("Gọi shipper " + status);
		System.out.println("Hoàn thành process trong :" + (System.currentTimeMillis() - startTime) + "ms");
	}

	static void execute(String name, int time) {
		try {
			Thread.sleep(time);
			System.out.println("done task : " + name);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}
```

Output:

```text
Start processor
done task : checkReadyDelivery
done task : sendMessageToCustomer
done task : callShipper
Gọi shipper thành công
Hoàn thành process trong :15103 ms
```

Có thể gọi thenApply(), thenAccept() và thenRun() nhiều lần để tạo thành một chuỗi xử lý (chain).

### thenApplyAsync(), thenAcceptAsync() VÀ thenRunAsync()
Ở bài toán delivery trên thì việc checkReadyDelivery cần xử lý trước các việc còn lại vì đó là điều kiện của các việc sau. Nhưng callShipper và sendMessage thì không cần phụ thuộc vào nhau vậy chúng nên xử lý đồng thời 2 việc này sau khi có kết quả từ checkReadyDelivery.

Thực tế thì các method trong CompletableFuture nếu có hậu tố Async thì đều hỗ trợ bất đồng bộ.

Vậy để xử lý đồng bộ bài toàn delivery trên ta có thể dùng thenApplyAsync(), thenAcceptAsync() và thenRunAsync().

```java
public class HandleDelivery2 {

    final static boolean READY_DELIVERY = true;
    final static int TIME_TO_CHECK_READY_DELIVERY = 5000;
    final static int TIME_TO_CALL_SHIPPER = 5000;
    final static int TIME_TO_SEND_MESSAGE = 5000;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Start processor");
        long startTime = System.currentTimeMillis();
        CompletableFuture<Boolean> checkReadyDelivery = CompletableFuture.supplyAsync(() -> {
            execute("checkReadyDelivery", TIME_TO_CHECK_READY_DELIVERY);
            return READY_DELIVERY;
        });

        CompletableFuture<String> callShipper = checkReadyDelivery.thenApplyAsync(readyDelivery -> {
            if (readyDelivery) {
                execute("callShipper", TIME_TO_CALL_SHIPPER);
                return "thành công";
            } else {
                return "thất bại";
            }
        });
        CompletableFuture<Void> sendMessageToCustomer = checkReadyDelivery.thenAcceptAsync(readyDelivery -> {
            if (readyDelivery) {
                execute("sendMessageToCustomer", TIME_TO_SEND_MESSAGE);
            }
        });
        String status = callShipper.get();
        System.out.println("Gọi shipper " + status);
        System.out.println("Hoàn thành process trong :" + (System.currentTimeMillis() - startTime) + "ms");
    }

    static void execute(String name, int time) {
        try {
            Thread.sleep(time);
            System.out.println("done task : " + name);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
```

Output:

```text
Start processor
done task : checkReadyDelivery
done task : callShipper
done task : sendMessageToCustomer
Gọi shipper thành công
Hoàn thành process trong :10086 ms
```

Vậy là thời gian khi sử lý bất đồng bộ đã giảm chỉ còn 10s.

## Kết hợp hai CompletableFutures với nhau

### thenCompose()
Khi muốn 2 CompletableFutures phụ thuộc vào nhau ta sử dụng thenCompose()

Ví dụ sau khi bạn muốn lấy thông tin của một quyển sách từ 1 api, và từ thông tin vừa lâý được bạn cần tính giá tiền của quyển sách từ api khác.

```java
class Book {
    String bookId;

    public Book(String bookId) {
        this.bookId = bookId;
    }
}

class BookService {
    public static Book getBookDetails(String bookId) {
        return new Book(bookId);
    }
}

class CreditBookService {
    public static Double getCreditRating(Book book) {
        return Double.parseDouble(book.bookId);
    }
}

class Util {
    public static CompletableFuture<Book> getBooksDetail(String bookId) {
        return CompletableFuture.supplyAsync(() -> {
            return BookService.getBookDetails(bookId);
        });
    }

    public static CompletableFuture<Double> getCredit(Book book) {
        return CompletableFuture.supplyAsync(() -> {
            return CreditBookService.getCreditRating(book);
        });
    }
}

public class CompletableFuture6 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Using thenCompose()
        CompletableFuture<Double> flattened = Util.getBooksDetail("1")
                .thenCompose(book ->
                        Util.getCredit(book)
                );
        System.out.println(flattened.get());
    }
}
```

Nhiều người sẽ thấy việc dùng thenCompose() giống với thenApply() vậy tại sao ko dùng luôn thenApply()? Đơn giản là vì phương thức thenApply() trả về một CompletableFuture<T>, T là một giá trị nhận được từ kết quả của supplyAsync(), như trong ví dụ này nó sẽ trả về CompletableFuture<CompletableFuture<Double>>. Để có thể nhận được trực tiếp CompletableFuture<Double> chúng ta cần sử dụng phương thức thenCompose().

### thenCombine

Nếu muốn 2 completableFuture chạy độc lập sau đó cần xử lý chung đồng thời 1 việc nào đó ta sẽ sử dụng thenCombine()

VD Bạn muốn làm một báo cáo về tỉ lệ “mua hàng/xem hàng” chúng ta cần tính toán hoặc lấy về dữ liệu về số lượt truy cập vào xem hàng, đồng thời lấy về thông tin số lượt mua hàng. Nhưng để tính được tỉ lệ thì phải cần cả 2 kết quả:

```java
public class ReportBuyTimesPerViews {

	final static int TIME_TO_GET_BUY_TIMES = 5000;
	final static int TIME_TO_GET_VIEWS = 5000;
	final static int TIME_TO_COMPUTE = 5000;
	final static int VIEWS = 1000;
	final static int BUY_TIMES = 69;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Start processor");
		long startTime = System.currentTimeMillis();
		CompletableFuture<Integer> getBuyTimes = CompletableFuture.supplyAsync(() -> {
			execute("getBuyTimes", TIME_TO_GET_BUY_TIMES);
			return BUY_TIMES;
		});

		CompletableFuture<Integer> getViews = CompletableFuture.supplyAsync(() -> {
			execute("getViews", TIME_TO_GET_VIEWS);
			return VIEWS;
		});

		CompletableFuture<Double> computeBuyTimesPerViews = getBuyTimes.thenCombine(getViews, (buyTimes, views) -> {
			execute("computeBuyTimesPerViews", TIME_TO_GET_VIEWS);
			return (double) buyTimes / views;
		});
		Double result = computeBuyTimesPerViews.get();
		System.out.println("Tỉ lệ : " + result);
		System.out.println("Hoàn thành process trong :" + (System.currentTimeMillis() - startTime) + "ms");
	}

	static void execute(String name, int time) {
		try {
			Thread.sleep(time);
			System.out.println("done task : " + name);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}
```

Output:
```text
Start processor
done task : getBuyTimes
done task : getViews
done task : computeBuyTimesPerViews
Tỉ lệ : 0.069
Hoàn thành process trong :10180 ms
```

### kết hợp nhiều completableFuture với allOf() và anyOf()
CompletableFuture.anyOf() được sử dụng khi cần thực hiện một danh sách các CompletableFuture song song và nó sẽ hoàn thành khi bất kỳ task nào trong danh sách hoàn thành.

Ví dụ với một ứng dụng gọi xe taxi, khi khách hàng đặt xe chúng ta sẽ liên hệ đồng thời tới nhiều xe taxi trong hãng ở gần đó, khi 1 tài xế nhanh nhất phản hồi nhất sẽ được kết nối và hủy các liên hệ khác.

```java
public class CallTaxi {

	final static int TIME_TO_CALL_A = 3000;
	final static int TIME_TO_CALL_B = 4000;
	final static int TIME_TO_CALL_C = 2000;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Start processor");
		long startTime = System.currentTimeMillis();
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			System.out.println(" Calling tài xế A ...");
			try {
				Thread.sleep(TIME_TO_CALL_A);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Tài xế A đã phản hồi";
		});

		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			System.out.println(" Calling tài xế B ...");
			try {
				Thread.sleep(TIME_TO_CALL_B);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Tài xế B đã phản hồi";
		});

		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
			System.out.println(" Calling tài xế C ...");
			try {
				Thread.sleep(TIME_TO_CALL_C);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Tài xế C đã phản hồi";
		});

		CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);
		System.out.println(anyOfFuture.get());
		System.out.println("Hoàn thành process trong :" + (System.currentTimeMillis() - startTime) + "ms");
	}
}
```

Output:
```text
Start processor
 Calling tài xế A ...
 Calling tài xế B ...
 Calling tài xế C ...
Tài xế C đã phản hồi
Hoàn thành process trong :2073 ms
```

Tiếp theo, CompletableFuture.allOf() được sử dụng khi cần thực hiện một danh sách các tác vụ song song và làm điều gì đó sau khi tất cả chúng hoàn tất.

Ví dụ để tăng tốc độ download file chúng ta nên chia file thành cách phần nhỏ rồi thực hiện download từng phần đến khi tất cả các phần đều hoàn thành thì tiến hành gộp thành file hoàn chỉnh.

```java
public class DownloadMultiplePartFile {

	final static int TIME_TO_DOWNLOAD = 5000;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Start processor");
		long startTime = System.currentTimeMillis();

		List<String> downloadParts = Arrays.asList("Tập 1", "Tập 2", "Tập 3", "Tập 4", "Tập 5", "Tập 6", "Tập 7");

		// Tạo 1 list các Future
		List<CompletableFuture<String>> partContentFutures = downloadParts.stream()
				.map(webPageLink -> download(webPageLink, TIME_TO_DOWNLOAD)).collect(Collectors.toList());

		// Tạo riêng 1 Future
		CompletableFuture<String> partSpecial = CompletableFuture.supplyAsync(() -> {
			System.out.println("Downloading: Tập đặc biệt");
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Done: Tập đặc biệt");
			return "Tập đặc biệt";
		});

		partContentFutures.add(partSpecial);

		CompletableFuture<Void> allFile = CompletableFuture
				.allOf(partContentFutures.toArray(new CompletableFuture[partContentFutures.size()]));

		CompletableFuture<String> mergeFile = allFile.thenApply(parts -> {// get
																			// all
																			// file
			return partContentFutures.stream().map(pageContentFuture -> pageContentFuture.join())
					.collect(Collectors.joining("+"));
		});
		System.out.println("Kết quả: " + mergeFile.get());
		System.out.println("Hoàn thành process trong :" + (System.currentTimeMillis() - startTime));
	}

	public static CompletableFuture<String> download(String namePart, int time) {
		return CompletableFuture.supplyAsync(() -> {
			System.out.println("Downloading: " + namePart);
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Done: " + namePart);
			return namePart;
		});
	}
}
```

Output
```text
Start processor
Downloading: Tập 2
Downloading: Tập 1
Downloading: Tập 3
Done: Tập 2
Downloading: Tập 4
Done: Tập 1
Downloading: Tập 5
Done: Tập 3
Downloading: Tập 6
Done: Tập 4
Downloading: Tập 7
Done: Tập 5
Downloading: Tập đặc biệt
Done: Tập 6
Done: Tập 7
Done: Tập đặc biệt
Kết quả: Tập 1+Tập 2+Tập 3+Tập 4+Tập 5+Tập 6+Tập 7+Tập đặc biệt
Hoàn thành process trong :16440
```

Ví dụ trên các task được hoàn thành vào thời điểm khác nhau nhưng vẫn cùng phải đợi task chậm nhất để hoàn thành việc xử lý cuối cùng.

Dù đã nhanh hơn so với xử lý đồng bộ (cần 40s cho 8 file) nhưng như bạn thấy mỗi file cần 5s để download nhưng chúng ta cần tới ~16s nghĩa là không phải cả 8 file được download cùng 1 lúc vậy nên tối ưu như thế nào?

## Sử dụng Executor với CompletableFuture
Với các method có hậu tố Async trong CompletableFuture chúng ta có thể tạo một Executor và truyền nó vào phương thức như trên để cho phép chúng thực hiện các tác vụ trong một Thread Pool độc lập. Nếu không truyền thì mặc định CompletableFuture sử dụng ForkJoinPool.commonPool() để thực thi các Thread độc lập.

Vậy để trả lời câu hỏi ở cuối phần trước chúng ta cần tự cấu hình một Executor có nhiều thread hơn để download file.

```java
public class DownloadMultiplePartFileWithExecutor {

	final static int TIME_TO_DOWNLOAD = 5000;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Start processor");
		long startTime = System.currentTimeMillis();

		List<String> downloadParts = Arrays.asList("Tập 1", "Tập 2", "Tập 3", "Tập 4", "Tập 5", "Tập 6", "Tập 7");

		// Tạo 1 list các Future
		List<CompletableFuture<String>> partContentFutures = downloadParts.stream()
				.map(webPageLink -> download(webPageLink, TIME_TO_DOWNLOAD)).collect(Collectors.toList());

		// Tạo riêng 1 Future
		CompletableFuture<String> partSpecial = CompletableFuture.supplyAsync(() -> {
			System.out.println("Downloading: Tập đặc biệt");
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Done: Tập đặc biệt");
			return "Tập đặc biệt";
		});

		partContentFutures.add(partSpecial);

		CompletableFuture<Void> allFile = CompletableFuture
				.allOf(partContentFutures.toArray(new CompletableFuture[partContentFutures.size()]));

		CompletableFuture<String> mergeFile = allFile.thenApply(parts -> {// get
																			// all
																			// file
			return partContentFutures.stream().map(pageContentFuture -> pageContentFuture.join())
					.collect(Collectors.joining("+"));
		});
		System.out.println("Kết quả: " + mergeFile.get());
		System.out.println("Hoàn thành process trong :" + (System.currentTimeMillis() - startTime));
	}

	public static CompletableFuture<String> download(String namePart, int time) {
		final AtomicLong count = new AtomicLong(0);
		final ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 10, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread t = new Thread(r);
						String threadName = "thread-" + count.getAndIncrement();
						t.setName(threadName);
						return t;
					}
				});

		return CompletableFuture.supplyAsync(() -> {
			System.out.println("Downloading: " + namePart);
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Done: " + namePart);
			return namePart;
		}, pool);
	}
}
```

Output:

```text
Start processor
Downloading: Tập 1
Downloading: Tập 2
Downloading: Tập 3
Downloading: Tập 4
Downloading: Tập 5
Downloading: Tập 6
Downloading: Tập 7
Downloading: Tập đặc biệt
Done: Tập 1
Done: Tập 2
Done: Tập 4
Done: Tập 5
Done: Tập 6
Done: Tập 3
Done: Tập 7
Done: Tập đặc biệt
Kết quả: Tập 1+Tập 2+Tập 3+Tập 4+Tập 5+Tập 6+Tập 7+Tập đặc biệt
Hoàn thành process trong :6305
```

Vậy là đến đây thì việc download file chỉ còn ~ 6s bởi vì mỗi phần của file đã được chạy 1 luồng riêng.

## Xử lý Exception trong CompletableFuture
### exceptionally()
Sử dụng exceptionally() để xử lý exception khi có bất cứ exeption nào được throw trong tất cả các phương thức trong CompletableFuture Chain.

Ví dụ 1 CompletableFuture về xử lý nhập tuổi của người dùng.

```java
public class HandleException {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFuture<String> handleAge = CompletableFuture.supplyAsync(() -> -1).thenApply(age -> {
			if (age < 0) {
				throw new IllegalArgumentException("Tuổi không thể âm!");
			}
			if (age > 18) {
				return "Nguoi lon";
			} else {
				return "Tre em";
			}
		}).exceptionally(ex -> {
			System.out.println("Bạn đã nhập sai tuổi - " + ex.getMessage());
			return "Chưa rõ tuổi";
		});
		System.out.println("Maturity : " + handleAge.get());
	}
}
```

Output:
```text
Bạn đã nhập sai tuổi - java.lang.IllegalArgumentException: Làm gì có ai sống lâu thế!
Kết quả : Chưa rõ tuổi
```

Khi ở trong chain có lỗi xảy ra! chúng ta xử lý bằng cách cho hiển thị đúng lỗi, và vẫn trả về kết quả “Chưa rõ tuổi”

### handle() và whenComplete()
handle() luôn được gọi, cho dù có exception xảy ra hay không, nên phương thức này để xử lý ngoại lệ hoặc kết quả của CompletableFuture.

Ví dụ

```java
public class HandleException2 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFuture<String> handleAge = CompletableFuture.supplyAsync(() -> 1).thenApply(age -> {
			if (age < 0) {
				throw new IllegalArgumentException("Tuổi không thể âm!");
			}
			if (age > 150) {
				throw new IllegalArgumentException("Làm gì có ai sống lâu thế!");
			}
			if (age > 18) {
				return "Nguoi lon";
			} else {
				return "Tre em";
			}
		}).handle((res, ex) -> {
			if (IllegalArgumentException.class.isInstance(ex)) {
				System.out.println("Bạn đã nhập sai tuổi - " + ex.getMessage());
				return "Chưa rõ tuổi";
			}
			return res;
		});
		System.out.println("Kết quả : " + handleAge.get());
	}
}

```

### whenComplete()
Phương thức whenComplete() cũng tương tự handle(), nhưng nó không có quả trả về.

```java
public class HandleException3 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFuture<String> handleAge = CompletableFuture.supplyAsync(() -> -1).thenApply(age -> {
			if (age < 0) {
				throw new IllegalArgumentException("Tuổi không thể âm");
			}
			if (age > 18) {
				return "Nguoi lon";
			} else {
				return "Tre em";
			}
		}).whenComplete((res, ex) -> {
			if (ex != null) {
				System.out.println("Đã xảy ra lỗi - " + ex.getMessage());
			}
		});
	}
}
```

@see <a href="https://luc.news.blog/2019/10/01/lap-trinh-da-luong-voi-completablefuture-trong-java-8/">https://luc.news.blog/2019/10/01/lap-trinh-da-luong-voi-completablefuture-trong-java-8/</a>
