package com.cs51.project.roadtrip.graphs;

import com.cs51.project.roadtrip.interfaces.IGraph;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by robertschupp on 4/17/15.
 */
public class ListGraphTest {

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(ListGraphTest.class);

    @Test
    public void performance() {
        for (int i = 0; i < 25; i++) {
            IGraph graph = new ListGraph(40);
        }
    }


}
