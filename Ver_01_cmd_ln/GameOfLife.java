/*
  Conway's Game of Life
  
  Alyssa M. Goncalves
  Final Project
  MAT-220 Discrete Structures
  Springfield Technical Community College
  Spring 2016

  POPULATION:
  The population is a two-dimensional boolean array of variable size. Each 
  boolean element in the array is a cell, or memeber, of the population that can 
  contain one of two values: TRUE or FALSE.  
  
  CELLS:
  Cells are individual boolean elements of the two-dimensional population array.  
  A cell is considered to be ALIVE if it's value is true and DEAD if it's value 
  is false. When a dead cell's value changes to true, the cell is brought to life.
  When a living cell's value changes to false, the cell dies.
  
  NEIGHBORHOOD:
  A cell's neighborhood is the 3x3 matrix that contains the cell itself at value 
  (1,1) and it's surrounding eight neighbors.

  NEIGHBORHOOD DIAGRAM:
  -------------------
  | 0,0 | 0,1 | 0,2 |
  -------------------
  | 1,0 |  *  | 1,2 |
  -------------------
  | 2,0 | 2,1 | 2,2 |
  -------------------

  RULES:
  A living cell dies from overpopulation if the neighborhood contains
  more than three living cells. 

  A living cell continues to live if its neighborhood contains two or three 
  living cells.
  
  A living cell dies from underpopulation if the cell's neighborhood contains
  less than two living cells.
  
  A dead cell can be birthed through reproduction if it is surrounded by exactly 
  three living neighbors.
*/

import java.util.Scanner;

public class GameOfLife {
  
  public static void main(String[] args) {
    String[] seedNames = {"r-pentomino", "b-heptomino", "pi-heptomino", "acorn", "glider", "block-glider"}; // initialize seed names
    
    // list seed pattern choices for user
    System.out.println("Available seed patterns:");
    for (int i = 0; i < seedNames.length; i++)
      System.out.printf("\t%d) %s\n", i, seedNames[i]);
    
    // get input from user
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter desired seed pattern: ");
    int num = sc.nextInt();  
    System.out.println("Enter number of generations: ");
    int numOfSteps = sc.nextInt();
    System.out.println("Show each generation? y/n: ");
    char showGenerations = sc.next().charAt(0);
    sc.close();
    
    // initialize population size and seed origin position
    int rowSize = 0; // number of rows in the population matrix
    int colSize = 0; // number of columns in the population matrix
    int seedRowPos = 0; // the row value of seed's origin position
    int seedColPos = 0; // the column value of seed's origin position
    
    switch (seedNames[num]) {
      // initializes an r-pentomino shaped seed at cell position row = 10, col = 10
      case "r-pentomino":  rowSize = 20;
                           colSize = 20;
                           seedRowPos = rowSize/2;
                           seedColPos = rowSize/2;
                           break;
      // initializes an r-pentomino shaped seed at cell position row = 10, col = 10
      case "b-heptomino":  rowSize = 20;
                           colSize = 20; 
                           seedRowPos = rowSize/2;
                           seedColPos = rowSize/2;
                           break;
                           
      case "pi-heptomino": rowSize = 20;
                           colSize = 20;
                           seedRowPos = rowSize/2;
                           seedColPos = rowSize/2;
                           break; 
                           
      case "acorn":        rowSize = 50;
                           colSize = 70;
                           seedRowPos = rowSize/2;
                           seedColPos = colSize - 14;  
                           break;
                           
      case "glider":       rowSize = 20;
                           colSize = 20;
                           seedRowPos = 4;
                           seedColPos = 4;
                           break;
                           
      case "block-glider": rowSize = 50;
                           colSize = 50;
                           seedRowPos = rowSize/3;
                           seedColPos = colSize/2;
                           break;
      default:             break; // initialize a 0x0 matrix, no seed
    }
  
    boolean[][] currentGen = new boolean[rowSize][colSize];
    boolean[][] nextGen = new boolean[rowSize][colSize];

    seed(currentGen, seedNames[num], seedRowPos, seedColPos);
    run(currentGen, nextGen, numOfSteps, showGenerations);
  }
  
