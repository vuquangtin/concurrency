package threadpools.mycustom.pool.job;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import threadpools.mycustom.pool.ThreadStateInfo;
import threadpools.mycustom.pool.ThreadUtil;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ThreadStateJob extends AbstractJob {

	private static Logger _logger = LoggerFactory.getLogger(ThreadStateJob.class);

	public ThreadStateJob(int interval) {
		super._interval = interval;
	}

	@Override
	protected void execute() {
		Map<String, ThreadStateInfo> statMap = ThreadUtil.statAllGroupThreadState();

		for (Map.Entry<String, ThreadStateInfo> entry : statMap.entrySet()) {
			ThreadStateInfo stateInfo = entry.getValue();
			_logger.info("ThreadGroup:{}, New:{},  Runnable:{}, Blocked:{}, Waiting:{}, TimedWaiting:{}, Terminated:{}",
					entry.getKey(), stateInfo.getNewCount(), stateInfo.getRunnableCount(), stateInfo.getBlockedCount(),
					stateInfo.getWaitingCount(), stateInfo.getTimedWaitingCount(), stateInfo.getTerminatedCount());
		}
		super.sleep();
	}
}
