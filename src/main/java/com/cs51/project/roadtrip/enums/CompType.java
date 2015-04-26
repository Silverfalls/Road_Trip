package com.cs51.project.roadtrip.enums;

import com.cs51.project.roadtrip.interfaces.IComparisonService;
import com.cs51.project.roadtrip.services.ApproximateComparisonService;
import com.cs51.project.roadtrip.services.BasicComparisonService;

/**
 * Comparison Type Enumeration
 * This is where the different types of algorithm comparison modes are defined.
 * The name, description, option char (button to press to run this comparison),
 * and the IComparisonService implementation are all defined here.
 */
public enum CompType {

    BASIC("Basic Comparison", "run one or more algorithms on the same graph and see various metrics", "B", true, false, new BasicComparisonService()),
    BASIC_AVG("Basic Comparison Averaged", "same as basic comparison but with certain results averaged n times", "BA", true, true, new BasicComparisonService()),
    APPROX_COMP("Compare Accuracy", "comparison that uses an exact algorithm as a base and compares the results of one or more non-exact algorithms", "AC", true, true, new ApproximateComparisonService());

    private final String name;
    private final String desc;
    private final String optionChar;
    private final boolean shouldPromptNumNodes;
    private final boolean shouldPromptIterations;
    private final IComparisonService service;

    CompType(String name, String desc, String optionChar, boolean shouldPromptNumNodes,
             boolean shouldPromptIterations, IComparisonService service) {
        this.name = name;
        this.desc = desc;
        this.optionChar = optionChar;
        this.shouldPromptNumNodes = shouldPromptNumNodes;
        this.shouldPromptIterations = shouldPromptIterations;
        this.service = service;
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

    public boolean shouldPromptNumNodes() { return shouldPromptNumNodes; }

    public boolean shouldPromptIterations() { return shouldPromptIterations; }

    public IComparisonService getService() {
        return service;
    }
}
