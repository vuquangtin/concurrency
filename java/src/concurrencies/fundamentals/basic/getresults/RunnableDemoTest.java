package basic.getresults;

public class RunnableDemoTest {
    public static void main(String[] args) throws Exception {
        RunnableDemo[] randomeTasks = new RunnableDemo[5];

        for(int i = 0; i < 5; i++) {
            randomeTasks[i] = new RunnableDemo();
            Thread t = new Thread(randomeTasks[i]);
            t.start();
        }

        for(int i = 0; i < 5; i++) {
            System.out.println(randomeTasks[i].get());
        }
    }
}