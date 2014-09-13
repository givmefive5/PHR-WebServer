package phr.exceptions;

public class HttpRequestException extends Exception {

	public HttpRequestException(String message, Exception e) {
		super(message, e);
	}
}
