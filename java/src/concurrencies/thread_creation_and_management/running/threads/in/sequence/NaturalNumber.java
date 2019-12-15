package running.threads.in.sequence;

/***
 * Print natural numbers 1 to 100 using two threads without using wait or
 * synchronized where one thread prints only odd number and other thread prints
 * only even number ? Solution :
 * 
 * @author admin
 *
 */
class OddPrint1 implements Runnable {

	static boolean flag = true;

	public void run() {

		for (int i = 1; i <= 99;) {
			if (flag) {
				System.out.print(i + "\t");
				flag = false;
				i = i + 2;
			}
		}
	}
}

class EvenPrint1 implements Runnable {

	public void run() {

		for (int i = 2; i <= 100;) {
			if (!OddPrint1.flag) {
				System.out.print(i + "\t");
				OddPrint1.flag = true;
				i = i + 2;
			}
		}
	}
}

public class NaturalNumber {

	public static void main(String[] args) {
		new Thread(new OddPrint1()).start();
		new Thread(new EvenPrint1()).start();
	}
}