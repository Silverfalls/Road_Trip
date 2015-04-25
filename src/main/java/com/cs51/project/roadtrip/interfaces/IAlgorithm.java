package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.common.dto.Result;

//TODO fill in javadocs

public interface IAlgorithm {

    /**
     * Executes this specific algorithm on a graph
     * @param graph the graph
     * @return the result of this algorithm
     */
    public Result execute(IGraph graph);

    /**
     * this can be used to reset the result information on a
     */
    public void reset();
}
