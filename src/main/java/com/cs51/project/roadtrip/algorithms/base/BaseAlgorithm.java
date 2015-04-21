package com.cs51.project.roadtrip.algorithms.base;

import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by robertschupp on 4/16/15.
 */
public abstract class BaseAlgorithm {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(BaseAlgorithm.class);

    //any common functionality to algorithms should go in here

    //I needed this in the BruteForceAlgorithm its probably useful in other algorithms
    public double getDistance (List<Node> nodesList, IGraph lg){
        double distance = 0;
        for (int i = 0; i < nodesList.size() - 1; i++) {
            distance += lg.getDistance(nodesList.get(i), nodesList.get(i + 1));
        }
        return distance;
    }
}
