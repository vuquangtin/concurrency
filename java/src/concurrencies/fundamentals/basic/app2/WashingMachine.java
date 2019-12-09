package basic.app2;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class WashingMachine {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WashingMachine.class);
	private final DelayProvider delayProvider;
	private WashingMachineState washingMachineState;

	/**
	 * Creates a new instance of WashingMachine
	 */
	public WashingMachine() {
		this((interval, timeUnit, task) -> {
			try {
				Thread.sleep(timeUnit.toMillis(interval));
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			task.run();
		});
	}

	/**
	 * Creates a new instance of WashingMachine using provided delayProvider.
	 * This constructor is used only for unit testing purposes.
	 */
	public WashingMachine(DelayProvider delayProvider) {
		this.delayProvider = delayProvider;
		this.washingMachineState = WashingMachineState.ENABLED;
	}

	public WashingMachineState getWashingMachineState() {
		return washingMachineState;
	}

	/**
	 * Method responsible for washing if the object is in appropriate state
	 */
	public void wash() {
		synchronized (this) {
			LOGGER.info("{}: Actual machine state: {}", Thread.currentThread()
					.getName(), getWashingMachineState());
			if (washingMachineState == WashingMachineState.WASHING) {
				LOGGER.error("ERROR: Cannot wash if the machine has been already washing!");
				return;
			}
			washingMachineState = WashingMachineState.WASHING;
		}
		LOGGER.info("{}: Doing the washing", Thread.currentThread().getName());

		this.delayProvider.executeAfterDelay(50, TimeUnit.MILLISECONDS,
				this::endOfWashing);
	}

	/**
	 * Method responsible of ending the washing by changing machine state
	 */
	public synchronized void endOfWashing() {
		washingMachineState = WashingMachineState.ENABLED;
		LOGGER.info("{}: Washing completed.", Thread.currentThread().getId());
	}

}