package de.buw.se4de;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;


public class MainWindow extends JFrame{
    Vector<User> Users;
    DBReader dbr;
    User currentuser = new User(-1,"");

    public MainWindow(String appName, Vector<User> U, DBReader D) {
        //super(appName);
        Users = U;
        dbr = D;

        //Frame declaration and settings
        JFrame startFrame = new JFrame(appName);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        startFrame.setVisible(true);
        startFrame.setSize(800, 600);
        startFrame.setLayout(new BorderLayout());

        //Declare
        JPanel labelP = new JPanel();
        JPanel topP = new JPanel();
        JPanel bottomP = new JPanel();
        JPanel leftP  = new JPanel();
        JPanel rightP = new JPanel();
        JLabel usernamelabel = new JLabel("User: ");
        JLabel useridlabel = new JLabel("UserID: ");
        JButton changeUser = new JButton("change User");
        JButton newUser = new JButton("new User");
        JButton delUser = new JButton("delete current User");
        JButton delHabits = new JButton("delete Habit");
        JButton newHabit = new JButton("add new Habit");
        JButton changeHabit = new JButton("change Habit");

        //add to parent
        bottomP.add(newUser);
        bottomP.add(newHabit);
        bottomP.add(changeHabit);
        bottomP.add(changeUser);
        bottomP.add(delHabits);
        bottomP.add(delUser);
        labelP.add(usernamelabel);
        labelP.add(useridlabel);

        JScrollPane scrollPane = new JScrollPane(topP);

        addcheckboxes(topP);

        startFrame.getContentPane().add(labelP, BorderLayout.NORTH);
        startFrame.getContentPane().add(leftP, BorderLayout.BEFORE_LINE_BEGINS);
        startFrame.getContentPane().add(rightP, BorderLayout.AFTER_LINE_ENDS);
        startFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        startFrame.getContentPane().add(bottomP, BorderLayout.SOUTH);
        SwingUtilities.updateComponentTreeUI(startFrame);

        delUser.addActionListener(e-> {
            dbr.deleteuser(currentuser);
            Users.remove(currentuser);
            currentuser = new User(-1,"");
            usernamelabel.setText("User: ");
            useridlabel.setText("ID: ");

            });
        newUser.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(startFrame, "Choose you Username!", null);
            if (name != null) {
                name = replaceUmlaute(name);
                User temp = dbr.insertuser(name);
                Users.add(temp);
                currentuser = temp;
                usernamelabel.setText("User: " + currentuser.username);
                useridlabel.setText("UserID: " + currentuser.uid);
                addcheckboxes(topP);
            }
        });
        changeUser.addActionListener(e->{
            //Quelle: https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html//
            if (Users.size() < 1) {
                JOptionPane.showMessageDialog(startFrame, "Create a user first!");
                return;
            }
            Object[] possibleValues = Users.toArray();
            Object temp = JOptionPane.showInputDialog(null,
                    "Choose your User", "Change User",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    possibleValues, possibleValues[0]);
            if(temp == null)
                return;
            currentuser = (User) temp;
            usernamelabel.setText("User: " + currentuser.username);
            useridlabel.setText("UserID: " + currentuser.uid);
            addcheckboxes(topP);
        });

        newHabit.addActionListener(e->{
            if (currentuser.uid == -1) {
                JOptionPane.showMessageDialog(startFrame, "Choose a user first!");
                return;
            }
            JFrame habitFrame = new JFrame(appName);
            habitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            habitFrame.setSize((12/6)*300, 280);

            habitFrame.setLocation((startFrame.getLocation().x + startFrame.getWidth() / 2) - habitFrame.getWidth() / 2, (startFrame.getLocation().y + startFrame.getHeight() / 2) - habitFrame.getHeight() / 2);

            habitFrame.setVisible(true);
            habitFrame.getContentPane().setLayout(new FlowLayout());
            JPanel basepanel = new JPanel();
            basepanel.setLayout(new GridLayout(12, 2));
            JLabel label1 = new JLabel("Habitname:");
            JLabel label2 = new JLabel("Habitdescription:");
            JLabel label3 = new JLabel("Habitcategory:");
            JLabel label4 = new JLabel("How often do you want to do the habit");
            JLabel label5 = new JLabel(" or on What weekdays ?");
            JCheckBox monday = new JCheckBox("Monday");
            JCheckBox tuesday = new JCheckBox("Tuesday");
            JCheckBox wednesday = new JCheckBox("Wednesday");
            JCheckBox thursday = new JCheckBox("Thursday");
            JCheckBox friday = new JCheckBox("Friday");
            JCheckBox saturday = new JCheckBox("Saturday");
            JCheckBox sunday = new JCheckBox("Sunday");

            JTextField habitname = new JTextField("");
            JTextField habitdescription = new JTextField("");
            JComboBox<Habit.Category> category = new JComboBox<>(Habit.Category.values());
            JPanel filler = new JPanel();
            JComboBox<Habit.Cycle> cycle = new JComboBox<>(Habit.Cycle.values());

            JButton submit = new JButton("add Habit");

            basepanel.add(label1);
            basepanel.add(habitname);
            basepanel.add(label2);
            basepanel.add(habitdescription);
            basepanel.add(label3);
            basepanel.add(category);
            basepanel.add(label4);//combo
            basepanel.add(label5);//combo
            basepanel.add(cycle);
            basepanel.add(monday);
            basepanel.add(tuesday);
            basepanel.add(wednesday);
            basepanel.add(thursday);
            basepanel.add(friday);
            basepanel.add(saturday);
            basepanel.add(sunday);

            basepanel.add(filler);
            basepanel.add(submit);


            habitFrame.getContentPane().add(basepanel);

            submit.addActionListener(e2-> {
                String name = replaceUmlaute(habitname.getText());
                String desc = replaceUmlaute(habitdescription.getText());
                Habit.Category cat = (Habit.Category) category.getSelectedItem();
                Habit.Cycle cyc = (Habit.Cycle) cycle.getSelectedItem();
                Vector<Habit.Days> daysvec = new Vector<>();

                if(monday.isSelected()){daysvec.add(Habit.Days.MONDAY);}
                if(tuesday.isSelected()){daysvec.add(Habit.Days.TUESDAY);}
                if(wednesday.isSelected()){daysvec.add(Habit.Days.WEDNESDAY);}
                if(thursday.isSelected()){daysvec.add(Habit.Days.THURSDAY);}
                if(friday.isSelected()){daysvec.add(Habit.Days.FRIDAY);}
                if(saturday.isSelected()){daysvec.add(Habit.Days.SATURDAY);}
                if(sunday.isSelected()){daysvec.add(Habit.Days.SUNDAY);}

                Habit temp;
                if(cycle.getSelectedItem() == Habit.Cycle.NONE){temp = new Habit(name, desc, cat, daysvec);}
                else{temp = new Habit(name, desc, cat, cyc);}

                dbr.inserthabit(temp, currentuser);
                for(Habit.Days el : Habit.Days.values()){el.ex = false;}
                currentuser.habitvec.add(temp);
                habitFrame.dispose();

                addcheckboxes(topP);
            });
        });

        delHabits.addActionListener(e-> {
            if (currentuser.habitvec.size() < 1) {
                JOptionPane.showMessageDialog(startFrame, "Create a habit first!");
                return;
            }
            Object[] possibleValues = currentuser.habitvec.toArray();
            Object temp = JOptionPane.showInputDialog(null,
                    "Choose a habit to delete", "Delete habit",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    possibleValues, possibleValues[0]);
            if(temp == null)
                return;
            currentuser.habitvec.remove((Habit) temp);
            dbr.deletehabit((Habit)temp);
            addcheckboxes(topP);
        });

        changeHabit.addActionListener(e->{
            if (currentuser.uid == -1) {
                JOptionPane.showMessageDialog(startFrame, "Choose a user first!");
                return;
            }
            //chose habit to change
            Object[] possibleValues = D.gethabits(currentuser).toArray();
            Object temp = JOptionPane.showInputDialog(null,
                    "Choose your Habit", "Change Habit",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    possibleValues, possibleValues[0]);
            if(temp == null) {
                return;
            }
            Habit currenthabit = (Habit) temp;
            //open Habit window to change the habit
            JFrame habitFrame = new JFrame(appName);
            habitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            habitFrame.setSize((12/6)*300, 280);

            habitFrame.setLocation((startFrame.getLocation().x + startFrame.getWidth() / 2) - habitFrame.getWidth() / 2, (startFrame.getLocation().y + startFrame.getHeight() / 2) - habitFrame.getHeight() / 2);

            habitFrame.setVisible(true);
            habitFrame.getContentPane().setLayout(new FlowLayout());
            JPanel basepanel = new JPanel();
            basepanel.setLayout(new GridLayout(12, 2));
            JLabel label1 = new JLabel("Habitname:");
            JLabel label2 = new JLabel("Habitdescription:");
            JLabel label3 = new JLabel("Habitcatergory:");
            JLabel label4 = new JLabel("How often do you want to do the habit");
            JLabel label5 = new JLabel(" or on What weekdays ?");
            //saving the booleans of workday in a vector, if the workday is in workdays it shows up as marked in change
            Vector<Boolean> dayvalue =  new Vector<>();
                for (int i = 1; i <= 7; i++) {
                    if (currenthabit.workdays.size()>0) {
                        for (Habit.Days d : currenthabit.workdays) {
                            if (d.dayofweek == i) {
                                dayvalue.add(true);
                            }
                        }
                    }

                    //checking if for this i was an entry made in dayvalue
                    if (dayvalue.size() < i) {
                        dayvalue.add(false);
                    }
                }
            //now filling in the checkboxes with their boolean
            JCheckBox monday = new JCheckBox("Monday",dayvalue.get(1));
            JCheckBox tuesday = new JCheckBox("Tuesday",dayvalue.get(2));
            JCheckBox wednesday = new JCheckBox("Wednesday",dayvalue.get(3));
            JCheckBox thursday = new JCheckBox("Thursday",dayvalue.get(4));
            JCheckBox friday = new JCheckBox("Friday",dayvalue.get(5));
            JCheckBox saturday = new JCheckBox("Saturday",dayvalue.get(6));
            JCheckBox sunday = new JCheckBox("Sunday",dayvalue.get(0));

            JTextField habitname = new JTextField(currenthabit.name);
            JTextField habitdescription = new JTextField(currenthabit.description);
            //swap currently selected to front of array
            Habit.Category[] changed = add2BeginningOfArray(Habit.Category.values(), currenthabit.category);
            JComboBox<Habit.Category> category = new JComboBox<>(changed);
            JPanel filler = new JPanel();
            //same as category
            Habit.Cycle[] swapped = add2BeginningOfArray(Habit.Cycle.values(),currenthabit.cycle);
            JComboBox<Habit.Cycle> cycle = new JComboBox<>(swapped);

            JButton submit = new JButton("change Habit");

            basepanel.add(label1);
            basepanel.add(habitname);
            basepanel.add(label2);
            basepanel.add(habitdescription);
            basepanel.add(label3);
            basepanel.add(category);
            basepanel.add(label4);//combo
            basepanel.add(label5);//combo
            basepanel.add(cycle);
            basepanel.add(monday);
            basepanel.add(tuesday);
            basepanel.add(wednesday);
            basepanel.add(thursday);
            basepanel.add(friday);
            basepanel.add(saturday);
            basepanel.add(sunday);

            basepanel.add(filler);
            basepanel.add(submit);


            habitFrame.getContentPane().add(basepanel);

            submit.addActionListener(e2-> {
                String name = replaceUmlaute(habitname.getText());
                String desc = replaceUmlaute(habitdescription.getText());
                Habit.Category cat = (Habit.Category) category.getSelectedItem();
                Habit.Cycle cyc = (Habit.Cycle) cycle.getSelectedItem();
                Vector<Habit.Days> daysvec = new Vector<>();

                if(monday.isSelected()){daysvec.add(Habit.Days.MONDAY);}
                if(tuesday.isSelected()){daysvec.add(Habit.Days.TUESDAY);}
                if(wednesday.isSelected()){daysvec.add(Habit.Days.WEDNESDAY);}
                if(thursday.isSelected()){daysvec.add(Habit.Days.THURSDAY);}
                if(friday.isSelected()){daysvec.add(Habit.Days.FRIDAY);}
                if(saturday.isSelected()){daysvec.add(Habit.Days.SATURDAY);}
                if(sunday.isSelected()){daysvec.add(Habit.Days.SUNDAY);}

                int currentid = currenthabit.habitid;
                Habit temphabit;
                if(cycle.getSelectedItem() == Habit.Cycle.NONE){temphabit = new Habit(currentid, name, desc, cat, daysvec);}
                else{temphabit = new Habit(currentid,name, desc, cat, cyc);}

                dbr.changehabit(temphabit, currentuser);
                for(Habit.Days el : Habit.Days.values()){el.ex = false;}
                currentuser.habitvec.add(temphabit);
                habitFrame.dispose();

                addcheckboxes(topP);
            });
        });
    }
    private void addcheckboxes(JPanel pane){
        ItemListener item = e-> {
                int checkboxhabitid = (int)((JCheckBox)e.getSource()).getClientProperty("id:");
                Habit thisone = new Habit(-1,"","",new Date(), Habit.Cycle.FIVE_PER_WEEK, Habit.Category.Anderes);
                for(Habit h:currentuser.habitvec){
                    if(h.habitid == checkboxhabitid){
                        thisone = h;
                    }
                }
                if(thisone.habitid > 0) {
                    if(e.getStateChange() == ItemEvent.SELECTED) {
                        dbr.trackhabit(thisone,new Date());
                    }else {
                        dbr.untrackhabit(thisone, new Date());
                    }
                }
        };
        for (Component c:pane.getComponents()){
            pane.remove(c);
        }


        int i = Math.max(25,selectedhabits().size());
        pane.setLayout(new GridLayout(i, 1));
        for(Habit h:selectedhabits()){
            JCheckBox temp = new JCheckBox(h.name);
            temp.addItemListener(item);
            temp.putClientProperty("id:",h.habitid);
            if(dbr.donetoday(h))
                temp.setSelected(true);
            pane.add(temp);
        }
        SwingUtilities.updateComponentTreeUI(pane);
    }
    private Vector<Habit> selectedhabits(){
        Vector<Habit> selectedvector = new Vector<>();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        for(Habit h:currentuser.habitvec) {
                if (h.cycle.amount > habitdone(h)) {
                    selectedvector.add(h);
                }
                else if(h.workdays.size()>0){
                    for(Habit.Days d : h.workdays){
                        if(day == d.dayofweek){
                            selectedvector.add(h);
                            break;
                        }
                    }
                }
        }
        return selectedvector;
    }
    private int habitdone(Habit h){
        int done = 0;
        OurCalendar cal = new OurCalendar(new Date());
        Vector<Date> dateVector = dbr.getdates(h);
        for(Date d:dateVector){
            if(cal.isinweek(d)){
                if(!dbr.donetoday(h)){
                    done++;
                }
            }
        }
        return done;
    }
    public static <T> T[] add2BeginningOfArray(T[] elements, T element)
    {
        T[] newArray = Arrays.copyOf(elements, elements.length + 1);
        newArray[0] = element;
        System.arraycopy(elements, 0, newArray, 1, elements.length);
        return newArray;
    }
    private static String replaceUmlaute(String output) {
        //https://stackoverflow.com/questions/32696273/java-replace-german-umlauts
        return output.replace("\u00fc", "ue")
                .replace("\u00f6", "oe")
                .replace("\u00e4", "ae")
                .replace("\u00df", "ss")
                .replaceAll("\u00dc(?=[a-z\u00e4\u00f6\u00fc\u00df ])", "Ue")
                .replaceAll("\u00d6(?=[a-z\u00e4\u00f6\u00fc\u00df ])", "Oe")
                .replaceAll("\u00c4(?=[a-z\u00e4\u00f6\u00fc\u00df ])", "Ae")
                .replace("\u00dc", "UE")
                .replace("\u00d6", "OE")
                .replace("\u00c4", "AE");
    }
}
