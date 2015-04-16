package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.common.dto.Result;

import java.util.List;

//TODO fill in java docs
/**
 * Created by Alexander on 14.04.2015.
 */
public interface IComparisonService {

    /**
     *
     * @param graph
     * @param algs
     * @return
     * @throws Exception
     */
    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs) throws Exception;


}
