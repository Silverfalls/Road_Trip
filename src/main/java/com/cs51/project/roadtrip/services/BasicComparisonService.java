package com.cs51.project.roadtrip.services;

import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IComparisonService;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robertschupp on 4/16/15.
 */
public class BasicComparisonService implements IComparisonService {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(BasicComparisonService.class);

    public List<Result> executeComparison(IGraph graph, List<IAlgorithm> algs) {

        //TODO i just stubbed this out for the command UI while I was working on it.. feel free to do as you please
        Result result = new Result();
        List<Result> results = new ArrayList<>();
        results.add(result);

        return results;
    }

}
