package com.msu.report;

import com.msu.model.AReport;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.RunFinishedEvent;

public class SolutionSetReport extends AReport {

	public SolutionSetReport() {
		super();
		EventDispatcher.getInstance().register(RunFinishedEvent.class, new IListener<RunFinishedEvent>() {
			@Override
			public void handle(RunFinishedEvent event) {
				pw.println("---------------------------------------------");
				pw.format("Problem: %s | Algorithm: %s | Run: %s \n", event.getProblem(), event.getAlgorithm(), event.getRun());
				pw.println("---------------------------------------------");
				pw.print(event.getNonDominatedSolutionSet().toString());
				pw.println();
				pw.println("---------------------------------------------");
			}
		});
	}
	




}
