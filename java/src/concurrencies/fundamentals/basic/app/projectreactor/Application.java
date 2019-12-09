package basic.app.projectreactor;

import reactor.core.publisher.Flux;

/***
 * https://huongdanjava.com/vi/concurrency-trong-project-reactor-voi-scheduler.
 * html
 * 
 * @author vuquangtin
 *
 */
public class Application {

	public static void main(String[] args) {
		Flux.just(5, 10, 23, 25).log().flatMap(n -> Flux.just(n * 2))
				.subscribe(System.out::println);
	}

}