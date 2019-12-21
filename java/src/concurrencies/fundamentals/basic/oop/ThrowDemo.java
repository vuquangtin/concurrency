package basic.oop;

import java.io.IOException;

class ArraySizeException extends RuntimeException {
	ArraySizeException() // constructor
	{
		super("You have passed an illegal array size");
	}
}

public class ThrowDemo {
	int size, array[];

	ThrowDemo(int s) {
		size = s;
		//try {
			checkSize();
		//} catch (ArraySizeException e) {
		//	System.out.println(e);
		//}
	}

	void checkSize() throws ArraySizeException {
		if (size < 0)
			throw new ArraySizeException();
		else
			System.out.println("The array size is ok.");
		array = new int[3];
		for (int i = 0; i < 3; i++)
			array[i] = i + 1;
	}

	public static void main(String arg[]) {
		new ThrowDemo(Integer.parseInt("-5"));
	}
}