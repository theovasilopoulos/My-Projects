/** TicTacToe.java
  * @description Tic-Tac-Toe game
  * @author T Vasilopoulos
  * @version 1.0 2023-04-02
  */
  
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class TicTacToe extends JPanel{
    
    // attributes
    private Random random;
    private boolean xTurn;
    private JLabel gameLabel;
    private JButton[] buttons;
    private JPanel titlePanel;
    private JPanel buttonPanel;
    
    /** Constructor */
    public TicTacToe(){
        
        // JLabel for top of frame when game is running
        gameLabel = new JLabel();
        gameLabel.setBackground(new Color(30, 20, 70));
        gameLabel.setForeground(new Color(67,99,149));
        gameLabel.setFont(new Font("Verdana", Font.BOLD, 55));
        gameLabel.setHorizontalAlignment(JLabel.CENTER); // sets gameLabel horizontal alignment: https://docs.oracle.com/en/java/javase/12/docs/api/java.desktop/javax/swing/JLabel.html#setHorizontalAlignment(int)
        
        // JPanel for gameLabel
        titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(new Color(30, 20, 70));
        titlePanel.add(gameLabel);
        
        // JPanel for game buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3)); // creates a 3x3 grid layout for buttonPanel
        buttonPanel.setBackground(new Color(30, 20, 70));
        
        // Game JButtons
        ButtonListener listener = new ButtonListener(); 
        buttons = new JButton[9]; // JButton array called buttons
        for (int i=0; i<9; i++){
            buttons[i] = new JButton(); // creates new JButton in every place in array
            buttonPanel.add(buttons[i]); // adds each JButton to buttonPanel
            buttons[i].setFont(new Font("Verdana", Font.BOLD, 120));
            buttons[i].addActionListener(listener); // adds listener to every JButton
        }
        
        // add JPanels to frame
        setLayout(new BorderLayout()); // creates new BorderLayout for frame
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        
        firstTurn();
    }
    
    /** Determines whose turn is first */
    public void firstTurn(){
        
        random = new Random(); 
        if (random.nextInt(2)==0){ // gets a random value (either 0 or 1), if value is 0...
            xTurn = true;                   
            gameLabel.setText("X turn");    // X plays first
        }else{
            xTurn = false;                  
            gameLabel.setText("O turn");    // O plays first
        }
    }
    
    /** Checks for winner */
    public void check(){
        
        // checks if X has won by filling a row
        for (int i=0; i<7; i+=3){
            if ((buttons[i].getText() == "X") && 
                (buttons[i+1].getText() == "X") && 
                (buttons[i+2].getText() == "X")){
            TicDriver.xWins(); // calls xWins in TicDriver class
            }
        }
        
        // checks if X has won by filling a column
        for (int i=0; i<3; i++){
            if ((buttons[i].getText() == "X") && 
                (buttons[i+3].getText() == "X") && 
                (buttons[i+6].getText() == "X")){
            TicDriver.xWins();
            }
        }
        
        // checks if X has won by filling diagonal
        if ((buttons[0].getText() == "X") && 
            (buttons[4].getText() == "X") && 
            (buttons[8].getText() == "X")){
        TicDriver.xWins();
        }
        
        // checks if X has won by filling other diagonal
        if ((buttons[2].getText() == "X") && 
            (buttons[4].getText() == "X") && 
            (buttons[6].getText() == "X")){
        TicDriver.xWins();
        }
        
        // checks if O has won by filling a row
        for (int i=0; i<7; i+=3){
            if ((buttons[i].getText() == "O") && 
                (buttons[i+1].getText() == "O") && 
                (buttons[i+2].getText() == "O")){
            TicDriver.oWins(); // calls oWins in TicDriver class
            }
        }
        
        // checks if O has won by filling a column
        for (int i=0; i<3; i++){
            if ((buttons[i].getText() == "O") && 
                (buttons[i+3].getText() == "O") && 
                (buttons[i+6].getText() == "O")){
            TicDriver.oWins();
            }
        }
        
        // checks if O has won by filling diagonal
        if ((buttons[0].getText() == "O") && 
            (buttons[4].getText() == "O") && 
            (buttons[8].getText() == "O")){
        TicDriver.oWins();
        }
        
        // checks if O has won by filling other diagonal
        if ((buttons[2].getText() == "O") && 
            (buttons[4].getText() == "O") && 
            (buttons[6].getText() == "O")){
        TicDriver.oWins();
        }
    }
    
    /** Subclass to handle events triggered by user action */
    private class ButtonListener implements ActionListener{
        /** Detector for actions in the game */
        public void actionPerformed(ActionEvent event){
            for (int i=0; i<9; i++){ // loops through each button
                if (event.getSource() == buttons[i]){  // if source of actionEvent is that button...
                    if (xTurn){                         // if it is X's turn...     
                        if (buttons[i].getText()==""){      // if button has not been pressed, then:
                            buttons[i].setForeground(new Color(255, 0, 0)); 
                            buttons[i].setText("X");            // display red X on button
                            xTurn = false;                      // change turns
                            gameLabel.setText("O turn");        // change gameLabel to display whose turn it is
                            check();                            // check to see if there's a winner yet                 
                        }
                    }else{                          
                        if (buttons[i].getText()==""){  // if it is O's turn...        
                            buttons[i].setForeground(new Color(0, 0, 255));
                            buttons[i].setText("O");            // display blue O on button
                            xTurn = true;                       // change turns
                            gameLabel.setText("X turn");        // change gameLabel to display whose turn it is
                            check();                            // check to see if there's a winner yet
                        }
                    }
                }
            }
        }
    }
}