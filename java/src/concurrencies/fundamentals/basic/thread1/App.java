package basic.thread1;


class MyTask extends Thread{
    @Override
    public void run(){
        for (int i=1;i<=10;i++){
            System.out.println("@@ Doc #"+i);
        }
    }
}
public class App {
    public static void main(String[] args){
        System.out.println("START");

        MyTask t = new MyTask();
        t.start();

        for (int i=1;i<=10;i++){
            System.out.println("## Doc #"+i);
        }

        System.out.println("END");
       
    }
}