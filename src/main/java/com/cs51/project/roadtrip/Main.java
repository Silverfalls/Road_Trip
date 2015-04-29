package com.cs51.project.roadtrip;

import com.cs51.project.roadtrip.interfaces.IUserInterface;
import com.cs51.project.roadtrip.uis.CommandLineUI;
import org.apache.log4j.Logger;

/**
 * Starting point for the Road Trip program
 */
class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    public static void main (String [] args) {
        IUserInterface commandUI = new CommandLineUI();
        commandUI.execute();
    }

}
