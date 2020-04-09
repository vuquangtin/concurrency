package threadpools.mycustom.pool.job;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import threadpools.mycustom.pool.common4j.SystemUtil;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ThreadStackJob extends AbstractJob {

	private static Logger _logger = LoggerFactory.getLogger(ThreadStackJob.class);

	private String _lineSeparator = SystemUtil.getEndLine();

	/** 线程堆栈缓冲区初始大小 */
	private final static int BUFFER_SIZE = 4096;

	public ThreadStackJob(int interval) {
		super._interval = interval;
	}

	@Override
	protected void execute() {
		Map<Thread, StackTraceElement[]> stackMap = Thread.getAllStackTraces();
		for (Entry<Thread, StackTraceElement[]> entry : stackMap.entrySet()) {
			// 线程基本信息
			Thread thread = entry.getKey();
			StringBuilder buffer = new StringBuilder(BUFFER_SIZE).append("name:").append(thread.getName())
					.append(", id:").append(thread.getId()).append(", status:").append(thread.getState().toString())
					.append(", priority:").append(thread.getPriority()).append(_lineSeparator);

			// 线程堆栈
			StackTraceElement[] stackList = entry.getValue();
			for (StackTraceElement ste : stackList) {
				buffer.append(ste.toString()).append(_lineSeparator);
			}
			_logger.info(buffer.toString());
		}
		super.sleep();
	}

}