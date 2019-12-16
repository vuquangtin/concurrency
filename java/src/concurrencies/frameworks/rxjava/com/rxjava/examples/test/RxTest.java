package com.rxjava.examples.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class RxTest {
	@Before
	public void setup() {
	}

	@Test
	public void testPublishSubjectCompleted() {
		PublishSubject<String> subject = PublishSubject.create();

		@SuppressWarnings("unchecked")
		Observer<String> observer = mock(Observer.class);
		subject.subscribe(observer);

		subject.onNext("one");
		subject.onNext("two");
		subject.onNext("three");
		subject.onCompleted();

		@SuppressWarnings("unchecked")
		Observer<String> anotherObserver = mock(Observer.class);
		subject.subscribe(anotherObserver);

		subject.onNext("four");
		subject.onCompleted();
		subject.onError(new Throwable());

		// Assert counts
		verify(observer, times(1)).onNext("one");
		verify(observer, times(1)).onNext("two");
		verify(observer, times(1)).onNext("three");
		verify(observer, Mockito.never()).onError(any(Throwable.class));
		verify(observer, times(1)).onCompleted();
	}

	@Test
	public final void testMergeWithoutScheduler() {

		final String currentThreadName = Thread.currentThread().getName();

		Observable<Integer> o1 = Observable.<Integer> just(1, 2, 3, 4, 5);
		Observable<Integer> o2 = Observable.<Integer> just(6, 7, 8, 9, 10);
		Observable<String> o = Observable.<Integer> merge(o1, o2).map(
				new Func1<Integer, String>() {

					@Override
					public String call(Integer t) {
						assertTrue(Thread.currentThread().getName()
								.equals(currentThreadName));
						return "Value_" + t + "_Thread_"
								+ Thread.currentThread().getName();
					}
				});

		o.toBlocking().forEach(new Action1<String>() {

			@Override
			public void call(String t) {
				System.out.println("t: " + t);
			}
		});
	}

	@Test
	public final void testMergeWithImmediateScheduler1() {

		final String currentThreadName = Thread.currentThread().getName();

		Observable<Integer> o1 = Observable.<Integer> just(1, 2, 3, 4, 5);
		Observable<Integer> o2 = Observable.<Integer> just(6, 7, 8, 9, 10);
		Observable<String> o = Observable.<Integer> merge(o1, o2)
				.subscribeOn(Schedulers.immediate())
				.map(new Func1<Integer, String>() {

					@Override
					public String call(Integer t) {
						assertTrue(Thread.currentThread().getName()
								.equals(currentThreadName));
						return "Value_" + t + "_Thread_"
								+ Thread.currentThread().getName();
					}
				});

		o.toBlocking().forEach(new Action1<String>() {

			@Override
			public void call(String t) {
				System.out.println("t: " + t);
			}
		});
	}

	@Test
	public final void testComputationThreadPool1() {
		Observable<Integer> o1 = Observable.<Integer> just(1, 2, 3, 4, 5);
		Observable<Integer> o2 = Observable.<Integer> just(6, 7, 8, 9, 10);
		Observable<String> o = Observable.<Integer> merge(o1, o2).map(
				new Func1<Integer, String>() {

					@Override
					public String call(Integer t) {
						assertTrue(Thread.currentThread().getName()
								.startsWith("RxComputationThreadPool"));
						return "Value_" + t + "_Thread_"
								+ Thread.currentThread().getName();
					}
				});

		o.subscribeOn(Schedulers.computation()).toBlocking()
				.forEach(new Action1<String>() {

					@Override
					public void call(String t) {
						System.out.println("t: " + t);
					}
				});
	}

	@Test
	public final void testMergeWithExecutorScheduler() {

		final String currentThreadName = Thread.currentThread().getName();

		Observable<Integer> o1 = Observable.<Integer> just(1, 2, 3, 4, 5);
		Observable<Integer> o2 = Observable.<Integer> just(6, 7, 8, 9, 10);
		Observable<String> o = Observable.<Integer> merge(o1, o2)
				.subscribeOn(Schedulers.computation())
				.map(new Func1<Integer, String>() {

					@Override
					public String call(Integer t) {
						assertFalse(Thread.currentThread().getName()
								.equals(currentThreadName));
						assertTrue(Thread.currentThread().getName()
								.startsWith("RxComputationThreadPool"));
						return "Value_" + t + "_Thread_"
								+ Thread.currentThread().getName();
					}
				});

		o.toBlocking().forEach(new Action1<String>() {

			@Override
			public void call(String t) {
				System.out.println("t: " + t);
			}
		});
	}
}