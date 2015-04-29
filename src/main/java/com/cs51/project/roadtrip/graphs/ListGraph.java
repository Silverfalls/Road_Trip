package com.cs51.project.roadtrip.graphs;


import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A very simple implementation of the IGraph interface using a standard List
 */
public class ListGraph implements IGraph {

    /**
     * Logger Instance
     */
    private static final Logger logger = Logger.getLogger(ListGraph.class);

    private static final String NODE_NAME_PREFIX = "N";
    private static final int MAX_PLANE_COORD = 100;
    private static final int DISTANCE_NUM_DECIMALS = 2;

    private List<Node> nodes;
    private Random random;
    private TreeMap<Node, TreeMap<Node, BigDecimal>> distanceMatrix;


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

    private void initGraph(int numNodes) {

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

    private TreeMap<Node, TreeMap<Node, BigDecimal>> generateDistanceMatrix() {

        TreeMap<Node, TreeMap<Node, BigDecimal>> matrix = new TreeMap<>();

        for (Node thisNode : nodes) {
            TreeMap<Node, BigDecimal> thisMap = new TreeMap<>();

            for (Node thatNode : nodes) {
                if (thisNode == thatNode) {
                    continue;
                }

                if (matrix.get(thatNode) != null && matrix.get(thatNode).get(thisNode) != null) {
                    thisMap.put(thatNode, matrix.get(thatNode).get(thisNode));
                    continue;
                }
                thisMap.put(thatNode, calcDistance(thisNode, thatNode));
            }
            matrix.put(thisNode, thisMap);
        }
        return matrix;
    }

    private Coordinate getRandomCoordinate() {
        return new Coordinate(getRandomPoint(), getRandomPoint());
    }

    private int getRandomPoint() {
        return random.nextInt(MAX_PLANE_COORD + 1);
    }

    private BigDecimal calcDistance(Node n1, Node n2) {
        int xDiff = n2.getxCoord() - n1.getxCoord();
        int yDiff = n2.getyCoord() - n1.getyCoord();

        return round(Math.sqrt(Math.pow(xDiff,2) + Math.pow(yDiff,2)), DISTANCE_NUM_DECIMALS);
    }

    private BigDecimal round(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd;
    }

    @Override
    public IGraph getClone(){
        List<Node> newNodes = new ArrayList<>();
        //we need a copy of all nodes
        nodes.stream().forEach(n -> newNodes.add(n.getClone()));

        return new ListGraph(newNodes);
    }

    @Override
    public List<Node> getListOfNodes() {
        //Changed this to create a new ArrayList so we can't change the actual graph when changing this list
        return new ArrayList<>(nodes);
    }

    @Override
    public BigDecimal getDistance(Node n1, Node n2) {
        return distanceMatrix.get(n1).get(n2);
    }

    @Override
    public TreeMap<Node, TreeMap<Node, BigDecimal>> getDistanceMatrix() {
        return distanceMatrix;
    }

    @Override
    public Node getStartingNode() {
        return nodes.stream()
                    .filter(Node::isStartingNode)
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
        if (n1 == null) {
            return null;
        } else if (this.getNodeById(n1.getId()) == null) {
            logger.error("getRandomUnvisitedNeighbor | the passed in node is not part of the graph");
            return null;
        }
        return nodes.stream()
                    .filter(n -> n != n1 && !n.isVisited())
                    .findAny()
                    .orElse(null);
    }

    @Override
    public Node getClosestUnvisitedNeighbor(Node n1) {
        if (n1 == null || distanceMatrix.get(n1) == null) {
            return null;
        }

        TreeMap<Node, BigDecimal> thisMap = distanceMatrix.get(n1);

        //get the closest unvisited neighbor
        Map.Entry<Node, BigDecimal> closestEntry = thisMap.entrySet()
                .stream()
                .reduce((minDist, dist) -> (dist.getValue().compareTo(minDist.getValue()) == -1 && !dist.getKey().isVisited())
                        || minDist.getKey().isVisited() ? dist : minDist)
                .orElse(null);

        //return the Node key
        return (closestEntry.getKey().isVisited()) ? null : closestEntry.getKey();
    }

    @Override
    public Node getFurthestUnvisitedNeighbor(Node n1) {
        if (n1 == null || distanceMatrix.get(n1) == null) {
            return null;
        }

        TreeMap<Node, BigDecimal> thisMap = distanceMatrix.get(n1);

        Map.Entry<Node, BigDecimal> furthestEntry = thisMap.entrySet()
                .stream()
                .reduce((minDist,dist) -> dist.getValue().compareTo(minDist.getValue()) == 1 && !dist.getKey().isVisited() ? dist : minDist)
                .orElse(null);

        return (furthestEntry.getKey().isVisited()) ? null : furthestEntry.getKey();
    }

    @Override
    public Node getClosestNeighbor(Node n1) {
        if (n1 == null || distanceMatrix.get(n1) == null) {
            return null;
        }

        TreeMap<Node, BigDecimal> thisMap = distanceMatrix.get(n1);

        Map.Entry<Node, BigDecimal> closestEntry = thisMap.entrySet()
                .stream()
                .reduce((minDist, dist) -> dist.getValue().compareTo(minDist.getValue()) == -1 ? dist : minDist)
                .orElse(null);

        Node result = null;

        if (closestEntry != null){
            result = closestEntry.getKey();
        }

        return result;
    }

    @Override
    public Node getFurthestNeighbor(Node n1) {
        if (n1 == null || distanceMatrix.get(n1) == null) {
            return null;
        }

        TreeMap<Node, BigDecimal> thisMap = distanceMatrix.get(n1);

        Map.Entry<Node, BigDecimal> furthestEntry = thisMap.entrySet()
                .stream()
                .reduce((minDist, dist) -> dist.getValue().compareTo(minDist.getValue())  == 1  ? dist : minDist)
                .orElse(null);

        Node result = null;

        if (furthestEntry != null){
            result = furthestEntry.getKey();
        }

        return result;
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
        return nodes.stream().filter(n -> n != node).collect(Collectors.toList());
    }

    @Override
    public int getGraphSize() {
        return nodes.size();
    }

    private class Coordinate {
        private final int x;
        private final int y;

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
