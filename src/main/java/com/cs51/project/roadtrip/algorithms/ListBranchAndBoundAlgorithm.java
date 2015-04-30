package com.cs51.project.roadtrip.algorithms;

import com.cs51.project.roadtrip.algorithms.base.BaseAlgorithm;
import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.enums.AlgType;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ListBranchAndBoundAlgorithm extends BaseAlgorithm implements IAlgorithm {

    /**
     * Logger instance
     */
    private static final Logger logger = Logger.getLogger(ListBranchAndBoundAlgorithm.class);

    private IGraph mGraph;
    private List<Branch> branches;
    private Branch shortestGoalBranch;
    private Long iterations = 0L;
    private Result result = null;
    private long runningTime = 0L;
    private List<Node> allGraphNodes;

    @Override
    public Result execute(IGraph graph) {
        result = new Result();
        mGraph = graph;
        branches = new ArrayList<>();

        if (allGraphNodes == null) {
            allGraphNodes = graph.getListOfNodes();
        }

        //start the clock
        long startTime = System.currentTimeMillis();

        //get our starting node and its neighbors (all other nodes in this case)
        Node startingNode = graph.getStartingNode();
        List<Node> neighbors = graph.getAllNeighbors(startingNode);

        //set up our root branch
        List<Node> startingList = new ArrayList<>();
        startingList.add(startingNode);
        Branch root = new Branch(startingList, neighbors);

        //set up branches for all of the neighbors to the root
        for (Node node : neighbors) {
            Branch b = new Branch(root, node);
//            root.addChildBranch(b);
            branches.add(b);
        }

        //clear the root unvisited list now since technically a branch has been created for all its neighbors
        root.getUnvisitedNodes().clear();

        do {
            iterations++;
            Branch branch = getShortestBranch();

            Node closestNode = getClosestNeighborToNode(branch.getNode(), branch.getUnvisitedNodes());

            if (closestNode != null) {
                Branch newBranch = new Branch(branch, closestNode);
                branch.getUnvisitedNodes().remove(closestNode);
                branches.add(newBranch);
            } else {
                if (visitedEverywhere(branch)) {
                    Branch getHomeBranch = new Branch(branch, startingNode);
                    if (shortestGoalBranch == null
                            || getHomeBranch.getAccumulatedDistance().compareTo(shortestGoalBranch.getAccumulatedDistance()) == -1) {
                        shortestGoalBranch = getHomeBranch;
                        pruneBranches();
                    }
                } else {
                    logger.info("execute | not all nodes are visited jet");
                }
                branches.remove(branch);
            }
        } while (!branches.isEmpty());

        //stop the clock
        runningTime = System.currentTimeMillis() - startTime;

        //set the result for return
        setResult();

        reset();

        return result;
    }

    private void setResult() {
        result.setName(AlgType.BRANCH_AND_BOUND_LIST.getName());
        result.setCalculatedDistance(shortestGoalBranch.getAccumulatedDistance());
        result.setGraphSize(mGraph.getGraphSize());
        result.setIterations(iterations);
        result.setRunningTime(runningTime);
        result.setCalculatedPath(shortestGoalBranch.getVisitedNodes());
    }

    private boolean visitedEverywhere(Branch b) {

        List<Node> allNodes = mGraph.getListOfNodes();

        for (Node n : allNodes) {
            if (!b.getVisitedNodes().contains(n)) {
                return false;
            }
        }
        return true;
    }

    private class Branch {

        private List<Node> visitedNodes;
        private List<Node> unvisitedNodes;
        private final BigDecimal accumulatedDistance;
        private final Node node;

        //constructor for root branch only
        private Branch(List<Node> visitedNodes, List<Node> unvisitedNodes) {
            this.visitedNodes = visitedNodes;
            this.unvisitedNodes = unvisitedNodes;
            this.accumulatedDistance = BigDecimal.ZERO;
            node = visitedNodes.get(0);
        }

        //constructor for all other branches
        private Branch(Branch parent, Node node) {
            setVisitedNodes(parent, node);
            setUnvisitedNodes(parent);
            this.accumulatedDistance = parent.getAccumulatedDistance().add(mGraph.getDistance(parent.getNode(), node));
            this.node = node;
        }


        private void setVisitedNodes(Branch parent, Node node) {
            visitedNodes = new ArrayList<>(parent.getVisitedNodes().size() + 1);
            visitedNodes.addAll(parent.getVisitedNodes());
            visitedNodes.add(node);
        }

        private void setUnvisitedNodes(Branch parent) {

            unvisitedNodes = new ArrayList<>();

            List<Node> alreadyVisited = parent.getVisitedNodes();
            unvisitedNodes = mGraph.getListOfNodes().stream()
                    .filter(n -> !alreadyVisited.contains(n))
                    .collect(Collectors.toList());
        }

        public List<Node> getVisitedNodes() {
            return visitedNodes;
        }

        public List<Node> getUnvisitedNodes() {
            return unvisitedNodes;
        }

        public BigDecimal getAccumulatedDistance() {
            return accumulatedDistance;
        }

        public Node getNode() {
            return node;
        }
    }

    private Branch getShortestBranch() {
        Branch branchToReturn = null;
        if (branches != null) {
            BigDecimal minDistance = null;
            for (Branch b : branches) {
                if (minDistance == null) {
                    minDistance = b.getAccumulatedDistance();
                    branchToReturn = b;
                    continue;
                }
                if (b.getAccumulatedDistance().compareTo(minDistance) == -1) {
                    minDistance = b.getAccumulatedDistance();
                    branchToReturn = b;
                }
            }
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("getShortestBranch | branches is null, can not get shortest branch");
            }
        }
        return branchToReturn;
    }

    //get rid of any branches that couldn't possibly be the best route
    private void pruneBranches() {

        if (shortestGoalBranch != null) {
            branches = branches.stream().filter(b -> b.getAccumulatedDistance().compareTo(shortestGoalBranch.getAccumulatedDistance()) == -1).collect(Collectors.toList());
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("pruneBranches | goal is not yet set");
            }
        }
    }


    //this takes a list of nodes and a node and returns the closest node from the list to the passed in node
    private Node getClosestNeighborToNode(Node node, List<Node> nodes) {
        Node nodeToReturn = null;
        BigDecimal distance = null;

        if (nodes != null && node != null) {
            for (Node n : nodes) {
                if (n == node) {
                    continue;
                }
                BigDecimal d = mGraph.getDistance(n, node);
                if (distance == null) {
                    distance = d;
                    nodeToReturn = n;
                    continue;
                }
                if (d.compareTo(distance) == -1) {
                    distance = d;
                    nodeToReturn = n;
                }
            }
        } else {
            logger.error("getClosestNeighborToNode | node or nodes is null");
        }
        return nodeToReturn;
    }

    private void reset() {
        runningTime = 0L;
        iterations = 0L;
        shortestGoalBranch = null;
        //no need to set result to null here because it will be reinstantiated on next execution
    }
}
