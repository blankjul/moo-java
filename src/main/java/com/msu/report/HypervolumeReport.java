package com.msu.report;

import java.util.ArrayList;
import java.util.List;

import com.msu.Configuration;
import com.msu.experiment.AExperiment;
import com.msu.experiment.ExperimentResult;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.AReport;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.util.Range;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.ProblemFinishedEvent;

public class HypervolumeReport extends AReport {

	public HypervolumeReport(String path) {
		
		super(path);
		pw.println("problem,algorithm,hv");
		
		EventDispatcher.getInstance().register(ProblemFinishedEvent.class, new IListener<ProblemFinishedEvent>() {

			@Override
			public void handle(ProblemFinishedEvent event) {
				IProblem problem = event.getProblem();
				AExperiment experiment = event.getExperiment();
				ExperimentResult result = experiment.getResult();
				

				// create reference point for normalized values
				List<Double> referencePoint = new ArrayList<>();
				for (int i = 0; i < problem.getNumberOfObjectives(); i++)
					referencePoint.add(1.0001);

				// estimate true front if not given and calculate the range for
				// normalization
				NonDominatedSolutionSet trueFront = problem.getOptimum();
				if (trueFront == null) {
					trueFront = new NonDominatedSolutionSet();
					for (IAlgorithm algorithm : experiment.getAlgorithms()) {
						for (NonDominatedSolutionSet set : result.get(problem, algorithm)) {
							trueFront.addAll(set.getSolutions());
						}
					}
				}
				Range<Double> range = trueFront.getRange();

				
				
				// plot the hypervolume
				Hypervolume calcHV = new Hypervolume(Configuration.PATH_TO_HYPERVOLUME);
				
				for (IAlgorithm algorithm : experiment.getAlgorithms()) {
					
					for (int i = 0; i < event.getNumOfRuns(); i++) {
						SolutionSet norm = result.get(problem, algorithm, i).getSolutions().normalize(range.get());
						Double hv = calcHV.calculate(new NonDominatedSolutionSet(norm), referencePoint);
						if (hv == null) hv = 0.0;
						pw.printf("%s,%s,%s\n", problem, algorithm, hv);
					}
					
					
				}
				
				
				
			}
			
			
		});
		
	}



}
