package com.livelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Livelock {
	// - transaction-1 withdraw 10$ from account “1”
	// - transaction-2 withdraw 10$ from account “2”
	// - transaction-1 failed to deposit in account “2” because “transaction-2”
	// already old the lock on that account. Account “1” refunded
	// - transaction-2 failed to deposit in account “1” because “transaction-1”
	// already old the lock on that account. Account “2” refunded
	// - transaction-1 withdraw 10.000000 from account “1”
	// - transaction-2 withdraw 10.000000 from account “2”
	// - transaction-1 failed to deposit in account “2” because “transaction-2”
	// already old the lock on that account. Account “1” refunded
	// - transaction-2 failed to deposit in account “1” because “transaction-1”
	// already old the lock on that account. Account “2” refunded
	// - and so on …
	public static void main(String[] args) {
		final BankAccount fooAccount = new BankAccount(1, 500d);
		final BankAccount barAccount = new BankAccount(2, 500d);

		new Thread(new Transaction(fooAccount, barAccount, 10d),
				"transaction-1").start();
		new Thread(new Transaction(barAccount, fooAccount, 10d),
				"transaction-2").start();

	}

	static class BankAccount {
		double balance;
		int id;
		Lock lock = new ReentrantLock();

		BankAccount(int id, double balance) {
			this.id = id;
			this.balance = balance;
		}

		boolean withdraw(double amount) {
			if (this.lock.tryLock()) {
				// Wait to simulate io like database access ...
				try {
					Thread.sleep(10l);
				} catch (InterruptedException e) {
				}
				balance -= amount;
				return true;
			}
			return false;
		}

		boolean deposit(double amount) {
			if (this.lock.tryLock()) {
				// Wait to simulate io like database access ...
				try {
					Thread.sleep(10l);
				} catch (InterruptedException e) {
				}
				balance += amount;
				return true;
			}
			return false;
		}

		public boolean tryTransfer(BankAccount destinationAccount, double amount) {
			if (this.withdraw(amount)) {
				if (destinationAccount.deposit(amount)) {
					return true;
				} else {
					// destination account busy, refund source account.
					this.deposit(amount);
				}
			}

			return false;
		}
	}

	static class Transaction implements Runnable {
		private BankAccount sourceAccount, destinationAccount;
		private double amount;

		Transaction(BankAccount sourceAccount, BankAccount destinationAccount,
				double amount) {
			this.sourceAccount = sourceAccount;
			this.destinationAccount = destinationAccount;
			this.amount = amount;
		}

		public void run() {
			while (!sourceAccount.tryTransfer(destinationAccount, amount))
				continue;
			System.out
					.printf("%s completed ", Thread.currentThread().getName());
		}

	}
}
