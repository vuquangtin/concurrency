package concurrencies.frameworks.hystrixs.commands;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import rx.Observable;
import rx.functions.Action1;

public class TestCommandHelloWorld {

	public static void main(String[] args) {

		CommandHelloWorld hellow = new CommandHelloWorld("Harish");

		// Synchronous Execution
		String greetings = hellow.execute();
		System.out.println(" Syncronous - " + greetings);

		// Asynchronous Execution
		Future<String> s = new CommandHelloWorld("Amit").queue();
		try {
			System.out.println(" ASyncronous - " + s.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		// Reactive Execution
		Observable<String> s1 = new CommandHelloWorld("Shree").observe();
		s1.subscribe(new Action1<String>() {
			public void call(String name) {
				System.out.println(" Reactive - " + name);
			}
		});
	}

}
