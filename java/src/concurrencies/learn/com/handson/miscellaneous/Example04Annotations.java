/**
 * 
 */
package com.handson.miscellaneous;

import static java.lang.System.out;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.stream.Stream;

/**
 * @author sveera
 *
 */
public class Example04Annotations {

	/*
	 * Example on annotation's.While defining every annotation should contain two
	 * meta annotations @Retention and @Target. @Retention Will give retention
	 * period (until compile time,until loading time and until runtime ) of
	 * annotation.@Target will provide us where that annotation can be
	 * applied(method,class,annotation,variable etc.).Annotations can store certain
	 * meta data using elements which are represented as abstract method with no
	 * arguments and data type stored by element as return type. Supported data
	 * types by these elements are primitive data types,enum,String,Class type and
	 * Array of previous allowed types. You can provide default value to the
	 * elements using default keyword proceeded by value.
	 */
	public static void main(String[] args) {
		CustomAnnotation[] customAnnotations = CustomAnnotaionClass.class
				.getDeclaredAnnotationsByType(CustomAnnotation.class);
		out.println("Printing annotaion details " + CustomAnnotation.class.getSimpleName() + " defined on class "
				+ CustomAnnotaionClass.class.getSimpleName());
		for (CustomAnnotation customAnnotation : customAnnotations) {
			out.println("Count Value " + customAnnotation.count());
			out.println("Description Value " + customAnnotation.description());
			out.println("Type Value " + customAnnotation.type());
			out.println("Keys are " + Stream.of(customAnnotation.keys()).reduce("", (prev, next) -> prev + next + ","));
		}

	}

	@Target(value = { ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CustomAnnotation {

		int count() default 1;

		String description();

		Class<? extends Number> type();

		String[] keys();
	}

	@CustomAnnotation(description = "Custom Annotation Class", type = Integer.class, keys = { "1", "2" })
	private static class CustomAnnotaionClass {

	}

}
