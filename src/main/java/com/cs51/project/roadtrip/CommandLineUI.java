package com.cs51.project.roadtrip;

import java.util.Scanner;

/**
 * Created by Alexander on 16.04.2015.
 */
public class CommandLineUI implements UI {

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

            if (command.equalsIgnoreCase("exit")){
                exitProgram();
            }
            else if (command.equalsIgnoreCase("compare")){
                runComparison();
            }
            else if (command.equalsIgnoreCase("options")){
                displayOptions();
            }
            else if (command.equalsIgnoreCase("results")){
                displayComparisonResults();
            }
        }
    }

    @Override
    public void runComparison() {
        ListGraph listGraph = new ListGraph();
        listGraph.initGraph();
        Node n1 = listGraph.getNodeById(0);

    }

    @Override
    public void exitProgram() {
        System.exit(0);
    }

    @Override
    public void displayComparisonResults() {

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
    public void execute() {
        System.out.println("Welcome to Road Trip\n" +
                "If you need help with the possible commands enter options\n" +
                "If you want to exit the programm please enter exit ");
        getUserInput();
    }
}
