package concurrencies.frameworks.hystrixs.test;

import java.util.Iterator;

import org.junit.Test;

import rx.Observable;
import concurrencies.frameworks.hystrixs.ObservableCommandHelloWorld;

public class ObservableCommandHelloWorldTest {

	@Test
	public void testObservable() {
		Observable<String> observable = new ObservableCommandHelloWorld("World")
				.observe();

		Iterator<String> iterator = observable.toBlocking().getIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	@Test
	public void testToObservable() {
		Observable<String> observable = new ObservableCommandHelloWorld("World")
				.observe();
		Iterator<String> iterator = observable.toBlocking().getIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

}