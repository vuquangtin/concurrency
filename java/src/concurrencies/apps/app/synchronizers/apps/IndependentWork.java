package app.synchronizers.apps;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class IndependentWork {
    private static int processors;

    public static void main(String... args) throws InterruptedException {

        processors = Runtime.getRuntime().availableProcessors();

        // состояния потоков
        stateExample();

        // приоритеты потоков
        priorityExample();

        // нежелательная блокировка - deadlock
        deadLock();

        // нежелательная блокировка - starvation
        starVation();

        // нежелательная блокировка - livelock
        liveLockExample();

        // join
        callJoin();
    }

    // нежелательная блокировка - deadlock
    static class A {
        static final B b = new B();
    }

    static class B {
        static final A a = new A();
    }

    public static void deadLock() {
        System.out.println("нежелательная блокировка - deadlock ->");
        // Если закомментировать следующую строчку
        // приложение блокируется
        new B();
        new Thread(A::new).start();
        new B();
    }

    // нежелательная блокировка - starvation
    public static class StarvationDemo {
        private static Object sharedObj = new Object();

        public void demoStart () {
            JFrame frame = createFrame();
            frame.setLayout(new FlowLayout(FlowLayout.LEFT));

            for (int i = 0; i < 5; i++) {
                ProgressThread progressThread = new ProgressThread();
                frame.add(progressThread.getProgressComponent());
                progressThread.start();
            }

            JButton btnExit = new JButton("Exit");

            btnExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { // если нажата кнопка

                    // Кнопка Exit
                    frame.removeAll();
                    frame.dispose(); // чистим память
                    System.exit(0);
                }
            });

            frame.add(btnExit);

            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
        }

        private static JFrame createFrame () {
            JFrame frame = new JFrame("Starvation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(new Dimension(300, 200));
            return frame;
        }

        private static class ProgressThread extends Thread {
            JProgressBar progressBar;

            ProgressThread () {
                progressBar = new JProgressBar();
                progressBar.setString(this.getName());
                progressBar.setStringPainted(true);
            }

            JComponent getProgressComponent () {
                return progressBar;
            }

            @Override
            public void run () {

                int c = 0;
                while (true) {
                    synchronized (sharedObj) {
                        if (c == 100) {
                            c = 0;
                        }
                        progressBar.setValue(++c);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private  static void starVation() {
        System.out.println("нежелательная блокировка - starvation -> ");

        StarvationDemo starvationDemo = new StarvationDemo();
        starvationDemo.demoStart();
    }

    // нежелательная блокировка - livelock
    private static class LiveLock {

        static class Resource {
            private User user;

            public Resource(User user) {
                this.user = user;
            }

            public User getUser() {
                return this.user;
            }

            public synchronized void setUser(User user) {
                this.user = user;
            }

            public synchronized void use() {
                System.out.println(user.name + "used resource");
            }
        }

        public static class User {
            String name;
            boolean usedResource;

            public User(String name) {
                this.name = name;
                this.usedResource = false;
            }

            public String getName() {
                return name;
            }

            public boolean isUsedResource() {
                return usedResource;
            }

            public void setUsedResource(boolean usedResource) {
                this.usedResource = usedResource;
            }

            public void jointUse(Resource resource, User user) {
                while (!usedResource) {
                    if (resource.getUser() != this) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            continue;
                        }
                        continue;
                    }

                    if (!user.isUsedResource()) {
                        System.out.println(this.getName() + " ждёт, пока " + user.getName() + " использует ресурс");
                        resource.setUser(user);
                        continue;
                    }

                    resource.use();
                    usedResource = true;
                    System.out.println(this.getName() + " обработал ресурс!");
                    resource.setUser(user);
                }

            }
        }
    }

    public static void liveLockExample() throws InterruptedException {
        System.out.println("нежелательная блокировка - livelock ->");

        final LiveLock.User user1 = new LiveLock.User("Пользователь № 1");
        final LiveLock.User user2 = new LiveLock.User("Пользователь № 2");

        final LiveLock.Resource resource = new LiveLock.Resource(user1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                user1.jointUse(resource, user2);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                user2.jointUse(resource, user1);
            }
        }).start();

        Thread.sleep(10);

        System.out.println("Прерываем Livelock !!!");
        user2.setUsedResource(true);
        user1.setUsedResource(true);
    }

    // join
    public static void callJoin() throws InterruptedException {
        System.out.println("вызов join ->");

        System.out.println("Новый поток:");
        ThreadExample threadExample = new ThreadExample();
        threadExample.printInfo();

        threadExample.start();
        Thread.sleep(50);
        threadExample.printInfo();

        System.out.println("Ждём завершения потока 2 сек.:");
        threadExample.join(2000);

        System.out.println("Удивительно, но... ");
        threadExample.printInfo();
        System.out.println("А сейчас... ");
        threadExample.setMustBeRun(false);
        threadExample.join();

        threadExample.printInfo();
    }

    public static class ThreadExample extends Thread {
        private boolean mustBeRun = true;
        private boolean mustBeWait = false;
        private boolean needSay = false;

        public boolean isNeedYield() {
            return needYield;
        }

        public void setNeedYield(boolean needYield) {
            this.needYield = needYield;
        }

        private boolean needYield = false;
        private static String message="Start message";

        ThreadExample() {}

        ThreadExample(String name) {
            super(name);
        }

        public static void setMess(String mess) {
            System.out.println(Thread.currentThread().getName() + " set message = " + mess);
            message = mess;
        }

        private void say() {
            System.out.println("I am " + this.getName() + " say: " + message);
        }

        public void setMustBeRun(boolean mustBeRun) {
            System.out.println(this.getName() + ".mustBeRun = " + mustBeRun);
            this.mustBeRun = mustBeRun;
        }
        public void setMustBeWait(boolean mustBeWait) {
            System.out.println(this.getName() + ".mustBeWait = " + mustBeWait);
            this.mustBeWait = mustBeWait;
        }

        public void setNeedSay(boolean needSay){
            System.out.println(this.getName() + ".needSay = " + needSay);
            this.needSay = needSay;
        }

        @Override
        public void start(){
            super.start();
            System.out.println("Start " + this.getName() + " priority = " + this.getPriority());
        }

        @Override
        public void run() {
            while(mustBeRun){
                if(mustBeWait) {
                    try {
                        Thread.currentThread().sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(needYield != true) {
                        if (needSay) {
                            say();
                        }
                    } else {
                        Thread.yield();
                    }
                }
            }
        }

        public void printInfo() {
            System.out.println(this.getName() +
                    " priority = " + this.getPriority() + " state = " + this.getState());
        }
    }

    // состояния потоков
    private static void stateExample() throws InterruptedException {
        System.out.println("состояния потоков ->");

        ThreadExample thread1 = new ThreadExample();
        thread1.printInfo();
        thread1.start();
        Thread.sleep(10);
        thread1.setPriority(Thread.MAX_PRIORITY);
        thread1.printInfo();
        thread1.setMustBeWait(true);
        Thread.sleep(10);
        thread1.printInfo();
        thread1.setMustBeWait(false);
        Thread.sleep(60);
        thread1.printInfo();
        thread1.setMustBeRun(false);
        Thread.sleep(10);
        thread1.printInfo();
    }

    // приоритеты потоков
    private static void priorityExample() throws InterruptedException {
        int MAX_PRIORITIES=3, MAX_THREADS=2;

        // 6 потоков по 2 с приоритетами мин., норм, и макс.
        System.out.println("приоритеты потоков ->");
        ThreadExample[][] threads = new ThreadExample[MAX_PRIORITIES][MAX_THREADS];
        for (int i = 0; i < MAX_PRIORITIES; i++) {
            for (int j = 0; j < MAX_THREADS; j++) {
                if (i == 0) {
                    threads[i][j] = new ThreadExample("Thread_MIN_" + Integer.toString(j));

                    threads[i][j].setPriority(Thread.MIN_PRIORITY);
                } else {
                    if (i == 2) {
                        threads[i][j] = new ThreadExample("Thread_MAX_" + Integer.toString(j));
                        threads[i][j].setPriority(Thread.MAX_PRIORITY);
                    } else {
                        threads[i][j] = new ThreadExample("Thread_NORM_" + Integer.toString(j));
                    }

                }
            }
        }

        for (int i = 0; i < MAX_PRIORITIES; i++)
            for(int j = 0; j < MAX_THREADS; j++)
                threads[i][j].printInfo();

         //Стартуем потоки
        for (int i = 0; i < MAX_PRIORITIES; i++)
            for(int j = 0;j < MAX_THREADS;j++)
                threads[i][j].start();

        for (int i = 0; i < MAX_PRIORITIES; i++)
            for(int j = 0;j < MAX_THREADS;j++)
                threads[i][j].printInfo();

        // Потоки начинают выдавать сообщения
        for (int i = 0; i < MAX_PRIORITIES; i++)
            for(int j = 0;j < MAX_THREADS;j++)
                threads[i][j].setNeedSay(true);
        Thread.sleep(7);

        for (int i = 0; i < MAX_PRIORITIES; i++)
            for(int j = 0;j < MAX_THREADS;j++)
                threads[i][j].printInfo();

        ThreadExample.setMess("NEW MESSAGE");
        Thread.sleep(7);

        for (int i = 0; i < MAX_PRIORITIES; i++)
            for(int j = 0;j < MAX_THREADS;j++)
                threads[i][j].setMustBeRun(false);

        for (int i = 0; i < MAX_PRIORITIES; i++)
            for(int j = 0;j < MAX_THREADS;j++)
                threads[i][j].printInfo();

        Thread.sleep(7);

        for (int i = 0; i < MAX_PRIORITIES; i++)
            for(int j = 0;j < MAX_THREADS;j++)
                threads[i][j].printInfo();
    }
}