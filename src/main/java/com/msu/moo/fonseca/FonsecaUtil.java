package com.msu.moo.fonseca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class FonsecaUtil {

	public static String toString(List<Double> l) {
		StringBuilder line = new StringBuilder();
		for (Double d : l) {
			line.append(d);
			line.append(" ");
		}
		return line.toString();
	}
	
	public static String fromStream(InputStream is) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader (new InputStreamReader(is));
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			reader.close();
		} catch (IOException e) {
		}
		
		if (sb.toString().isEmpty()) return null;
		else return sb.toString();
	}
	
	public static String toString(SolutionSet set) {
		StringBuilder sb = new StringBuilder();
		for (Solution s : set) {
			sb.append(FonsecaUtil.toString(s.getObjectives()));
			sb.append("\n");
		}
		return sb.toString();
	}

	
}
