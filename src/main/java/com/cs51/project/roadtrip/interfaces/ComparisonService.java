package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.common.Result;
import com.cs51.project.roadtrip.enums.CompType;

import java.util.List;

/**
 * Created by Alexander on 14.04.2015.
 */
public interface ComparisonService {
    //TODO: implement the real ComparisonService Interface

    public List<Result> executeComparison(Graph graph, List<Algorithm> algs) throws Exception;


}
