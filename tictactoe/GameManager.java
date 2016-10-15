import java.awt.*;
import java.io.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;

/**
 * Manages everything from player inputs to graphics. 


 * @author (Kyaw Thant , Patrick Lou) 
 * @version (6/6/2014)
 */
public class GameManager extends JPanel implements MouseListener
{

    UltimateGameBoard myBoard = new UltimateGameBoard();
    //used to determine the boundaries on  where you can play
    int ultimateGridLocX = -1;//one thing to keep in mind, we have to switch the X and Y coordinates because
    int ultimateGridLocY = -1;//jpanel uses column row while arrays uses row column
    int currentPlayer = 1;
    boolean AI = false;
    //int lastMove

    final String [] scene = new String[2];
    String currentScene = "titleScreen";
    public static void main(String[] args){
        new GameManager();
    }

    public GameManager()
    {
        //  ImageIcon icon = createImageIcon("images/middle.gif");
        JFrame myFrame = new JFrame("Ultimate Tic Tac Toe");
        myFrame.add(this);//^^initiates the JFrame

        //drawGrid();
        myFrame.setSize(608,800);

        addMouseListener(this);
        myFrame.setVisible(true);

    }

    public void paintComponent(Graphics g){

        if(currentScene.equals("gameScreen")){
            g.setColor(Color.white);
            g.fillRect(0,0,625,600);
            g.setColor(Color.black);
            g.fillRect(0,600,625,800); //^resets the screen
            if(myBoard.isUltimateGameOver()){
                paintVictoryScreen(g);

            } 
            paintIndicator(g);
            paintPlayerColor(g);
            paintUltimateGrid(g);
            resetButton(g);
        }
        else if(currentScene.equals("titleScreen")){
            g.setColor(Color.black);
            g.fillRect(0,0,625,400);

            g.setColor(Color.white);
            g.drawString("One Player",280,200);
            g.fillRect(0,400,625,800);
            g.setColor(Color.black);
            g.drawString("Two Player",280,600);

        }

    }

    public void resetButton(Graphics g){
        g.setColor(Color.black);
        g.fillRect(195,595,210,70);
        //Color something = new Co
        g.setColor(new Color(255, 0, 0));   
        g.fillRect(10,600,580,160);
        g.setColor(Color.black);
        g.drawString("Reset",285,630);
    }

    public void paintVictoryScreen(Graphics g){

        if(myBoard.getUltimateWinner() == 1){
            g.setColor(Color.cyan);

        }
        else{
            g.setColor(Color.yellow);

        }

        g.fillRect(0,0,625,800);
        g.setColor(Color.black);
        g.drawString ("Game Over, Player: " + currentPlayer + "Wins", 200, 200);
    }

    public void paintIndicator(Graphics g){
        if(!myBoard.isUltimateGameOver()){
            if(ultimateGridLocX == -1 && ultimateGridLocY == -1){
                g.setColor(Color.gray);
                g.fillRect(0,0,600,600); 
            }
            else{
                for(int r=0; r<3; r++){
                    for(int c=0;c<3; c++){
                        if(ultimateGridLocX == r && ultimateGridLocY == c){
                            g.setColor(Color.gray);
                            g.fillRect((r*200),(c*200),200,200);      
                        }
                    }
                }
            }
        }
    }

    public void paintPlayerColor(Graphics g){

        for(int row=0; row<3; row++){
            for(int col=0; col<3; col++){
                for(int r=0; r<3; r++){
                    for(int c=0;c<3; c++){
                        if(myBoard.getSmallBoard(col,row).getBoard()[c][r] == 1){
                            g.setColor(Color.blue);
                            g.fillRect((row*200)+10+(r*60),(col*200)+10+(c*60),60,60);      
                        }
                        if(myBoard.getSmallBoard(col,row).getBoard()[c][r] == 2){
                            g.setColor(new Color(255,165,0));
                            g.fillRect((row*200)+10+(r*60),(col*200)+10+(c*60),60,60);          
                        }

                    }
                }
            }
        }
        //g.fillRect(10,10,10+50,10+50); 
        // g.fillRect(10+60,10+60,60,60);
    }   

