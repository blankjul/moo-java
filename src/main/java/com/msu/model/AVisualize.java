package com.msu.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;

import com.msu.interfaces.IPlot;

/**
 * This interface provides the method to visualize an experiment result in any
 * way.
 */
public abstract class AVisualize {

	protected boolean isVisible = false;
	protected String pathToFolder = null;

	public AVisualize setOutputFolder(String pathToFile) {
		this.pathToFolder = pathToFile;
		return this;
	}

	public AVisualize setVisibility(boolean isVisible) {
		this.isVisible = isVisible;
		return this;
	}

	protected void showOrPrint(IPlot plot, String title) {
		if (pathToFolder != null) {
			AVisualize.print(plot, String.format("%s/%s.png", pathToFolder, title));
		}
		if (isVisible) {
			AVisualize.show(plot);
		}
	}
	
	public static void show(IPlot plot) {
		ChartFrame frame = new ChartFrame("", plot.getChart());
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void print(IPlot plot, String fileName) {
		try {
			File file = new File(fileName);
			FileOutputStream fop = new FileOutputStream(file);
			ChartUtilities.writeChartAsPNG(fop, plot.getChart(), 1600, 1200);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
