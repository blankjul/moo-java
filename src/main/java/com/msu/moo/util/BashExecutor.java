package com.msu.moo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class BashExecutor {

	public static String execute(String command) {
		Process p = null;
		try {
			ProcessBuilder builder = new ProcessBuilder("/bin/bash");
			p = builder.start();

			BufferedWriter stdin = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			stdin.write(command);
			stdin.flush();
			stdin.close();
			p.waitFor();
			String out = BashExecutor.fromStream(p.getInputStream());
			return out;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error while executing command");
		}
	}

	public static String fromStream(InputStream is) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			reader.close();
		} catch (IOException e) {
		}
		if (sb.toString().isEmpty())
			return null;
		else
			return sb.toString();
	}
}
