import java.util.ArrayList;

/**
 * This class represents the board for the game
 *
 * @author Qihong Wu
 */
public class Board
{
    /** 2D array of the board */
    private String[][] board;

    /**
     * Constructs a 6x5 board
     */
    public Board()
    {
        board = new String[6][5];
        for(int row = 0; row < board.length; row++)
        {
            for(int col = 0; col < board[0].length; col++)
            {
                board[row][col] = "  _  ";
            }
        }
    }

    /**
     * Prints out the board for the user to see the correctness of their guess
     *
     * Empty Board:
     *
     * _ _ _ _ _
     * _ _ _ _ _
     * _ _ _ _ _
     * _ _ _ _ _
     * _ _ _ _ _
     * _ _ _ _ _
     *
     * As user inputs their guess:
     *
     * A P P L E
     * M O V I E
     * _ _ _ _ _
     * _ _ _ _ _
     * _ _ _ _ _
     * _ _ _ _ _
     */
    public void printGrid()
    {
        for(String[] arr : board)
        {
            for(String letter : arr)
            {
                System.out.print(letter);
            }
            System.out.println();
        }
    }

    /**
     * Reset the board back to empty.
     */
    public void resetBoard()
    {
        for(int row = 0; row < board.length; row++)
        {
            for(int col = 0; col < board[0].length; col++)
            {
                board[row][col] = "  _  ";
            }
        }
    }

    /**
     * Takes in the user's input and puts it on the board
     *
     * @param row   the row of the 2D array to change
     * @param input the ArrayList you want to change the designated row with
     */
    public void changeBoard(int row, ArrayList<String> input)
    {
        for(int col = 0; col < board[0].length; col++)
        {
            board[row][col]  = input.get(col);
        }

    }

}
