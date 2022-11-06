package de.buw.se4de;

import java.util.Vector;

public class StartUp {
    public static void main(String[] args){//PROBLEM MIT DEN IDS :C
        DBReader dbr = new DBReader("jdbc:h2:./app/src/main/resources/h2User");
        dbr.InitializeDB();
        Vector<User> uservec = new Vector<>();
        dbr.getusers(uservec);
        TerminalMain l = new TerminalMain(uservec,dbr);
        l.Mainloop();
        //Trying stuff//
        //User Q = dbr.insertuser("Quentin");
        //User E = dbr.insertuser("Erik");
        //dbr.inserthabit(new Habit("Jura", Habit.Category.Uni),Q);
        //dbr.inserthabit(new Habit("Feuerwehr", Habit.Category.Sport),E);

        //System.out.println("Done");


        //dbr.inserthabit(new Habit())
        //Trying Stuff//
    }
}
