package com.rxjava3.tutorials;

import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;

public class MethodReference implements Action {

	@Override
	public void run() throws Exception {
		try {

			// same as the previous line, this creates A Runnable for you
			// similar to
			// the one in runnable() method, it checks that the referenced
			// method
			// return type
			// and parameters matches the "Runnable.run()" method, if true, it
			// creates
			// the Runnable for you
			consumer().accept(runnable());
			consumer().accept(this::invokeRun);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Consumer<Runnable> consumer() {
		return new Consumer<Runnable>() {
			@Override
			public void accept(Runnable runnable) {
				runnable.run();
			}
		};
	}

	private Runnable runnable() {
		return new Runnable() {
			@Override
			public void run() {
				invokeRun();
			}
		};
	}

	private void invokeRun() {
		System.out.print("invoke runnable");
	}

}