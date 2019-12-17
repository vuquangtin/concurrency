package com.hystrix.async.spring.hystrix.test;
/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.hystrix.async.spring.hystrix.HystrixFallbacks;

public class HystrixFallbacksTest {
    @Test
    public void HystrixFallbackBasic_usesTheFallback() throws Exception {
        assertThat(new HystrixFallbacks.HystrixFallbackBasic(false).execute()).isEqualTo("Success");
		assertThat(new HystrixFallbacks.HystrixFallbackBasic(true).execute())
				.isEqualTo("Fallback");
	}

    @Test
    public void HystrixFallbackChain_usesTheChain() throws Exception {
        assertThat(new HystrixFallbacks.HystrixFallbackChain(false).execute()).isEqualTo("Success");
        assertThat(new HystrixFallbacks.HystrixFallbackChain(true).execute()).isEqualTo("Fallback HystrixCommand");
    }
}
