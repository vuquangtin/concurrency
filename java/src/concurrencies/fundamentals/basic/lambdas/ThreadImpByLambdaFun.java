package basic.lambdas;
public class ThreadImpByLambdaFun {
	
	public static void main(String[] args) {
		new Thread(() -> { while(true){ System.out.println("Hello"); }}).start();
	}

}