package de.buw.se4de;

import java.util.Vector;

public class StartUp {
    public static void main(String[] args){//PROBLEM MIT DEN IDS :C
        DBReader dbr = new DBReader("jdbc:h2:./src/main/resources/h2User");
        dbr.InitializeDB();
        Vector<User> uservec = new Vector<>();
        dbr.getusers(uservec);
        //System.out.println("Done");
        new MainWindow("Habit Tracker",uservec,dbr);

    }
}
