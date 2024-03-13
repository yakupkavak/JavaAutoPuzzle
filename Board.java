import lib.StdDraw; // the StdDraw class in the lib package is used for drawings 

import java.awt.Color; // used for coloring the board
import java.awt.Point; // used for the positions of the tiles and the empty cell
import java.util.ArrayList;

// A class that is used for modeling the board in the 8 puzzle.
public class Board {
   // Data fields: the class variables (actually constants here)
   // --------------------------------------------------------------------------
   // the background color used for the empty cell on the board
   private static final Color backgroundColor = new Color(145, 234, 255);
   // the color used for drawing the boundaries around the board
   private static final Color boxColor = new Color(31, 160, 239);
   // the line thickness value for the boundaries around the board
   // (it is twice the value used for the tiles as only half of it is visible)
   private static final double lineThickness = 0.02;

   private int[][] twoDimensionalNumberArray = new int[3][3];
   private int[] oneDimensionalNumber = new int[9];

   // Data fields: the instance variables
   // --------------------------------------------------------------------------
   // a matrix to store the tiles on the board in their current configuration
   private Tile[][] tiles = new Tile[3][3];
   // the row and the column indexes of the empty cell
   private int emptyCellRow, emptyCellCol;

   // The default constructor creates a random board
   // --------------------------------------------------------------------------
   public Board() {
      // create an array that contains each number from 0 to 8
      int[] numbers = new int[9];
      for (int i = 0; i < 9; i++)
         numbers[i] = i;
      // randomly shuffle the numbers in the array by using the randomShuffling
      // method defined below
      randomShuffling(numbers);
      //numbers aray changed in here

      // create the tiles and the empty cell on the board by using the randomly
      // shuffled numbers from 0 to 8 and store them in the tile matrix
      int arrayIndex = 0; // the index of the current number in the numbers array
      // for each tile in the tile matrix
      for (int row = 0; row < 3; row++)
         for (int col = 0; col < 3; col++) {
            // create a tile if the current value in the numbers array is not 0
            if (numbers[arrayIndex] != 0)
               // create a tile with the current number and assign this tile to
               // the current cell of the tile matrix
               tiles[row][col] = new Tile(numbers[arrayIndex]);
            // otherwise, this is an empty cell
            else {
               // assign the row and the column indexes of the empty cell
               emptyCellRow = row; // --- numara 0 ise orayı boş olarak tanımladık
               emptyCellCol = col;
            }
            // increase the array index by 1
            arrayIndex++;
         }

      int loopNum = 0;
      for (int i = 0;i <3;i++){
         for (int j = 0; j< 3; j++){
            twoDimensionalNumberArray[i][j] = numbers[loopNum]; //make it two dimensional
            loopNum++;
         }
      }
      oneDimensionalNumber = numbers;
      drawMessage(isSolvable(numbers));
      isSolvable(numbers);
      solutionGame(twoDimensionalNumberArray);

   }

   // The method(s) of the Board class
   // --------------------------------------------------------------------------
   // An inner method that randomly reorders the elements in a given int array.
   private void randomShuffling(int[] array) {
      // loop through all the elements in the array
      for (int i = 0; i < array.length; i++) {
         // create a random index in the range [0, array.length - 1]
         int randIndex = (int) (Math.random() * array.length);
         // swap the current element with the randomly indexed element
         if (i != randIndex) {
            int temp = array[i];
            array[i] = array[randIndex];
            array[randIndex] = temp;
         }
      }
   }

