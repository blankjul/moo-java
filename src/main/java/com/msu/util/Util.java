package com.msu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS") ;
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
	
	
	public static <T> T cloneObject(T obj){
		Cloner cloner=new Cloner();
        try{
        	T clone = cloner.deepClone(obj);
            return clone;
        }catch(Exception e){
        	e.printStackTrace();
        	throw new RuntimeException(String.format("Error while cloning object %s.", obj));
        }
    }
	
	public static <T> List<T> createListWithDefault(int n, T t) {
		List<T> l = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			l.add(t);
		}
		return l;
	}


	public static List<Integer> createIndex(int n) {
		List<Integer> l = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			l.add(i);
		}
		return l;
	}



}
