package sic.sonar.common;



/**
 * 잘못된 요리(법)에 대해 발생시키는 Exception 입니다
 *
 */
public class SICRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SICRuntimeException(String internalMessage, Throwable t) {
		super(internalMessage, t);
	}

	public SICRuntimeException(String internalMessage) {
		super(internalMessage);
	}

	public SICRuntimeException(Exception e2) {
		super(e2);
	}

}
