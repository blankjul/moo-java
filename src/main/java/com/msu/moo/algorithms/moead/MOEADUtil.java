package com.msu.moo.algorithms.moead;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.msu.util.Random;

public class MOEADUtil {

	public static List<List<Double>> getUniformDistributedWeights(Random r, int n, int length) {
		if (length != 2) throw new RuntimeException("This implemenation works so far only with 2 objectives!");
		List<List<Double>> result = new ArrayList<>(n);
		result.add(Arrays.asList(0.0, 1.0));
		final double step = 1.0 / (double) (n-1);
		
		for (int i = 1; i < n-1; i++) {
			double value = i * step;
			result.add(Arrays.asList(value, 1.0 - value));
		}
		
		result.add(Arrays.asList(1.0, 0.0));
		return result;
	}
	
	public static List<List<Double>> getRandomWeights(Random r, int n, int length) {
		List<List<Double>> result = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			double sum = 0;
			List<Double> v = new ArrayList<>();
			for (int j = 0; j < length; j++) {
				Double value = r.nextDouble();
				v.add(value);
				sum += value;
			}
			for (int j = 0; j < length; j++) {
				v.set(j, v.get(j) / sum);
			}
			result.add(v);
		}
		return result;
	}

	public static Double getEuclideanDistance(List<Double> v1, List<Double> v2) {
		if (v1.size() != v2.size())
			throw new RuntimeException("Eucl. Distance could not be calculated. Dimensions are different!");

		final int size = v1.size();
		Double result = 0d;
		for (int i = 0; i < size; i++) {
			result += Math.pow(v1.get(i) - v2.get(i),2);
		}
		result = Math.sqrt(result);
		return result;
	}
	
	
	public static List<List<Double>> calcDistanceMatrix(List<List<Double>> weights) {
		List<List<Double>> result = new ArrayList<>();
		for (int i = 0; i < weights.size(); i++) {
			List<Double> v = new ArrayList<>();
			for (int j = 0; j < weights.size(); j++) {
				v.add(getEuclideanDistance(weights.get(i), weights.get(j)));
			}
			result.add(v);
		}
		return result;
	}
	
	public static List<Integer> getNearestTWeights(int index, List<List<Double>> distances, int T) {
		List<Integer> indices = new ArrayList<>();
		for (int i = 0; i < distances.size(); i++) {
			indices.add(i);
		}
		Collections.sort(indices, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Double.compare(distances.get(index).get(o1), distances.get(index).get(o2));
			}
		});
		return new ArrayList<>(indices.subList(0, T));
	}
	
	
	public static Double calcWeightedSum(List<Double> obj, List<Double> w) {
		double result = 0;
		for (int i = 0; i < obj.size(); i++) {
			result += obj.get(i) * w.get(i);
		}
		return result;
	}
	
	public static Double calcTchebichew(List<Double> obj, List<Double> w, List<Double> z) {
		double maxDistance = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < obj.size(); i++) {
			maxDistance = Math.max(maxDistance, w.get(i) * (obj.get(i) - z.get(i)));
		}
		return maxDistance;
	}
	
	
	
	public static void updateReferencePoint(List<Double> z, List<Double> obj) {
		for (int i = 0; i < z.size(); i++) {
			z.set(i, Math.min(z.get(i), obj.get(i)));
		}
	}
	

}
