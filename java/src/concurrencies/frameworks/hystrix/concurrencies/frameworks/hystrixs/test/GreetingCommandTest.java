package concurrencies.frameworks.hystrixs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.Future;

import org.junit.Test;

import concurrencies.frameworks.hystrixs.async.GreetingCommand;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class GreetingCommandTest {

	@Test
	public void testAsynchronousGreeting1() throws Exception {
		GreetingCommand command1 = new GreetingCommand("World");
		GreetingCommand command2 = new GreetingCommand("John");

		Future fWorld = new GreetingCommand("World").queue();
		Future fJohn = new GreetingCommand("John").queue();

		assertEquals("Hello World!", fWorld.get());
		assertEquals("Hello John!", fJohn.get());

		assertFalse(command1.isResponseFromFallback());
		assertFalse(command2.isResponseFromFallback());
	}

	@Test
	public void testAsynchronousGreetingFallBack() throws Exception {
		try {
			GreetingCommand command = new GreetingCommand(null);
			Future fNull = command.queue();
			assertEquals("Hello Guest!", fNull.get());
			assertTrue(command.isResponseFromFallback());
		} catch (Exception e) {
			fail("we should not get an exception as we return a fallback");
		}
	}
}
