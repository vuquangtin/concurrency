package thread.objects;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Assemble{
	public static void main(String []args){

		//create a carassembly object
		CarAssembly carAssembly = new CarAssembly();

		//create worker1 thread
		Thread worker1 = new Thread(new WorkerThread(carAssembly));

		//create worker2 thread
		Thread worker2 = new Thread(new WorkerThread(carAssembly));

		Long start = System.currentTimeMillis();

		//start worker1 and worker2 threads
		worker1.start();
		worker2.start();

		//wait till worker 1 and 2 will die
		try{
			worker1.join();
			worker2.join();
			
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
		Long end = System.currentTimeMillis();

		System.out.println("Total time taken: "+ (end-start));
		System.out.println("List1 size :"+ carAssembly.getPart1().size() +
				"\t" + carAssembly.getPart2().size());
	}
}
