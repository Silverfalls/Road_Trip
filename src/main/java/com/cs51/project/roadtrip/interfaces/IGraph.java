package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.graphs.Node;

import java.util.List;
import java.util.TreeMap;

//TODO fill in javadocs

public interface IGraph {

    /**
     *
     * @return
     */
    public List<Node> getListOfNodes();

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
    public Node getRandomUnvisitedNeighbor(Node n1);

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
    public Node getClosestUnvisitedNeighbor(Node n1);

    /**
     *
     * @param n1
     * @return
     */
    public Node getFurthestNeighbor(Node n1);

    /**
     *
     * @param n1
     * @return
     */
    public Node getFurthestUnvisitedNeighbor(Node n1);

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
    public Node getStartingNode();

    /**
     *
     * @return
     */
    public TreeMap<Node, TreeMap<Node, Double>> getDistanceMatrix();

}
