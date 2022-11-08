package de.buw.se4de;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class MainWindow extends JFrame{
    Vector<User> Users;
    DBReader dbr;
    User currentuser = new User(-1,"");

    public MainWindow(String appName, Vector<User> U, DBReader D) {
        //super(appName);
        Users = U;
        dbr = D;
        JFrame startFrame = new JFrame(appName);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setContentPane(mainPanel); ???


        //this.pack();
        startFrame.setVisible(true);
        startFrame.setSize(800, 700);

        startFrame.getContentPane().setLayout(new FlowLayout());
        JPanel leftP = new JPanel();
        JPanel rightP = new JPanel();
        JLabel label1 = new JLabel("User: ");
        JButton changeUser = new JButton("change User");
        JButton newUser = new JButton("new User");
        JButton delHabits = new JButton("delete Habits");
        JButton newHabit = new JButton("add new Habit");
        rightP.add(changeUser);
        rightP.add(newUser);
        rightP.add(delHabits);
        rightP.add(newHabit);

        Vector<JCheckBox> CheckBoxes = new Vector<>();
        addCheckbox(CheckBoxes);
        //DefaultListModel<String> l1 = new DefaultListModel<String>();
        //addHabitList(l1);
        //JList<String> HabitList = new JList<>(l1);
        //leftP.add(HabitList);
        leftP.add(label1);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, leftP, rightP);
        startFrame.getContentPane().add(splitPane);

        delHabits.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        newUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(startFrame, "Choose you Username!", null);
                User temp = dbr.insertuser(name);
                Users.add(temp);
                currentuser = temp;
                label1.setText("User: " + currentuser.username);
            }

        });
        changeUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                //TODO change to another user
            }
        });
        newHabit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(currentuser.uid == -1){
                    JOptionPane.showMessageDialog(startFrame, "Choose a user first!");
                    return;
                }
                JFrame habitFrame = new JFrame(appName);
                habitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                habitFrame.setSize(300, 200);

                habitFrame.setLocation((startFrame.getLocation().x + startFrame.getWidth()/2)-habitFrame.getWidth()/2,(startFrame.getLocation().y + startFrame.getHeight()/2)-habitFrame.getHeight()/2);

                habitFrame.setVisible(true);
                habitFrame.getContentPane();
                habitFrame.getContentPane().setLayout(new FlowLayout());
                JPanel basepanel = new JPanel();//TODO sieht scheiße aus
                basepanel.setLayout(new GridLayout(5,2,1,1));
                JLabel label1 = new JLabel("Habitname:");
                JLabel label2 = new JLabel("Habitdescription:");
                JLabel label3 = new JLabel("Habitcatergory:");

                JTextField habitname = new JTextField("");
                JTextField habitdescription = new JTextField("");
                JComboBox<Habit.Category>catergory = new JComboBox(Habit.Category.values());
                JPanel filler = new JPanel();


                JButton submit = new JButton("add Habit");

                basepanel.add(label1);
                basepanel.add(habitname);
                basepanel.add(label2);
                basepanel.add(habitdescription);
                basepanel.add(label3);
                basepanel.add(catergory);
                //cycle
                basepanel.add(filler);
                basepanel.add(submit);

                habitFrame.getContentPane().add(basepanel);


                submit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = habitname.getText();
                        String desc = habitdescription.getText();
                        Habit.Category cat = (Habit.Category) catergory.getSelectedItem();//TODO Cycle
                        Habit temp = new Habit(name,desc,cat);
                        dbr.inserthabit(temp,currentuser);
                        currentuser.habitvec.add(temp);

                        for (Habit h:currentuser.habitvec)
                            System.out.printf("(%d, %s)",h.habitid,h.name);
                        habitFrame.dispose();
                    }
                });
                //TODO: add Habit
            }
        });
    }
    public void addHabitList(DefaultListModel<String> l1){
        l1.addElement("Uni");
        l1.addElement("Sport");
        //Todo: DB Verknüpfung
    }

    public void deleteHabit(String Value){
        //TODO: DB Verknüpfung
    }

    public void addCheckbox(Vector<JCheckBox> v1){

    }
}
