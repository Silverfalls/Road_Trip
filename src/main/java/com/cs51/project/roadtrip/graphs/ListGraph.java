package com.cs51.project.roadtrip.graphs; /**
 * Created by Alexander on 16.04.2015.
 */

import com.cs51.project.roadtrip.interfaces.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * A very simple and dumb implementation using a standard List
 */
public class ListGraph implements Graph {
    private List<Node> nodes = new ArrayList<>();

    @Override
    public void initGraph() {
        //TODO buildin some randomness to this

        nodes.add(0,new Node(0, 0, 0, "A",true));
        for (int i = 1; i < 4; i++) {
            String name = String.valueOf((char) ('A' + i));
            Node node = new Node(i, i, i, name, false);
            nodes.add(i,node);
        }
    }

    @Override
    public Node getRandomNeighbor(Node n1) {
        Stream<Node> nodeStream = nodes.stream();

        return nodeStream.filter(n -> n != n1)
                .findAny()
                .orElse(null);
    }

    @Override
    public double getDistance(Node n1, Node n2) {
        int xDif = n2.getxCoord() - n1.getxCoord();
        int yDif = n2.getyCoord() - n2.getyCoord();

        return Math.sqrt(Math.pow(xDif,2) + Math.pow(yDif,2));
    }

    @Override
    public Node getNodeById(long id) {
        Stream<Node> nodeStream = nodes.stream();
        return nodeStream.filter(n -> n.getId() == id)
                .findAny().orElse(null);
    }

    @Override
    public Node getNodeByName(String name) {
        Stream<Node> nodeStream = nodes.stream();
        return nodeStream.filter(n -> n.getName() == name)
                .findAny().orElse(null);
    }
}
