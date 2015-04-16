package com.cs51.project.roadtrip;

/**
 * Created by Alexander on 14.04.2015.
 */
public interface Graph {
    //TODO: implement the real Graph Interface
    void initGraph();
    Node getRandomNeighbor(Node n1);
    double getDistance(Node n1, Node n2);
    Node getNodeById(long id);
    Node getNodeByName(String name);

}
