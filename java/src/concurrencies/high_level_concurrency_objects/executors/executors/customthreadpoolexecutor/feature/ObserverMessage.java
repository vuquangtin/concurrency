package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.Observable;
import java.util.Observer;

class MessageObservable extends Observable {
	
	public void updateMsg(String message) {
		setChanged();
		notifyObservers(message);
	}
	
}

class StudentObserver implements Observer {
	
	@Override
	public void update(Observable o, Object arg) {
		out.println("Message updates: " + arg);
	}
	
}

public class ObserverMessage {
	
	public static void main(String[] args) {
		MessageObservable msgObser = new MessageObservable();
		StudentObserver bob = new StudentObserver();
		StudentObserver joe = new StudentObserver();
		msgObser.addObserver(bob);
		msgObser.addObserver(joe);
		msgObser.updateMsg("More Homework!");
	}
	
}
