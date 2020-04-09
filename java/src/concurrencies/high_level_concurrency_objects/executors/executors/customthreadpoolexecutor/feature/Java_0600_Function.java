package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.Arrays;
import java.util.List;

public class Java_0600_Function {

	public static void main(String[] args) {
		
		List<String> numberInStringArrayList = new Java_0600_Function().createNumberInStringArrayList();
		new Java_0600_Function().varArgsJava6(numberInStringArrayList);
		varArgsJava7(numberInStringArrayList);

	}
	
	
	List<String> createNumberInStringArrayList(){
		String[] numberInStringArray = { "ZERO", "one", "tWO", "Three", "FOUR", "five"};
		List<String> numberInStringArrayList = Arrays.asList(numberInStringArray);
		return numberInStringArrayList;
	}
	
	@SuppressWarnings({"unchecked"})
	public void varArgsJava6(List<String>... numberInStringArrayList){
		out.println("\n\nVariable args with @SuppressWarnings 'Assign parameter to the field List<String>[]'");
	    for(List<String> list : numberInStringArrayList){
	        out.println(list);
	    }
	}
	
	@SafeVarargs
	public static void varArgsJava7(List<String>... lists){
		out.println("\n\nVariable args with @SafeVarargs & static method");
	    for(List<String> list : lists){
	        out.println(list);
	    }
	}


}
