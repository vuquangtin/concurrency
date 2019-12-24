# Tìm hiểu ReactiveX
Đây là những kiến thức mà mình đã tìm hiểu được về ReactiveX. Mặc dù chưa đi sâu hết về Rx nhưng cũng là những kiến thức tổng quan mà hầu hết chúng ta cần nắm được. Hy vọng bài đọc này sẽ hữu ích với các bạn.
## I. Giới thiệu ReactiveX

>ReactiveX là một thư viện giúp xử lí các tác vụ không đồng bộ, các sự kiện cơ bản bằng cách sử dụng tuần tự Observable

ReactiveX là sự kết hợp của 3 mô hình Observer, Interator và Functional programming

* Mô hình Observer pattern: Observer pattern là một software design pattern mà trong đó mỗi Object được gọi là một subject, duy trì một danh sách những object phụ thuộc vào nó được gọi là observer. Và nó sẽ thông báo khi có bất cứ sự thay đổi nào trong trạng thái của object.
* Iterator pattern : truy cập những phần tử của một đối tượng một cách tuần tự mà không làm lộ cách thức thể hiện của chúng.
* Functional Programming : giảm thiểu tối đã sự phụ thuộc các giá trị đầu ra, chỉ phụ thuộc vào tham số truyền vào.

ReactiveX cho phép lập trình bất đồng bộ với các luồng quan sát được

Nó đôi khi được gọi là “ Functional Reactive Programming” (lập trình phản ứng chức năng) nhưng đó là một sự nhầm lẫn. ReactiveX có thể hoạt động và nó có thể phản ứng nhưng lập trình phản ứng chức năng lại là một thể loại khác. Một điểm khác biệt chính là Functional Reactive Programming hoạt động trên các giá trị thay đổi liên tục theo thời gian, trong khi ReactiveX hoạt động trên các giá trị rời rạc được phát ra theo thời gian.

## II. Tại sao sử dụng Observer pattern

ReactiveX sử dụng Observer pattern vì:

* Observer có thể điều chỉnh được. Observer tạo ra các luồng xử lí đồng thời và bất đồng bộ nhưng vẫn tuần tự với nhau
* Observer rât linh hoạt : Observer không chỉ có thể emit một giá trị mà còn có thể emit một hoặc nhiều các event vô hạn. Observer là một object trừu tượng nên có thể được sử dụng cho bất kì trường hợp nào
* Dễ dàng get data sau khi thread xử lí thành công.

## III. Observable

Trong Rx, một observer đăng kí một observable. Observable là các nguồn dữ liệu, thông thường nó bắt đầu cung cấp data khi subcribers bắt đầu lắng nghe. Nó có thể phát ra bất kì số lượng item nào.

Một observable có thể có nhiều subscriber. Nếu một item được phát ra từ một Observable thì onNext() sẽ được gọi trên mỗi subscriber

Để kết nối với Observable thì subscriber phải có các phương thức sau:

* onNext() : Observable gọi phương thức này khi mà có bất kì một item nào được phát ra. Tham số truyền vào là kết quả mà mình nhận được
* onError(): Observable gọi phương thức này khi có lỗi xảy ra, dữ liệu không trả về, khi có lỗi nó sẽ không gọi onNext() hay onCompleted().
* onCompleted() : Observable sẽ gọi method này khi nó gọi onNext() lần cuối cùng

<img src='../../../../../../resources/images/rx1.PNG'>



* (1) : là timeline của observable. Time flow từ trái qua phải

* (2) : các item được phát ra bởi observable

* (3) : vạch thẳng đứng biểu thị Observable đã hoàn thành thành công nhiệm vụ phát ra các item

* (4) : đường chấm chấm và cái hộp hình chữ nhật này biểu thị một sự chuyển đổi đang được áp dụng cho Observable. Và cách kiểm tra bên trong hộp hiển thị bản chất của transformation (phép chuyển đổi)

