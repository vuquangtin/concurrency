package basic.app1;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: HttpFilter
 * @ProjectName cuncurrency_demo
 * @Description: TODO
 * @date 19-2-19 下午9:02
 */
@Slf4j
public class HttpFilter implements Filter {
	static Logger logger = Logger.getLogger(HttpFilter.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		/* request.getSession().getAttribute("attribute"); */
		logger.info("do filter,{" + Thread.currentThread().getId() + "},{"
				+ request.getServletPath() + "}");
		RequestHolder.add(Thread.currentThread().getId());
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}
}
