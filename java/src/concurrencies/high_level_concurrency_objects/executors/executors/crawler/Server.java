package executors.crawler;

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

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(9000);
		int i=100;
		while (true) {
			final Socket s = socket.accept();
			Runnable r = new Runnable() {
				@Override
				public void run() {
					doWork(s);
				}
			};
			new Thread(r).start();
			if(i++>100)
				break;
		}
		socket.close();
	}

	static void doWork(Socket s) {
	}
}