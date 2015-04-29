package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.graphs.Node;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

/**
 * All graphs should implement this interface
 */
public interface IGraph {

    /**
     * Get a list of all nodes in the graph
     * @return list of nodes
     */
    List<Node> getListOfNodes();

    /**
     * Get a node by its ID
     * @param id ID of the node
     * @return the node
     */
    Node getNodeById(long id);

    /**
     * Get a node by its name
     * @param name name of the node
     * @return the node
     */
    Node getNodeByName(String name);

    /**
     * Get a random neighbor of the node you pass in
     * @param n1 the node
     * @return the random neighbor
     */
    @SuppressWarnings("unused")
    Node getRandomNeighbor(Node n1);

    /**
     * Get a random neighbor of the node you pass in that is unvisited
     * @param n1 the node
     * @return the random neighbor
     */
    @SuppressWarnings("unused")
    Node getRandomUnvisitedNeighbor(Node n1);

    /**
     * Get the closest neighbor from the node you pass in
     * @param n1 the node
     * @return the closest neighbor
     */
    Node getClosestNeighbor(Node n1);

    /**
     * Get the closest neighbor from the node you pass in that is unvisited
     * @param n1 the node
     * @return the closest neighbor
     */
    Node getClosestUnvisitedNeighbor(Node n1);

    /**
     * Get the neighbor that is the furthest away from the node you pass in
     * @param n1 the node
     * @return the furthest neighbor
     */
    Node getFurthestNeighbor(Node n1);

    /**
     * Get the neighbor that is the furthest away from the node you pass in that is unvisited
     * @param n1 the node
     * @return the furthest unvisited neighbor
     */
    Node getFurthestUnvisitedNeighbor(Node n1);

    /**
     * Get the distance between two nodes
     * @param n1 node 1
     * @param n2 node 2
     * @return distance between node 1 and node 2
     */
    BigDecimal getDistance(Node n1, Node n2);

    /**
     * get the starting node
     * @return starting node
     */
    Node getStartingNode();

    /**
     * get the distance matrix of this graph
     * @return the distance matrix
     */
    TreeMap<Node, TreeMap<Node, BigDecimal>> getDistanceMatrix();

    /**
     * Get all neighbors to a node
     * @param node the node
     * @return all neighbors to the node
     */
    List<Node> getAllNeighbors(Node node);

    /**
     * Get the size of the graph which means the number of nodes in it
     * @return graph size
     */
    int getGraphSize();

    /**
     * Make a copy of an existing Graph
     * @return the copy of the graph
     */
    IGraph getClone();

}
