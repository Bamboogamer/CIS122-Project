package SimonSays_v1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu implements ActionListener {
	
	// creates the menu frame with a title
	private final JFrame menuFrame = new JFrame("Simon Says (Main Menu)");
	
	// creates the menu panel
	private final JPanel menuPanel = new JPanel();
	
	// creates the buttons with the appropriate text
	private final JButton playButton = new JButton("Play");
	private final JButton highscoresButton = new JButton("High Scores");
	private final JButton exitButton = new JButton("Exit");
	
	
	public MainMenu() {
		
		
		// sets the borders and layout for the menuPanel, and adds the buttons to the panel
		menuPanel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
		menuPanel.setLayout(new GridLayout(0, 1));
		menuPanel.add(playButton);
		menuPanel.add(highscoresButton);
		menuPanel.add(exitButton);
		
		
		
		// adds the menuPanel to the menuFrame, and sets the size, title, and makes it visible.
		menuFrame.add(menuPanel, BorderLayout.CENTER);
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