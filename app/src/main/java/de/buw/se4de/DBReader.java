package de.buw.se4de;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class DBReader {
    String path;
    public DBReader(String path){
        this.path = path;
    }
     public Vector<Habit> gethabits() {
         Vector<Habit> habvec= new Vector<Habit>();
         return habvec;
     }
     public boolean inserthabit(Habit h){
         //TODO
         return true;
     }
     public boolean deletehabit(int id){
         //TODO
         return true;
     }
     public boolean trackhabit(int id, Date d){
          //TODO
         return true;
     }
    public static void main(String[] a) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        // add application code here
        conn.close();
    }
}
