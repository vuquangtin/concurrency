package concurrencies.frameworks.hystrixs.core;

public class ClientException extends Exception {
	private static final long serialVersionUID = 1L;

	public static final String AUTHENTICATION_ERROR_MESSAGE = "Permission denied!";
	public static final String POST_NOT_EXIST_ERROR_MESSAGE = "Post not exist!";
	public static final String INVALID_REQUEST_MESSAGE = "Invalid request!";

	public ClientException() {
		super();
	}

	public ClientException(String msg) {
		super(msg);
	}

	public ClientException(String msg, Exception e) {
		super(msg, e);
	}
}