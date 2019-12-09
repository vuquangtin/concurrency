package volatilekeyword;

class SimpleExampleBuggy {
    static class Looper extends Thread {
        boolean finish = false;

        @Override public void run() {
            while (!finish) {
                // do something
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Looper looper = new Looper();
        looper.start();
        Thread.sleep(1000); // wait 1s
        looper.finish = true;
        System.out.println("Wait for Looper to terminate...");
        looper.join();
        System.out.println("Done.");
    }
}