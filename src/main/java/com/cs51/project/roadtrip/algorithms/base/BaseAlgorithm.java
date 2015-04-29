package com.cs51.project.roadtrip.algorithms.base;

import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

/**
 * any common functionality to algorithms should go in here
 */
public abstract class BaseAlgorithm {

    /**
     * Logger Instance
     */
    private static final Logger logger = Logger.getLogger(BaseAlgorithm.class);

    //get the overall distance of a list based path
    protected BigDecimal getDistance(List<Node> nodesList, IGraph lg){
        BigDecimal distance = BigDecimal.ZERO;
        for (int i = 0; i < nodesList.size() - 1; i++) {
            distance = distance.add(lg.getDistance(nodesList.get(i), nodesList.get(i + 1)));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("a distance of " + distance + "was calculated");
        }
        return distance;
    }
}
