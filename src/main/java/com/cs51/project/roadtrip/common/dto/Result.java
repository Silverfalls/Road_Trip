package com.cs51.project.roadtrip.common.dto;

import com.cs51.project.roadtrip.graphs.Node;

import java.math.BigDecimal;
import java.util.List;

/**
 * POJO to store information about algorithm execution metrics
 */
public class Result {
    private long runningTime;
    private long iterations;
    private String name;
    private int graphSize;
    private boolean finished;
    private List<Node> calculatedPath;
    private BigDecimal calculatedDistance;
    private Integer numIncorrectSolutions;
    private BigDecimal averageDeviation;

    public BigDecimal getAverageDeviation() {
        return averageDeviation;
    }

    public void setAverageDeviation(BigDecimal averageDeviation) {
        this.averageDeviation = averageDeviation;
    }

    public Integer getNumIncorrectSolutions() {
        return numIncorrectSolutions;
    }

    public void setNumIncorrectSolutions(Integer numIncorrectSolutions) {
        this.numIncorrectSolutions = numIncorrectSolutions;
    }

    public BigDecimal getCalculatedDistance() {
        return calculatedDistance;
    }

    public void setCalculatedDistance(BigDecimal calculatedDistance) {
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

    public long getIterations() {
        return iterations;
    }

    public void setIterations(long iterations) {
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

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
