package SimonSays_v3;
import javax.swing.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;


public class  HighScores implements ActionListener {

    // creates the menu frame with a title
    private final JFrame scoresFrame = new JFrame("Simon Says (High Scores)");

    // creates the menu panels
    private final JPanel scoresTitlePanel = new JPanel();
    private final JPanel buttonsPanel = new JPanel();
    private final JPanel scoresPanel = new JPanel();

    // creates the menu label
    private final JLabel scoresLabel = new JLabel("High Scores");


    // creates the buttons with the appropriate text
    private final JButton menuButton = new JButton("Main Menu");
    private final JButton exitButton = new JButton("Exit");



    public HighScores() {

        // Sets the default preferred sized
        scoresFrame.setPreferredSize(new java.awt.Dimension(650, 800));

        // formats menuLabel and sets dimensions
        scoresLabel.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 50));
        scoresLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoresLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        scoresLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(0));
        scoresLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        scoresLabel.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        scoresLabel.setSize(new java.awt.Dimension(300, 60));

        // formats the buttons
        menuButton.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 13));
        menuButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);


        exitButton.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 13));
        exitButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        exitButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);

        // sets the borders and layout for both menu panels, and adds the buttons to the buttonsPanel
        scoresTitlePanel.setPreferredSize(new java.awt.Dimension(650, 150));
        scoresTitlePanel.setMaximumSize(new java.awt.Dimension(650, 500));
        scoresTitlePanel.add(scoresLabel);

        scoresPanel.setPreferredSize(new java.awt.Dimension(250, 500));
        scoresPanel.setMaximumSize(new java.awt.Dimension(250, 500));
        scoresPanel.setLayout(new GridLayout(6, 2));
        scoresPanel.setAlignmentY(javax.swing.SwingConstants.CENTER);

        for (int j = 0; j < getHighScores().size(); j++) {
            scoresPanel.add(new JLabel(getHighScores().get(j)));
        }

        buttonsPanel.setPreferredSize(new java.awt.Dimension(250, 100));
        buttonsPanel.setMaximumSize(new java.awt.Dimension(250, 100));
        buttonsPanel.add(menuButton);
        buttonsPanel.add(exitButton);


        // adds the menuPanel to the menuFrame, and sets the size, title, and makes it visible.
        scoresFrame.add(scoresTitlePanel, BorderLayout.NORTH);
        scoresFrame.add(scoresPanel, BorderLayout.CENTER);
        scoresFrame.add(buttonsPanel, BorderLayout.SOUTH);
        scoresFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scoresFrame.setTitle("Simon Says (Main Menu)");
        scoresFrame.pack();
        scoresFrame.setVisible(true);


        // adds action listeners to the buttons.
        menuButton.addActionListener(this);
        exitButton.addActionListener(this);



    } // end MainMenu method


    public static void main(String[] args) {
        new HighScores();

    } // end main method

    public static ArrayList<String> getHighScores() {
        try {
            // gets the high score data from the flask database server and returns it as a string

            HttpClient httpClient = HttpClientBuilder.create().build(); // build the client

            HttpGet httpGet = new HttpGet("http://127.0.0.1:5000/showTopFive"); //  set the flask server url for the getEntity method

            HttpResponse response = httpClient.execute(httpGet); // get the server response for the getEnity method

            HttpEntity entity = response.getEntity(); // gets the entity from the response from the server url

            String httpString = EntityUtils.toString(entity); // converts the entity to a string using EntityUtils


            int beginIndex = 0;
            beginIndex=httpString.indexOf("[",beginIndex+1);

            ArrayList<String> userInfoArrayList = new ArrayList<String>();
            ArrayList<String> userNameArrayList = new ArrayList<String>();
            ArrayList<String> userScoreArrayList = new ArrayList<String>();
            ArrayList<String> checkNumberofUsersArrayList = new ArrayList<String>();

            checkNumberofUsersArrayList.add(httpString.split(",")[0]);

            while(beginIndex>=0) {
                int endind1 = httpString.indexOf("]",beginIndex+1);
                String userinfoString = httpString.substring(beginIndex+1, endind1);

                userNameArrayList.add(userinfoString.split(",")[0]);
                userNameArrayList.add(userinfoString.split(",")[1]);

                beginIndex = httpString.indexOf("[",endind1);

            }

            return userNameArrayList;
        }
        catch (Exception e) { // error control for server not returning the high scores data
            System.out.println(e.getStackTrace());
        }
        ArrayList<String> failArrayList = new ArrayList<String>();

        for(int i=0; i<11;i++) {
            failArrayList.add("***");
        }
        return failArrayList;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        // performs the appropriate action given the text from the button pushed, e
        switch (e.getActionCommand()) {

            // if play is pushed, closes the main menu and opens the Play Screen
            case "Main Menu":
                scoresFrame.dispose();
                new MainMenu();

                // if Exit is pushed, closes the main menu.
            case "Exit":
                scoresFrame.dispose();


        } // end switch


    }// end actionPerformed method

} // end class HighScores
