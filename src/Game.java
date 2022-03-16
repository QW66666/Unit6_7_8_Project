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
    private int currScore;
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";


    public Game() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        dictionary = new ArrayList<>();
        importDictionary();
        currScore = 0;
        highScore = 0;
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
            System.out.println("High Score: " + highScore);
        }
        else if(!choice.equals("q"))
        {
            System.out.println("INVALID CHOICE");
        }
    }

    public void startGame()
    {
        Board board = new Board();
        //answer = dictionary.get(((int)(Math.random()*(dictionary.size()-1))+1));
        answer = "bells"; //TESTING
        int row = -1;
        numTries = 6;
        int round = 1;
        while(numTries > 0)
        {
            board.printGrid();
            System.out.println(answer); //TESTING
            System.out.print("Enter Your Guess: ");
            String userGuess = scanner.nextLine();
            if(userGuess.length() != 5)
            {
                System.out.println("NOT VALID WORD LENGTH");
            }
            else if(!validWord(userGuess))
            {
                System.out.println("NOT A WORD IN LIST");
            }
            else
            {
                numTries--;
                row++;
                for(int i = 0; i < 5; i++)
                {
                    board.changeBoard(row, i, checkWord(userGuess).get(i));
                }
                if(userGuess.equalsIgnoreCase(answer))
                {
                    for(int i = 0; i < 5; i++)
                    {
                        board.changeBoard(row, i, checkWord(userGuess).get(i));
                    }
                    board.printGrid();
                    System.out.println("YOU GUESSED THE WORD");
                    calculateScore();
                    System.out.println("Score: " + currScore);
                    if(numTries > 0)
                    {
                        round++;
                        System.out.println("\nWORD " + round);
                        board.resetBoard();
                        row = -1;
                        numTries = 6;
                        answer = dictionary.get(((int)(Math.random()*(dictionary.size()-1))+1));
                    }

                }
            }
            if(numTries == 0)
            {
                if (currScore > highScore)
                {
                    highScore = currScore;
                }
                currScore = 0;
            }
        }
    }

    private ArrayList<String> checkWord(String input)
    {
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i < answer.length(); i++)
        {
            String inputLetter = input.substring(i, i+1).toUpperCase();
            String answerLetter = answer.substring(i, i+1).toUpperCase();
            if(answerLetter.contains(inputLetter))
            {
                if(answerLetter.equals(inputLetter))
                {
                    result.add("  " + GREEN + inputLetter + "  " + RESET);
                }
                else
                {
                    result.add("  " + YELLOW + inputLetter + "  " + RESET);
                }
            }
            else
            {
                result.add("  " + RED + inputLetter + "  " + RESET);
            }
        }
        return result;
    }

    private boolean validWord(String input)
    {
        for(String str : dictionary)
        {
            if(input.equalsIgnoreCase(str))
            {
                return true;
            }
        }
        return false;
    }

    private void calculateScore()
    {
        currScore += 10*(numTries+1);
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
