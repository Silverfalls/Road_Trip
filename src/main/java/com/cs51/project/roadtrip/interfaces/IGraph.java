package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.graphs.Node;

import java.util.List;
import java.util.TreeMap;

//TODO fill in javadocs

public interface IGraph {

    /**
     * Get a list of all nodes in the graph
     * @return list of nodes
     */
    public List<Node> getListOfNodes();

    /**
     * Get a node by it's ID
     * @param id ID of the node
     * @return the node
     */
    public Node getNodeById(long id);

    /**
     * Get a node by it's name
     * @param name name of the node
     * @return the node
     */
    public Node getNodeByName(String name);

    /**
     * Get a random neighbor of the node you pass in
     * @param n1 the node
     * @return the random neighbor
     */
    public Node getRandomNeighbor(Node n1);

    /**
     * Get a random neighbor of the node you pass in that is unvisited
     * @param n1 the node
     * @return the random neighbor
     */
    public Node getRandomUnvisitedNeighbor(Node n1);

    /**
     * Get the closest neighbor from the node you pass in
     * @param n1 the node
     * @return the closest neighbor
     */
    public Node getClosestNeighbor(Node n1);

    /**
     * Get the closest neighbor from the node you pass in that is unvisited
     * @param n1 the node
     * @return the closest neighbor
     */
    public Node getClosestUnvisitedNeighbor(Node n1);

    /**
     * Get the neighbor that is the furthest away from the node you pass in
     * @param n1 the node
     * @return the furthest neighbor
     */
    public Node getFurthestNeighbor(Node n1);

    /**
     * Get the neighbor that is the furthest away from the node you pass in that is unvisited
     * @param n1 the node
     * @return the furthest unvisited neighbor
     */
    public Node getFurthestUnvisitedNeighbor(Node n1);

    /**
     * Get the distance between two nodes
     * @param n1 node 1
     * @param n2 node 2
     * @return distance between node 1 and node 2
     */
    public double getDistance(Node n1, Node n2);

    /**
     * get the starting node
     * @return starting node
     */
    public Node getStartingNode();

    /**
     * get the distance matrix of this graph
     * @return the distance matrix
     */
    public TreeMap<Node, TreeMap<Node, Double>> getDistanceMatrix();

    /**
     * Get all neighbors to a node
     * @param node the node
     * @return all neighbors to the node
     */
    public List<Node> getAllNeighbors(Node node);

    /**
     * Get the size of the graph which means the number of nodes in it
     * @return graph size
     */
    public int getGraphSize();

    /**
     * Make a copy of an existing Graph
     * @return the copy of the graph
     */
    public IGraph clone();

}
