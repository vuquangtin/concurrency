package threadpools.mycustom.pool;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import threadpools.mycustom.pool.common4j.ILifeCycle;
import threadpools.mycustom.pool.common4j.XmlUtil;

/**
 * 从配置文件（/biz/threadPool.xml）读取配置信息并存储在内存中。
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class ThreadPoolConfig implements ILifeCycle {

	public final static String DEFAULT_CONFIG_FILE = "/threadPool.xml";
	protected String _configFile = DEFAULT_CONFIG_FILE;

	/**
	 * key为线程池名称，value为{@link ThreadPoolInfo}实例。
	 */
	protected Map<String, ThreadPoolInfo> _multiThreadPoolInfo = new HashMap<>();

	/** 线程池状态收集开关 */
	protected boolean _threadPoolStateSwitch = false;

	/** 单位：秒 */
	protected int _threadPoolStateInterval = 60;

	/** 线程状态收集开关 */
	protected boolean _threadStateSwitch = false;

	/** 单位：秒 */
	protected int _threadStateInterval = 60;

	/** 线程堆栈收集开关 */
	protected boolean _threadStackSwitch = false;

	/** 单位：秒 */
	protected int _threadStackInterval = 60;

	@Override
	public void init() {
		initConfig();
	}

	private void initConfig() {

		Element node = XmlUtil.getTopElement(DEFAULT_CONFIG_FILE);

		List<Element> list = node.elements();

		for (Element element : list) {
			if (element.element("pool") != null) {
				ThreadPoolInfo info = new ThreadPoolInfo();
				info.setName(element.attributeValue("name"));
				info.setCoreSize(Integer.parseInt(element.attributeValue("corePoolSize")));
				info.setMaxSize(Integer.parseInt(element.attributeValue("maxPoolSize")));
				info.setThreadKeepAliveTime(Long.parseLong(element.attributeValue("keepAliveTime")));
				info.setQueueSize(Integer.parseInt(element.attributeValue("workQueueSize")));
				_multiThreadPoolInfo.put(info.getName(), info);
			} else if ("threadpoolstate".equals(element.getName())) {
				_threadPoolStateSwitch = "on".equals(element.attributeValue("switch"));
				_threadPoolStateInterval = Integer.parseInt(element.attributeValue("interval"));
			} else if ("threadstate".equals(element.getName())) {
				_threadPoolStateSwitch = "on".equals(element.attributeValue("switch"));
				_threadPoolStateInterval = Integer.parseInt(element.attributeValue("interval"));
			} else if ("threadstack".equals(element.getName())) {
				_threadPoolStateSwitch = "on".equals(element.attributeValue("switch"));
				_threadPoolStateInterval = Integer.parseInt(element.attributeValue("interval"));
			}
		}
	}

	/**
	 * 指定名称的线程池的配置是否存在。
	 * 
	 * @return 如果指定名称的线程池的配置存在返回true，如果不存在返回false；如果传入的线程池名称为null也返回false。
	 */
	public boolean containsPool(String poolName) {
		if (null == poolName || null == _multiThreadPoolInfo || _multiThreadPoolInfo.isEmpty()) {
			return false;
		}

		return _multiThreadPoolInfo.containsKey(poolName);
	}

	/**
	 * 获取指定线程池的配置信息。
	 * 
	 * @param threadpoolName
	 *            线程池名称
	 * @return 线程池配置信息（{@link ThreadPoolInfo}）
	 */
	public ThreadPoolInfo getThreadPoolConfig(String threadpoolName) {
		return _multiThreadPoolInfo.get(threadpoolName);
	}

	/**
	 * 获取所有线程池的配置信息。
	 * 
	 * @return 线程池配置信息（{@link ThreadPoolInfo}）集合
	 */
	public Collection<ThreadPoolInfo> getThreadPoolConfig() {
		return _multiThreadPoolInfo.values();
	}

	/**
	 * @return 输出各个线程池状态信息的开关，true表示开，false表示关
	 */
	public boolean getThreadPoolStateSwitch() {
		return _threadPoolStateSwitch;
	}

	/**
	 * @return 线程池状态信息输出的间隔时间（单位：秒）
	 */
	public int getThreadPoolStateInterval() {
		return _threadPoolStateInterval;
	}

	/**
	 * @return 输出各个线程组中线程状态信息的开关，true表示开，false表示关
	 */
	public boolean getThreadStateSwitch() {
		return _threadStateSwitch;
	}

	/**
	 * @return 线程状态信息输出的间隔时间（单位：秒）
	 */
	public int getThreadStateInterval() {
		return _threadStateInterval;
	}

	/**
	 * @return 输出所有线程堆栈的开关，true表示开，false表示关
	 */
	public boolean getThreadStackSwitch() {
		return _threadStackSwitch;
	}

	/**
	 * @return 线程堆栈信息输出的间隔时间（单位：秒）
	 */
	public int getThreadStackInterval() {
		return _threadStackInterval;
	}

	@Override
	public void destroy() {
		_threadPoolStateSwitch = false;
		_threadStateSwitch = false;
		_multiThreadPoolInfo.clear();
	}

}