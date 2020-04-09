package executors.customthreadpoolexecutor.feature;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Ann_SwiftMapping {
	
	public String isinDetails() default "not known";
	
}
