package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.common.dto.Result;

//TODO fill in javadocs

public interface IAlgorithm {

    /**
     *
     * @param graph
     * @return
     */
    public Result execute(IGraph graph);

    /**
     *
     */
    public void reset();
}
