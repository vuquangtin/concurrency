package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

public class Java_0050_Literal {

	public static void main(String[] args) {
		new Java_0050_Literal().binaryLiteralJava6();
		new Java_0050_Literal().binaryLiteralJava7();

		new Java_0050_Literal().numericLiteralUnderscore();
	}
	
	
	public void binaryLiteralJava6() {
		out.println("\n\nBinary Literal");
        int binary = 8;
        if(binary == 8) {
            out.println("binary == 8: " + (binary == 8));
        } else{
            out.println("binary == 8: " + (binary == 8));
        }
	}

	public void binaryLiteralJava7() {
		int binary = 0b1000; // 2^3 = 8
		int binary0bUnderscore = 0b10_00;
		int binary0BUnderscore = 0B10_00;
		int number = 8;
		if(binary == number) {
			out.println("0b1000 == 8: " + (binary == number));
		} else {
			out.println("0b1000 != 8: " + (binary == number));
		}
		if(binary0bUnderscore == number) {
			out.println("0b10_00 == 8: " + (binary0bUnderscore == number));
		}
		if(binary0BUnderscore == number) {
			out.println("0B10_00 == 8: " + (binary0BUnderscore == number));
		}
	}
	
	
	
	/*************   Numeric Literals with Underscore   ***************/
	int billion = 1_000_000_000;  // 10^9
	long creditCardNumber =  1234_4567_8901_2345L; //16 digit number
	long ssn = 777_99_8888L;
	double pi = 3.1415_9265;
	float  pif = 3.14_15_92_65f;

	public void numericLiteralUnderscore() {
		out.println("\n\nNumeric Literals with Underscore");
		int oneMillion_ = 1_000_000;
		int oneMillion = 1000000;
		if (oneMillion_ == oneMillion) {
			out.println("1_000_000 ==  1000000: " + (oneMillion_ == oneMillion));
		} else {
			out.println("1_000_000 ==  1000000: " + (oneMillion_ == oneMillion));
		}
	}

}
