package executors.customthreadpoolexecutor.feature;

import java.util.Observable;

public class UserObservable extends Observable {
	
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String addr) {
		this.address = addr;
	}
	
	public void updateAddr(String addr) {
		setChanged();
		notifyObservers(addr);
	}

}
