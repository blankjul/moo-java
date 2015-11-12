package com.msu.util.events;

public interface IListener<E extends IEvent> {
	public void handle(E event);
}
