package de.buw.se4de;

import javax.swing.*;

public class MainWindow extends JFrame{
    private JPanel mainPanel;

    public MainWindow(String appName){
        super(appName);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setContentPane(mainPanel); ???
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new MainWindow("Habit Tracker");
        frame.setVisible(true);
    }
}
