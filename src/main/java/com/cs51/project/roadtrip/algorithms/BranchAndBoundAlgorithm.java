package com.cs51.project.roadtrip.algorithms;

import com.cs51.project.roadtrip.algorithms.base.BaseAlgorithm;
import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;


public class BranchAndBoundAlgorithm extends BaseAlgorithm implements IAlgorithm {

    /**
     * Logger instance
     */
    private static Logger logger = Logger.getLogger(BranchAndBoundAlgorithm.class);

    IGraph mGraph;
    List<Branch> branches;
    Branch shortestGoalBranch;

    @Override
    public Result execute(IGraph graph) {

        mGraph = graph;
        branches = new ArrayList<>();

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
        //clear the root unvisited list now since technically a branch has been created for all its neighbors
        root.getUnvisitedNodes().clear();


//        for (Branch b : branches) {
//            System.out.println(b.toString());
//        }


        do {
            Branch branch = getShortestBranch();
            Node closestNode = getClosestNeighborToNode(branch.getNode(), branch.getUnvisitedNodes());
            if (closestNode != null) {
                Branch newBranch = new Branch(branch, closestNode);
                branch.getUnvisitedNodes().remove(closestNode);
                branches.add(newBranch);
            } else {
//            if (branch.getUnvisitedNodes().isEmpty()) {
                if (visitedEverywhere(branch)) {
                    Branch getHomeBranch = new Branch(branch, startingNode);
                    if (shortestGoalBranch == null
                            || getHomeBranch.getAccumulatedDistance() < shortestGoalBranch.getAccumulatedDistance()) {
                        shortestGoalBranch = getHomeBranch;
                    }
                }
                branches.remove(branch);
            }
            pruneBranches();
        } while (!branches.isEmpty());

        System.out.println("shortest path = " + shortestGoalBranch.toString());

        System.out.println("we're done");






//        System.out.println("root = " + root.toString());
//        System.out.println("first branch = " + firstBranch.toString());



        return null;
    }

    private boolean visitedEverywhere(Branch b) {
//        System.out.println("starting new iteration");
        List<Node> allNodes = mGraph.getListOfNodes();
//        System.out.println("all nodes");
//        for (Node n : allNodes) {
//            System.out.println(n.getName());
//        }
//        System.out.println("visited nodes");
//        for (Node n : b.getVisitedNodes()) {
//            System.out.println(n.getName());
//        }
//        int i = 0;
        for (Node n : allNodes) {
            if (!b.getVisitedNodes().contains(n)) {
                return false;
            }
//            i++;
//            for (Node n1 : b.getVisitedNodes()) {
//                if (!n.getName().equals(n1.getName())) {
//                    return false;
//                }
//            }
        }
        return true;
    }



    private class Branch {

        private List<Node> visitedNodes;
        private Branch parent;
        private List<Branch> children;
        private List<Node> unvisitedNodes;
        private Double accumulatedDistance;
        private Node node;
        private String name;

        //constructor for root branch only
        private Branch(List<Node> visitedNodes, List<Node> unvisitedNodes) {
            this.visitedNodes = visitedNodes;
            this.unvisitedNodes = unvisitedNodes;
            this.accumulatedDistance = 0.0;
            this.parent = null;
            node = visitedNodes.get(0);
        }

        //constructor for all other branches
        private Branch(Branch parent, Node node) {
            this.parent = parent;
            setVisitedNodes(parent, node);
            setUnvisitedNodes(parent, node);
            this.accumulatedDistance = parent.getAccumulatedDistance() + mGraph.getDistance(parent.getNode(), node);
            this.node = node;
            this.name = node.getName();
        }


        private void setVisitedNodes(Branch parent, Node node) {
            visitedNodes = new ArrayList<>(parent.getVisitedNodes().size() + 1);
            visitedNodes.addAll(parent.getVisitedNodes());
            visitedNodes.add(node);
//            System.out.println("new branch visited nodes");
//            for (Node n : visitedNodes) {
//                System.out.println(n.getName());
//            }
        }

        private void setUnvisitedNodes(Branch parent, Node node) {
//            if (parent.getUnvisitedNodes() == null) {
//                System.out.println("empty parent");
//            }
            unvisitedNodes = new ArrayList<>();
            //TODO says I can use collect here
            for (Node n : parent.getUnvisitedNodes()) {
                if (n != node) {
                    unvisitedNodes.add(n);
                }
            }
//            if (unvisitedNodes.size() == 0) {
//                System.out.println("empyt unvisited nodes");
//            }
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

        public Double getAccumulatedDistance() {
            return accumulatedDistance;
        }

        public void removeNodeFromUnvisitedList(Node node) {
            unvisitedNodes.remove(node);
        }

        public Node getNode() {
            return node;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("accumulated distance = " + accumulatedDistance + "\n");
            sb.append("unextended nodes list size = " + unvisitedNodes.size() + "\n");
            sb.append("path node list size = " + visitedNodes.size() + "\n");
            sb.append("children list size = " + ((children != null) ? children.size() : "null children") + "\n");
            return sb.toString();
        }

    }

    //TODO can we shorten this method down???
    private Branch getShortestBranch() {
        Branch branchToReturn = null;
        if (branches != null) {
            Double minDistance = null;
            for (Branch b : branches) {
                if (minDistance == null) {
                    minDistance = b.getAccumulatedDistance();
                    branchToReturn = b;
                    continue;
                }
                if (b.getAccumulatedDistance() < minDistance) {
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
                if (b.getAccumulatedDistance() >= shortestGoalBranch.getAccumulatedDistance()) {
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
        if (nodes != null && node != null) {
            Double distance = null;
            for (Node n : nodes) {
                if (n == node) {
                    continue;
                }
                Double d = mGraph.getDistance(n, node);
                if (distance == null) {
                    distance = d;
                    nodeToReturn = n;
                    continue;
                }
                if (d < distance) {
                    distance = d;
                    nodeToReturn = n;
                }
            }
        } else {
            logger.error("getClosestNeighborToNode | node or nodes is null");
        }
        return nodeToReturn;
    }

    public Double getD(List<Node> n, IGraph g) {
        return getDistance(n, g);
    }



    @Override
    public void reset() {

    }
}
