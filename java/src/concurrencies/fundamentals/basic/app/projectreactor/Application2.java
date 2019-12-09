package basic.app.projectreactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Application2 {

	public static void main(String[] args) throws InterruptedException {
		Flux.just(5, 10, 23, 25).log()
			.flatMap(n -> Flux.just(n * 2)).subscribeOn(Schedulers.elastic())
			.subscribe(value -> System.out.println(Thread.currentThread().getName()+ " : " + value));

		Thread.sleep(10000);
	}

}