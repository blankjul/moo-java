package com.msu.moo.fonseca;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

public class EmpiricalAttainmentFunction {

	public NonDominatedSolutionSet calculate(List<NonDominatedSolutionSet> sets) {
		return calculate(sets, (sets.size() / 2) + 1);
	}
	
	public NonDominatedSolutionSet calculate(List<NonDominatedSolutionSet> sets, int level) {

		// result where the value are added
		NonDominatedSolutionSet result = new NonDominatedSolutionSet();
		String command = getCommand(sets, level);
		
		try {

			ProcessBuilder builder = new ProcessBuilder("/bin/bash");
			//builder.redirectErrorStream(true);
			Process p = builder.start();

			BufferedWriter stdin = new BufferedWriter(new OutputStreamWriter(p.getOutputStream ()));
			stdin.write(command);
			stdin.flush();
			stdin.close();
			
			System.out.println(command);
			
			String out = FonsecaUtil.fromStream(p.getInputStream());
			
			// for each line at the results
			for(String line : out.split("\n")) {
				
				if (line.startsWith("#")) continue;
				
				// parse the objective values from the line
				List<Double> objectives = new ArrayList<>();
				for(String value : line.split("\\s")) objectives.add(Double.valueOf(value));
				
				result.add(new Solution(null, objectives));
				
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not calculate Hypervolume!");
		}

		return result;
	}

	
	protected String getCommand(List<NonDominatedSolutionSet> sets, int level) {
		StringBuilder sb = new StringBuilder();
		sb.append("echo -e \"");
		for (NonDominatedSolutionSet set : sets) {
			sb.append(FonsecaUtil.toString(set.getSolutions()));
			sb.append("\n");
		}
		sb.append("\" | ");
		sb.append("vendor/aft-0.95/eaf -l " + level);
		return sb.toString();
	}

	
}