  /**
  * 
  * @param  currentGen two-dimensional boolean array that holds the state of 
  *                    the current population
  * @param  nextGen two-dimensional boolean array to be initialized with the 
  *                 state of the population in currentGen after the Game of Life
  *                 rules are applied
  * @param  numOfSteps integer value of the number of generations to iterate
  * @param  showGenerations print every generation if value is 'y', otherwise
  *                         print only initial and final population
  */
  public static void run(boolean[][] currentGen, boolean[][] nextGen, int numOfSteps, char showGenerations) {
    System.out.println("Starting Population:\n");
    printMatrix(currentGen);
    for (int i = 0; i < numOfSteps; i++) {
        generate(currentGen, nextGen);
        if (showGenerations == 'y') {
          System.out.println("Generation " + i + ":");
          printMatrix(currentGen);
        }
    }
    System.out.println("\nFinal Population: " + numOfSteps + " generations\n");
    printMatrix(nextGen);
  }
  
  /**
  * Applies the Game of Life rules to each element in the current population 
  * matrix and places the results in the corresponding element in the next 
  * generation matrix.
  *
  * @param  current a two-dimensional boolean array holding the starting 
  *                 population
  * @param  next a two-dimensional boolean array to be initialized with the next 
  *              generation of the population after the Game of Life rules have 
  *              been applied to each cell in current.
  */
  public static void generate(boolean[][] current, boolean[][] next) {
    for (int row = 0; row < current.length; row++) {
      for (int col = 0; col < current[row].length; col++) {
        next[row][col] = checkNeighbors(getNeighborhood(current, row, col));
      }
    }
    copyMatrix(current, next);
  }

  /**
  * Seeds a given population with initial values.  True = alive, False = dead
  *
  * @param  population a two-dimensional boolean array 
  */
  private static void seed(boolean[][] population, String seed, int row, int col) {
    //System.out.print("Initialize: " + seed);
    switch (seed) {
      case "r-pentomino":  Seeds.initRPentomino(population, row, col);
                           break;
      case "b-heptomino":  Seeds.initBHeptomino(population, row, col);
                           break;
      case "pi-heptomino": Seeds.initPiPentomino(population, row, col);
                           break; 
      case "acorn":        Seeds.initAcorn(population, row, col);
                           break;
      case "glider":       Seeds.initGlider(population, row, col);
                           break;
      case "block-glider": Seeds.initBlockGlider(population, row, col);
                           break;
      default:             Seeds.wasteland(population);
                           break;
    }
  }
  
  /**
  * Initializes a 2-dimensional boolean array of any dimension with the values
  * of an identically sized 2D boolean array 
  *
  * @param  old a 2-dimensional boolean array to be written
  * @param  copy a 2-dimension boolean array to copy
  */
  private static void copyMatrix(boolean[][] old, boolean[][] copy) {
    for (int i = 0; i < old.length; i++)
    for (int j = 0; j < old[i].length; j++)
    old[i][j] = copy[i][j];
  }
  
  /**
  * prints a 2-dimensional boolean array of any dimension to standard output
  *
  * @param  m a 2-dimensional boolean array
  */
  public static void printMatrix(boolean[][] m) {
    for (int i = 0; i <= m[0].length; i++) {
      System.out.print("--");
    }
    System.out.print("\n");
     for(int i = 0; i < m.length; i++) {
       System.out.print("|");
       for (int j = 0; j < m[i].length; j++) {
         System.out.printf("%s ", (m[i][j] ? "*" : " "));
       }
       System.out.print("|\n");
    }
    for (int i = 0; i <= m[0].length; i++) {
      System.out.print("--");
    }
    System.out.print("\n");
  }	
  
