package com.msu.moo.visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class ScatterPlot extends Abstract2DPlot {

	
	public ScatterPlot() {
		super();
	}
	
	public ScatterPlot(String title) {
		super(title);
	}

	public ScatterPlot(String title, String xLabel, String yLabel) {
		super(title, xLabel, yLabel);
	}

	// collection to save all the series
	protected XYSeriesCollection collection = new XYSeriesCollection();

	/**
	 * Add a solution set to scatter plot!
	 * @param set with the data points. Must be 2D in objective space!!
	 * @param name of the data set
	 */
	public void add(SolutionSet set, String name) {
		if (set.isEmpty()) return;
		if (set.get(0).getObjectives().size() != 2) {
				throw new RuntimeException("Only plotting of 2D objective space is allowed!");
		}
		XYSeries series = new XYSeries(name);
		for (Solution s : set) series.add(s.getObjectives().get(0), s.getObjectives().get(1));
		collection.addSeries(series);
	}
	
	public void add(NonDominatedSolutionSet set, String name) {
		add(set.getSolutions(), name);
	}
	
	

	@Override
	public JFreeChart getChart() {
		JFreeChart chart = ChartFactory.createScatterPlot(title, xLabel, yLabel, collection,
				PlotOrientation.VERTICAL, true, true, false);
		return chart;
	}
}