    public void paintUltimateGrid(Graphics g){
        g.setColor(Color.black);

        //vertial lines   

        g.fillRect(0,0,10,590); 
        g.fillRect(590,0,10,590);
        g.fillRect(190,0,20,590);
        g.fillRect(390,0,20,590);

        //Horizontal Lines
        g.fillRect(0,0,590,10);
        g.fillRect(0,190,590,20);
        g.fillRect(0,390,590,20);
        g.fillRect(0,590,600,10);

        //draws the inner grids
        paintGrid(g,10,10);
        paintGrid(g,210,10);
        paintGrid(g,410,10);
        paintGrid(g,10,210);
        paintGrid(g,10,410);
        paintGrid(g,210,210);
        paintGrid(g,210,410);
        paintGrid(g,410,210);
        paintGrid(g,410,410);

    }

    public void paintGrid(Graphics g,int xStart, int yStart){
        g.drawLine(xStart+60,yStart,xStart+60,yStart+180);
        g.drawLine(xStart+120,yStart,xStart+120,yStart+180);

        g.drawLine(xStart,yStart+60,xStart+180,yStart+60);
        g.drawLine(xStart,yStart+120,xStart+180,yStart+120);
    }

    public void switchPlayer(){
        if(currentPlayer == 1){
            currentPlayer = 2;   
        }
        else{
            currentPlayer = 1;
        }
    }

