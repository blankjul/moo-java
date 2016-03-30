package com.msu.moo.util;



public class ObjectFactory {

	public static Object create(String fullType) {
		Class<?> c;
		try {
			c = Class.forName(fullType);
			Object pc = (Object) c.newInstance();
			return pc;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static <T> T create(Class<T> c, String fullType)  {
		try {
			@SuppressWarnings("unchecked")
			T obj = (T) Class.forName(fullType).newInstance();
			return obj;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

}
