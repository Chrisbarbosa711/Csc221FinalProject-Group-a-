package GUI;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Windows extends JFrame{
  
  public void myWindows(){
    final User instance = new User();
    // Create a JFrame (the window)
        final JFrame launchPage = new JFrame("Fitness application");
          JLabel label = new JLabel("Welcome to the Fitness application", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 24));
          JButton button = new JButton("close");
          JButton button2 = new JButton("Enter");
          final JTextField textField = new JTextField(20);
          launchPage.add(button);
          launchPage.add(button2);
          launchPage.add(label, BorderLayout.CENTER);
          launchPage.add(textField, BorderLayout.SOUTH);
          // Set the size of the frame
          launchPage.setSize(475, 500);
    launchPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          final JFrame WorkoutsPage = new JFrame("exercise lists");
          WorkoutsPage.setSize(475, 500);
          JButton button3 = new JButton("back");
          JLabel label2 = new JLabel("workouts list to be added", SwingConstants.CENTER);
          JButton button4 = new JButton("search");
          JButton button5 = new JButton("test workout");
          final JFrame SelectWorkoutPage = new JFrame("Selected Workout");
          SelectWorkoutPage.setSize(475, 500);
          final JButton button7 = new JButton("Add");
          final JButton button8 = new JButton("Remove");
          JLabel selectScreen = new JLabel("select whether to add or remove this workout", SwingConstants.CENTER);
          SelectWorkoutPage.add(button7);
          SelectWorkoutPage.add(button8);
          SelectWorkoutPage.add(selectScreen);
          label2.setFont(new Font("Arial", Font.PLAIN, 24));
          WorkoutsPage.add(button3);
          WorkoutsPage.add(button4);
          WorkoutsPage.add(button5);
          WorkoutsPage.add(label2, BorderLayout.CENTER);


          button.setBounds(1, 2, 100, 30); // Set button position and size
          button.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                    System.exit(0);
              }
          });
          //button two will be used to tranfer to the next page
          button2.setBounds(550, 350, 250, 50); // Set button position and size
            button2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  WorkoutsPage.setVisible(true);
                }
            });

          textField.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  String text = textField.getText();
                  instance.setName(text);
                  JLabel name = new JLabel("Welcome, " + instance.returnName());
                  name.setFont(new Font("Arial", Font.PLAIN, 12));
                name.setBounds(1, 5, 100, 30);
                  JButton button6 = new JButton("workouts list");
                  WorkoutsPage.add(name);
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.add(button6);
                WorkoutsPage.add(buttonPanel, BorderLayout.NORTH);
              }
          });
          button3.setBounds(1, 2, 100, 30); // Set button position and size
          //to go back to launch screen from the new window
          button3.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
              launchPage.setVisible(true);
              }
          });

          button4.setBounds(550, 350, 250, 50); // Set button position and size
          //go to search button window from the window
          //the search button currently does not work
          button4.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
              SearchBox s = new SearchBox("Look for workouts");
              s.setVisible(true);
              }
          });
          button5.setBounds(550, 420, 250, 50); // Set button position and size
          button5.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
              SelectWorkoutPage.setVisible(true);
              }
          });
          // need to create a window for these two button(not made yet)
          button7.setBounds(1, 2, 100, 50);
          button7.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  if(!(instance.checkWorkout("test workout"))){
                    instance.addWorkout("test workout");
                  }
              }
          });
          button8.setBounds(1, 100, 100, 50);
          button8.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  if(instance.checkWorkout("test workout")){
                    instance.removeWorkout("test workout");
                  }
              }
          });

          launchPage.setVisible(true);
  }
}
