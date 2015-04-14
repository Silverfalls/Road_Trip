package com.cs51.project.roadtrip;

/**
 * Created by Alexander on 14.04.2015.
 */
public class Node {
    private long id;
    private int xCoord;
    private int yCoord;
    private String name;
    private boolean isStartingNode;
    private boolean visited;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
