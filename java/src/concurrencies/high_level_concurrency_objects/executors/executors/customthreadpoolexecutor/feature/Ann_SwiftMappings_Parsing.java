package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Ann_SwiftMappings_Parsing {
	
	//@Target(ElementType.FIELD) 
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface SwiftMappings {
		SwiftMapping[] value();
	}

	//@Target(ElementType.FIELD)
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Repeatable(SwiftMappings.class)
	public @interface SwiftMapping {
		String value();
	};

	@SwiftMapping("CorporateAction1")
	@SwiftMapping("CorporateAction1 abstract class")
	public abstract class CorporateAction1 {
		
		private String isin1;

		public String getIsin1() {
			return isin1;
		}

		public void setIsin1(String isin1) {
			this.isin1 = isin1;
		}
		
	}
	
	@SwiftMapping("CorporateAction2")
	@SwiftMapping("CorporateAction2 interface")
	public interface CorporateAction2 {
		
		String isin2="";
		
	}

	public static void main(String[] args) {
		for (SwiftMapping swift1 : CorporateAction1.class.getAnnotationsByType(SwiftMapping.class)) {
			out.println(swift1.value());
		}
		
		for (SwiftMapping swift2 : CorporateAction2.class.getAnnotationsByType(SwiftMapping.class)) {
			out.println(swift2.value());
		}
	}
	
}
