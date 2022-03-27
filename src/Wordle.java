import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents the Wordle game.
 * The methods of this class runs the game
 *
 * @author Qihong Wu
 */
public class Wordle
{
    /** The randomized answer */
    private String answer;
    private Scanner scanner;
    /** The number of tries user has left */
    private int numTries;
    /** The user's score */
    private int currScore;
    /** The number of games user played */
    private int gameNumber;
    /** The number of words user guessed */
    private int wordsGuessed;
    /** The Arraylist of words */
    private final ArrayList<String> dictionary;
    /** The ArrayList of Game user played */
    private ArrayList<Game> history;
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";


    /**
     * Instantiates a new Wordle game.
     * dictionary is imported with the words in the word.txt file
     *
     * @throws FileNotFoundException the file not found exception
     */
    public Wordle() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        dictionary = new ArrayList<>();
        importDictionary();
        history = new ArrayList<>();
        gameNumber = 0;
        currScore = 0;
        wordsGuessed = 0;
    }

    /**
     * Prints out the menu.
     */
    public void menu()
    {
        String userChoice = "";
        System.out.println("Welcome to Wordle!");
        System.out.println("Can you guess the 5-letter word?");
        System.out.println("You have 6 tries to guess the 5-letter word");
        System.out.println("If you guessed it before 6 tries then the game keeps going, how many words can you guess?");
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

    /**
     * A loop will run the game until the user uses all 6 of their tries.
     * If the user guessed the correct word before 6 tries, the number
     * of tries resets and the game continues.
     *
     * Sets up a new Board
     */
    public void startGame()
    {
        Board board = new Board();
        //user starts with 6 tries
        numTries = 6;
        //randomize a word in dictionary and set it to the answer
        answer = dictionary.get(((int)(Math.random()*(dictionary.size()-1))+1));
        //adds 1 to number of games the user played
        gameNumber++;
        int row = -1;
        //round representing which word the user is guessing ex: (Word 3)
        int round = 1;
        boolean guessed = false;
        //repeat until user runs out of tries
        while(numTries > 0)
        {
            board.printGrid();
            System.out.print("Enter Your Guess: ");
            String userGuess = scanner.nextLine();
            //check if user input is valid word or valid length
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
                board.changeBoard(row, checkWord(userGuess));
                if(userGuess.equalsIgnoreCase(answer))
                {
                    board.changeBoard(row, checkWord(userGuess));
                    board.printGrid();
                    System.out.println("YOU GUESSED THE WORD");
                    guessed = true;
                    wordsGuessed++;
                    calculateScore();
                    System.out.println("Score: " + currScore);
                    //resets number of tries, board; game continues if user use less than 6 tries to guess the word
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
                //create a new Game object and stores it into history when game ends
                Game game = new Game(gameNumber, wordsGuessed, currScore);
                history.add(game);
                currScore = 0;
                wordsGuessed = 0;
                //prints out the answer if the answer wasn't guessed
                if(!guessed)
                {
                    System.out.println("ANSWER: " + answer);
                }
            }
        }
    }

    /**
     * Returns ArrayList of the input with color of each letter changed
     * depending on correctness of each letter in the input;
     *
     * Green if correct position.
     * Yellow if wrong position.
     * Red if letter is not in the answer.
     *
     * @param input the word to compare with the
     * @return ArrayList of the user input with the colors of the letters changed
     */
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

    /**
     * Processes the user's input choice
     * If choice is n, start game
     * If choice is h, print out game history
     * If choice is q, end the program and quit game
     *
     * @param choice the user's input choice
     */
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

    /**
     * Prints out the game history when user input is h.
     * Prints out the score and number of words guessed in past games if there is any.
     * Prints out games with the highest score and the most words guessed.
     */
    public void processHistory()
    {
        if(gameNumber != 0)
        {
            Game highScoreGame = history.get(0);
            Game mostWordsGame = history.get(0);
            int highScore = history.get(0).getScore();
            int highWords = history.get(0).getWordsGuessed();
            System.out.println("****GAME HISTORY****");
            for (Game game : history)
            {
                System.out.println();
                game.printGameHistory();
                if (game.getScore() >= highScore)
                {
                    highScoreGame = game;
                }
                if (game.getWordsGuessed() >= highWords)
                {
                    mostWordsGame = game;
                }
            }
            System.out.println();
            System.out.println("****BEST GAMES****");
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
            System.out.println("****GAME HISTORY****");
            System.out.println("HISTORY NOT AVAILABLE");
            System.out.println("NO GAMES EXISTS");
        }
    }

    /**
     * Returns the duplicate letter in a word
     *
     * @param word the word to check for duplicate letters
     * @return the duplicate letter in word, else return empty String.
     */
    private String findDuplicate(String word)
    {
        String[] temp = word.split("");
        String[] temp2 = word.split("");
        int count = 0;
        for (String str : temp)
        {
            for (int j = 0; j < temp.length; j++)
            {
                if (str.equals(temp2[j])) {
                    count++;
                    if (count > 1) {
                        return str;
                    }
                }
            }
            count = 0;
        }
        return "";
    }

    /**
     * Returns if there is any duplicate letters in a word
     *
     * @param word the word to check for duplicate
     * @return the true if there is duplicate letters, false if there isn't.
     */
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

    /**
     * Returns if the input is a valid word in dictionary
     *
     * @param input the word to check in dictionary
     * @return true if is a word in dictionary, false if not.
     */
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

    /**
     * Calculates the user's score based on number of tries left.
     */
    private void calculateScore()
    {
        currScore += 10*(numTries+1);
    }

    /**
     * Imports the words in word.txt file to ArrayList dictionary
     *
     */
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