* (5) : Nếu có một vài lí do làm cho Observable bị dừng quá trinh phát với một lỗi thì gạch thẳng đứng sẽ được thay thế bởi dấu X

* (6) : kết quả trả về của việc chuyển đổi

## IV. Subject

* Subject là một đối tượng đặc biệt đóng vai trò là cầu nối giữa observable và observer (có thể hình dùng subject như một đường ống bạn có thể đặt mọi thứ vào một đầu của đường ống và nó sẽ đi ra đầu còn lại).
* Bởi vì nó là một Observer nên nó có thể đăng kí một hoặc nhiều observable và bởi vì nó là một observable nên nó có thể thông qua các item mà nó quan sát bằng cách phát lại chúng, và nó có thể phát ra các item mới
* Subject có 4 loại :

 * AsyncSubject : chỉ emit giá trị cuối cùng đc emit bởi nguồn observable
 * BehaviorSubject: khi một observer đăng kí một BehaviorSubject thì nó sẽ phát ra item được phát ra gần đấy nhất tính từ thời điểm đăng kí
 * PublishSubject: chỉ phát ra item sau thời gian đăng kí
 * ReplaySubject: phát ra tất cả item mà nó có cho bất kì observer nào đăng kí nó

### 1. AsyncSubject

Phát ra giá trị cuối cùng (và chỉ là giá trị cuối cùng) đc phát ra bởi Observable gốc và chỉ sau khi observable nguồn hoàn thành

Hay nói cách khác : nó chỉ push giá trị cuối cùng được emitted bởi Observable nguồn đến tất cả các observer đang subscribe() nó và chỉ sau khi observable hoàn thành

<img src='../../../../../../resources/images/rx2.PNG'/>

Nó cũng sẽ phát ra cùng giá trị cuối cùng này cho bất kì observer nào đăng kí sau này. Tuy nhiên nếu observable gốc bị dừng vì một lỗi phát sinh thì AsynxSubject sẽ không phát ra bất kì một item nào

<img src='../../../../../../resources/images/rx3.PNG'/>

```java
package com.rxjava3.reactivex.io.subjects;

import io.reactivex.rxjava3.subjects.AsyncSubject;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;
import concurrencies.utilities.LogTest;

/**
 * Phát ra giá trị cuối cùng (và chỉ là giá trị cuối cùng) đc phát ra bởi
 * Observable gốc và chỉ sau khi observable nguồn hoàn thành <br/>
 * Hay nói cách khác : nó chỉ push giá trị cuối cùng được emitted bởi Observable
 * nguồn đến tất cả các observer đang subscribe() nó và chỉ sau khi observable
 * hoàn thành <br/>
 * Nó cũng sẽ phát ra cùng giá trị cuối cùng này cho bất kì cho bất kì observer
 * nào đăng kí sau này. Tuy nhiên nếu observable gốc bị dừng vì một lỗi phát
 * sinh thì AsynxSubject sẽ không phát ra bất kì một item nào
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class AsyncSubjectExample {
	static Logger logger = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		AsyncSubject<Integer> source = AsyncSubject.create();

		source.subscribe(Common.getFirstObserver()); // it will emit only 4 and
														// onComplete

		source.onNext(1);
		source.onNext(2);
		source.onNext(3);

		/*
		 * it will emit 4 and onComplete for second observer also.
		 */
		source.subscribe(Common.getSecondObserver());

		source.onNext(4);
		source.onComplete();

	}

}

```

output:

```java
 First onSubscribe : false
 Second onSubscribe : false
 First onNext value : 4
 First onComplete
 Second onNext value : 4
 Second onComplete

```

### 2. BehaviorSubject

Khi observer subscribe một behavior subject thì ngay lập tức behavior subjet sẽ push giá trị gần nhất nó nhận đc Observable source (hoặc giá trị khởi tạo behavior subject) và sau đó tiếp tục push data như bình thường

<img src='../../../../../../resources/images/rx4.PNG'/>

