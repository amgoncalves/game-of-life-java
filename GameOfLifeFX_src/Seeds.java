public class Seeds {
  
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
  * Plants a 3x3 R-Pentomino seed in a given dead population
  *
  */
  public static void initRPentomino(boolean[][] p, int row, int col) {
    p[row - 1][col - 1] = false;
    p[row - 1][col] = true;
    p[row - 1][col + 1] = true;
    
    p[row][col - 1] = true;
    p[row][col] = true;
    p[row][col + 1] = false;
    
    p[row + 1][col - 1] = false;
    p[row + 1][col] = true;
    p[row + 1][col + 1] = false;    
  }
  
  public static void initBHeptomino(boolean[][] population, int row, int col) {
    population[row - 1][col - 1] = true;
    population[row - 1][col] = false;
    population[row - 1][col + 1] = true;
    population[row - 1][col + 2] = true;
    
    population[row][col - 1] = true;
    population[row][col] = true;
    population[row][col + 1] = true;
    population[row][col + 2] = false;
    
    population[row + 1][col - 1] = false;
    population[row + 1][col] = true;
    population[row + 1][col + 1] = false;
    population[row + 1][col + 2] = false;
  }
  
  public static void initPiPentomino(boolean[][] population, int row, int col) {
    population[row - 1][col - 1] = true;
    population[row - 1][col] = true;
    population[row - 1][col + 1] = true;
    
    population[row][col - 1] = true;
    population[row][col] = false;
    population[row][col + 1] = true;
    
    population[row + 1][col - 1] = true;
    population[row + 1][col] = false;
    population[row + 1][col + 1] = true;
  }
  
  public static void initAcorn(boolean[][] p, int row, int col) {
    p[row - 1][col - 3] = false;
    p[row - 1][col - 2] = true;
    p[row - 1][col - 1] = false;
    p[row - 1][col] = false;
    p[row - 1][col + 1] = false;
    p[row - 1][col + 2] = false;
    p[row - 1][col + 3] = false;
    
    p[row][col - 3] = false;
    p[row][col - 2] = false;
    p[row][col - 1] = false;
    p[row][col] = true;
    p[row][col + 1] = false;
    p[row][col + 2] = false;
    p[row][col + 3] = false;
    
    p[row + 1][col - 3] = true;
    p[row + 1][col - 2] = true;
    p[row + 1][col - 1] = false;
    p[row + 1][col] = false;
    p[row + 1][col + 1] = true;
    p[row + 1][col + 2] = true;
    p[row + 1][col + 3] = true;
  }
  
  public static void initGlider(boolean[][] population, int row, int col) {
    population[row - 1][col - 1] = false;
    population[row - 1][col] = true;
    population[row - 1][col + 1] = false;
    
    population[row][col - 1] = false;
    population[row][col] = false;
    population[row][col + 1] = true;
    
    population[row + 1][col - 1] = true;
    population[row + 1][col] = true;
    population[row + 1][col + 1] = true;  
  }
  
  public static void initBlockGlider(boolean[][] population, int row, int col) {
    population[row - 1][col - 1] = true;
    population[row - 1][col] = true;
    population[row - 1][col + 1] = false;
    population[row - 1][col + 2] = false;
    
    population[row][col - 1] = true;
    population[row][col] = false;
    population[row][col + 1] = true;
    population[row][col + 2] = false;
    
    population[row + 1][col - 1] = false;
    population[row + 1][col] = false;
    population[row + 1][col + 1] = true;
    population[row + 1][col + 2] = true;
  }
}