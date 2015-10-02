package com.msu.moo.report;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.IListener;
import com.msu.moo.util.events.ProblemFinishedEvent;

public class SingleObjectiveReport extends AReport {

	public SingleObjectiveReport() {
		super();
		pw.println("problem,algorithm,objective,normalized,var");
		
		EventDispatcher.getInstance().register(ProblemFinishedEvent.class, new IListener<ProblemFinishedEvent>() {

			@Override
			public void update(ProblemFinishedEvent event) {
				IProblem problem = event.getProblem();
				AExperiment experiment = event.getExperiment();

				double min = Double.MAX_VALUE;
				double max = Double.MIN_VALUE;

				for (IAlgorithm algorithm : experiment.getAlgorithms()) {
					for (NonDominatedSolutionSet set : experiment.getResult().get(problem, algorithm)) {
						if (set.size() != 1)
							throw new RuntimeException("Single Objective problem only one solution allowed.");
						Solution best = set.get(0);
						double value = best.getObjectives(objective);
						if (value < min)
							min = value;
						if (value > max)
							max = value;
					}
				}

				for (IAlgorithm algorithm : experiment.getAlgorithms()) {
					for (NonDominatedSolutionSet set : experiment.getResult().get(problem, algorithm)) {
						Solution best = set.get(0);
						double value = best.getObjectives(objective);
						double normalized = (max != min) ? (value - min) / (max - min) : Double.MAX_VALUE;
						pw.format("%s,%s,%s,%s,\"%s\"\n", problem, algorithm, value, normalized, best.getVariable());
					}
				}
				
			}
			
			
		});
		
	}

	// ! objective that is printed - indeed single objective but
	// ! if first objective always zero second objective is interesting
	protected int objective = 0;


	public int getObjective() {
		return objective;
	}

	public void setObjective(int objective) {
		this.objective = objective;
	}

}
