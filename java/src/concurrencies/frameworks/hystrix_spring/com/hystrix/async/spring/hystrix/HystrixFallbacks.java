package com.hystrix.async.spring.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class HystrixFallbacks {
	public static class HystrixFallbackBasic extends HystrixCommand<String> {
		private final boolean fail;

		public HystrixFallbackBasic(boolean fail) {
			super(HystrixCommandGroupKey.Factory.asKey("HystrixFallbackBasic"));
			this.fail = fail;
		}

		@Override
		protected String run() throws Exception {
			if (fail) {
				throw new Exception("My exception");
			}
			return "Success";
		}

		@Override
		protected String getFallback() {
			// Return the fallback in case of error.
			return "Fallback";
		}
	}

	public static class HystrixFallbackChain extends HystrixCommand<String> {
		private final boolean fail;

		public HystrixFallbackChain(boolean fail) {
			super(HystrixCommandGroupKey.Factory.asKey("HystrixFallbackChain"));
			this.fail = fail;
		}

		@Override
		protected String run() throws Exception {
			if (fail) {
				throw new Exception("My exception");
			}
			return "Success";
		}

		@Override
		protected String getFallback() {
			System.out.println("Running chained fallback");
			return new HystrixFallbackChainFallback().execute();
		}
	}

	public static class HystrixFallbackChainFallback extends
			HystrixCommand<String> {
		protected HystrixFallbackChainFallback() {
			super(HystrixCommandGroupKey.Factory.asKey("HystrixFallbackChain"));
		}

		@Override
		protected String run() throws Exception {
			// This could run any network command (like accessing Memcache) with
			// it's own fallback.
			return "Fallback HystrixCommand";
		}
	}
}