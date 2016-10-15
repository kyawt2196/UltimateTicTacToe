
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
/**
 * sets up the gameboad of tictactoe. 2d array 3 by 3 grid. It will have a game state where winningPlayer 
will be 0 when no one has one. 1 when player 1 has won. 2 when player 2 has won. 
 * 
 * @author (Kyaw Thant) 
 * @version (0)
 */

public class GameBoard
{
    private int [][] board = {{0,0,0}, {0,0,0}, {0,0,0}};
    private int winningPlayer = 0;
    private boolean gameOver = false;

    public GameBoard()
    {

    }

    public void printScreen(){
        //temperary interface
        for(int r=0; r<3; r++){
            System.out.println(board[r][0] + " " + board[r][1] + " " + board[r][2]);                 
        }
    }

    public boolean isGameOver(){

        return gameOver;
    }

    public int [][] getBoard(){
        return board;
    }

    /**
     *Used to change the values
     * 
     * @param row, col, value(1 or 2)
     */
    public void changeValueInBoard(int row, int col, int value){
        if(!gameOver){
            board[row][col] = value;
        }
        checkWinner();
    }

    /**
     * to see if anyone won
     * 
     * @param  nada
     * @return     winningPlayer
     */
    public int checkWinner()
    {
        //checks horizontal and vertical grids

        if(!gameOver){
            for(int i=0; i<3; i++){
                if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][2] != 0){
                    winningPlayer = board[i][0];
                    gameOver = true;
                    return winningPlayer;
                }
                if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[2][i] != 0){
                    winningPlayer = board[0][i];
                    gameOver = true;
                    return winningPlayer;
                }
            } 
            //checks diagonals
            if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] != 0){
                winningPlayer = board[1][1];
                gameOver = true;
                return winningPlayer;
            }
            if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[1][1] != 0){
                winningPlayer = board[1][1];
                gameOver = true;
                return winningPlayer;
            }

            //checks for tie
            boolean flag = true;
            for (int r = 0; r<board.length ; r++){
                for (int c = 0; c<board[r].length; c++){
                    if  (board[r][c] == 0){
                        flag = false;
                    }
                }
                gameOver = flag;
            }
        }else{
            if(winningPlayer !=0){
                for (int r = 0; r<board.length ; r++){
                    for (int c = 0; c<board[r].length; c++){

                        board[r][c] =  winningPlayer;
                    }
                }
            }
            return winningPlayer;
        }
        return 0;
    }

    public void boardReset(){
        for (int r = 0; r<3;r++){
            for (int c = 0; c<3;c++){
                board[r][c]= 0;
            }
        }
        gameOver = false;
    }

    public int getWinner(){
        return winningPlayer;
    }
}

