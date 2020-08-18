package executors.customthreadpoolexecutor.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Java_1011_UtilListContains {

	public static void main(String[] args) {
		
		List<Integer> numberList = new ArrayList<Integer>();
		numberList.add(20);
		numberList.add(25);
		numberList.add(10);
		numberList.add(15);
		
		boolean retval = numberList.contains(10);
		System.out.println("Search number using contains(): " + retval);
		
		
		
		List<String> stringList = new ArrayList<String>();
		stringList.add("  A");
		stringList.add("B  ");
		stringList.add("  C  ");
		stringList.add("D");
		
		String traineeName = "b";
		String traineeNameWithoutCaseSensetive = "D";
		
		// require to iterate through list for case-sensitive match
		boolean isRegisteredUser5 = stringList.contains("B  ");
		System.out.println("Check presence of String using contains(): " + isRegisteredUser5);
		
		for(String str: stringList) {
			// does not support Predicate
			// ArrayList.contains() uses equals(), not object identity
		    if(str.trim().toLowerCase().contains(traineeName.toLowerCase())){
		    	System.out.println("Check presence of String using contains()");
		    }
		}
		
		
		
		// matching without iteration
		boolean isRegisteredUser2 = stringList.stream().anyMatch(traineeNameWithoutCaseSensetive::equalsIgnoreCase);
		System.out.println("Check presence of String using anyMatch(): " + isRegisteredUser2);
		
		
		// matching with iteration
		Predicate<String> userNameAsTraineePredicate = str -> str.trim().equalsIgnoreCase(traineeName);
		boolean isRegisteredUser6 = stringList.stream().anyMatch(userNameAsTraineePredicate);
		System.out.println("Check presence of String using anyMatch(): " + isRegisteredUser6);
		
		// Pending > not working
		/*if (userNameAsTraineePredicate.test(stringList)) {
			out.println(stringList);
		}*/
		
		// Pending > not working
		boolean isRegisteredUser7 = stringList.stream().allMatch(userNameAsTraineePredicate);
		System.out.println("Check presence of String using allMatch(): " + isRegisteredUser7);
		
		// findFirst() for first occurance
		boolean isRegisteredUser3 = stringList.stream().filter(userNameAsTraineePredicate).findFirst().isPresent();
		System.out.println("Check presence of String using filter(): " + isRegisteredUser3);
		
		
		// Pending
		/*boolean isRegisteredUser4 = traineeList.stream().map(ExampleProductDto::getTraineeName)
				.filter(traineeName::equals).findFirst().isPresent();		
		System.out.println("using map(): " + isRegisteredUser4);*/
		
	}

	// Pending
	public Object isCurrentUserRegisteredForCourse(List<ExampleProductDto> traineeList, String traineeName){
	    return traineeList.stream().filter(o -> o.getProductName().equals(traineeName));
	    		//forEach(o -> {});
	}
	
}
