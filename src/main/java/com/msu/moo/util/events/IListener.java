package com.msu.moo.util.events;

public interface IListener<E extends IEvent> {
	public void handle(E event);
}
