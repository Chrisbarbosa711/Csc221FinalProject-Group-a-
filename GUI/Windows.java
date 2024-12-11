package GUI;
import SQLtest.DBconnection;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Windows extends JFrame{
  
    public void myWindows(){
        final User instance = new User();
    
        // Create a JFrame (the window)
        final JFrame launchPage = new JFrame("Fitness application");
        JLabel LPGreeting = new JLabel("Welcome to the fitness tracker application", SwingConstants.CENTER);
        JLabel LogInLabel = new JLabel("please enter your name in the textbox below:", SwingConstants.CENTER);
        LPGreeting.setFont(new Font("Arial", Font.PLAIN, 30));

        final JButton submitButton = new JButton("Submit");
        JButton EnterApp = new JButton("Enter");
        EnterApp.setVisible(false);
        final JTextField textField = new JTextField(20);

        launchPage.add(LPGreeting, BorderLayout.NORTH);
        launchPage.add(textField);
        launchPage.add(submitButton);
        launchPage.add(EnterApp, BorderLayout.SOUTH);
        launchPage.add(LogInLabel, BorderLayout.CENTER);
        launchPage.pack();      
    
        // Set the size of the frame
        launchPage.setSize(475, 500);

        //button two will be used to transfer to the next page
        EnterApp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Load loadPanel = new Load();
                // Remove specific components
                launchPage.remove(LPGreeting);
                launchPage.remove(LogInLabel);
                launchPage.remove(textField);
                launchPage.remove(submitButton);
                launchPage.remove(EnterApp);
                
                launchPage.add(loadPanel, BorderLayout.CENTER);
                loadPanel.run();
                launchPage.revalidate();
                launchPage.repaint();
            }
        });

        submitButton.setBounds(800, 420, 100, 50);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                //deals with issue of user not entering anything or entering a whitespace only or before the input
                if(text.isEmpty() || Character.isWhitespace(text.charAt(0))) {
                	JOptionPane.showMessageDialog(null, "Please enter a valid username");
                }
                else {
                	submitButton.setVisible(false);
                    EnterApp.setVisible(true);
                }
            }
        });

        textField.setBounds(550, 420, 250, 50);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                instance.setName(text);
            }
        });
          
        launchPage.setVisible(true);
        launchPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Close DB connection when the window is closed
        launchPage.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DBconnection.closeConnection();
                System.out.println("Database connection closed.");
            }
        });
    }
}