    public void makeMove(int mouseX, int mouseY){
        System.out.print('\f');
        if(currentScene.equals("gameScreen")){
            if(!myBoard.isUltimateGameOver()){
                int allowedLocX = ultimateGridLocX*200;
                int allowedLocY = ultimateGridLocY*200;

                if(!(mouseX>allowedLocX &&mouseX<allowedLocX +200&&mouseY>ultimateGridLocY*200&&mouseY<ultimateGridLocY*200+200)){
                    System.out.println("Out of Bounds");//decides which row aand column the player is allowed to play in
                }
                else{
                    //top part
                    if(mouseX>allowedLocX +10 && mouseX<allowedLocX +70 && mouseY>allowedLocY+10 && mouseY<allowedLocY+70){
                        if(myBoard.getValue(ultimateGridLocY,ultimateGridLocX,0,0) == 0 && myBoard.getSmallBoard(ultimateGridLocY,ultimateGridLocX).checkWinner() == 0){
                            myBoard.changeValue(ultimateGridLocY,ultimateGridLocX,0,0,currentPlayer);
                            ultimateGridLocY = 0;
                            ultimateGridLocX = 0;
                            switchPlayer();
                            aiMove();
                        }
                    }
                    else if(mouseX>allowedLocX +70 && mouseX<allowedLocX +130 && mouseY>allowedLocY+10 && mouseY<allowedLocY+70){
                        if(myBoard.getValue(ultimateGridLocY,ultimateGridLocX,0,1) == 0 && myBoard.getSmallBoard(ultimateGridLocY,ultimateGridLocX).checkWinner() == 0){
                            myBoard.changeValue(ultimateGridLocY,ultimateGridLocX,0,1,currentPlayer);
                            ultimateGridLocY = 0;
                            ultimateGridLocX = 1;
                            switchPlayer();
                            aiMove();
                        }
                    }
                    else if(mouseX>allowedLocX +130 && mouseX<allowedLocX +200 && mouseY>allowedLocY+10 && mouseY<allowedLocY+70){
                        if(myBoard.getValue(ultimateGridLocY,ultimateGridLocX,0,2) == 0 && myBoard.getSmallBoard(ultimateGridLocY,ultimateGridLocX).checkWinner() == 0){
                            myBoard.changeValue(ultimateGridLocY,ultimateGridLocX,0,2,currentPlayer);
                            ultimateGridLocY = 0;
                            ultimateGridLocX = 2;
                            switchPlayer();
                            aiMove();
                        }
                    }
                    //middle part
                    else if(mouseX>allowedLocX+10 && mouseX<allowedLocX+70 && mouseY>allowedLocY+70 && mouseY<allowedLocY+140){
                        if(myBoard.getValue(ultimateGridLocY,ultimateGridLocX,1,0) == 0 && myBoard.getSmallBoard(ultimateGridLocY,ultimateGridLocX).checkWinner() == 0){
                            myBoard.changeValue(ultimateGridLocY,ultimateGridLocX,1,0,currentPlayer);
                            ultimateGridLocY = 1;
                            ultimateGridLocX = 0;
                            switchPlayer();
                            aiMove();
                        }
                    }
                    else if(mouseX>allowedLocX+70 && mouseX<allowedLocX+130 && mouseY>allowedLocY+70 && mouseY<allowedLocY+140){
                        if(myBoard.getValue(ultimateGridLocY,ultimateGridLocX,1,1) == 0 && myBoard.getSmallBoard(ultimateGridLocY,ultimateGridLocX).checkWinner() == 0){
                            myBoard.changeValue(ultimateGridLocY,ultimateGridLocX,1,1,currentPlayer);
                            ultimateGridLocY = 1;
                            ultimateGridLocX = 1;
                            switchPlayer();
                            aiMove();
                        }
                    }
                    else if(mouseX>allowedLocX+130 && mouseX<allowedLocX+200 && mouseY>allowedLocY+70 && mouseY<allowedLocY+140){
                        if(myBoard.getValue(ultimateGridLocY,ultimateGridLocX,1,2) == 0 && myBoard.getSmallBoard(ultimateGridLocY,ultimateGridLocX).checkWinner() == 0){
                            myBoard.changeValue(ultimateGridLocY,ultimateGridLocX,1,2,currentPlayer);
                            ultimateGridLocY = 1;
                            ultimateGridLocX = 2;
                            switchPlayer();
                            aiMove();
                        }
                    }
                    //bottom part
                    else if(mouseX>allowedLocX+10 && mouseX<allowedLocX+70 && mouseY>allowedLocY+140 && mouseY<allowedLocY+200){
                        if(myBoard.getValue(ultimateGridLocY,ultimateGridLocX,2,0) == 0 && myBoard.getSmallBoard(ultimateGridLocY,ultimateGridLocX).checkWinner() == 0){
                            myBoard.changeValue(ultimateGridLocY,ultimateGridLocX,2,0,currentPlayer);
                            ultimateGridLocY = 2;
                            ultimateGridLocX = 0;
                            switchPlayer();
                            aiMove();
                        }
                    }
                    else if(mouseX>allowedLocX+70 && mouseX<allowedLocX+130 && mouseY>allowedLocY+140 && mouseY<allowedLocY+200){
                        if(myBoard.getValue(ultimateGridLocY,ultimateGridLocX,2,1) == 0 && myBoard.getSmallBoard(ultimateGridLocY,ultimateGridLocX).checkWinner() == 0){
                            myBoard.changeValue(ultimateGridLocY,ultimateGridLocX,2,1,currentPlayer);
                            ultimateGridLocY = 2;
                            ultimateGridLocX = 1;
                            switchPlayer();
                            aiMove();
                        }
                    }
                    else if(mouseX>allowedLocX+130 && mouseX<allowedLocX+200 && mouseY>allowedLocY+140 && mouseY<allowedLocY+200){
                        if(myBoard.getValue(ultimateGridLocY,ultimateGridLocX,2,2) == 0 && myBoard.getSmallBoard(ultimateGridLocY,ultimateGridLocX).checkWinner() == 0){
                            myBoard.changeValue(ultimateGridLocY,ultimateGridLocX,2,2,currentPlayer);
                            ultimateGridLocY = 2;
                            ultimateGridLocX = 2;
                            switchPlayer();
                            aiMove();
                        }
                    }

                    myBoard.getSmallBoard(ultimateGridLocY,ultimateGridLocX).checkWinner();
                    myBoard.checkUltimateWinner();

                }

                //System.out.print('\f');

                checkValidLocation(ultimateGridLocY, ultimateGridLocX);
                if(myBoard.isUltimateGameOver()){
                    switchPlayer();//to show the right player
                    ultimateGridLocY = -1;
                    ultimateGridLocX = -1;
                }
                myBoard.printUltimateScreen();
                System.out.println(mouseX +" "+ mouseY);
                System.out.println(ultimateGridLocY+", "+ultimateGridLocX + " Player:" + currentPlayer);
                repaint();
            }
            if(mouseX >200&&mouseX<400&&mouseY>610&&mouseY<670){//button for reset
                gameReset();
            }

        }else if(currentScene.equals("titleScreen")){
            if(mouseX >0 && mouseX<625 && mouseY>0 && mouseY<400){
                currentScene = "gameScreen";
                AI = true;
                gameReset();
            }
            if(mouseX >0 && mouseX<625 && mouseY>400 && mouseY<800){
                currentScene = "gameScreen";
                AI = false;
                gameReset();
            }
        }

    }

