package com.cs51.project.roadtrip;

import com.cs51.project.roadtrip.algorithms.BruteForceAlgorithm;
import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import com.cs51.project.roadtrip.interfaces.IUserInterface;
import com.cs51.project.roadtrip.uis.CommandLineUI;
import org.apache.log4j.Logger;


public class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    public static void main (String [] args) {

        //TODO this stuff here can eventually be moved into a test class for ListGraph

//        IGraph graph = new ListGraph(5);
//        for (Node n : graph.getDistanceMatrix().keySet()) {
//            for (Node n2 : graph.getDistanceMatrix().get(n).keySet()) {
//                System.out.println(n.getName() + " -> " + n2.getName() + " = " + graph.getDistanceMatrix().get(n).get(n2));
//            }
//        }
//        System.out.println("N0 closest unvisited neighbor = " + graph.getClosestUnvisitedNeighbor(graph.getNodeByName("N0")).getName());

        //this was just a test to see that the permutation stuff i had was working... still needs work
//
//        logger.error("this is a test error");
//
//        IGraph graph = new ListGraph(3);
//        IAlgorithm bfAlg = new BruteForceAlgorithm();
//        bfAlg.execute(graph);


        //TODO this is the stuff that should stay in this method

        IUserInterface commandUI = new CommandLineUI();
        commandUI.execute();
    }

}
