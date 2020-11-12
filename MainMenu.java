package SimonSays_v2;

import SimonSays_v2.PlayScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Matthew Abel

public class MainMenu implements ActionListener {
	
	// creates the menu frame with a title
	private final JFrame menuFrame = new JFrame("Simon Says (Main Menu)");
	
	// creates the menu panels
	private final JPanel menuTitlePanel = new JPanel();
	private final JPanel buttonsPanel = new JPanel();
	
	// creates the menu label
	private final JLabel menuLabel = new JLabel("Simon Says");
	
	// creates the buttons with the appropriate text
	private final JButton playButton = new JButton("Play");
	private final JButton highscoresButton = new JButton("High Scores");
	private final JButton exitButton = new JButton("Exit");
	
	
	public MainMenu() {
		
		// Sets the default preferred sized
		menuFrame.setPreferredSize(new java.awt.Dimension(650, 300));
		
		// formats menuLabel and sets dimensions
		menuLabel.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 50));
		menuLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		menuLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		menuLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(0));
		menuLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		menuLabel.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		menuLabel.setSize(new java.awt.Dimension(300, 60));
		
		// formats the buttons
        playButton.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 13));
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        
        highscoresButton.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 13));
        highscoresButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        highscoresButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        
        exitButton.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 13));
        exitButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        exitButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        
		// sets the borders and layout for both menu panels, and adds the buttons to the buttonsPanel
		menuTitlePanel.setPreferredSize(new java.awt.Dimension(650, 150));
		menuTitlePanel.setMaximumSize(new java.awt.Dimension(650, 150));
		menuTitlePanel.add(menuLabel);
		
		
		buttonsPanel.setPreferredSize(new java.awt.Dimension(250, 100));
		buttonsPanel.setMaximumSize(new java.awt.Dimension(250, 100));
		buttonsPanel.add(playButton);
		buttonsPanel.add(highscoresButton);
		buttonsPanel.add(exitButton);
		
		
		
		// adds the menuPanel to the menuFrame, and sets the size, title, and makes it visible.
		menuFrame.add(menuTitlePanel, BorderLayout.CENTER);
		menuFrame.add(buttonsPanel, BorderLayout.SOUTH);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setTitle("Simon Says (Main Menu)");
		menuFrame.pack();
		menuFrame.setVisible(true);
		
		
		
		// adds action listeners to the buttons.
		playButton.addActionListener(this);
		highscoresButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		
		
	} // end MainMenu method
	
	
	public static void main(String[] args) {
		
		new MainMenu();
		
	} // end main method
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// performs the appropriate action given the text from the button pushed, e
		switch (e.getActionCommand()) {
		
		// if play is pushed, closes the main menu and opens the Play Screen
		case "Play":
			menuFrame.dispose();
			new PlayScreen();
		
		// if High Scores is pushed, closes the main menu and opens the High Scores screen
		case "High Scores":
			menuFrame.dispose();
			// new HighScores();
		
		// if Exit is pushed, closes the main menu.
		case "Exit":
			menuFrame.dispose();
		
		
		} // end switch
		
		
	}// end actionPerformed method
    
} // end class MainMenu
