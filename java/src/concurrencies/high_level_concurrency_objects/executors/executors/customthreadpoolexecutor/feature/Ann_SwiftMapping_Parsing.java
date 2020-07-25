package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;
import static java.lang.System.err;

@Ann_SwiftMapping(isinDetails="US-000402625-0")
class Ann_SwiftMapping_App {
	
}

@Ann_SwiftMapping()
class Ann_SwiftMapping_App2 {
	
}

public class Ann_SwiftMapping_Parsing {
	
	public static void main(String args[]) {
		Ann_SwiftMapping swift1 = Ann_SwiftMapping_App.class.getAnnotation(Ann_SwiftMapping.class);
		out.println("ISIN Details 1: " + swift1.isinDetails());
		
		Ann_SwiftMapping swift2 = Ann_SwiftMapping_App2.class.getAnnotation(Ann_SwiftMapping.class);
		err.println("ISIN Details 2: " + swift2.isinDetails());
		
		// Pending
		//Ann_SwiftMapping targetValue = new DynamicGreeter("UK-987654321-0");
		//alterAnnotationValueJDK8(Greetings.class, Ann_SwiftMapping.class, targetValue);
		Ann_SwiftMapping swift3 = Ann_SwiftMapping_App.class.getAnnotation(Ann_SwiftMapping.class);
		out.println("ISIN Details 3: " + swift3.isinDetails());
	}

}
