package com.rxjava3.reactivex.io.subjects;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import org.apache.log4j.Logger;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Common {
	static Logger logger = Logger.getLogger(Common.class.getName());

	public static Observer<Integer> getFirstObserver() {
		return new Observer<Integer>() {

			@Override
			public void onSubscribe(Disposable d) {
				System.out.println(" First onSubscribe : " + d.isDisposed());
			}

			@Override
			public void onNext(Integer value) {
				// textView.append(" First onNext : value : " + value);
				// textView.append(AppConstant.LINE_SEPARATOR);
				System.out.println(" First onNext value : " + value);
			}

			@Override
			public void onError(Throwable e) {
				// textView.append(" First onError : " + e.getMessage());
				// textView.append(AppConstant.LINE_SEPARATOR);
				System.out.println(" First onError : " + e.getMessage());
			}

			@Override
			public void onComplete() {
				// textView.append(" First onComplete");
				// textView.append(AppConstant.LINE_SEPARATOR);
				System.out.println(" First onComplete");
			}
		};
	}

	public static Observer<Integer> getSecondObserver() {
		return new Observer<Integer>() {

			@Override
			public void onSubscribe(Disposable d) {
				// textView.append(" Second onSubscribe : isDisposed :"
				// + d.isDisposed());
				System.out.println(" Second onSubscribe : " + d.isDisposed());
				// textView.append(AppConstant.LINE_SEPARATOR);
			}

			@Override
			public void onNext(Integer value) {
				// textView.append(" Second onNext : value : " + value);
				// textView.append(AppConstant.LINE_SEPARATOR);
				System.out.println(" Second onNext value : " + value);
			}

			@Override
			public void onError(Throwable e) {
				// textView.append(" Second onError : " + e.getMessage());
				// textView.append(AppConstant.LINE_SEPARATOR);
				System.out.println(" Second onError : " + e.getMessage());
			}

			@Override
			public void onComplete() {
				// textView.append(" Second onComplete");
				// textView.append(AppConstant.LINE_SEPARATOR);
				System.out.println(" Second onComplete");
			}
		};
	}
}
