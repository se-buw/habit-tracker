package de.buw.se4de;

import java.util.Vector;

public class User {
    public int uid;
    public String username;
    Vector<Habit> habitvec;

    public User(int id, String un){
        //only called after inserting it into DB or retrieving from db
        username = un;
        uid = id;
        habitvec = new Vector<>();
    }

    public static int gethabbitid() {
        return 0;
    }

    public void checkstats(){
        //TODO öffnet fenster für stats

    }
    public void addhabit(Habit h){
        habitvec.add(h);
    }
    public void removehabbit(int id){
        //TODO return false if exception true if it works, removes habit from DB
    }

    public void checkoffhabit(){
        //TODO ÄMMELIE MACHT DAS SCHON DW
    }
    private int getnewid(){
        return 0;//TODO id zuweisung
    }

}
