package com.softwarefinal.gym;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

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
