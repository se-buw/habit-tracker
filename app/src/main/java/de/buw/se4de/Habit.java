package de.buw.se4de;


import java.util.Date;//sql class also possible

public class Habit {

    public String name;
    public String description = "";
    public Date startdate;
    public Category category;
    public int habitid;
    public float cycle;

    public Habit(String n,String d,Category c){
        name = n;
        description = d;
        category = c;
        startdate = new Date();
        habitid = -1;
    }

    public Habit(int id,String n,String d,Date sd,float cyc,Category c){
        name = n;
        description = d;
        category = c;
        startdate = sd;
        cycle = cyc;
        habitid = id;//TODO add Cycle
    }
    enum Category{
        Sport,Uni,Arbeit
    }
}
