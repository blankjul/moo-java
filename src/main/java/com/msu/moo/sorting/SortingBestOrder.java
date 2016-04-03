package com.msu.moo.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.SolutionSet;

public class SortingBestOrder {

	
	public static <S extends ISolution<?>> List<NonDominatedSet<S>> sort(SolutionSet<S> population) {

		final int M = population.get(0).numOfObjectives();
		final int N = population.size();

		int numOfRankedSolutions = 0;

		// list sorted according the objective in index
		List<List<SolutionNode>> sortedByObjective = new ArrayList<>(M);

		// finally the fronts which are created
		List<List<SolutionNode>> fronts = new ArrayList<>();

		// create a template node list which is sorting according different
		// objectives
		List<SolutionNode> nodeList = new ArrayList<>(N);
		for (int n = 0; n < N; n++) {
			nodeList.add(new SolutionNode(n, M));
		}

		// sort all nodes according all objectives
		for (int m = 0; m < M; m++) {
			List<SolutionNode> copy = new ArrayList<>(nodeList);
			Collections.sort(copy, new SolutionNodeComparator<>(population, m));
			sortedByObjective.add(copy);
		}

		// for all solutions
		rankingloop:
		for (int n = 0; n < N; n++) {

			// for all objectives
			for (int m = 0; m < M; m++) {

				// if all solutions are ranked we are done
				if (numOfRankedSolutions == N) break rankingloop;

				SolutionNode node = sortedByObjective.get(m).get(n);
				node.isDominatingInObjective[m] = true;

				// if it is ranked just continue
				if (!node.isRanked()) {

					// != -1 when no front was found
					int indexOfFront = -1;
					List<Double> objNode = population.get(node.index).getObjectives();

					// for every front which exists so far
					for (int i = 0; i < fronts.size(); i++) {

						List<SolutionNode> front = fronts.get(i);
						boolean isDominated = false;

						// for every node in that front
						outerloop: for (SolutionNode other : front) {

							// if other is not better in current objective m, it
							// could not dominate node
							if (other.isDominatingInObjective[m] == false) {
								continue outerloop;
							} else {

								List<Double> objOther = population.get(other.index).getObjectives();

								// check for each objective if node is better than other 
								for (int j = 0; j < M; j++) {
									
									// if node is once better -> not dominated
									if (objNode.get(j) < objOther.get(j)) {
										continue outerloop;
									}
									
								}

								isDominated = true;
								break outerloop;

							}

						}

						// if the new solution is not dominated we found the
						// front
						if (!isDominated) {
							indexOfFront = i;
							break;
						}

					}

					// if no front was found create a new one to add
					if (indexOfFront == -1) {
						List<SolutionNode> l = new ArrayList<>();
						l.add(node);
						fronts.add(l);

						node.setRank(fronts.size() - 1);
						// else add to the correct one
					} else {
						fronts.get(indexOfFront).add(node);
						node.setRank(indexOfFront);
					}
					numOfRankedSolutions++;

				}

			}

		// end of ranking
		}
		
		
		// convert the result to NonDominatedSets
		List<NonDominatedSet<S>> result = new ArrayList<>(fronts.size());
		
		for (List<SolutionNode> front : fronts) {
			NonDominatedSet<S> set = new NonDominatedSet<>();
			
			for (SolutionNode s : front) {
				
				S solutionToAdd = population.get(s.index);
				set.addWithoutCheck(solutionToAdd);
				
			}
			result.add(set);
			
		}
		
		return result;
		
		

	}

}
