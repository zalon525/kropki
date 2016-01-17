package com.galas.filip.kropki;

public class GameEvent {

	public static enum EventType {
		LOST, LINK
	};

	private EventType type;
	private String linkURL;

	private GameEvent(EventType type, String linkURL) {
		this.linkURL = linkURL;
		this.type = type;
	}
	
	public static GameEvent newLostGameEvent() {
		return new GameEvent(EventType.LOST, null);
	}
	
	public static GameEvent newLinkGameEvent(String linkURL) {
		return new GameEvent(EventType.LINK, linkURL);
	}

	public String getLinkURL() {
		return linkURL;
	}

	public EventType getEventType() {
		return type;
	}
}