   // A method for moving the empty cell right
   public boolean moveRight() {
      // the empty cell cannot go right if it is already at the rightmost column
      if (emptyCellCol == 2)
         return false; // return false as the empty cell cannot be moved
      // replace the empty cell with the tile on its right
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow][emptyCellCol + 1];
      tiles[emptyCellRow][emptyCellCol + 1] = null;
      // update the column index of the empty cell
      emptyCellCol++;
      // return true as the empty cell is moved successfully
      return true;
   }

   // A method for moving the empty cell left
   public boolean moveLeft() {
      // the empty cell cannot go left if it is already at the leftmost column
      if (emptyCellCol == 0)
         return false; // return false as the empty cell cannot be moved
      // replace the empty cell with the tile on its left
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow][emptyCellCol - 1];
      tiles[emptyCellRow][emptyCellCol - 1] = null;
      // update the column index of the empty cell
      emptyCellCol--;
      // return true as the empty cell is moved successfully
      return true;
   }

   // A method for moving the empty cell up
   public boolean moveUp() {
      // the empty cell cannot go up if it is already at the topmost row
      if (emptyCellRow == 0)
         return false; // return false as the empty cell cannot be moved
      // replace the empty cell with the tile above it
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow - 1][emptyCellCol];
      tiles[emptyCellRow - 1][emptyCellCol] = null;
      // update the row index of the empty cell
      emptyCellRow--;
      // return true as the empty cell is moved successfully
      return true;
   }

   // A method for moving the empty cell down
   public boolean moveDown() {
      // the empty cell cannot go down if it is already at the bottommost row
      if (emptyCellRow == 2)
         return false; // return false as the empty cell cannot be moved
      // replace the empty cell with the tile below it
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow + 1][emptyCellCol];
      tiles[emptyCellRow + 1][emptyCellCol] = null;
      // update the row index of the empty cell
      emptyCellRow++;
      // return true as the empty cell is moved successfully
      return true;
   }

   // A method for drawing the board by using the StdDraw library
   public void draw() {
      // clear the drawing canvas using the background color
      StdDraw.clear(backgroundColor);
      // for each tile in the tile matrix
      for (int row = 0; row < 3; row++)
         for (int col = 0; col < 3; col++) {
            // skip the empty cell
            if (tiles[row][col] == null)
               continue;
            // get the position of the tile based on its indexes
            // by using the getTilePosition method defined below
            Point tilePosition = getTilePosition(row, col);
            // draw the tile centered on its position
            tiles[row][col].draw(tilePosition.x, tilePosition.y);

         }
      // draw the box around the board
      StdDraw.setPenColor(boxColor);
      StdDraw.setPenRadius(lineThickness);
      StdDraw.square(2, 2, 1.5);
      StdDraw.setPenRadius(); // reset pen radius to its default value
   }

   // An inner method that returns the position of the tile on the board
   // with the given row and column indexes
   private Point getTilePosition(int rowIndex, int columnIndex) {
      // convert the indexes to the positions in StdDraw
      int posX = columnIndex + 1, posY = 3 - rowIndex;
      return new Point(posX, posY);
   }

   public boolean isSolvable(int[] numberArray){
      int biggerCount = 0;
      for (int i = 0;i< numberArray.length;i++){

         for(int j = i+1; j< numberArray.length; j++){
            if ((numberArray[i] > numberArray[j]) && (numberArray[j] != 0)){
               biggerCount ++;
            }
         }
         System.out.println(numberArray[i]);
         System.out.println("count = " + biggerCount);
      }
      if (biggerCount % 2 == 0){
         System.out.println("It can be solve");
         return true;
      }
      else {
         System.out.println("it can't be solved");
         return false;
      }
   }

   public void drawMessage(boolean isTrue) {
      // Use StdDraw to display a message based on solvability
      System.out.println( isTrue? "Solvable Puzzle" : "Unsolvable Puzzle");
   }

   public void solutionGame(int[][] numberArray){
      int[][] trueSolution = {{1,2,3},{4,5,6},{7,8,0}};
      int[] solution = {};
      int row = 0,column = 0;
      int countNum = 0;
      String combinedPosition = "";
      if (!isSolvable(oneDimensionalNumber)){
         System.out.println("It can't be solved");
      }
      else{ //çözüme gidiyoruz

         for(int i = 0; i < 3;i ++){
            for (int j = 0; j < 3; j ++){
               if(numberArray[i][j] == 0){
                  row = i;
                  column = j; //get the blank position;
                  combinedPosition = row + "" + column;
               }}}

         System.out.println("our position"+combinedPosition); //boşun konumu var,

         String trueWay = trueStep(combinedPosition,row,column,twoDimensionalNumberArray);

         System.out.println("true way is" + trueWay);

         if (trueWay == "right"){
            rightWork(row,column,twoDimensionalNumberArray);
         }
         else if(trueWay == "left"){

         }
         else if(trueWay == "top"){

         }
         else if(trueWay == "bottom"){

         }
         else{
            System.out.println("There is an error!");
         }

      }


       //her seferinde hepsinin mesafesini hesaplayan bir fonksiyon

   }
   public int manhattanDistance(int[][] numberArray){ //bir değer değiştirmemektedir
      int distance = 0;
      int[][] trueSolution = {{1,2,3},{4,5,6},{7,8,0}};
      for (int i = 0; i <3; i++){
         for(int j = 0; j < 3; j++){
            for (int x = 0; x <3; x++){
               for(int y = 0; y < 3; y++){
                  if (numberArray[i][j] == trueSolution[x][y]  && numberArray[i][j] != 0){
                     distance += Math.abs(i-x) + Math.abs(j-y);
                  }
               }
            }

         }
      }

      return distance;
   }
   public void rightWork(int x,int y,int[][] numberArray){
      int storageNum = numberArray[x][y];
      numberArray[x][y] = numberArray[x][y+1];
      numberArray[x][y+1] = storageNum;
   }
   public void leftWork(int x,int y,int[][] numberArray){
      int storageNum = numberArray[x][y];
      numberArray[x][y] = numberArray[x][y-1];
      numberArray[x][y-1] = storageNum;
   }
   public void bottomWork(int x,int y,int[][] numberArray){
      int storageNum = numberArray[x][y];
      numberArray[x][y] = numberArray[x+1][y];
      numberArray[x+1][y] = storageNum;
   }
   public void topWork(int x,int y,int[][] numberArray){
      int storageNum = numberArray[x][y];
      numberArray[x][y] = numberArray[x-1][y];
      numberArray[x-1][y] = storageNum;
   }


   public int rightWay(int x,int y,int[][] numberArray){
      int[][] copyList = numberArray;
      int storageNum = copyList[x][y];
      copyList[x][y] = copyList[x][y+1];
      copyList[x][y+1] = storageNum;
      return manhattanDistance(copyList);
   }
   public int leftWay(int x,int y,int[][] numberArray){
      int[][] copyList = numberArray;
      int storageNum = copyList[x][y];
      copyList[x][y] = copyList[x][y-1];
      copyList[x][y-1] = storageNum;
      return manhattanDistance(copyList);
   }
   public int bottomWay(int x,int y,int[][] numberArray){
      int[][] copyList = numberArray;
      int storageNum = copyList[x][y];
      copyList[x][y] = copyList[x+1][y];
      copyList[x+1][y] = storageNum;
      return manhattanDistance(copyList);
   }
   public int topWay(int x,int y,int[][] numberArray){
      int[][] copyList = numberArray;
      int storageNum = copyList[x][y];
      copyList[x][y] = copyList[x-1][y];
      copyList[x-1][y] = storageNum;
      return manhattanDistance(copyList);
   }

   //top way Fonksiyonları bizlere yukarı çıktığımız taktirde hangi uzaklık toplamını verdiğini döndürüyor
   public String trueStep(String position,int x,int y,int[][] numberArray){
      int rightWay = 0;
      int leftWay = 0;
      int topWay = 0;
      int bottomWay = 0;
      int smallest = 0;

      switch (position){
         case("00"):
            System.out.println("00 in");
            rightWay = rightWay(x,y,numberArray); //olası sonuçlar alınıyor
            bottomWay = bottomWay(x,y,numberArray);
            if (rightWay < bottomWay){
               return "right";
            }
            else {
               return "bottom";
            }


         case("01"):
            System.out.println("01 in");
            rightWay = rightWay(x,y,numberArray);
            leftWay = leftWay(x,y,numberArray);
            bottomWay = bottomWay(x,y,numberArray);
            smallest = Math.min(rightWay,Math.min(leftWay,bottomWay));
            if (rightWay == smallest){
               return "right";
            }
            else if(leftWay == smallest){
               return "left";
            }
            else{
               return "bottom";
            }

         case("02"):
            leftWay = leftWay(x,y,numberArray);
            bottomWay = bottomWay(x,y,numberArray);
            if (leftWay < bottomWay){
               return "left";
            }
            else {
               return "bottom";
            }


         case("10"):
            rightWay = rightWay(x,y,numberArray);
            topWay = topWay(x,y,numberArray);
            bottomWay = bottomWay(x,y,numberArray);
            smallest = Math.min(rightWay,Math.min(leftWay,bottomWay));
            if (rightWay == smallest){
               return "right";
            }
            else if(topWay == smallest){
               return "top";
            }
            else{
               return "bottom";
            }

         case("11"):
            rightWay = rightWay(x,y,numberArray);
            topWay = topWay(x,y,numberArray);
            bottomWay = bottomWay(x,y,numberArray);
            leftWay = leftWay(x,y,numberArray);
            smallest = Math.min(Math.min(rightWay,topWay),Math.min(leftWay,bottomWay));
            if (rightWay == smallest){
               return "right";
            }
            else if(topWay == smallest){
               return "top";
            }
            else if(leftWay == smallest){
               return "left";
            }
            else{
               return "bottom";
            }

         case("12"):
            topWay = topWay(x,y,numberArray);
            bottomWay = bottomWay(x,y,numberArray);
            leftWay = leftWay(x,y,numberArray);
            smallest = Math.min(leftWay,Math.min(topWay,bottomWay));
            if (bottomWay == smallest){
               return "bottom";
            }
            else if(topWay == smallest){
               return "top";
            }
            else{
               return "left";
            }

         case("20"):
            rightWay = rightWay(x,y,numberArray);
            topWay = topWay(x,y,numberArray);
            if (rightWay < topWay){
               return "right";
            }
            else {
               return "top";
            }

         case("21"):
            rightWay = rightWay(x,y,numberArray);
            topWay = topWay(x,y,numberArray);
            leftWay = leftWay(x,y,numberArray);
            smallest = Math.min(leftWay,Math.min(topWay,rightWay));
            if (rightWay == smallest){
               return "right";
            }
            else if(topWay == smallest){
               return "top";
            }
            else{
               return "left";
            }

         case("22"):
            topWay = topWay(x,y,numberArray);
            leftWay = leftWay(x,y,numberArray);
            if (leftWay < topWay){
               return "left";
            }
            else {
               return "top";
            }
         default:
            return "false";
      }


   }

}