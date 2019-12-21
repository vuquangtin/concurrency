package basic.thread1;

public class ShutdownHookMainClass {
	  public static void main(String[] args) throws Exception {
	    Object f = new Object() {
	      public void finalize() {
	        System.out.println("Running finalize()");
	      }
	    };
	    Runtime.getRuntime().addShutdownHook(new Thread() {
	      public void run() {
	        System.out.println("Running Shutdown Hook");
	      }
	    });

	    f = null;
	    System.gc();

	    System.out.println("Calling System.exit()");
	    System.exit(0);
	  }
	}