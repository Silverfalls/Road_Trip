package com.cs51.project.roadtrip.common.dto;

import com.cs51.project.roadtrip.graphs.Node;

import java.util.List;
import java.util.Optional;


public class Result {
    private boolean isOptimal;
    private long runningTime;
    private Optional<Long> iterations;
    private String name;
    private int graphSize;
    private boolean finished;
    private List<Node> calculatedPath;
    //Maybe we should use BigDecimal here
    private double calculatedDistance;

    public double getCalculatedDistance() {
        return calculatedDistance;
    }

    public void setCalculatedDistance(double calculatedDistance) {
        this.calculatedDistance = calculatedDistance;
    }
    
    public List<Node> getCalculatedPath() {return calculatedPath;}

    public void setCalculatedPath(List<Node> calculatedPath) {this.calculatedPath = calculatedPath;}

    public long getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(long runningTime) {
        this.runningTime = runningTime;
    }

    public Optional<Long> getIterations() {
        return iterations;
    }

    public void setIterations(Optional<Long> iterations) {
        this.iterations = iterations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGraphSize() {
        return graphSize;
    }

    public void setGraphSize(int graphSize) {
        this.graphSize = graphSize;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Optional<Boolean> isOptimal() {
        return Optional.ofNullable(isOptimal);
    }

    public void setOptimal(boolean isOptimal) {
        this.isOptimal = isOptimal;
    }

}
