package f1;
//Danny Lee
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayScreen implements ActionListener {

	private static SimonSays.Game game;
	private static ArrayList<Character> player_pattern;

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
	public static int TIMER_DELAY = 1000;

	public PlayScreen() {
		// Creates a new instance of the game and creates the PlayScreen UI
		game = new SimonSays.Game();
		player_pattern = new ArrayList<>();

		play_frame = new JFrame("Simon Says (Play Screen)");
		score_label = new JLabel("Score: " + game.getScore());

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
		blink();
	}
	
// colorChange(), blink(), buttonBlink() contributed by Syeda Hafsa Peerzada 
	
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
	
	public Boolean match(ArrayList<Character> player_pattern, ArrayList<Character> main_pattern) {
		boolean matches = true;

		for (int i = 0; i < player_pattern.size(); i++) {

			if (player_pattern.get(i) != main_pattern.get(i)) {
				matches = false;
				break;
			}
		}

		return matches;
	}

	public Boolean end_of_pattern(ArrayList<Character> player_pattern, ArrayList<Character> main_pattern) {
		return (player_pattern.size() == main_pattern.size());
	}

	public void check_input() {
		if (!match(player_pattern, game.getPattern())) {
			game.end_game();
			GAMEOVER_SCREEN();
		} else if (match(player_pattern, game.getPattern()) && end_of_pattern(player_pattern, game.getPattern())) {

			game.add_score();
			player_pattern.clear();
			game.add_to_pattern();
			blink();
			score_label.setText("Score: " + game.getScore());
			System.out.println("NEW PATTERN: " + game.getPattern()); // TODO KEEP FOR TESTING
		}
	}

	public void PLAY_SCREEN() {

		// Starts the game with 1 random color
		game.add_to_pattern();
		System.out.println("START PATTERN: " + game.getPattern());

		// By default, closes the frame when the window is closed, setting up the whole
		// screen here
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

		// For better visuals, I set the colors to the basic RED, BLUE, YELLOW, and
		// GREEN colors
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

		// Adds all the buttons to the colors_panel, then adds the colors_panel to the
		// main_screen_panel
		colors_panel.add(btn_red);
		colors_panel.add(btn_blue);
		colors_panel.add(btn_yellow);
		colors_panel.add(btn_green);

		play_panel.add(colors_panel);

		// Adds the main screen panel to the play screen (play_frame)
		play_frame.add(play_panel);

		// Default sizing of the play_frame 400 x 600 pixels
		play_frame.setSize(1000, 400);
		play_frame.setVisible(true);

	}

	public void GAMEOVER_SCREEN() {
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
			System.out.println("RED was pressed");
			player_pattern.add('R');
			check_input();
			break;

		// "BLUE" Option was clicked
		case "BLUE":
			System.out.println("BLUE was pressed");
			player_pattern.add('B');
			check_input();
			break;

		// "YELLOW" Option was clicked
		case "YELLOW":
			System.out.println("YELLOW was pressed");
			player_pattern.add('Y');
			check_input();
			break;

		// "BLUE" Option was clicked
		case "GREEN":
			System.out.println("GREEN was pressed");
			player_pattern.add('G');
			check_input();
			break;
		}

	}

}
