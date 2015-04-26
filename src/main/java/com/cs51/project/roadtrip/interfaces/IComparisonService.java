package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.common.dto.Result;

import java.util.List;

public interface IComparisonService {

    /**
     * Executes the specific typ of algorithm
     * @param graph the graph for the comparison
     * @param algs a list of algorithms
     * @return a list of results from the algorithms
     * @throws Exception
     */
    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs, int iterations);


}
