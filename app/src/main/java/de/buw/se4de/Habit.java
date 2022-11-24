package de.buw.se4de;

import java.util.Date;//sql class also possible

public class Habit {

    public String name;
    public String description;
    public Date startdate;
    public Category category;
    public int habitid;
    public Cycle cycle;


    public Habit(String n,String d,Category c,Cycle cycvec){
        name = n;
        description = d;
        category = c;
        startdate = new Date();
        habitid = -1;
        cycle = cycvec;
    }
    public Habit(int id,String n,String d,Category c,Cycle cycvec){
        habitid = id;
        name = n;
        description = d;
        category = c;
        startdate = new Date();
        cycle = cycvec;
    }

    public Habit(int id,String n,String d,Date sd,Cycle cyc,Category c){
        name = n;
        description = d;
        category = c;
        startdate = sd;
        cycle = cyc;
        habitid = id;
    }
    enum Category{
        Meditation,Studium,Kunst,Sport,Unterhaltung,Soziales,Finanzen,Gesundheit,Arbeit,Ernaehrung,Zuhause,ImFreien,Anderes
    }
    enum Cycle{
        ONE_PER_WEEK("One time per week",1),TWO_PER_WEEK("Two times per week",2),
        THREE_PER_WEEK("Three times per week",3),FOUR_PER_WEEK("Four times per week",4),
        FIVE_PER_WEEK("Five times per week",5),SIX_PER_WEEK("Six times per week",6),
        SEVEN_PER_WEEK("Seven times per week",7);
        public final String value;
        public final int amount;
        @Override
        public String toString(){
            return value;
        }
        Cycle(String v,int a){
            value = v;
            amount = a;
        }
    }
    @Override
    public String toString(){
        return habitid + ": " + name;
    }
}


