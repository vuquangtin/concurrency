package basic.singletons;

import basic.annotations.NotThreadSafe;


/**
 * @author panghu
 * @Title: SingletonExample4
 * @Description: TODO
 * @date 19-2-17 下午3:38
 */


/**
 * 双重同步锁单例模式
 */
@NotThreadSafe
public class SingletonExample4 {

    /**
     * 私有构造函数
     */
    private SingletonExample4(){

    }

    /**
     * 单例对象
     */
    public static SingletonExample4 instance = null;

    /**
     * 1. memory = allocate()  分配对象的内存空间
     * 2.ctorInstance()  初始化对象
     * 3.instance = memory  设置instance只想刚分配的内存
     *
     * JVM和CPU优化发生了指令重排   第二步和第三不没有必要的联系，所以是可以发生指令重排的
     * 1.memory = allocate()  分配对象的内存空间
     * 3.instance = memory  设置instance只想刚分配的内存
     * 2.ctorInstance()  初始化对象
     */

    /**
     * 静态工厂方法
     * 使用双重检测机制
     * @return
     */
    public static SingletonExample4 getInstance(){
        if (instance == null){
            //同步锁  B-2  B线程可能在没有完成instance对象初始化之前就被返回了  也就是A中的instance没有初始化
            synchronized (SingletonExample4.class){
                //A -3
                if (instance == null){
                    instance = new SingletonExample4();
                }
            }
        }
        return instance;
    }

}
