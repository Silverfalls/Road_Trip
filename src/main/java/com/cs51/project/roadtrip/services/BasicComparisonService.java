package com.cs51.project.roadtrip.services;

import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IComparisonService;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO fill this out
 */
public class BasicComparisonService implements IComparisonService {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(BasicComparisonService.class);

    List<Result> avgResults;

    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs, int numCycles) {

        avgResults = null;

        //create a list for our results
        List<Result> currentResults = new ArrayList<>();

        for (int i = 0; i < numCycles; i++) {
            //execute each algorithm and store the result
            algs.stream().forEach(alg -> currentResults.add(alg.execute(graph.clone())));
            addResults(currentResults, i);
            currentResults.clear();
        }

        //now average out any fields we want to average
        averageFields(numCycles);

        //returned our averaged results
        return avgResults;
    }

    private void addResults(List<Result> current, int count) {
        if (avgResults == null) {
            if (current != null) {
                avgResults = new ArrayList<>();
                for (Result r : current) {
                    avgResults.add(r);
                }
            } else {
                logger.error("addResults | average list and current list are null");
            }
            return;
        }

        for (Result r : avgResults) {
            for (Result cr : current) {
                if (r.getName().equals(cr.getName())) {
                    r.setCalculatedPath(cr.getCalculatedPath());
                    r.setGraphSize(cr.getGraphSize());
                    r.setCalculatedDistance(cr.getCalculatedDistance());
                    r.setRunningTime(r.getRunningTime() + cr.getRunningTime());
                    r.setIterations(r.getIterations() + cr.getIterations());
                }
            }
        }
    }

    private void averageFields(int numCycles) {
        for (Result r : avgResults) {
            r.setRunningTime(r.getRunningTime() / (long) numCycles);
            r.setIterations(r.getIterations() / numCycles);
        }
    }
}
