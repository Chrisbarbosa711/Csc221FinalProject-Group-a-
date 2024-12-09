package com.softwarefinal.gym;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Load {
	public class DayButton extends JButton {
		
		DayButton(JPanel panel, JPanel day, String text){
			super(text);
			panel.add(this);
			
			JPanel dayPage = new JPanel();
			dayPage.setBackground(Color.PINK);
			dayPage.setLayout(new BorderLayout());
			
			JLabel dayLabel = new JLabel(text, JLabel.CENTER);
			dayPage.add(dayLabel, BorderLayout.CENTER);
			day.add(dayPage, text);
			
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

