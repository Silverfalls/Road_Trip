package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.graphs.Node;

import java.util.TreeMap;

//TODO fill in javadocs
/**
 * Created by Alexander on 14.04.2015.
 */
public interface IGraph {

    /**
     *
     * @param numNodes
     */
    public void initGraph(int numNodes);

    /**
     *
     * @param id
     * @return
     */
    public Node getNodeById(long id);

    /**
     *
     * @param name
     * @return
     */
    public Node getNodeByName(String name);

    /**
     *
     * @param n1
     * @return
     */
    public Node getRandomNeighbor(Node n1);

    /**
     *
     * @param n1
     * @return
     */
    public Node getClosestNeighbor(Node n1);

    /**
     *
     * @param n1
     * @return
     */
    public Node getFurthestNeighbor(Node n1);

    /**
     *
     * @param n1
     * @param n2
     * @return
     */
    public double getDistance(Node n1, Node n2);

    /**
     *
     * @return
     */
    public TreeMap<String, double[]> getDistanceMatrix();

}
