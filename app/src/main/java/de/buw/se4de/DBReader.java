package de.buw.se4de;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Date;
import java.util.Vector;

public class DBReader {
    Connection DBconnection;
    Statement stmt;
    public boolean connected;
    public DBReader(String connpath){
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(connpath, "", "");
            DBconnection = conn;
            stmt = conn.createStatement();
            connected = true;
        }catch (SQLException sqlE){
            connected = false;
            System.out.println("Fehler, Programm kann sich nicht mit der Datenbank verbinden");
        }catch (ClassNotFoundException classE){
            connected = false;
            System.out.println("Fehler, can't initialize \"org.h2.Driver\" ");
        }
    }
    public void InitializeDB() {
        String createUserDB = "CREATE TABLE IF NOT EXISTS USERDB" + "(Userid INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL, Username VARCHAR(255))";
        String createHabitDB = "CREATE TABLE IF NOT EXISTS HABITDB " + "( Habitid INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL,Userid INT ,Habitname VARCHAR(255),Habitdescription TEXT(500) ,Startdate DATE,Habitcycle VARCHAR(255),Habitcategory VARCHAR(255))";
        String createTrackerDB = "CREATE TABLE IF NOT EXISTS TRACKERDB" + "(Habitid INT, Donedate DATE)";
        try {
            stmt.executeUpdate(createUserDB);
            stmt.executeUpdate(createHabitDB);
            stmt.executeUpdate(createTrackerDB);
        }catch (SQLException e){
            System.out.printf("Cannot Initialize the Databases: %d ",e.getErrorCode());
        }

    }
    public void getusers(Vector<User> uservec) {
        String getuserquery = "Select * From USERDB";
        try {
            ResultSet selectRS = stmt.executeQuery(getuserquery);
            while (selectRS.next())
                uservec.add(new User(selectRS.getInt("Userid"), selectRS.getString("Username")));
            for (User u : uservec){
                Vector<Habit> hv= gethabits(u);
                for(Habit h : hv){
                    u.addhabit(h);
                }
            }
        }catch (SQLException e){
            System.out.printf("Cannot retrieve Users from Database: %d\n",e.getErrorCode());
        }
    }
    public Vector<Habit> gethabits(User u) {
        Vector<Habit> habvec= new Vector<>();
        String gethabitsquery = "Select * FROM HABITDB WHERE Userid = " + u.uid;
        Vector<Habit.Days> dayvec = new Vector<>();
        try {
            ResultSet selectH = stmt.executeQuery(gethabitsquery);
            while (selectH.next()) {
                if(check(selectH.getString("Habitcycle"))){
                    String string = selectH.getString("Habitcycle");
                    String[] array = string.split("/");
                    for(String s : array){dayvec.add(Habit.Days.valueOf(s));}
                    habvec.add(new Habit(selectH.getInt("Habitid"), selectH.getString("Habitname"),
                            selectH.getString("Habitdescription"), selectH.getDate("Startdate"),
                            dayvec, Habit.Category.valueOf(selectH.getString("Habitcategory"))));
                }

                else{
                habvec.add(new Habit(selectH.getInt("Habitid"), selectH.getString("Habitname"),
                        selectH.getString("Habitdescription"), selectH.getDate("Startdate"),
                       Habit.Cycle.valueOf(selectH.getString("Habitcycle")), Habit.Category.valueOf(selectH.getString("Habitcategory"))));
                dayvec.clear();
                }
            }
        }catch(SQLException e){
            System.out.printf("Cannot retrieve habits of User %s: %d",u.username,e.getErrorCode());
        }
        return habvec;
    }
    public void inserthabit(@NotNull Habit h, @NotNull User u){
        String chosen = "";
        if(h.workdays.size() == 0){chosen = h.cycle.name();}
        else {chosen = Habit.print(h.workdays);}
         String inserthabit = "INSERT INTO HabitDB (Userid,Habitname,Habitdescription,Startdate,Habitcycle,Habitcategory)"
                 + "VALUES("+ u.uid +",'" + h.name + "','" + h.description + "','"+ new java.sql.Date(h.startdate.getTime()) + "','" + chosen + "','" + h.category.name() + "')";
         String gethabtidid = "Select Habitid FROM HABITDB WHERE Habitname = '"+ h.name  +"' ORDER BY Userid DESC Limit 1";
         try{
             stmt.executeUpdate(inserthabit);
             ResultSet habitid = stmt.executeQuery(gethabtidid);
             habitid.next();
             h.habitid = habitid.getInt("Habitid");
         }catch (SQLException e){
             System.out.printf("Cannot insert Habit: %d\n",e.getErrorCode());
         }
     }
     //changes name only after restart
    public void changehabit(@NotNull Habit h, @NotNull User u){
        String updatehabit = "UPDATE HabitDB SET Userid = "+u.uid+", Habitname='"+h.name+
                "', Habitdescription='"+h.description+ "',Habitcycle='"
                +h.cycle.name()+"', Habitcategory='"+h.category.name()+"' WHERE Habitid="+h.habitid+"";
        try{
            stmt.executeUpdate(updatehabit);
        }catch (SQLException e){
            System.out.printf("Cannot update Habit: %d\n",e.getErrorCode());
        }
    }
    public User insertuser(String username){
        String insertuser = "INSERT INTO USERDB (Username) VALUES('"+ username +"')";
        try{
            stmt.executeUpdate(insertuser);
            String getuserquery = "Select Userid FROM USERDB WHERE Username = '"
                    + username +"' ORDER BY Userid DESC Limit 1";//BESSERE IDEE ?
            ResultSet h2userid = stmt.executeQuery(getuserquery);
            h2userid.next();
            return new User(h2userid.getInt("Userid"),username);
        }catch (SQLException e){
            System.out.printf("Cannot insert User: %d\n",e.getErrorCode());
            return new User(-1,"NULL");
        }
    }
    public void deletehabit(Habit h){
         String query = "DELETE FROM HABITDB WHERE Habitid = "+ h.habitid;
         String query2 = "DELETE FROM TRACKERDB WHERE Habitid = "+ h.habitid;
        try {
            stmt.executeUpdate(query);
            stmt.executeUpdate(query2);
         }catch (SQLException e){
             System.out.printf("Cannot Delete Habit: %d\n",e.getErrorCode());
         }
     }
    public void deleteuser(User u){
        String duquery = "DELETE FROM USERDB WHERE Userid ="+ u.uid;
        String duhquery = "DELETE FROM HABITDB WHERE Userid = " + u.uid;
        String fhabitquery = "SELECT Habitid FROM HABITDB Where Userid = " + u.uid;
        String dh = "DELETE FROM TRACKERDB WHERE Habitid =";
        try{
            stmt.executeUpdate(duquery);
            ResultSet habiids = stmt.executeQuery(fhabitquery);
            Vector<Integer> ids = new Vector<>();
            while (habiids.next()){
                ids.add(habiids.getInt("Habitid"));
            }
            stmt.executeUpdate(duhquery);
            for(int i:ids) {
                String temp = dh + i;
                stmt.executeUpdate(temp);
            }

        }catch(SQLException e){
            System.out.printf("Cannot Delete User/Habit/Tracker: %d\n",e.getErrorCode());
        }
     }
    public void trackhabit(Habit h, Date d){
        String query = "INSERT INTO TRACKERDB (HABITID,DONEDATE) VALUES(" + h.habitid + ",'" + new java.sql.Date(d.getTime()) + "')";
        try {
            stmt.executeUpdate(query);
        }catch (SQLException e){
            System.out.printf("Cannot Insert Tracked Habit: %d\n",e.getErrorCode());
        }
     }
    public void untrackhabit(Habit h, Date d){
        String query = "DELETE FROM TRACKERDB WHERE Habitid =" + h.habitid + " and Donedate = '" + new java.sql.Date(d.getTime()) +"'";
        try{
            stmt.executeUpdate(query);
        } catch (SQLException e){
            System.out.printf("Cannot delete Tracked Habit: %d\n",e.getErrorCode());
        }
     }
    public Vector<Date> getdates(Habit h){
        Vector<Date> dvec = new Vector<>();
        String datequery = "SELECT * FROM TRACKERDB WHERE Habitid = "+ h.habitid;
        try{
           ResultSet dates = stmt.executeQuery(datequery);
           while (dates.next()){
               dvec.add(new Date(dates.getDate("Donedate").getTime()));
           }
        }catch (SQLException e){
            System.out.printf("Cannot get the specified habit: %d\n",e.getErrorCode());
        }
        return dvec;
     }
    public boolean donetoday(Habit h) {
       int hid= 0;
        String querydate = "SELECT Habitid FROM TRACKERDB WHERE Donedate ='" + new java.sql.Date(new Date().getTime()) + "' AND Habitid = " + h.habitid;
        try{
            ResultSet selectH = stmt.executeQuery(querydate);
            while (selectH.next()) {
                hid++;
            }
        } catch (SQLException e){
            System.out.printf("Cannot get dates for this habit: %d\n",e.getErrorCode());
        }
        return hid > 0;
    }
    public boolean check(String s){
            return s.contains("/");

    }
}
