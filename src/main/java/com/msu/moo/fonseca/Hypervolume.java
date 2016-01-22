package com.msu.moo.fonseca;

import java.util.List;

import com.msu.moo.Configuration;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.BashExecutor;
import com.msu.moo.util.Util;

public class Hypervolume {


	public static Double calculate(SolutionSet<?> set) {
		return calculate(set, null);
	}

	public static Double calculate(SolutionSet<?> set, List<Double> referencePoint) {

		if (!Util.doesFileExist(Configuration.PATH_TO_HYPERVOLUME)) throw new RuntimeException("Fonseca's Hypverolume Implementation not found!");
		
		Double hv = null;

		StringBuilder sb = new StringBuilder();
		sb.append("echo -e  \"");
		sb.append(FonsecaUtil.toString(set));
		sb.append("\" | ");
		sb.append(Configuration.PATH_TO_HYPERVOLUME);
		
		
		if (referencePoint != null) {
			sb.append(" -r \"");
			sb.append(FonsecaUtil.toString(referencePoint));
			sb.append("\"");
		}
		String out = BashExecutor.execute(sb.toString());
		if (out == null) return null;
		hv = Double.valueOf(out);
		return hv;
	}

	
	
	protected String getCommand(SolutionSet<?> set) {
		StringBuilder sb = new StringBuilder();
		sb.append("echo -e  \"");
		sb.append(FonsecaUtil.toString(set));
		sb.append("\" | ");
		sb.append(Configuration.PATH_TO_HYPERVOLUME);
		return sb.toString();
	}

}
