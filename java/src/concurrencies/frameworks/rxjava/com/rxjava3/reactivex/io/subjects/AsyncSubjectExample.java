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
