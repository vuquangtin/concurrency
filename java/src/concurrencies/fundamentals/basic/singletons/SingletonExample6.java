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
 * @author panghu
 */
@ThreadSafe
public class SingletonExample6 {

    private SingletonExample6() {
    }

    private static SingletonExample6 instance = null;

    /**
     * 通过静态代码块实例化对象
     * 注意静态代码块的防止位置
     */
    static{
        instance = new SingletonExample6();
    }


    public static SingletonExample6 getInstance(){
        return instance; 
    }

    public static void main(String[] args) {
        System.out.println(getInstance().hashCode());
        System.out.println(getInstance().hashCode());
    }
}
