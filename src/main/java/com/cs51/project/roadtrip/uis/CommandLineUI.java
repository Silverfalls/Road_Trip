package com.cs51.project.roadtrip.uis;

import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.common.constants.RoadTripConstants;
import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IGraph;
import com.cs51.project.roadtrip.interfaces.IUserInterface;

import java.util.List;
import java.util.Scanner;


public class CommandLineUI implements IUserInterface {

    private static final String WAITING_INDICATOR = "...";

    //TODO somewhere in this flow we need to squeeze in the option to print hte distance matrix
    //UI flow
    //welcomes user
    //provides options and waits for input
    //options:
        //help - has some descriptions of how to do things
        //run basic comparison
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



    public void displayOptions() {
        //TODO: this needs some work
//        System.out.println("Possible commandline inputs:\n" +
//                "compare : compares all algorithms\n" +
//                "results : show latest results of the comparison\n" +
//                "options : shows all possible commandline inputs\n" +
//                "exit : closes the programm");
        System.out.println("Commands:\n");
        System.out.println("'B' : Basic Comparison of Algorithms");
        System.out.println("'O' : Print these options");
        System.out.println("'H' : Help");
        System.out.println("'Q' : Quit");
    }


    public void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            String command = scanner.next();

            //TODO consider putting these options in an Enum
            switch (command.toUpperCase()) {
                case "Q":
                    exitProgram();
                case "B":
                    runComparison();
                case "O":
                    displayOptions();
//                case "D":
//                    displayComparisonResults();
            }
        }
    }

    public void runComparison() {
//        ListGraph listGraph = new ListGraph(5);
//        listGraph.initGraph(5);
//        Node n1 = listGraph.getNodeById(0);

    }

    public void exitProgram() {
        System.exit(0);
    }

    public void displayComparisonResults(List<Result> results) {

    }

//    @Override
//    public void updateComparisonResults() {
//
//    }


//    @Override
//    public void initialize() {
//
//    }

//    @Override
//    public void showDistanceMatrix(IGraph graph) {
//
//    }

    @Override
    public void execute() {
        //TODO any set up to do before starting ?... if so, should it just go in the main?
        System.out.println("################################\n");
        System.out.println(RoadTripConstants.PROGRAM_NAME + "\n");
        System.out.println("################################\n");

//        System.out.println("Welcome to " + RoadTripConstants.PROGRAM_NAME + "\n" +
//                "If you need help with the possible commands enter options\n" +
//                "If you want to exit the programm please enter exit ");
        displayOptions();
        getUserInput();
    }
}
