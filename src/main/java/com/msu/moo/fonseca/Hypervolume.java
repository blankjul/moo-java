package com.msu.moo.fonseca;

import java.util.List;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.BashExecutor;
import com.msu.moo.util.Util;

public class Hypervolume {

	// ! default path to the EAF executable
	protected String pathToHV = null;


	public Hypervolume(String pathToHV) {
		super();
		this.pathToHV = pathToHV;
	}

	public Double calculate(NonDominatedSolutionSet<?> set) {
		return calculate(set, null);
	}

	public Double calculate(NonDominatedSolutionSet<?> set, List<Double> referencePoint) {

		if (!Util.doesFileExist(pathToHV)) throw new RuntimeException("Fonseca's Hypverolume Implementation not found!");
		
		Double hv = null;

		StringBuilder sb = new StringBuilder();
		sb.append("echo -e  \"");
		sb.append(FonsecaUtil.toString(set.getSolutions()));
		sb.append("\" | ");
		sb.append(pathToHV);
		
		
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

	
	
	protected String getCommand(NonDominatedSolutionSet<?> set) {
		StringBuilder sb = new StringBuilder();
		sb.append("echo -e  \"");
		sb.append(FonsecaUtil.toString(set.getSolutions()));
		sb.append("\" | ");
		sb.append(pathToHV);
		return sb.toString();
	}

}
