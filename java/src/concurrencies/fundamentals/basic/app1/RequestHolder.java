package basic.app1;

/**
 * @author panghu
 * @Title: RequestHolder
 * @ProjectName cuncurrency_demo
 * @Description: TODO
 * @date 19-2-19 下午8:44
 */
public class RequestHolder {

    private final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    /**
     * 在请求即将进入后端服务器之前进行调用
     * @param id
     */
    public static void add(Long id){
        requestHolder.set(id);
    }

    public static Long getId(){
        return requestHolder.get();
    }

    /**
     * 接口处理完之后进行
     * 如果不进行释放，将会造成内存泄露
     */
    public static void remove(){
        requestHolder.remove();
    }

}
