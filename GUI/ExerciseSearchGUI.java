package GUI;

import SQLtest.DBconnection;
import SQLtest.UnpackRS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExerciseSearchGUI extends JPanel { // Change from JFrame to JPanel

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String currentDay; // Variable to store the current day

    public ExerciseSearchGUI(String day) {
        this.currentDay = day; // Initialize with the current day

        // Set up layout and components
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Skip directly to the search page
        mainPanel.add(createSearchPage(), "Search");

        add(mainPanel, BorderLayout.CENTER); // Add the panel to the ExerciseSearchGUI panel
    }

    private JPanel createSearchPage() {
        JPanel searchPanel = new JPanel(new BorderLayout());

        JTextField searchField = new JTextField(20);
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String exerciseName = searchField.getText().trim();
                resultPanel.removeAll();
                if (!exerciseName.isEmpty()) {
                    try (ResultSet rs = DBconnection.searchExercise(exerciseName)) {
                        ArrayList<String> exercises = UnpackRS.unpackSearch(rs);

                        if (exercises != null && !exercises.isEmpty()) {
                            for (String exercise : exercises) {
                                JButton exerciseButton = new JButton(exercise);
                                exerciseButton.addActionListener(ev -> showExerciseDetails(exercise));
                                resultPanel.add(exerciseButton);
                            }
                        } else {
                            resultPanel.add(new JLabel("No exercises found."));
                        }
                    } catch (SQLException ex) {
                        resultPanel.add(new JLabel("Error retrieving data."));
                        System.out.println("Query failed: " + ex.getMessage());
                    }
                } else {
                    resultPanel.add(new JLabel("Please enter a valid exercise name."));
                }
                resultPanel.revalidate();
                resultPanel.repaint();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Exercise Name: "));
        inputPanel.add(searchField);
        inputPanel.add(submitButton);

        // Back button functionality
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            // Get the parent container (which should be a CardLayout)
            JPanel parentPanel = (JPanel) this.getParent();

            // Get the CardLayout for the parent container
            CardLayout layout = (CardLayout) parentPanel.getLayout();

            // Remove this ExerciseSearchGUI panel (essentially closing it)
            parentPanel.remove(this);

            // Switch back to the correct day panel
            layout.show(parentPanel, currentDay); // Use the stored current day to switch back
        });
        searchPanel.add(backButton, BorderLayout.SOUTH);

        searchPanel.add(inputPanel, BorderLayout.NORTH);
        searchPanel.add(new JScrollPane(resultPanel), BorderLayout.CENTER);

        return searchPanel;
    }

    private void showExerciseDetails(String exerciseName) {
    	System.out.println("Selected exercise: " + exerciseName);
        JPanel detailPanel = new JPanel(new BorderLayout());
        JLabel detailLabel = new JLabel("Fetching details for " + exerciseName + "...");
        detailPanel.add(detailLabel, BorderLayout.CENTER);
        mainPanel.add(detailPanel, "Details");
        cardLayout.show(mainPanel, "Details");

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Search"));
        detailPanel.add(backButton, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add Exercise for " + this.currentDay);
        addButton.addActionListener(e -> {
            // Directly use the exerciseName that is already defined in your code
            DBconnection.addExercise(this.currentDay, exerciseName);  // Call the addExercise method
            System.out.println("Exercise added to " + this.currentDay + ": " + exerciseName);
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
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
