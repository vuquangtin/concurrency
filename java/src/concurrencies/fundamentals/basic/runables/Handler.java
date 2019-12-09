package basic.runables;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
public class Handler {

	void process(Socket s) {
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			int request = in.readInt();
			int result = -request; // return negation to client
			out.writeInt(result);
		} catch (IOException ex) {
		} // fall through

		finally { // clean up
			try {
				if (in != null)
					in.close();
			} catch (IOException ignore) {
			}
			try {
				if (out != null)
					out.close();
			} catch (IOException ignore) {
			}
			try {
				s.close();
			} catch (IOException ignore) {
			}
		}
		
	}
}

