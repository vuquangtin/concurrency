package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Java8_0700_ParallelOperation {

	public static void main(String[] args) {

		new Java8_0700_ParallelOperation().parallelArrays();
		
		//new Java8_0700_ParallelOperation().parallelArrays_();

	}

	void parallelArrays() {
		long[] arrayOfLong = new long[20000];
		Arrays.parallelSetAll(arrayOfLong, index -> ThreadLocalRandom.current().nextInt(1000000));
		Arrays.stream(arrayOfLong).limit(10).forEach(i -> out.print(i + " "));
		out.println();

		Arrays.parallelSort(arrayOfLong);
		Arrays.stream(arrayOfLong).limit(10).forEach(i -> out.print(i + " "));
		out.println();
	}

	//incomplete solution
	/*void parallelArrays_(){
		Collection<Person> roster;
		ConcurrentMap<Person.Sex, List<Person>> byGender =
				roster.parallelStream().collect(Collectors.groupingByConcurrent(Person :: getGender))
	}*/

}
