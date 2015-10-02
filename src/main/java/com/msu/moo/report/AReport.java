package com.msu.moo.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.lowagie.text.pdf.codec.Base64.OutputStream;
import com.msu.moo.interfaces.IReport;
import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.ExperimentFininshedEvent;
import com.msu.moo.util.events.IListener;

public abstract class AReport implements IReport {

	//! printer to write the file
	protected PrintWriter pw;
	
	public AReport() {
		pw = new PrintWriter(System.out, true);
		// close the file when the experiment is finished
		EventDispatcher.getInstance().register(ExperimentFininshedEvent.class, new IListener<ExperimentFininshedEvent>() {
			@Override
			public void update(ExperimentFininshedEvent event) {
				pw.close();
			}
		});
	}
	
	public AReport set(String pathToFile) {
		try {
			pw = new PrintWriter(new FileOutputStream(pathToFile), true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	
	public AReport set(OutputStream os) {
		pw = new PrintWriter(os, true);
		return this;
	}
	



}
