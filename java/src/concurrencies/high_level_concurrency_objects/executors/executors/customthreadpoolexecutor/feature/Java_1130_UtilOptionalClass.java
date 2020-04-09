package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalDouble;

public class Java_1130_UtilOptionalClass {
	
	public static void main(String args[]) {
		new Java_1130_UtilOptionalClass().optionalOperation();
		new Java_1130_UtilOptionalClass().optionalObjectContainer();
	}
	
	
	void optionalOperation(){
		String test = "abc,def,123";
		
		String[] output1 = test.split(",");
		out.println(output1[0]);
		
		String[] output2 = test.split("\\,");
		out.println(output2[1]);
		
		String noComma = "1,2,3,4,5,6";
		String[] noArray = noComma.split(",");
		out.println(Integer.parseInt(noArray[0]));
		
		
		out.println("\n\nOptional");
		Optional<String> prodId = Optional.of("1,2,3,4,5,6");
		out.println(prodId);
		prodId.ifPresent(p -> {
			out.println(p);
		});
		
		// First convert the comma separated String into Arrays
		// Convert the Arrays into list for iteration purpose
		prodId.ifPresent(p -> {
			Arrays.asList(p.split(",")).forEach(pId -> out.println(pId));
		});
		
		
		Optional<String> gender = Optional.of("MALE");
        Optional<String> emptyGender = Optional.empty();
        if (gender.isPresent()) {
            out.println("Value available.");
        } else {
            out.println("Value not available.");
        }
        gender.ifPresent(g -> out.println("In gender Option, value available."));
        //condition failed, no output print
        emptyGender.ifPresent(g -> System.out.println("In emptyGender Option, value available."));
		
	}
	
	
	
	void optionalObjectContainer() {
		// it is mandatory to assign some value otherwise throw NullPointerException, if Optional is not used
		Integer value1 = null;
		Integer value2 = new Integer(10);

		// Optional.ofNullable(): Returns an Optional with the specified value, if not-null; 
		// otherwise returns an empty Optional
		Optional<Integer> optionalValue1 = Optional.ofNullable(value1);

		// Optional.of() Returns an Optional with the specified present not-null value
		// and throws NullPointerException if passed parameter is null
		Optional<Integer> optionalValue2 = Optional.of(value2);
		
		out.println("Sum: " + sum(optionalValue1, optionalValue2));
		
		double value3 = 3.14;
		OptionalDouble optionalDoubleValue3 = OptionalDouble.of(value3); 
		if (optionalDoubleValue3.isPresent()) {
			out.println("Optional Double Value3: " + optionalDoubleValue3.getAsDouble());
		} else {
			out.println("Optional Double Value3 is not present");
		}
	}

	Integer sum(Optional<Integer> optionalValue1, Optional<Integer> optionalValue2) {
		// optionalValue.isPresent(): checks the value is present or not
		out.println("\n\noptionalValue1.isPresent(): " + optionalValue1.isPresent());
		out.println("optionalValue2.isPresent(): " + optionalValue2.isPresent());

		// optionalValue.orElse(): returns the value if present otherwise returns the default value passed
		// optionalValue.get(): gets the value, value should be present
		return optionalValue1.orElse(new Integer(2)) + optionalValue2.get();
	}
}
