package com.msu.util.plots;

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
		
		final int dimension = set.get(0).getObjective().size();
		
		if (dimension <= 0 || dimension > 2) {
			throw new RuntimeException("Only plotting of 2D objective space is allowed!");
		} else {
			XYSeries series = new XYSeries(name);
			for (Solution s : set) {
				double first = s.getObjective().get(0);
				double second = (dimension == 1) ? 0 : s.getObjective().get(1);
				series.add(first,second );
			}
			collection.addSeries(series);
		} 
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