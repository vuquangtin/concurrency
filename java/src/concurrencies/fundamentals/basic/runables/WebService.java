package basic.runables;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class WebService implements Runnable {
	static final int PORT = 1040; // just for demo
	Handler handler = new Handler();

	public void run() {
		ServerSocket socket=null;
		try {
			socket = new ServerSocket(PORT);
			for (;;) {
				final Socket connection = socket.accept();
				new Thread(new Runnable() {
					public void run() {
						handler.process(connection);
					}
				}).start();
			}
		} catch (Exception e) {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		} // die
	}

	public static void main(String[] args) {
		new Thread(new WebService()).start();
	}

}