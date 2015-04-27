package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.common.dto.Result;

import java.util.List;

/**
 * All Comparison Services should implement this interface
 */
public interface IComparisonService {

    /**
     * executes a comparison of different algorithms on a graph iterations number of times
     */
    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs, int iterations);


}
