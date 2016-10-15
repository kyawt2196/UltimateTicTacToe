
/**
 * This is the almagamation(however you spell it) of gameBoard in a 2d array of gameboards. It contains methods to change the values and the code to represent
the game graphically. 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UltimateGameBoard 
{
    // instance variables - replace the example below with your own
    private GameBoard [][] gameBoard = new GameBoard[3][3];
    private boolean ultimateGameOver = false;
    private int ultimateWinner = 0;

    public UltimateGameBoard()
    {
        for(int r=0; r<3; r++){
            for(int c=0; c<3; c++){
                gameBoard[r][c] = new GameBoard();
            }
        }

    }
    public void ultimateReset(){
        for(int row = 0; row<3; row++){
            for(int col =0; col<3; col++){
                gameBoard[row][col].boardReset();
            }
        }
        ultimateGameOver = false;
    }
    
    public void printUltimateScreen(){
        for(int row=0; row<3; row++){
            for(int i=0; i<3; i++){
                for(int col=0; col<3; col++){
                    System.out.print(gameBoard[row][col].getBoard()[i][0]+" "+gameBoard[row][col].getBoard()[i][1]+" "+gameBoard[row][col].getBoard()[i][2]);
                    System.out.print("   ");
                }
                System.out.println("");
            }
            System.out.println(" ");
        }
    }
    
    public int getUltimateWinner(){
          
             return ultimateWinner;
            
    }
    
    public boolean isUltimateGameOver(){
        return ultimateGameOver;
    }
    
    public void changeValue(int ultimateRow, int ultimateCol, int row, int col, int value){
        gameBoard[ultimateRow][ultimateCol].changeValueInBoard(row,col,value);
    }

    public GameBoard getSmallBoard(int r, int c){
        return gameBoard[r][c];
    }

    public int getValue(int row, int col, int r, int c){
        return gameBoard[row][col].getBoard()[r][c];
    }

    public void checkUltimateWinner(){
        if(!ultimateGameOver){
            for (int i=0; i<3; i++){
                if(gameBoard[i][0].checkWinner()==gameBoard[i][1].checkWinner() && gameBoard[i][1].checkWinner()==gameBoard[i][2].checkWinner() && gameBoard[i][2].checkWinner()!=0){
                    ultimateGameOver = true;
                   ultimateWinner = gameBoard[i][1].checkWinner();
                }
                if(gameBoard[0][i].checkWinner() == gameBoard[1][i].checkWinner() && gameBoard[1][i].checkWinner() == gameBoard[2][i].checkWinner() && gameBoard[2][i].checkWinner()!=0){
                    ultimateGameOver = true;
                  ultimateWinner = gameBoard[0][i].checkWinner();
                }
            }
            if(gameBoard[0][0].checkWinner() == gameBoard[1][1].checkWinner() && gameBoard[1][1].checkWinner() == gameBoard[2][2].checkWinner() && gameBoard[2][2].checkWinner() != 0){
                ultimateGameOver = true;
               ultimateWinner = gameBoard[0][0].checkWinner();
            }
            if(gameBoard[0][2].checkWinner() == gameBoard[1][1].checkWinner() && gameBoard[1][1].checkWinner() == gameBoard[2][0].checkWinner() && gameBoard[2][0].checkWinner() != 0){
                ultimateGameOver = true;
                ultimateWinner = gameBoard[0][2].checkWinner();
                      
            }
            System.out.println("GameOver = " + ultimateGameOver);
        }
      
       
    }
    

}

