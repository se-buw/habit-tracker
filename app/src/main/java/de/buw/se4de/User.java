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

    public void addhabit(Habit h){
        habitvec.add(h);
    }

    @Override
    public String toString(){
        return uid + ": " + username;
    }
}
