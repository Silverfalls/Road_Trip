package com.cs51.project.roadtrip;

import com.cs51.project.roadtrip.interfaces.IUserInterface;
import com.cs51.project.roadtrip.uis.CommandLineUI;

/**
 * Created by robertschupp on 4/16/15.
 */
public class Main {

    public static void main (String [] args) {
        IUserInterface commandUI = new CommandLineUI();
//        commandUI.initialize();
        commandUI.execute();
    }

}
