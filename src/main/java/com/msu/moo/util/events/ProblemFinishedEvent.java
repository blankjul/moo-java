package com.msu.moo.util.events;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class ProblemFinishedEvent implements IEvent {

	protected AExperiment experiment;
	
	protected IProblem problem;

	protected int numOfRuns;
	
	protected NonDominatedSolutionSet trueFront;

	public ProblemFinishedEvent(AExperiment experiment, IProblem problem, int numOfRuns, NonDominatedSolutionSet trueFront) {
		super();
		this.experiment = experiment;
		this.problem = problem;
		this.numOfRuns = numOfRuns;
		this.trueFront = trueFront;
	}

	public AExperiment getExperiment() {
		return experiment;
	}

	public IProblem getProblem() {
		return problem;
	}

	public int getNumOfRuns() {
		return numOfRuns;
	}

	public void setNumOfRuns(int numOfRuns) {
		this.numOfRuns = numOfRuns;
	}

	public NonDominatedSolutionSet getTrueFront() {
		return trueFront;
	}
	
	
	
	
	
}
