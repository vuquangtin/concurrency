package com.hystrix.async.spring.repositories;

import com.hystrix.async.spring.dto.CrawledUrlDetailsDTO;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public interface CrawlUrlRepository {

	CrawledUrlDetailsDTO findDataByUrlAndDepth(int hashKey);

	void saveDataByUrlAndDepth(int hashKey,
			CrawledUrlDetailsDTO crawledUrlDetailsDTO);

}