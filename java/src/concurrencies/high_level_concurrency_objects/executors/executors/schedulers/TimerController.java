package executors.schedulers;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Design Patterns
 * 
 * @see https
 *      ://github.com/devinlee/Java/blob/18c78dd84fe3e9002e72740a466fd88559a09f87
 *      /Base/src/base/timer/TimerController.java
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class TimerController {
	/**
	 * 线程读写锁
	 */
	protected static final ReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * 计时器集
	 */
	private static ConcurrentHashMap<String, Timer> timers = new ConcurrentHashMap<String, Timer>();

	/**
	 * 取得指定名称的计时器
	 * 
	 * @param name
	 *            名称
	 */
	public static Timer getTimer(String name) {
		lock.readLock().lock();
		try {
			if (timers.containsKey(name)) {
				return timers.get(name);
			}
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					"取得计时器任务出现错误。", e);
		} finally {
			lock.readLock().unlock();
		}
		return null;
	}

	/**
	 * 移除指定名称的计时器
	 * 
	 * @param name
	 *            名称
	 */
	public static void removeTimer(String name) {
		lock.writeLock().lock();
		try {
			if (timers.containsKey(name)) {
				Timer timer = timers.remove(name);
				timer.cancel();
				timer = null;
			}
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					"计时器任务移除出现错误。", e);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 安排在指定的时间执行指定的任务
	 * 
	 * @param name
	 *            名称
	 */
	public static Timer timer(String name) {
		lock.writeLock().lock();
		Timer timer = null;
		try {
			if (timers.containsKey(name)) {
				timer = timers.get(name);
			} else {
				timer = new Timer();
				timers.put(name, timer);
			}
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					"计时器任务创建出现错误。", e);
		} finally {
			lock.writeLock().unlock();
		}
		return timer;
	}

	/**
	 * 安排在指定的时间执行指定的任务
	 * 
	 * @param name
	 *            名称
	 * @param timerTask
	 *            所要安排的任务
	 * @param time
	 *            执行任务的时间
	 */
	public static void timer(String name, TimerTask timerTask, Date time) {
		lock.writeLock().lock();
		try {
			if (timers.containsKey(name)) {
				Timer timer = timers.get(name);
				timer.schedule(timerTask, time);
			} else {
				Timer t = timer(timerTask, time);
				timers.put(name, t);
			}
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					"计时器任务创建出现错误。", e);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 安排指定的任务在指定的时间开始进行重复的固定延迟执行。以近似固定的时间间隔（由指定的周期分隔）进行后续执行
	 * 
	 * @param name
	 *            名称
	 * @param timerTask
	 *            所要安排的任务
	 * @param firstTime
	 *            首次执行任务的时间
	 * @param period
	 *            执行各后续任务之间的时间间隔，单位是毫秒
	 */
	public static void timer(String name, TimerTask timerTask, Date firstTime,
			long period) {
		lock.writeLock().lock();
		try {
			if (period < 0) {
				Logger.getLogger(TimerController.class.getName()).log(
						Level.SEVERE,
						"计时器 " + name + " 任务创建出现时间错误 period:" + period,
						new Throwable());
				return;
			}

			if (timers.containsKey(name)) {
				Timer timer = timers.get(name);
				timer.schedule(timerTask, firstTime, period);
			} else {
				Timer t = timer(timerTask, firstTime, period);
				timers.put(name, t);
			}
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					"计时器任务创建出现错误。", e);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 安排在指定延迟后执行指定的任务。
	 * 
	 * @param name
	 *            名称
	 * @param timerTask
	 *            所要安排的任务
	 * @param delay
	 *            执行任务前的延迟时间，单位是毫秒
	 */
	public static void timer(String name, TimerTask timerTask, long delay) {
		lock.writeLock().lock();
		try {
			if (delay < 0) {
				Logger.getLogger(TimerController.class.getName()).log(
						Level.SEVERE,
						"计时器 " + name + " 任务创建出现延迟时间错误 delay:" + delay,
						new Throwable());
				return;
			}

			if (timers.containsKey(name)) {
				Timer timer = timers.get(name);
				timer.schedule(timerTask, delay);
			} else {
				Timer t = timer(timerTask, delay);
				timers.put(name, t);
			}
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					"计时器任务创建出现错误。", e);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 安排指定的任务从指定的延迟后开始进行重复的固定延迟执行。以近似固定的时间间隔（由指定的周期分隔）进行后续执行
	 * 
	 * @param name
	 *            名称
	 * @param timerTask
	 *            所要安排的任务
	 * @param delay
	 *            执行任务前的延迟时间，单位是毫秒
	 * @param period
	 *            执行各后续任务之间的时间间隔，单位是毫秒
	 */
	public static void timer(String name, TimerTask timerTask, long delay,
			long period) {
		lock.writeLock().lock();
		try {
			if (delay < 0 || period < 0) {
				Logger.getLogger(TimerController.class.getName()).log(
						Level.SEVERE,
						"计时器 " + name + " 任务创建出现时间错误 delay:" + delay
								+ " period:" + period, new Throwable());
				return;
			}

			if (timers.containsKey(name)) {
				Timer timer = timers.get(name);
				timer.schedule(timerTask, delay, period);
			} else {
				Timer t = timer(timerTask, delay, period);
				timers.put(name, t);
			}
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					"计时器任务创建出现错误。", e);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 安排指定的任务在指定的时间开始进行重复的固定速率执行。以近似固定的时间间隔（由指定的周期分隔）进行后续执行
	 * 
	 * @param name
	 *            名称
	 * @param timerTask
	 *            所要安排的任务
	 * @param firstTime
	 *            首次执行任务的时间
	 * @param period
	 *            执行各后续任务之间的时间间隔，单位是毫秒
	 */
	public static void timerAtFixedRate(String name, TimerTask timerTask,
			Date firstTime, long period) {
		lock.writeLock().lock();
		try {
			if (period < 0) {
				Logger.getLogger(TimerController.class.getName()).log(
						Level.SEVERE,
						"计时器 " + name + " 任务创建出现时间错误 period:" + period,
						new Throwable());
				return;
			}

			if (timers.containsKey(name)) {
				Timer timer = timers.get(name);
				timer.scheduleAtFixedRate(timerTask, firstTime, period);
			} else {
				Timer t = timerAtFixedRate(timerTask, firstTime, period);
				timers.put(name, t);
			}
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					"计时器任务创建出现错误。", e);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 安排指定的任务在指定的时间开始进行重复的固定速率执行。以近似固定的时间间隔（由指定的周期分隔）进行后续执行
	 * 
	 * @param name
	 *            名称
	 * @param timerTask
	 *            所要安排的任务
	 * @param delay
	 *            执行任务前的延迟时间，单位是毫秒
	 * @param period
	 *            执行各后续任务之间的时间间隔，单位是毫秒
	 */
	public static void timerAtFixedRate(String name, TimerTask timerTask,
			long delay, long period) {
		lock.writeLock().lock();
		try {
			if (delay < 0 || period < 0) {
				Logger.getLogger(TimerController.class.getName()).log(
						Level.SEVERE,
						"计时器 " + name + " 任务创建出现时间错误 delay:" + delay
								+ " period:" + period, new Throwable());
				return;
			}

			if (timers.containsKey(name)) {
				Timer timer = timers.get(name);
				timer.scheduleAtFixedRate(timerTask, delay, period);
			} else {
				Timer t = timerAtFixedRate(timerTask, delay, period);
				timers.put(name, t);
			}
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					"计时器任务创建出现错误。", e);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 安排在指定的时间执行指定的任务
	 * 
	 * @param timerTask
	 *            所要安排的任务
	 * @param time
	 *            执行任务的时间
	 */
	public static Timer timer(TimerTask timerTask, Date time) {
		try {
			Timer timer = new Timer();
			timer.schedule(timerTask, time);
			return timer;
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					null, e);
			return null;
		}
	}

	/**
	 * 安排指定的任务在指定的时间开始进行重复的固定延迟执行。以近似固定的时间间隔（由指定的周期分隔）进行后续执行
	 * 
	 * @param timerTask
	 *            所要安排的任务
	 * @param firstTime
	 *            首次执行任务的时间
	 * @param period
	 *            执行各后续任务之间的时间间隔，单位是毫秒
	 */
	public static Timer timer(TimerTask timerTask, Date firstTime, long period) {
		try {
			if (period < 0) {
				Logger.getLogger(TimerController.class.getName()).log(
						Level.SEVERE, "计时器创建出现时间错误 period:" + period,
						new Throwable());
				return null;
			}

			Timer timer = new Timer();
			timer.schedule(timerTask, firstTime, period);
			return timer;
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					null, e);
			return null;
		}
	}

	/**
	 * 安排在指定延迟后执行指定的任务。
	 * 
	 * @param timerTask
	 *            所要安排的任务
	 * @param delay
	 *            执行任务前的延迟时间，单位是毫秒
	 */
	public static Timer timer(TimerTask timerTask, long delay) {
		try {
			if (delay < 0) {
				Logger.getLogger(TimerController.class.getName()).log(
						Level.SEVERE, "计时器创建出现时间错误 delay:" + delay,
						new Throwable());
				return null;
			}

			Timer timer = new Timer();
			timer.schedule(timerTask, delay);
			return timer;
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					null, e);
			return null;
		}
	}

	/**
	 * 安排指定的任务从指定的延迟后开始进行重复的固定延迟执行。以近似固定的时间间隔（由指定的周期分隔）进行后续执行
	 * 
	 * @param timerTask
	 *            所要安排的任务
	 * @param delay
	 *            执行任务前的延迟时间，单位是毫秒
	 * @param period
	 *            执行各后续任务之间的时间间隔，单位是毫秒
	 */
	public static Timer timer(TimerTask timerTask, long delay, long period) {
		try {
			if (delay < 0 || period < 0) {
				Logger.getLogger(TimerController.class.getName()).log(
						Level.SEVERE,
						"计时器创建出现时间错误 delay:" + delay + " period:" + period,
						new Throwable());
				return null;
			}

			Timer timer = new Timer();
			timer.schedule(timerTask, delay, period);
			return timer;
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					null, e);
			return null;
		}
	}

	/**
	 * 安排指定的任务在指定的时间开始进行重复的固定速率执行。以近似固定的时间间隔（由指定的周期分隔）进行后续执行
	 * 
	 * @param timerTask
	 *            所要安排的任务
	 * @param firstTime
	 *            首次执行任务的时间
	 * @param period
	 *            执行各后续任务之间的时间间隔，单位是毫秒
	 */
	public static Timer timerAtFixedRate(TimerTask timerTask, Date firstTime,
			long period) {
		try {
			if (period < 0) {
				Logger.getLogger(TimerController.class.getName()).log(
						Level.SEVERE, "计时器创建出现时间错误 period:" + period,
						new Throwable());
				return null;
			}

			Timer timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, firstTime, period);
			return timer;
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					null, e);
			return null;
		}
	}

	/**
	 * 安排指定的任务在指定的时间开始进行重复的固定速率执行。以近似固定的时间间隔（由指定的周期分隔）进行后续执行
	 * 
	 * @param timerTask
	 *            所要安排的任务
	 * @param delay
	 *            执行任务前的延迟时间，单位是毫秒
	 * @param period
	 *            执行各后续任务之间的时间间隔，单位是毫秒
	 */
	public static Timer timerAtFixedRate(TimerTask timerTask, long delay,
			long period) {
		try {
			if (delay < 0 || period < 0) {
				Logger.getLogger(TimerController.class.getName()).log(
						Level.SEVERE,
						"计时器创建出现时间错误 delay:" + delay + " period:" + period,
						new Throwable());
				return null;
			}

			Timer timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, delay, period);
			return timer;
		} catch (Exception e) {
			Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE,
					null, e);
			return null;
		}
	}
}
