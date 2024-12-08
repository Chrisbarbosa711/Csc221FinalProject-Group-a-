package GUI;
import com.softwarefinal.gym.Load; //import the class from another project so we can use it.
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;



//(NOTE) ANYTHING WHICH IS NAMED LABELX OR BUTTONX IS TEMPERARY

@SuppressWarnings("serial")
public class Windows extends JFrame{
  
  public void myWindows(){
	final User instance = new User();
	GridLayout gb = new GridLayout(2, 2);
    
	// Create a JFrame (the window)
	final JFrame launchPage = new JFrame("Fitness application");
	JLabel LPGreeting = new JLabel("Welcome to the Fitness application", SwingConstants.CENTER);
	JLabel LogInLabel = new JLabel("please enter your name in the textbox below:", SwingConstants.CENTER);
	LPGreeting.setFont(new Font("Arial", Font.PLAIN, 24));
	//buttons for the launch page
	JButton CloseWindow = new JButton("close");
	final JButton submitButton = new JButton("Submit");
	JButton EnterApp = new JButton("Enter");
	EnterApp.setVisible(false); //we initially set not visible
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
	final JFrame WorkoutsPage = new JFrame("exercise lists");
	WorkoutsPage.setSize(475, 500);
  
	//buttons, etc. for the WorkoutsPage window
	JButton ReturnToLaunch = new JButton("back");
	JLabel label2 = new JLabel("workouts list to be added", SwingConstants.CENTER);
        JButton SearchButton = new JButton("search");			
        JButton button5 = new JButton("test workout");
          
        //window to add or remove a workout from the users list
          final JFrame SelectWorkoutPage = new JFrame("Select a Workout");
          SelectWorkoutPage.setSize(475, 500);
          SelectWorkoutPage.setLayout(gb);
          final JButton AddWorkout = new JButton("Add");
          final JButton RemoveWorkout = new JButton("Remove");
          JLabel selectScreen = new JLabel("select whether to add or remove this workout", SwingConstants.CENTER);
          
          SelectWorkoutPage.add(AddWorkout);
          SelectWorkoutPage.add(RemoveWorkout);
          SelectWorkoutPage.add(selectScreen);
          label2.setFont(new Font("Arial", Font.PLAIN, 24));
          
          WorkoutsPage.add(ReturnToLaunch);
          WorkoutsPage.add(SearchButton);
          WorkoutsPage.add(button5);
          WorkoutsPage.add(label2, BorderLayout.CENTER);
          
          final JFrame UserWorkoutsPage = new JFrame("Users workouts");


          CloseWindow.setBounds(1, 2, 100, 30); // Set button position and size
          CloseWindow.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                    System.exit(0);
              }
          });
          //button two will be used to transfer to the next page
            EnterApp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  Load create = new Load(); //creates Load object 
		create.run(); //runs the page created by Load class
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
                    EnterApp.setVisible(true); //once the user has entered a name then we set visible
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
          ReturnToLaunch.setBounds(1, 2, 100, 30); // Set button position and size
          //to go back to launch screen from the new window
          ReturnToLaunch.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
              launchPage.setVisible(true);
              }
          });

          SearchButton.setBounds(550, 500, 250, 50); // Set button position and size
          //go to search button window from the window
          //the search button currently does not work
          SearchButton.addActionListener(new ActionListener() {
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
          
          

          launchPage.setVisible(true);
          launchPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
