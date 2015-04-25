package com.cs51.project.roadtrip.common.utils;

import com.cs51.project.roadtrip.graphs.Node;

import java.util.List;

/**
 * Created by robertschupp on 4/25/15.
 */
public class RoadTripUtils {

    public static String convertListToPath(List<Node> nodes) {

        StringBuilder sb = new StringBuilder();

        int i = 0;
        int size = nodes.size();
        for (Node node : nodes) {
            sb.append(node.getName());
            if (i < size - 1) {
                sb.append(" -> ");
            }
            i++;
        }

        return sb.toString();
    }

}
