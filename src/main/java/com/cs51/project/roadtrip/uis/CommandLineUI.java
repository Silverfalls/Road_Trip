package com.cs51.project.roadtrip.uis;

import com.cs51.project.roadtrip.common.constants.RoadTripConstants;
import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.common.utils.RoadTripUtils;
import com.cs51.project.roadtrip.enums.AlgType;
import com.cs51.project.roadtrip.enums.CompType;
import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import com.cs51.project.roadtrip.interfaces.IUserInterface;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * UI for the RoadTrip program geared around a command line interface
 */
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
    private static final String NOT_APPLICABLE = "n/a";

    /**
     * kicks off the CommandLineUI's functionality
     */
    @Override
    public void execute() {
        if (logger.isDebugEnabled()) {
            logger.debug("execute | start...");
        }

        showWelcomeScreen();
        displayOptions();
        getUserInput();

        //should never see this line because get userInput is an infinite loop that ends when the user exits the program
        if (logger.isDebugEnabled()) {
            logger.debug("execute | end...");
        }
    }


    //displays the main options to the user
    private void displayOptions() {
        if (logger.isDebugEnabled()) {
            logger.debug("displayOptions | start...");
        }

        System.out.println("\nCommands:\n");

        //loop through each comparison type and print it as an option
        for (CompType compType : CompType.values()) {
            printOption(compType.getOptionChar(), compType.getName());
        }

        //print static options
        printOption(HELP_OPTION_CHAR, "Help");
        printOption(QUIT_OPTION_CHAR, "Quit");

        if (logger.isDebugEnabled()) {
            logger.debug("displayOptions | end...");
        }
    }

    //prints a single option
    private void printOption(String optionChar, String title) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-3s", optionChar));
        sb.append("| ");
        sb.append(title);
        System.out.println(sb.toString());
    }


    //get user input for main options
    private void getUserInput() {
        if (logger.isDebugEnabled()) {
            logger.debug("getUserInput | start...");
        }

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
                //check if command is a comparison type option
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

    //prepare our comparison to be run
    private void setupComparison(CompType compType) {
        if (logger.isDebugEnabled()) {
            logger.debug("setupComparison | start...");
        }

        //set the node size for the graph and create the graph
        int numNodes = compType.shouldPromptNumNodes() ? promptNumNodes() : RoadTripConstants.DEFAULT_STARTING_NODES;

        //set the number of iterations for the comparison if applicable
        int numIterations = compType.shouldPromptIterations() ? promptIterations() : 1;
        IGraph graph = new ListGraph(numNodes);

        //print the graph distance matrix if the user wants to
        if (shouldPrintDistanceMatrix()) {
            printDistanceMatrix(graph);
        }

        //get the algorithms to execute from the user
        List<AlgType> algTypes = getAlgorithmsToCompareFromUser();

        if (!algTypes.isEmpty()) {
            //print the status and then run the comparison
            System.out.println("\nCommencing the " + compType.getName() + " on the following algorithm(s):");
            algTypes.stream().forEach(a -> System.out.println(a.getName()));
            if (compType.shouldPromptNumNodes()) {
                System.out.println("on a graph with " + numNodes + " nodes.");
            }
            runComp(compType, graph, algTypes, numIterations);
        } else {
            logger.error("setUpComparison | algType list is empty");
            System.out.println("whoops.. something went wrong.. we have to have one or more algorithms");
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("setupComparison | end...");
        }
    }

    //run the comparison and print the results
    private void runComp(CompType compType, IGraph graph, List<AlgType> algTypes, int numIterations) {
        System.out.println("\n" + WAITING_INDICATOR + "\n");
        List<Result> results =
                compType.getService().executeComparison(graph, convertAlgTypeListToAlgs(algTypes), numIterations);
        if (results != null) {
            if (numIterations > 1) {
                System.out.println("results are averaged over " + numIterations + " runs\n");
            }
            printResults(results);
        } else {
            logger.warn("setUpComparison | results are null");
            System.out.println("the results are null.");
        }
    }

    //ask the user how many iterations of an algorithm should the comparison average results over
    private int promptIterations() {

        System.out.println("Enter the amount of runs you would like the results averaged over");

        int numIterations = 1;
        Scanner scanner = new Scanner(System.in);
        String command;
        while (true) {
            command = scanner.next();
            try {
                numIterations = Integer.parseInt(command);
                if (numIterations >= 1) {
                    break;
                }
                System.out.println("the number of iterations must be at least 1");
            } catch (Exception e) {
                displayInvalidInputWarning(command);
                continue;
            }
        }
        return numIterations;
    }

    //print the results from the comparison
    private void printResults(List<Result> results) {
        if (logger.isDebugEnabled()) {
            logger.debug("printResults | start...");
        }

        for (Result result : results) {
            System.out.println("\nResults for " + result.getName());
            System.out.println("- graph size      - " + result.getGraphSize());
            System.out.println("- running time    - " + result.getRunningTime() + "ms");
            System.out.println("- num iterations  - " + result.getIterations());
            System.out.println("- path            - " + (RoadTripUtils.convertListToPath(result.getCalculatedPath())));
            System.out.println("- distance        - " + result.getCalculatedDistance());
            System.out.println("- # incorrect     - " +
                    ((result.getNumIncorrectSolutions() != null) ? result.getNumIncorrectSolutions() : NOT_APPLICABLE));
            System.out.println("- average dev     - " +
                    ((result.getAverageDeviation() != null) ? result.getAverageDeviation() : NOT_APPLICABLE));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("printResults | end...");
        }
    }

    //get a list of algorithm implementations based on a List of AlgType enums
    private List<IAlgorithm> convertAlgTypeListToAlgs(List<AlgType> algTypes) {
        if (logger.isDebugEnabled()) {
            logger.debug("convertAlgTypeListToAlgs | start...");
        }

        if (algTypes == null || algTypes.isEmpty()) {
            logger.warn("convertAlgTypeListToAlgs | algTypes is null or empty");
            return null;
        }

        List<IAlgorithm> algs = algTypes.stream().map(a -> a.getAlg()).collect(Collectors.toList());

        if (logger.isDebugEnabled()) {
            logger.debug("printResults | end...");
        }

        return algs;
    }

    //ask the user how many nodes large a graph should be
    private int promptNumNodes() {
        if (logger.isDebugEnabled()) {
            logger.debug("promptNumNodes | start...");
        }

        System.out.println("Please enter the number of nodes for the graph the algorithms will attempt to solve");
        System.out.println("The max number of nodes " + RoadTripConstants.PROGRAM_NAME +
                " supports is " + RoadTripConstants.MAX_GRAPH_SIZE);

        Scanner scanner = new Scanner(System.in);
        String command;
        int numNodes;
        while (true) {
            command = scanner.next();
            try {
                numNodes = Integer.parseInt(command);
                if (numNodes > RoadTripConstants.MAX_GRAPH_SIZE) {
                    System.out.println("That is too many nodes");
                } else if (numNodes < RoadTripConstants.MIN_GRAPH_SIZE) {
                    System.out.println("A graph must have at least " + RoadTripConstants.MIN_GRAPH_SIZE + " nodes");
                } else {
                    break;
                }
            } catch (Exception e) {
                displayInvalidInputWarning(command);
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("promptNumNodes | end...");
        }

        return numNodes;
    }

    //ask the user if they would like to print the graph's distance matrix
    private boolean shouldPrintDistanceMatrix() {
        if (logger.isDebugEnabled()) {
            logger.debug("shouldPrintDistanceMatrix | start...");
        }

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

        if (logger.isDebugEnabled()) {
            logger.debug("shouldPrintDistanceMatrix | end...");
        }

        return shouldPrint;
    }

    private List<AlgType> getAlgorithmsToCompareFromUser() {
        if (logger.isDebugEnabled()) {
            logger.debug("getAlgorithmsToCompareFromUser | start...");
        }

        List<AlgType> algs = new ArrayList<>();

        //print a prompt
        System.out.println("From the list below, choose the algorithms you would like to compare");
        System.out.println("enter H for help on how to pick and choose algorithms");
        System.out.println("enter * to choose all of them\n");

        //print the algorithm choices
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
                //split up the input and choose the appropriate algorithms
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

        if (logger.isDebugEnabled()) {
            logger.debug("getAlgorithmsToCompareFromUser | end...");
        }

        return algs;
    }

    //prints the node distance matrix to stdout
    private void printDistanceMatrix(IGraph graph) {
        if (logger.isDebugEnabled()) {
            logger.debug("printDistanceMatrix | start...");
        }

        //get the distance matrix from the graph
        TreeMap<Node, TreeMap<Node, BigDecimal>> dm = graph.getDistanceMatrix();

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
                BigDecimal distance = dm.get(thisNode).get(thatNode);
                sb2.append(String.format(distanceFormat, distance));
                k++;
            }
            System.out.println(sb2.toString());
            j++;
        }
        System.out.println("\n");

        if (logger.isDebugEnabled()) {
            logger.debug("printDistanceMatrix | end...");
        }
    }

    //Reads the help file's text and displays it for the user
    private void showHelp() {
        if (logger.isDebugEnabled()) {
            logger.debug("showHelp | start...");
        }

        try {
            Path path = Paths.get(RoadTripConstants.PATH_TO_GENERAL_HELP_FILE);
            Files.lines(path).forEach(System.out::println);
        } catch (IOException eio) {
            System.out.println("Could not read the help file. Please check the logs for more info");
            logger.error("showHelp | error reading help file",eio);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("showHelp | end...");
        }
    }

    //displays a warning that the user input is invalid
    private void displayInvalidInputWarning(String command) {
        if (logger.isDebugEnabled()) {
            logger.debug("displayInvalidInputWarning | start...");
        }

        System.out.println("Invalid input: " + command + "\n");

        if (logger.isDebugEnabled()) {
            logger.debug("displayInvalidInputWarning | end...");
        }
    }

    //steps required to shut down the program
    private void exitProgram() {
        if (logger.isDebugEnabled()) {
            logger.debug("exitProgram | start...");
        }

        showClosingScreen();
        System.exit(0);
    }

    //shows the welcome screen when the program first starts
    private void showWelcomeScreen() {
        if (logger.isDebugEnabled()) {
            logger.debug("showWelcomeScreen | start...");
        }

        System.out.println(DECORATIVE_BORDER);
        System.out.println("Welcome to " + RoadTripConstants.PROGRAM_NAME + "\n");
        System.out.println(DECORATIVE_BORDER + "\n");
        System.out.println("All commands are case insensitive");

        if (logger.isDebugEnabled()) {
            logger.debug("showWelcomeScreen | end...");
        }
    }

    //prints the closing screen when the program is exiting
    private void showClosingScreen() {
        if (logger.isDebugEnabled()) {
            logger.debug("showClosingScreen | start...");
        }

        System.out.println("\n" + DECORATIVE_BORDER);
        System.out.println("Thank you for using " + RoadTripConstants.PROGRAM_NAME);
        System.out.println("Goodbye...\n");
        System.out.println(DECORATIVE_BORDER + "\n");

        if (logger.isDebugEnabled()) {
            logger.debug("showClosingScreen | end...");
        }
    }

}
