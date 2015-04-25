package com.cs51.project.roadtrip.services;

import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IComparisonService;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by robertschupp on 4/16/15.
 */
public class BasicComparisonService implements IComparisonService {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(BasicComparisonService.class);


    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs) {

        return executeComparison(graph, algs, 1);
    }

    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs, int numCycles) {
        //create a list for our results
        List<Result> results = new ArrayList<>();




        //execute each algorithm and store the result
        algs.stream().forEach(alg -> results.add(alg.execute(graph)));

        return results;
    }

}
