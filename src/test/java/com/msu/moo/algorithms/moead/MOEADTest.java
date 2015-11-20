package com.msu.moo.algorithms.moead;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.util.MyRandom;

public class MOEADTest {
	
	
	public List<List<Double>> getExample() {
		List<List<Double>> weights = new ArrayList<>();
		weights.add(Arrays.asList(0d,0d));
		weights.add(Arrays.asList(2d,-1d));
		weights.add(Arrays.asList(-2d,2d));
		return weights;
	}
	
	@Test
	public void testRandomWeights() {
		for (List<Double> l : MOEADUtil.getRandomWeights(new MyRandom(), 100, 2)) {
			assertEquals(2,l.size());
		}
	}
	
	@Test
	public void testGetEuclideanDistanceEqual() {
			assertEquals(0.0, MOEADUtil.getEuclideanDistance(Arrays.asList(0d,0d), Arrays.asList(0d,0d)), 0.01);
	}
	
	@Test
	public void testGetEuclideanDistanceNotNull() {
			assertEquals(Math.sqrt(2d), MOEADUtil.getEuclideanDistance(Arrays.asList(0d,0d), Arrays.asList(1d,1d)), 0.01);
	}
	
	@Test
	public void testGetEuclideanDistanceFurtherExample() {
			assertEquals(Math.sqrt(25), MOEADUtil.getEuclideanDistance(Arrays.asList(2d,-1d), Arrays.asList(-2d,2d)), 0.01);
	}
	
	
	@Test
	public void testCalcDistanceMatrix() {
		List<List<Double>> weights = getExample();
		List<List<Double>> distances = MOEADUtil.calcDistanceMatrix(weights);
		
		assertEquals(0, distances.get(0).get(0), 0.01);
		assertEquals(Math.sqrt(5), distances.get(0).get(1), 0.01);
		assertEquals(Math.sqrt(8), distances.get(0).get(2), 0.01);
		
		assertEquals(Math.sqrt(5), distances.get(0).get(1), 0.01);
		assertEquals(0, distances.get(1).get(1), 0.01);
		assertEquals(Math.sqrt(25), distances.get(1).get(2), 0.01);
		
		assertEquals(Math.sqrt(8), distances.get(2).get(0), 0.01);
		assertEquals(Math.sqrt(25), distances.get(2).get(1), 0.01);
		assertEquals(0, distances.get(2).get(2), 0.01);
		
	}
	
	
	@Test
	public void testgetNearestTWeights() {
		List<List<Double>> weights = getExample();
		List<List<Double>> distances = MOEADUtil.calcDistanceMatrix(weights);
		
		assertEquals(2, MOEADUtil.getNearestTWeights(0, distances, 2).size());
		
		assertEquals(Arrays.asList(0,1), MOEADUtil.getNearestTWeights(0, distances, 2));
		assertEquals(Arrays.asList(1,0), MOEADUtil.getNearestTWeights(1, distances, 2));
		assertEquals(Arrays.asList(2,0), MOEADUtil.getNearestTWeights(2, distances, 2));

	}
	
	
	

}
