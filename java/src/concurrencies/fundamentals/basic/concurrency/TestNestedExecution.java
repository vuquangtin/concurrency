package basic.concurrency;

public class TestNestedExecution implements Runnable {
    
    public TestNestedExecution(){
       this(1); 
       new T4();
       new T5();
    }
    
    public TestNestedExecution (int a){
        new T2();
    }
    
    public void run(){}
    
    public static void main(String[] args) {
        new TestNestedExecution();
    }
}

class T2 implements Runnable {
    
    public double sum;
    
    public T2(){
        this(1); 
        double[] arr = new double[1000];
        for (int i = 0; i < 1000; i++) {
            arr[i] = i;
        }
        for (double el : arr) {
            sum += el;
        }
    }
    
    public T2 (int a){
        double[] arr = new double[1000];
        for (int i = 0; i < 1000; i++) {
            arr[i] = i;
        }
        for (double el : arr) {
            sum += el;
        }
        new T3();
    }
    
    public void run() {}
}

class T3 implements Runnable {
    
    public T3(){
        double[][] arr = new double[100][100];
    }
    
    public void run() {}
}

class T4 implements Runnable {
    
    public T4(){
        double[][] arr = new double[10][10];
    }
    
    public void run() {}
}

class T5 implements Runnable {
    
    public T5(){
        this(1);
        int i = 1; 
    }
    
    public T5(int a){
       double[][] arr = new double[10][10];
    }
    
    public void run() {}
}