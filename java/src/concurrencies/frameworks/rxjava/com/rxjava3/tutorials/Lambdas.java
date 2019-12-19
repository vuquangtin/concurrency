package com.rxjava3.tutorials;

import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;

@SuppressWarnings("all")
public class Lambdas implements Action {

	@Override
	public void run() throws Exception {
		runnableClass().run();
		runnableLambda().run();
		try {
			consumerClass().accept(0);
			consumerLambda().accept(1);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Runnable runnableClass() {
		return new Runnable() {
			@Override
			public void run() {
				System.out.print("Runnable : anonymous inner class");
			}
		};
	}

	/**
	 * same as runnableClass()
	 */
	private Runnable runnableLambda() {
		return () -> System.out.print("Runnable : lambda");
	}

	private Consumer<Integer> consumerClass() {
		return new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				System.out.print("Consumer class : integer = " + integer);
			}
		};
	}

	/**
	 * same as consumerClass()
	 */
	private Consumer<Integer> consumerLambda() {
		return integer -> System.out.print("Consumer lambda : integer = " + integer);
	}

}