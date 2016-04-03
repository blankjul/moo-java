package com.msu.moo.util.visualization;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;

public class NonDominatedSetToJson {

	// ! string writer for getting the result
	protected List<List<Scatter>> scatters = new ArrayList<>();

	public void write(PrintStream pw) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(pw, scatters);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public <V> void append(Collection<ISolution<V>> set) {
		
		NonDominatedSet<ISolution<V>> isNonDominated = new NonDominatedSet<>(set);

		Scatter scNonDom = new Scatter();
		Scatter scDom = new Scatter();
		
		for (ISolution<V> s : set) {
			Scatter sc = (isNonDominated.getSolutions().contains(s)) ? scNonDom : scDom;
			sc.x.add(s.getObjective(0));
			sc.y.add(s.getObjective(1));
		}
		
		scatters.add(Arrays.asList(scNonDom, scDom));

	}

	public void asHtml(String pathToHtml) {
		try {

			StringWriter sw = new StringWriter();
			ObjectMapper mapper = new ObjectMapper();

			mapper.writeValue(sw, scatters);

			PrintWriter writer = new PrintWriter(pathToHtml, "UTF-8");

			HashMap<String, Object> scopes = new HashMap<String, Object>();
			scopes.put("data", sw.toString());

			MustacheFactory mf = new DefaultMustacheFactory();
			Mustache mustache = mf.compile("resources/fronts.html");
			mustache.execute(writer, scopes);
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