Tuy nhiên nếu observable gốc bị dừng với một lỗi thì Behavior subject sẽ không phát ra bất kì một item nào cho observer nào đăng kí nó sau này, nhưng chỉ đơn giản là sẽ chuyển thông báo lỗi từ Observable gốc
<img src='../../../../../../resources/images/rx5.PNG'/>

```java
package com.rxjava3.reactivex.io.subjects;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;
import concurrencies.utilities.LogTest;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * Khi observer subscribe một behavior subject thì ngay lập tức behavior subjet
 * sẽ push giá trị gần nhất nó nhận đc Observable source (hoặc giá trị khởi tạo
 * behavior subject) và sau đó tiếp tục push data như bình thường <br/>Tuy nhiên nếu
 * observable gốc bị dừng với một lỗi thì Behavior subject sẽ không phát ra bất
 * kì một item nào cho observer nào đăng kí nó sau này, nhưng chỉ đơn giản là sẽ
 * chuyển thông báo lỗi từ Observable gốc
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BehaviorSubjectExample {
	static Logger logger = Logger.getLogger(LogTest.class.getName());
	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		BehaviorSubject<Integer> source = BehaviorSubject.create();

		source.subscribe(Common.getFirstObserver()); // it will get 1, 2, 3, 4
														// and onComplete

		source.onNext(1);
		source.onNext(2);
		source.onNext(3);

		/*
		 * it will emit 3(last emitted), 4 and onComplete for second observer
		 * also.
		 */
		source.subscribe(Common.getSecondObserver());

		source.onNext(4);
		source.onComplete();
	}

}

```

output:

```java
 First onSubscribe : false
 First onNext value : 1
 First onNext value : 2
 First onNext value : 3
 Second onSubscribe : false
 Second onNext value : 3
 First onNext value : 4
 Second onNext value : 4
 First onComplete
 Second onComplete
```

### 3. PublishSubject

Chỉ phát ra item sau thời gian đăng kí

Đây là loại phổ biến nhất, nó push mọi data từ source sang cho observer theo thời gian thực khi sử dụng publish subject sẽ có khả năng data bị thất lạc trong khoảng thời gian từ khi subject đc tạo ra tới lúc subject đc subscribe. Bạn sẽ cần lưu tâm tới điều này khi sử dụng loại subject này.

<img src='../../../../../../resources/images/rx6.PNG'/>

```java
package com.rxjava3.reactivex.io.subjects;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;
import concurrencies.utilities.LogTest;

/**
 * AsyncSubject
 * 
 * Phát ra giá trị cuối cùng (và chỉ là giá trị cuối cùng) đc phát ra bởi
 * Observable gốc và chỉ sau khi observable nguồn hoàn thành <br/>
 * Hay nói cách khác : nó chỉ push giá trị cuối cùng được emitted bởi Observable
 * nguồn đến tất cả các observer đang subscribe() nó và chỉ sau khi observable
 * hoàn thành <br/>Nó cũng sẽ phát ra cùng giá trị cuối cùng này cho bất kì cho bất
 * kì observer nào đăng kí sau này. Tuy nhiên nếu observable gốc bị dừng vì một
 * lỗi phát sinh thì AsynxSubject sẽ không phát ra bất kì một item nào
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class PublishSubjectExample {
	static Logger logger = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		PublishSubject<Integer> source = PublishSubject.create();

		// It will get 1, 2, 3, 4 and onComplete
		source.subscribe(Common.getFirstObserver());

		source.onNext(1);
		source.onNext(2);
		source.onNext(3);

		// It will get 4 and onComplete for second observer also.
		source.subscribe(Common.getSecondObserver());

		source.onNext(4);
		source.onComplete();
	}

	
}

```

output:

```java
 First onSubscribe : false
 First onNext value : 1
 First onNext value : 2
 First onNext value : 3
 Second onSubscribe : false
 First onNext value : 4
 Second onNext value : 4
 First onComplete
 Second onComplete
```

### 4. ReplaySubject

