package concurrencies.frameworks.hystrixs.test;

import org.junit.Test;

import concurrencies.frameworks.hystrixs.ObservableCommandHelloWorld;
import rx.Observable;

import java.util.Iterator;

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