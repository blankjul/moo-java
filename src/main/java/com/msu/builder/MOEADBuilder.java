package com.msu.builder;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.msu.moo.algorithms.moead.MOEAD;

public class MOEADBuilder extends Builder<MOEAD> {

	public MOEADBuilder() {
		super(MOEAD.class);
	}

	@Override
	public void addRequiredFields(Set<String> requiredFields) {
		requiredFields.addAll(Arrays.asList("factory", "crossover", "mutation"));
	}

	@Override
	public void addDefaultFields(Map<String, Object> defaultFields) {
		defaultFields.put("probMutation", 0.3);
		defaultFields.put("populationSize", 100);
		defaultFields.put("T", 100);
		//defaultFields.put("delta", 1d);
		//defaultFields.put("n_r", 10);
	}

}
