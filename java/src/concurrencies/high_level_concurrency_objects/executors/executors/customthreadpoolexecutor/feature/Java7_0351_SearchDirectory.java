package executors.customthreadpoolexecutor.feature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Java7_0351_SearchDirectory extends RecursiveTask<List<String>> {
	private static final long serialVersionUID = 7570286838840187368L;
	private String directoryName = "";
	private String searchString = "";

	public Java7_0351_SearchDirectory(String directoryName, String searchString) {
		this.directoryName = directoryName;
		this.searchString = searchString;
	}

	@Override
	protected List<String> compute() {
		List<String> matchingFilesList = new ArrayList<>();
		List<Java7_0351_SearchDirectory> taskList = new ArrayList<>();
		File directory = new File(directoryName);

		if (directoryName == null || "".equals(directoryName) || !directory.exists())
			throw new IllegalArgumentException("Directory Name is NOT Valid");

		File[] fileArray = directory.listFiles();
		for (File file : fileArray) {
			if (file.isDirectory()) {
				Java7_0351_SearchDirectory searchDirectory = new Java7_0351_SearchDirectory(directoryName,
						searchString);
				searchDirectory.fork();
				taskList.add(searchDirectory);
			} else {
				if (checkName(file.getName()))
					matchingFilesList.add(file.getPath());
			}
		}
		for (Java7_0351_SearchDirectory sd : taskList) {
			List<String> intermediateResultList = sd.join();
			matchingFilesList.addAll(intermediateResultList);
		}
		return matchingFilesList;
	}

	private boolean checkName(String filename) {
		return filename.contains(searchString);
	}
}
