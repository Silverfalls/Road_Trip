package com.cs51.project.roadtrip.enums;

import com.cs51.project.roadtrip.algorithms.BranchAndBoundAlgorithm;
import com.cs51.project.roadtrip.algorithms.BruteForceAlgorithm;
import com.cs51.project.roadtrip.algorithms.NearestNeighborAlgorithm;
import com.cs51.project.roadtrip.interfaces.Algorithm;

/**
 * Created by robertschupp on 4/16/15.
 */
public enum AlgorithmType {
    BRUTE_FORCE("Brute Force", "description", new BruteForceAlgorithm()),
    NEAREST_NEIGHBOR("Nearest Neighbor", "description", new NearestNeighborAlgorithm()),
    BRANCH_AND_BOUND("Branch and Bound", "description", new BranchAndBoundAlgorithm());

    private String name;
    private String desc;
    private Algorithm alg;

    AlgorithmType(String name, String desc, Algorithm alg) {
        this.name = name;
        this.desc = desc;
        this.alg = alg;
    }

    //TODO
    public Algorithm getAlgorithmByType(AlgorithmType type) {

        return null;
    }
}
