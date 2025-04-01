/** TicDriver.java
  * @description Driver class for Tic-Tac-Toe game
  * @author T Vasilopoulos
  * @version 1.0 2023-04-02
  */
  
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TicDriver{
    
    // NOTE: This is a driver class, but it also contains methods which are called in other classes. 
    // It must be run with command "java TicDriver" and not "java TicDriver.java"
    
    // attributes
    private static JFrame frame; // static because the same frame is used to display panels of each class
    private static int xScore = 0, oScore = 0; // static because variables must be referenced in home screen class for score display

    /** Main method to drive the program */
    public static void main(String[] args){
         
        frame = new JFrame("Tic-Tac-Toe by Theo Vasilopoulos"); // creates new game frame

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets frame's close operator
        
        frame.setSize(720, 800); // sets frame size
        
        frame.setBounds(50, 50, 720, 800); // sets frame bounds
        
        frame.getContentPane().setBackground(new Color(0, 0, 0)); // sets frame background

        frame.getContentPane().add(new TicStartScreen()); // calls constructor for game's starting screen
        
        frame.setVisible(true); // makes frame visible
    }
    
    /** Incrementor if X wins */
    public static void xWins(){
        ++xScore;
        endGame(true, xScore, oScore); // true indicates that X won
    }
    
    /** Incrementor if O wins */
    public static void oWins(){
        ++oScore;
        endGame(false, xScore, oScore); // false indicates that O won
    }
    
    /** Starts game */
    public static void startGame(){
        frame.getContentPane().removeAll(); // removes content of frame so that game can be displayed
        frame.getContentPane().add(new TicTacToe()); // calls constructor of game
        frame.setVisible(true);
    }
    
    /** Ends game and takes user to home screen */
    public static void endGame(boolean xWon, int xScore, int oScore){
        frame.getContentPane().removeAll(); // removes content of frame so that home screen can be displayed
        frame.getContentPane().add(new TicHomeScreen(xWon, xScore, oScore)); // calls constructor of home screen
        frame.setVisible(true);
    }
}