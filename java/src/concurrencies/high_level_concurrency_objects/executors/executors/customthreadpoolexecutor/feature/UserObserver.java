package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.Observable;
import java.util.Observer;

public class UserObserver implements Observer {

	private UserObservable uObservable;

	@Override
	public void update(Observable obser, Object obj) {
		uObservable = (UserObservable) obser;
		out.println("Previous: " + uObservable.getAddress() + ", Updates: " + obj);
	}

	public static void main(String[] args) {
		UserObservable uObservable = new UserObservable();
		uObservable.setAddress("Addr-1");
		
		UserObserver observer1 = new UserObserver();
		uObservable.addObserver(observer1);
		
		uObservable.updateAddr("Addr-3");
		uObservable.updateAddr("Addr-4");
	}
	
}
