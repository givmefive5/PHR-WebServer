package phr.exceptions;

public class SNSException extends Exception {

	public SNSException(String message, Exception e) {
		super(message, e);
	}
}
