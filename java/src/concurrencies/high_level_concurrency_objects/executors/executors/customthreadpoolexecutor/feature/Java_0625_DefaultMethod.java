package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

public class Java_0625_DefaultMethod {

	public static void main(String args[]) {
		new Java_0625_DefaultMethod().defaultStaticMethodDemo();
	}

	void defaultStaticMethodDemo() {
		Message9 message9 = new MessageImpl();
		message9.defaultDisplay();
	}

	interface Message9 {
		
		default void defaultDisplay() {
			out.println("default method called from Message9");
		}

		static void staticDisplay() {
			out.println("static method called from Message9");
		}
	}

	interface Message11 {

		default void defaultDisplay() {
			out.println("default method called from Message11");
		}
	}

	class MessageImpl implements Message9, Message11 {
		
		@Override
		public void defaultDisplay() {
			out.println("Override default method in MessageImpl class");
			Message9.super.defaultDisplay();
			Message11.super.defaultDisplay();
			Message9.staticDisplay();
		}
		
	}
}