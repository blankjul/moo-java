package com.msu.moo.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.msu.interfaces.IReport;
import com.msu.moo.util.Util;
import com.msu.moo.util.events.IListener;
import com.msu.moo.util.events.impl.EventDispatcher;
import com.msu.moo.util.events.impl.ExperimentFininshedEvent;

public abstract class AReport implements IReport {

	// ! printer to write the file
	protected PrintWriter pw;
	
	protected String pathToFile = null;


	public AReport() {
		pw = new PrintWriter(System.out, true);
	}
	
	public AReport(String pathToFile) {
		
		setPath(pathToFile);

		// close the file when the experiment is finished
		EventDispatcher.getInstance().register(ExperimentFininshedEvent.class, new IListener<ExperimentFininshedEvent>() {
			@Override
			public void handle(ExperimentFininshedEvent event) {
				pw.close();
			}
		});
	}

	
	public AReport setPath(String pathToFile) {
		try {
			pw = new PrintWriter(new FileOutputStream(pathToFile), true);
			this.pathToFile = pathToFile;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}

	
	@Override
	public String toString() {
		 if (pathToFile != null) return Util.readFile(pathToFile);
		 return "";
	}
	
	
	
}
