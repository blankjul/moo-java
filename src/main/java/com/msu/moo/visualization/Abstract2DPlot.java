package com.msu.moo.visualization;

import org.jfree.chart.ChartFrame;

public abstract class Abstract2DPlot implements IPlot {
	
	protected String title = "Title";
	protected String xLabel = "X";
	protected String yLabel = "Y";

	
	public Abstract2DPlot() {
		super();
	}


	public Abstract2DPlot(String title, String xLabel, String yLabel) {
		super();
		this.title = title;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
	}



	public void show() {
        ChartFrame frame = new ChartFrame("", getChart());
        frame.pack();
        frame.setVisible(true);
	}
}
