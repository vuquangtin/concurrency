package com.rxjava.examples;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by achaub001c on 7/12/2016. a utility class to make http requests to
 * a url and retrieve results
 */
public class RestUtility {
	public static final long SAFE_DELAY = 2000;
	public static final long EXCEED_DELAY = 4000;
	private static final String QUOTE_URL = "http://gturnquist-quoters.cfapps.io/api/random";
	private static final int TIMEOUT_MS = 3000;
	private static final RestTemplate restTemplate = new RestTemplate();

	private static ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(TIMEOUT_MS);
		factory.setConnectTimeout(TIMEOUT_MS);
		return factory;
	}

	/**
	 * get a random quote from URL
	 *
	 * @return
	 */
	public static QuoteResource getRandomQuote() {
		QuoteResource q = restTemplate.getForObject(QUOTE_URL,
				QuoteResource.class);
		q.getValue().setThreadName(getThreadName());
		return q;
	}

	/**
	 * add intentional delay
	 *
	 * @param delay
	 * @return
	 */
	public static QuoteResource getRandomQuoteWithDelay(long delay) {
		System.out.println("fetching quote...");
		QuoteResource q = restTemplate.getForObject(QUOTE_URL,
				QuoteResource.class);
		q.getValue().setThreadName(getThreadName());
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return q;
	}

	private static String getThreadName() {
		return Thread.currentThread().getName();
	}
}