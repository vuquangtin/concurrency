package basic.immutables;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import basic.annotations.NotThreadSafe;

import com.google.common.collect.Maps;

@Slf4j
@NotThreadSafe
public class FinalExample {
	static Logger logger = Logger.getLogger(FinalExample.class.getName());
    private final static Integer a = 1;
    private final static String b = "2";
    private final static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(8, 9);
        map.put(4, 5);
    }

    public static void main(String[] args) {
        map.put(1, 3);
        logger.info("{}"+ map.get(1));
    }

}
