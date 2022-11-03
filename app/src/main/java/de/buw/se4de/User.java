package de.buw.se4de;

import java.util.Vector;

public class User {
    int uid;
    String username;
    Vector<Habit> habitvec;

    public User(String un){
        this.uid = getnewid();
        this.username = un;
    }

    public static int gethabbitid() {
        return 0;
    }

    public void checkstats(){
        //TODO öffnet fenster für stats

    }
    public boolean addhabit(Habit h,DBReader dbr){
        //TODO return false if exception true if it works, adds habit to DB
        if (dbr.inserthabit(h)){
            return true;
        }
        return false;
    }
    public boolean removehabbit(int id){
        //TODO return false if exception true if it works, removes habit from DB
        return true;
    }

    public boolean checkoffhabit(){
        //TODO ÄMMELIE MACHT DAS SCHON DW
        return true;
    }
    private int getnewid(){
        return 0;//TODO id zuweisung
    }

}
