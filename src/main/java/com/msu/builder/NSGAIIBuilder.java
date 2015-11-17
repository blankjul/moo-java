package com.msu.builder;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.msu.moo.algorithms.nsgaII.NSGAII;

public class NSGAIIBuilder extends Builder<NSGAII> {

	public NSGAIIBuilder() {
		super(NSGAII.class);
	}

	@Override
	public void addRequiredFields(Set<String> requiredFields) {
		requiredFields.addAll(Arrays.asList("factory", "crossover", "mutation"));
	}

	@Override
	public void addDefaultFields(Map<String, Object> defaultFields) {
		defaultFields.put("probMutation", 0.3);
		defaultFields.put("populationSize", 100);
	}

}
