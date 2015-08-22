package com.msu.moo.visualization;

import org.jfree.chart.JFreeChart;

public interface IPlot {
	
	/**
	 * Every Plot needs the chart frame to visualize the results
	 * @return chart frame to make it visible
	 */
	public JFreeChart getChart();

}