push toàn bộ giá trị đã nhận được trong suốt vòng đời vào Observer.

<img src='../../../../../../resources/images/rx7.PNG'/>

```java
package com.rxjava3.reactivex.io.subjects;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;
import concurrencies.utilities.LogTest;
import io.reactivex.rxjava3.subjects.ReplaySubject;

/**
 * push toàn bộ giá trị đã nhận được trong suốt vòng đời vào Observer.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ReplaySubjectExample {
	static Logger logger = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		ReplaySubject<Integer> source = ReplaySubject.create();

		source.subscribe(Common.getFirstObserver()); // it will get 1, 2, 3, 4

		source.onNext(1);
		source.onNext(2);
		source.onNext(3);
		source.onNext(4);
		source.onComplete();

		/*
		 * it will emit 1, 2, 3, 4 for second observer also as we have used
		 * replay
		 */
		source.subscribe(Common.getSecondObserver());

	}

}

```

outpur:

```java
 First onSubscribe : false
 First onNext value : 1
 First onNext value : 2
 First onNext value : 3
 First onNext value : 4
 First onComplete
 Second onSubscribe : false
 Second onNext value : 1
 Second onNext value : 2
 Second onNext value : 3
 Second onNext value : 4
 Second onComplete
```

## V. Threading Concept

A free threaded model : (mô hình luồng tự do):

* Trái với những gì mọi người thường nghĩ khi nhắc đến ReactiveX, ở trạng thái mạc định nó hoàn toàn không chạy đa luồng.
* Cấu trúc Rx bao gồm : 1 Obsevable theo sau bởi 0 hoặc nhiều Operator theo sau bởi 1 subcriber
* Operator là một tập hợp những hàm có thể dùng giữa Observable gốc và subscriber với nhiều chức năng như tính toán, lọc hay biến đổi dữ liệu.
* Với cấu trúc này thì concept threading mạc định là:
 * Các thành phần tính toán để tạo nên Observable gốc được chạy trên thread mà hàm subscribe() được gọi đến
 * Các phần tính toán của một Operator được chạy trên thread mà Operator ở trước nó được chạy. Nếu trước nó không có một Operator nào khác, nó sẽ được chạy trên thread tạo ra Observable gốc
 * Các phần tính toán của một subscriber được chạy trên thread mà Operator ở trước nó được chạy. Nếu trước nó không có một operator nào khác, nó sẽ được chạy trên thread tạo ra Observable gốc

Code sample :

```java
private Observable<Integer> getANumberObservable() {
       return Observable.defer(new Func0<Observable<Integer>>() {
           @Override public Observable<Integer> call() {
               Log.i("Observable thread", Thread.currentThread().getName());
               return Observable.just(1);
           }
       });
}
```

```java
//Run this inside onCreate() of an Activity
getANumberObservable()
               .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        Log.i("Operator thread", Thread.currentThread().getName());
                        return String.valueOf(integer);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                        public void call(String s) {
                        Log.i("Subscriber thread", Thread.currentThread().getName());
                    }
                });
```

sẽ log ra output

```java
    Observable thread: main
    Operator thread: main
    Subscriber thread: main
```

Giải thích :

