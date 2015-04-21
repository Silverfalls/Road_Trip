package com.cs51.project.roadtrip.uis;

import com.cs51.project.roadtrip.algorithms.BruteForceAlgorithm;
import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.common.constants.RoadTripConstants;
import com.cs51.project.roadtrip.enums.CompType;
import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IComparisonService;
import com.cs51.project.roadtrip.interfaces.IGraph;
import com.cs51.project.roadtrip.interfaces.IUserInterface;
import com.cs51.project.roadtrip.services.BasicComparisonService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Stream;


public class CommandLineUI implements IUserInterface {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(CommandLineUI.class);

    private static final String HELP_OPTION_CHAR = "H";
    private static final String QUIT_OPTION_CHAR = "Q";
    private static final String WAITING_INDICATOR = "...";

    @Override
    public void execute() {

        showWelcomeScreen();
        displayOptions();
        getUserInput();
    }


    private void displayOptions() {

        System.out.println("\nCommands:\n");

        //loop through each comparison type and print it as an option
        for (CompType compType : CompType.values()) {
            System.out.println(compType.getOptionChar() + " : " + compType.getName());
        }

        System.out.println(HELP_OPTION_CHAR + " : Help");
        System.out.println(QUIT_OPTION_CHAR + " : Quit");
    }


    private void getUserInput() {

        Scanner scanner = new Scanner(System.in);
        while (true){
            String command = scanner.next();
            String commandUpper = command.toUpperCase();

            boolean validInput = false;
            if (HELP_OPTION_CHAR.equals(commandUpper)) {
                validInput = true;
                showHelp();
            } else if (QUIT_OPTION_CHAR.equals(commandUpper)) {
                validInput = true;
                exitProgram();
            } else {
                for (CompType compType : CompType.values()) {
                    if (compType.getOptionChar().toUpperCase().equals(commandUpper)) {
                        validInput = true;
                        runComparison(compType.getService());
                        break;
                    }
                }
            }
            if (!validInput) {
                displayInvalidInputWarning(command);
            }
            displayOptions();
        }
    }

    private void displayInvalidInputWarning(String command) {
        System.out.println("Invalid input: " + command + "\n");
    }

    private void runComparison(IComparisonService compService) {

        //get the number of nodes for the graph from the user and create the graph
        int numNodes = promptNumNodes();
        IGraph graph = new ListGraph(numNodes);

        printDistanceMatrix(graph);

        //get the algorithms the user wants to run the comparison with
//        List<IAlgorithm> algs = promptAlgs();

//        try {
//            //execute the comparison
//            compService.executeComparison(graph, algs);
//        } catch (Exception e) {
//            //TODO parameterize these statements
//            System.out.println("There was an error running the comparison. Please see the logs for more details");
//            logger.error("runComparison | error executing comparison",e);
//        }


    }

    private int promptNumNodes() {
        return 8;
    }

    private List<IAlgorithm> promptAlgs() {
        List<IAlgorithm> algs = new ArrayList<>();
        algs.add(new BruteForceAlgorithm());
        return algs;
    }

    //TODO UGH this method is ugly
    private void printDistanceMatrix(IGraph graph) {

        //get the distance matrix from the graph
        TreeMap<Node, TreeMap<Node, Double>> dm = graph.getDistanceMatrix();

        //define our formatting strings
        String leftColumnFormat = "%-4s";
        String otherColumnsFormat = "%9s";
        String distanceFormat = "%9.2f";


        //print the top row column headers
        int i = 0;
        StringBuilder sb1 = new StringBuilder();
        sb1.append(String.format(leftColumnFormat,""));
        for (Node node : dm.keySet()) {
            if (i + 1 == dm.keySet().size()) {
                break;
            }
            sb1.append(String.format(otherColumnsFormat,node.getName()));
            i++;
        }
        System.out.println(sb1.toString());

        //print each row, we should not duplicate distances, so a row stops when it encounters itself
        int j = 0;
        for (Node thisNode : dm.keySet()) {
            if (j == 0) {
                j++;
                continue;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(String.format(leftColumnFormat,thisNode.getName()));
            int k = 0;
            for (Node thatNode : dm.get(thisNode).keySet()) {
                if (k == j) {
                    break;
                }
                Double distance = dm.get(thisNode).get(thatNode);
                sb2.append(String.format(distanceFormat, distance));
                k++;
            }
            System.out.println(sb2.toString());
            j++;
        }
    }

    //Reads the help file's text and displays it for the user
    private void showHelp() {

        try {
            Path path = Paths.get(RoadTripConstants.PATH_TO_HELP_FILE);
            Files.lines(path).forEach(System.out::println);
        } catch (IOException eio) {
            System.out.println("Could not read the help file. Please check the logs for more info");
            logger.error("showHelp | error reading help file",eio);
        }
    }

    private void exitProgram() {

        showClosingScreen();
        System.exit(0);
    }

    private void showWelcomeScreen() {

        System.out.println("################################\n");
        System.out.println("Welcome to " + RoadTripConstants.PROGRAM_NAME + "\n");
        System.out.println("################################\n");
    }

    private void showClosingScreen() {

        System.out.println("################################\n");
        System.out.println("Thank you for using " + RoadTripConstants.PROGRAM_NAME);
        System.out.println("Goodbye...\n");
        System.out.println("################################\n");
    }

}
