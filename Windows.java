package GUI;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Windows extends JFrame{
  
  public void myWindows(){
    // Create a JFrame (the window)
        final JFrame launchPage = new JFrame("Fitness application");
          JLabel label = new JLabel("Welcome to the Fitness application", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 24));
          JButton button = new JButton("close");
          JButton button2 = new JButton("Enter");
          launchPage.add(button);
          launchPage.add(button2);
          launchPage.add(label, BorderLayout.CENTER);
          // Set the size of the frame
          launchPage.setSize(475, 500);
    launchPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          final JFrame WorkoutsPage = new JFrame("exercise lists");
          WorkoutsPage.setSize(475, 500);
          JButton button3 = new JButton("back");
          JLabel label2 = new JLabel("workouts list to be added", SwingConstants.CENTER);
          JButton button4 = new JButton("search");
          label2.setFont(new Font("Arial", Font.PLAIN, 24));
          WorkoutsPage.add(button3);
          WorkoutsPage.add(button4);
          WorkoutsPage.add(label2, BorderLayout.CENTER);


          button.setBounds(1, 2, 100, 30); // Set button position and size
          button.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                    System.exit(0);
              }
          });
          //button two will be used to transfer to the next page
          button2.setBounds(550, 420, 250, 50); // Set button position and size
            button2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  WorkoutsPage.setVisible(true);
                }
            });
          button3.setBounds(1, 2, 100, 30); // Set button position and size
          //to go back to launch screen from the new window
          button3.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
              launchPage.setVisible(true);
              }
          });

          button4.setBounds(550, 420, 250, 50); // Set button position and size
          //go to search button window from the window
          //the search button currently does not work
          button4.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
              SearchBox s = new SearchBox("Look for workouts");
              s.setVisible(true);
              }
          });
    

          launchPage.setVisible(true);
  }
}
