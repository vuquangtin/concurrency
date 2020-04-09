package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Ann_MethodInfo_App {

	@Override
	@Ann_MethodInfo(author = "Def", comments = "toString method", date = "May 7 2012", revision = 1)
	public String toString() {
		return "Overriden toString method";
	}

	@Deprecated
	@Ann_MethodInfo(comments = "deprecated old method", date = "Nov 17 2012")
	public static void oldMethod() {
		out.println("Don't use oldMethod");
	}

	@SuppressWarnings({ })
	@Ann_MethodInfo(author = "Xyz", comments = "genericsTest method", date = "Jan 3 2012", revision = 10)
	public static void genericsTest() throws FileNotFoundException {
		List<String> l = new ArrayList<String>();
		l.add("abc");
		oldMethod();
	}
}