    public void checkValidLocation(int y, int x){
        if(myBoard.getSmallBoard(y,x).isGameOver()){
            ultimateGridLocY = -1;
            ultimateGridLocX = -1;  
        }
    }

    public void aiMove(){
        if(AI){
            checkValidLocation(ultimateGridLocY, ultimateGridLocX);
            int ultimateR = ultimateGridLocY;
            int ultimateC = ultimateGridLocX;
            if (ultimateGridLocY == -1 || ultimateGridLocX == -1){
                ultimateR = (int)(Math.random()*3);
                ultimateC = (int)(Math.random()*3);
                System.out.println(ultimateGridLocY +", "+ ultimateGridLocX);
                while (myBoard.getSmallBoard(ultimateR, ultimateC).checkWinner() != 0){
                    ultimateR =(int)(Math.random()*3);
                    ultimateC = (int)(Math.random()*3);
                }
            }
            System.out.println(ultimateR +", "+ ultimateC);
            aiSmallBoardMove(ultimateR,ultimateC,(int)(Math.random()*3),(int)(Math.random()*3));

            switchPlayer();
        }
    }

    public void aiSmallBoardMove(int ultimateRow, int ultimateCol, int row, int col){

        if(myBoard.getValue(ultimateRow, ultimateCol, row, col) == 0){
            myBoard.changeValue(ultimateRow,ultimateCol,row,col,currentPlayer);
            ultimateGridLocY = row;
            ultimateGridLocX = col;
        }else{
            aiSmallBoardMove(ultimateRow, ultimateCol, (int)(Math.random()*3), (int)(Math.random()*3));
        }

    }

    public void gameReset(){
        ultimateGridLocY = -1;
        ultimateGridLocX = -1; 
        myBoard.ultimateReset();
        System.out.println("Reset");
        currentPlayer = 1;
        repaint();
    }

    public void mouseClicked(MouseEvent event){
        //System.out.println(event.getX() +"  "+event.getY());
        //System.out.print("      adf");

    }

    public void mouseReleased(MouseEvent event){
        makeMove(event.getX(),event.getY());
    }

    // when a grid is won, gridLocXandY are reset to -1. This method resets toe method to allow you to click where you want
    public void mousePressed(MouseEvent event){
        int mouseX = event.getX();
        int mouseY = event.getY();

        if(ultimateGridLocX == -1 && ultimateGridLocY == -1){
            if(mouseX > 0 && mouseX < 200){
                if(mouseY > 0 && mouseY < 200){
                    ultimateGridLocY = 0;
                    ultimateGridLocX = 0;
                }
                else if(mouseY > 200 && mouseY < 400){
                    ultimateGridLocY = 1;
                    ultimateGridLocX = 0;              
                }
                else if(mouseY > 400 && mouseY < 600){
                    ultimateGridLocY = 2;
                    ultimateGridLocX = 0;              
                }
            }
            else if(mouseX > 200 && mouseX < 400){
                if(mouseY > 0 && mouseY < 200){
                    ultimateGridLocY = 0;
                    ultimateGridLocX = 1;
                }
                else if(mouseY > 200 && mouseY < 400){
                    ultimateGridLocY = 1;
                    ultimateGridLocX = 1;              
                }
                else if(mouseY > 400 && mouseY < 600){
                    ultimateGridLocY = 2;
                    ultimateGridLocX = 1;              
                }
            }
            else if(mouseX > 400 && mouseX < 600){
                if(mouseY > 0 && mouseY < 200){
                    ultimateGridLocY = 0;
                    ultimateGridLocX = 2;
                }
                else if(mouseY > 200 && mouseY < 400){
                    ultimateGridLocY = 1;
                    ultimateGridLocX = 2;              
                }
                else if(mouseY > 400 && mouseY < 600){
                    ultimateGridLocY = 2;
                    ultimateGridLocX = 2;              
                }
            }

            // ultimateGridLocY = 0;
            //ultimateGridLocX = 1;
        }

    }

    public void mouseEntered(MouseEvent event){
    }

    public void mouseExited(MouseEvent event){
    }

}

