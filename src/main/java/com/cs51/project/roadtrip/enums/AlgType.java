package com.cs51.project.roadtrip.enums;

import com.cs51.project.roadtrip.algorithms.ListBranchAndBoundAlgorithm;
import com.cs51.project.roadtrip.algorithms.BruteForceAlgorithm;
import com.cs51.project.roadtrip.algorithms.NearestNeighborAlgorithm;
import com.cs51.project.roadtrip.interfaces.IAlgorithm;

/**
 * TODO java doc needs filled out
 */
public enum AlgType {
    BRUTE_FORCE("Brute Force", "description", "A", new BruteForceAlgorithm()),
    NEAREST_NEIGHBOR("Nearest Neighbor", "description", "B", new NearestNeighborAlgorithm()),
    BRANCH_AND_BOUND_LIST("Branch and Bound (List Based)", "description", "C", new ListBranchAndBoundAlgorithm()),
    BRANCH_AND_BOUND_TREE("Branch and Bound (Tree Based)", "description", "D", new ListBranchAndBoundAlgorithm());

    private final String name;
    private final String desc;
    private final String optionChar;
    private final IAlgorithm alg;

    AlgType(String name, String desc, String optionChar, IAlgorithm alg) {
        this.name = name;
        this.desc = desc;
        this.optionChar = optionChar;
        this.alg = alg;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getOptionChar() {
        return optionChar;
    }

    public IAlgorithm getAlg() {
        return alg;
    }
}
