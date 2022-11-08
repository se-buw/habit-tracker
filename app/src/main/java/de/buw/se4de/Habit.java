package de.buw.se4de;


import org.h2.util.StringUtils;

import java.util.Date;//sql class also possible
import java.util.Vector;

public class Habit {

    public String name;
    public String description = "";
    public Date startdate;
    public Category category;
    public int habitid;
    public Vector<Cycle> cycle;

    public Habit(String n,String d,Category c){
        name = n;
        description = d;
        category = c;
        startdate = new Date();
        habitid = -1;
    }

    public Habit(int id,String n,String d,Date sd,Vector<Cycle> cyc,Category c){
        name = n;
        description = d;
        category = c;
        startdate = sd;
        cycle = cyc;
        habitid = id;//TODO add Cycle
    }
    enum Category{
        Meditation,Studium,Kunst,Sport,Unterhaltung,Soziales,Finanzen,Gesundheit,Arbeit,Ernährung,Zuhause,ImFreien,Anders//TODO weitere Enums(Nutzerdefiniert)
    }
    enum Cycle{
        ONE_PER_WEEK("One time per week"),TWO_PER_WEEK("Two times per week"),
        THREE_PER_WEEK("Three times per week"),FOUR_PER_WEEK("Four times per week"),
        FIVE_PER_WEEK("Five times per week"),SIX_PER_WEEK("Six times per week"),
        SEVEN_PER_WEEK("Seven times per week"),MONDAY("1"),
        THUESDAY("2"),WEDNESDAY("3"),THURSDAY("4"),
        FRYDAY("5"),SATURDAY("6"),SUNDAY("7");
        public final String value;
        private Cycle(String v){
            value = v;
        }
    }

}
