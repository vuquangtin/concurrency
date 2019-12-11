package concurrencies.frameworks.hystrixs.test;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import concurrencies.frameworks.hystrixs.ObservableUserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class ObservableUserServiceTest {

	@Autowired
	private ObservableUserService observableUserService;

	@Test
	public void testObserve() {
		Iterator<String> iterator = observableUserService.getUserById(30L)
				.toBlocking().getIterator();
		while (iterator.hasNext()) {
			System.out.println("===============" + iterator.next());
		}
	}

	@Test
	public void testToObservable() {
		Iterator<String> iterator = observableUserService.getUserByName("王五")
				.toBlocking().getIterator();
		while (iterator.hasNext()) {
			System.out.println("===============" + iterator.next());
		}
	}
}