* getANumberObservable() là hàm để tạo ra một Observable gốc và được chạy trên main thread vì hàm subscribe() được gọi trên main thread
* map() : là một trong các operator và cũng chạy trên main thread bởi vì trước nó không có một operator nào khác nên mạc định nó sẽ chạy trên thread đã tạo ra Observable
* Hàm call() (hay tất cả các hàm nằm trong subscribe() như onNext(), onCompleted(), onError() là hàm tính toán của subscriber được chạy trên main thread bởi vì operator trước đó là map() cũng chạy trên main thread
* Dễ thấy rằng mạc định Rx sẽ chạy trên thread mà hàm subscribe() được gọi đến. tuy có bản chất là mô hình luồng tự do nhưng không có nghĩa rằng Rx sẽ tự động sử dụng đa luồng cho bạn, nó chỉ mang ý nghĩa là bạn có thể chọn bất cứ thread nào để thực thi công việc trên đó
* Nói như vậy cũng không có nghĩa là bạn không thể lập trình đa luồng với Rx. Rx cung cấp cho chúng ta một cơ chế xử lí đa luồng rất tiện dụng và hữu ích đó chính là scheduling

## VI. Scheduler

* Scheduler sẽ định nghĩa ra một thread để chạy 1 khối lượng công việc. Rxjava cung cấp những lựa chọn scheduler như sau :
* Immediate(): tạo ra và trả về 1 scheduler để thực thi công việc trên thread hiện tại o Trampoline() : tạo ra và trả về một scheduler để sắp xếp 1 hang chờ cho công việc trên thread hiện tại để thực thi khi công việc hiện tại kết thúc o newThread() : tạo ra và trả về một scheduler để tạo ra một thread mới cho mỗi đơn vị công việc o computation() : tào ra và trả về một scheduler với mục đích xử lí các công việc tính toán được hỗ trợ với một thread pool giới hạn size bằng số cpu hiện có o io(): tạo ra và trả về một scheduler với mục đích xử lí các công việc không mang nặng tính chất tính toán, được hỗ trợ bởi một thread pool không giới hạn có thể mở rộng khi cần. có thể được dùng để thực thi các tiến trình bất đồng bộ không gây ảnh hưởng lớn tới CPU
* để sử dụng scheduler sử dụng hàm subscribeOn() và observeOn()

### 1. subscribeOn()

>hàm Subscribe() nhận vào một tham số là một scheduler sẽ quyết định việc xử lí các phần tính toán để tạo nên một Observable trên thread cung cấp bởi scheduler đó

```java
getANumberObservable()
              .subscribeOn(Schedulers.newThread())
              .map(new Func1<Integer, String>() {
                   @Override
                   public String call(Integer integer) {
                       Log.i("Operator thread", Thread.currentThread().getName());
                       return String.valueOf(integer);
                   }
               })
               .subscribe(new Action1<String>() {
                   @Override
                       public void call(String s) {
                       Log.i("Subscriber thread", Thread.currentThread().getName());
                   }
               });
```

OutPut

```java
Observable thread: RxNewThreadScheduler-1
Operator thread: RxNewThreadScheduler-1
Subscriber thread: RxNewThreadScheduler-1
```



* Với đoạn code trên hàm gọi subscribeOn(Scheduler.newThread()) sẽ làm cho tất cả các operation được thực thi trên một thread mới.
* Vị trí gọi subscribeOn() không quan trọng : bạn có thể gọi hàm này từ bất kì chỗ nào giữa observable và subscribe() được gọi đến
* Tương tác giữa các hàm khởi tạo Observable đối với subscribeOn()
* Note : cần lưu í khi sử dụng các hàm Observable.just(), Observable.from(), hay Observable.range() những hàm này sẽ nhận vào giá trị ngay khi chúng đc khởi tạo nên subscribeOn() sẽ không có tác dụng, nguyên nhân do subscribeOn() chỉ có tác dụng khi hàm subscribe được gọi đến mà những hàm khởi tạo nói trên lại khởi tạo observable trước khi tạo subscribe() nên cần tránh đưa vào các giá trị mà cần tính toán trong một khoảng thời gian dài vào các hàm khởi tạo đó. Thay vào đó đối với các hàm blocking bạn nên sử dụng Observable.create() hoặc Observable.defer(), hai hàm này đảm bảo là Observable sẽ chỉ được khởi tạo khi hàm subscribe() được gọi đến

### 2. observeOn()

Hàm này nhận vào tham số một Scheduler sẽ làm cho các Operator hay subscriber được gọi đằng sau nó chạy trên thread được cung cấp bởi scheduler đó

```java
    getANumberObservable() //this will run on main thread
                .observeOn(Schedulers.io())
                .map(new Func1<Integer, String>() { // this will run on a thread intended for I/O bound
                    @Override
                    public String call(Integer integer) {
                        Log.i("Operator thread", Thread.currentThread().getName());
                        return String.valueOf(integer);
                    }
                })
                .subscribe(new Action1<String>() {  // this will run on a thread intended for I/O bound
                    @Override
                        public void call(String s) {
                        Log.i("Subscriber thread", Thread.currentThread().getName());
                    }
                });
```

Sau khi observeOn(Scheduler.io()) được gọi đến, tất cả các hàm đằng sau nó đều sẽ chạy trên thread cung cấp bởi Schedulers.io().

Khi chúng ta có nhiều hàm gọi đến observeOn(), các Scheduler truyền vào sẽ có tác dụng giữa hàm gọi này cho đến hàm gọi tiếp theo. Nói cách khác, observeOn() sẽ thay đổi thread của tất cả các hàm chạy đằng sau nó cho đến khi có một hàm observeOn() khác được gọi đến. Trong trường hợp chúng ta truyền vào cùng một Scheduler vào nhiều hàm observeOn(), một thread mới cũng sẽ được tạo ra chứ không phải dùng lại thread cũ. ObserveOn() rất hữu ích khi chúng ta muốn thực thi các tác vụ trên thread mà chúng ta mong muốn. Một trong những tác vụ cơ bản trong lập trình mobile chính là việc execute task dưới background thread sau đó update kết quả lên UI. Đối với android, để đạt được điều này chúng ta cần phải sử dụng 2 thread khác nhau, một thread dùng để request và main thread dùng để update UI. Tuy nhiên RxJava không cung cấp cho chúng ta một Scheduler nào để sắp xếp công việc trên main thread của Android.

Trong RxJava, mặc định Scheduler chạy trên deamon thread, chính vì vậy một khi nó thoát khỏi main thread thì các công việc trong background cũng bị dừng. 0


## VII. Các Operator

Hầu hết các operator đều hoạt động trên 1 observable để trả về một observable khác nên nó rất phù hợp để bạn nối các operator với nhau để tạo ra các observable mong muốn trước khi gửi nó cho subscrier

Thứ tự thực thi các operator : operator trước chạy xong thì operator tiếp theo sẽ đc thực thi

### 1. from()

Nhận vào số parameter không hạn chế và có thể thuộc mọi type (ở đây truyền vào 3 tham số 1,2,3). Observable.subscribe() sẽ tạo ra 1 subscriber với 3 hàm onCompleted(), onError() và onNext() để sử dụng các item được truyền vào ở trên


<img src='../../../../../../resources/images/rx8.PNG'/>


```java
Observable.from(1,2,3).subscribe(new Subscriber<Integer>() {
            public void onCompleted() {
            }
            public void onError(Throwable e) {
            }
            public void onNext(Integer integer) {
               Log.i("onNext", String.valueOf(integer));
            }
        });
```

kết quả khi chạy :

```java
I/onNext: 1
I/onNext: 2
I/onNext: 3
```

### 2. just()

```java
Integer[] integers = {1,2,3};

Observable.just(integers).subscribe(new Subscriber<Integer[]>() {
   public void onNext(Integer[] integers) {
       Log.i("onNext", Arrays.toString(integers));
   }
}

Observable.from(integers).subscribe(new Subscriber<Integer>() {
   public void onNext(Integer integer) {
       Log.i("onNext", String.valueOf(integer));
   }
}
```

Kết quả đối với toán tử just() : I/onNext: [1, 2, 3] đối với from : I/onNext: 1 I/onNext: 2 I/onNext: 3
Với just(), khi chúng ta truyền vào 1 array hoặc list item, nó sẽ phát ra array và list item đó và Subscriber cũng sẽ nhận vào parameter là 1 array hoặc list tương ứng. Còn đối với from(), nó sẽ phát ra từng item trong list (sẽ gọi đến onNext() số lần bằng với size của list trong điều kiện ko có lỗi xảy ra).

### 3. defer()

Chỉ tạo Observable khi nào có 1 observer subscribe nó 

<img src='../../../../../../resources/images/rx9.PNG'/>

```java
Movie movie = new Movie("Captain America: Civil War");
Observable<Movie> movieObservable = Observable.just(movie);
movie = new Movie("Batman v Superman: Dawn of Justice");
movieObservable.subscribe(new Subscriber<Movie>() {
      public void onNext(Movie movie) {
          Log.i("onNext", movie.name);
      }
});
```


### 4. map()

>Biến đổi các item được phát ra bởi Observable bằng cách aply function nào đấy cho mỗi item, sau đó gộp lại thành một observable. Bởi vậy flatmap sẽ không quan tâm đến thứ tự các item

Map là một hàm thuộc nhánh Transformation, nó có tác dụng biến đổi data phát ra từ Observable

<img src='../../../../../../resources/images/rx10.PNG'/>

Dữ liệu trả về từ map không nhất thiết phải cùng kiểu với dữ liệu nhận vào

<img src='../../../../../../resources/images/rx11.PNG'/>


```java
var s = 'Syntax Highlighting';

searchMovie("Doctor Strange")
  .map(new Func1<String, List<Movie>() {
        @Override
        public List<Movie> call(String s) {
            return parse(s);
        }
   })
  .subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())
  .subscribe(new Action1<List<Movie>>() {
      @Override
      public void call(List<Movie> movies) {
           list.clear();
           list.addAll(movies);
           adapter.notifyDataSetChanged();
  });

```

Trong ví dụ trên observable gốc phát ra một chuỗi JSON thuộc kiểu String và hàm map sử dùng hàm parse để covert sang List<Movie>. Subscriber nhận được là một list movie chứ không phải string

Nhưng map không xử lí tốt trong việc xảy ra exeption nó bắt buộc phải return một thứ gì đó


### 5 . flatMap()

>Biến đổi các item được phát ra bởi Observable thành các Observable khác

```java
getAllMyFriendsObservable()
    	.flatMap(new Function<List<User>, ObservableSource<User>>() { 
        	@Override
        	public ObservableSource<User> apply(List<User> usersList) throws Exception {
            	return Observable.fromIterable(usersList); // returning user one by one from usersList.
        	}
    	})
```

Sự khác nhau giữa Map và FlatMap

* Map va flatMap đều thuộc transformation Transforming : chuyển đổi từ đối tượng này sang đối tượng khác

* Map : chuyển đổi các item được phát ra bởi một observable bằng cách ap dụng hàm cho mỗi item( hay nói cách khác map dùng để chuyển một item này sang mộ item khác)

* flatMap trả về một observable<T> (với flatMap bạn có thể không phát ra item nào, phát ra chính xác một item phát ra nhiều item hoặc phát ra một lỗi

### 6. filter()

>dùng để lọc các item phát ra từ bởi một observable, và trả về một giá trị boolean, nếu true thì giá trị đc trả về sẽ đc chuyển tiếp tới subscriber còn nếu false thì sẽ đc đưa vào thùng rác

```java
searchMovie("Doctor Strange")
  .flatMap(json -> {
     try {
      return Observable.defer(() -> Observable.just(parse(json)));
    } catch(JSONException e) {
      return Observable.error(e);
    }
  })
  .flatMap(movies -> Observable.from(movies))
  .subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())
  .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onNext(Movie movie) {
                    }
  });
```

### 7. debounce()

>Chỉ phát ra một item từ một observable nếu một thời gian cụ thể đã trôi qua mà không phát ra một mục khác

Sử dụng với một hằng số để hiển thị thời gian

```java
RxSearchObservable.fromView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String text) throws Exception {
                        if (text.isEmpty()) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                })
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String query) throws Exception {
                        return dataFromNetwork(query);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String result) throws Exception {
                        textViewResult.setText(result);
                    }
                });
```