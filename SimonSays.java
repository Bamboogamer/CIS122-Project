package SimonSays_v2;
/*
Code by Danny Le

TODO:

1. Allow for only 1 character at a time? Controller input will allow for only 1 button press for each color
2. Change PLAYSCREEN UI to blink the getPattern() from Game object
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class SimonSays {

    public static void main(String[] args) {
        Game game = new Game();
        game.test_game();
    }

    public static class Game{

        private ArrayList<Character> pattern = new ArrayList<>();
        private Boolean game_over = false;
        private int score = 0;
        private StringBuilder player_name = new StringBuilder();

        public StringBuilder get_player_name(){
            return player_name;
        }

        public Boolean game_status(){
            return game_over;
        }

        public void end_game(){
            game_over = true;
        }

        public int getScore(){
            return score;
        }

        public void add_score(){
            score++;
        }

        public void add_to_pattern(){
            Random rand = new Random();
            String colors = "RBGY";
            int rand_idx = rand.nextInt((colors.length()));
            pattern.add(colors.charAt(rand_idx));

        }

        public ArrayList<Character> getPattern(){
            return pattern;
        }

        // For testing purposes only
        public void test_game(){

            while (!game_status()){
                add_to_pattern();
                System.out.println(getPattern());
                for (int i = 0; i < getPattern().size(); i++){
                    // This could be from the Controller, replace system.in with controller input?
                    System.out.print("ENTER A COLOR: ");
                    Scanner input = new Scanner(System.in);
                    Character player_input = input.next().charAt(0);
                    Character curr_color = getPattern().get(i);

                    if (!(player_input.equals(curr_color) || player_input.equals(Character.toLowerCase(curr_color)))){
                        end_game();
                        System.out.println("FINAL score: " + getScore());
                        break;
                    }

                    if (i == getPattern().size()-1){
                        score++;

                        // Testing
                        System.out.println("Current score: " + getScore());
                    }
                }

            }
        }

    }

}
