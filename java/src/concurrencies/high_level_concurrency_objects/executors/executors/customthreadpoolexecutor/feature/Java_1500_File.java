package executors.customthreadpoolexecutor.feature;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.getAttribute;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.isExecutable;
import static java.nio.file.Files.isHidden;
import static java.nio.file.Files.isReadable;
import static java.nio.file.Files.isRegularFile;
import static java.nio.file.Files.isWritable;
import static java.nio.file.Files.newDirectoryStream;
import static java.nio.file.Files.notExists;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.Paths.get;
import static java.nio.file.attribute.FileTime.fromMillis;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Java_1500_File {

	private static final Logger LOGGER = getLogger(Java_1500_File.class.getName());
	
	private static final Path PATH_WITHOUT_FILE_NAME = get("src/main", "resources", "");
	private static final Path PATH_WITH_FILE_NAME = get("src/main", "resources", "data.txt");

	public static void main(String[] args) {
		
		new Java_1500_File().lambdaOperationTraverseFileContent();
		
		new Java_1500_File().lambdaOperationMethodReference();
		
		new Java_1500_File().predicateInInnerClass();
		new Java_1500_File().predicateWithLambda();
		
		new Java_1500_File().listFilesForFolderJava7();
		new Java_1500_File().listFilesForFolderJava8();
	}
	
	
	private void lambdaOperationTraverseFileContent() {
		try {
			if (exists(PATH_WITH_FILE_NAME, NOFOLLOW_LINKS)) {
				LOGGER.config(PATH_WITH_FILE_NAME.toAbsolutePath().toString());
				List<String> stringList = readAllLines(PATH_WITH_FILE_NAME);
				
				out.print("\nTraverse File Content using Lambda Expression\n");
				stringList.forEach(str -> out.print(str));
			} else {
				LOGGER.info("");
				LOGGER.log(SEVERE, "{0} , Doesn't exists", PATH_WITH_FILE_NAME.toAbsolutePath());
			}
		} catch (IOException ex) {
			LOGGER.severe(ex.getMessage());
		}
		
		
		
		out.println("\n\nFile Contents using UTF-8 character encoding");
		final String pathFile3 = "e:/WorkspaceEasyStepEclipseSync/java-javax/src/main/resources/data.txt";
		final Path path = new File(pathFile3).toPath();
		try (Stream<String> stringStream = Files.lines(path, StandardCharsets.UTF_8)) {
			stringStream.onClose(() -> out.println("File End!")).forEach(out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	private void lambdaOperationMethodReference() {
		out.println("\n\nMethod Reference (Static and Instance)");
		Java_1500_File mr = new Java_1500_File();
		
		mr.doFilterAndPrintPath(mr::isExists);
		
		out.println("\nStatic method reference");
		doFilterAndPrintPath(FilesDirTests::isAccessible);
		
		out.println("\nInstance method reference");
		if(isNotExists(PATH_WITH_FILE_NAME)){
			doFilterAndPrintPath(this::isNotExists);
		}
		
	}
	
	private void doFilterAndPrintPath(Predicate<Path> pathPredicate) {
		try {
			newDirectoryStream(PATH_WITHOUT_FILE_NAME).forEach(path -> {
				if (pathPredicate.test(path)) {
					out.println(path.getFileName());
				}
			});
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
	}
	

	private boolean isNotExists(Path path) {
		return !notExists(path, new LinkOption[] { NOFOLLOW_LINKS });
	}

	private boolean isExists(Path path) {
		return exists(path, new LinkOption[] { NOFOLLOW_LINKS });
	}
	
	
	private void predicateInInnerClass() {
		out.println("\n\nFind file name using Predicate<Path> and DirectoryStream<Path>");
		Predicate<Path> pathPredicate = new Predicate<Path>() {
			@Override
			public boolean test(Path path) {
				return isDirectory(path, NOFOLLOW_LINKS);
			}
		};

		try (DirectoryStream<Path> pathDirectoryStream = newDirectoryStream(PATH_WITHOUT_FILE_NAME)) {
			for (Path path : pathDirectoryStream) {
				if (pathPredicate.test(path)) {
					out.println(path.getFileName());
				}
			}
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
	}

	private void predicateWithLambda() {
		out.println("\n\nAll Path contents");
		Predicate<Path> pathPredicate1 = (path) -> true;
		doFilterAndPrintPath(pathPredicate1);
		
		out.println("\nPrint dirs only");
		doFilterAndPrintPath((path) -> isDirectory(path, NOFOLLOW_LINKS));
		
		out.println("\nPrint hidden files/dirs only");
		Predicate<Path> hiddenFilter = (path) -> {
			boolean hidden = false;
			try {
				hidden = isHidden(path);
			} catch (IOException e) {
			}
			return hidden;
		};
		doFilterAndPrintPath(hiddenFilter);
		
		out.println("\nPrint today modified files/dirs only");
		Predicate<Path> pathPredicate = (path) -> {
			long currentTime = 0, modifiedTime = 0;
			try {
				currentTime = fromMillis(currentTimeMillis()).to(DAYS);
				modifiedTime = ((FileTime) getAttribute(path, "basic:lastModifiedTime", NOFOLLOW_LINKS)).to(DAYS);
			} catch (IOException e) {
			}
			return currentTime == modifiedTime;
		};
		doFilterAndPrintPath(pathPredicate);
	}
	
	
	
	private void listFilesForFolderJava7() {
		final String pathFile2 = "e:/WorkspaceEasyStepEclipseSync/java-javax/src/main/resources";
		final File folder = new File(pathFile2);
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	//listFilesForFolderJava7(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}
	
	
	private void listFilesForFolderJava8() {
		out.print("\n\nAbsolute Path of the File...\n");
		final String pathFile2 = "e:/WorkspaceEasyStepEclipseSync/java-javax/src/main/resources";
		try (Stream<Path> paths = Files.walk(Paths.get(pathFile2))) {
		    paths.filter(Files::isRegularFile)
		        .forEach(out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

class FilesDirTests {
	
	public static boolean isAccessible(Path p) {
		return isRegularFile(p) & isReadable(p) & isExecutable(p) & isWritable(p);
	}
}
