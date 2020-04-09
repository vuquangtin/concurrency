package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

public class Java_0400_SelectionStatement {	

	public static void main(String[] args) {			
		new Java_0400_SelectionStatement().stringInSwitchJava_6("Java 6");
		new Java_0400_SelectionStatement().stringInSwitchJava_7("Java 7");
		new Java_0400_SelectionStatement().stringInSwitchJava_7("JAVA-7");
	}

	public void stringInSwitchJava_6(String param) {
		out.println("\n\nSwitch with String");
		final String JAVA5 = "Java 5";
		final String JAVA6 = "Java 6";
		final String JAVA7 = "Java 7";

		if (param.equals(JAVA5)) {
			out.println(JAVA5);
		} else if (param.equals(JAVA6)) {
			out.println("JAVA6: equals with String");
		} else if (param.equals(JAVA7)) {
			out.println(JAVA7);
		}
	}
	
	public void stringInSwitchJava_7(String param) {
		final String JAVA5 = "Java 5";
		final String JAVA6 = "Java 6";
		final String JAVA7 = "Java 7";

		switch(param) {
		case JAVA5:
			out.println(JAVA5);
			break;
		case JAVA6:
			out.println(JAVA6);
			break;
		case JAVA7:
			out.println("JAVA7: Switch with String");
			break;
		case "JAVA-7":
			out.println("JAVA-7: Switch with String");
			break;
		default: out.println("Invalid");
		}
	}

}
