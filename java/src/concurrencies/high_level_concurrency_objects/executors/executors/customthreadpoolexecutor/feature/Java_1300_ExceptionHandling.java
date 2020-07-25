package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Java_1300_ExceptionHandling {

	final String absolutePath = "e:/WorkspaceEasyStepEclipseSync/java-javax/src/main/resources/data.txt";

	public static void main(String[] args) {
		new Java_1300_ExceptionHandling().automaticResourceManagementJava6();
		new Java_1300_ExceptionHandling().multipleCatchJava7_();
	}

	public void automaticResourceManagementJava6() {
		out.println("\n\nResource Management in Java6");
		FileInputStream fis = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(new File(absolutePath));
			br = new BufferedReader(new InputStreamReader(fis));
			if (br.ready()) {
				out.println("File Contents: " + br.readLine());
				out.println("read(): " + br.read());
			}

			if ((new File(absolutePath)).isHidden()) {
				out.println("File is Hidden");
				// fis: explicitly throw exception
				throw new FileNotFoundException("FileNotFoundException");
			} else {
				out.println("File is not Hidden");
			}
		} catch (FileNotFoundException ex) {
			out.println("data.txt is not found");
		} catch (IOException ex) {
			out.println("Can't read the file");
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException ie) {
				out.println("Failed to close files");
			}
		}
	}

	public void multipleCatchJava7_() {
		out.println("\n\nTry with Resource OR Automatic Resource Management and Multiple Catch in Java7");
		out.flush();
		try (FileInputStream fis = new FileInputStream(new File(absolutePath));
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));) {
			if (br.ready()) {
				String str = br.readLine();
				SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
				Date date = format.parse(str);
				out.println("Date in ddMMyyyy: " + date);
				out.println("Date in format ddMMyyyy: " + new SimpleDateFormat("ddMMyyyy").parse(str));
			}
		} catch (ParseException | FileNotFoundException e) {
			out.println("data.txt is not found");
			e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			out.println("Can't read the file");
			e.printStackTrace();
		}
	}

}
