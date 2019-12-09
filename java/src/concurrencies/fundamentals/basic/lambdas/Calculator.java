package basic.lambdas;


public class Calculator {

	/*
	 * Function interface
	 * */
	interface IntegerMath {
		int operation(int a, int b);
	}

	public int operateBinary(int a, int b, IntegerMath op) {
		return op.operation(a, b);
	}

	/*
	 * Lambda function at class  level
	 * */
	IntegerMath add1 = (a, b) -> a + b;

	public static void main(String[] args) {

		Calculator a1 = new Calculator();

		IntegerMath add = (a, b) -> a + b;
		IntegerMath sub = (a, b) -> a - b;
		IntegerMath mul = (a, b) -> a * b;

		/*
		 * Call Lambda function in static block main()
		 * */
		System.out.println("lambda sum:"+a1.operateBinary(10, 30,add));
		System.out.println("lambda sub:"+a1.operateBinary(10, 30,sub));
		System.out.println("lambda mul:"+a1.operateBinary(10, 30,mul));
	}

	
	public void printSum(){
		/*
		 * Call lambda function add1
		 * */
		System.out.println(add1.operation(10,20));
	}
	
}