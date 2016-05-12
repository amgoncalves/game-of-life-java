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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class GameOfLife extends Application {

  private static int mRow = 100;
  private static int mCol = 100;
  private static int mSize = 5;

  @Override
  public void start(Stage primaryStage) {
    
    boolean[][] population = new boolean[mRow][mCol];
    boolean[][] nextPopulation = new boolean[mRow][mCol];

    int seedRow = mRow / 2;
    int seedCol = mCol / 2;  
    
    Button btStop = new Button("Stop");
    Button btStart = new Button("Start");
    Button btClear = new Button("Clear");
    
    Button btAcorn = new Button("Acorn");
    Button btRPent = new Button("R-Pentomino");
    Button btBHept = new Button("B-Heptomino");
    Button btPiPent = new Button("Pi-Pentomino");
    Button btGlider = new Button("Glider");
    Button btBlockGlider = new Button("Block-Glider");
    
    Pane board = new Pane();
    drawBoard(board, population);
      
    EventHandler<ActionEvent> generateEvent = e -> {
      generate(population, nextPopulation);    
      drawBoard(board, population);
    };
    
    Timeline tick = new Timeline(
      new KeyFrame(Duration.millis(1000), generateEvent));
    tick.setCycleCount(Timeline.INDEFINITE);
    
    //tick.play();
    
    btStop.setOnAction((ActionEvent e) -> tick.stop());
    btStart.setOnAction((ActionEvent e) -> tick.play());
    
    btClear.setOnAction((ActionEvent e) -> {
      seed(population, "wasteland", seedRow, seedCol);
      drawBoard(board, population);
    });
    
    btAcorn.setOnAction((ActionEvent e) -> {
      seed(population, "acorn", seedRow, seedCol);
      drawBoard(board, population);
    });
    
    btRPent.setOnAction((ActionEvent e) -> {
      seed(population, "r-pentomino", seedRow, seedCol);
      drawBoard(board, population);
    });
    
    btBHept.setOnAction((ActionEvent e) -> {
      seed(population, "b-heptomino", seedRow, seedCol);
      drawBoard(board, population);
    });
    
    btPiPent.setOnAction((ActionEvent e) -> {
      seed(population, "pi-heptomino", seedRow, seedCol);
      drawBoard(board, population);
    });
    
    btGlider.setOnAction((ActionEvent e) -> {
      seed(population, "glider", seedRow, seedCol);
      drawBoard(board, population);
    });
    
    btBlockGlider.setOnAction((ActionEvent e) -> {
      seed(population, "block-glider", seedRow, seedCol);
      drawBoard(board, population);
    });
    
    // Place controls into panes:
    HBox seedControls1 = new HBox();
    seedControls1.setSpacing(10);
    seedControls1.setAlignment(Pos.CENTER);
    seedControls1.getChildren().addAll(btAcorn, btRPent, btBHept); 
    
    HBox seedControls2 = new HBox();
    seedControls2.setSpacing(10);
    seedControls2.setAlignment(Pos.CENTER);
    seedControls2.getChildren().addAll(btPiPent, btGlider, btBlockGlider);
    
    HBox mainControls = new HBox();
    mainControls.setSpacing(10);
    mainControls.setAlignment(Pos.CENTER);
    mainControls.getChildren().addAll(btStop, btStart, btClear);
    
    VBox main = new VBox();
    main.setSpacing(10);
    main.setAlignment(Pos.CENTER);
    main.getChildren().addAll(board, seedControls1, seedControls2, mainControls);    
    
    int sceneHeight = (mRow * mSize) + 140;
    int sceneWidth = mCol * mSize;
    
    Scene scene = new Scene(main, sceneWidth, sceneHeight);
    primaryStage.setTitle("Conway's Game of Life");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }
   
  /**
  * Launches JavaFX application
  */
  public static void main(String[] args) {
     launch(args);
  }
   
  /**
  * Draws the game of life board by converting the two-dimensional boolean array
  * into a two-dimensional grid of black (living) and white (dead) square rectangles
  *
  * @param  board the pane to display the graphical representation of a two-dimensional boolean array
  * @param  population a two-dimensional boolean array
  */
  public static void drawBoard(Pane board, boolean[][] population) {
    for (int i = 0; i < mRow; i++) {
     for (int j = 0; j < mCol; j++) {
      Rectangle rec = new Rectangle(mSize * j, mSize * i, mSize, mSize);
      if (population[i][j])
       rec.setFill(Color.BLACK);
      else
       rec.setFill(Color.WHITE);
      board.getChildren().add(rec);
     }
    }
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
  * Seeds a given population with initial values. True = alive, False = dead
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
      case "wasteland":    Seeds.wasteland(population);
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
        }
      }
    }
    if (neighborhood[1][1] == true && count < 2) // loneliness: cell dies from underpopulation
      return false;
    else if (neighborhood[1][1] == true && (count == 2 || count == 3)) // stasis: live cell continues to live on to the next generation
      return true;
    else if (neighborhood[1][1] == true && count > 3) // overcrowding: live cell dies from overpopulation
      return false;
    else if (neighborhood[1][1] == false && count == 3) // reproduction: dead cell is populated with a live cell
      return true;
    else if (neighborhood[1][1] == false && (count <= 2 || count > 3)) // conditions where a dead cell stays dead
      return false;
    return neighborhood[1][1]; // statement never reaches this point
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
    n[1][1] = p[i][j];
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
    n[1][1] = p[i][j];
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
    n[1][1] = p[i][j];
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
    n[1][1] = p[i][j];
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
    n[1][1] = p[i][j];
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
    n[1][1] = p[i][j];
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
    n[1][1] = p[i][j];
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
    n[1][1] = p[i][j];
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
    n[1][1] = p[i][j];
    n[1][2] = p[i][j + 1];
    n[2][0] = false;
    n[2][1] = p[i + 1][j];
    n[2][2] = p[i + 1][j + 1];  
  }
  
}
