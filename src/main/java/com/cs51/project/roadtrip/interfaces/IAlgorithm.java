package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.common.dto.Result;

/**
 * Created by Alexander on 14.04.2015.
 */
public interface IAlgorithm {
    //TODO: implement the real Algorithm Interface
    public Result execute(IGraph graph);
    public void reset();
}
