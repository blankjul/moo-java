package com.msu.moo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class CImplementationComparator {

	
	public static void main(String[] args) {
		
		
		List<DoubleSummaryStatistics> result = new ArrayList<>();
		for (int j = 0; j < 200; j++) {
			result.add(new DoubleSummaryStatistics());
		}
		
		
		for (int i = 1; i < 10; i++) {
			
			
			final String path = String.format("comparison/zdt1/my%s.out", i);
			
			try (Stream<String> stream = Files.lines(Paths.get(path))) {

				List<String> list = stream.collect(Collectors.toList());
				
				SolutionSet<Integer> set = new SolutionSet<>(); 
				int counter = 0;
				
				for (String line : list) {
					
					if (line.startsWith("#")) {
						
						if (!set.isEmpty()) {
							Double hv = Hypervolume.calculate(set, Arrays.asList(1.01, 1.01));
							if (hv == null) hv = 0d;
							result.get(counter).accept(hv);
							counter++;
							set = new SolutionSet<>();
						}

					} else {
						String[] values = line.split("\\t");
						Solution<Integer> s = new Solution<Integer>(1, Arrays.asList(Double.valueOf(values[0]), Double.valueOf(values[1])));
						set.add(s);
					}
					
					
				}
				
				

			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println("--------");
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i).getAverage());
		}
		
		
	}
	
}
