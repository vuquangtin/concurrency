/**
 * 
 */
package com.handson.miscellaneous;

import static java.lang.System.out;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sveera
 *
 */
public class Example04AnnotationsInheritance {

	/*
	 * Example on extending annotation with another annotation.Subclass can inherit
	 * annotations only If Annotation is defined using @Inherited meta annotation
	 * (In this example check @ExtendingAnnotation). To read annotations defined on
	 * another annotation (meta-annotations) checkout spring utility class
	 * https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/
	 * core/annotation/AnnotationUtils.html
	 */
	public static void main(String[] args) {
		ExtendingAnnotation[] extendingAnnotations = AnnotaionClass.class
				.getDeclaredAnnotationsByType(ExtendingAnnotation.class);
		out.println("Printing @" + ExtendingAnnotation.class.getSimpleName() + " annotaion details defined on class "
				+ AnnotaionClass.class.getSimpleName());
		for (ExtendingAnnotation extendingAnnotation : extendingAnnotations) {
			out.println("Extended Count Value " + extendingAnnotation.extendedCount());
			/*
			 * Below approach to read meta-annotation (annotation applied on another
			 * annotation) is not working.However, We can directly read meta-annotation
			 * using Annotation.class variable(Explained in next outer for loop) . This need
			 * to be further checked.
			 */
			for (BaseAnnotation baseAnnotation : extendingAnnotation.getClass()
					.getAnnotationsByType(BaseAnnotation.class)) {
				out.println("Printing @" + BaseAnnotation.class.getSimpleName() + " annotaion details defined on class "
						+ AnnotaionClass.class.getSimpleName());
				out.println("Count Value " + baseAnnotation.count());
			}

		}

		out.println();
		out.println();

		for (BaseAnnotation baseAnnotation : ExtendingAnnotation.class
				.getDeclaredAnnotationsByType(BaseAnnotation.class)) {
			out.println("Printing @" + BaseAnnotation.class.getSimpleName() + " annotaion details defined on @ "
					+ ExtendingAnnotation.class.getSimpleName() + " annotation");
			out.println("Count Value " + baseAnnotation.count());
		}

		out.println();
		out.println();

		ExtendingAnnotation[] extendedClassAnoations = ClassExtendingAnnotaionClass.class
				.getAnnotationsByType(ExtendingAnnotation.class);
		out.println("Printing @" + ExtendingAnnotation.class.getSimpleName()
				+ " annotaion details extended from super class on sub class "
				+ ClassExtendingAnnotaionClass.class.getSimpleName());
		for (ExtendingAnnotation extendingAnnotation : extendedClassAnoations) {
			out.println("Extended Count Value " + extendingAnnotation.extendedCount());
		}

	}

	@Target(value = { ElementType.TYPE, ElementType.ANNOTATION_TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface BaseAnnotation {
		int count() default 1;
	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@BaseAnnotation(count = 40)
	@Inherited
	private static @interface ExtendingAnnotation {
		int extendedCount();
	}

	@ExtendingAnnotation(extendedCount = 20)
	private static class AnnotaionClass {

	}

	private static class ClassExtendingAnnotaionClass extends AnnotaionClass {

	}
}
