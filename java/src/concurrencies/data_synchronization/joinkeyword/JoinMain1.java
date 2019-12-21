package joinkeyword;

/***
 * https://stackoverflow.com/questions/20495414/thread-join-equivalent-in-
 * executor
 * 
 * @author vuquangtin
 *
 */
public class JoinMain1 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		IntHolder aHolder = new IntHolder();
		aHolder.Number = 0;

		IncrementorThread A = new IncrementorThread(1, aHolder);
		IncrementorThread B = new IncrementorThread(2, aHolder);
		IncrementorThread C = new IncrementorThread(3, aHolder);

		A.start();
		B.start();
		C.start();

		A.join();
		B.join();
		C.join();
		System.out.println("All threads completed...");

	}

}