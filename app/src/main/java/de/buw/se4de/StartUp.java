package de.buw.se4de;

import javax.swing.*;

public class StartUp {


    public static void main(String[] args){
        DBReader dbr = new DBReader("");
        dbr.gethabits();
        new MainWindow("Habit Tracker");
    }
}
