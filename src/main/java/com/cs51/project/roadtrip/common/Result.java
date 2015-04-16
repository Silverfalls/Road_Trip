package com.cs51.project.roadtrip.common;

import java.util.Optional;

/**
 * Created by Alexander on 14.04.2015.
 */
public class Result {
    private Optional<Boolean> isOptimal;
    private long runningTime;
    private long iterations;
    private String name;
    private int graphSize;
    private boolean finished;


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
