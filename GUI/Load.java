package com.softwarefinal.gym;
import SQLtest.DBconnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*;

public class Load {
	public class DayButton extends JButton {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		DBconnection s = new DBconnection();

		DayButton(JPanel panel, JPanel day, String text){
			super(text);
			panel.add(this);
			//DBconnection s = new DBconnection();
			s.establishConnection();
			
			JPanel dayPage = new JPanel();
			dayPage.setBackground(Color.PINK);
			dayPage.setLayout(new BorderLayout());
			
			JLabel exercisesLabel = new JLabel("Exercises for " + text + ": ", JLabel.CENTER);
            dayPage.add(exercisesLabel, BorderLayout.NORTH);
			
			// Create search field and submit button
            JPanel searchPanel = new JPanel();
            JTextField searchField = new JTextField(20);
            JButton searchButton = new JButton("Search for more exercises");
            searchPanel.add(searchField);
            searchPanel.add(searchButton);
            dayPage.add(searchPanel, BorderLayout.SOUTH);
            
            updateExercisesForDay(text, exercisesLabel);

            day.add(dayPage, text);
			
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					CardLayout layout = (CardLayout) day.getLayout();
	                layout.show(day, text);
				}
			});
			
			searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // On search, update exercises based on the query
                    String searchText = searchField.getText().trim();
                    if (!searchText.isEmpty()) {
                        updateExercisesForDayWithSearch(text, searchText, exercisesLabel);
                    }
                }
            });
		}
		
		private void updateExercisesForDay(String day, JLabel exercisesLabel) {
            ResultSet exercises = getExercisesForDay(day);
            displayExercises(exercisesLabel, exercises);  // Pass exercisesLabel to update
        }

        // Method to update exercises for the selected day based on search query
		private void updateExercisesForDayWithSearch(String day, String searchText, JLabel exercisesLabel) {
            ResultSet exercises = getExercisesForDayWithSearch(day, searchText);
            displayExercises(exercisesLabel, exercises);  // Pass exercisesLabel to update
        }

        // Method to display exercises on the label
        private void displayExercises(JLabel label, ResultSet exercises) {
            try {
                StringBuilder exercisesText = new StringBuilder("Exercises: ");
                while (exercises.next()) {
                    exercisesText.append(exercises.getString("exercise_name")).append(", ");
                }
                if (exercisesText.length() > 0) {
                    exercisesText.setLength(exercisesText.length() - 2); // Remove last comma
                } else {
                    exercisesText.append("No exercises found.");
                }
                label.setText(exercisesText.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Method to fetch exercises for each day from the database and return as a ResultSet
        private ResultSet getExercisesForDay(String day) {
            return s.dayExercises(day);
        }

        // Method to fetch exercises based on search query
        private ResultSet getExercisesForDayWithSearch(String day, String searchText) {
            return s.dayExercises(day);
        }
	}
		
	public void run(){
		JFrame frame = new JFrame("Select Day");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new BorderLayout());
	        frame.setBackground(Color.PINK);
	        frame.setSize(1000, 1000); 
	        
	    	JPanel Buttons = new JPanel();
	    	Buttons.setLayout(new GridLayout(1, 7, 20, 0));
	    	
	    	JPanel Pages = new JPanel(new CardLayout());
	    	
	    	String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	    	for (String day: week) {
	            DayButton dayButton = new DayButton(Buttons, Pages, day);
	            Buttons.add(dayButton);
        }

         frame.add(Buttons, BorderLayout.NORTH); 
         frame.add(Pages, BorderLayout.CENTER); 

         frame.setVisible(true);
	}
}

