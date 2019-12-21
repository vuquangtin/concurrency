package basic.oop;

import java.io.File;
import java.util.jar.JarFile;

/***
 * @see https://coderanch.com/t/541986/java/conflict-due-class-package-structure
 * @author admin
 *
 */
public class SamePackageStructure {
	public static void main(String... args) throws Exception {
		JarFile jar = new JarFile(new File("/Users/admin/Documents/workspace/FBDetector/libs/jcalendar-1.4.jar"));
		try {
			MyClassWrapper.Factory factory = new MyClassWrapper.Factory(jar);

			MyClassWrapper instance = factory.build("Java");
			instance.doSomethingElse(true);
		} finally {
			jar.close();
		}
		System.out.println(f(2));
	}

	public static int f(int n) {
		try {
			int r = n * n;
			return r;
		} finally {
			if (n == 2)
				return 0;
		}
	}
}
