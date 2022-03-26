public class Game {
    private int score;
    private int wordsGussed;
    private int totalTries;
    private int gameNumber;

    public Game(int gameNumber,int wordsGussed, int score)
    {
        this.gameNumber = gameNumber;
        this.wordsGussed = wordsGussed;
        this.score = score;
    }

    public void printGameHistory()
    {
        System.out.println("------Game " + gameNumber + "------");
        System.out.println("Total Words Guessed: " + wordsGussed);
        System.out.println("Score: " + score);
    }


    public int getScore()
    {
        return score;
    }

    public int getWordsGuessed()
    {
        return wordsGussed;
    }

}
