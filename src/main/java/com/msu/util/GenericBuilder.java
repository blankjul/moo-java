package com.msu.util;

import java.lang.reflect.Field;

public class GenericBuilder<T> {

	private T obj;
    

	public GenericBuilder(T obj) {
		super();
		this.obj = obj;
	}

	public GenericBuilder<T> set(String key, Object param){
		Field field;
		try {
			field = obj.getClass().getDeclaredField(key);
			field.setAccessible(true);
			field.set(obj, param);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(String.format("Could not set attribute %s for object.", key));
		}
        return this;
    }


    public T build(){
        return obj;
    }

}