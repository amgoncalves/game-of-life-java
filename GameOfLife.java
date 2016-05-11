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

public class GameOfLife {
  
  public static void main(String[] args) {
    
    int rows = 3;
    int columns = 3;
    boolean[][] population = new boolean[rows][columns];
    boolean[][] nextPopulation = new boolean[rows][columns];
    seed(population);
    
    for (int i = 0; i < 3; i++) {
      System.out.println("-----------------------------------------------------");
      System.out.println("Starting Population:");
      printMatrix(population);
      for (int row = 0; row < population.length; row++) {
        for (int col = 0; col < population.length; col++) {
          nextPopulation[row][col] = checkNeighbors(getNeighborhood(population, row, col));
        }
      }
      System.out.println("New Population:");
      printMatrix(nextPopulation);
      population = nextPopulation;
    }
  }
  
  /**
  * Seeds a given population with initial values.  True = alive, False = dead
  *
  * @param  population a two-dimensional boolean array 
  */
  private static void seed(boolean[][] population) {
    population[0][0] = true;
    population[0][1] = true;
    population[0][2] = true;
    population[1][0] = true;
    population[1][1] = true;
    population[1][2] = true;
    population[2][0] = true;
    population[2][1] = true;
    population[2][2] = true;
  }

  /**
  * Initializes a dead population
  *
  * @param  population a two-dimensional boolean array  
  */
  public static void wasteland(boolean[][] population) {
    for (int i = 0; i < population.length; i++) {
      for (int j = 0; j < population.length; j++) {
        population[i][j] = false;
      }
    }
  }
  
  /**
   * prints a 2-dimensional boolean array of any dimension to standard output
   *
   * @param  m a 2-dimensional boolean array
   */
   public static void printMatrix(boolean[][] m) {
     for(int i = 0; i < m.length; i++) {
       for (int j = 0; j < m[i].length; j++) {
         System.out.printf("%d ", (m[i][j] ? 1 : 0));
       }
       System.out.print("\n");
    }
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
    boolean verdict = neighborhood[1][1];
    if (neighborhood[1][1] == true && count < 2) // dies from underpopulation
      verdict = false;
    else if (neighborhood[1][1] == true && (count == 2 || count == 3)) // lives on to the next generation
      verdict = true;
    else if (neighborhood[1][1] == true && count > 3) // dies from overpopulation
      verdict = false;
    else if (neighborhood[1][1] == false && count == 3) // reproduction
      verdict = true;
    //String verdictS = verdict ? " Lives" : " Dies";
    //System.out.println("Count: " + count + verdictS);
    return verdict;
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
    else if (col == (population[population.length - 1].length - 1))
      buildEastEdge(population, neighborhood, row, col);
    else if (row == (population.length - 1))
      buildSouthEdge(population, neighborhood, row, col);
    else if (col == 0)
      buildWestEdge(population, neighborhood, row, col);
    else
      buildMiddle(population, neighborhood, row, col);
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
  private static void buildMiddle(boolean[][] p, boolean[][] n, int i, int j) {
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
