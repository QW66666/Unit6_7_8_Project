/**
 * the class representing a Game
 * stores information of each game played
 *
 * @author Qihong Wu
 */
public class Game {
    /** The score of the Game */
    private final int score;
    /** The number of words guessed in the Game */
    private final int wordsGuessed;
    /** The number of game */
    private final int gameNumber;

    /**
     * Constructs a new Game;
     * Stores the Game's number, number of words guessed, and the store
     *
     * @param gameNumber  the game number
     * @param wordsGuessed the number of words guessed
     * @param score       the score
     */
    public Game(int gameNumber,int wordsGuessed, int score)
    {
        this.gameNumber = gameNumber;
        this.wordsGuessed = wordsGuessed;
        this.score = score;
    }

    /**
     * Prints out the game results with total words guessed and the score
     */
    public void printGameHistory()
    {
        System.out.println("------Game " + gameNumber + "------");
        System.out.println("Total Words Guessed: " + wordsGuessed);
        System.out.println("Score: " + score);
    }


    /**
     * Gets the score.
     *
     * @return the score
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Gets the amount of words guessed.
     *
     * @return the words guessed
     */
    public int getWordsGuessed()
    {
        return wordsGuessed;
    }

}
