import lib.StdDraw; // the StdDraw class in the lib package is used for drawings

import java.awt.Color; // used for coloring the board
import java.awt.Point; // used for the positions of the tiles and the empty cell
import java.util.ArrayList;
import java.util.Objects;

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
   private String right = "right";
   private String left = "left";
   private String bottom = "bottom";
   private String top = "top";



   int[] oneDimensionalNumber = new int[9];

   // Data fields: the instance variables
   // --------------------------------------------------------------------------
   // a matrix to store the tiles on the board in their current configuration
   private Tile[][] tiles = new Tile[3][3];
   // the row and the column indexes of the empty cell
   private int emptyCellRow, emptyCellCol;
   int[] numbers = new int[9];

   // The default constructor creates a random board
   // --------------------------------------------------------------------------
   public Board() {
      // create an array that contains each number from 0 to 8
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
      oneDimensionalNumber = numbers.clone();
      //drawMessage(isSolvable(numbers));
      //isSolvable(numbers);
      //manhattanDistance(twoDimensionalNumberArray);
      //solutionGame();

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

   public void solutionGame(){
      int[][] numberArray = twoDimensionalNumberArray;
      int[][] trueSolution = {{1,2,3},{4,5,6},{7,8,0}};
      int[] solution = {};
      int row = 0,column = 0;
      int countNum = 0;
      String pastPosition = "empty";
      String combinedPosition = "empty";

      if (!isSolvable(oneDimensionalNumber)){
         System.out.println("It can't be solved");
      }
      else{ //çözüme gidiyoruz
         while (trueSolution != twoDimensionalNumberArray){
            for(int i = 0; i < 3;i ++){
               for (int j = 0; j < 3; j ++){
                  if(numberArray[i][j] == 0){
                     row = i;
                     column = j; //get the blank position;
                     combinedPosition = row + "" + column;
                     System.out.println("Boşluğun bulunduğu konum ->" + combinedPosition);
                     //System.out.println("Two dimen ->" + twoDimensionalNumberArray[row][column]);
                  }}}
            for (int i = 0; i < 3; i++){
               System.out.println();
               for (int j = 0; j<3;j++ ){
                  System.out.print(twoDimensionalNumberArray[i][j]);
               }}
            System.out.println();
            //System.out.println("Row =" + row + "\nColumn =" + column);
            //System.out.println("our position ->"+combinedPosition); //getting current empty position
            //System.out.println("Two dimensi ->" + twoDimensionalNumberArray[row][column]);

            String trueWay = trueStep(combinedPosition,row,column,twoDimensionalNumberArray,pastPosition); //getting true way
            //TRUE STEP DOENSN'T CHANGE THE MAIN LIST

            //System.out.println("New Row =" + row + "\nColumn =" + column);
            //System.out.println("Two dimensional ->" + twoDimensionalNumberArray[row][column]);
            //System.out.println("Past position is -> " + pastPosition);
            System.out.println("true way is ->" + trueWay);
            if (Objects.equals(trueWay, right)){
               rightWork(row,column,twoDimensionalNumberArray);
            }
            else if(Objects.equals(trueWay, left)){
               leftWork(row,column,twoDimensionalNumberArray);
            }
            else if(Objects.equals(trueWay, top)){
               topWork(row,column,twoDimensionalNumberArray);
            }
            else if(Objects.equals(trueWay, bottom)){
               bottomWork(row,column,twoDimensionalNumberArray);
            }
            else{
               System.out.println("There is an error!");
            }
            pastPosition = combinedPosition;
            System.out.println();
         }


      }


       //her seferinde hepsinin mesafesini hesaplayan bir fonksiyon

   }
   public int manhattanDistance(int[][] numberArray){ //bir değer değiştirmemektedir

      int[][] trueSolution = {{1,2,3},{4,5,6},{7,8,0}};
//      System.out.println("NumberArray");
//      for (int i = 0; i <3; i++){
//         System.out.println();
//         for(int j = 0; j < 3; j++){
//            System.out.print(numberArray[i][j]);
//         }
//      }
//      System.out.println("SolutionArray");
//      for (int i = 0; i <3; i++){
//         System.out.println();
//         for(int j = 0; j < 3; j++){
//            System.out.print(trueSolution[i][j]);
//         }
//      }
      int distance = 0;
      int totalDistance = 0;
      System.out.println();
      for (int i = 0; i <3; i++){ //row
         for(int j = 0; j < 3; j++){ //column
            for (int x = 0; x <3; x++){ //row
               for(int y = 0; y < 3; y++){ //column
                  if (numberArray[i][j] == trueSolution[x][y] && numberArray[i][j] != 0){
                     distance = Math.abs(i-x) + Math.abs(j-y);
                     //System.out.println("X line distance ->" + Math.abs(i-x));
//                     System.out.println("Y line distance ->" + Math.abs(j-y));
//                     System.out.println("Number " + numberArray[i][j] + " distance is ->" + distance);
                     totalDistance += distance;
                  }}}}
      }
      return totalDistance;
   }

   public int inversionsCount(int[][] numberArray){ //it calculates the number order bigger smaller
      int myNumber = 0;
      int[] linearArray = new int[9];
      int index = 0;
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) { //two dimensional to convert one dimensional
            linearArray[index] = numberArray[i][j];
            index++;
         }
      }

      for (int i = 0; i < linearArray.length - 1; i++) {
         for (int j = i + 1; j < linearArray.length; j++) {
            if (linearArray[i] != 0 && linearArray[j] != 0 && linearArray[i] > linearArray[j]) {
               myNumber++;
            }
         }
      }

      return myNumber;
   }

   public void rightWork(int x,int y,int[][] numberArray){
      int storageNum = numberArray[x][y];
      numberArray[x][y] = numberArray[x][y+1];
      numberArray[x][y+1] = storageNum;
      System.out.println("right work");
   }
   public void leftWork(int x,int y,int[][] numberArray){
      int storageNum = numberArray[x][y];
      numberArray[x][y] = numberArray[x][y-1];
      numberArray[x][y-1] = storageNum;
      System.out.println("left work");
   }
   public void bottomWork(int x,int y,int[][] numberArray){
      int storageNum = numberArray[x][y];
      //System.out.println("Storage Num ->" + storageNum);
      numberArray[x][y] = numberArray[x+1][y];
      //System.out.println("numberArray[x][y] ->" + numberArray[x][y]);
      numberArray[x+1][y] = storageNum;
      //System.out.println("numberArray[x+1][y] ->" + numberArray[x+1][y]);
      System.out.println("bottom work");
   }
   public void topWork(int x,int y,int[][] numberArray){
      int storageNum = numberArray[x][y];
      numberArray[x][y] = numberArray[x-1][y];
      numberArray[x-1][y] = storageNum;
      System.out.println("top work");
   }

   public int rightWay(int x,int y,int[][] numberArray){
      int[][] copyList = cloneTwoDimensionalArray(numberArray);
      int storageNum = copyList[x][y];
      copyList[x][y] = copyList[x][y+1];
      copyList[x][y+1] = storageNum;
      int distance = manhattanDistance(copyList);
      System.out.println("Right way distance->" + distance);
      return distance;
   }
   public int rightWayInversion(int x,int y,int[][] numberArray){
      int[][] copyList = cloneTwoDimensionalArray(numberArray);
      int storageNum = copyList[x][y];
      copyList[x][y] = copyList[x][y+1];
      copyList[x][y+1] = storageNum;
      int distance = inversionsCount(copyList);
      System.out.println("Right way inversion->" + distance);
      return distance;
   }

   public int leftWay(int x,int y,int[][] numberArray){
      int[][] copyList = cloneTwoDimensionalArray(numberArray);
      int storageNum = copyList[x][y];
      copyList[x][y] = copyList[x][y-1];
      copyList[x][y-1] = storageNum;
      int distance = manhattanDistance(copyList);
      System.out.println("Left way distance->" + distance );
      return distance;
   }
   public int leftWayInversion(int x,int y,int[][] numberArray){
      int[][] copyList = cloneTwoDimensionalArray(numberArray);
      int storageNum = copyList[x][y];
      copyList[x][y] = copyList[x][y-1];
      copyList[x][y-1] = storageNum;
      int distance = inversionsCount(copyList);
      System.out.println("Left way inversion->" + distance );
      return distance;
   }
   public int bottomWay(int x,int y,int[][] numberArray){
      int[][] copyList = cloneTwoDimensionalArray(numberArray);
      int storageNum = copyList[x][y];
      copyList[x][y] = copyList[x+1][y];
      copyList[x+1][y] = storageNum;
      int distance = manhattanDistance(copyList);
      System.out.println("Bottom way distance->" + distance );
      return distance;
   }
   public int bottomWayInversion(int x,int y,int[][] numberArray){
      int[][] copyList = cloneTwoDimensionalArray(numberArray);
      int storageNum = copyList[x][y];
      copyList[x][y] = copyList[x+1][y];
      copyList[x+1][y] = storageNum;
      int distance = inversionsCount(copyList);
      System.out.println("Bottom way inversion->" + distance );
      return distance;
   }
   public int topWay(int x,int y,int[][] numberArray){
      int[][] copyList = cloneTwoDimensionalArray(numberArray);
      int storageNum = copyList[x][y];
      copyList[x][y] = copyList[x-1][y];
      copyList[x-1][y] = storageNum;
      int distance = manhattanDistance(copyList);
      System.out.println("Top way distance->" + distance );
      return distance;
   }
   public int topWayInversion(int x,int y,int[][] numberArray){
      int[][] copyList = cloneTwoDimensionalArray(numberArray);
      int storageNum = copyList[x][y];
      copyList[x][y] = copyList[x-1][y];
      copyList[x-1][y] = storageNum;
      int distance = inversionsCount(copyList);
      System.out.println("Top way inversion->" + distance );
      return distance;
   }

   //top way Fonksiyonları bizlere yukarı çıktığımız taktirde hangi uzaklık toplamını verdiğini döndürüyor
   public String trueStep(String position,int x,int y,int[][] numberArray,String backStep){
      int rightWay = 0;
      int leftWay = 0;
      int topWay = 0;
      int bottomWay = 0;
      int smallest = 0;
      int rightInversion = 0;
      int leftInversion = 0;
      int bottomInversion = 0;
      int topInversion = 0;
      int smallestInversion = 0;
      System.out.println("BackStep ->" + backStep);

      switch (position){
         case("00"):
            rightWay = rightWay(x,y,numberArray); //olası sonuçlar alınıyor
            bottomWay = bottomWay(x,y,numberArray);
            if (backStep.equals("01")){
               return bottom;
            }
            else if (backStep.equals("10")){
               return right;
            }
            else{
               if (rightWay < bottomWay){
                  return right;
               }
               else {
                  return bottom;
               }
            }

         case("01"):
            rightWay = rightWay(x,y,numberArray);
            leftWay = leftWay(x,y,numberArray);
            bottomWay = bottomWay(x,y,numberArray);
            smallest = Math.min(rightWay,Math.min(leftWay,bottomWay));

            rightInversion = rightWayInversion(x,y,numberArray);
            leftInversion = leftWayInversion(x,y,numberArray);
            bottomInversion = bottomWayInversion(x,y,numberArray);
            topInversion = topWayInversion(x,y,numberArray);

            smallestInversion = Math.min(rightInversion,Math.min(leftInversion,bottomInversion));

            if (backStep.equals("02")){
               if(leftWay == bottomWay){  //if left and bottom is equal check the inversion
                  if (leftWayInversion(x,y,numberArray) <= bottomWayInversion(x,y,numberArray)){
                     return left;
                  }
                  else {
                     return bottom;
                  }
               }
               else if(leftWay == smallest){  //burada bir sorun olabilir %%fark etmiyor
                  return left;
               }
               else{
                  return bottom;
               }
            }
            else if(backStep.equals("00")){
               if(rightWay == bottomWay){  //if left and bottom is equal check the inversion
                  if (rightWayInversion(x,y,numberArray) <= bottomWayInversion(x,y,numberArray)){
                     return right;
                  }
                  else {
                     return bottom;
                  }
               }
               else if(rightWay == smallest){  //burada bir sorun olabilir %%fark etmiyor
                  return left;
               }
               else{
                  return bottom;
               }
            }
            else if (backStep.equals("11")){
               if(rightWay == leftWay){  //if left and bottom is equal check the inversion
                  if (rightWayInversion(x,y,numberArray) <= leftWayInversion(x,y,numberArray)){
                     return right;
                  }
                  else {
                     return left;
                  }
               }
               else if(rightWay == smallest){  //burada bir sorun olabilir %%fark etmiyor
                  return right;
               }
               else{
                  return left;
               }
            }
            else {
               if (rightWay == smallest){
                  return right;
               }
               else if(leftWay == smallest){
                  return left;
               }
               else{
                  return bottom;
               }
            }

         case("02"):
            leftWay = leftWay(x,y,numberArray);
            bottomWay = bottomWay(x,y,numberArray);
            if (backStep.equals("01")){
               return bottom;
            }
            else if(backStep.equals("12")){
               return left;
            }
            else{
               if (leftWay < bottomWay){
                  return left;
               }
               else {
                  return bottom;
               }
            }



         case("10"):
            rightWay = rightWay(x,y,numberArray);
            topWay = topWay(x,y,numberArray);
            bottomWay = bottomWay(x,y,numberArray);
            smallest = Math.min(rightWay,Math.min(leftWay,bottomWay));

            rightInversion = rightWayInversion(x,y,numberArray);
            bottomInversion = bottomWayInversion(x,y,numberArray);
            topInversion = topWayInversion(x,y,numberArray);
            smallestInversion = Math.min(rightInversion,Math.min(topInversion,bottomInversion));

            if (Objects.equals(backStep, "00")){
               if(rightWay == bottomWay){  //if left and bottom is equal check the inversion
                  if (rightWayInversion(x,y,numberArray) <= bottomWayInversion(x,y,numberArray)){
                     return right;
                  }
                  else {
                     return bottom;
                  }
               }
               else if(rightWay == smallest){  //burada bir sorun olabilir %%fark etmiyor
                  return right;
               }
               else{
                  return bottom;
               }
            }
            else if(Objects.equals(backStep, "20")){
               if(topWay == rightWay){  //if left and bottom is equal check the inversion
                  if (rightWayInversion(x,y,numberArray) <= topWayInversion(x,y,numberArray)){
                     return right;
                  }
                  else {
                     return top;
                  }
               }
               else if(rightWay == smallest){  //burada bir sorun olabilir %%fark etmiyor
                  return right;
               }
               else{
                  return top;
               }
            }
            else if (Objects.equals(backStep, "11")){
               if(topWay == bottomWay){  //if left and bottom is equal check the inversion
                  if (topWayInversion(x,y,numberArray) <= bottomWayInversion(x,y,numberArray)){
                     return top;
                  }
                  else {
                     return bottom;
                  }
               }
               else if(topWay == smallest){  //burada bir sorun olabilir %%fark etmiyor
                  return top;
               }
               else{
                  return bottom;
               }

            }
            else{
               if (rightWay == smallest){
                  return right;
               }
               else if(topWay == smallest){
                  return top;
               }
               else{
                  return bottom;
               }
            }


         case("11"):
            rightWay = rightWay(x,y,numberArray);
            topWay = topWay(x,y,numberArray);
            bottomWay = bottomWay(x,y,numberArray);
            leftWay = leftWay(x,y,numberArray);
            smallest = Math.min(Math.min(rightWay,topWay),Math.min(leftWay,bottomWay));

            rightInversion = rightWayInversion(x,y,numberArray);
            leftInversion = leftWayInversion(x,y,numberArray);
            bottomInversion = bottomWayInversion(x,y,numberArray);
            topInversion = topWayInversion(x,y,numberArray);
            smallestInversion = Math.min(Math.min(rightInversion,topInversion),Math.min(leftInversion,bottomInversion));


            if (Objects.equals(backStep, "01")){
               if (rightWay == leftWay && leftWay == bottomWay){
                  if (!(bottomInversion == leftInversion && leftInversion == rightInversion)){// if three of them not equal than it works
                     if (bottomInversion == smallestInversion){
                        return bottom;
                     }
                     else if(leftInversion == smallestInversion){
                        return left;
                     }
                     else if(rightInversion == smallestInversion){
                        return right;
                     }
                  }
               }
               if (rightWay == leftWay){
                  if (rightInversion < leftInversion){
                     return right;
                  }
                  else {
                     return left;
                  }
               }
               else if(leftWay == bottomWay){
                  if (leftInversion < bottomInversion){
                     return left;
                  }
                  else {
                     return bottom;
                  }
               }
               else if(rightWay == bottomWay){
                  if (rightInversion < bottomInversion){
                     return right;
                  }
                  else {
                     return bottom;
                  }

               }//hiçbiri birbirine eş değil
               else{
                  if (rightWay == smallest){
                     return right;
                  }
                  else if(leftWay == smallest){
                     return left;
                  }
                  else{
                     return bottom;
                  }
               }

            }
            else if(Objects.equals(backStep, "10")){
               if (rightWay == topWay && topWay == bottomWay){
                  if (!(bottomInversion == topInversion && topInversion == rightInversion)){// if three of them not equal than it works
                     if (bottomInversion == smallestInversion){
                        return bottom;
                     }
                     else if(topInversion == smallestInversion){
                        return top;
                     }
                     else if(rightInversion == smallestInversion){
                        return right;
                     }
                  }
               }
               if (rightWay == topWay){
                  if (rightInversion < topInversion){
                     return right;
                  }
                  else {
                     return top;
                  }
               }
               else if(topWay == bottomWay){
                  if (topInversion < bottomInversion){
                     return top;
                  }
                  else {
                     return bottom;
                  }
               }
               else if(rightWay == bottomWay){
                  if (rightInversion < bottomInversion){
                     return right;
                  }
                  else {
                     return bottom;
                  }

               }//hiçbiri birbirine eş değil
               else{
                  if (rightWay == smallest){
                     return right;
                  }
                  else if(topWay == smallest){
                     return top;
                  }
                  else{
                     return bottom;
                  }
               }
            }
            else if(Objects.equals(backStep, "12")){
               if (topWay == leftWay && leftWay == bottomWay){
                  if (!(bottomInversion == leftInversion && leftInversion == topInversion)){// if three of them not equal than it works
                     if (bottomInversion == smallestInversion){
                        return bottom;
                     }
                     else if(leftInversion == smallestInversion){
                        return left;
                     }
                     else if(topInversion == smallestInversion){
                        return top;
                     }
                  }
               }
               if (topWay == leftWay){
                  if (topInversion < leftInversion){
                     return top;
                  }
                  else {
                     return left;
                  }
               }
               else if(leftWay == bottomWay){
                  if (leftInversion < bottomInversion){
                     return left;
                  }
                  else {
                     return bottom;
                  }
               }
               else if(topWay == bottomWay){
                  if (topInversion < bottomInversion){
                     return top;
                  }
                  else {
                     return bottom;
                  }

               }//hiçbiri birbirine eş değil
               else{
                  if (topWay == smallest){
                     return top;
                  }
                  else if(leftWay == smallest){
                     return left;
                  }
                  else{
                     return bottom;
                  }
               }
            }
            else if(Objects.equals(backStep, "21")){
               if (rightWay == leftWay && leftWay == topWay){
                  if (!(topInversion == leftInversion && leftInversion == rightInversion)){// if three of them not equal than it works
                     if (topInversion == smallestInversion){
                        return top;
                     }
                     else if(leftInversion == smallestInversion){
                        return left;
                     }
                     else if(rightInversion == smallestInversion){
                        return right;
                     }
                  }
               }
               if (rightWay == leftWay){
                  if (rightInversion < leftInversion){
                     return right;
                  }
                  else {
                     return left;
                  }
               }
               else if(leftWay == topWay){
                  if (leftInversion < topInversion){
                     return left;
                  }
                  else {
                     return top;
                  }
               }
               else if(rightWay == topWay){
                  if (rightInversion < topInversion){
                     return right;
                  }
                  else {
                     return top;
                  }

               }//hiçbiri birbirine eş değil
               else{
                  if (rightWay == smallest){
                     return right;
                  }
                  else if(leftWay == smallest){
                     return left;
                  }
                  else{
                     return top;
                  }
               }
            }
            else{
               if (rightWay == smallest){
                  return right;
               }
               else if(topWay == smallest){
                  return top;
               }
               else if(leftWay == smallest){
                  return left;
               }
               else{
                  return bottom;
               }
            }


         case("12"):
            topWay = topWay(x,y,numberArray);
            bottomWay = bottomWay(x,y,numberArray);
            leftWay = leftWay(x,y,numberArray);
            smallest = Math.min(leftWay,Math.min(topWay,bottomWay));

            leftInversion = leftWayInversion(x,y,numberArray);
            bottomInversion = bottomWayInversion(x,y,numberArray);
            topInversion = topWayInversion(x,y,numberArray);
            smallestInversion = Math.min(leftInversion,Math.min(topInversion,bottomInversion));

            if (Objects.equals(backStep, "02")){
               if(leftWay == bottomWay){  //if left and bottom is equal check the inversion
                  if (leftWayInversion(x,y,numberArray) <= bottomWayInversion(x,y,numberArray)){
                     return left;
                  }
                  else {
                     return bottom;
                  }
               }
               else if(leftWay == smallest){  //burada bir sorun olabilir %%fark etmiyor
                  return left;
               }
               else{
                  return bottom;
               }
            }
            else if(Objects.equals(backStep, "11")){
               if(topWay == bottomWay){  //if left and bottom is equal check the inversion
                  if (topWayInversion(x,y,numberArray) <= bottomWayInversion(x,y,numberArray)){
                     return top;
                  }
                  else {
                     return bottom;
                  }
               }
               else if(topWay == smallest){  //burada bir sorun olabilir %%fark etmiyor
                  return top;
               }
               else{
                  return bottom;
               }
            }
            else if (Objects.equals(backStep, "22")){
               if(leftWay == topWay){  //if left and bottom is equal check the inversion
                  if (leftWayInversion(x,y,numberArray) <= topWayInversion(x,y,numberArray)){
                     return left;
                  }
                  else {
                     return top;
                  }
               }
               else if(leftWay == smallest){  //burada bir sorun olabilir %%fark etmiyor
                  return left;
               }
               else{
                  return top;
               }
            }
            else{
               if (bottomWay == smallest){
                  return bottom;
               }
               else if(topWay == smallest){
                  return top;
               }
               else{
                  return left;
               }
            }
            case("20"):
            rightWay = rightWay(x,y,numberArray);
            topWay = topWay(x,y,numberArray);
            if (Objects.equals(backStep, "10")){
               return right;
            }
            else if (Objects.equals(backStep, "21")){
               return top;
            }
            else {
               if (rightWay < topWay){
                  return right;
               }
               else {
                  return top;
               }
            }


         case("21"):
            rightWay = rightWay(x,y,numberArray);
            topWay = topWay(x,y,numberArray);
            leftWay = leftWay(x,y,numberArray);
            smallest = Math.min(leftWay,Math.min(topWay,rightWay));

            leftInversion = leftWayInversion(x,y,numberArray);
            rightInversion = rightWayInversion(x,y,numberArray);
            topInversion = topWayInversion(x,y,numberArray);
            smallestInversion = Math.min(leftInversion,Math.min(topInversion,rightInversion));

            if (Objects.equals(backStep, "11")){
               if(rightWay == leftWay){  //if left and bottom is equal check the inversion
                  if (rightWayInversion(x,y,numberArray) <= leftWayInversion(x,y,numberArray)){
                     return right;
                  }
                  else {
                     return left;
                  }
               }
               else if(rightWay == smallest){  //burada bir sorun olabilir %%fark etmiyor
                  return right;
               }
               else{
                  return left;
               }
            }
            else if (Objects.equals(backStep, "20")){
               if(rightWay == topWay){  //if left and bottom is equal check the inversion
                  if (rightWayInversion(x,y,numberArray) <= topWayInversion(x,y,numberArray)){
                     return right;
                  }
                  else {
                     return top;
                  }
               }
               else if(rightWay == smallest){  //burada bir sorun olabilir %%fark etmiyor
                  return right;
               }
               else{
                  return top;
               }
            }
            else if (Objects.equals(backStep, "22")){
               if(topWay == leftWay){  //if left and bottom is equal check the inversion
                  if (topWayInversion(x,y,numberArray) <= leftWayInversion(x,y,numberArray)){
                     return top;
                  }
                  else {
                     return left;
                  }
               }
               else if(topWay == smallest){  //burada bir sorun olabilir %%fark etmiyor
                  return top;
               }
               else{
                  return left;
               }

            }
            else {
               if (rightWay == smallest){
                  return right;
               }
               else if(topWay == smallest){
                  return top;
               }
               else{
                  return left;
               }
            }


         case("22"):
            topWay = topWay(x,y,numberArray);
            leftWay = leftWay(x,y,numberArray);
            if (Objects.equals(backStep, "12")){
               return left;
            }
            else if (Objects.equals(backStep, "21")){
               return top;
            }
            else {
               if (leftWay < topWay){
                  return left;
               }
               else {
                  return top;
               }
            }
         default:
            return "false";
      }

   }

   public int[][] cloneTwoDimensionalArray(int[][] myArray){
      int[][] newArray = new  int[myArray.length][myArray[0].length];
      for(int i = 0; i < myArray.length;i++){
         for (int j = 0; j < myArray[0].length;j++){
            newArray[i][j] =myArray[i][j];
         }
      }
      return newArray;
   }


}