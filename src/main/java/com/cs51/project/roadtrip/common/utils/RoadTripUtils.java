package com.cs51.project.roadtrip.common.utils;

import com.cs51.project.roadtrip.common.constants.RoadTripConstants;
import com.cs51.project.roadtrip.graphs.Node;

import java.util.List;

/**
 * A utility class for functionality that may be helpful around the program.. basically a 'service' that RoadTrip offers
 * to objects
 */
public class RoadTripUtils {

    public static String convertListToPath(List<Node> nodes) {

        StringBuilder sb = new StringBuilder();

        int i = 0;
        int size = nodes.size();
        for (Node node : nodes) {
            sb.append(node.getName());
            if (i < size - 1) {
                sb.append(RoadTripConstants.TOUR_PATH_SEPARATOR);
            }
            i++;
        }

        return sb.toString();
    }

}
