package basic.immutables;

import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class ImmutableExample {
	static Logger logger = Logger.getLogger(ImmutableExample.class.getName());
    private final static ImmutableList list = ImmutableList.of(1, 2, 3);

    private final static ImmutableSet set = ImmutableSet.copyOf(list);

    private final static ImmutableMap<Integer, Integer> map = ImmutableMap.<Integer, Integer>builder()
            .put(1, 2).put(2, 4).put(3, 6).build();

    public static void main(String[] args) {
        list.add(4);
        set.add(5);
        map.put(4, 8);
    }
}
