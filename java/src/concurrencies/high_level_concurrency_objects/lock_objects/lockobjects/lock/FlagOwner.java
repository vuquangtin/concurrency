package lockobjects.lock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class FlagOwner implements FlagAccess {
	private AtomicBoolean _flag;

	public boolean getFlag() {
		return _flag.get();
	}

	public void setFlag(boolean value) {
		_flag.set(value);
	}
}

interface FlagAccess {
	public boolean getFlag();

	public void setFlag(boolean value);
}

class Worker extends Thread {
	private FlagAccess _access;

	public Worker(FlagAccess access) {
		_access = access;
	}

	public void run() {
		_access.getFlag();
		// ...
		_access.setFlag(true);
	}
}