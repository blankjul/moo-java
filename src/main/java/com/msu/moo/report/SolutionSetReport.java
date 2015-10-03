package com.msu.moo.report;

import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.IListener;
import com.msu.moo.util.events.RunFinishedEvent;

public class SolutionSetReport extends AReport {

	public SolutionSetReport() {
		super();
		EventDispatcher.getInstance().register(RunFinishedEvent.class, new IListener<RunFinishedEvent>() {
			@Override
			public void update(RunFinishedEvent event) {
				pw.println("---------------------------------------------");
				pw.format("Problem: %s | Algorithm: %s | Run: %s \n", event.getProblem(), event.getAlgorithm(), event.getRun());
				pw.println("---------------------------------------------");
				pw.print(event.getNonDominatedSolutionSet().toString());
				pw.println();
			}
		});
	}
	




}
