package s2;
//import simonsaystest.MainMenu;
//import simonsaystest.PlayScreen;
//import java.awt.*;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//A GUI that sets up the difficulty level of the game

public class DifficultyScreen implements ActionListener {
	
	
	private final JFrame diffFrame = new JFrame();
	//private final JLabel diffLabel = new JLabel("CHOOE YOUR LEVEL");
	private final JPanel diffPanel = new JPanel();
	
	
		// creates the buttons with appropriate text
	private final JButton beginnerButton = new JButton("Beginner-Easy");
	private final JButton expertButton = new JButton("Expert-Medium");
	private final JButton proButton = new JButton("Pro-Hard");
		
		
	public DifficultyScreen() {
		
		
		// sets the borders and layout for the Panel
		diffPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		diffPanel.setLayout(new GridLayout(3, 3));
		//sets up the buttons
		diffPanel.add(beginnerButton);
		diffPanel.add(expertButton);
		diffPanel.add(proButton);
		
		
		//creates the borders for the buttons
		beginnerButton.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));
		expertButton.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));
		proButton.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));
 
		
		
		
			
		
			
		
		//sets up the Frame 
		diffFrame.add(diffPanel, BorderLayout.CENTER);
		diffFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		diffFrame.setTitle("Simon Says (Difficulty Level)");
		diffFrame.pack();
		diffFrame.setVisible(true);
		diffFrame.setTitle("CHOOSE YOUR LEVEL");
		
		
			
			// adds action listeners to the buttons.
		beginnerButton.addActionListener(this);
		expertButton.addActionListener(this);
		proButton.addActionListener(this);
			
			
			
	} 
		
		
	public static void main(String[] args) {
			
		new DifficultyScreen();
			
	} 
		
		
		@Override
	public void actionPerformed(ActionEvent e) {
			
			// performs the appropriate action depending on the button pressed, 
		switch (e.getActionCommand()) {
		case "Beginner-Easy":
			diffFrame.dispose();
			PlayScreen.TIMER_DELAY=1000;
			new PlayScreen();
			break;
			// if High Scores is pushed, closes the main menu and opens the High Scores screen
		case "Expert-Medium":
			diffFrame.dispose();
			PlayScreen.TIMER_DELAY=800;//500
			new PlayScreen();
			break;
				// new HighScores();
			// if Exit is pushed, closes the main menu.
		case "Pro-Hard":
			diffFrame.dispose();
			PlayScreen.TIMER_DELAY=500;
			new PlayScreen();
			break;
			
			
		} // end switch
			
			
	}// end actionPerformed method

} // end class MainMenu