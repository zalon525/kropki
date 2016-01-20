package com.galas.filip.kropki;

public class GameEvent {

	public static enum EventType {
		LOST, LINK
	};

	private EventType type;
	private String targetSceneName;

	private GameEvent(EventType type, String targetSceneName) {
		this.targetSceneName = targetSceneName;
		this.type = type;
	}
	
	public static GameEvent newLostGameEvent() {
		return new GameEvent(EventType.LOST, null);
	}
	
	public static GameEvent newLinkGameEvent(String targetSceneName) {
		return new GameEvent(EventType.LINK, targetSceneName);
	}

	public String getTargetSceneName() {
		return targetSceneName;
	}

	public EventType getEventType() {
		return type;
	}
}
