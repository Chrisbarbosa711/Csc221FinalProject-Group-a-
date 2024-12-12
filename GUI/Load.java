package GUI;

//we need to import the following classes so that we can use the database functionality
import SQLtest.DBconnection;
import SQLtest.UnpackRS;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Load extends JPanel {

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

//this function serves to refresh the information on each of the day panels with the exercises/reps/sets we add
public void refreshDayExercises(String day, JPanel Pages) {
	    System.out.println("Refreshing " + day);
	    //we define this so that we can retrieve the data from the sqlquiery for each exercise
	    ResultSet exercises = DBconnection.dayExercises(day);
	    try {
	    	
	        ArrayList<String[]> exerciseList = UnpackRS.unpackDayExercise(exercises);

	        // Create a JPanel to hold exercise buttons for each exercise
	        JPanel exercisePanel = new JPanel();
	        exercisePanel.setLayout(new BoxLayout(exercisePanel, BoxLayout.Y_AXIS)); // Vertical list of buttons

	        // Create a button for each exercise
	        for (String[] exercise : exerciseList) {
	            String exerciseName = exercise[0];
	            String exSets = exercise[1];
	            String exReps = exercise[2];
	            
	            //create and format the button for each exercise added for that day
	            JButton exerciseButton;
	    
	            exerciseButton = new JButton(exerciseName + " - " + exSets + " sets, " + exReps + " reps" );
	            
	            exerciseButton.setFont(new Font("Arial", Font.PLAIN, 16));

	            // Define a custom class to store the exercise and its reps/sets
	            class ExerciseInfo {
	                private String name;
	                private String reps;
	                private String sets;
	                //define a constructor for the custom class which stored the exercise string in name and the sets and reps in strings
	                public ExerciseInfo(String name, String reps, String sets) {
	                    this.name = name;
	                    this.reps = reps;
	                    this.sets = sets;
	                }
	                //we define the string name depending on if we have sets and reps available or if the user has not entered that yet
	                public String getDisplayText() {
	                    if (reps.isEmpty() && sets.isEmpty()) {
	                        return name;
	                    }
	                    return name + " - " + sets + " sets, " + reps + " reps";
	                }
	            }

	            // Store exercise information
	            ExerciseInfo exerciseInfo = new ExerciseInfo(exerciseName, exReps, exSets);

	            // Define what happens when the exercise button is clicked
	            exerciseButton.addActionListener(e -> {
	                //once the button is clicked it we see three option panes
	            	//the first one asks if the user wants to delete the exercise
	            	//if so then it deletes it and thats it
	            	//if not then we are given the option to add the sets and reps for the exercise
	                int option = JOptionPane.showConfirmDialog(this,
	                        "Do you want to remove " + exerciseName + " from " + day + "?",
	                        "Remove Exercise",
	                        JOptionPane.YES_NO_OPTION);

	                if (option == JOptionPane.YES_OPTION) {
	                    // Remove the exercise button from the panel
	                    exercisePanel.remove(exerciseButton);
	                    exercisePanel.revalidate();  // Revalidate the panel to update the layout
	                    exercisePanel.repaint();  // Repaint the panel to reflect the changes
	                    DBconnection.deleteExercise(day, exerciseName);
	                    System.out.println(exerciseName + " has been removed from " + day);
	                } else {
	                    // If the user doesn't want to remove the exercise, ask for reps and sets
	                	String sets = JOptionPane.showInputDialog(this, "Enter sets for " + exerciseName);
	                    String reps = JOptionPane.showInputDialog(this, "Enter reps for " + exerciseName);

	                    // Validate and update the reps and sets if not empty
	                    if (reps != null && !reps.isEmpty()) {
	                    	
	                    	//need to make sure that what the user enters is a numbers that we can use so we employ the given try catch block to
	                    	//account for this, for both setting the reps and setting the sets of the exercise
	                        try {
	                            int repsCount = Integer.parseInt(reps);
	                            exerciseInfo.reps = reps;
	                	        DBconnection.setReps(exerciseName, repsCount);
	                        } catch (NumberFormatException e1) {
	                            System.out.println("Invalid input: " + reps + " is not a number.");
	                        };
	                        
	                        
	                    }
	                    if (sets != null && !sets.isEmpty()) {
	                        try {
	                            int setCountt = Integer.parseInt(sets);
	                            exerciseInfo.sets = sets;
	                	        DBconnection.setSets(exerciseName, setCountt);
	                        } catch (NumberFormatException e2) {
	                            System.out.println("Invalid input: " + sets + " is not a number.");
	                        }    
	                    }

	                    // Update the button text to reflect the reps and sets
	                    exerciseButton.setText(exerciseInfo.getDisplayText());

	                    // Optionally, store the data in the database or elsewhere for persistence
	                    System.out.println("For " + exerciseName + " on " + day + ": " + sets + " sets, " + reps + " reps");
	                }
	            });

	            // Add button to the panel
	            exercisePanel.add(exerciseButton);
	        }

	        // Create the main panel to display the exercises for each day
	        JLabel dayLabel = new JLabel("Exercises for " + day, SwingConstants.CENTER);
	        dayLabel.setFont(new Font("Arial", Font.PLAIN, 18));
	        
	        //the JPanel to display the days 
	        JPanel dayPage = new JPanel(new BorderLayout());
	        dayPage.setBackground(Color.PINK);
	        dayPage.add(dayLabel, BorderLayout.NORTH);  // Add the label at the top
	        dayPage.add(exercisePanel, BorderLayout.CENTER);  // Add the exercise buttons in the center

	        // Add a search button at the bottom
	        JButton searchButton = new JButton("Search exercise for " + day);
	        searchButton.addActionListener(e -> {
	        	//declare an instance of the ExerciseSearchGUI class which formats the search into an sql quiery for our database
	            ExerciseSearchGUI exerciseSearchPage = new ExerciseSearchGUI(this, day, Pages);
	            CardLayout layout = (CardLayout) Pages.getLayout();
	            Pages.add(exerciseSearchPage, "ExerciseSearch");
	            layout.show(Pages, "ExerciseSearch");
	        });

	        dayPage.add(searchButton, BorderLayout.SOUTH);

	        Pages.add(dayPage, day);
	        CardLayout layout = (CardLayout) Pages.getLayout();
	        layout.show(Pages, day);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

//this way we can write less lines
public class DayButton extends JButton {
	private static final long serialVersionUID = 1L;
	//take parameter panel, this adds the button to that panel
	//parameter day adds whatever type of panel you want to be tied to the button
	//text is the name of the button
        DayButton(JPanel panel, JPanel day, String text) {
		super(text);
		//this adds the button
		panel.add(this);
		this.addActionListener(e -> {
            	//refreshes the page to pull any thing that was added, etc.
                refreshDayExercises(text, day); 
	});
	}
}

    public void run() {
    	//creates the layout for the day buttons and displays the exercises for each day, and the search for each day
    	
        setLayout(new BorderLayout());
        setBackground(Color.PINK);

	//make the buttons a GridLayout bcs it's easier to do
        JPanel Buttons = new JPanel(new GridLayout(1, 7, 20, 0));
        JPanel Pages = new JPanel(new CardLayout());

	//for each day in the week create a button
        String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : week) {
            new DayButton(Buttons, Pages, day);
        }

	//Button to create archive
        JButton deleteButton = new JButton("Clear Tracker");
        archiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBconnection.deleteWorkouts();
                System.out.println("Archived.");
            }
        });
        add(deleteButton, BorderLayout.SOUTH);

        add(Buttons, BorderLayout.NORTH);
        add(Pages, BorderLayout.CENTER);
    }
}
