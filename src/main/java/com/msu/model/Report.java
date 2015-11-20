package com.msu.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.msu.util.Util;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.ExperimentFininshedEvent;

public class Report {

	// ! printer to write the file
	protected PrintWriter pw;
	
	protected String pathToFile = null;


	public Report() {
		pw = new PrintWriter(System.out, true);
	}
	
	public Report(String pathToFile) {
		
		setPath(pathToFile);

		// close the file when the experiment is finished
		EventDispatcher.getInstance().register(ExperimentFininshedEvent.class, new IListener<ExperimentFininshedEvent>() {
			@Override
			public void handle(ExperimentFininshedEvent event) {
				pw.close();
			}
		});
	}

	
	public Report setPath(String pathToFile) {
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
	
	
	public void write(String s) {
		pw.write(s);
	}
	
	
}
