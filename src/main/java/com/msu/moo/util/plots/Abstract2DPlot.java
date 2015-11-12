package com.msu.moo.util.plots;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;

import com.msu.interfaces.IPlot;

public abstract class Abstract2DPlot implements IPlot {
	
	protected String title = "Title";
	protected String xLabel = "X";
	protected String yLabel = "Y";

	
	public Abstract2DPlot() {
		super();
	}
	
	
	public Abstract2DPlot(String title) {
		super();
		this.title = title;
	}


	public Abstract2DPlot(String title, String xLabel, String yLabel) {
		super();
		this.title = title;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
	}
	
	
	public void save(String name) {
		try {
			File file = new File(name);
			FileOutputStream fop = new FileOutputStream(file);
			ChartUtilities.writeChartAsPNG(fop, getChart(), 800, 600);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void show() {
        ChartFrame frame = new ChartFrame("", getChart());
        frame.pack();
        frame.setVisible(true);
	}
}
