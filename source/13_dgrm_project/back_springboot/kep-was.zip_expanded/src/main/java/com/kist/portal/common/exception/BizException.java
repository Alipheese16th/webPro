package com.kist.portal.common.exception;


public class BizException extends Exception {
	private static final long serialVersionUID = -802695570287296775L;

	public BizException(String message) {
		super(message);
	}
	public BizException(Throwable cause) {
		super(cause);
	}
	public BizException(String message, Throwable cause) {
		super(message, cause);
	}
}
