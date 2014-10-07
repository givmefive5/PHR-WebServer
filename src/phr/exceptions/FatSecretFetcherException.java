package phr.exceptions;

public class FatSecretFetcherException extends Exception {

	public FatSecretFetcherException(String message, Exception e){
		super(message, e) ;
	}
}
