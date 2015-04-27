package com.cs51.project.roadtrip.services;

import com.cs51.project.roadtrip.algorithms.BruteForceAlgorithm;
import com.cs51.project.roadtrip.algorithms.NearestNeighborAlgorithm;
import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IComparisonService;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for BasicComparisonService implementation of IComparisonService interface
 */
public class BasicComparisonServiceTest {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(BasicComparisonServiceTest.class);

    private static IComparisonService bcs = new BasicComparisonService();

    @Test
    public void testBasicComparisonServiceWithOneIteration() {
        if (logger.isDebugEnabled()) {
            logger.debug("testBasicComparisonServiceWithOneIteration | start...");
        }

        List<IAlgorithm> algs = new ArrayList<>();
        IAlgorithm nn = new NearestNeighborAlgorithm();
        IAlgorithm bf = new BruteForceAlgorithm();
        algs.add(nn);
        algs.add(bf);
        IGraph graph = new ListGraph(5);
        List<Result> results = bcs.executeComparison(graph, algs, 1);
        assertTrue(results != null);
        assertEquals(2, results.size());

        if (logger.isDebugEnabled()) {
            logger.debug("testBasicComparisonServiceWithOneIteration | end...");
        }
    }

    @Test
    public void testBasicComparisonServiceWithFiveIterations() {
        if (logger.isDebugEnabled()) {
            logger.debug("testBasicComparisonServiceWithFiveIterations | start...");
        }

        List<IAlgorithm> algs = new ArrayList<>();
        IAlgorithm nn = new NearestNeighborAlgorithm();
        IAlgorithm bf = new BruteForceAlgorithm();
        algs.add(nn);
        algs.add(bf);
        IGraph graph = new ListGraph(5);
        List<Result> results = bcs.executeComparison(graph, algs, 5);
        assertTrue(results != null);
        assertEquals(2, results.size());

        if (logger.isDebugEnabled()) {
            logger.debug("testBasicComparisonServiceWithFiveIterations | end...");
        }
    }

    @Test
    public void testBasicComparisonServiceWithEmptyListOfAlgs() {
        if (logger.isDebugEnabled()) {
            logger.debug("testBasicComparisonServiceWithEmptyListOfAlgs | start...");
        }

        List<IAlgorithm> algs = new ArrayList<>();
        IGraph graph = new ListGraph(5);
        List<Result> results = bcs.executeComparison(graph, algs, 5);
        assertTrue(results == null);

        if (logger.isDebugEnabled()) {
            logger.debug("testBasicComparisonServiceWithEmptyListOfAlgs | end...");
        }
    }

    @Test
    public void testBasicComparisonServiceWithNullListOfAlgs() {
        if (logger.isDebugEnabled()) {
            logger.debug("testBasicComparisonServiceWithNullListOfAlgs | start...");
        }

        IGraph graph = new ListGraph(5);
        List<Result> results = bcs.executeComparison(graph, null, 5);
        assertTrue(results == null);

        if (logger.isDebugEnabled()) {
            logger.debug("testBasicComparisonServiceWithNullListOfAlgs | end...");
        }
    }

    @Test
    public void testBasicComparisonServiceWithInvalidIterations() {
        if (logger.isDebugEnabled()) {
            logger.debug("testBasicComparisonServiceWithInvalidIterations | start...");
        }

        List<IAlgorithm> algs = new ArrayList<>();
        IAlgorithm nn = new NearestNeighborAlgorithm();
        IAlgorithm bf = new BruteForceAlgorithm();
        algs.add(nn);
        algs.add(bf);
        IGraph graph = new ListGraph(5);
        List<Result> results = bcs.executeComparison(graph, algs, 0);
        assertTrue(results == null);

        if (logger.isDebugEnabled()) {
            logger.debug("testBasicComparisonServiceWithInvalidIterations | end...");
        }
    }

    @Test
    public void testBasicComparisonServiceWithNullGraph() {
        if (logger.isDebugEnabled()) {
            logger.debug("testBasicComparisonServiceWithNullGraph | start...");
        }

        List<IAlgorithm> algs = new ArrayList<>();
        IAlgorithm nn = new NearestNeighborAlgorithm();
        IAlgorithm bf = new BruteForceAlgorithm();
        algs.add(nn);
        algs.add(bf);
        List<Result> results = bcs.executeComparison(null, algs, 5);
        assertTrue(results == null);

        if (logger.isDebugEnabled()) {
            logger.debug("testBasicComparisonServiceWithNullGraph | end...");
        }
    }


}
