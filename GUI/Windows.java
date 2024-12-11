package GUI;
import com.softwarefinal.gym.Load;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


@SuppressWarnings("serial")
public class Windows extends JFrame{
  
  public void myWindows(){
	  	final User instance = new User();
	  	GridLayout gb = new GridLayout(2, 2);
    
	  	// Create a JFrame (the window)
	  	final JFrame launchPage = new JFrame("Fitness application");
	  	JLabel LPGreeting = new JLabel("Welcome to the fitness tracker application", SwingConstants.CENTER);
	  	JLabel LogInLabel = new JLabel("please enter your name in the textbox below:", SwingConstants.CENTER);
	  	LPGreeting.setFont(new Font("Arial", Font.PLAIN, 30));
	  	//buttons for the launch page
	  	JButton CloseWindow = new JButton("close");
	  	final JButton submitButton = new JButton("Submit");
	  	JButton EnterApp = new JButton("Enter");
	  	EnterApp.setVisible(false);
	  	final JTextField textField = new JTextField(20);
	  	launchPage.add(CloseWindow);
	  	launchPage.add(LPGreeting, BorderLayout.NORTH);
	  	launchPage.add(textField);
	  	launchPage.add(submitButton);
	  	launchPage.add(EnterApp, BorderLayout.SOUTH);
	  	launchPage.add(LogInLabel, BorderLayout.CENTER);
	  	launchPage.pack();	  	
    
	  	// Set the size of the frame
	  	launchPage.setSize(475, 500);
	  	//The main window where the exercise interaction takes place
          CloseWindow.setBounds(1, 2, 100, 30); // Set button position and size
          CloseWindow.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                    System.exit(0);
              }
          });
          //button two will be used to transfer to the next page
            EnterApp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	Load create = new Load();
            		create.run();
                }
            });

	  
            submitButton.setBounds(800, 420, 100, 50);
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // When the submit button is pressed, hide it and show the enter button
                	String text = textField.getText();
                	if(!text.isEmpty()) {
                    submitButton.setVisible(false);
                    EnterApp.setVisible(true);
                	}
                }
            });

	  
          textField.setBounds(550, 420, 250, 50);
          textField.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  String text = textField.getText();
                  instance.setName(text);
                  JLabel name = new JLabel("Welcome, " + instance.returnName());
                  name.setFont(new Font("Arial", Font.PLAIN, 12));
                name.setBounds(1, 5, 100, 30);
                  JButton UsersList = new JButton("workouts list");
                  WorkoutsPage.add(name);
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.add(UsersList);
                WorkoutsPage.add(buttonPanel, BorderLayout.NORTH);
                
                UsersList.setBounds(1, 100, 100, 50);
                UsersList.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	UserWorkoutsPage.setVisible(true);
                    	String st = instance.returnWorkouts();
                        JLabel Workouts = new JLabel(st);
                        Workouts.setBounds(200, 300, 100, 30);
                        UserWorkoutsPage.add(Workouts);
                    }
                });

              }
          });
          
          
          //THE FOLLOWING COULD PROVE USEFUL
          /*
          // need to create a window for these two button(not made yet)
          AddWorkout.setBounds(1, 2, 100, 50);
          AddWorkout.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  if(!(instance.checkWorkout("test workout"))){
                    instance.addWorkout("test workout");
                  }
              }
          });
          
          RemoveWorkout.setBounds(1, 100, 100, 50);
          RemoveWorkout.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  if(instance.checkWorkout("test workout")){
                    instance.removeWorkout("test workout");
                  }
              }
          });
          */
          

          launchPage.setVisible(true);
          launchPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
