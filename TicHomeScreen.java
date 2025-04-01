/** TicHomeScreen.java
  * @description Homescreen for Tic-Tac-Toe game
  * @author T Vasilopoulos
  * @version 1.0 2023-04-02
  */


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TicHomeScreen extends JPanel{

    // attributes 
    private JLabel title, winnerLabel, score;
    private JButton play, end;
    private JPanel titlePanel, winnerPanel, scorePanel, playOrEndPanel;
    private boolean xWon;
    private int xScore, oScore;

    /** Constructor */
    public TicHomeScreen(boolean xWon, int xScore, int oScore){ // created JPanel
                
        this.xWon = xWon;
        this.xScore = xScore;
        this.oScore = oScore;
        
        // call methods that make JLabels, JButtons, JPanels
        makeText(); 
        setBackground(new Color(30, 20, 70));
        makeButtons();
        makePanels();
        
        // add panels to frame
        add(titlePanel);
        add(winnerPanel);
        add(playOrEndPanel);
        add(scorePanel);
    }

    /** Creates JLabels */
    public void makeText(){
        
        // JLabel for home screen title
        title = new JLabel("Tic-Tac-Toe");
        title.setFont(new Font("Verdana", Font.BOLD, 100));
        title.setForeground(new Color(67,99,149));
        
        // JLabel to display winner
        if (xWon) // boolean is passed through TicHomeScreen constructor called in TicDriver class
            winnerLabel = new JLabel("X WINS!");
        else
            winnerLabel = new JLabel("O WINS!");
        winnerLabel.setFont(new Font("Verdana", Font.BOLD, 40));
        winnerLabel.setForeground(new Color(215, 205, 250));
        
        // JLabel to display winners
        score = new JLabel("O's Score: " + oScore + "   X's Score: " + xScore); // oSore and xScore are passed through TicHomeScreen constructor called in TicDriver class
        score.setFont(new Font("Verdana", Font.BOLD, 40));
        score.setForeground(new Color(215, 205, 250));    
    }
    
    /** Creates JButtons */
    public void makeButtons(){
        
        // JButton called play which will start game
        play = new JButton("Play Again");
        play.setFont(new Font("Verdana", Font.BOLD, 30));
        play.setBackground(new Color(215, 205, 250));
        play.setForeground(new Color(30, 20, 70));
        
        // JButton called end which will end game 
        end = new JButton("End Game");
        end.setFont(new Font("Verdana", Font.BOLD, 30));
        end.setBackground(new Color(215, 205, 250));
        end.setForeground(new Color(30, 20, 70));
        
        // ButtonListener for play and end buttons
        ButtonListener listener = new ButtonListener(); 
        play.addActionListener(listener); // adds listener to play button
        end.addActionListener(listener); // adds listener to end button
    }

    /** Creates JPanels */
    public void makePanels(){
                
        // JPanel for title
        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(720, 200));
        titlePanel.add(title);
        titlePanel.setBackground(new Color(30, 20, 70));
        
        // JPanel displaying winner
        winnerPanel = new JPanel();
        winnerPanel.setPreferredSize(new Dimension(720, 200));
        winnerPanel.add(winnerLabel);
        winnerPanel.setBackground(new Color(30, 20, 70));
        
        // JPanel for play again and end buttons
        playOrEndPanel = new JPanel();
        playOrEndPanel.setPreferredSize(new Dimension(720, 200));
        playOrEndPanel.add(play);
        playOrEndPanel.add(end);
        playOrEndPanel.setBackground(new Color(30, 20, 70));

        // JPanel for score
        scorePanel = new JPanel();
        scorePanel.setPreferredSize(new Dimension(720, 200));
        scorePanel.add(score);
        scorePanel.setBackground(new Color(30, 20, 70));  
    }

    /** Subclass to handle events triggered by user action */
    private class ButtonListener implements ActionListener{

        /** Detector for actions in the home screen */
        public void actionPerformed(ActionEvent event){ 

            Object o = event.getSource();
            JButton b = (JButton) o;        // gets source of event and casts it as a JButton

            if (b == end){
                System.exit(0);  // if source of event is close button, game closes
            }else{
                TicDriver.startGame(); // if source of event is not close button, game restarts
            }
        }
    }
}