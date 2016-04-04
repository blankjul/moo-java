package com.msu.moo.algorithms.impl.nsgaII;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.BashExecutor;
import com.msu.moo.util.MyRandom;

/**
 * This class allows to read the population file of the original NSGAII
 * implementation.
 */
public class PopulationReader {

	public static List<SolutionSet<NSGAIISolution<?>>> read(String path) throws IOException {

		List<SolutionSet<NSGAIISolution<?>>> result = new ArrayList<>();

		String content = new String(Files.readAllBytes(Paths.get(path)));

		String[] generations = content.split("gen =.*\n");

		for (int i = 1; i < generations.length; i++) {

			String g = generations[i];

			SolutionSet<NSGAIISolution<?>> set = new SolutionSet<>();

			for (String line : g.split("\n")) {

				if (line.startsWith("#")) continue;

				List<Double> objectives = new ArrayList<>();

				String[] values = line.split(",");
				for (String strvalue : values) {
					double v = Double.valueOf(strvalue);
					objectives.add(v);
				}

				set.add(new NSGAIISolution<Object>(null, objectives));
			}

			result.add(set);

		}

		return result;

	}

	public static void main(String[] args) throws IOException {
		
		
		final MyRandom rand = new MyRandom(654321);
		
		final String path = "all_pop.out";
		
		for (int j = 0; j < 10; j++) {

			String folder = "/home/julesy/workspace/nsga2-gnuplot-v1.1.6";
			
			// NOTE: Compile the source code for the specific problem! problemdef.c!!!!!!
			double seed = rand.nextDouble();
			BashExecutor.execute(String.format("%s/nsga2r %f <%s/input_data/zdt3.in", folder, seed, folder));
			
			List<SolutionSet<NSGAIISolution<?>>> result = read(path);
			
			BashExecutor.execute("rm *out");
			
			// for each generation
			
			for (int i = 0; i < result.size(); i++) {

				Double hv = Hypervolume.calculate(result.get(i), Arrays.asList(1.0, 1.0));
				if (hv == null)
					hv = 0.0;
				System.out.println(String.format("C,%s,%s", i, hv));
			}
			
			
			/*
			Double hv = Hypervolume.calculate(result.get(result.size()-1), Arrays.asList(1.0, 1.0));
			if (hv == null)
				hv = 0.0;
			System.out.println(String.format("C,%s,%s", seed, hv));
			*/

		}
		
		

	}

}
