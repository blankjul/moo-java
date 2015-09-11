package com.msu.moo.experiment;import java.util.ArrayList;
import java.util.List;

import com.msu.moo.Configuration;
import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Range;
import com.msu.moo.visualization.BoxPlot;
import com.msu.moo.visualization.ScatterPlot;

public abstract class NProblemNAlgorithmExperiment<P extends IProblem> extends AbstractExperiment<P>{

	@Override
	protected void visualize() {
		for(P p : problems.keySet()) {
			visualize(p);
		}
		
	}

	
	protected void visualize(P problem) {
		
		NonDominatedSolutionSet trueFront = problems.get(problem);
		
		// plot all the median fronts
		EmpiricalAttainmentFunction eaf = new EmpiricalAttainmentFunction(Configuration.pathToEAF);
		
		ScatterPlot sp = new ScatterPlot(problem.toString(), "X", "Y");
		for(IAlgorithm<?> algorithm : algorithms) {
			NonDominatedSolutionSet median = eaf.calculate(storage.get(problem, algorithm));
			sp.add(median, algorithm.toString());
		}
		sp.show();
		
		
		
		// plot the hypervolume
		Hypervolume calcHV = new Hypervolume(Configuration.pathToHV);
		
		// create reference point for normalized values
		List<Double> referencePoint = new ArrayList<>();
		for (int i = 0; i < problem.getNumberOfObjectives(); i++) referencePoint.add(1.0001);
		
		// estimate true front if not given and calculate the range for normalization
		if (trueFront == null) trueFront = AbstractExperiment.estimateTrueFront(storage.get().values());
		Range<Double> range = trueFront.getRange();
		
		
		BoxPlot bp = new BoxPlot(problem.toString());
		for (IAlgorithm<?> algorithm : algorithms) {
			List<Double> hvs = new ArrayList<>();
			for(NonDominatedSolutionSet set : storage.get(problem, algorithm)) {
				SolutionSet norm = set.getSolutions().normalize(range.get());
				Double hv = calcHV.calculate(new NonDominatedSolutionSet(norm), referencePoint);
				hvs.add(hv);
			}
			bp.add(hvs, algorithm.toString());
		}
		bp.show();
		
	
	}


}
