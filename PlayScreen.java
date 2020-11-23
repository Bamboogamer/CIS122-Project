package SimonSays_v2;
// Danny Le

// Creates the basic UI of the simon says game with a score and the 4 colors that can be pressed like buttons to play the game
// Currently the score only goes up when the RED button is pressed

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayScreen implements ActionListener{

    // if High Score was found
    private static boolean high_score_found = true;

    private JFrame new_high_score;
    private JLabel initials;

    // Game instance and player input pattern
    private static SimonSays.Game game;
    private static ArrayList<Character> player_pattern;

    // Main play frame
    private final JFrame play_frame;
    private final JLabel score_label;
    // Game Over UI Objects
    private final JFrame gameover_screen;
    private final JLabel game_over_label;
    private final JPanel game_over_panel;
    private final JButton btn_main_menu;

    // Creates the colored buttons
    private final JButton btn_red;
    private final JButton btn_blue;
    private final JButton btn_yellow;
    private final JButton btn_green;

    public PlayScreen(){
        // Creates a new instance of the game and creates the PlayScreen UI
        game = new SimonSays.Game();
        player_pattern = new ArrayList<>();

        play_frame = new JFrame("Simon Says (Play Screen)");
        score_label= new JLabel("Score: " + game.getScore());

        // Game Over UI Objects
        gameover_screen = new JFrame();
        game_over_label = new JLabel();
        game_over_panel = new JPanel();
        btn_main_menu = new JButton("Main Menu");

        // Creates the colored buttons
        btn_red = new JButton();
        btn_blue = new JButton();
        btn_yellow = new JButton();
        btn_green = new JButton();
        PLAY_SCREEN();
    }

    public void NEW_HIGH_SCORE(){
        new_high_score = new JFrame("CONGRATULATIONS YOU GOT NEW HIGH SCORE!");
        initials = new JLabel("NAME: YOUR INITIALS HERE");

        new_high_score.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel high_score_panel = new JPanel();
        high_score_panel.setLayout(new BoxLayout(high_score_panel, BoxLayout.Y_AXIS));

        JPanel keyboard_panel = new JPanel();
        keyboard_panel.setLayout(new GridLayout(2, 13));

        initials.setFont(new Font(initials.getName(), Font.BOLD, 35));
        new_high_score.add(initials, BorderLayout.PAGE_START);


        char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

        for (char c : alphabet) {
            JButton btn_letter = new JButton(String.valueOf(c));
            btn_letter.setActionCommand(String.valueOf(c));
            btn_letter.addActionListener(this);
            btn_letter.setPreferredSize(new Dimension(50, 50));
            btn_letter.setMaximumSize(new Dimension(75, 75));
            keyboard_panel.add(btn_letter);
        }

        new_high_score.setSize(1000,500);
        new_high_score.add(keyboard_panel);
        new_high_score.setVisible(true);
    }

    public Boolean match(ArrayList<Character> player_pattern, ArrayList<Character> main_pattern){
        boolean matches = true;

        for (int i = 0; i < player_pattern.size(); i++){

            if (player_pattern.get(i) != main_pattern.get(i)) {
                matches = false;
                break;
            }
        }

        return matches;
    }

    public Boolean end_of_pattern(ArrayList<Character> player_pattern, ArrayList<Character> main_pattern){
        return (player_pattern.size() == main_pattern.size());
    }

    public void check_input(){
        if(!match(player_pattern, game.getPattern())){
            game.end_game();
            GAMEOVER_SCREEN();
        }
        else if (match(player_pattern, game.getPattern()) && end_of_pattern(player_pattern, game.getPattern())){
            game.add_score();
            player_pattern.clear();
            game.add_to_pattern();
            score_label.setText("Score: " + game.getScore());
            System.out.println("NEW PATTERN: "+ game.getPattern());  // TODO KEEP FOR TESTING
        }
    }

    public void PLAY_SCREEN(){

        // Starts the game with 1 random color
        game.add_to_pattern();
        System.out.println("START PATTERN: "+ game.getPattern());

        // By default, closes the frame when the window is closed, setting up the whole screen here
        play_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel play_panel = new JPanel();
        play_panel.setLayout(new BoxLayout(play_panel, BoxLayout.Y_AXIS));

        // color_panel as a grid layout of 2 x 2 (the 4 buttons)
        JPanel colors_panel = new JPanel();
        colors_panel.setLayout(new GridLayout(1, 4));

        // The Score label is created then added to the main_screen_panel
        score_label.setPreferredSize(new Dimension(0, 50));
        score_label.setFont(new Font(score_label.getName(), Font.BOLD, 35));
        play_panel.add(score_label, BorderLayout.PAGE_START);

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

        // Adds all the buttons to the colors_panel, then adds the colors_panel to the main_screen_panel
        colors_panel.add(btn_red);
        colors_panel.add(btn_blue);
        colors_panel.add(btn_yellow);
        colors_panel.add(btn_green);

        play_panel.add(colors_panel);

        // Adds the main screen panel to the play screen (play_frame)
        play_frame.add(play_panel);

        // Default sizing of the play_frame 1000 x 400 pixels
        play_frame.setSize(1000,400);
        play_frame.setVisible(true);

    }

    public void GAMEOVER_SCREEN(){
        // Disables all the game's buttons so you can't keep playing
        btn_red.setEnabled(false);
        btn_blue.setEnabled(false);
        btn_yellow.setEnabled(false);
        btn_green.setEnabled(false);

        // Creates a game over screen to return to the "main menu"
        btn_main_menu.setActionCommand("MAIN MENU");
        btn_main_menu.addActionListener(this);

        gameover_screen.setSize(1000, 400);
        btn_main_menu.setPreferredSize(new Dimension(200, 200));

        game_over_panel.setLayout(new BorderLayout());
        game_over_label.setText("GAME OVER! Your Score was: " + game.getScore());
        game_over_label.setFont(new Font(game_over_label.getName(), Font.BOLD, 50));
        game_over_panel.add(game_over_label, BorderLayout.CENTER);
        game_over_panel.add(btn_main_menu, BorderLayout.PAGE_END);
        gameover_screen.add(game_over_panel);
        gameover_screen.setVisible(true);

        if(high_score_found){
            NEW_HIGH_SCORE();
        }

    }

    public static void main(String[] args) {
        new PlayScreen();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Switch case block to check for specific buttons being pressed
        switch (e.getActionCommand()) {

            // Closes the screen and opens the Main Menu
            case "MAIN MENU":
                play_frame.dispose();
                gameover_screen.dispose();
                new MainMenu();
                break;

            // "RED" Option was clicked
            case "RED":
//                System.out.println("RED was pressed");
                player_pattern.add('R');
                check_input();
                break;

            // "BLUE" Option was clicked
            case "BLUE":
//                System.out.println("BLUE was pressed");
                player_pattern.add('B');
                check_input();
                break;

            // "YELLOW" Option was clicked
            case "YELLOW":
//                System.out.println("YELLOW was pressed");
                player_pattern.add('Y');
                check_input();
                break;

            // "BLUE" Option was clicked
            case "GREEN":
//                System.out.println("GREEN was pressed");
                player_pattern.add('G');
                check_input();
                break;

            case "A":
//                System.out.println("A");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("A");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
                System.out.println(game.get_player_name());
                break;

            case "B":
//                System.out.println("B");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("B");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;

            case "C":
//                System.out.println("C");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("C");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "D":
//                System.out.println("D");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("D");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "E":
//                System.out.println("E");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("E");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "F":
//                System.out.println("F");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("F");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "G":
//                System.out.println("G");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("G");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "H":
//                System.out.println("H");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("H");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "I":
//                System.out.println("I");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("I");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "J":
//                System.out.println("J");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("J");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "K":
//                System.out.println("K");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("K");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "L":
//                System.out.println("L");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("L");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "M":
//                System.out.println("M");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("M");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "N":
//                System.out.println("N");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("N");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "O":
//                System.out.println("O");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("O");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "P":
//                System.out.println("P");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("P");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "Q":
//                System.out.println("Q");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("Q");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "R":
//                System.out.println("R");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("R");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "S":
//                System.out.println("S");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("S");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "T":
//                System.out.println("T");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("T");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "U":
//                System.out.println("U");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("U");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "V":
//                System.out.println("V");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("V");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "W":
//                System.out.println("W");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("W");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "X":
//                System.out.println("X");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("X");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "Y":
//                System.out.println("Y");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("Y");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
            case "Z":
//                System.out.println("Z");
                if(game.get_player_name().length() != 3){
                    game.get_player_name().append("Z");
                    initials.setText("NAME: \"" + game.get_player_name() + "\"");
                }
                else{
                    new_high_score.dispose();
                }
//                System.out.println(game.get_player_name());
                break;
        }
    }
}