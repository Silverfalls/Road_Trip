package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.common.dto.Result;

/**
 * All algorithm implementations should implement this interface
 */
public interface IAlgorithm {

    /**
     * Executes this specific algorithm on a graph
     */
    public Result execute(IGraph graph);
}
