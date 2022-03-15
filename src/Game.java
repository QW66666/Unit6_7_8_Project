import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Game
{
    private String answer;
    private Scanner scanner;
    private String userName;
    private final ArrayList<String> dictionary;

    public Game() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        dictionary = new ArrayList<>();
        importDictionary();
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
        board.printGrid();
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
