package com.msu.moo.visualization;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class LinePlot extends Abstract2DPlot {

	// collection to save all the series
	protected XYSeriesCollection set = new XYSeriesCollection();

	
	
	public LinePlot(String title) {
		super(title);
	}


	public void add(List<List<Double>> l, String name) {
		if (l.isEmpty()) return;
		if (l.get(0).size() != 2) throw new RuntimeException("2D Plot -> Input size has to be 2.");
		
		final XYSeries series = new XYSeries(name);
		for(List<Double> point : l) series.add(point.get(0), point.get(1));
		set.addSeries(series);
		
	}
	


	@Override
	public JFreeChart getChart() {

		final JFreeChart chart = ChartFactory.createXYLineChart(this.title, 
				this.xLabel, 
				this.yLabel,
				set,
				PlotOrientation.VERTICAL, true, 
				true, 
				false 
		);

		chart.setBackgroundPaint(Color.white);

		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		plot.setRenderer(renderer);


		return chart;
	}
	


}
