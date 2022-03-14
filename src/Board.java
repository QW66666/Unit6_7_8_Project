public class Board {
    private final String[][] board;

    public Board()
    {
        board = new String[6][5];
        for(int row = 0; row < board.length; row++)
        {
            for(int col = 0; col < board[0].length; col++)
            {
                board[row][col] = "_";
            }
        }
    }

    public void printGrid()
    {
        for(String[] arr : board)
        {
            for(String letter : arr)
            {
                System.out.print(letter + " ");
            }
            System.out.println();
        }
    }

}
