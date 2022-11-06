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
            System.out.println("Fehler, Programm kann sich nicht mit der Datenbank verbinden");//TODO vernünftige Fehlerbehandlung
        }catch (ClassNotFoundException classE){
            connected = false;
            System.out.println("Fehler, can't initialize \"org.h2.Driver\" ");//TODO vernünftige Fehlerbehandlung
        }
    }
    public void InitializeDB() {
        String createUserDB = "CREATE TABLE IF NOT EXISTS USERDB" + "(Userid INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL, Username VARCHAR(255))";
        String createHabitDB = "CREATE TABLE IF NOT EXISTS HABITDB " + "( Habitid INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL,Userid INT ,Habitname VARCHAR(255),Habitdescription TEXT(500) ,Startdate DATE,Habitcycle INT,Habitcategory VARCHAR(255))";//TODO Habitcycle
        String createTrackerDB = "CREATE TABLE IF NOT EXISTS TRACKERDB" + "(Habitid INT, Donedate DATE)";
        //TODO: ints to test the execution of each statement
        try {
            stmt.executeUpdate(createUserDB);
            stmt.executeUpdate(createHabitDB);
            stmt.executeUpdate(createTrackerDB);
        }catch (SQLException e){
            System.out.printf("Cannot Initialize the Databases: %d ",e.getErrorCode());
        }

    }
    public Vector<User> getusers() {
        Vector<User> uservec= new Vector<>();
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
        return uservec;
    }
    public Vector<Habit> gethabits(User u) {
        Vector<Habit> habvec= new Vector<>();
        String gethabitsquery = "Select * FROM HABITDB WHERE Userid = " + u.uid;
        try {
            ResultSet selectH = stmt.executeQuery(gethabitsquery);
            while (selectH.next()) {
                habvec.add(new Habit(selectH.getInt("Habitid"), selectH.getString("Habitname"),
                        selectH.getString("Habitdescription"), selectH.getDate("Startdate"),
                        selectH.getInt("Habitcycle"), Habit.Category.Uni /*.getString("Habitcategory")*/));//TODO Denk An die Kategorie
            }
        }catch(SQLException e){
            System.out.printf("Cannot retrieve habits of User %s: %d",u.username,e.getErrorCode());
        }
        return habvec;
    }
     public Habit inserthabit(@NotNull Habit h, @NotNull User u){//TODO maybe dont return Habit when u can change it ?!?
         String inserthabit = "INSERT INTO HabitDB (Userid,Habitname,Habitdescription,Startdate,Habitcycle,Habitcategory)"
                 + "VALUES("+ u.uid +",'" + h.name + "','" + h.description + "','"+ new java.sql.Date(h.startdate.getTime()) + "'," + h.cycle +",'" + h.category.name() + "')";
         String gethabtidid = "Select Habitid FROM HABITDB WHERE Habitname = '"+ h.name  +"' ORDER BY Userid DESC Limit 1";
         try{
             stmt.executeUpdate(inserthabit);
             ResultSet habitid = stmt.executeQuery(gethabtidid);
             habitid.next();
             h.habitid = habitid.getInt("Habitid");
         }catch (SQLException e){
             System.out.printf("Cannot insert Habit: %d\n",e.getErrorCode());
             return new Habit("", Habit.Category.Uni);
         }
         return h;
     }
    public User insertuser(String username){
        String insertuser = "INSERT INTO USERDB (Username) VALUES('"+ username +"')";
        try{
            stmt.executeUpdate(insertuser);
            String getuserquery = "Select Userid FROM USERDB WHERE Username = '"+ username +"' ORDER BY Userid DESC Limit 1";//BESSERE IDEE ?
            ResultSet h2userid = stmt.executeQuery(getuserquery);
            h2userid.next();
            return new User(h2userid.getInt("Userid"),username);
        }catch (SQLException e){
            System.out.printf("Cannot insert User: %d\n",e.getErrorCode());
        }
        return new User(0,"NULL");//TODO: idk
    }
     public boolean deletehabit(Habit h){
         String query = "DELETE FROM HABITDB WHERE Habitid = "+ h.habitid;
         String query2 = "DELETE FROM TRACKERDB WHERE Habitid = "+ h.habitid;
         try {
             stmt.executeUpdate(query);
             stmt.executeUpdate(query2);
         }catch (SQLException e){
             System.out.printf("Cannot Delete Habit: %d\n",e.getErrorCode());
             return false;
         }
         return true;
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
                stmt.executeUpdate(temp);//TODO muss man wircklich immer einen string da rein packen ?
            }

        }catch(SQLException e){
            System.out.printf("Cannot Delete User/Habit/Tracker: %d\n",e.getErrorCode());
        }
     }
     public boolean trackhabit(Habit h, Date d){
        String query = "INSERT INTO TRACKERDB (HABITID,DONEDATE) VALUES(" + h.habitid + ",'" + new java.sql.Date(d.getTime()) + "')";
        try {
            stmt.executeUpdate(query);
        }catch (SQLException e){
            System.out.printf("Cannot Insert Tracked Habit: %d\n",e.getErrorCode());
            return false;
        }
         return true;
     }
     public Vector<Date> getdates(Habit h){
        Vector<Date> dvec = new Vector<>();
        String datequery = "SELECT * FROM TRACKERDB WHERE Habitid = "+ h.habitid;
        try{
           ResultSet dates = stmt.executeQuery(datequery);
           while (dates.next()){
               dvec.add(dates.getDate("Donedate"));
           }
        }catch (SQLException e){
            System.out.printf("Cannot get dates for this habit: %d\n",e.getErrorCode());
        }
        return dvec;
     }
}