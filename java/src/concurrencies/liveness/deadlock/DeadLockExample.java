package deadlock;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/17 20:07
 */
public class DeadLockExample {

    String o1 = "Lock";
    String o2 = "Step";

    Thread t1 = (new Thread("Printer1") {

        @Override
        public void run() {
            while (true){
                synchronized (o1){
                    synchronized (o2){
                        System.out.println(getName()+": "+o1 + o2);
                    }
                }
            }
        }
    });

    Thread t2 = (new Thread("Printer2") {
        @Override
        public void run() {
            while (true){
                synchronized (o2){
                    synchronized (o1){
                        System.out.println(getName()+": "+o2 + o1);
                    }
                }
            }
        }
    });

    public static void main(String[] args) {
        DeadLockExample lock = new DeadLockExample();
        lock.t1.start();
        lock.t2.start();
    }

}