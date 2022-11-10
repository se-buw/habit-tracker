package de.buw.se4de;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

public class OurCalendar {
    Calendar calendar;
    int thisweek;
    int day;
 public OurCalendar(Date d){
     calendar = Calendar.getInstance();
     calendar.setTime(d);
     thisweek = calendar.get(Calendar.WEEK_OF_YEAR);
     day = calendar.get(Calendar.DAY_OF_WEEK);
 }
 public boolean isinweek(Date date){
     Calendar cal = Calendar.getInstance();
     cal.setTime(date);

     int dayofweek = cal.get(Calendar.DAY_OF_WEEK);

     Duration d = Duration.between(date.toInstant(),new Date().toInstant());
     long daysbetween = d.getSeconds() / 86400;
     return daysbetween <= 7 && daysbetween <= dayofweek;
 }

}
