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

	            // Create a button for the exercise
	            JButton exerciseButton = new JButton(exerciseName);
	            exerciseButton.setFont(new Font("Arial", Font.PLAIN, 16));

	            // Define a custom class to store the exercise and its reps/sets
	            class ExerciseInfo {
	                String name;
	                String reps;
	                String sets;

	                public ExerciseInfo(String name) {
	                    this.name = name;
	                    this.reps = "";
	                    this.sets = "";
	                }

	                public String getDisplayText() {
	                    if (reps.isEmpty() && sets.isEmpty()) {
	                        return name;
	                    }
	                    return name + " - " + sets + " sets, " + reps + " reps";
	                }
	            }

	            // Store exercise information
	            ExerciseInfo exerciseInfo = new ExerciseInfo(exerciseName);

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
	                    System.out.println(exerciseName + " has been removed from " + day);
	                } else {
	                    // If the user doesn't want to remove the exercise, ask for reps and sets
	                	String sets = JOptionPane.showInputDialog(this, "Enter sets for " + exerciseName);
	                    String reps = JOptionPane.showInputDialog(this, "Enter reps for " + exerciseName);

	                    // Validate and update the reps and sets if not empty
	                    if (reps != null && !reps.isEmpty()) {
	                        exerciseInfo.reps = reps;
	                    }
	                    if (sets != null && !sets.isEmpty()) {
	                        exerciseInfo.sets = sets;
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

        add(Buttons, BorderLayout.NORTH);
        add(Pages, BorderLayout.CENTER);
    }
}
