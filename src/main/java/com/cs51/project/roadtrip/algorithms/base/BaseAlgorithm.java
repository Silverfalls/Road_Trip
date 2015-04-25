package com.cs51.project.roadtrip.algorithms.base;

import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
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
    public BigDecimal getDistance (List<Node> nodesList, IGraph lg){
        BigDecimal distance = BigDecimal.ZERO;
        for (int i = 0; i < nodesList.size() - 1; i++) {
            distance = distance.add(lg.getDistance(nodesList.get(i), nodesList.get(i + 1)));
        }
        return distance;
    }
}
