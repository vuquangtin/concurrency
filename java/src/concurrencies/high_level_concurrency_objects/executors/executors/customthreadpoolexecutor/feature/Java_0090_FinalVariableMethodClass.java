package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

public class Java_0090_FinalVariableMethodClass {

	public static void main(String args[]) {
		new Java_0090_FinalVariableMethodClass().effectiveFinalVariableLambdaJava8();
	}
	
	
	void effectiveFinalVariableLambdaJava8() {
		
		final String effectiveFinal = "\nEffectively Final Variable ";
		MessageDisplayService2 displayMessageService = message -> out.println(effectiveFinal);
		displayMessageService.display("Java8");
		
		
		String nonFinal = "Non final local variable";
		Runnable runnable1 = new Runnable() {
			@Override
			public void run() {
				out.println(nonFinal + " using annonymous class");
				// error: Local variable nonFinal defined in an enclosing scope must be final or effectively final
				//nonFinal = "Can I change non-final variable inside anonymous class";
			}
		};
		new Thread(runnable1).start();
		
		
		Runnable runnable2 = () -> {
			out.println(nonFinal + " using lambda expression 1");
		};
		new Thread(runnable2).start();
		
	}
	
	@FunctionalInterface
	interface MessageDisplayService2 {
		void display(String message);
	}
	
}
