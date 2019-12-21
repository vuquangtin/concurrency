package basic.thread1;

//Java environment:
//java version "1.6.0_0"
//OpenJDK Runtime Environment (IcedTea6 1.6.1) (6b16-1.6.1-3ubuntu3)
//OpenJDK 64-Bit Server VM (build 14.0-b16, mixed mode)
public class NoVisibilityTest2 extends Thread {
 boolean  keepRunning = true;
 public static void main(String[] args) throws InterruptedException {
	 NoVisibilityTest2 t = new NoVisibilityTest2();
     t.start();
     Thread.sleep(1000);
     t.keepRunning = false;
     System.out.println(System.currentTimeMillis() + ": keepRunning is "+t.keepRunning);
 }
 public void run() {
     while (keepRunning) 
     {    	 
    	 //System.out.println(System.currentTimeMillis());
    	 //System.out.println(System.currentTimeMillis() + ": keepRunning is "+keepRunning);
     }
 }
}