import lib.StdDraw; // used for animating the 8 puzzle with user interactions

import java.awt.event.KeyEvent; // for the constants of the keys on the keyboard

// A program that partially implements the 8 puzzle.
public class EightPuzzle {
   // The main method is the entry point where the program starts execution.
   public static void main(String[] args) {
      // StdDraw setup
      // -----------------------------------------------------------------------
      // set the size of the canvas (the drawing area) in pixels
      StdDraw.setCanvasSize(500, 500);
      // set the range of both x and y values for the drawing canvas
      StdDraw.setScale(0.5, 3.5);
      // enable double buffering to animate moving the tiles on the board
      StdDraw.enableDoubleBuffering();

      // create a random board for the 8 puzzle
      Board board = new Board();

      // The main animation and user interaction loop
      // -----------------------------------------------------------------------
      while (true) {
         // draw the board, show the resulting drawing and pause for a short time
         board.draw();
         StdDraw.show();
         StdDraw.pause(100); // 100 ms
         //int[][] list = board.getList();

         board.solutionGame();

         // if the user has pressed the right arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
            // move the empty cell right
            board.moveRight();
         // if the user has pressed the left arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
            // move the empty cell left
            board.moveLeft();
         // if the user has pressed the up arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_UP))
            // move the empty cell up
            board.moveUp();
         // if the user has pressed the down arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
            // move the empty cell down
            board.moveDown();
      }
   }
}