package executors.customthreadpoolexecutor.feature;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Java7_0351_ForkJoinPoolMain {
	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		Java7_0351_SearchDirectory searchDir = new Java7_0351_SearchDirectory("C:\\Progs\\.m2\\repository", "com");
		// SearchDirectory searchDir = new
		// SearchDirectory("F:\\vapt\\SFMSNEFT","NEFT");
		pool.execute(searchDir);
		List<String> fileList = searchDir.join();
		System.out.println("The Search returned following files : " + fileList);
	}
}