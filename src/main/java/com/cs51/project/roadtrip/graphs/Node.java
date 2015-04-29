package com.cs51.project.roadtrip.graphs;

/**
 * Node POJO used to hold information about 'cities' in the traveling salesman problem
 */
public class Node implements Comparable {
    private final long id;
    private final int xCoord;
    private final int yCoord;
    private final String name;
    private boolean isStartingNode;
    private boolean visited;

    //Added this to initialize a Node
    public Node(long id, int xCoord, int yCoord, String name, boolean isStartingNode) {
        this.id = id;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.name = name;
        this.isStartingNode = isStartingNode;
        visited = false;
    }

    //Added this to initialize a Node
    public Node(long id, int xCoord, int yCoord, String name) {
        this(id, xCoord, yCoord, name, false);
    }

    public Node getClone() {
        Node newNode = new Node (id, xCoord, yCoord, name, isStartingNode);
        newNode.setVisited(visited);
        return newNode;
    }

    public long getId() {
        return id;
    }


    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public String getName() {
        return name;
    }

    public boolean isStartingNode() {
        return isStartingNode;
    }

    public void setIsStartingNode(boolean isStartingNode) {
        this.isStartingNode = isStartingNode;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((Node) o).getName());
    }
}