  /**
  * Iterates through each element in a 3x3 matrix, except the middle "cell" (1,1), and
  * tallies the number of living (true) cells found.
  *
  * Function returns true if the cell has three living neighbors.
  *
  * Function returns false if the cell has greater than or less than three living neighbors.
  *
  * @param  neighborhood a two-dimensional boolean array consisting of a cell from the
  *                      main population and it's surrounding neighbors.
  */  
  private static boolean checkNeighbors(boolean[][] neighborhood) {
    int count = 0;
    for (int i = 0; i < neighborhood.length; i++) {
      for (int j = 0; j < neighborhood[i].length; j++) {
        if (!(i == 1 && j == 1) && neighborhood[i][j]) {
          count++;
          //System.out.println("Match found on i=" + i + ", j=" + j);
        }
      }
    }
    //System.out.println("Count: " + count);
    if (neighborhood[1][1] == true && count < 2) // loneliness: cell dies from underpopulation
      return false;
    else if (neighborhood[1][1] == true && (count == 2 || count == 3)) // stasis: live cell continues to live on to the next generation
      return true;
    else if (neighborhood[1][1] == true && count > 3) // overcrowding: live cell dies from overpopulation
      return false;
    else if (neighborhood[1][1] == false && count == 3) // reproduction: dead cell is populated with a live cell
      return true;
    return neighborhood[1][1];
  }
  
  /**
  * Initializes a new, two-dimensional boolean array to represent the surrounding neighborhood of 
  * a cell in row, col of the population
  *
  * @param  population a two-dimensional boolean array
  * @param  row the row position of the element in population
  * @param  col the column position of the cell in population
  */
  private static boolean[][] getNeighborhood(boolean[][] population, int row, int col) {
    boolean[][] neighborhood = new boolean[3][3];
    if (row == 0 && col == 0)
      buildNWCorner(population, neighborhood, row, col);
    else if (row == 0 && col == population[row].length - 1)
      buildNECorner(population, neighborhood, row, col);
    else if (row == (population.length - 1) && col == (population[population.length - 1].length - 1))
      buildSECorner(population, neighborhood, row, col);
    else if (row == (population.length - 1) && col == 0)
      buildSWCorner(population, neighborhood, row, col);
    else if (row == 0)
      buildNorthEdge(population, neighborhood, row, col);
    else if (col == (population[row].length - 1))
      buildEastEdge(population, neighborhood, row, col);
    else if (row == (population.length - 1))
      buildSouthEdge(population, neighborhood, row, col);
    else if (col == 0)
      buildWestEdge(population, neighborhood, row, col);
    else
      buildNeighborhood(population, neighborhood, row, col);
    //System.out.println("The neighborhood for cell row = " + row + ", col = " + col + " is:");
    //printMatrix(neighborhood);
    return neighborhood;  
}
  
  /**
  * Initializes a 3x3 neighborhood with elements from a given cell (*) at position row = i, col = j in a given population 
  *
  * @param  p the entire population
  * @param  n the 3x3 neighborhood array undergoing initialization
  * @param  i the row position of the cell
  * @param  j the column position of the cell
  *
  *  REGULAR NEIGHBORHOOD:
  *  -------------------
  *  | 0,0 | 0,1 | 0,2 |
  *  -------------------
  *  | 1,0 |  *  | 1,2 |
  *  -------------------
  *  | 2,0 | 2,1 | 2,2 |
  *  -------------------
  */
  private static void buildNeighborhood(boolean[][] p, boolean[][] n, int i, int j) {
    n[0][0] = p[i - 1][j - 1];
    n[0][1] = p[i - 1][j];
    n[0][2] = p[i - 1][j + 1];
    n[1][0] = p[i][j - 1];
    n[1][1] = p[i][j]; // the cell!
    n[1][2] = p[i][j + 1];
    n[2][0] = p[i + 1][j - 1];
    n[2][1] = p[i + 1][j];
    n[2][2] = p[i + 1][j + 1];
  }
  
