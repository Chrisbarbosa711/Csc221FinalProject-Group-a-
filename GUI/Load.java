package GUI;

import SQLtest.DBconnection;
import SQLtest.UnpackRS;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Load extends JPanel {

   public void refreshDayExercises(String day, JPanel Pages) {
	    System.out.println("Refreshing " + day);
	    ResultSet exercises = DBconnection.dayExercises(day);
	    try {
	        ArrayList<String[]> exerciseList = UnpackRS.unpackDayExercise(exercises);

	        // Create a JPanel to hold exercise buttons
	        JPanel exercisePanel = new JPanel();
	        exercisePanel.setLayout(new BoxLayout(exercisePanel, BoxLayout.Y_AXIS)); // Vertical list of buttons

	        // Create a button for each exercise
	        for (String[] exercise : exerciseList) {
	            String exerciseName = exercise[0];
	            String exSets = exercise[1];
	            String exReps = exercise[2];
	            // Create a button for the exercise
	            
	            JButton exerciseButton;
	    
	            exerciseButton = new JButton(exerciseName + " - " + exSets + " sets, " + exReps + " reps" );
	            
	            exerciseButton.setFont(new Font("Arial", Font.PLAIN, 16));

	            // Define a custom class to store the exercise and its reps/sets
	            class ExerciseInfo {
	                String name;
	                String reps;
	                String sets;

	                public ExerciseInfo(String name, String reps, String sets) {
	                    this.name = name;
	                    this.reps = reps;
	                    this.sets = sets;
	                }

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
	                // First, show a confirmation dialog for removing the exercise
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

	        // Create the main panel to display exercises
	        JLabel dayLabel = new JLabel("Exercises for " + day, SwingConstants.CENTER);
	        dayLabel.setFont(new Font("Arial", Font.PLAIN, 18));

	        JPanel dayPage = new JPanel(new BorderLayout());
	        dayPage.setBackground(Color.PINK);
	        dayPage.add(dayLabel, BorderLayout.NORTH);  // Add the label at the top
	        dayPage.add(exercisePanel, BorderLayout.CENTER);  // Add the exercise buttons in the center

	        // Add a search button at the bottom
	        JButton searchButton = new JButton("Search exercise for " + day);
	        searchButton.addActionListener(e -> {
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

    public class DayButton extends JButton {
        private static final long serialVersionUID = 1L;

        DayButton(JPanel panel, JPanel day, String text) {
            super(text);
            panel.add(this);

            this.addActionListener(e -> {
                refreshDayExercises(text, day); 
            });
        }
    }

    public void run() {
        setLayout(new BorderLayout());
        setBackground(Color.PINK);

        JPanel Buttons = new JPanel(new GridLayout(1, 7, 20, 0));
        JPanel Pages = new JPanel(new CardLayout());

        String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : week) {
            new DayButton(Buttons, Pages, day);
        }

        JButton archiveButton = new JButton("Clear Tracker");
        archiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBconnection.deleteWorkouts();
                System.out.println("Archived.");
            }
        });
        add(archiveButton, BorderLayout.SOUTH);

        add(Buttons, BorderLayout.NORTH);
        add(Pages, BorderLayout.CENTER);
    }
}
