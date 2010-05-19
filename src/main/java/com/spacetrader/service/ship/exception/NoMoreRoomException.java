package com.spacetrader.service.ship.exception;

public class NoMoreRoomException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoMoreRoomException(String message, Throwable cause) {
		super(message, cause);
	}
}
