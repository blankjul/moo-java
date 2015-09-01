package com.msu.moo.fonseca;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.List;

import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class Hypervolume {

	// ! default path to the EAF executable
	protected String pathToHV = "vendor/hv-1.3-src/hv";

	
	public Hypervolume() {
		super();
	}
	
	public Hypervolume(String pathToHV) {
		super();
		this.pathToHV = pathToHV;
	}

	public Double calculate(NonDominatedSolutionSet set) {
		return calculate(set, null);
	}

	public Double calculate(NonDominatedSolutionSet set, List<Double> referencePoint) {

		// implementation for the one dimensional case
		if (referencePoint.size() == 1) {
			// for one dimension there is always only one point in the front -> if not exception
			if (set.size() != 1) throw new RuntimeException("One Dimension is only allowed to have one NonDominatedPoint!");
			return referencePoint.get(0) - set.getSolutions().get(0).getObjectives().get(0);
		}
		
		Double hv = null;

		String command = getCommand(set);
		if (referencePoint != null) {
			command += " -r \"" + FonsecaUtil.toString(referencePoint) + "\"";
		}

		try {

			ProcessBuilder builder = new ProcessBuilder("/bin/bash");
			// builder.redirectErrorStream(true);
			Process p = builder.start();

			BufferedWriter stdin = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			stdin.write(command);
			stdin.flush();
			stdin.close();

			p.waitFor();

			String out = FonsecaUtil.fromStream(p.getInputStream());
			// String err = fromStream(p.getErrorStream());

			if (out == null || out.isEmpty())
				return null;

			hv = Double.valueOf(out);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not calculate Hypervolume!");
		}

		return hv;
	}

	protected String getCommand(NonDominatedSolutionSet set) {
		StringBuilder sb = new StringBuilder();
		sb.append("echo -e  \"");
		sb.append(FonsecaUtil.toString(set.getSolutions()));
		sb.append("\" | ");
		sb.append(pathToHV);
		return sb.toString();
	}

}
