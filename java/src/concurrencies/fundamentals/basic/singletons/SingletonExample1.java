package basic.singletons;
import basic.annotations.NotThreadSafe;

/**
 * @author panghu
 * @Title: SingletonExample1
 * @Description: TODO
 * @date 19-2-17 下午3:03
 */


/**
 * 懒汉模式
 * 单利实例在第一次使用的时候进行创建
 */
@NotThreadSafe
public class SingletonExample1 {

    /**
     * private constructor
     **/
    private SingletonExample1() {

    }

    /**
     * singleton object
     */
    private static SingletonExample1 instance = null;

    /**
     * 静态的工厂方法
     * @return
     */
    public static SingletonExample1 getInstance(){
        if (instance == null){
            instance = new SingletonExample1();
        }
        return instance;
    }

}
