package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.Arrays;
import java.util.List;

public class Java_8080_InnerClass_LambdaExpression {
	
	public static void main(String args[]) {
		
		new Java_8080_InnerClass_LambdaExpression().lambdaExpressionOfTypeFunctionalInterface();
		
		new Java_8080_InnerClass_LambdaExpression().userDefinedAnonymousClassPreJava8();
		
		new Java_8080_InnerClass_LambdaExpression().staticMethodFunctionalInterface();
	}	
	
	List<String> createNumberInStringArrayList(){
		String[] numberInStringArray = { "ZERO", "one", "tWO", "Three", "FOUR", "five"};
		List<String> numberInStringArrayList = Arrays.asList(numberInStringArray);
		return numberInStringArrayList;
	}
	
	List<Integer> createNumberArrayList(){
		Integer[] numberArray = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };
		List<Integer> numberArrayList = Arrays.asList(numberArray);
		return numberArrayList;
	}
	
	
	void lambdaExpressionOfTypeFunctionalInterface(){
		
		// below expression is applicable only when target type of the expression is functional interface
		// here function doesn't have any parameter but it is required to use empty parenthesis
		Message1 message1 = (msg) -> out.println(msg);
		message1.display("\n\nHere target type of the lambda expression is functional interface");
		
		
		
		Message2 message21 = new Message2Impl();
		message21.display("\nDefine method of Functional Interface in a Class");
		message21.defaultDisplay(); // super keyword is not allowed in this case
		message21.defaultDisplay2();
		Message2.staticDisplay();
		
		Message2 message23 = new Message2(){
			@Override
			public void display(String message) {
				out.println(message);
			}
		};
		message23.display("Define overloaded method in Inner Annonymous Class");
		
		Message2 message25 = message -> out.println(message);
		message25.display("Replaced Annonymous class using Lambda expression without parenthesis");
		
		Message2 message27 = (message) -> out.println(message);
		message27.display("Replaced Annonymous class using Lambda expression with parenthesis");
		
		
		
		out.println("\n\nwith type declaration");
		MathOper2 addMathOper2 = (int a, int b) -> a + b;
		out.println("10 + 5 = " + addMathOper2.operation(10, 5));
		// Pass the overridden function definition reference to another function as argument
		out.println("10 + 5 = " + operate(10, 5, addMathOper2));

		
		out.println("\nwithout type declaration");
		MathOper2 subtractMathOper2 = (a, b) -> a - b;
		out.println("10 - 5 = " + subtractMathOper2.operation(10, 5));

		
		out.println("\nwith return statement along with curly braces");
		MathOper2 multiplyMathOper2 = (int a, int b) -> {
			return a * b;
		};
		out.println("10 x 5 = " + multiplyMathOper2.operation(10, 5));

		
		out.println("\nwithout return statement and without curly braces"); 
		MathOper2 divisionMathOper2 = (int a, int b) -> a / b;
		out.println("10 / 5 = " + divisionMathOper2.operation(10, 5));

		
	}
	
	//Message1 interface is functional interface only when it contains only one function declaration
	@FunctionalInterface
	interface Message1 {
		void display(String msg);
	}
	
	@FunctionalInterface
	interface Message2 {
		
		void display(String message);
		
		// but multiple default function can be declared in functional interface using default keyword
		default void defaultDisplay() {
			out.println("Default Display Method of Functional Interface");
		}
		
		default void defaultDisplay2() {
			out.println("Default Display Method 2 of Functional Interface");
		}
		
		static void staticDisplay() {
			out.println("Static Display Method of Functional Interface");
		}
		
	}
	
	class Message2Impl implements Message2 {
		@Override
		public void display(String message){
			out.println(message);
		}
	}
	
	@FunctionalInterface
	interface MathOper2 {
		int operation(int a, int b);
	}
	// function call statement: out.println("10 + 5 = " + operate(10, 5, addMathOper2));
	int operate(int a, int b, MathOper2 mathOper2) {
		return mathOper2.operation(a, b);
	}
	
	
	
	void userDefinedAnonymousClassPreJava8() {
		out.println("\n\nImplement interface using Anonymous Class using Pre Java8");
		MathOper product = new MathOper() {
			@Override
			public int multiply(int x, int y) {
				return x * y;
			}
		};
		out.println("Square of 2: " + product.square(2));
		out.println("Cube of 2: " + product.cube(2));
		
		
		out.println("\nImplements abstract method of interface using Lambda Expression");
		MathOper lambda = (x, y) -> x * y;
		out.println("Square of 3: " + lambda.square(3));
		out.println("Cube of 3: " + lambda.cube(3));
	}
	
	// Functional Interface
	// only one abstract method is allowed
	// multiple non-abstract default method is allowed using default keyword
	@FunctionalInterface
	interface MathOper {

		int multiply(int a, int b);

		default int square(int a) {
			return multiply(a, a);
		}

		default int cube(int a) {
			return multiply(multiply(a, a), a);
		}
	}
	
	
	
	void staticMethodFunctionalInterface(){		
		out.println("\n\nx. Static Method");
		out.println("\nx.a. using Anonymous class");
		Message3 msg3Obj = new Message3() {
			@Override
			public void display() {
				Java_8080_InnerClass_LambdaExpression.staticMethod();
			}
		};
		msg3Obj.display();
		
		
		out.println("\nx.b. using Lambda Expression");
		Message3 msg3Obj2 = () -> Java_8080_InnerClass_LambdaExpression.staticMethod();
		msg3Obj2.display();
	}
	@FunctionalInterface
	interface Message3 {
		public void display();
	}
	static void staticMethod() {
		out.println("Static Method called.");
	}
	
}
