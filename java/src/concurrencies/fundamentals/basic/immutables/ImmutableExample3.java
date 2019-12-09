package basic.immutables;

import java.util.List;

import basic.annotations.ThreadSafe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * @author panghu
 * @Title: ImmutableExample3
 * @Description: TODO
 * @date 19-2-17 下午9:14
 */
@ThreadSafe
public class ImmutableExample3 {

    private final static List<Integer> list = ImmutableList.of(1,2,3);

    private final static ImmutableSet set = ImmutableSet.copyOf(list);

    private final static ImmutableMap<Integer,Integer> map = ImmutableMap.of(1,2,3,4);

    private final static ImmutableMap<Integer,Integer> map2 = ImmutableMap.<Integer,Integer>builder().put(1,2).put(3,4).build();

    public static void main(String[] args) {
        System.out.println(map2.get(3));
    }
}
