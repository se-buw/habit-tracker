package de.buw.se4de;

import javax.swing.*;

public class MainWindow extends JFrame{
    private static JPanel mainPanel;

    public MainWindow(String appName){
        super(appName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setContentPane(mainPanel); ???
        this.pack();
    }

}
