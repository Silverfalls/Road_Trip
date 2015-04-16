package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.graphs.Node;

import java.util.TreeMap;

/**
 * Created by Alexander on 14.04.2015.
 */
public interface IGraph {
    //TODO: implement the real Graph Interface
    void initGraph(int numNodes);
    Node getRandomNeighbor(Node n1);
    double getDistance(Node n1, Node n2);
    Node getNodeById(long id);
    Node getNodeByName(String name);
    TreeMap<String, double[]> getDistanceMatrix();

}
