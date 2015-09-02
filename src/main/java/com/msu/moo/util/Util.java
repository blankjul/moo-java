package com.msu.moo.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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

}
