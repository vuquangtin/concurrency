package com.hystrix.async.spring.dto;

import java.io.Serializable;

import org.jsoup.select.Elements;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class CrawledUrlsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2583162243113608611L;

	private String url;

	private Elements navigations;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Elements getNavigations() {
		return navigations;
	}

	public void setLinks(Elements navigations) {
		this.navigations = navigations;
	}

	public CrawledUrlsDTO(String url) {
		super();
		this.url = url;
	}

	public CrawledUrlsDTO(Elements navigations) {
		super();
		this.navigations = navigations;
	}

	public CrawledUrlsDTO(String url, Elements navigations) {
		super();
		this.url = url;
		this.navigations = navigations;
	}

}