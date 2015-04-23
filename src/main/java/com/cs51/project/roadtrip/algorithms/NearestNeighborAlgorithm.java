package com.cs51.project.roadtrip.algorithms;

import com.cs51.project.roadtrip.algorithms.base.BaseAlgorithm;
import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by robertschupp on 4/16/15.
 */
public class NearestNeighborAlgorithm extends BaseAlgorithm implements IAlgorithm {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(NearestNeighborAlgorithm.class);

    @Override
    public Result execute(IGraph graph) {
        long startTime = System.currentTimeMillis();

        if (logger.isDebugEnabled()) {
            logger.debug("start to execute NearestNeighbor");
        }

        LinkedList<Node> shortestPath = new LinkedList<>();
        Node startingNode = graph.getStartingNode();
        startingNode.setVisited(true);
        shortestPath.addFirst(startingNode);

        Node next = graph.getClosestUnvisitedNeighbor(startingNode);
        while (next != null){
            shortestPath.addLast(next);
            next.setVisited(true);
            next = graph.getClosestUnvisitedNeighbor(next);
        }
        shortestPath.addLast(startingNode);

        Result result = new Result();

        //set values of result
        result.setRunningTime(System.currentTimeMillis() - startTime);
        result.setCalculatedDistance(getDistance(shortestPath, graph));
        result.setCalculatedPath(shortestPath);
        result.setGraphSize(graph.getGraphSize());
        result.setFinished(true);

        if (logger.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            for (Node aShortestPath : shortestPath) {
                sb.append(aShortestPath.getName());
                sb.append(" -> ");
            }
            logger.debug("The following is currently the shortest path with " + getDistance(shortestPath,graph) + " " +
                    sb.substring(0, sb.length() - 4));
            logger.debug("Finished NearestNeighbor");
        }
        return result;

    }

    @Override
    public void reset() {

    }
}
