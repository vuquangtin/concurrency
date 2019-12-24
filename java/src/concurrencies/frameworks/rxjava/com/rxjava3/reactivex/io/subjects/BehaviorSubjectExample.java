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
