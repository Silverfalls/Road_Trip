package com.cs51.project.roadtrip.graphs;


import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A very simple and dumb implementation using a standard List
 */
public class ListGraph implements IGraph {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(ListGraph.class);

    //TODO something to keep in mind... we might be able to reuse most of the code for get closest neighbor and getfurthest neighbor and
    //TODO use some lambdas... they're pretty much the same except one uses > and the other < and an isVisitedCheck... we should talk about these...
    //TODO i think there's a lot of room for improvement
    //TODO please ignore the repeated code for now... we can fix that
    //TODO i read that there is a reduce function in Java 8 now.. maybe we can use that for closest and furthest

    private static final String NODE_NAME_PREFIX = "N";
    private static final int MAX_PLANE_COORD = 100;
    private static final int DISTANCE_NUM_DECIMALS = 2;

    private List<Node> nodes;
    private Random random;
    private TreeMap<Node, TreeMap<Node, Double>> distanceMatrix;

    /**
     * Constructor for testing primarily.  Creates a graph using a passed in list of Node objects.
     * @param nodes  a list of nodes to construct the graph with
     */
    public ListGraph(List<Node> nodes) {
        this.nodes = nodes;
        distanceMatrix = generateDistanceMatrix();
    }

    /**
     * Standard constructor to create a Graph with numNodes random nodes.
     * @param numNodes number of Nodes in the Graph
     */
    public ListGraph(int numNodes) {
        initGraph(numNodes);
    }

    /**
     * Clones the current Graph
     * @return IGraph the cloned Graph
     */
    public IGraph clone(){
        List<Node> newNodes = new ArrayList<>();
        //we need a copy of all nodes
        nodes.stream().forEach(n -> newNodes.add(n.clone()));

        return new ListGraph(newNodes);
    }

    public void initGraph(int numNodes) {

        nodes = new ArrayList<>(numNodes);
        random = new Random();

        List<Coordinate> coordsUsed = new ArrayList<>(numNodes);

        for (int i = 0; i < numNodes; i++) {

            Coordinate coord;

            //satisfy our invariant that no two nodes can have the same coordinate
            do {
                coord = getRandomCoordinate();
            } while (coordsUsed.contains(coord));

            coordsUsed.add(coord);

            Node newNode = new Node(i, coord.getX(), coord.getY(), (NODE_NAME_PREFIX + i));

            nodes.add(newNode);

            if (i == 0) {
                newNode.setIsStartingNode(true);
            }
        }

        distanceMatrix = generateDistanceMatrix();
    }



    private TreeMap<Node, TreeMap<Node, Double>> generateDistanceMatrix() {

        //TODO we probably need to revisit this... maybe we can use Java 8 map lambda

        TreeMap<Node, TreeMap<Node, Double>> matrix = new TreeMap<Node, TreeMap<Node, Double>>();

        long start = System.currentTimeMillis();
        for (Node thisNode : nodes) {
            TreeMap<Node, Double> thisMap = new TreeMap<>();

            for (Node thatNode : nodes) {
                if (thisNode == thatNode) {
                    continue;
                }

                /*tested this and at least on my machine this is actually quicker but it's only measurable with a
                * few hundred nodes
                */
                if (matrix.get(thatNode) != null && matrix.get(thatNode).get(thisNode) != null) {
                    thisMap.put(thatNode, matrix.get(thatNode).get(thisNode));
                    continue;
                }
                thisMap.put(thatNode, calcDistance(thisNode, thatNode));
            }
            matrix.put(thisNode, thisMap);
        }
        System.out.println("time spend :" + (System.currentTimeMillis() - start));
        return matrix;
    }

    private Coordinate getRandomCoordinate() {
        return new Coordinate(getRandomPoint(), getRandomPoint());
    }

    private int getRandomPoint() {
        return random.nextInt(MAX_PLANE_COORD + 1);
    }

    private double calcDistance(Node n1, Node n2) {
        int xDiff = n2.getxCoord() - n1.getxCoord();
        int yDiff = n2.getyCoord() - n1.getyCoord();

        return round(Math.sqrt(Math.pow(xDiff,2) + Math.pow(yDiff,2)), DISTANCE_NUM_DECIMALS);
    }

