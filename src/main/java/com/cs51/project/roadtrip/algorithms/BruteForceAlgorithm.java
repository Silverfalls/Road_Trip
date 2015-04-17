package com.cs51.project.roadtrip.algorithms;

import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class BruteForceAlgorithm implements IAlgorithm {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(BruteForceAlgorithm.class);

    Result result = null;


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
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nodes.size(); i++) {
                sb.append(nodes.get(i).getName());
                sb.append(" -> ");
            }
            System.out.println(sb.toString());
        }
    }

    @Override
    public Result execute(IGraph graph) {

        result = new Result();

        permute(graph.getListOfNodes(), 0);


        return result;
    }

    @Override
    public void reset() {
        result = null;
    }
}
