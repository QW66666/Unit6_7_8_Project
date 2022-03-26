import java.io.*;
import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;


public class Wordle
{
    private String answer;
    private Scanner scanner;
    private int numTries;
    private int currScore;
    private int gameNumber;
    private int wordsGuessed;
    private final ArrayList<String> dictionary;
    private ArrayList<Game> history;
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";


    public Wordle() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        dictionary = new ArrayList<>();
        history = new ArrayList<>();
        importDictionary();
        gameNumber = 0;
        currScore = 0;
        wordsGuessed = 0;
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
            System.out.println("(H)istory");
            System.out.println("(Q)uit");
            System.out.print("Enter choice: ");
            userChoice = scanner.nextLine();
            processChoice(userChoice);
        }
    }

    public void startGame()
    {
        Board board = new Board();
        //answer = dictionary.get(((int)(Math.random()*(dictionary.size()-1))+1));
        answer = "fires"; //TESTING
        numTries = 6;
        gameNumber++;
        int row = -1;
        int round = 1;
        boolean guessed = false;
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
                    guessed = true;
                    wordsGuessed++;
                    calculateScore();
                    System.out.println("Score: " + currScore);
                    if(numTries > 0)
                    {
                        round++;
                        System.out.println("\nWORD " + round);
                        board.resetBoard();
                        guessed = false;
                        row = -1;
                        numTries = 6;
                        answer = dictionary.get(((int)(Math.random()*(dictionary.size()-1))+1));
                    }

                }
            }
            if(numTries == 0)
            {
                Game game = new Game(gameNumber, wordsGuessed, currScore);
                history.add(game);
                //board.printGrid();
                currScore = 0;
                wordsGuessed = 0;
                if(!guessed)
            {
                System.out.println("ANSWER: " + answer);
            }
            }
        }
    }

    private ArrayList<String> checkWord(String input)
    {
        ArrayList<String> result = new ArrayList<>();
        boolean duplicateChecked = false;
        boolean haveDuplicate = checkDuplicate(answer);
        for(int i = 0; i < answer.length(); i++)
        {
            String inputLetter = input.substring(i, i+1).toUpperCase();
            String answerLetter = answer.substring(i, i+1).toUpperCase();
            if(answer.toUpperCase().contains(inputLetter))
            {
                if(answerLetter.equals(inputLetter))
                {
                    result.add("  " + GREEN + inputLetter + "  " + RESET);
                    if(checkDuplicate(input) && !haveDuplicate)
                    {
                        duplicateChecked = true;
                    }
                }
                else
                {
                    if(duplicateChecked && inputLetter.equalsIgnoreCase(findDuplicate(input)))
                    {
                        result.add("  " + RED + inputLetter + "  " + RESET);
                    }
                    else
                    {
                        result.add("  " + YELLOW + inputLetter + "  " + RESET);
                    }
                }
            }
            else
            {
                result.add("  " + RED + inputLetter + "  " + RESET);
            }
        }
        return result;
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
            processHistory();
        }
        else if(!choice.equals("q"))
        {
            System.out.println("INVALID CHOICE");
        }
    }

    public void processHistory()
    {
        if(gameNumber != 0)
        {
            Game highScoreGame = history.get(0);
            Game mostWordsGame = history.get(0);
            int highScore = history.get(0).getScore();
            int highWords = history.get(0).getWordsGuessed();
            System.out.println("GAME HISTORY");
            for(int i = 0; i < history.size(); i++)
            {
                Game game = history.get(i);
                System.out.println();
                game.printGameHistory();
                if(game.getScore() >= highScore)
                {
                    highScoreGame = history.get(i);
                }
                if(game.getWordsGuessed() >= highWords)
                {
                    mostWordsGame = history.get(i);
                }
            }
            System.out.println();
            System.out.println("BEST GAMES");
            System.out.println();
            System.out.println("Highest Score Game");
            highScoreGame.printGameHistory();
            System.out.println();
            System.out.println("Most Words Guessed Game");
            mostWordsGame.printGameHistory();
            System.out.println();
        }
        else
        {
            System.out.println();
            System.out.println("GAME HISTORY");
            System.out.println("HISTORY NOT AVAILABLE");
            System.out.println("NO GAMES EXISTS");
        }
    }

    private String findDuplicate(String word)
    {
        String[] temp = word.split("");
        String[] temp2 = word.split("");
        int count = 0;
        for (String s : temp) {
            for (int j = 0; j < temp.length; j++) {
                if (s.equals(temp2[j])) {
                    count++;
                    if (count > 1) {
                        return s;
                    }
                }
            }
            count = 0;
        }
        return "";
    }

    public static boolean checkDuplicate(String word)
    {
        String[] temp = word.split("");
        String[] temp2 = word.split("");
        int count = 0;
        for (String s : temp) {
            for (int j = 0; j < temp.length; j++) {
                if (s.equals(temp2[j])) {
                    count++;
                    if (count > 1) {
                        return true;
                    }
                }
            }
            count = 0;
        }
        return false;
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
