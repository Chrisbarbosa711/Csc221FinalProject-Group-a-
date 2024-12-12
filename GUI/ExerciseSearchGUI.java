package GUI;

//we have to import the following so that we can use the classes and interact with the database
import SQLtest.DBconnection;
import SQLtest.UnpackRS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExerciseSearchGUI extends JPanel {
	//we define the following private members which allows us to format the window and organize the panels
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String currentDay;
    private Load loadPanel;
    private JPanel pages;

    //constructor for the class which set the initial conditions of the window upon declaration of a ExerciseSearchGUI
    public ExerciseSearchGUI(Load loadPanel, String day, JPanel pages) {
        this.loadPanel = loadPanel;
        this.currentDay = day;
        this.pages = pages;

        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createSearchPage(), "Search");
        add(mainPanel, BorderLayout.CENTER);
    }

    
    private JPanel createSearchPage() {
    	//we initial the panels which we need to use in order to display the information for each exercise
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField(20);
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JButton submitButton = new JButton("Submit");
        
        //When the user presses the submit button we retrieve the data exercises from the database using the DBconnections function
        //searchExercises which pulls up a list of all exercises which matches the search queiry
        submitButton.addActionListener(e -> {
            String exerciseName = searchField.getText().trim();
            //the .removeAll gets rid of previous queiries from pressing submit 
            resultPanel.removeAll();
            //we need to add the following to parse what the user enters and retrieve the correct data
            if (!exerciseName.isEmpty()) {
            	//we try to see if what the user entered matches anything in the database
                try (ResultSet rs = DBconnection.searchExercise(exerciseName)) {
                	//if so then we store them in the following ArrayList
                    ArrayList<String> exercises = UnpackRS.unpackSearch(rs);
                    if (exercises != null && !exercises.isEmpty()) {
                    	//if the exercises are properly retrieved from the database then we display then as buttons on the screen
                        for (String exercise : exercises) {
                            JButton exerciseButton = new JButton(exercise);
                            exerciseButton.addActionListener(ev -> showExerciseDetails(exercise));
                            resultPanel.add(exerciseButton);
                        }
                        //otherwise we display the following error message
                    } else {
                        resultPanel.add(new JLabel("No exercises found."));
                    }
                } catch (SQLException ex) {
                    resultPanel.add(new JLabel("Error retrieving data."));
                }
            } 
            //in case the user enters something that is invalid
            else {
                resultPanel.add(new JLabel("Please enter a valid exercise name."));
            }
            resultPanel.revalidate();
            resultPanel.repaint();
        });
        
        //allows us to go back to the search screen so that we can add more exercises to the given day
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            loadPanel.refreshDayExercises(currentDay, pages); 
            CardLayout layout = (CardLayout) pages.getLayout();
            layout.show(pages, currentDay); 
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Exercise Name: "));
        inputPanel.add(searchField);
        inputPanel.add(submitButton);

        searchPanel.add(backButton, BorderLayout.SOUTH);
        searchPanel.add(inputPanel, BorderLayout.NORTH);
        searchPanel.add(new JScrollPane(resultPanel), BorderLayout.CENTER);

        return searchPanel;
    }
    
    
    //the following functions displays the retrieved exercises from the database
    private void showExerciseDetails(String exerciseName) {
        JPanel detailPanel = new JPanel(new BorderLayout());
        JLabel detailLabel = new JLabel("Fetching details for " + exerciseName + "...");
        detailPanel.add(detailLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Search"));
        detailPanel.add(backButton, BorderLayout.SOUTH);
        
        JButton addButton = new JButton("Add Exercise for " + currentDay);
        addButton.addActionListener(e -> {
            //message confirmation that the user has added the exercise
            JOptionPane.showMessageDialog(null, "You have successfully added " + exerciseName + " for " + currentDay);
            DBconnection.addExercise(currentDay, exerciseName);
        });
        detailPanel.add(addButton, BorderLayout.NORTH);

        
        try (ResultSet rs = DBconnection.exerciseDetail(exerciseName)) {
            String[] details = UnpackRS.unpackExerciseDetailsByName(rs);
            if (details != null && details[1] != null) {
                StringBuilder detailText = new StringBuilder("<html><h2>").append(details[1]).append("</h2><ul>");
                for (int i = 2; i < details.length; i++) {
                    if (details[i] != null && !details[i].equals("N/A")) {
                        detailText.append("<li>").append(details[i]).append("</li>");
                    }
                }
                detailText.append("</ul></html>");
                detailLabel.setText(detailText.toString());
            } else {
                detailLabel.setText("Details not found for " + exerciseName + ".");
            }
        } catch (SQLException ex) {
            detailLabel.setText("Error fetching details.");
        }

        mainPanel.add(detailPanel, "Details");
        cardLayout.show(mainPanel, "Details");
    }
}
