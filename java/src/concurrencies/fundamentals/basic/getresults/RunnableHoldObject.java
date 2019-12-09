package basic.getresults;

class MainThread {
    public void startMyThread() {
        Object requiredObject = new Object(); //Map/List/OwnClass
        Thread myThread = new Thread(new RunnableHoldObject(requiredObject));
        myThread.start();
        try {
			myThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println(requiredObject.getClass());    
    }
}



public class RunnableHoldObject implements Runnable {
    private Object requiredObject;

    public RunnableHoldObject(Object requiredObject) {
        this.requiredObject = requiredObject;
    }

    public void run() {
        //requiredObject.setRequiredValue(xxxxx);
    }
}