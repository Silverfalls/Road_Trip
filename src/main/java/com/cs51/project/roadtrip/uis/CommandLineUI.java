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
import java.util.stream.Collectors;


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


    private void displayOptions() {
        if (logger.isDebugEnabled()) {
            logger.debug("displayOptions | start...");
        }

        System.out.println("\nCommands:\n");

        //loop through each comparison type and print it as an option
        for (CompType compType : CompType.values()) {
            System.out.println(compType.getOptionChar() + " : " + compType.getName());
        }

        //print static options
        System.out.println(HELP_OPTION_CHAR + " : Help");
        System.out.println(QUIT_OPTION_CHAR + " : Quit");

        if (logger.isDebugEnabled()) {
            logger.debug("displayOptions | end...");
        }
    }


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
        if (logger.isDebugEnabled()) {
            logger.debug("setupComparison | start...");
        }

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
                try {
                    System.out.println(WAITING_INDICATOR);
                    List<Result> results = compType.getService().executeComparison(graph, converAlgTypeListToAlgs(algTypes));
                    if (results != null) {
                        printResults(results);
                    } else {
                        //do something
                    }
                } catch (Exception e) {
                    //do something about it
                    e.printStackTrace();
                }
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("setupComparison | end...");
        }
    }

    private void printResults(List<Result> results) {
        if (logger.isDebugEnabled()) {
            logger.debug("printResults | start...");
        }
        //TODO TODO TODO left off here
        System.out.println("In the print results section, left off here for the night");

        if (logger.isDebugEnabled()) {
            logger.debug("printResults | end...");
        }
    }

    private List<IAlgorithm> converAlgTypeListToAlgs(List<AlgType> algTypes) {
        if (logger.isDebugEnabled()) {
            logger.debug("converAlgTypeListToAlgs | start...");
        }

        if (algTypes == null || algTypes.isEmpty()) {
            //log and do something
        }

        List<IAlgorithm> algs = algTypes.stream().map(a -> a.getAlg()).collect(Collectors.toList());

        if (logger.isDebugEnabled()) {
            logger.debug("printResults | end...");
        }

        return algs;
    }

    private int promptNumNodes() {
        if (logger.isDebugEnabled()) {
            logger.debug("promptNumNodes | start...");
        }

        int numNodes = 4;

        //TODO!!!!



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

        if (logger.isDebugEnabled()) {
            logger.debug("getAlgorithmsToCompareFromUser | end...");
        }

        return algs;
    }

    //TODO UGH this method is ugly
    private void printDistanceMatrix(IGraph graph) {
        if (logger.isDebugEnabled()) {
            logger.debug("printDistanceMatrix | start...");
        }

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

    private void displayInvalidInputWarning(String command) {
        if (logger.isDebugEnabled()) {
            logger.debug("displayInvalidInputWarning | start...");
        }

        System.out.println("Invalid input: " + command + "\n");

        if (logger.isDebugEnabled()) {
            logger.debug("displayInvalidInputWarning | end...");
        }
    }

    private void exitProgram() {
        if (logger.isDebugEnabled()) {
            logger.debug("exitProgram | start...");
        }

        showClosingScreen();
        System.exit(0);
    }

    private void showWelcomeScreen() {
        if (logger.isDebugEnabled()) {
            logger.debug("showWelcomeScreen | start...");
        }

        System.out.println(DECORATIVE_BORDER + "\n");
        System.out.println("Welcome to " + RoadTripConstants.PROGRAM_NAME + "\n");
        System.out.println(DECORATIVE_BORDER + "\n");

        if (logger.isDebugEnabled()) {
            logger.debug("showWelcomeScreen | end...");
        }
    }

    private void showClosingScreen() {
        if (logger.isDebugEnabled()) {
            logger.debug("showClosingScreen | start...");
        }

        System.out.println(DECORATIVE_BORDER + "\n");
        System.out.println("Thank you for using " + RoadTripConstants.PROGRAM_NAME);
        System.out.println("Goodbye...\n");
        System.out.println(DECORATIVE_BORDER + "\n");

        if (logger.isDebugEnabled()) {
            logger.debug("showClosingScreen | end...");
        }
    }

}
