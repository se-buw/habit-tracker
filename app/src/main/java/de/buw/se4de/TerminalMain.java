package de.buw.se4de;

import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

public class TerminalMain {
    /*
    Vector<User> Users;
    DBReader dbr;
    User currentuser = new User(-1,"");
    Scanner myObj = new Scanner(System.in);
    TerminalMain(Vector<User> U, DBReader d){
        Users = U;
        dbr = d;
    }


    public void Mainloop() {
        while (true){
            System.out.println("What do you wanna do ?");
            String dothisactionpls = myObj.nextLine();
            String name;
            switch (dothisactionpls){
                case "New User":
                    System.out.println("How do you want to be called?");
                    name = myObj.nextLine();
                    Users.add(dbr.insertuser(name));
                    break;
                case "Swap User":
                    System.out.println("What User do you choose?");
                    if(Users.size() == 0)
                        break;
                    for(User u: Users) {
                        System.out.printf("%s,",u.username);
                    }
                    System.out.print("\n");
                    name = myObj.nextLine();
                    for(User u: Users){
                        if (u.username.equals(name)){
                            currentuser = u;
                        }
                    }
                    break;
                case "New Habit":
                    if(currentuser.uid > -1) {
                        System.out.println("Habit Name:");
                        name = myObj.nextLine();
                        System.out.println("Habit description:");
                        String desc = myObj.nextLine();
                        //TODO: make the enum work with strings (maybe custom enums ?)
                        Habit h = new Habit(name,desc, Habit.Category.Uni);
                        if(dbr.inserthabit(h,currentuser))
                            currentuser.habitvec.add(h);
                        else{
                            System.out.println("Something went wrong please try again");
                        }
                    }
                    break;
                case "Track Habit":
                    if(currentuser.uid == -1){
                        break;
                    }
                    System.out.println("Which Habitid do you want to track? (Give id)");
                    for (Habit h:currentuser.habitvec)
                        System.out.printf("(%d, %s),",h.habitid,h.name);
                    System.out.print("\n");
                    String id = myObj.nextLine();
                    for (Habit h:currentuser.habitvec)
                        if(h.habitid == Integer.parseInt(id)) {
                            dbr.trackhabit(h,new Date());
                        }

                    break;
                case "Delete Habit":
                    if(currentuser.uid == -1){
                        break;
                    }
                    System.out.println("Which Habit do you want to Delete? (Give id)");
                    for (Habit h:currentuser.habitvec)
                        System.out.printf("(%d, %s)",h.habitid,h.name);
                    System.out.print("\n");
                    id = myObj.nextLine();
                    for (Habit h:currentuser.habitvec)
                        if(h.habitid == Integer.parseInt(id)) {
                            dbr.deletehabit(h);
                            currentuser.habitvec.remove(h);
                            break;
                        }

                    break;
                case "Delete User":
                    System.out.println("Which User do you want to Delete?");
                    if(Users.size() == 0)
                        break;
                    for(User u: Users) {
                        System.out.printf("%s,",u.username);
                    }
                    System.out.print("\n");
                    name = myObj.nextLine();
                    for(User u: Users){
                        if (u.username.equals(name)){
                            if(currentuser.username.equals(name))
                                currentuser = new User(-1,"");
                            dbr.deleteuser(u);
                            Users.remove(u);
                            break;
                        }
                    }
                    break;
                case "Current User":
                    System.out.println(currentuser.username);
                    break;
                case "Show Dates":
                    if(currentuser.uid == -1){
                        break;
                    }
                    Vector<Date> dvec = new Vector<>();
                    System.out.println("Which Habit do you want to See? (Give id)");
                    for (Habit h:currentuser.habitvec)
                        System.out.printf("(%d, %s)",h.habitid,h.name);
                    System.out.print("\n");
                    id = myObj.nextLine();
                    for (Habit h:currentuser.habitvec)
                        if(h.habitid == Integer.parseInt(id)) {
                            dvec = dbr.getdates(h);
                        }
                    for (Date d: dvec){
                        System.out.printf("%s,",d.toString());
                    }
                    System.out.print("\n");
                case "Exit":
                    return;
            }
        }
    }
*/
}
