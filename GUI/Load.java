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
	            JButton exerciseButton = new JButton(exerciseName);
	            exerciseButton.setFont(new Font("Arial", Font.PLAIN, 16));

	            // Define what happens when the exercise button is clicked
	            exerciseButton.addActionListener(e -> {
	                // Action for when the exercise is clicked
	                JOptionPane.showMessageDialog(this, "You clicked on: " + exerciseName);
	                // You could add more logic here to handle the clicked exercise
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
