package de.buw.se4de;
import java.util.Calendar;
import java.util.Date;

public class OurCalendar {
    Calendar calendar;
    int thisweek;
    int day;
 public OurCalendar(Date d){
     int year=0;
     int month=0;
     int day =0;
     calendar.set(year,month,day);
     thisweek = calendar.get(calendar.WEEK_OF_YEAR);
     day = calendar.get(Calendar.DAY_OF_WEEK);
 }
}
