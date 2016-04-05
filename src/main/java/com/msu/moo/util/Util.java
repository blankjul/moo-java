package com.msu.moo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.rits.cloning.Cloner;

public class Util {

	/**
	 * Return the first duplicate that is found on a given collection
	 * 
	 * @param c
	 *            collection of a generic type.
	 * @return first found duplicate
	 */
	public static <T> T getDuplicate(HashSet<T> hash, Collection<T> c) {
		for (T element : c) {
			if (hash.contains(element)) {
				return element;
			}
			hash.add(element);
		}
		return null;
	}

	public static <T> void swap(List<T> obj, int a, int b) {
		// swap this two!
		T tmp = obj.get(a);
		obj.set(a, obj.get(b));
		obj.set(b, tmp);
	}

	public static <T> int find(T[] array, T value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value)
				return i;
		}
		return -1;
	}

	public static <T> void write(String path, T obj) {
		System.out.println("Writing " + path);
		try {
			FileWriter writer = new FileWriter(path);
			writer.write(obj.toString());
			writer.write('\n');
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean doesFileExist(String path) {
		File f = new File(path);
		return f.exists() && !f.isDirectory();
	}

	public static String getTimestamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String date = dateFormat.format(System.currentTimeMillis());
		return date;
	}

	public static String readFile(String pathToFile) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(pathToFile));
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static <T> T cloneObject(Cloner cloner, T obj) {
		try {
			T clone = cloner.deepClone(obj);
			return clone;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(String.format("Error while cloning object %s.", obj));
		}
	}
	
	public static <T> T cloneObject(T obj) {
		return cloneObject(new Cloner(), obj);
	}
	

	public static <T> List<T> createListWithDefault(int n, T t) {
		List<T> l = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			l.add(t);
		}
		return l;
	}
	
	public static <T> void addDefault(Collection<T> c, int n, T t) {
		for (int i = 0; i < n; i++) c.add(t);
	}

	public static List<Integer> createIndex(int n) {
		List<Integer> l = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			l.add(i);
		}
		return l;
	}
	
	public static List<Integer> createIndex(int l, int u) {
		List<Integer> list = new ArrayList<>();
		for (int i = l; i < u; i++) {
			list.add(i);
		}
		return list;
	}

	/**
	 * Get all fields through reflection. also of superclass
	 */
	public static Field getField(String fieldName, Class<?> clazz) {
		Class<?> tmpClass = clazz;
		do {
			try {
				Field f = tmpClass.getDeclaredField(fieldName);
				return f;
			} catch (NoSuchFieldException e) {
				tmpClass = tmpClass.getSuperclass();
			}
		} while (tmpClass != null);

		throw new RuntimeException("Field '" + fieldName + "' not found on class " + clazz);
	}



}
