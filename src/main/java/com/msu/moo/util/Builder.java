package com.msu.moo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.msu.moo.util.exceptions.BuilderException;


public class Builder<T> {

	// private object that is build during the process
	private T obj;
	
	//! all variables that are set by this builder
	private Set<String> attributes = new HashSet<>();
    
	//! required fields for building the object
	protected Set<String> requiredFields = new HashSet<>();
	
	//! default fields which are set before building
	protected Map<String,Object> defaultFields = new HashMap<>();
	
	
	private Builder() {
		addRequiredFields(requiredFields);
		addDefaultFields(defaultFields);
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
		// set default fields
		for(Entry<String, Object> entry : defaultFields.entrySet()) set(entry.getKey(), entry.getValue());
	}
	

	public Builder<T> set(String key, Object param){
		Field field;
		try {
			field = Util.getField(key, obj.getClass());
			field.setAccessible(true);
			field.set(obj, param);
			attributes.add(key);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(String.format("Could not set attribute %s for object.", key));
		}
        return this;
    }
	
	
	protected void addRequiredFields(Set<String> requiredFields) {
	}

	protected void addDefaultFields(Map<String,Object> defaultFields) {
	}


    public T build(){
    	for(String s : requiredFields) {
    		if (!attributes.contains(s)) throw new BuilderException(String.format("Required field '%s' for building %s not set. \nRequired are: %s", s, obj, Arrays.toString(requiredFields.toArray())));
    	}
        return Util.cloneObject(obj);
    }
    
    


}