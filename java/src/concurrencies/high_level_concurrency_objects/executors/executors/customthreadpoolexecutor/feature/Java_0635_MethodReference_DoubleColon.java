package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;

//Need to explore more (map, reduce, ::) Pending
public class Java_0635_MethodReference_DoubleColon {

	public static void main(String args[]) {
		
		new Java_0635_MethodReference_DoubleColon().lambdaOperationMethodReference();
		
		new Java_0635_MethodReference_DoubleColon().methodReference();
	}

	String[] createNumberInStringWithoutNewKeywordArray(){
		String[] numberInStringArray = { "ZERO", "one", "tWO", "Three", "FOUR", "five"};
		return numberInStringArray;
	}
	
	
	void lambdaOperationMethodReference() {
		//List<String> stringList = Arrays.asList(createNumberInStringWithoutNewKeywordArray());
		Java_0635_MethodReference_DoubleColon mr = new Java_0635_MethodReference_DoubleColon();
		
		
		//out.println("\nconvert into method reference way step1");
		//stringOperationPredicateFunction2(str -> true);
		
		
		out.println("\n\nMethod Reference (Static and Instance)");
		// check how argument is passed into isExists3 using method reference
		mr.stringOperationPredicateFunction2(mr::isExists3);
		
		out.println("\n\nMethod Reference (without object.)");
		stringOperationPredicateFunction2(mr::isExists3);
		
		out.println("\n\nStatic method reference");
		stringOperationPredicateFunction2(StaticMethodNonFunctionalClass::isExists2);
		
		out.println("\n\nInstance method reference");
		stringOperationPredicateFunction2(this::isExists3);
	}
	
	void stringOperationPredicateFunction2(Predicate<String> conditionOrPredicate) {
		List<String> stringList = Arrays.asList(createNumberInStringWithoutNewKeywordArray());
		stringList.stream().forEach(str -> {
			if (conditionOrPredicate.test(str)) {
				out.print(str + ", ");
			}
		});
	}
	boolean isExists3(String str) {
		return true;
	}
	
	
	
	void methodReference() {
		out.println("\n\n\nMethod Reference (no pair of parenthesis at the end of method name)"); 
		out.println("OR C++ double colon scope resolution operator for method reference");
		out.println("Error Msg when only method name is used: nonStaticMethod cannot be resolved to a variable");
		
		out.println("\n1. Non-Static Method Reference ");
		out.println("\n1.a. built-in print");
		List<String> numberInStringList = Arrays.asList(createNumberInStringWithoutNewKeywordArray());
		numberInStringList.forEach(out::println);

		out.println("\n1.b. user-defined nonStaticMethod");
		try (AutoCloseable ac = new Java_0635_MethodReference_DoubleColon()::nonStaticMethod) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		out.println("\n\n2. Static Method Reference");
		out.println("\n2.a. using Method Reference");
		ReferenceFunctionalInterface methodReferenceObj = Java_0635_MethodReference_DoubleColon::staticMethod;
		methodReferenceObj.referenceDemo();
		
		out.println("\n2.b. using Method Reference Pending"); //not working Pending
		IntBinaryOperator staticMethod2 = Java_0635_MethodReference_DoubleColon::staticMethod2;
		staticMethod2(1, 2);
		staticMethod2.applyAsInt(3, 5);
		
		
		
		out.println("\n\n3. Constructor References Pending");//Pending
	}
	void nonStaticMethod() {
		System.out.println("Non-Static Method called");
	}
	
	@FunctionalInterface
	interface ReferenceFunctionalInterface {
		public void referenceDemo();
	}
	static void staticMethod() {
		out.println("Static Method called");
	}
	
	static int staticMethod2(int num1, int num2) {
		out.println(num1 + num2);
		return num1 + num2;
	}
	
	

}

class StaticMethodNonFunctionalClass {
	
	static boolean isExists2(String str) {
		return true;
	}
}
