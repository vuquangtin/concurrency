package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Simple Java program to read a text file into String in Java. You can use this
 * one liner code for quickly reading file and displaying as String.
 */
public class Java_1510_FileCharacterEncoding {

	public static void main(String args[]) throws IOException {
		
		final String path = "e:/WorkspaceEasyStepEclipseSync/java-javax/src/main/resources/data.txt"; 

		out.println("\nJava-7: Uses Platform's default character encoding");
		out.println(new String(Files.readAllBytes(Paths.get(path))));
		out.println(new String(Files.readAllBytes(Paths.get(path))));

		
		out.println("\nJava-8: Uses UTF-8 character encoding");
		List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
		StringBuilder sb = new StringBuilder(1024);
		for (String line : lines) {
			sb.append(line);
		}
		String fromFile = sb.toString();
		out.println("String created by reading File in Java");
		out.println(fromFile);
		
		
		out.println("\nJava-8: read a file in one line using static import");
		out.println(new String(readAllBytes(get(path))));
		
		
		out.println("\nJava-8: read File using Stream for further optimization");
        Files.lines(Paths.get(path), StandardCharsets.UTF_8).forEach(out::println);

		
		
	}
}
