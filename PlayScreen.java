//okhttp-4.10.0-RC1
// Danny Le / Jiwon Kim / Syeda Hafsa Peerzada

// Danny's Methods: match(), check_input(), PlayScreen(), PLAY(), GAMEOVER(), HIGHSCORE(), keyboard_input()
// highscore_to_db(), keyboard_input(), showTopFive(), check_lowest_score(), check_lowest_score(), score_status() by Jiwon Kim
// colorChange(), blink(), buttonBlink() contributed by Syeda Hafsa Peerzada

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class PlayScreen implements ActionListener{

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

    // NEW SCORE FOUND screen
    private JFrame new_high_score;
    private JLabel initials;

    // Difficulty Screen variables
    public static int TIMER_DELAY = 1000;

    // High Score Database stuff
    private OkHttpClient client;
    private Response response;
    private Request request;
    private static boolean high_score_found = false;

//    public static void main(String[] args) {
//        new PlayScreen();
//    }


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
        PLAY();
        blink();

        client = new OkHttpClient().newBuilder()
                .build();
    }

    private void colorChange() {

        Timer blinkTimer = new Timer(TIMER_DELAY, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //to prevent the buttons from staying dark after they indicate the blinking pattern,
                //colorChange() sets the colors of the buttons to their original color

                btn_red.setBackground(Color.RED);

                btn_blue.setBackground(Color.BLUE);

                btn_yellow.setBackground(Color.YELLOW);

                btn_green.setBackground(Color.GREEN);

                ((Timer) e.getSource()).stop();
            }
        });
        blinkTimer.start();//initiate the timer
    }
    public void blink() {
        int i=1;

        //takes a character from the Simon pattern and checks it to blink each appropriate button with the time delay
        for (Character character : game.getPattern()) {
            i++;

            try {//puts the system to sleep for 100 milliseconds after each blink
                Thread.sleep(100);

            } catch (Exception e) {
            }

            if (character.equals('R')) {

                buttonblink(btn_red, i*TIMER_DELAY);

            } else if (character.equals('B')) {

                buttonblink(btn_blue, i*TIMER_DELAY);

            } else if (character.equals('Y')) {

                buttonblink(btn_yellow, i*TIMER_DELAY);

            } else if (character.equals('G')) {

                buttonblink(btn_green, i*TIMER_DELAY);
            }
        }
    }
    public void buttonblink(JButton button,int delay){

        //the blinking pattern is indicated by the the buttons turning dark.
        JButton tem = new JButton();
        tem.setBackground(button.getBackground().darker().darker().darker());

        Timer timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                button.setBackground(tem.getBackground());
                colorChange();

            }
        });
        timer.setRepeats(false);// so that the pattern only blinks once
        timer.start();//initiates the timer
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
    public void check_input(){
        // Checks the user's color input. If any input does not match the pattern the game is over.
        // Only adds to the score once the completed player_pattern matches the actual game pattern (game.getPattern())
        // Resets the player_pattern to "empty" once a new color is added to the game's main pattern
        if(!match(player_pattern, game.getPattern())){
            game.end_game();
            GAMEOVER();

        }
        else if ((match(player_pattern, game.getPattern())) && (player_pattern.size() == game.getPattern().size())){
            game.add_score();
            player_pattern.clear();
            game.add_to_pattern();
            blink();
            score_label.setText("Score: " + game.getScore());
//            System.out.println("NEW PATTERN: "+ game.getPattern());  // TODO KEEP FOR TESTING
        }
    }
    public void PLAY(){

        // Starts the game with 1 random color
        game.add_to_pattern();
//        System.out.println("START PATTERN: "+ game.getPattern());

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
    public void GAMEOVER(){
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
        score_status();

        if (high_score_found) {
            HIGHSCORE();
        }
    }

    public void HIGHSCORE(){

        // NEW_HIGH_SCORE screen is created to ask for user input for their initials

        new_high_score = new JFrame("CONGRATULATIONS YOU GOT NEW HIGH SCORE!");
        initials = new JLabel("NAME: YOUR INITIALS HERE");
        new_high_score.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel high_score_panel = new JPanel();
        high_score_panel.setLayout(new BoxLayout(high_score_panel, BoxLayout.Y_AXIS));

        JPanel keyboard_panel = new JPanel();
        keyboard_panel.setLayout(new GridLayout(2, 13));

        initials.setFont(new Font(initials.getName(), Font.BOLD, 35));
        new_high_score.add(initials, BorderLayout.PAGE_START);

        // Adds entirety of Alphabet as buttons in a 2x13 layout
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

    public void highscore_to_db() {
        String player_name = game.get_player_name().toString();
        int player_score = game.getScore();
        System.out.println(player_name);
        System.out.println(player_score);
        String url = "http://127.0.0.1:5000/user/" + player_name + "/score/" + player_score;
        System.out.println(url);

        Request request1 = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        try{
            Response response1 = client.newCall(request1).execute();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    // returns 0 if there's no score in the database
    // If not, returns the lowest score in the database.
    public int check_lowest_score() {
        JSONArray jsonArray;
        Request request = new Request.Builder()
                .url("http://127.0.0.1:5000/showLowestScore")
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();
            if (!jsonString.matches((".*\\d.*"))) {
                return 10000;
            }
            else {
                String s = jsonString.substring(1, jsonString.length() - 2);
                return Integer.parseInt(s);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            return 0;
        }
        return 0;
    }

    public void score_status() {
        int player_score = game.getScore();
        if (player_score > check_lowest_score()) {
            high_score_found = true;
        }
        else if (check_lowest_score() == 10000) {
            high_score_found = true;
        }
    }

    public void delLowestScore() {
        Request request2 = new Request.Builder()
                .url("http://127.0.0.1:5000/delLowestScore")
                .method("GET", null)
                .build();
        try{
            Response response2 = client.newCall(request2).execute();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray showTopFive() {
        Request request3 = new Request.Builder()
                .url("http://127.0.0.1:5000/showTopFive")
                .method("GET", null)
                .build();
        try{
            Response response3 = client.newCall(request3).execute();
            JSONArray jsonArray = new JSONArray(response3.body().string());
            return jsonArray;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public void keyboard_input(String letter){
        if(game.get_player_name().length() != 3){
            game.get_player_name().append(letter);
            initials.setText("NAME: \"" + game.get_player_name() + "\"");

            // When player finishes inputting 3 initials
            if(game.get_player_name().length() == 3){
                new_high_score.dispose();
                highscore_to_db();

                if (showTopFive().length() > 5) {
                    delLowestScore();
                }
            }
        }
    }


    public void actionPerformed(ActionEvent e) {
        // Switch case block to check for specific buttons being pressed
        switch (e.getActionCommand()) {

            // Closes the screen and opens the Main Menu
            case "MAIN MENU":
                play_frame.dispose();
                gameover_screen.dispose();
                new_high_score.dispose();
                new MainMenu();
                break;

            // "RED" Option was clicked
            case "RED":
                player_pattern.add('R');
                check_input();
                break;

            // "BLUE" Option was clicked
            case "BLUE":
                player_pattern.add('B');
                check_input();
                break;

            // "YELLOW" Option was clicked
            case "YELLOW":
                player_pattern.add('Y');
                check_input();
                break;

            // "BLUE" Option was clicked
            case "GREEN":
                player_pattern.add('G');
                check_input();
                break;

            // ALL CASES BELOW ADDS to the game's player_initials (get by using game.get_player_name())
            // Using both game.getScore() and game.get_player_name() to create a "HIGH SCORE"
            case "A":
                keyboard_input("A");
                break;

            case "B":
                keyboard_input("B");
                break;

            case "C":
                keyboard_input("C");

                break;
            case "D":
                keyboard_input("D");
                break;

            case "E":
                keyboard_input("E");
                break;

            case "F":
                keyboard_input("F");
                break;

            case "G":
                keyboard_input("G");
                break;

            case "H":
                keyboard_input("H");
                break;

            case "I":
                keyboard_input("I");
                break;

            case "J":
                keyboard_input("J");
                break;

            case "K":
                keyboard_input("K");
                break;

            case "L":
                keyboard_input("L");
                break;

            case "M":
                keyboard_input("M");
                break;

            case "N":
                keyboard_input("N");
                break;

            case "O":
                keyboard_input("O");
                break;

            case "P":
                keyboard_input("P");
                break;

            case "Q":
                keyboard_input("Q");
                break;

            case "R":
                keyboard_input("R");
                break;

            case "S":
                keyboard_input("S");
                break;

            case "T":
                keyboard_input("T");
                break;

            case "U":
                keyboard_input("U");
                break;

            case "V":
                keyboard_input("V");
                break;

            case "W":
                keyboard_input("W");
                break;

            case "X":
                keyboard_input("X");
                break;

            case "Y":
                keyboard_input("Y");
                break;

            case "Z":
                keyboard_input("Z");
                break;
        }
    }
}