package com.msu.moo.util.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @param <T>
 *            Resulting file that should be read by this class.
 */
public abstract class AReader<T> {

	protected abstract T read_(String pathToFile) throws IOException;
	
	public T read(String pathToFile) {
		try {
			return read_(pathToFile);
		} catch (IOException e) {
			throw new RuntimeException(String.format("Error while reading %s.", pathToFile));
		}
	};

	protected BufferedReader createBufferedReader(String pathToFile) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(pathToFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(String.format("Could not open %s.", pathToFile));
		}
		return br;
	}

}
