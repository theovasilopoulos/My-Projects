This Java program implements a graphical Tic-Tac-Toe game using Swing. It consists of multiple classes:

TicDriver (Main Driver Class):

- Initializes the game frame and manages transitions between screens.

- Tracks scores for X and O.

- Controls game flow by switching between the start screen, game board, and home screen.

TicStartScreen (Start Screen Panel):

- Displays a title, an image, and buttons to start or close the game.

- Clicking "Play" transitions to the game board, while "Close" exits the application.

TicTacToe (Game Panel):

- Implements the 3x3 grid for Tic-Tac-Toe using JButton elements.

- Randomly selects which player (X or O) goes first.

- Handles user moves, updates the game board, and checks for a winner.

- Calls TicDriver methods to declare a winner and transition to the home screen.

TicHomeScreen (End Screen Panel):

- Displays the winner and the updated score.

- Offers options to play again or end the game.
