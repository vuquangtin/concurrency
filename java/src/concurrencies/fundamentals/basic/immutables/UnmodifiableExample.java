package basic.immutables;

import java.util.Collections;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import basic.annotations.ThreadSafe;

import com.google.common.collect.Maps;

/**
 * Unmodifiable 的实现是, 将原本容器中的值拷贝进新创建的 Unmodifiable 容器中, 然后将该容器的修改方法直接改为 抛出异常
 */

@Slf4j
@ThreadSafe
public class UnmodifiableExample {
	static Logger logger = Logger.getLogger(UnmodifiableExample.class.getName());
	private static Map<Integer, Integer> map = Maps.newHashMap();

	static {
		map.put(1, 2);
		map.put(8, 9);
		map.put(4, 5);
		map = Collections.unmodifiableMap(map);
	}

	public static void main(String[] args) {
		map.put(1, 3);
		logger.info("{}" + map.get(1));
	}
}
