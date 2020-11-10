package simonsaystest;
import java.util.*;

public class Main1 {
	
    public static void main(String[] args) {
    	
        String simonPattern;
        char userPattern;
        int userScore;

        Scanner in = new Scanner(System.in);
        Random rand = new Random();

        userScore = 0;
        int count = 0;
        String choice = "";

        String Pattern = "RGBY";

        do {

            List<Character> charlist = new ArrayList<Character>();
            for(char c:Pattern.toCharArray()){
                charlist.add(c);
                Collections.shuffle(charlist);
            }
            //System.out.println(charlist);
            simonPattern = charlist.get(0).toString();
            System.out.println("Simon Pattern: " + simonPattern);
            

            while (true) { // infinite loop for each new input
                for (int i = 0; i < simonPattern.length(); ++i) {   // to check both strings character by character 
                    userPattern = in.next().charAt(0);
                    if (simonPattern.charAt(i) == userPattern) { // in case of  character match, continue to all remaining characters, count and add to score
                        ++count;
                        userScore = ++userScore;
                        //System.out.println("Score now : " + userScore);
                    } else { // else break loop and Game Over
                        userScore = userScore - count;
                        System.out.println("----Game Over----\n");
                        System.out.println("Final Score: " + userScore);
                        System.out.println("\n");
                        break;
                    }
                }
                if (count == simonPattern.length()) { // if match count is equals to the Simon pattern length then user pattern matches with the Simon pattern
                    //System.out.println("All matched");
                    System.out.println("Total Score: " + userScore);
                    count = 0;
                    Collections.shuffle(charlist);
                    simonPattern = simonPattern + charlist.get(0);
                    System.out.println("\nSimon Pattern: " + simonPattern);
                }
                else
                {
                    count = 0;
                    System.out.println("Say \"Yes\" to play or press any key to exit");//Ask if the user wants to continue playing 
                    choice = in.next();
                    break;
                }
            }
        }while("yes".equals(choice.toLowerCase()));
    }
}

	