package com.msu.moo.util.plots;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.BoxAndWhiskerCalculator;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

public class BoxPlot extends Abstract2DPlot {

	// ! the data set which contains the data
	protected DefaultBoxAndWhiskerCategoryDataset set = new DefaultBoxAndWhiskerCategoryDataset();

	
	public BoxPlot() {
		super();
	}

	public BoxPlot(String title) {
		super(title);
	}


	public void add(List<Double> l, String name) {
		BoxAndWhiskerItem entry = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(l);
		set.add(entry, name, "");
	}



	@Override
	public JFreeChart getChart() {
		BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();   
	    renderer.setFillBox(true);
	    renderer.setMedianVisible(true);
	    renderer.setMeanVisible(false);
	    
		CategoryPlot xyplot = new CategoryPlot(set, new CategoryAxis(xLabel), new NumberAxis(yLabel), renderer);
		JFreeChart jfreechart = new JFreeChart(title, xyplot);
		jfreechart.setBackgroundPaint(Color.white);
		return jfreechart;
	}

	
}
