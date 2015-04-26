package com.cs51.project.roadtrip.algorithms;

import com.cs51.project.roadtrip.algorithms.base.BaseAlgorithm;
import com.cs51.project.roadtrip.common.dto.Result;
import com.cs51.project.roadtrip.enums.AlgType;
import com.cs51.project.roadtrip.graphs.Node;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

/**
 * Tree Based implementation of the branch and bound algorithm
 */

public class TreeBranchAndBoundAlgorithm extends BaseAlgorithm implements IAlgorithm {

    private static Logger logger = Logger.getLogger(TreeBranchAndBoundAlgorithm.class);

    private List<Node> nodesList = null;

    private IGraph graph = null;

    private long iterations = 0;

    private class TreeNode {
        private BigDecimal distance = BigDecimal.ZERO;
        private TreeNode parent = null;
        private Node node = null;
        private List<Node> unvisited = new LinkedList<>();
        private List<Node> visited = new LinkedList<>();
        private boolean complete = false;

        public TreeNode(Node node, TreeNode parent){
            this.node = node;
            this.parent = parent;
            if (parent != null){
                distance = calculateDistance(parent);
                visited = calculateVisited(parent);
                unvisited = calculateUnvisited();
            }
            else{
                unvisited.addAll(nodesList);
                unvisited.sort((uvNode1, uvNode2) -> graph.getDistance(node, uvNode1).compareTo(graph.getDistance(node, uvNode2)));
            }
        }

        private List<Node> calculateUnvisited() {
            LinkedList<Node> unvisitedNodes = new LinkedList<>(nodesList);
            unvisitedNodes.removeAll(visited);
            unvisitedNodes.sort((uvNode1, uvNode2) -> graph.getDistance(node,uvNode1).compareTo(graph.getDistance(node,uvNode2)));
            return unvisitedNodes;
        }

        public TreeNode getParent (){
            return parent;
        }

        public BigDecimal getDistance(){
            return distance;
        }

        public Node getNode(){
            return node;
        }

        public List<Node> getVisited(){
            return visited;
        }

        private List<Node> calculateVisited(TreeNode parent) {

            List<Node> visitedNodes = new LinkedList<>(parent.getVisited());
            visitedNodes.add(node);
            if (visitedNodes.containsAll(nodesList)){
                setComplete(true);
            }

            return visitedNodes;
        }

        private BigDecimal calculateDistance(TreeNode parent) {

            BigDecimal parentDistance = parent.getDistance();
            Node parentNode = parent.node;

            return distance.add(graph.getDistance(node, parentNode)).add(parentDistance);
        }

        public Node getShortestUnvisited(){
            Node shortestNode;
            if (unvisited.isEmpty()){
                shortestNode = null;
            }
            else{
                shortestNode = unvisited.get(0);
                unvisited.remove(0);
            }
            return shortestNode;
        }

        public boolean isComplete() {
            return complete;
        }

        public void setComplete(boolean complete) {
            this.complete = complete;
        }

        public LinkedList<Node> getPath() {
            LinkedList<Node> path = new LinkedList<>();
            path.addFirst(node);
            TreeNode parent = getParent();
            do{
                path.addFirst(parent.getNode());
                parent = parent.getParent();
            }while (parent != null);

            return path;
        }
    }

    @Override
    public Result execute(IGraph graph) {
        if (logger.isDebugEnabled()) {
            logger.debug("execute | start...");
        }

        long startTime = System.currentTimeMillis();
        BigDecimal bound = null;
        LinkedList<Node> currentOptimal;
        this.graph = graph;
        Node startingNode = graph.getStartingNode();

        nodesList = graph.getListOfNodes();
        nodesList.remove(startingNode);
        TreeNode startingTreeNode = new TreeNode(startingNode, null);

        PriorityQueue<TreeNode> bottomNodes = new PriorityQueue<>((distance1, distance2) -> distance1.getDistance().compareTo(distance2.getDistance()));

        bottomNodes.add(startingTreeNode);
        TreeNode bestNode = startingTreeNode;
        do {
            iterations++;
            TreeNode shortest = bottomNodes.peek();
            if (bound != null && (shortest.getDistance().compareTo(bound) == 0)) {
                break;
            }

            Node shortestUnvisited = shortest.getShortestUnvisited();
            TreeNode bottomNode;

            if (shortestUnvisited == null) {
                if (shortest.isComplete()){
                    bottomNode = new TreeNode(startingNode, shortest);

                    if (bound!= null){
                        if (bound.compareTo(bottomNode.getDistance()) >= 0){
                            bestNode = bottomNode;
                            bound = bottomNode.getDistance();

                            PriorityQueue<TreeNode> nBottomNodes = new PriorityQueue<>((distance1, distance2) -> distance1.getDistance().compareTo(distance2.getDistance()));
                            for (TreeNode tn : bottomNodes) {
                                if (tn.getDistance().compareTo(bestNode.getDistance()) == -1) {
                                    nBottomNodes.add(tn);
                                } else {
                                    break;
                                }
                            }
                            bottomNodes = nBottomNodes;
                        }
                    }
                    else {
                        bestNode = bottomNode;
                        bound = bottomNode.getDistance();

                        PriorityQueue<TreeNode> nBottomNodes = new PriorityQueue<>((distance1, distance2) -> distance1.getDistance().compareTo(distance2.getDistance()));
                        for (TreeNode tn : bottomNodes) {
                            if (tn.getDistance().compareTo(bestNode.getDistance()) == -1) {
                                nBottomNodes.add(tn);
                            } else {
                                break;
                            }
                        }
                        bottomNodes = nBottomNodes;
                    }
                }
                bottomNodes.remove(shortest);
            }
            else{
                bottomNode = new TreeNode(shortestUnvisited, shortest);
                bottomNodes.add(bottomNode);
            }

        } while (!bottomNodes.isEmpty());

        currentOptimal = bestNode.getPath();

        Result result = new Result();

        result.setName(AlgType.BRANCH_AND_BOUND_TREE.getName());
        result.setRunningTime(System.currentTimeMillis() - startTime);
        result.setCalculatedDistance(getDistance(currentOptimal, graph));
        result.setCalculatedPath(currentOptimal);
        result.setGraphSize(graph.getGraphSize());
        result.setFinished(true);
        result.setIterations(iterations);
        iterations = 0;

        if (logger.isDebugEnabled()) {
            logger.debug("execute | end...");
        }

        return result;
    }

    @Override
    public void reset() {
    }
}
