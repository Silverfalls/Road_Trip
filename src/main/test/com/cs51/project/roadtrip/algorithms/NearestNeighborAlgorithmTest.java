package com.cs51.project.roadtrip.algorithms;

import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the NearestNeighbor algorithm implementation
 */
public class NearestNeighborAlgorithmTest {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(NearestNeighborAlgorithmTest.class);

    private static IGraph graph;
    private static IGraph graph2;

    private static Node n0;
    private static Node n1;
    private static Node n2;
    private static Node n3;

    private static Node n20;
    private static Node n21;
    private static Node n22;
    private static Node n23;

    @BeforeClass
    public static void initTest(){
        n0 = new Node(0, 99, 13, "N0", true);
        n1 = new Node(1, 50, 89, "N1", false);
        n2 = new Node(2, 99, 77, "N2", false);
        n3 = new Node(3, 43, 4, "N3", false);
        List<Node> baseList = new ArrayList<>(4);
        baseList.add(n0);
        baseList.add(n1);
        baseList.add(n2);
        baseList.add(n3);

        graph = new ListGraph(baseList);

        n20 = new Node(0, 0, 100, "N0", true);
        n21 = new Node(2, 0, 0, "N2", false);
        n22 = new Node(1, 100, 0, "N1", false);
        n23 = new Node(3, 100, 100, "N3", false);
        List<Node> baseList2 = new ArrayList<>(4);
        baseList2.add(n20);
        baseList2.add(n21);
        baseList2.add(n22);
        baseList2.add(n23);

        graph2 = new ListGraph(baseList2);

    }


    @Test
    public void executeTest (){
        IAlgorithm alg = new NearestNeighborAlgorithm();

        Result result = alg.execute(graph);
        Result result2 = alg.execute(graph2);

        //fill expected result
        List<Object> expectedResult = new ArrayList<>(4);
        expectedResult.add(n0);
        expectedResult.add(n3);
        expectedResult.add(n1);
        expectedResult.add(n2);
        expectedResult.add(n0);

        List<Object> expectedResult2 = new ArrayList<>(4);
        expectedResult2.add(n20);
        expectedResult2.add(n21);
        expectedResult2.add(n22);
        expectedResult2.add(n23);
        expectedResult2.add(n20);

        //check the result values of the NearestNeighbor

        assertEquals("Wrong number of iterations", 4, result.getIterations());
        assertEquals("Wrong calculated path", expectedResult, result.getCalculatedPath());
        assertEquals("Wrong calculated distance", new BigDecimal("256.46"), result.getCalculatedDistance());
        assertEquals("Wrong graph size", 4, result.getGraphSize());

        assertEquals("Wrong number of iterations", 4, result2.getIterations());
        assertEquals("Wrong calculated path", expectedResult2, result2.getCalculatedPath());
        assertEquals("Wrong calculated distance", new BigDecimal("400.00"), result2.getCalculatedDistance());
        assertEquals("Wrong graph size", 4,result2.getGraphSize());
    }
}
