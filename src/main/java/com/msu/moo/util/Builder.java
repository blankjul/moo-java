package com.msu.moo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class Builder<T> {

	// private object that is build during the process
	protected T obj;
	
	protected Set<String> propertiesToSet = new HashSet<>();
	
	
	private Builder() {
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
			propertiesToSet.remove(key);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(String.format("Could not set attribute %s for object.", key));
		}
        return this;
    }
	

	private void checkIfAllPropertiesSet() {
		if (!propertiesToSet.isEmpty())
			throw new RuntimeException(String.format("Not all properties are set. Please define %s.", Arrays.toString(propertiesToSet.toArray())));
	
	}
	
    public T build(){
    	checkIfAllPropertiesSet();
        return Util.cloneObject(obj);
    }
    
    public T buildNoClone(){
    	checkIfAllPropertiesSet();
        return obj;
    }
    


}