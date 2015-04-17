package com.cs51.project.roadtrip.common.dto;

import java.util.Optional;


public class Result {
    private Optional<Boolean> isOptimal;
    private long runningTime;
    private Optional<Long> iterations;
    private String name;
    private int graphSize;
    private boolean finished;


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
        return isOptimal;
    }

    public void setOptimal(Optional<Boolean> isOptimal) {
        this.isOptimal = isOptimal;
    }

}
