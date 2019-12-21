package basic.thread1;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MyRecursiveAction extends RecursiveAction {

    private static final long serialVersionUID = 1L;
	
    private long workLoad = 0;

    public MyRecursiveAction(long workLoad) {
    	System.out.println("creating:" + workLoad);
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {

        //if work is above threshold, break tasks up into smaller tasks
        if(this.workLoad > 16) {
            System.out.println("Splitting workLoad : " + this.workLoad);

            List<MyRecursiveAction> subtasks =
                new ArrayList<MyRecursiveAction>();

           /* subtasks.addAll(createSubtasks());
            for(RecursiveAction subtask : subtasks){
            	System.out.println("forking: " + subtask.getForkJoinTaskTag());
                subtask.fork();
            }*/
            MyRecursiveAction subtask1 = new MyRecursiveAction(this.workLoad / 2);
            MyRecursiveAction subtask2 = new MyRecursiveAction(this.workLoad / 2);
            invokeAll(subtask1, subtask2);

        } else {
            System.out.println("Doing workLoad myself: " + this.workLoad);
        }
    }

    private List<MyRecursiveAction> createSubtasks() {
        List<MyRecursiveAction> subtasks =
            new ArrayList<MyRecursiveAction>();

        MyRecursiveAction subtask1 = new MyRecursiveAction(this.workLoad / 2);
        MyRecursiveAction subtask2 = new MyRecursiveAction(this.workLoad / 2);

        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }
    
    public static void main(String[] args) throws Exception {
    	ForkJoinPool forkJoinPool = new ForkJoinPool(10);
    	MyRecursiveAction myRecursiveAction = new MyRecursiveAction(320);

    	forkJoinPool.invoke(myRecursiveAction);
    	myRecursiveAction.get();
    }

}