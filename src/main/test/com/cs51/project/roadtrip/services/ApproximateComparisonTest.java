package com.cs51.project.roadtrip.services;

import com.cs51.project.roadtrip.algorithms.BruteForceAlgorithm;
import com.cs51.project.roadtrip.algorithms.NearestNeighborAlgorithm;
import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.graphs.ListGraph;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IComparisonService;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for approximate comparison service
 */
public class ApproximateComparisonTest {
    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(ApproximateComparisonTest.class);

    private static IComparisonService acs = new ApproximateComparisonService();

    @Test
    public void testApproximateComparisonServiceWithOneIteration() {
        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithOneIteration | start...");
        }

        List<IAlgorithm> algs = new ArrayList<>();
        IAlgorithm nn = new NearestNeighborAlgorithm();
        IAlgorithm bf = new BruteForceAlgorithm();
        algs.add(nn);
        algs.add(bf);
        IGraph graph = new ListGraph(5);
        List<Result> results = acs.executeComparison(graph, algs, 1);
        assertTrue(results != null);
        assertEquals(2, results.size());

        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithOneIteration | end...");
        }
    }

    @Test
    public void testApproximateComparisonServiceWithFiveIterations() {
        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithFiveIterations | start...");
        }

        List<IAlgorithm> algs = new ArrayList<>();
        IAlgorithm nn = new NearestNeighborAlgorithm();
        IAlgorithm bf = new BruteForceAlgorithm();
        algs.add(nn);
        algs.add(bf);
        IGraph graph = new ListGraph(5);
        List<Result> results = acs.executeComparison(graph, algs, 5);
        assertTrue(results != null);
        assertEquals(2, results.size());

        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithFiveIterations | end...");
        }
    }

    @Test
    public void testApproximateComparisonServiceWithEmptyListOfAlgs() {
        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithEmptyListOfAlgs | start...");
        }

        List<IAlgorithm> algs = new ArrayList<>();
        IGraph graph = new ListGraph(5);
        List<Result> results = acs.executeComparison(graph, algs, 5);
        assertTrue(results == null);

        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithEmptyListOfAlgs | end...");
        }
    }

    @Test
    public void testApproximateComparisonServiceWithNullListOfAlgs() {
        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithNullListOfAlgs | start...");
        }

        IGraph graph = new ListGraph(5);
        List<Result> results = acs.executeComparison(graph, null, 5);
        assertTrue(results == null);

        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithNullListOfAlgs | end...");
        }
    }

    @Test
    public void testApproximateComparisonServiceWithInvalidIterations() {
        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithInvalidIterations | start...");
        }

        List<IAlgorithm> algs = new ArrayList<>();
        IAlgorithm nn = new NearestNeighborAlgorithm();
        IAlgorithm bf = new BruteForceAlgorithm();
        algs.add(nn);
        algs.add(bf);
        IGraph graph = new ListGraph(5);
        List<Result> results = acs.executeComparison(graph, algs, 0);
        assertTrue(results == null);

        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithInvalidIterations | end...");
        }
    }

    @Test
    public void testApproximateComparisonServiceWithNullGraph() {
        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithNullGraph | start...");
        }

        List<IAlgorithm> algs = new ArrayList<>();
        IAlgorithm nn = new NearestNeighborAlgorithm();
        IAlgorithm bf = new BruteForceAlgorithm();
        algs.add(nn);
        algs.add(bf);
        List<Result> results = acs.executeComparison(null, algs, 5);
        assertTrue(results == null);

        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithNullGraph | end...");
        }
    }

    @Test
    public void testApproximateComparisonServiceWithNoExactAlg() {
        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithNoExactAlg | start...");
        }

        List<IAlgorithm> algs = new ArrayList<>();
        IAlgorithm nn = new NearestNeighborAlgorithm();
        algs.add(nn);
        IGraph graph = new ListGraph(5);
        List<Result> results = acs.executeComparison(graph, algs, 5);
        assertNull(results);

        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithNoExactAlg | end...");
        }
    }

    @Test
    public void testApproximateComparisonServiceWithNoNonExactAlg() {
        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithNoNonExactAlg | start...");
        }

        List<IAlgorithm> algs = new ArrayList<>();
        IAlgorithm nn = new BruteForceAlgorithm();
        algs.add(nn);
        IGraph graph = new ListGraph(5);
        List<Result> results = acs.executeComparison(graph, algs, 5);
        assertNull(results);

        if (logger.isDebugEnabled()) {
            logger.debug("testApproximateComparisonServiceWithNoNonExactAlg | end...");
        }
    }

}
