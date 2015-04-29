package com.cs51.project.roadtrip.services;

import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.enums.AlgType;
import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IComparisonService;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * This comparison requires that at least one exact algorithm and at least one non-exact algorithm are selected.
 * It then picks one of the exact algorithms as the control.  It then runs every algorithm against the same graph
 * number of iterations times.  However, after each iteration, it generates a new graph of the same size.  Then, after
 * all iterations, it reports how many times each non-exact solution was not the optimal solution and on average how
 * far off the optimal solution it was.
 */
public class ApproximateComparisonService implements IComparisonService {

    /**
     * Logger Instance
     */
    private static final Logger logger = Logger.getLogger(ApproximateComparisonService.class);

    private List<Result> approxResults = null;
    private Result optimalResult = null;

    /**
     * executes the Compare Accuracy comparison
     * returns null if there is not an exact algorithm or a non-exact algorithm or iterations is < 1
     */
    @Override
    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs, int iterations) {

        optimalResult = null;
        approxResults = null;

        IAlgorithm optimalAlg = null;
        List<IAlgorithm> approximateAlgs = null;

        //have to have at least one iteration
        if (iterations < 1) {
            logger.warn("executeComparison | iterations is less than 1");
            return null;
        }

        //have to have a graph to start (just to get the desired size)
        if (graph == null) {
            logger.warn("executeComparison | graph is null");
            return null;
        }

        //can't have a null list of algorithms
        if (algs == null) {
            logger.warn("execute | passed in list of algorithms is null");
            return null;
        }

        //set the control algorithm and all of the non-exact algorithms
        for (IAlgorithm alg : algs) {
            AlgType algType = AlgType.getAlgTypeByImplementation(alg);
            if (algType.isOptimal()) {
                optimalAlg = alg;
            } else {
                if (approximateAlgs == null) {
                    approximateAlgs = new ArrayList<>();
                }
                approximateAlgs.add(alg);
            }
        }

        //make sure we have at least one exact and one non-exact algorithm before we run
        if (optimalAlg != null && approximateAlgs != null && !approximateAlgs.isEmpty()) {

            for (int i = 0; i < iterations; i++) {
                IGraph g = new ListGraph(graph.getGraphSize());

                Result optimalResult = optimalAlg.execute(g.getClone());

                List<Result> notOptimalResults = new ArrayList<>();
                approximateAlgs.stream().forEach(alg -> notOptimalResults.add(alg.execute(g.getClone())));

                mergeResults(optimalResult, notOptimalResults);
            }

            averageResults(iterations);
        } else {
            logger.warn("execute | this comparison requires at least one exact algorithm and one or more non exact algorithms");
            return null;
        }

        //put the optimal result at the start of the list so it is the first one printed out
        approxResults.add(0, optimalResult);

        return approxResults;
    }

    //update the running results with the most recent set of results
    private void mergeResults(Result optimal, List<Result> notOptimal) {

        if (optimal == null || notOptimal == null) {
            logger.error("mergeResults | optimal result or non optimal results is null");
            return;
        }

        if (optimalResult == null) {
            optimalResult = optimal;
        } else {
            optimalResult.setCalculatedDistance(optimalResult.getCalculatedDistance().add(optimal.getCalculatedDistance()));
        }

        if (approxResults == null) {
            approxResults = notOptimal;
        } else {
            //aggregate the results
            for (Result r : notOptimal) {
                for (Result ar : approxResults) {
                    if (r.getName().equals(ar.getName())) {
                        ar.setCalculatedDistance(ar.getCalculatedDistance().add(r.getCalculatedDistance()));
                        BigDecimal distDelta = r.getCalculatedDistance().subtract(optimal.getCalculatedDistance());
                        if (distDelta.compareTo(BigDecimal.ZERO) != 0) {
                            if (ar.getNumIncorrectSolutions() == null) {
                                ar.setNumIncorrectSolutions(1);
                            } else {
                                ar.setNumIncorrectSolutions(ar.getNumIncorrectSolutions() + 1);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    //any fields that should be averaged can use the number of iterations (cycles) and the summed values
    private void averageResults(int cycles) {
        for (Result r : approxResults) {
            BigDecimal numCycles = new BigDecimal(cycles);
            BigDecimal optAvg = optimalResult.getCalculatedDistance().divide(numCycles, 2, RoundingMode.HALF_UP);
            optimalResult.setCalculatedDistance(optAvg);
            r.setCalculatedDistance(r.getCalculatedDistance().divide(numCycles, 2, RoundingMode.HALF_UP));
            r.setAverageDeviation((r.getCalculatedDistance().subtract(optAvg)));
        }
    }
}
