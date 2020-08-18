package concurrency.java.blocking;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Board {

	public void commitNewValues() {

	}

	public Board getSubBoard(int count) {
		return new Board();
	}

	public boolean hasConverged() {
		return false;
	}

	public int getMaxX() {
		return 2;
	}

	public int getMaxY() {
		return 2;
	}

	public void setNewValue(int x, int y, int max) {

	}

	public void waitForConvergence() {

	}
}
