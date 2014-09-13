package phr.exceptions;

public class TrackerServiceException extends Exception {

	public TrackerServiceException(String message, Exception e) {
		super(message, e);
	}
}
