package com.cs51.project.roadtrip.algorithms;

import com.cs51.project.roadtrip.algorithms.base.BaseAlgorithm;
import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class BruteForceAlgorithm extends BaseAlgorithm implements IAlgorithm {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(BruteForceAlgorithm.class);

    private Result result = null;

    private Node startingNode = null;

    private IGraph graph = null;

    private BigDecimal shortestPathDistance = BigDecimal.ZERO;

    private List<Node> shortestPath = null;

    private long iterations = 0;



    //TODO
    //we have to still:
    //remove the starting Node, then calculate all permutations, then add the starting node back on to the head and the tail
    //then calculate the distance
    //because n! grows so fast, we will probably want to implement it in such a way that we are not storing all permutations in
    //memory at once... rather, we get a permutation, calculate the distance,... if that's the new lowest distance, store that as the
    //solution and the distance, then repeat

    //This function simply prints out the different permutations...read the comments above... we are far from done with this one :)
    private void permute(List<Node> nodes, int k) {
        for (int i = k; i < nodes.size(); i++) {
            Collections.swap(nodes, i, k);
            permute(nodes, k + 1);
            Collections.swap(nodes, k, i);
        }
        if (k == nodes.size() - 1) {

            LinkedList<Node> currentPath = new LinkedList<>(nodes);

            //add the starting node back to the path
            currentPath.addFirst(startingNode);
            currentPath.addLast(startingNode);
            iterations++;

            BigDecimal currentDistance = getDistance(currentPath,graph);

            if(currentDistance.compareTo(shortestPathDistance) == -1 || shortestPathDistance.compareTo(BigDecimal.ZERO) == 0){

                StringBuilder sb = new StringBuilder();
                shortestPathDistance = currentDistance;
                shortestPath = currentPath;
                sb.append("The following is currently the shortest path with ").append(currentDistance).append(" ");

                for (Node aShortestPath : shortestPath) {
                    sb.append(aShortestPath.getName());
                    sb.append(" -> ");
                }

                if (logger.isDebugEnabled()) {
                    logger.debug(sb.substring(0, sb.length() - 4));
                }
            }
        }
    }

    @Override
    public Result execute(IGraph graph) {

        long startTime = System.currentTimeMillis();

        if (logger.isDebugEnabled()) {
            logger.debug("start to execute BruteForceAlgorithm");
        }

        this.graph = graph;
        startingNode = graph.getStartingNode();

        List<Node> nodesList = graph.getListOfNodes();
        nodesList.remove(startingNode);

        result = new Result();

        permute(nodesList, 0);

        //set values of result
        result.setRunningTime(System.currentTimeMillis() - startTime);
        result.setCalculatedDistance(shortestPathDistance);
        result.setCalculatedPath(shortestPath);
        result.setGraphSize(graph.getGraphSize());
        result.setFinished(true);
        result.setOptimal(true);
        result.setIterations(iterations);

        shortestPathDistance = BigDecimal.ZERO;
        shortestPath = null;
        iterations  = 0;

        if (logger.isDebugEnabled()) {
            logger.debug("finished BruteForceAlgorithm");
        }
        return result;
    }

    @Override
    public void reset() {
        result = null;
    }


}
