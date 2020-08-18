package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Java_1120_UtilFunction {

	public static void main(String args[]) {
		
		// Predicate Functional Interface: check file Java_1110_UtilStreamOperation

		new Java_1120_UtilFunction().ConsumerFunctionalInterface();

		new Java_1120_UtilFunction().functionFunctionalInterface();
		
	}

	ArrayList<String> createNumberInStringList(){
		ArrayList<String> numberInStringList = new ArrayList<>();
		numberInStringList.add("zero");
		numberInStringList.add("one");
		numberInStringList.add("two");
		numberInStringList.add("three");
		numberInStringList.add("four");
		numberInStringList.add("five");
		return numberInStringList;
	}
	
	List<ExampleProductDto> createExampleProductDtoList(){
		List<ExampleProductDto> exampleProductDtoList = Arrays.asList(
			new ExampleProductDto(1, "Product-1", 100),
			new ExampleProductDto(3, "Product-3", 300),
			new ExampleProductDto(2, "Product-2", 200),
			new ExampleProductDto(5, "Product-5", 500),
			new ExampleProductDto(4, "Product-4", 400));
		return exampleProductDtoList;
	}
	
	
	
	void ConsumerFunctionalInterface() {
		List<String> numberInStringList = createNumberInStringList();
		
		out.println("\n\nTraverse Original List Or All Strings (Consumer interface & accept method)");
		
		out.println("\nusing Consumer Inner Class, accept() and forEach()");
		EsExampleCustomFunctionConsumer customFunctionConsumer = new EsExampleCustomFunctionConsumer();
		numberInStringList.forEach(customFunctionConsumer);
		
		out.println("\n\nusing Anonymous Consumer Inner Class, accept() and forEach()");
		numberInStringList.forEach(new Consumer<String>() {
			@Override
			public void accept(String str) {
				out.print(str + ", ");
			}
		});
		
		out.println("\n\nusing Embedded Consumer Functional Interface, accept(), forEach() and Lambda Exp");
		Consumer<String> stringConsumer = out::println; //try with other Consumer Functional Interface Pending
		numberInStringList.forEach(str -> stringConsumer.accept(str));
		
		out.println("\nusing Parameterized Consumer Functional Interface, accept(), forEach() and Lambda Exp");
		printStringList(numberInStringList, out::println);
		
		out.println("\nNote: Consumer represents an operation that accepts a single input argument and returns no result");
		out.println("Consumer.accept(): Performs this operation on the given argument");
	}

	//Here Consumer Inner Class can be reused
	class EsExampleCustomFunctionConsumer implements Consumer<String> {
		@Override
		public void accept(String str) {
			out.print(str + ", ");
		}
	}

	void printStringList(List<String> stringList, Consumer<String> stringConsumer) {
		stringList.forEach(str -> stringConsumer.accept(str));
	}

		
	
	void functionFunctionalInterface() {
		out.println("\n\nFunction Functional Interface using apply()");
		List<ExampleProductDto> exampleProductDtoList = createExampleProductDtoList();
		
		
		out.println("\na. for input-output primitive data-type");
		int x = 10;
		numberMathFunction(x, num -> num + 20);
		numberMathFunction(x, num -> "Function".length() + num - 100);
		
		Function<Integer, Integer> intIntMultiplyFunction = num -> num * 10;
		numberMathFunction(x, intIntMultiplyFunction);
		
		
		
		out.println("\n\nb. using Function<Obj, String> and list of user-defined objects as parameters");
		Function<ExampleProductDto, String> ipObjOpStringFunction1 = (ExampleProductDto obj) -> {
			return obj.getProductName();
		};
		List<String> filteredList3 = filterName(exampleProductDtoList, ipObjOpStringFunction1);
		filteredList3.forEach(str -> out.print(str + ", "));
		
		
		
		out.println("\n\nc. using Function<Obj, String>.andThen(Function<String, String>)");
		Function<ExampleProductDto, String> inputObjOutputStringFunction = (ExampleProductDto obj) -> {
			return obj.getProductName();
		};
		Function<String, String> substring9Function = (String s) -> s.substring(0, 9);
		List<String> filteredList = filterName(exampleProductDtoList, inputObjOutputStringFunction.andThen(substring9Function));
		filteredList.forEach(str -> out.print(str + ", "));
		
		
		
		out.println("\n\nd. using Function<Obj, String>.compose(Function<Obj, Obj>)");
		Function<ExampleProductDto, String> ipObjOpStringFunction2 = (ExampleProductDto obj) -> {
			return obj.getProductName();
		};
		Function<ExampleProductDto, ExampleProductDto> ipObjOpObjNameFunction = (ExampleProductDto obj) -> {
			String name = obj.getProductName().substring(0, obj.getProductName().indexOf("-") );
			obj.setProductName(name);
			return obj;
		};
		List<String> filteredList2 = filterName(exampleProductDtoList, ipObjOpStringFunction2.compose(ipObjOpObjNameFunction));
		filteredList2.forEach(str -> out.print(str + ", "));
		
		
		
		out.println("\n\ne. using Function.identity() for input user-defined list");
		/*Function<ExampleProductDto, String> ipObjOpStringFunction3 = (ExampleProductDto obj) -> {
			return obj.getProductName();
		};*/
		List<ExampleProductDto> empNameListInitials = applyIdentityToEmpList(exampleProductDtoList, Function.identity());
		empNameListInitials.forEach(out::println);
		
		
		out.println("\n\nFunction represents a function that accepts one argument and produces a result");
		out.println("Function.apply(): Applies this function to the given argument");
		out.println("Function.andThen(): Returns a composed function that first applies this function to its input, and then applies the after function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the composed function");
		out.println("Function.compose(): Returns a composed function that first applies the before function to its input, and then applies this function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the composed function");
		out.println("Function.identity(): Returns a function that always returns its input argument");
	}

	void numberMathFunction(int num, Function<Integer, Integer> intIntMathOperationFunction) {
		out.print(intIntMathOperationFunction.apply(num) + ", ");
	}
	
	List<String> filterName(List<ExampleProductDto> productList, Function<ExampleProductDto, String> inputObjOutputStringFunction) {
		List<String> productList2 = new ArrayList<String>();
		for (ExampleProductDto tempProductList : productList) {
			productList2.add(inputObjOutputStringFunction.apply(tempProductList));
		}
		return productList2;
	}
	
	List<ExampleProductDto> applyIdentityToEmpList(List<ExampleProductDto> productList,
			Function<ExampleProductDto, ExampleProductDto> ipObjOpObjFunction) {
		List<ExampleProductDto> productList2 = new ArrayList<ExampleProductDto>();
		for (ExampleProductDto obj : productList) {
			productList2.add(ipObjOpObjFunction.apply(obj));
		}
		return productList2;
	}

}
