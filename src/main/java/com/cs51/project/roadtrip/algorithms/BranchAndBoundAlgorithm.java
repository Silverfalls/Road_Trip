package com.cs51.project.roadtrip.algorithms;

import com.cs51.project.roadtrip.algorithms.base.BaseAlgorithm;
import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.common.utils.RoadTripUtils;
import com.cs51.project.roadtrip.enums.AlgType;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;


public class BranchAndBoundAlgorithm extends BaseAlgorithm implements IAlgorithm {

    /**
     * Logger instance
     */
    private static Logger logger = Logger.getLogger(BranchAndBoundAlgorithm.class);

    private IGraph mGraph;
    private List<Branch> branches;
    private Branch shortestGoalBranch;
    private Long iterations = 0L;
    private Result result = null;
    private long runningTime = 0L;

    @Override
    public Result execute(IGraph graph) {
        result = new Result();
        mGraph = graph;
        branches = new ArrayList<>();

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
            root.addChildBranch(b);
            branches.add(b);
        }

        //TODO delete
        for (Branch b : branches) {
            System.out.println("initial branch = " + RoadTripUtils.convertListToPath(b.getVisitedNodes()));
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
//                System.out.println("unvisited nodes = empty so here is the path : " + RoadTripUtils.convertListToPath(branch.getVisitedNodes()));
                if (visitedEverywhere(branch)) {
                    Branch getHomeBranch = new Branch(branch, startingNode);
                    System.out.println("SGB candidate = " + RoadTripUtils.convertListToPath(getHomeBranch.getVisitedNodes()) +
                        " accumulated distance = " + getHomeBranch.getAccumulatedDistance());
                    if (shortestGoalBranch == null
                            || getHomeBranch.getAccumulatedDistance().compareTo(shortestGoalBranch.getAccumulatedDistance()) == -1) {
                        shortestGoalBranch = getHomeBranch;
                    }
                    System.out.println("SGB path = " + RoadTripUtils.convertListToPath(shortestGoalBranch.getVisitedNodes())
                        + " accumulated distance = " + shortestGoalBranch.getAccumulatedDistance());
                } else {
                    logger.warn("execute | for some reason, getClosestNeighborToNode returned null but the branch did" +
                        " not pass the visitedEverywhere check");
                }
                branches.remove(branch);
            }
            pruneBranches();
        } while (!branches.isEmpty());

        //stop the clock
        runningTime = System.currentTimeMillis() - startTime;

        System.out.println("B&B final path = " + RoadTripUtils.convertListToPath(shortestGoalBranch.getVisitedNodes()));

        //set the result for return
        setResult();

        reset();

        return result;
    }

    private void setResult() {
        result.setName(AlgType.BRANCH_AND_BOUND.getName());
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
        private Branch parent;
        private List<Branch> children;
        private List<Node> unvisitedNodes;
        private BigDecimal accumulatedDistance;
        private Node node;

        //constructor for root branch only
        private Branch(List<Node> visitedNodes, List<Node> unvisitedNodes) {
            this.visitedNodes = visitedNodes;
            this.unvisitedNodes = unvisitedNodes;
            this.accumulatedDistance = BigDecimal.ZERO;
            this.parent = null;
            node = visitedNodes.get(0);
        }

        //constructor for all other branches
        private Branch(Branch parent, Node node) {
            this.parent = parent;
            setVisitedNodes(parent, node);
            setUnvisitedNodes(parent, node);
            this.accumulatedDistance = parent.getAccumulatedDistance().add(mGraph.getDistance(parent.getNode(), node));
            this.node = node;
        }


        private void setVisitedNodes(Branch parent, Node node) {
            visitedNodes = new ArrayList<>(parent.getVisitedNodes().size() + 1);
            visitedNodes.addAll(parent.getVisitedNodes());
            visitedNodes.add(node);
        }

        public void setAccumulatedDistance(BigDecimal d) {

            accumulatedDistance = d;
        }

        private void setUnvisitedNodes(Branch parent, Node node) {

            unvisitedNodes = new ArrayList<>();
            //TODO says I can use collect here
            for (Node n : parent.getUnvisitedNodes()) {
                if (n != node) {
                    unvisitedNodes.add(n);
                }
            }
        }

        public List<Node> getVisitedNodes() {
            return visitedNodes;
        }

        public Branch getParent() {
            return parent;
        }

        public List<Node> getUnvisitedNodes() {
            return unvisitedNodes;
        }

        public void addChildBranch(Branch branch) {
            if (children == null) {
                children = new ArrayList<>();
            }
            children.add(branch);
        }

        public BigDecimal getAccumulatedDistance() {
            return accumulatedDistance;
        }

        public void removeNodeFromUnvisitedList(Node node) {
            unvisitedNodes.remove(node);
        }

        public Node getNode() {
            return node;
        }

        //TODO, if we have time, include this and clean it up, otherwise throw it away
//        public String toString() {
//            StringBuilder sb = new StringBuilder();
//            sb.append("accumulated distance = " + accumulatedDistance + "\n");
//            sb.append("unextended nodes list size = " + unvisitedNodes.size() + "\n");
//            sb.append("path node list size = " + visitedNodes.size() + "\n");
//            sb.append("children list size = " + ((children != null) ? children.size() : "null children") + "\n");
//            return sb.toString();
//        }

    }

    //TODO can we shorten this method down???
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

    private void pruneBranches() {
        if (shortestGoalBranch != null) {
            Iterator iterator = branches.iterator();
            while (iterator.hasNext()) {
                Branch b = (Branch) iterator.next();
                if (b.getAccumulatedDistance().compareTo(shortestGoalBranch.getAccumulatedDistance()) >= 0) {
                    iterator.remove();
                }
            }
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("pruneBranches | goal is not set yet");
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
                if (d.compareTo(distance) ==  -1) {
                    distance = d;
                    nodeToReturn = n;
                }
            }
        } else {
            logger.error("getClosestNeighborToNode | node or nodes is null");
        }
        return nodeToReturn;
    }

    public BigDecimal getD(List<Node> n, IGraph g) {
        return getDistance(n, g);
    }



    @Override
    public void reset() {
        runningTime = 0L;
        iterations = 0L;
        shortestGoalBranch = null;
        //no need to set result to null here because it will be reinstantiated on next execution
    }
}
