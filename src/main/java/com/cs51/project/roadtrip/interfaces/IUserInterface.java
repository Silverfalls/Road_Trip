package com.cs51.project.roadtrip.interfaces;

import com.cs51.project.roadtrip.common.dto.Result;

import java.util.List;

/**
 * Created by Alexander on 14.04.2015.
 */
public interface IUserInterface {
    //TODO: implement the real UI Interface
    public void displayOptions();
    public void getUserInput();
    public void runComparison();
    public void exitProgram();
    public void displayComparisonResults(List<Result> results);

    //TODO Javadocs on all of these (but this one in particular is here for future GUIs)
    public void updateComparisonResults();
    public void execute();
    public void initialize();
    public void showDistanceMatrix(IGraph graph);
}
