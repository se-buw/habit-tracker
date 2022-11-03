package de.buw.se4de;

import java.util.Date;
import java.util.Vector;

public class DBReader {
    String path;
    public DBReader(String path){
        this.path = path;
    }
     public Vector<Habit> gethabits(){
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
}
