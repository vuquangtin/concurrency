package deadlocks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public final class ImmutableMessage {
	private final String subject;
	private final String message;
	private final Map<String, String> header;

	public ImmutableMessage(Map<String, String> header, String subject,
			String message) {
		this.header = new HashMap<String, String>(header);
		this.subject = subject;
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}

	public String getHeader(String key) {
		return this.header.get(key);
	}

	public Map<String, String> getHeaders() {
		return Collections.unmodifiableMap(this.header);
	}
}
