package SimonSays_v2;

// Syeda Hafsa Peerzada

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//A GUI that sets up the difficulty level of the game

public class DifficultyScreen implements ActionListener {

    private final JFrame diffFrame = new JFrame();

    private final JPanel diffPanel = new JPanel();

    // creates the buttons with appropriate text

    private final JButton beginnerButton = new JButton("Beginner-Easy");
    private final JButton expertButton = new JButton("Expert-Moderate");
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

        switch (e.getActionCommand()) {
            //Create a time delay according to the players choice.
            case "Beginner-Easy":
                diffFrame.dispose();
                PlayScreen.TIMER_DELAY=500;
                new PlayScreen();
                break;

            case "Expert-Moderate":
                diffFrame.dispose();
                PlayScreen.TIMER_DELAY=410;
                new PlayScreen();
                break;


            case "Pro-Hard":
                diffFrame.dispose();
                PlayScreen.TIMER_DELAY=300;
                new PlayScreen();
                break;

        }

    }

}
