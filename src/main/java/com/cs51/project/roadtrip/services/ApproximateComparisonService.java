package com.cs51.project.roadtrip.services;

import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.enums.AlgType;
import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IComparisonService;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO fill this out
 */
public class ApproximateComparisonService implements IComparisonService {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(ApproximateComparisonService.class);

    List<Result> approxResults = null;
    Result optimalResult = null;

    @Override
    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs, int iterations) {

        optimalResult = null;
        approxResults = null;

        IAlgorithm optimalAlg = null;
        List<IAlgorithm> approximateAlgs = null;

        if (iterations < 1) {
            logger.warn("executeComparison | iterations is less than 1");
            return null;
        }

        if (graph == null) {
            logger.warn("executeComparison | graph is null");
            return null;
        }

        if (algs == null) {
            logger.warn("execute | passed in list of algorithms is null");
            return null;
        }

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

        approxResults.add(0, optimalResult);

        return approxResults;
    }

    private void mergeResults(Result optimal, List<Result> notOptimal) {

        if (optimal != null) {
            if (optimalResult == null) {
                optimalResult = optimal;
            }
        } else {
            logger.error("mergeResults | optimal result is null");
        }

        if (approxResults == null) {
            approxResults = notOptimal;
            return;
        }

        optimalResult.setCalculatedDistance(optimalResult.getCalculatedDistance().add(optimal.getCalculatedDistance()));

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
