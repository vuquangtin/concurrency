package com.hystrix.async.spring.repositories;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

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

// @Component("CrawlUrlRepositoryImpl")

// @Service("CrawlUrlRepositoryImpl")
// @Qualifier("CrawlUrlRepositoryImpl")
public class CrawlUrlRepositoryImpl implements CrawlUrlRepository {

	private static final String KEY = "CRAWLER";
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations hashOperations;

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public CrawledUrlDetailsDTO findDataByUrlAndDepth(int hashKey) {
		return (CrawledUrlDetailsDTO) hashOperations.get(KEY, hashKey);
	}

	@Override
	public void saveDataByUrlAndDepth(int hashKey,
			CrawledUrlDetailsDTO crawledUrlDetailsDTO) {
		hashOperations.put(KEY, hashKey, crawledUrlDetailsDTO);
	}
}