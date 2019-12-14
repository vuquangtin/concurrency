package synchronizers.semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
class Restaurant {
	  private Semaphore tables;

	  public Restaurant(int tablesCount) {
	    this.tables = new Semaphore(tablesCount);
	  }

	  public void getTable(int customerID) {
	    try {
	      System.out.println("Customer  #" + customerID
	          + "  is trying  to  get  a  table.");
	      tables.acquire();
	      System.out.println("Customer #" + customerID + "  got  a  table.");
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	  }

	  public void returnTable(int customerID) {
	    System.out.println("Customer #" + customerID + "  returned a  table.");
	    tables.release();
	  }
	}
	class RestaurantCustomer extends Thread {
	  private Restaurant r;
	  private int customerID;
	  private static final Random random = new Random();

	  public RestaurantCustomer(Restaurant r, int customerID) {
	    this.r = r;
	    this.customerID = customerID;
	  }
	  public void run() {
	    r.getTable(this.customerID); // Get a table
	    try {
	      int eatingTime = random.nextInt(30) + 1;
	      System.out.println("Customer #" + this.customerID
	          + "  will eat for " + eatingTime + "  seconds.");
	      Thread.sleep(eatingTime * 1000);
	      System.out.println("Customer #" + this.customerID
	          + "  is done  eating.");
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } finally {
	      r.returnTable(this.customerID);
	    }
	  }
	}
	public class SemaphoreMain{
	  public static void main(String[] args) {
	    Restaurant restaurant = new Restaurant(2);
	    for (int i = 1; i <= 5; i++) {
	      RestaurantCustomer c = new RestaurantCustomer(restaurant, i);
	      c.start();
	    }
	  }
	}