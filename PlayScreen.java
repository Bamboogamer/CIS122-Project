package SimonSays_v1;
// Danny Le

// Creates the basic UI of the simon says game with a score and the 4 colors that can be pressed like buttons to play the game
// Currently the score only goes up when the RED button is pressed

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayScreen implements ActionListener{
    private boolean game_over = false;
    private boolean new_high_score = true;

    private int total_score = 0;
    private final JFrame play_frame = new JFrame("Simon Says (Play Screen)");
    private final JLabel score_label = new JLabel("Score: " + total_score);

    // Game Over UI Objects
    private final JFrame game_over_screen = new JFrame();
    private final JLabel game_over_label = new JLabel();
    private final JPanel game_over_panel = new JPanel();
    private final JButton btn_main_menu = new JButton("Main Menu");

    // Creates the colored buttons
    private final JButton btn_red = new JButton();
    private final JButton btn_blue = new JButton();
    private final JButton btn_yellow = new JButton();
    private final JButton btn_green = new JButton();


    public PlayScreen(){

        // By default, closes the frame when the window is closed, setting up the whole screen here
        play_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel main_screen_panel = new JPanel();
        main_screen_panel.setLayout(new BoxLayout(main_screen_panel, BoxLayout.Y_AXIS));

        // color_panel as a grid layout of 2 x 2 (the 4 buttons)
        JPanel colors_panel = new JPanel();
        colors_panel.setLayout(new GridLayout(2, 2));

        // The Score label is created then added to the main_screen_panel
        score_label.setPreferredSize(new Dimension(0, 50));
        main_screen_panel.add(score_label);

        // Sets the commands for each button pressed, Hides the TEXT from the UI
        btn_red.setActionCommand("RED");
        btn_blue.setActionCommand("BLUE");
        btn_yellow.setActionCommand("YELLOW");
        btn_green.setActionCommand("GREEN");

        // Adds an action listener to every button created
        btn_red.addActionListener(this);
        btn_blue.addActionListener(this);
        btn_yellow.addActionListener(this);
        btn_green.addActionListener(this);

        // For better visuals, I set the colors to the basic RED, BLUE, YELLOW, and GREEN colors
        // Can be changed to be less... aggressive to the eyes
        btn_red.setBackground(Color.RED);
        btn_blue.setBackground(Color.BLUE);
        btn_yellow.setBackground(Color.YELLOW);
        btn_green.setBackground(Color.GREEN);

        // Uniform size of each label should be 100 x 100 pixels
        btn_red.setPreferredSize(new Dimension(100, 100));
        btn_blue.setPreferredSize(new Dimension(100, 100));
        btn_yellow.setPreferredSize(new Dimension(100, 100));
        btn_green.setPreferredSize(new Dimension(100, 100));

        // Saw in examples, doesn't seem to do anything so I'm commenting this out for now
//        btn_red.setOpaque(true);
//        btn_blue.setOpaque(true);
//        btn_yellow.setOpaque(true);
//        btn_green.setOpaque(true);

        // Adds all the buttons to the colors_panel, then adds the colors_panel to the main_screen_panel
        colors_panel.add(btn_red);
        colors_panel.add(btn_blue);
        colors_panel.add(btn_yellow);
        colors_panel.add(btn_green);

        main_screen_panel.add(colors_panel);

        // Adds the main screen panel to the play screen (play_frame)
        play_frame.add(main_screen_panel);

        // Default sizing of the play_frame 400 x 600 pixels
        play_frame.setSize(400,600);
        play_frame.setVisible(true);

    }

    public static void main(String[] args) {
        new PlayScreen();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Test case when score reaches 5 to "end" the game
        if (total_score >= (5-1)){
            game_over = true;
        }

        if (game_over){
            // Disables all the game's buttons so you can't keep playing
            btn_red.setEnabled(false);
            btn_blue.setEnabled(false);
            btn_yellow.setEnabled(false);
            btn_green.setEnabled(false);

            // Creates a game over screen to return to the "main menu"
            btn_main_menu.setActionCommand("MAIN MENU");
            btn_main_menu.addActionListener(this);

            game_over_screen.setSize(200, 300);
            btn_main_menu.setPreferredSize(new Dimension(200, 200));

            game_over_panel.setLayout(new BorderLayout());
            game_over_label.setText("GAME OVER! Your Score was: " + (total_score+1));
            game_over_panel.add(game_over_label, BorderLayout.PAGE_START);
            game_over_panel.add(btn_main_menu, BorderLayout.PAGE_END);
            game_over_screen.add(game_over_panel);
            game_over_screen.show();
        }

        // Switch case block to check for specific buttons being pressed
        switch (e.getActionCommand()) {
            case "MAIN MENU":
                play_frame.dispose();
                game_over_screen.dispose();
                break;

            case "RED":

                // TESTING FOR SCORE CHANGING, needs to set a condition of the combination of commands match the random set of colors
                total_score+= 1;
                score_label.setText("Score: " + total_score);

                System.out.println("RED was pressed");
                break;
            case "BLUE":
                System.out.println("BLUE was pressed");
                break;
            case "YELLOW":
                System.out.println("YELLOW was pressed");
                break;
            case "GREEN":
                System.out.println("GREEN was pressed");
                break;
        }
    }
}
