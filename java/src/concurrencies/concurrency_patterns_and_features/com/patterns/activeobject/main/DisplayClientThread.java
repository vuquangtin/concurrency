package com.patterns.activeobject.main;

import com.patterns.activeobject.impl.ActiveObject;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class DisplayClientThread extends Thread {
	private final ActiveObject activeObject;

	public DisplayClientThread(String name, ActiveObject activeObject) {
		super(name);
		this.activeObject = activeObject;
	}

	public void run() {
		try {
			for (int i = 0; true; i++) {
				// 有返回值的调用
				String string = Thread.currentThread().getName() + " " + i;
				activeObject.displayString(string);
				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
		}
	}
}