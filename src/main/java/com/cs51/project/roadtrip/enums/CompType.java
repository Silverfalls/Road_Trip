package com.cs51.project.roadtrip.enums;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 14.04.2015.
 */
public enum CompType {

    BASIC("Basic Comparison", "TODO description of basic comparison");

    /**
     * Logger Instance
     */
    private static Logger logger = Logger.getLogger(CompType.class);

    //we will implement this if we have time
//    RACE("Race Comparison", "TODO description");

    private final String name;
    private final String desc;

    CompType(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    private String getName() { return name; }
    private String getDesc() { return desc; }

    //todo add javadoc
    public String getDescriptionByType(CompType type){
        //TODO consider changing this using streams and lambdas
        for (CompType t : CompType.values()) {
            if (t == type) {
                return t.getDesc();
            }
        }
        return null;
    }

    //TODO add javadoc
    public String getDescriptionByName(String name){
        //TODO also consider streams and lambdas
        for (CompType t: CompType.values()) {
            if (t.getName().equals(name)) {
                return t.getDesc();
            }
        }
        return null;
    }

    //TODO add javadoc
    public List<CompType> getAllTypes() {
        List<CompType> types = new ArrayList<>();
        for (CompType type : CompType.values()) {
            types.add(type);
        }
        return types;
    }

}
