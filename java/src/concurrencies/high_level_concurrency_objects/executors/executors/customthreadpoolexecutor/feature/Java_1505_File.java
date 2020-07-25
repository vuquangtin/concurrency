package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;
import static java.nio.file.Files.exists;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.Paths.get;
import static java.util.logging.Logger.getLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java_1505_File {

	private static final Logger LOGGER = getLogger(Java_1505_File.class.getName());
	
	public static void main(String[] args) {
		new Java_1505_File().listFilesForFolderJava8();
	}
	
	
	private void listFilesForFolderJava8() {
		String pathPrefix = "e:/WorkspaceEasyStepEclipseSync/java-javax/src/main/";
		List<String> pathDateList = Arrays.asList("resources", "resources2", "resources3");
		String pathFile = "data3.xml";
		
		List<Path> pathList = new ArrayList<>();
		out.print("\n\nAbsolute Path of the File...\n");
		pathDateList.stream().forEach(p1 -> {
			pathList.add(get(pathPrefix, p1, pathFile));
		});
		out.println(pathList);
		
		
		out.print("\n\nFile content...\n");
		pathDateList.stream().forEach(p2 -> {
			//Predicate<Path> existFilePred = p4 -> Files.exists(p4, NOFOLLOW_LINKS);
			Predicate<Path> existFilePred2 = p4 -> isExists(p4);
			
			try (Stream<Path> pathStream = Files.walk(Paths.get(pathPrefix, p2, pathFile))) {
				//List<String> pathList2 = pathStream.parallel().map(p4 -> p4.toString()).collect(Collectors.toList());
				//out.println(pathList2);
				Path path3 = pathStream.parallel().filter(existFilePred2).collect(Collectors.toList()).get(0);
				
				if(exists(path3)){
					try {
						List<String> contentList = Files.readAllLines(path3);
						out.println("contentList\n" + contentList);
						String contentStr = new String(Files.readAllBytes(path3));
						out.println("contentStr\n" + contentStr);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				LOGGER.log(Level.SEVERE, null, ex);
			}
		});
		
	}
	
	private boolean isExists(Path path) {
		return exists(path, new LinkOption[] { NOFOLLOW_LINKS });
	}
	
	private void checkEmptyFile() {
		out.println("empty file");
	}
	
	private String readFromInputStream(InputStream inputStream) throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}
	
}
