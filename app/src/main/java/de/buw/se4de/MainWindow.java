package de.buw.se4de;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MainWindow extends JFrame{

    public MainWindow(String appName) {
        //super(appName);
        JFrame startFrame = new JFrame(appName);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setContentPane(mainPanel); ???
        //this.pack();
        startFrame.setVisible(true);
        startFrame.setSize(800, 700);

        startFrame.getContentPane().setLayout(new FlowLayout());
        JPanel leftP = new JPanel();
        JPanel rightP = new JPanel();
        JLabel label1 = new JLabel();
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
        /*
        delHabits.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = "";
                if (HabitList.getSelectedIndex() != -1) {
                    data = "delete this habit: " + HabitList.getSelectedValue();
                    label1.setText(data);
                    deleteHabit(HabitList.getSelectedValue());
                }
            }
        });*/
        newUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO add new user
            }
        });
        changeUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO change to another user
            }
        });
        newHabit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
