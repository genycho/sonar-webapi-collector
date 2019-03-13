package sic.sonar.common;



/**
 *
 */
public class SICServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public SICServiceException(String internalMessage, Throwable t) {
		super(internalMessage, t);
	}

	public SICServiceException(String internalMessage) {
		super(internalMessage);
	}

	public SICServiceException(Exception e2) {
		super(e2);
	}

}
