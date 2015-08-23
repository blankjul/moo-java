package com.msu.moo.fonseca;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.msu.moo.model.solution.SolutionSet;

public class Hypervolume {

	
	public Double calculate(SolutionSet set) {
		return calculate(set, null);
	}
	
	public Double calculate(SolutionSet set, List<Double> referencePoint) {

		Double hv = null;
		
		
		String command = getCommand(set);
		if (referencePoint != null) {
			command += " -r \"" + FonsecaUtil.toString(referencePoint) + "\"";
		}
		
		try {

			ProcessBuilder builder = new ProcessBuilder("/bin/bash");
			//builder.redirectErrorStream(true);
			Process p = builder.start();

			BufferedWriter stdin = new BufferedWriter(new OutputStreamWriter(p.getOutputStream ()));
			stdin.write(command);
			stdin.flush();
			stdin.close();
			
			String out = FonsecaUtil.fromStream(p.getInputStream());
			//String err = fromStream(p.getErrorStream());
			
			if (out == null || out.isEmpty()) return null;
			
			hv = Double.valueOf(out);


		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not calculate Hypervolume!");
		}

		return hv;
	}

	
	protected String getCommand(SolutionSet set) {
		StringBuilder sb = new StringBuilder();
		sb.append("printf \"");
		sb.append(FonsecaUtil.toString(set));
		sb.append("\" | ");
		sb.append("vendor/hv-1.3-src/hv");
		return sb.toString();
	}



}
