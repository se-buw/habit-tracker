package de.buw.se4de;

import java.util.Date;//sql class also possible
import java.util.Vector;

import static de.buw.se4de.Habit.Cycle.NONE;

public class Habit {

    public String name;
    public String description;
    public Date startdate;
    public Category category;
    public int habitid;
    public Cycle cycle;

    public Vector<Days> workdays;
    //a day gets inserted with ex= false, sunday being 1 and Saturday 7, no starting with 0 :(


    public Habit(String n,String d,Category c,Cycle cycvec){
        name = n;
        description = d;
        category = c;
        startdate = new Date();
        habitid = -1;
        cycle = cycvec;
        workdays = new Vector<>();
    }

    public Habit(String n,String d,Category c, Vector<Days> daysvec){
        name = n;
        description = d;
        category = c;
        startdate = new Date();
        habitid = -1;
        cycle = NONE;
        workdays = daysvec;
    }
    public Habit(int id,String n,String d,Category c,Vector<Days> daysvec){
        habitid = id;
        name = n;
        description = d;
        category = c;
        startdate = new Date();
        workdays = daysvec;
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
        workdays = new Vector<>();
    }

    public Habit(int id,String n,String d,Date sd,Vector<Days> daysvec,Category c){
        name = n;
        description = d;
        category = c;
        startdate = sd;
        cycle = NONE;
        habitid = id;
        workdays = daysvec;
    }
    enum Category{
        Meditation,Studium,Kunst,Sport,Unterhaltung,Soziales,Finanzen,Gesundheit,Arbeit,Ernaehrung,Zuhause,ImFreien,Anderes
    }
    enum Cycle{
        NONE("none",0), ONE_PER_WEEK("One time per week",1),TWO_PER_WEEK("Two times per week",2),
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

    enum Days{
        MONDAY("Monday", false, 2), TUESDAY("Tuesday", false, 3),
        WEDNESDAY("Wednesday", false, 4), THURSDAY("Thursday", false, 5),
        FRIDAY("Friday", false, 6), SATURDAY("Saturday", false, 7), SUNDAY("Sunday", false, 1);

        public final String value;

        public final int dayofweek;
        public boolean ex;


        Days(String v, boolean i, int f){
            value = v;
            ex = i;
            dayofweek = f;
        }
    }

    @Override
    public String toString(){
        return habitid + ": " + name;
    }

    public static String print(Vector<Days> v) {
        StringBuilder string = new StringBuilder();
        for(Days d : v){
            string.append(d.name()).append("/");}

        return string.toString();
    }
}




