package basic.immutables;

import java.util.Collections;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import basic.annotations.ThreadSafe;

import com.google.common.collect.Maps;

/**
 * @author panghu
 * @Title: ImmutableExample1
 * @Description: TODO
 * @date 19-2-17 下午7:52
 */
@Slf4j
@ThreadSafe
public class ImmutableExample2 {
	static Logger logger = Logger.getLogger(ImmutableExample2.class.getName());
    private static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        map.put(1, 3);
        logger.info("{}"+ map.get(1));
    }

}
