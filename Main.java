package simonsaystest;


//INITIAL CODE FOR THE GAME
import java.util.*;

public class Main {


    public static void main(String[] args) {
        String simonPattern;
        String userPattern;
        int userScore;
        int i;

        Scanner in = new Scanner(System.in);
        Random rand = new Random();

        userScore = 0;
        int count = 0;

        simonPattern = "RGBY";
        List<Character> charlist = new ArrayList<Character>();
        for(char c:simonPattern.toCharArray()){
            charlist.add(c);
            Collections.shuffle(charlist);
        }
        simonPattern ="";
        for(int j = 0 ; j<charlist.size();j++)
        {
            simonPattern = simonPattern +charlist.get(j);
        }
        System.out.println("Simon Pattern is : " + simonPattern);

        while (true) { // infinite loop for each new input
            System.out.println("Enter " + simonPattern.length() + " characters of Pattern for match" );
            userPattern = in.nextLine();
            if (simonPattern.length() == userPattern.length())  //  comparison of user pattern length with simon pattern, if similar then continue
            {
                for (i = 0; i < simonPattern.length(); ++i) {   // check both strings character by character and see if they match
                    if (simonPattern.charAt(i) == userPattern.charAt(i)) { // in case of character match, continue to all remaining character match and then count score
                        ++count;
                        userScore = ++userScore;
                        //System.out.println(userScore);
                    }
                    else { // else break loop
                        break;
                    }
                }
                if(count==simonPattern.length()) { // if match count is equivalent to simon pattern length, the user pattern matches with the simon pattern.
                    System.out.println("All matched");
                    System.out.println("Your Score now : " + userScore);
                    count=0;
                    simonPattern = simonPattern + simonPattern.charAt(rand.nextInt(simonPattern.length()-1));
                    charlist = new ArrayList<Character>();
                    for(char c:simonPattern.toCharArray()){
                        charlist.add(c);
                        Collections.shuffle(charlist);
                    }
                    simonPattern ="";
                    for(int j = 0 ; j<charlist.size();j++)
                    {
                        simonPattern = simonPattern +charlist.get(j);
                    }//Generate a new pattern to continue with the game
                    System.out.println("New Simon Pattern: " + simonPattern);
                }
                else // else pattern not match
                    {//if user pattern is the same length as simon pattern but doesn't match with the other. Ask the user for more input
                        count=0;
                        System.out.println("Pattern does not match");
                        System.out.println("Enter User Pattern Again");
                }
            }
            else // If the length of user pattern is not same as simon pattern. ask user to enter new input
            {
                System.out.println("Strings length not matched, Re-enter pattern");
            }
        }
    }
}