  /**
  * Initializes a 3x3 neighborhood with elements from the cell (*) in the north-west corner of a given population 
  *
  * @param  p the entire population
  * @param  n the 3x3 neighborhood array undergoing initialization
  * @param  i the row position of the cell
  * @param  j the column position of the cell
  *
  *  NW CORNER CELL:
  *  row = 0, column = 0
  *  -------------------
  *  |     |     |     |
  *  -------------------
  *  |     |  *  | 1,2 |
  *  -------------------
  *  |     | 2,1 | 2,2 |
  *  -------------------
  */  
  private static void buildNWCorner(boolean[][] p, boolean[][] n, int i, int j) {
    n[0][0] = false;
    n[0][1] = false;
    n[0][2] = false;
    n[1][0] = false;
    n[1][1] = p[i][j]; // the cell!
    n[1][2] = p[i][j + 1];
    n[2][0] = false;
    n[2][1] = p[i + 1][j];
    n[2][2] = p[i + 1][j + 1];
  }
  
  /**
  * Initializes a 3x3 neighborhood with elements from the cell (*) in the north-east corner of a given population 
  *
  * @param  p the entire population
  * @param  n the 3x3 neighborhood array undergoing initialization
  * @param  i the row position of the cell
  * @param  j the column position of the cell
  *
  *  NE CORNER CELL NEIGHBORHOOD:
  *  row = 0, column = population[0].length
  *  -------------------
  *  |     |     |     |
  *  -------------------
  *  | 1,0 |  *  |     |
  *  -------------------
  *  | 2,0 | 2,1 |     |
  *  -------------------
  */
  private static void buildNECorner(boolean[][] p, boolean[][] n, int i, int j) {
    n[0][0] = false;
    n[0][1] = false;
    n[0][2] = false;
    n[1][0] = p[i][j - 1];
    n[1][1] = p[i][j]; // the cell!
    n[1][2] = false;
    n[2][0] = p[i + 1][j - 1];
    n[2][1] = p[i + 1][j];
    n[2][2] = false;  
  }
  
  /**
  * Initializes a 3x3 neighborhood with elements from the cell (*) in the south-east corner of a given population 
  *
  * @param  p the entire population
  * @param  n the 3x3 neighborhood array undergoing initialization
  * @param  i the row position of the cell
  * @param  j the column position of the cell
  *
  *  SE CORNER CELL:
  *  row = population.length, column = population[population.length].length
  *  -------------------
  *  | 0,0 | 0,1 |     |
  *  -------------------
  *  | 1,0 |  *  |     |
  *  -------------------
  *  |     |     |     |
  *  -------------------
  */  
  private static void buildSECorner(boolean[][] p, boolean[][] n, int i, int j) {
    n[0][0] = p[i - 1][j - 1];
    n[0][1] = p[i - 1][j];
    n[0][2] = false;
    n[1][0] = p[i][j - 1];
    n[1][1] = p[i][j]; // the cell!
    n[1][2] = false;
    n[2][0] = false;
    n[2][1] = false;
    n[2][2] = false;  
  }
  
  /**
  * Initializes a 3x3 neighborhood with elements from the cell (*) in the south-west corner of a given population 
  *
  * @param  p the entire population
  * @param  n the 3x3 neighborhood array undergoing initialization
  * @param  i the row position of the cell
  * @param  j the column position of the cell
  *
  *  SW CORNER CELL:
  *  row = population.length - 1, column = 0
  *  -------------------
  *  |     | 0,1 | 0,2 |
  *  -------------------
  *  |     |  *  | 1,2 |
  *  -------------------
  *  |     |     |     |
  *  -------------------
  */  
  private static void buildSWCorner(boolean[][] p, boolean[][] n, int i, int j) {
    n[0][0] = false;
    n[0][1] = p[i - 1][j];
    n[0][2] = p[i - 1][j + 1];
    n[1][0] = false;
    n[1][1] = p[i][j]; // the cell!
    n[1][2] = p[i][j + 1];
    n[2][0] = false;
    n[2][1] = false;
    n[2][2] = false;  
  }
  
