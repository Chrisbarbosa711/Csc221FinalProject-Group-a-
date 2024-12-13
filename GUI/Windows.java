package GUI;
import SQLtest.DBconnection;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Windows extends JFrame{
  
    public void myWindows(){
    	//define an instance of the user class so we can use the info entered at launch-screen
    	final User instance = new User();

        //we create our launch window which serves are the basis of the application
        //along with essential labels and formatting effects
        final JFrame launchPage = new JFrame("Fitness application");
        launchPage.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        
        JLabel LPGreeting = new JLabel("Welcome to the fitness tracker application");
        JLabel LogInLabel = new JLabel("please enter your name in the textbox below:");
        LPGreeting.setFont(new Font("Arial", Font.PLAIN, 30));

        //button for submitting the login name
        final JButton submitButton = new JButton("Submit");
        
        //appears when the entered text for login is valid 
        JButton EnterApp = new JButton("Enter");
        EnterApp.setVisible(false);
        //we initially set this button visibility as false so that it only shows up when we want it to
        final JTextField textField = new JTextField(20);
        
        //add all the buttons,etc. to the window and call .pack() which makes it so everything that we set are at
        //there correct sizes and positions in the window 
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

        EnterApp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//we wrap the rest of the application functionality into a JPanel
            	//provided by the Load class, so once we enter from the launch screen, we remove everything
            	//that was previously visible
            	Load loadPanel = new Load();
                
            	//remove previous stuff
                launchPage.getContentPane().removeAll();
                
                //loadPanel is in a different layout so we have to change theJFrame layout
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
                //or the user name is too long
                if(text.isEmpty() || Character.isWhitespace(text.charAt(0)) || text.length() > 24) {
                	//shows up as a pane window that the user has to close in order to continue entering text
                	JOptionPane.showMessageDialog(null, "Please enter a valid username");
                }
                else {
                	//if the input is valid then we make the enter button visible for them to enter the application
                	submitButton.setVisible(false);
                    EnterApp.setVisible(true);
                }
            }
        });

        //set the positioning and the functionality of the textfield
        textField.setBounds(550, 420, 250, 50);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//save the users name as a string and add as name attribute to the user class
            	//which we defined an instance of previously
                String text = textField.getText();
                instance.setName(text);
            }
        });
        
        //set the visibility of the launch screen and the closure properties
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