    //stole this off of stack overflow
    private double round(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    //Public interface methods below TODO link to Interface javadocs for each of these methods

    @Override
    public List<Node> getListOfNodes() {
        //Changed this to create a new ArrayList so we can't change the actual graph when changing this list
        return new ArrayList<Node>(nodes);
    }

    @Override
    public double getDistance(Node n1, Node n2) {
        return distanceMatrix.get(n1).get(n2);
    }

    @Override
    public TreeMap<Node, TreeMap<Node, Double>> getDistanceMatrix() {
        return distanceMatrix;
    }

    @Override
    public Node getStartingNode() {
        return nodes.stream()
                    .filter(n -> n.isStartingNode())
                    .findAny()
                    .orElse(null);
    }

    @Override
    public Node getRandomNeighbor(Node n1) {
        if (n1 == null) {
            return null;
        }
        return nodes.stream()
                    .filter(n -> n != n1)
                    .findAny()
                    .orElse(null);
    }

    @Override
    public Node getRandomUnvisitedNeighbor(Node n1) {
        //TODO this applies to all of these get methods... how do we ensure the node is part of the graph???
        //TODO  we could use a check like  this.getNodeById(n1.getId()) == null
        //TODO but if we do that, should we be throwing exceptions ???
        if (n1 == null) {
            return null;
        }
        return nodes.stream()
                    .filter(n -> n != n1 && !n.isVisited())
                    .findAny()
                    .orElse(null);
    }

    @Override
    public Node getClosestUnvisitedNeighbor(Node n1) {
        //TODO for the 2nd check, maybe we should split that off and throw an exception ???
        if (n1 == null || distanceMatrix.get(n1) == null) {
            return null;
        }

        TreeMap<Node, Double> thisMap = distanceMatrix.get(n1);
        Node n2 = null;
        double minDist = 0;

        for (Node thatN : thisMap.keySet()) {
            double thisDist = thisMap.get(thatN);
            if (n2 == null && !thatN.isVisited()) {
                n2 = thatN;
                minDist = thisDist;
                continue;
            }
            if (thisDist < minDist && !thatN.isVisited()) {
                n2 = thatN;
                minDist = thisDist;
            }
        }

        return n2;
    }

    @Override
    public Node getFurthestUnvisitedNeighbor(Node n1) {
        //TODO for the 2nd check, maybe we should split that off and throw an exception ???
        if (n1 == null || distanceMatrix.get(n1) == null) {
            return null;
        }

        TreeMap<Node, Double> thisMap = distanceMatrix.get(n1);
        Node n2 = null;
        double maxDist = 0;

        for (Node thatN : thisMap.keySet()) {
            double thisDist = thisMap.get(thatN);
            if (n2 == null && !thatN.isVisited()) {
                n2 = thatN;
                maxDist = thisDist;
                continue;
            }
            if (thisDist > maxDist && !thatN.isVisited()) {
                n2 = thatN;
                maxDist = thisDist;
            }
        }

        return n2;
    }

    @Override
    public Node getClosestNeighbor(Node n1) {
        //TODO for the 2nd check, maybe we should split that off and throw an exception ???
        if (n1 == null || distanceMatrix.get(n1) == null) {
            return null;
        }

        TreeMap<Node, Double> thisMap = distanceMatrix.get(n1);
        Node n2 = null;
        double minDist = 0;

        for (Node thatN : thisMap.keySet()) {
            double thisDist = thisMap.get(thatN);
            if (n2 == null) {
                n2 = thatN;
                minDist = thisDist;
                continue;
            }
            if (thisDist < minDist) {
                n2 = thatN;
                minDist = thisDist;
            }
        }

        return n2;
    }

    @Override
    public Node getFurthestNeighbor(Node n1) {
        //TODO for the 2nd check, maybe we should split that off and throw an exception ???
        if (n1 == null || distanceMatrix.get(n1) == null) {
            return null;
        }

        TreeMap<Node, Double> thisMap = distanceMatrix.get(n1);
        Node n2 = null;
        double maxDist = 0;

        for (Node thatN : thisMap.keySet()) {
            double thisDist = thisMap.get(thatN);
            if (n2 == null) {
                n2 = thatN;
                maxDist = thisDist;
                continue;
            }
            if (thisDist > maxDist) {
                n2 = thatN;
                maxDist = thisDist;
            }
        }

        return n2;
    }

    @Override
    public Node getNodeById(long id) {
        return nodes.stream()
                    .filter(n -> n.getId() == id)
                    .findAny()
                    .orElse(null);
    }

    @Override
    public Node getNodeByName(String name) {
        if (name == null) {
            return null;
        }
        return nodes.stream()
                    .filter(n -> name.equals(n.getName()))
                    .findAny()
                    .orElse(null);
    }

    @Override
    public List<Node> getAllNeighbors(Node node) {
        List<Node> neighbors = nodes.stream()
                                    .filter(n -> n!= node)
                                    .collect(Collectors.toList());
        return neighbors;
    }

    @Override
    public int getGraphSize() {
        return nodes.size();
    }

    private class Coordinate {
        private int x;
        private int y;

        private Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private int getX() {
            return x;
        }

        private int getY() {
            return y;
        }
    }

}
