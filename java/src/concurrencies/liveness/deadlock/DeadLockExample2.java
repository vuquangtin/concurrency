package deadlock;

/**
 * reference:  https://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/18 22:59
 */
public class DeadLockExample2 {
    static class Friend {
        private final String name;

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public synchronized void bow(Friend bower) {
            System.out.format("%s: %s has bowed to me! %n", this.name, bower.getName());
            bower.bowBack(this);

        }

        public synchronized void bowBack(Friend bower) {
            System.out.format("%s: %s has bowed back to me!%n", this.name, bower.getName());
        }
    }


    public static void main(String[] args) {
        final Friend tom = new Friend("Tom");
        final Friend sam = new Friend("Sam");

        new Thread(() -> tom.bow(sam)).start();

        new Thread(() -> sam.bow(tom)).start();
    }

}