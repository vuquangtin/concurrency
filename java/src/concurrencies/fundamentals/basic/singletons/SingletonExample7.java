package basic.singletons;

import basic.annotations.Recommend;
import basic.annotations.ThreadSafe;


/**
 * @author panghu
 * @Title: SingletonExample7
 * @Description: TODO
 * @date 19-2-17 下午5:12
 */

/**
 * 枚举模式：最安全
 */
@ThreadSafe
@Recommend
public class SingletonExample7 {

    private SingletonExample7(){

    }

    public static SingletonExample7 getInstance(){
        return Singleton.INSTANCE.getSingleton();
    }

    private enum Singleton{
        /***/
        INSTANCE;

        private SingletonExample7 singleton;

        /**
         * JVM保证该方法绝对只会被调用一次
         */
        Singleton(){
            singleton = new SingletonExample7();
        }

        public SingletonExample7 getSingleton(){
            return singleton;
        }

    }

}
