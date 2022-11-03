package de.buw.se4de;

import javax.swing.*;

public class StartUp {


    public static void main(String[] args){
        DBReader dbr = new DBReader("");
        JFrame frame = new MainWindow("Habit Tracker");
        frame.setVisible(true);
        frame.setSize(1920,1080);
    }
}
