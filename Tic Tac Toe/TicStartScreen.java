/** TicStartScreen.java
  * @description Starting screen for Tic-Tac-Toe game
  * @author T Vasilopoulos
  * @version 1.0 2023-04-02
  */


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TicStartScreen extends JPanel{

    // attributes
    private ImageIcon image;
    private JLabel title;
    private JButton play, close;
    private JPanel titlePanel, buttonPanel, imagePanel;

    /** Constructor for starting screen */
    public TicStartScreen(){ // created JPanel
        
        // call methods that make JLabels, JButtons, JPanels, ImageIcon
        makeTitle();
        setBackground(new Color(0, 0, 0));
        makeButtons();
        makeImageAndAdd();
        makePanels();
        
        // add panels to frame
        add(titlePanel);
        add(buttonPanel);
        add(imagePanel, BorderLayout.NORTH);     
    }

    /** Creates JLabel */
    public void makeTitle(){
        
        // JLabel for starting screen title
        title = new JLabel("Tic-Tac-Toe");
        title.setFont(new Font("Verdana", Font.BOLD, 100));
        title.setForeground(new Color(67,99,149));
    }
    
    /** Creates ImageIcon in JLabel in JPanel */
    public void makeImageAndAdd(){
        
        // JLabel containing image.png
        image = new ImageIcon("image.png");
        JLabel imageLabel = new JLabel(image); // creates new JLabel called imageLabel containing image.png
        
        // JPanel for imagePanel
        imagePanel = new JPanel(); 
        imagePanel.setPreferredSize(new Dimension(720, 500)); 
        imagePanel.add(imageLabel, BorderLayout.CENTER); 
        imagePanel.setBackground(new Color(0, 0, 0)); 
    }
    
    /** Creates JButtons */
    public void makeButtons(){
        
        // JButton to start game
        play = new JButton("Play");
        play.setFont(new Font("Verdana", Font.BOLD, 30));
        play.setBackground(new Color(67,99,149));
        play.setForeground(new Color(30, 20, 70));
        
        // JButton to close game
        close = new JButton("Close");
        close.setFont(new Font("Verdana", Font.BOLD, 30));
        close.setBackground(new Color(67,99,149));
        close.setForeground(new Color(30, 20, 70));
        
        // Add ButtonListener to buttons play and close
        ButtonListener listener = new ButtonListener();
        play.addActionListener(listener);
        close.addActionListener(listener);
    }

    /** Creates JPanels */
    public void makePanels(){
        
        // JPanel for title
        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(720, 200));
        titlePanel.add(title);
        titlePanel.setBackground(new Color(0, 0, 0));
        
        // JPanel for buttons play and close
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(720, 50));
        buttonPanel.add(play);
        buttonPanel.add(close);
        buttonPanel.setBackground(new Color(0, 0, 0));
    }

    /** Subclass to handle events triggered by user action */
    private class ButtonListener implements ActionListener{

        /** Detector for actions in the starting screen */
        public void actionPerformed(ActionEvent event){

            Object o = event.getSource();
            JButton b = (JButton) o;        // gets source of event and casts it as a JButton

            if (b == close){ 
                System.exit(0);  // if source of event is close button, game closes
            }else{
                TicDriver.startGame(); // if source of event is not close button, game starts
            }
        }
    }
}