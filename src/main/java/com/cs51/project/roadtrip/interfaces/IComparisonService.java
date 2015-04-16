package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.common.Result;

import java.util.List;

/**
 * Created by Alexander on 14.04.2015.
 */
public interface IComparisonService {
    //TODO: implement the real ComparisonService Interface

    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs) throws Exception;


}
