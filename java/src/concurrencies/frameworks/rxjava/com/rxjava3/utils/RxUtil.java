package com.rxjava3.utils;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

@SuppressWarnings({ "WeakerAccess", "unused" })
public final class RxUtil {

	private RxUtil() {
	}

	/**
	 * newThread->newThread
	 */
	public static final ObservableTransformer THREAD_TRANSFORMER = new ObservableTransformer() {
		@Override
		public ObservableSource apply(Observable upstream) {
			return upstream.subscribeOn(Schedulers.newThread())
					.unsubscribeOn(Schedulers.newThread())
					.observeOn(Schedulers.newThread());
		}
	};

	/**
	 * newThread->mainThread
	 */
	public static final ObservableTransformer THREAD_ON_UI_TRANSFORMER = new ObservableTransformer() {
		@Override
		public ObservableSource apply(Observable upstream) {
			return upstream.subscribeOn(Schedulers.newThread())
					.unsubscribeOn(Schedulers.newThread())
					.observeOn(Schedulers.io());
		}
	};

	/**
	 * io->io
	 */
	public static final ObservableTransformer IO_TRANSFORMER = new ObservableTransformer() {
		@Override
		public ObservableSource apply(Observable upstream) {
			return upstream.subscribeOn(Schedulers.io())
					.unsubscribeOn(Schedulers.io()).observeOn(Schedulers.io());
		}
	};

	/**
	 * io->io
	 */
	public static final FlowableTransformer IO_TRANSFORMER_BACK_PRESSURE = new FlowableTransformer() {
		@Override
		public Publisher apply(Flowable upstream) {
			return upstream.subscribeOn(Schedulers.io())
					.unsubscribeOn(Schedulers.io()).observeOn(Schedulers.io());
		}
	};

	/**
	 * io->mainThread
	 */
	public static final ObservableTransformer IO_ON_UI_TRANSFORMER = new ObservableTransformer() {
		@Override
		public ObservableSource apply(Observable upstream) {
			return upstream.subscribeOn(Schedulers.io())
					.unsubscribeOn(Schedulers.io()).observeOn(Schedulers.io());
		}
	};

	/**
	 * io->mainThread
	 */
	public static final FlowableTransformer IO_ON_UI_TRANSFORMER_BACK_PRESSURE = new FlowableTransformer() {
		@Override
		public Publisher apply(Flowable upstream) {
			return upstream.subscribeOn(Schedulers.io())
					.unsubscribeOn(Schedulers.io()).observeOn(Schedulers.io());
		}
	};

	/**
     *
     */
	public static final ObservableTransformer COMPUTATION_TRANSFORMER = new ObservableTransformer() {
		@Override
		public ObservableSource apply(Observable upstream) {
			return upstream.subscribeOn(Schedulers.computation())
					.unsubscribeOn(Schedulers.newThread())
					.observeOn(Schedulers.newThread());
		}
	};

	public static final ObservableTransformer COMPUTATION_ON_UI_TRANSFORMER = new ObservableTransformer() {
		@Override
		public ObservableSource apply(Observable upstream) {
			return upstream.subscribeOn(Schedulers.computation())
					.unsubscribeOn(Schedulers.newThread())
					.observeOn(Schedulers.io());
		}
	};

	@SuppressWarnings("unchecked")
	public static <T> ObservableTransformer<T, T> applySchedulers(
			ObservableTransformer transformer) {
		return (ObservableTransformer<T, T>) transformer;
	}

	@SuppressWarnings("unchecked")
	public static <T> FlowableTransformer<T, T> applySchedulers(
			FlowableTransformer transformer) {
		return (FlowableTransformer<T, T>) transformer;
	}

	/**
	 * 运行一个定时任务在子线程
	 *
	 * @param delay
	 *            延迟时间，单位：毫秒
	 * @param onNext
	 * @return
	 */
	public static Disposable time(long delay, @NonNull Consumer<Long> onNext) {
		return time(delay, TimeUnit.MILLISECONDS, onNext);
	}

	/**
	 * 运行一个定时任务在子线程
	 *
	 * @param delay
	 *            延迟时间
	 * @param unit
	 *            单位
	 * @param onNext
	 * @return
	 */
	public static Disposable time(long delay, TimeUnit unit,
			@NonNull Consumer<Long> onNext) {
		return Observable
				.timer(delay, unit)
				.compose(RxUtil.<Long> applySchedulers(COMPUTATION_TRANSFORMER))
				.subscribe(onNext);
	}

	/**
	 * 运行一个定时任务在UI线程
	 *
	 * @param delay
	 *            延迟时间
	 * @param unit
	 *            单位
	 * @param onNext
	 * @return
	 */
	public static Disposable timeOnUI(long delay, TimeUnit unit,
			@NonNull Consumer<Long> onNext) {
		return Observable
				.timer(delay, unit)
				.compose(
						RxUtil.<Long> applySchedulers(COMPUTATION_ON_UI_TRANSFORMER))
				.subscribe(onNext);
	}

	/**
	 * 运行一个轮询任务在子线程
	 *
	 * @param interval
	 *            轮询间隔，单位：毫秒
	 * @param onNext
	 * @return
	 */
	public static Disposable interval(long interval,
			@NonNull Consumer<Long> onNext) {
		return interval(interval, TimeUnit.MILLISECONDS, onNext);
	}

	/**
	 * 运行一个轮询任务在子线程
	 *
	 * @param interval
	 *            轮询间隔
	 * @param unit
	 *            单位
	 * @param onNext
	 * @return
	 */
	public static Disposable interval(long interval, TimeUnit unit,
			@NonNull Consumer<Long> onNext) {
		return Observable
				.interval(interval, unit)
				.compose(RxUtil.<Long> applySchedulers(COMPUTATION_TRANSFORMER))
				.subscribe(onNext);
	}

	/**
	 * 运行一个任务在子线程
	 *
	 * @param backgroundAction
	 * @return
	 */
	public static Disposable run(@NonNull Action backgroundAction) {
		return Observable
				.<Integer> empty()
				.compose(RxUtil.<Integer> applySchedulers(THREAD_TRANSFORMER))
				.subscribe(Functions.emptyConsumer(), Functions.ERROR_CONSUMER,
						backgroundAction);
	}

	/**
	 * 运行一个任务在UI线程
	 *
	 * @param backgroundSubscribe
	 * @param uiAction
	 * @return
	 */
	public static <T> Disposable runOnUI(
			@NonNull ObservableOnSubscribe<T> backgroundSubscribe,
			@NonNull Consumer<T> uiAction) {
		return Observable.create(backgroundSubscribe)
				.compose(RxUtil.<T> applySchedulers(THREAD_ON_UI_TRANSFORMER))
				.subscribe(uiAction);
	}
}