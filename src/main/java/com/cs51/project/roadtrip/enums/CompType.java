package com.cs51.project.roadtrip.enums;

import com.cs51.project.roadtrip.interfaces.IComparisonService;
import com.cs51.project.roadtrip.services.BasicComparisonService;

/**
 * Comparison Type Enumeration
 * This is where the different types of algorithm comparison modes are defined.
 * The name, description, option char (button to press to run this comparison),
 * and the IComparisonService implementation are all defined here.
 */
public enum CompType {

    BASIC("Basic Comparison", "description", "B", true, new BasicComparisonService());

    private final String name;
    private final String desc;
    private final String optionChar;
    private final boolean shouldPromptNumNodes;
    private final IComparisonService service;

    CompType(String name, String desc, String optionChar, boolean shouldPromptNumNodes, IComparisonService service) {
        this.name = name;
        this.desc = desc;
        this.optionChar = optionChar;
        this.shouldPromptNumNodes = shouldPromptNumNodes;
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

    public IComparisonService getService() {
        return service;
    }
}
