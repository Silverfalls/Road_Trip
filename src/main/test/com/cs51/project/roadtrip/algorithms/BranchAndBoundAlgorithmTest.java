package com.cs51.project.roadtrip.algorithms;

import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by robertschupp on 4/17/15.
 */
public class BranchAndBoundAlgorithmTest {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(BranchAndBoundAlgorithmTest.class);


    @Test
    public void testTest() {
        IGraph graph = new ListGraph(4);
        IAlgorithm alg = new BranchAndBoundAlgorithm();
        alg.execute(graph);
    }



}
