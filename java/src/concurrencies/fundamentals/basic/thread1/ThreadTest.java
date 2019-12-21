package basic.thread1;

public class ThreadTest {

    public static boolean toolA = false;
    public static boolean toolB = false;

    public static int started = 0;
    public static int finished = 0;

    public static void main(String[] args) {
        new productB().start();
        new productA().start();
        new productA().start();
        new productB().start();
        new productB().start();


    }

}

class productA extends Thread {

    @Override
    public void run() {
        ThreadTest a = new ThreadTest();
        int index = a.started;
        a.started++;

        System.out.println("Product A" + index + " started!");
        while (a.toolA == true) {
            //wait A
        }
        a.toolA = true;
        //use A
        a.toolA = false;


        while (a.toolB == true) {
            //wait B
        }
        a.toolB = true;
        //Use B
        //Finish should be here

        a.finished++;
        System.out.println("Product A" + index + " finished!");

        a.toolB = false;
        System.out.println("Finished product: " + a.finished);

    }

}

class productB extends Thread {

    @Override
    public void run() {
        ThreadTest a = new ThreadTest();

        int index = a.started;
        a.started++;
        System.out.println("Product B" + index + " started!");

        while (a.toolA == true) {
            //Wait for A
        }
        a.toolA = true;

        //Use A

        a.toolA = false;


        while (a.toolB == true) {

        }


        a.toolB = true;


        a.finished++;
        //useB
        System.out.println("Product B" + index + " finished!");

        a.toolB = false;
        System.out.println("Finished product: " + a.finished);
    }

}
