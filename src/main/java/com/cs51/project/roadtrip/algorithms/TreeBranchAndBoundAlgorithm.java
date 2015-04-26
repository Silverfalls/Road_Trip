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
 * Created by Alexander on 26.04.2015.
 */

public class TreeBranchAndBoundAlgorithm extends BaseAlgorithm implements IAlgorithm {

    private static Logger logger = Logger.getLogger(TreeBranchAndBoundAlgorithm.class);

    private Result result = null;

    private List<Node> nodesList = null;

    private Node startingNode = null;

    private IGraph graph = null;

    private BigDecimal shortestPathDistance = BigDecimal.ZERO;

    private List<Node> shortestPath = null;

    private long iterations = 0;

    private class TreeNode {
        private BigDecimal distance = BigDecimal.ZERO;
        private TreeNode parent = null;
        private Node node = null;
        private List<Node> unvisited = new LinkedList<Node>();
        private List<Node> visited = new LinkedList<Node>();
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
            LinkedList<Node> unvisitedNodes = new LinkedList<Node>(nodesList);
            unvisitedNodes.removeAll(visited);
            unvisitedNodes.sort((uvNode1, uvNode2) -> graph.getDistance(node,uvNode1).compareTo(graph.getDistance(node,uvNode2)));
            return unvisitedNodes;
        }

        public TreeNode getParent (){
            return parent;
        }

        public void setVisited(Node n){
            unvisited.remove(n);
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

            List<Node> visitedNodes = new LinkedList<Node>(parent.getVisited());
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
        long startTime = System.currentTimeMillis();

        if (logger.isDebugEnabled()) {
            logger.debug("start to execute BruteForceAlgorithm");
        }

        BigDecimal bound = null;
        LinkedList<Node> currentOptimal = null;

        this.graph = graph;
        startingNode = graph.getStartingNode();

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
                            final BigDecimal currentBound = bound;
                            Iterator iterator = bottomNodes.iterator();
                            while (iterator.hasNext()) {
                                TreeNode b = (TreeNode) iterator.next();
                                if (b.getDistance().compareTo(bestNode.getDistance()) >= 0) {
                                    iterator.remove();
                                }
                            }

                            /*
                            bottomNodes = new PriorityQueue<>((distance1, distance2) -> distance1.getDistance().compareTo(distance2.getDistance()));
                            bottomNodes.addAll(bottomNodes.stream().filter(tn -> tn.getDistance().compareTo(currentBound)> 0).collect(Collectors.toList()));*/
                        }
                    }
                    else {
                        bestNode = bottomNode;
                        bound = bottomNode.getDistance();
                        final BigDecimal currentBound = bound;

                        Iterator iterator = bottomNodes.iterator();
                        while (iterator.hasNext()) {
                            TreeNode b = (TreeNode) iterator.next();
                            if (b.getDistance().compareTo(bestNode.getDistance()) >= 0) {
                                iterator.remove();
                            }
                        }
                        /*
                        bottomNodes = new PriorityQueue<>((distance1, distance2) -> distance1.getDistance().compareTo(distance2.getDistance()));
                        bottomNodes.addAll(bottomNodes.stream().filter(tn -> tn.getDistance().compareTo(currentBound)> 0).collect(Collectors.toList()));*/
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
        shortestPathDistance = getDistance(currentOptimal, graph);

        result = new Result();

        result.setName(AlgType.BRANCH_AND_BOUND_TREE.getName());
        result.setRunningTime(System.currentTimeMillis() - startTime);
        result.setCalculatedDistance(shortestPathDistance);
        result.setCalculatedPath(currentOptimal);
        result.setGraphSize(graph.getGraphSize());
        result.setFinished(true);
        result.setIterations(iterations);

        return result;
    }

    @Override
    public void reset() {

    }
}
