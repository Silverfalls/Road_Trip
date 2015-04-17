package com.cs51.project.roadtrip.enums;

import com.cs51.project.roadtrip.algorithms.BranchAndBoundAlgorithm;
import com.cs51.project.roadtrip.algorithms.BruteForceAlgorithm;
import com.cs51.project.roadtrip.algorithms.NearestNeighborAlgorithm;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;
import org.apache.log4j.Logger;

/**
 * Created by robertschupp on 4/16/15.
 */
public enum AlgType {
    BRUTE_FORCE("Brute Force", "description", new BruteForceAlgorithm()),
    NEAREST_NEIGHBOR("Nearest Neighbor", "description", new NearestNeighborAlgorithm()),
    BRANCH_AND_BOUND("Branch and Bound", "description", new BranchAndBoundAlgorithm());

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(AlgType.class);

    private String name;
    private String desc;
    private IAlgorithm alg;

    AlgType(String name, String desc, IAlgorithm alg) {
        this.name = name;
        this.desc = desc;
        this.alg = alg;
    }

    //TODO
    public IAlgorithm getAlgorithmByType(AlgType type) {

        return null;
    }
}
