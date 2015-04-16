package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.common.dto.Result;

//TODO fill in javadocs
/**
 * Created by Alexander on 14.04.2015.
 */
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
