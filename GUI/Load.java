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
    public class DayButton extends JButton {
        private static final long serialVersionUID = 1L;

        DayButton(JPanel panel, JPanel day, String text) {
            super(text);
            panel.add(this);

            JPanel dayPage = new JPanel();
            dayPage.setBackground(Color.PINK);
            dayPage.setLayout(new BorderLayout());

            // Retrieve and display exercises for the selected day
            ResultSet exercises = DBconnection.dayExercises(text);
            try {
                ArrayList<String[]> exerciseList = UnpackRS.unpackDayExercise(exercises);
                StringBuilder exercisesText = new StringBuilder("Exercises for " + text + ":<br><ul>");
                
                for (String[] exercise : exerciseList) {
                    exercisesText.append("<li>").append(exercise[0]).append("</li>");
                }
                exercisesText.append("</ul>");

                JLabel dayLabel = new JLabel("<html>" + exercisesText + "</html>", SwingConstants.CENTER);
                dayLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                dayPage.add(dayLabel, BorderLayout.CENTER);

                // Create and add the "Search exercise for [day]" button
                JButton searchButton = new JButton("Search exercise for " + text);
                searchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Pass the current day (e.g., "Monday") to the ExerciseSearchGUI
                        ExerciseSearchGUI exerciseSearchPage = new ExerciseSearchGUI(text);
                        CardLayout layout = (CardLayout) day.getLayout();
                        day.add(exerciseSearchPage, "ExerciseSearch");
                        layout.show(day, "ExerciseSearch"); // Switch to the new page
                    }
                });

                // Add the search button to the bottom of the page
                dayPage.add(searchButton, BorderLayout.SOUTH);

                day.add(dayPage, text);
            } catch (Exception e) {
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

    public void run() {
        // Set the layout for this panel (Load)
        this.setLayout(new BorderLayout());
        this.setBackground(Color.PINK);

        JPanel Buttons = new JPanel();
        Buttons.setLayout(new GridLayout(1, 7, 20, 0));

        JPanel Pages = new JPanel(new CardLayout());

        String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : week) {
            DayButton dayButton = new DayButton(Buttons, Pages, day);
            Buttons.add(dayButton);
        }

        // Add components to this panel instead of frame
        this.add(Buttons, BorderLayout.NORTH);
        this.add(Pages, BorderLayout.CENTER);
    }
}
