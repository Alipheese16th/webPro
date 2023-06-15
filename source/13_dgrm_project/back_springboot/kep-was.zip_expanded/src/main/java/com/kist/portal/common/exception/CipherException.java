package com.kist.portal.common.exception;


public class CipherException extends Exception {
	private static final long serialVersionUID = -802695570287296775L;

	public CipherException(String message) {
		super(message);
	}
	public CipherException(Throwable cause) {
		super(cause);
	}
	public CipherException(String message, Throwable cause) {
		super(message, cause);
	}
}
