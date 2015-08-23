package com.msu.moo.fonseca;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class EmpiricalAttainmentFunction {

	public SolutionSet calculate(List<SolutionSet> sets) {
		return calculate(sets, (sets.size() / 2) + 1);
	}
	
	public SolutionSet calculate(List<SolutionSet> sets, int level) {

		// hash all the solution to re-map to median front!
		Map<List<Double>, Solution> map = new HashMap<>();
		for(SolutionSet set : sets) {
			for (Solution s : set) {
				map.put(s.getObjectives(), s);
			}
		}
		
		// result where the value are added
		SolutionSet result = new SolutionSet();
		String command = getCommand(sets, level);
		
		try {

			ProcessBuilder builder = new ProcessBuilder("/bin/bash");
			//builder.redirectErrorStream(true);
			Process p = builder.start();

			BufferedWriter stdin = new BufferedWriter(new OutputStreamWriter(p.getOutputStream ()));
			stdin.write(command);
			stdin.flush();
			stdin.close();
			
			String out = FonsecaUtil.fromStream(p.getInputStream());
			
			// for each line at the results
			for(String line : out.split("\n")) {
				
				if (line.startsWith("#")) continue;
				
				// parse the objective values from the line
				List<Double> objectives = new ArrayList<>();
				for(String value : line.split("\\s")) objectives.add(Double.valueOf(value));
				
				// find corresponding solution and add
				result.add(map.get(objectives));
				
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not calculate Hypervolume!");
		}

		return result;
	}

	
	protected String getCommand(List<SolutionSet> sets, int level) {
		StringBuilder sb = new StringBuilder();
		sb.append("printf \"");
		for (SolutionSet set : sets) {
			sb.append(FonsecaUtil.toString(set));
			sb.append("\n");
		}
		sb.append("\" | ");
		sb.append("vendor/aft-0.95/eaf -l " + level);
		return sb.toString();
	}

	
}
