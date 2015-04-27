package com.cs51.project.roadtrip.graphs;

import com.cs51.project.roadtrip.interfaces.IGraph;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Test class for the list based implementation of the IGraph interface
 */
public class ListGraphTest {

    /**
     * Logger Instance
     */
//    private static Logger logger = Logger.getLogger(ListGraphTest.class);

    private static IGraph graph;

    private static List<Node> baseList;

    private static Node n0;
    private static Node n1;
    private static Node n2;
    private static Node n3;

    @BeforeClass
    public static void initTest(){
        n0 = new Node(0, 25, 13, "N0", true);
        n1 = new Node(1, 50, 89, "N1", false);
        n2 = new Node(2, 99, 77, "N2", false);
        n3 = new Node(3, 43, 4, "N3", false);
        baseList = new ArrayList<>(4);
        baseList.add(n0);
        baseList.add(n1);
        baseList.add(n2);
        baseList.add(n3);
        n2.setVisited(true);

        graph = new ListGraph(baseList);
    }

    @Test
    public void cloneTest() {
        IGraph newGraph = graph.getClone();
        assertTrue("Nodes from original graph in getClone",
                newGraph.getListOfNodes().stream().allMatch(n -> !baseList.contains(n)));

        //TODO: oh god this looks ugly
        assertTrue("Node values of the getClone don't match with the original",
                newGraph.getListOfNodes().stream().allMatch(n ->
                {
                    boolean inList = false;
                    for (Node node : baseList) {
                        if (node.getName().equals(n.getName()) && node.getxCoord() == n.getxCoord() && node.getyCoord() == n.getyCoord() &&
                                node.isVisited() == n.isVisited() && node.isStartingNode() == n.isStartingNode())
                            inList = true;
                    }
                    return inList;
                }));
    }

    @Test
    public void getListOfNodesTest() {
        List<Node> nodesList = graph.getListOfNodes();
        assertTrue(graph.getListOfNodes().stream().allMatch(nodesList::contains));
    }

    @Test
    public void getNodeByIdTest() {
        assertEquals(n1, graph.getNodeById(1));
        assertEquals(n3, graph.getNodeById(3));
        assertEquals(null, graph.getNodeById(8));
    }

    @Test
    public void getNodeByNameTest() {
        assertEquals(n1, graph.getNodeByName("N1"));
        assertEquals(n3, graph.getNodeByName("N3"));
        assertEquals(null, graph.getNodeByName("N8"));
    }

    @Test
    public void getClosestNeighborTest() {
        assertEquals(n2, graph.getClosestNeighbor(n1));
        assertEquals(n0, graph.getClosestNeighbor(n3));
    }

    @Test
    public void getClosestUnvisitedNeighborTest() {
        assertEquals(n0, graph.getClosestUnvisitedNeighbor(n1));
        assertEquals(n0, graph.getClosestUnvisitedNeighbor(n3));
    }

    @Test
    public void getFurthestNeighborTest() {
        assertEquals(n2, graph.getFurthestNeighbor(n3));
        assertEquals(n0, graph.getFurthestNeighbor(n2));
    }

    @Test
    public void getFurthestUnvisitedNeighborTest() {
        assertEquals(n1, graph.getFurthestUnvisitedNeighbor(n3));
        assertEquals(n0, graph.getFurthestUnvisitedNeighbor(n2));
    }

    @Test
    public void getDistanceTest() {
        assertEquals(new BigDecimal("80.01"), graph.getDistance(n0,n1));
        assertEquals(new BigDecimal("92.01"), graph.getDistance(n2,n3));
    }

    @Test
    public void getStartingNodeTest() {
        assertEquals(n0, graph.getStartingNode());
    }

    @Test
    public void getDistanceMatrixTest() {
    }

    @Test
    public void getAllNeighborsTest() {
        List<Node> result = graph.getAllNeighbors(n0);
        List<Node> expResult = new ArrayList<>(3);
        expResult.add(n1);
        expResult.add(n2);
        expResult.add(n3);
        assertTrue(result.containsAll(expResult));
    }

    @Test
    public void getGraphSizeTest() {
        assertEquals(4,graph.getGraphSize());
        assertEquals(25,new ListGraph(25).getGraphSize());
    }
}
