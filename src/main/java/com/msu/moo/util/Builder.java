package com.msu.moo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class Builder<T> {

	// private object that is build during the process
	private T obj;
	
	
	private Builder() {
	}
	
	public Builder(T obj) {
		this.obj = obj;
	}
	
	
	@SuppressWarnings("unchecked")
	public Builder(Class<?> clazz) {
		this();
		try {
	      Constructor<?> constructor = clazz.getDeclaredConstructor();
	      constructor.setAccessible(true);
		  obj = (T) constructor.newInstance();
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	

	public Builder<T> set(String key, Object param){
		Field field;
		try {
			field = Util.getField(key, obj.getClass());
			field.setAccessible(true);
			field.set(obj, param);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(String.format("Could not set attribute %s for object.", key));
		}
        return this;
    }
	


    public T build(){
        return Util.cloneObject(obj);
    }
    
    public T buildNoClone(){
        return obj;
    }
    


}