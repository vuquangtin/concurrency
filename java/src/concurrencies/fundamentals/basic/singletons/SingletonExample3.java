package basic.singletons;
import basic.annotations.NotRecommend;
/**
 * @author panghu
 * @Title: SingletonExample3
 * @Description: TODO
 * @date 19-2-17 下午3:03
 */
import basic.annotations.ThreadSafe;


/**
 * 懒汉模式
 * 单利实例在第一次使用的时候进行创建
 * 工厂方法中 添加了synchronized关键字 降低了性能
 */
@ThreadSafe
@NotRecommend
public class SingletonExample3 {

    /**
     * private constructor
     **/
    private SingletonExample3() {

    }

    /**
     * singleton object
     */
    private static SingletonExample3 instance = null;

    /**
     * 静态的工厂方法
     * @return
     */
    public static synchronized SingletonExample3 getInstance(){
        if (instance == null){
            instance = new SingletonExample3();
        }
        return instance;
    }

}
