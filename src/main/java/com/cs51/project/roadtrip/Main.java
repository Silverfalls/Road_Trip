package com.cs51.project.roadtrip;

import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IGraph;
import com.cs51.project.roadtrip.interfaces.IUserInterface;
import com.cs51.project.roadtrip.uis.CommandLineUI;

import java.util.Random;


public class Main {

    public static void main (String [] args) {

        //TODO this stuff here can eventually be moved into a test class for ListGraph

//        Random r = new Random();
//        for (int i = 0; i < 10; i++) {
//            int res = r.nextInt(100);
//            System.out.println(res);
//        }

        IGraph graph = new ListGraph(5);
        for (Node n : graph.getDistanceMatrix().keySet()) {
            for (Node n2 : graph.getDistanceMatrix().get(n).keySet()) {
                System.out.println(n.getName() + " -> " + n2.getName() + " = " + graph.getDistanceMatrix().get(n).get(n2));
            }
        }
        System.out.println("N0 closest neighbor = " + graph.getClosestUnvisitedNeighbor(graph.getNodeByName("N0")).getName());



        IUserInterface commandUI = new CommandLineUI();
//        commandUI.initialize();
        commandUI.execute();
    }

}
