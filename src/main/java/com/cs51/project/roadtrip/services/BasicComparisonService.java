package com.cs51.project.roadtrip.services;

import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IComparisonService;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs the selected algorithms on the same graph n times.  If n is > 1, it averages the running
 * time for each algorithm.
 */
public class BasicComparisonService implements IComparisonService {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(BasicComparisonService.class);

    List<Result> avgResults;

    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs, int numCycles) {

        //have to have some algorithms to run
        if (algs == null || algs.isEmpty()) {
            logger.warn("executeComparison | list of algs is either null or empty");
            return null;
        }

        //have to have a graph
        if (graph == null) {
            logger.warn("executeComparison | graph is null");
            return null;
        }

        //have to have at least one iteration
        if (numCycles < 1) {
            logger.warn("executeComparison | numCycles is less than 1");
            return null;
        }

        avgResults = null;

        //create a list for our results
        List<Result> currentResults = new ArrayList<>();

        //execute the algorithms on the graph numCycles times
        for (int i = 0; i < numCycles; i++) {
            //execute each algorithm and store the result
            algs.stream().forEach(alg -> currentResults.add(alg.execute(graph.getClone())));
            addResults(currentResults);
            currentResults.clear();
        }

        //now average out any fields we want to average
        averageFields(numCycles);

        //returned our averaged results
        return avgResults;
    }

    //add the most recent results to our average results
    private void addResults(List<Result> current) {
        //if this is just the first set of results, set the average results to the list of results
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
                //merge the results however you want based on the field
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

    //any fields that should be averaged over numCycles should have it done here
    private void averageFields(int numCycles) {
        for (Result r : avgResults) {
            r.setRunningTime(r.getRunningTime() / (long) numCycles);
            r.setIterations(r.getIterations() / (long) numCycles);
        }
    }
}
