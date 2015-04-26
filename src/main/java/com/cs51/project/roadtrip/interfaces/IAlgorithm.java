package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.common.dto.Result;

/**
 * All algorithm implementations should implement this interface
 */
public interface IAlgorithm {

    /**
     * Executes this specific algorithm on a graph
     * @param graph the graph
     * @return the result of this algorithm
     */
    public Result execute(IGraph graph);

    /**
     * this can be used to reset the result information for an algorithm
     */
    public void reset();
}
