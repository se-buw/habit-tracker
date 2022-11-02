package de.buw.se4de;


import java.util.Date;//sql class also possible

abstract public class Habit {
    String name;
    String description = "";
    Date startdate;
    Category category;
    int id;
    public Habit(String n,Category c){
        this.name = n;
        this.category = c;
        this.startdate = new Date();
        id = User.gethabbitid();
    }

    public Habit(String n,String d,Category c){
        this.name = n;
        this.description = d;
        this.category = c;
        this.startdate = new Date();
        id = User.gethabbitid();
    }

    //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    //    Date date = new Date();
    //    System.out.println(formatter.format(date));

    enum Category{
        Sport,Uni,Arbeit,
    }
}
