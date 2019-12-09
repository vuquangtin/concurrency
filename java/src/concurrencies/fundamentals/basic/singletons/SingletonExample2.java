package basic.singletons;
import basic.annotations.ThreadSafe;

/**
 * @author panghu
 * @Title: SingletonExample2
 * @Description: TODO
 * @date 19-2-17 下午3:08
 */


/**
 *饿汉 模式
 * 单例实例在类装载的时候进行创建
 * 会造成资源的浪费
 */
@ThreadSafe
public class SingletonExample2 {

    private SingletonExample2() {
    }

    private static SingletonExample2 instance = new SingletonExample2();

    public static SingletonExample2 getInstance(){
        return instance;
    }
}