  /**
  * Initializes a 3x3 neighborhood with elements from a cell (*) on the north edge of a given population 
  *
  * @param  p the entire population
  * @param  n the 3x3 neighborhood array undergoing initialization
  * @param  i the row position of the cell
  * @param  j the column position of the cell
  *
  *  NORTH EDGE CELLS:
  *  row = 0
  *  -------------------
  *  |     |     |     |
  *  -------------------
  *  | 1,0 |  *  | 1,2 |
  *  -------------------
  *  | 2,0 | 2,1 | 2,2 |
  *  -------------------
  */
  private static void buildNorthEdge(boolean[][] p, boolean[][] n, int i, int j) {
    n[0][0] = false;
    n[0][1] = false;
    n[0][2] = false;
    n[1][0] = p[i][j - 1];
    n[1][1] = p[i][j]; // the cell!
    n[1][2] = p[i][j + 1];
    n[2][0] = p[i + 1][j - 1];
    n[2][1] = p[i + 1][j];
    n[2][2] = p[i + 1][j + 1];
  }
  
  /**
  * Initializes a 3x3 neighborhood with elements from a cell (*) on the east edge of a given population 
  *
  * @param  p the entire population
  * @param  n the 3x3 neighborhood array undergoing initialization
  * @param  i the row position of the cell
  * @param  j the column position of the cell
  *
  *  EAST EDGE CELLS:
  *  column = population[population.length - 1].length - 1
  *  -------------------
  *  | 0,0 | 0,1 |     |
  *  -------------------
  *  | 1,0 |  *  |     |
  *  -------------------
  *  | 2,0 | 2,1 |     |
  *  -------------------
  */
  private static void buildEastEdge(boolean[][] p, boolean[][] n, int i, int j) {
    n[0][0] = p[i - 1][j - 1];
    n[0][1] = p[i - 1][j];
    n[0][2] = false;
    n[1][0] = p[i][j - 1];
    n[1][1] = p[i][j]; // the cell!
    n[1][2] = false;
    n[2][0] = p[i + 1][j - 1];
    n[2][1] = p[i + 1][j];
    n[2][2] = false;  
  }
  
  /**
  * Initializes a 3x3 neighborhood with elements from a cell (*) on the south edge of a given population 
  *
  * @param  p the entire population
  * @param  n the 3x3 neighborhood array undergoing initialization
  * @param  i the row position of the cell
  * @param  j the column position of the cell
  *
  *  SOUTH EDGE CELLS:
  *  row = population.length - 1
  *  -------------------
  *  | 0,0 | 0,1 | 0,2 |
  *  -------------------
  *  | 1,0 |  *  | 1,2 |
  *  -------------------
  *  |     |     |     |
  *  -------------------
  */  
  private static void buildSouthEdge(boolean[][] p, boolean[][] n, int i, int j) {
    n[0][0] = p[i - 1][j - 1];
    n[0][1] = p[i - 1][j];
    n[0][2] = p[i - 1][j + 1];
    n[1][0] = p[i][j - 1];
    n[1][1] = p[i][j]; // the cell!
    n[1][2] = p[i][j + 1];
    n[2][0] = false;
    n[2][1] = false;
    n[2][2] = false;  
  }
  
  /**
  * Initializes a 3x3 neighborhood with elements from a cell (*) on the west edge of a given population 
  *
  * @param  p the entire population
  * @param  n the 3x3 neighborhood array undergoing initialization
  * @param  i the row position of the cell
  * @param  j the column position of the cell
  *
  *  WEST EDGE CELLS:
  *  column = 0
  *  -------------------
  *  |     | 0,1 | 0,2 |
  *  -------------------
  *  |     |  *  | 1,2 |
  *  -------------------
  *  |     | 2,1 | 2,2 |
  *  -------------------
  */
  private static void buildWestEdge(boolean[][] p, boolean[][] n, int i, int j) {
    n[0][0] = false;
    n[0][1] = p[i - 1][j];
    n[0][2] = p[i - 1][j + 1];
    n[1][0] = false;
    n[1][1] = p[i][j]; // the cell!
    n[1][2] = p[i][j + 1];
    n[2][0] = false;
    n[2][1] = p[i + 1][j];
    n[2][2] = p[i + 1][j + 1];  
  }
  
}
