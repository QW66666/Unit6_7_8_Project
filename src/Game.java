import java.io.*;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Game
{
    private String answer;
    private Scanner scanner;
    private String userName;
    private final ArrayList<String> dictionary;
    private int highScore;
    private int numTries;

    public Game() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        dictionary = new ArrayList<>();
        importDictionary();
        numTries = 6;
    }

    public void menu()
    {
        String userChoice = "";
        System.out.println("Welcome to Wordle!");
        System.out.println("Can you guess the 5-letter word?");
        while (!userChoice.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("(N)ew Game");
            System.out.println("(H)ighest Score");
            System.out.println("(Q)uit");
            System.out.print("Enter choice: ");
            userChoice = scanner.nextLine();
            processChoice(userChoice);
        }
    }
    public void processChoice(String choice)
    {
        choice = choice.toLowerCase();
        if(choice.equals("n"))
        {
            startGame();
        }
        else if(choice.equals("h"))
        {
            //get high score
        }
        else
        {
            System.out.println("INVALID CHOICE");
        }
    }

    public void startGame()
    {
        Board board = new Board();
        //answer = dictionary.get(((int)(Math.random()*(dictionary.size()-1))+1));
        answer = "bells";
        int row = 0;
        while(numTries > 0)
        {
            board.printGrid();
            System.out.println(answer);
            System.out.print("Enter Your Guess: ");
            String userGuess = scanner.nextLine();
            if(userGuess.length() != 5)
            {
                System.out.println("NOT VALID WORD LENGTH");
            }
            else
            {
                for(int i = 0; i < 5; i++)
                {
                    board.changeBoard(row, i, checkWord(userGuess).get(i));
                }
                if(userGuess.equals(answer))
                {
                    System.out.println("YOU GUESSED THE WORD");
                    break;
                }
                row++;
                numTries++;
            }
        }
    }

    private ArrayList<String> checkWord(String input)
    {
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i < answer.length(); i++)
        {
            if(answer.contains(input.substring(i, i+1)))
            {
                if(answer.substring(i, i+1).equals(input.substring(i, i+1)))
                {
                    result.add("  " + input.substring(i, i+1) + "  ");
                }
                else
                {
                    result.add(" ?" + input.substring(i, i+1) + "? ");
                }
            }
            else
            {
                result.add("  " + "X" + "  ");
            }
        }
        return result;
    }

    private void importDictionary() throws FileNotFoundException
    {
        File file = new File("src\\words.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine())
        {
            dictionary.add(sc.nextLine());
        }


    }


}
