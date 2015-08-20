package com.msu.moo.model;

public interface IVariable<C, E> {

	public E encode();

	public C random(int length);

	public void set(Object obj);

	public Object get();

}