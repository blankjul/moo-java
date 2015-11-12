package com.msu.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.log4j.Logger;

import com.msu.util.io.AReader;

public class FileCollectorParser<T> {

	static final Logger logger = Logger.getLogger(FileCollectorParser.class);
	
	// the map for all the files that should be loaded
	protected Map<Pair<String, String>, Function<String, ? extends T>> map = new LinkedHashMap<>();

	public FileCollectorParser<T> add(String folder, String regex, Function<String, ? extends T> func) {
		map.put(Pair.create(folder, regex), func);
		return this;
	}

	public FileCollectorParser<T> add(String folder, String regex, AReader<? extends T> reader) {
		return add(folder, regex, new Function<String, T>() {
			@Override
			public T apply(String t) {
				return reader.read(t);
			}
		});
	}
	

	public List<T> collect() {
		List<T> list = new ArrayList<>();

		for (Pair<String, String> p : map.keySet()) {

			String folder = p.first;
			String pattern = p.second;
			Function<String, ? extends T> func = map.get(p);

			DirectoryStream<Path> dirStream;
			try {
				dirStream = Files.newDirectoryStream(Paths.get(folder), pattern);
				logger.info(String.format("Load files from folder %s with pattern %s.", folder, pattern));
				
				// sort the files
				List<String> files = new ArrayList<>();
				dirStream.forEach(s -> files.add(s.toString()));
				Collections.sort(files);
				
				for (String s : files) {
					logger.info(String.format("Loading file %s.", s));
					list.add(func.apply(s));
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(String.format("Could not load files from patern: %s", pattern));
			}

		}

		return list;
	}

}
