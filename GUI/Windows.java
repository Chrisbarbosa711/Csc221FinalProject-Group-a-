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
        launchPage.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(5, 10, 5, 10);
        
        JLabel LPGreeting = new JLabel("Welcome to the fitness tracker application");
        JLabel LogInLabel = new JLabel("please enter your name in the textbox below:");
        LPGreeting.setFont(new Font("Arial", Font.PLAIN, 30));

        final JButton submitButton = new JButton("Submit");
        JButton EnterApp = new JButton("Enter");
        EnterApp.setVisible(false);
        final JTextField textField = new JTextField(20);
        
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1; 
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.BOTH;
        launchPage.add(LPGreeting, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        launchPage.add(LogInLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        launchPage.add(textField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        launchPage.add(submitButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        launchPage.add(EnterApp, gbc);
        launchPage.pack();      
    
        // Set the size of the frame
        launchPage.setSize(600, 600);
        launchPage.setLocationRelativeTo(null);

        //button two will be used to transfer to the next page
        EnterApp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Load loadPanel = new Load();
                
                
                launchPage.getContentPane().removeAll();
                launchPage.setLayout(new BorderLayout());
                launchPage.add(loadPanel, BorderLayout.CENTER);
                loadPanel.run();
                launchPage.revalidate();
                launchPage.repaint();
            }
        });

        
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                //deals with issue of user not entering anything or entering a whitespace only or before the input
                if(text.isEmpty() || Character.isWhitespace(text.charAt(0)) || text.length() > 24) {
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
