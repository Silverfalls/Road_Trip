package com.cs51.project.roadtrip.algorithms;

import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robertschupp on 4/17/15.
 */
public class BranchAndBoundAlgorithmTest {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(BranchAndBoundAlgorithmTest.class);


    @Test
    public void testTest() {

        Node n0 = new Node(0, 1, 1, "N0", true);
        Node n1 = new Node(1, 50, 89, "N1", false);
        Node n2 = new Node(2, 99, 77, "N2", false);
        Node n3 = new Node(3, 43, 4, "N3", false);
        List<Node> baseList = new ArrayList<>();
        baseList.add(n0);
        baseList.add(n1);
        baseList.add(n2);
        baseList.add(n3);

        IGraph graph = new ListGraph(baseList);
        BranchAndBoundAlgorithm alg = new BranchAndBoundAlgorithm();


        List<Node> l1 = new ArrayList<>();
        l1.add(n0);
        l1.add(n1);
        l1.add(n2);
        l1.add(n3);
        l1.add(n0);

        List<Node> l2 = new ArrayList<>();
        l1.add(n0);
        l1.add(n1);
        l1.add(n3);
        l1.add(n2);
        l1.add(n0);

        List<Node> l3 = new ArrayList<>();
        l1.add(n0);
        l1.add(n2);
        l1.add(n1);
        l1.add(n3);
        l1.add(n0);

        List<Node> l4 = new ArrayList<>();
        l1.add(n0);
        l1.add(n2);
        l1.add(n3);
        l1.add(n1);
        l1.add(n0);

        List<Node> l5 = new ArrayList<>();
        l1.add(n0);
        l1.add(n3);
        l1.add(n1);
        l1.add(n2);
        l1.add(n0);

        List<Node> l6 = new ArrayList<>();
        l1.add(n0);
        l1.add(n3);
        l1.add(n2);
        l1.add(n1);
        l1.add(n0);

        List<List<Node>> possibilities = new ArrayList<>();
        possibilities.add(l1);
        possibilities.add(l2);
        possibilities.add(l3);
        possibilities.add(l4);
        possibilities.add(l5);
        possibilities.add(l6);

        for (List<Node> l : possibilities) {
            StringBuilder sb = new StringBuilder();
            for (Node n : l) {
                sb.append(n.getName() + " -> ");
            }
            sb.append(alg.getD(l, graph));
            System.out.println(sb.toString());
        }



//        IGraph graph = new ListGraph(3);
        alg.execute(graph);
    }



}
