package com.galas.filip.kropki.exception;

public class UnsupportedSceneFormatException extends SceneLoadingException {

	private static final long serialVersionUID = -3981684839709699532L;

	public UnsupportedSceneFormatException() {

	}

	public UnsupportedSceneFormatException(String msg) {
		super(msg);
	}

	public UnsupportedSceneFormatException(Throwable cause) {
		super(cause);
	}

	public UnsupportedSceneFormatException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
