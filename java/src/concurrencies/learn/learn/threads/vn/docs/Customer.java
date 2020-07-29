package learn.threads.vn.docs;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Customer {
	private int balance = 1000;

	public Customer() {
		System.out.println("Tai khoan cua ban la " + balance);
	}

	private synchronized void withdraw(int amount) {
		System.out.println("Giao dich rut tien dang thuc hien " + amount
				+ "...");
		if (balance < amount) {
			System.out.println("Cannot withdraw!");
			try {
				wait();
			} catch (InterruptedException ie) {
				System.out.println(ie.toString());
			}
		}
		balance -= amount;
		System.out
				.println("Rut tien thanh cong. Tai khoan hien tai cua ban la "
						+ balance);
	}

	private synchronized void deposit(int amount) {
		System.out.println("Giao dich nap tien " + amount + "...");
		balance += amount;
		System.out
				.println("Nap tien thanh cong. Tai khoan hien tai cua ban la "
						+ balance);
		notify();
	}

	public static void main(String[] args) {
		Customer c = new Customer();
		Thread t1 = new Thread() {
			public void run() {
				c.withdraw(2000);
			}
		};
		t1.start();

		Thread t2 = new Thread() {
			public void run() {
				c.deposit(3000);
			}
		};
		t2.start();
	}
}