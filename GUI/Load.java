package com.softwarefinal.gym;
import SQLtest.DBconnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*;

public class Load {
	public class DayButton extends JButton {
		//adding this allowed it to run in eclipse
		/** 
		 * 
		 */
		private static final long serialVersionUID = 1L;

		DayButton(JPanel panel, JPanel day, String text){
			super(text);
			panel.add(this);
			DBconnection s = new DBconnection(); //create a DBconnection object
			s.establishConnection(); //Establish the connection to the database

			JPanel dayPage = new JPanel();
			dayPage.setBackground(Color.PINK);
			dayPage.setLayout(new BorderLayout());


			//adds the dayexercises resultset to each day upon the press of the button
			ResultSet exercises = s.dayExercises(text);
			try {
                StringBuilder exercisesText = new StringBuilder("Exercises for " + text + ": ");
                // Iterate through the ResultSet and append the exercise names
                while (exercises.next()) {
                    exercisesText.append(exercises.getString("exercise_name")).append(", ");
                }
                // Remove the last comma
                if (exercisesText.length() > 0) {
                    exercisesText.setLength(exercisesText.length() - 2);
                }

                // Create a label with the exercises
                JLabel dayLabel = new JLabel(exercisesText.toString(), SwingConstants.CENTER);
                dayLabel.setFont(new Font("Arial", Font.PLAIN, 24));
                dayPage.add(dayLabel, BorderLayout.CENTER);
                day.add(dayLabel);
                day.add(dayLabel, text);
            } catch (SQLException e) {
                e.printStackTrace();
            }

			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					CardLayout layout = (CardLayout) day.getLayout();
	                layout.show(day, text);
				}
			});
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
