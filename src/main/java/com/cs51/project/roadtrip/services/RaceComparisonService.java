package com.cs51.project.roadtrip.services;

import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IComparisonService;
import com.cs51.project.roadtrip.interfaces.IGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Alexander on 23.04.2015.
 */
public class RaceComparisonService implements IComparisonService {
    @Override
    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs) throws Exception {
        return executeComparison (graph,algs,20000);
    }

    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs, long time) throws Exception {

        return null;
    }
}
