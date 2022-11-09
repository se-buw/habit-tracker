package de.buw.se4de;

import org.checkerframework.checker.units.qual.C;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;


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
        startFrame.setSize(700, 700);
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

        //add to parent
        bottomP.add(newUser);
        bottomP.add(newHabit);
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

        if(true) {
            delUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dbr.deleteuser(currentuser);
                    Users.remove(currentuser);
                    currentuser = new User(-1,"");
                }
            });
            newUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = JOptionPane.showInputDialog(startFrame, "Choose you Username!", null);
                    if (name != null) {
                        User temp = dbr.insertuser(name);
                        Users.add(temp);
                        currentuser = temp;
                        usernamelabel.setText("User: " + currentuser.username);
                        useridlabel.setText("UserID: " + currentuser.uid);
                    }
                }
            });
            changeUser.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html
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

                }
            });

            newHabit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (currentuser.uid == -1) {
                        JOptionPane.showMessageDialog(startFrame, "Choose a user first!");
                        return;
                    }
                    JFrame habitFrame = new JFrame(appName);
                    habitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    habitFrame.setSize((12/6)*300, 200);

                    habitFrame.setLocation((startFrame.getLocation().x + startFrame.getWidth() / 2) - habitFrame.getWidth() / 2, (startFrame.getLocation().y + startFrame.getHeight() / 2) - habitFrame.getHeight() / 2);

                    habitFrame.setVisible(true);
                    habitFrame.getContentPane().setLayout(new FlowLayout());
                    JPanel basepanel = new JPanel();
                    basepanel.setLayout(new GridLayout(8, 2));
                    JLabel label1 = new JLabel("Habitname:");
                    JLabel label2 = new JLabel("Habitdescription:");
                    JLabel label3 = new JLabel("Habitcatergory:");
                    JLabel label4 = new JLabel("How often do you want to do the habit");
                    JLabel label5 = new JLabel(" or on What weekdays ?");


                    JTextField habitname = new JTextField("");
                    JTextField habitdescription = new JTextField("");
                    JComboBox<Habit.Category> catergory = new JComboBox(Habit.Category.values());
                    JPanel filler = new JPanel();
                    JComboBox<Habit.Cycle> cycle = new JComboBox<>(Habit.Cycle.values());

                    JButton submit = new JButton("add Habit");

                    basepanel.add(label1);
                    basepanel.add(habitname);
                    basepanel.add(label2);
                    basepanel.add(habitdescription);
                    basepanel.add(label3);
                    basepanel.add(catergory);
                    basepanel.add(label4);//combo
                    basepanel.add(label5);//combo
                    basepanel.add(cycle);

                    //cycle
                    basepanel.add(filler);
                    basepanel.add(submit);


                    habitFrame.getContentPane().add(basepanel);

                    submit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String name = habitname.getText();
                            String desc = habitdescription.getText();
                            Habit.Category cat = (Habit.Category) catergory.getSelectedItem();//TODO Cycle
                            Habit.Cycle cyc = (Habit.Cycle) cycle.getSelectedItem();
                            ;//TODO richtiges Umwandeln entwerder kein vec oder wochentage
                            Habit temp = new Habit(name, desc, cat, cyc);
                            dbr.inserthabit(temp, currentuser);
                            currentuser.habitvec.add(temp);
                            habitFrame.dispose();

                            addcheckboxes(topP);
                        }
                    });
                }
            });

            delHabits.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (currentuser.habitvec.size() < 1) {
                        JOptionPane.showMessageDialog(startFrame, "Create a habbit first!");
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
                }
            });
        }//TODO mach das if wieder weg, war nur der Übersicht wegen hier
    }
    private void addcheckboxes(JPanel pane){
        //JCheckBox[] temp;
        ItemListener item = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int checkboxhabitid = (int)((JCheckBox)e.getSource()).getClientProperty("id:");
                Habit thisone = new Habit(-1,"","",new Date(), Habit.Cycle.FIVE_PER_WEEK, Habit.Category.Anderes);
                for(Habit h:currentuser.habitvec){
                    if(h.habitid == checkboxhabitid){
                        thisone = h;
                    }
                }
                if(thisone.habitid >0) {
                    if(e.getStateChange() == 1) {
                        dbr.trackhabit(thisone,new Date());
                        Vector<Date> dates = dbr.getdates(thisone);
                        for(Date d:dates) {
                            System.out.println(d);
                        }
                        System.out.println("---------");
                    }else {
                        dbr.untrackhabit(thisone, new Date());
                        Vector<Date> dates = dbr.getdates(thisone);
                        for(Date d:dates) {
                            System.out.println(d);
                        }
                        System.out.println("---------");

                    }
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
        for(Habit h:currentuser.habitvec) {
                if (h.cycle.amount > habitdone(h)) {
                    selectedvector.add(h);
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
                    if(!dbr.donetoday(h))
                        done++;
                }
            }
            return done;
    }


    //TODO private/public von allen
}
