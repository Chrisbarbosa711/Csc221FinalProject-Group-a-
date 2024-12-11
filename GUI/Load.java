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
            StringBuilder exercisesText = new StringBuilder("Exercises for " + day + ":<br><ul>");

            for (String[] exercise : exerciseList) {
                exercisesText.append("<li>").append(exercise[0]).append("</li>");
            }
            exercisesText.append("</ul>");

            JLabel dayLabel = new JLabel("<html>" + exercisesText + "</html>", SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.PLAIN, 18));

            JPanel dayPage = new JPanel(new BorderLayout());
            dayPage.setBackground(Color.PINK);
            dayPage.add(dayLabel, BorderLayout.CENTER);

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
