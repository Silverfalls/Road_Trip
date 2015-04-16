package com.cs51.project.roadtrip.uis;

import com.cs51.project.roadtrip.common.Result;
import com.cs51.project.roadtrip.common.RoadTripConstants;
import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.Algorithm;
import com.cs51.project.roadtrip.interfaces.Graph;
import com.cs51.project.roadtrip.interfaces.UI;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Alexander on 16.04.2015.
 */
public class CommandLineUI implements UI {

    private static final String WAITING_INDICATOR = "...";
    private static final String WELCOME_MESSAGE = "Welcome to Road Trip";

    //TODO somewhere in this flow we need to squeeze in the option to print hte distance matrix
    //UI flow
    //welcomes user
    //provides options and waits for input
    //options:
        //help - has some descriptions of how to do things
        //run basic comparision
            //ask for number of nodes in graph
                //say user enters 5, then we create a graph of 5 nodes
            //ask what algorithms to run on (any one, any combo, or all)
                //we can vet out how this is displayed, but then we can create new algorithm objects from UI based on selection
            //prompts user to press enter to run or something
                //create new BasicComparisonService object and pass it the graph we just created and the list of algorithms
            //comparison runs
            //results are displayed... go back to top
        //run race comparison (similar to basic, will implement this if time)
            //ask for number of nodes in graph
                //say user enters 5, then we create a graph of 5 nodes
            //ask what algorithms to run on (any one, any combo, or all)
                //we can vet out how this is displayed, but then we can create new algorithm objecs from UI based on selection
            //prompts user to press enter to run or something
                //create new RaceComparisonService object and pass it the graph we just created and the list of algorithms
            //comparison runs
            //results are displayed... go back to top
        //exit



    @Override
    public void displayOptions() {
        //TODO: this needs some work
        System.out.println("Possible commandline inputs:\n" +
                "compare : compares all algorithms\n" +
                "results : show latest results of the comparison\n" +
                "options : shows all possible commandline inputs\n" +
                "exit : closes the programm");
    }

    @Override
    public void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            String command = scanner.next();

            //TODO consider putting these options in an Enum
            switch (command) {
                case "exit":
                    exitProgram();
                case "compare":
                    runComparison();
                case "options":
                    displayOptions();
                case "results":
//                    displayComparisonResults();
            }
        }
    }

    @Override
    public void runComparison() {
        ListGraph listGraph = new ListGraph();
        listGraph.initGraph(5);
        Node n1 = listGraph.getNodeById(0);

    }

    @Override
    public void exitProgram() {
        System.exit(0);
    }

    @Override
    public void displayComparisonResults(List<Result> results) {

    }

    @Override
    public void updateComparisonResults() {

    }

    public CommandLineUI() {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void showDistanceMatrix(Graph graph) {

    }

    @Override
    public void execute() {
        System.out.println("Welcome to " + RoadTripConstants.PROGRAM_NAME + "\n" +
                "If you need help with the possible commands enter options\n" +
                "If you want to exit the programm please enter exit ");
        getUserInput();
    }
}
