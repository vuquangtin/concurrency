package basic.app1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author panghu
 * @Title: HttpInterceptor
 * @ProjectName cuncurrency_demo
 * @Description: TODO
 * @date 19-2-19 下午9:55
 */
@Slf4j
public class HttpInterceptor  extends HandlerInterceptorAdapter {
	static Logger logger = Logger.getLogger(HttpInterceptor.class.getName());
    /**
     * 接口处理之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	logger.info("preHandle");
        return true;
    }

    /**
     * 接口处理之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*一定要记得移除数据，否则会造成内存泄露*/
        RequestHolder.remove();
        logger.info("afterCompletion");
        return;
    }
}
