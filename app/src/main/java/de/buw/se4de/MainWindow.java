package de.buw.se4de;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
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
        this.pack();
        startFrame.setVisible(true);
        startFrame.setSize(600, 700);

        JPanel topP = new JPanel(new GridLayout(20, 1));
        JPanel bottomP = new JPanel();
        JToolBar toolbar = new JToolBar();
        JLabel label1 = new JLabel("User: ");
        JButton changeUser = new JButton("change User");
        JButton newUser = new JButton("new User");
        JButton delHabits = new JButton("delete Habits");
        JButton newHabit = new JButton("add new Habit");
        toolbar.add(changeUser);
        toolbar.addSeparator(new Dimension(25, 25));
        toolbar.add(newUser);
        toolbar.addSeparator(new Dimension(25, 25));
        toolbar.add(delHabits);
        toolbar.addSeparator(new Dimension(25, 25));
        toolbar.add(newHabit);
        toolbar.addSeparator(new Dimension(25, 25));
        bottomP.add(toolbar);

        Vector<JCheckBox> CheckBoxes = new Vector<>();
        addCheckbox(CheckBoxes);
        topP.add(label1);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topP, bottomP);
        startFrame.getContentPane().add(splitPane);


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
                //https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html
                Object[] possibleValues = Users.toArray();
                Object temp = JOptionPane.showInputDialog(null,
                        "Choose your User", "Change User",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        possibleValues, possibleValues[0]);
                currentuser = (User) temp;
                label1.setText("User: " + currentuser.username);
            }
        });
        //Todo delete User

        newHabit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentuser.uid == -1) {
                    JOptionPane.showMessageDialog(startFrame, "Choose a user first!");
                    return;
                }
                JFrame habitFrame = new JFrame(appName);
                habitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                habitFrame.setSize(300, 200);

                habitFrame.setLocation((startFrame.getLocation().x + startFrame.getWidth() / 2) - habitFrame.getWidth() / 2, (startFrame.getLocation().y + startFrame.getHeight() / 2) - habitFrame.getHeight() / 2);

                habitFrame.setVisible(true);
                habitFrame.getContentPane().setLayout(new FlowLayout());
                JPanel basepanel = new JPanel();
                basepanel.setLayout(new GridLayout(4, 2));
                JLabel label1 = new JLabel("Habitname:");
                JLabel label2 = new JLabel("Habitdescription:");
                JLabel label3 = new JLabel("Habitcatergory:");

                JTextField habitname = new JTextField("");
                JTextField habitdescription = new JTextField("");
                JComboBox<Habit.Category> catergory = new JComboBox(Habit.Category.values());
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
                        Habit temp = new Habit(name, desc, cat);
                        dbr.inserthabit(temp, currentuser);
                        currentuser.habitvec.add(temp);

                        for (Habit h : currentuser.habitvec)
                            System.out.printf("(%d, %s)", h.habitid, h.name);
                        habitFrame.dispose();
                    }
                });
            }
        });

        delHabits.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
    }

    public void addCheckbox(Vector<JCheckBox> v1){

    }
}
