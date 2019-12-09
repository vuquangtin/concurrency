package basic.singletons;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import basic.annotations.NotRecommend;
import basic.annotations.NotThreadSafe;

/**
 * 这个错误会在构造期间导致 this 引用的逸出, 新线程总会在所属对象初始化完成之前看到它. 如果要在构造函数中创建线程, 那么不要启动他,
 * 而是采用一个专有的 start() 来统一启动线程 在对象未完成构造之前不可以将其发布
 *
 * 此时会导致出现两种问题 1. 发布线程以外的任何线程都可以看到被发布对象的过期的值 2. 线程看到的被发布对象的引用是最新的,
 * 然而被发布对象的状态确实过期的
 */

@Slf4j
@NotRecommend
@NotThreadSafe
public class Escape {
	static Logger logger = Logger.getLogger(Escape.class.getName());
	private int thisCanBeEscape = 0;

	public Escape() {
		new InnerClass();
	}

	private class InnerClass {
		public InnerClass() {
			logger.info("{}" + Escape.this.thisCanBeEscape);
		}
	}

	public static void main(String[] args) {
		new Escape();
	}
}
