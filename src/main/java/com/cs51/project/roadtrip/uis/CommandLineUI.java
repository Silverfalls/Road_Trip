package com.cs51.project.roadtrip.uis;

import com.cs51.project.roadtrip.algorithms.BruteForceAlgorithm;
import com.cs51.project.roadtrip.common.constants.RoadTripConstants;
import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.enums.AlgType;
import com.cs51.project.roadtrip.enums.CompType;
import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import com.cs51.project.roadtrip.interfaces.IUserInterface;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class CommandLineUI implements IUserInterface {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(CommandLineUI.class);

    private static final String DECORATIVE_BORDER = "################################\n";
    private static final String HELP_OPTION_CHAR = "H";
    private static final String QUIT_OPTION_CHAR = "Q";
    private static final String WAITING_INDICATOR = "...";
    private static final String YES = "Y";
    private static final String NO = "N";
    private static final String WILDCARD = "*";

    /**
     * TODO link to interface javadoc
     */
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

        //print static options
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
                        setupComparison(compType);
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


    //TODO, needs lots of work
    private void setupComparison(CompType compType) {

        //set the node size for the graph and create the graph
        int numNodes = compType.shouldPromptNumNodes() ? promptNumNodes() : RoadTripConstants.DEFAULT_STARTING_NODES;
        IGraph graph = new ListGraph(numNodes);

        if (shouldPrintDistanceMatrix()) {
            printDistanceMatrix(graph);
        }

        List<AlgType> algTypes = getAlgorithmsToCompareFromUser();

        if (algTypes.isEmpty()) {
            //log and do something here
        } else {
            System.out.println("Commencing the " + compType.getName() + " on the following algorithm(s):");
            algTypes.stream().forEach(a -> System.out.println(a.getName()));
            if (compType.shouldPromptNumNodes()) {
                System.out.println("on a graph with " + numNodes + " nodes.");
                System.out.println(WAITING_INDICATOR);
                try {
                    List<Result> results = compType.getService().executeComparison(graph, converAlgTypeListToAlgs(algTypes));
                    if (results != null) {
                        printResults(results);
                    } else {
                        //do something
                    }
                } catch (Exception e) {
                    //do something about it
                }
            }
        }

        //get the algorithms the user wants to run the comparison with
//        List<IAlgorithm> algs = promptAlgs();

//        try {
//            //execute the comparison
//            compService.executeComparison(graph, algs);
//        } catch (Exception e) {
//            //TODO parameterize these statements
//            System.out.println("There was an error running the comparison. Please see the logs for more details");
//            logger.error("setupComparison | error executing comparison",e);
//        }


    }

    private void printResults(List<Result> results) {
        //TODO TODO TODO left off here
        System.out.println("In the print results section, left off here for the night");
    }

    private List<IAlgorithm> converAlgTypeListToAlgs(List<AlgType> algTypes) {
        if (algTypes == null || algTypes.isEmpty()) {
            //log and do something
        }
        List<IAlgorithm> algs = new ArrayList<>(algTypes.size());
        for (AlgType algType : algTypes) {
            algs.add(algType.getAlg());
        }
        return algs;
    }

    private int promptNumNodes() {
        return 8;
    }

    //ask the user if they would like to print the graph's distance matrix
    private boolean shouldPrintDistanceMatrix() {
        boolean shouldPrint = false;

        System.out.println("Would you like to print the graph's distance matrix before running the comparison?");
        System.out.println("y/n");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.next();
            if (YES.equals(command.toUpperCase())) {
                shouldPrint = true;
                break;
            } else if (NO.equals(command.toUpperCase())) {
                shouldPrint = false;
                break;
            } else {
                displayInvalidInputWarning(command);
            }
        }
        return shouldPrint;
    }

    private List<AlgType> getAlgorithmsToCompareFromUser() {
        List<AlgType> algs = new ArrayList<>();

        System.out.println("From the list below, choose the algorithms you would like to compare");
        System.out.println("enter H for help on how to pick and choose algorithms");
        System.out.println("enter * to choose all of them");


        for (AlgType algType : AlgType.values()) {
            System.out.println(algType.getOptionChar() + " : " + algType.getName() + " - " + algType.getDesc());
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {

            String command = scanner.next();
            boolean validInput = false;

            if (WILDCARD.equals(command)) {
                algs = Arrays.asList(AlgType.values());
                validInput = true;
            } else if (HELP_OPTION_CHAR.equals(command.toUpperCase())) {
                try {
                    Path path = Paths.get(RoadTripConstants.PATH_TO_ALGORITHM_CHOICE_HELP_FILE);
                    Files.lines(path).forEach(System.out::println);
                    validInput = true;
                } catch (IOException eio) {
                    System.out.println("Could not read the help file. Please check the logs for more info");
                    logger.error("getAlgorithmsToCompareFromUser | error reading help file",eio);
                }
            } else {
                String[] tokens = command.split("(?!^)");
                for (int i = 0; i < tokens.length; i++) {
                    for (AlgType algType : AlgType.values()) {
                        if (algType.getOptionChar().equals(tokens[i].toUpperCase())) {
                            if (!algs.contains(algType.getAlg())) {
                                algs.add(algType);
                            } else {
                                if (logger.isInfoEnabled()) {
                                    logger.info("getAlgorithmsToCompareFromUser | list already contains alg: "
                                        + algType.getName());
                                }
                            }
                        }
                    }
                }
                validInput = !algs.isEmpty();
            }

            if (validInput && !algs.isEmpty()) {
                break;
            } else if (!validInput) {
                displayInvalidInputWarning(command);
            }
        }

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
            Path path = Paths.get(RoadTripConstants.PATH_TO_GENERAL_HELP_FILE);
            Files.lines(path).forEach(System.out::println);
        } catch (IOException eio) {
            System.out.println("Could not read the help file. Please check the logs for more info");
            logger.error("showHelp | error reading help file",eio);
        }
    }

    private void displayInvalidInputWarning(String command) {
        System.out.println("Invalid input: " + command + "\n");
    }

    private void exitProgram() {

        showClosingScreen();
        System.exit(0);
    }

    private void showWelcomeScreen() {

        System.out.println(DECORATIVE_BORDER + "\n");
        System.out.println("Welcome to " + RoadTripConstants.PROGRAM_NAME + "\n");
        System.out.println(DECORATIVE_BORDER + "\n");
    }

    private void showClosingScreen() {

        System.out.println(DECORATIVE_BORDER + "\n");
        System.out.println("Thank you for using " + RoadTripConstants.PROGRAM_NAME);
        System.out.println("Goodbye...\n");
        System.out.println(DECORATIVE_BORDER + "\n");
    }

}
