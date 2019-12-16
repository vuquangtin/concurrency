package executors.completions;
import java.util.concurrent.Callable;
/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */


public class ExemploCallable implements Callable<ExemploCallable> {

	private String name;
	private boolean valid;

	public ExemploCallable() {

	}

	public ExemploCallable call() throws Exception {
		Thread.sleep(1000);

		ExemploCallable result = new ExemploCallable();
		result.setName(Thread.currentThread().getName());

		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "ExemploCallable [name=" + name + ", valid=" + valid + "]";
	}

}