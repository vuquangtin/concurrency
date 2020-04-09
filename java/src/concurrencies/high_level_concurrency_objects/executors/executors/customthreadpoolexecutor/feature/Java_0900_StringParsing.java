package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.Scanner;

public class Java_0900_StringParsing {

	public static void main(String[] args) {
		new Java_0900_StringParsing().multipleCatchJava7();
	}
	
	
	public void multipleCatchJava7() {
		out.println("\n\nRegular expression matching");
		String regex = "\\d\\d";// \d\d
		String input = "1b2c335f456";
		Scanner scanner;
		try {
			// out.println("Enter input by typing in eclipse console and press enter: ");
			// scanner = new Scanner(System.in);
			// OR
			scanner = new Scanner(input);

			do {
				input = scanner.findInLine(regex); // next(), nextLine()
				out.println(input);
			} while (input != null && scanner.hasNextLine());
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
	}
}
