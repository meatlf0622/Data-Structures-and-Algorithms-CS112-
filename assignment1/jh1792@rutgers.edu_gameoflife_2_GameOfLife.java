package conwaygame;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        // WRITE YOUR CODE HERE
        StdIn.setFile(file);
        int row = StdIn.readInt();
        int col = StdIn.readInt();
        grid = new boolean [row][col];
        
        
        for(int i = 0; i< grid.length; i++){
            for(int j = 0; j<grid[i].length; j++ ){
                grid[i][j] = StdIn.readBoolean();
                if(grid[i][j] == false){
                    grid[i][j] = DEAD;
                    totalAliveCells ++;
                } else if(grid[i][j] == true) {
                    grid[i][j] = ALIVE;
                }
            }
        }
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        // WRITE YOUR CODE HERE
        if(grid[row][col] == ALIVE){
            return true;
        }
        else{
            return false; 
        }// update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        // WRITE YOUR CODE HERE
        boolean a = false; 
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length;j++){
                if(grid[i][j] == ALIVE){
                    a= true;
                    break;
                } 
            }
        }
        return a;
        // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        // WRITE YOUR CODE HERE
        
        int totalALive = 0;
        int lastRow = grid.length-1;
        int lastCol = grid[row].length-1; 

        // edge cases 

        // corner 1  top left 
        if(row == 0 && col ==0){
            if(grid[row][col+1] == ALIVE){
                totalALive++;
            }
            if(grid[row+1][col]==ALIVE){
                totalALive++;
            }
            if(grid[row+1][col+1]){
                totalALive++;
            }
            if(grid[lastRow][col] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow][col+1] == ALIVE){
                totalALive++;
            }
            if(grid[row][lastCol] == ALIVE){
                totalALive++;
            }
            if(grid[row+1][lastCol] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow][lastCol] == ALIVE){
                totalALive++;
            }
            return totalALive;
        }
        //corner 2 top right
        if(row == 0 && col == lastCol){
            if(grid[row][col-1] == ALIVE){
                totalALive++;
            }
            if(grid[row+1][col-1] == ALIVE){
                totalALive++;
            }
            if(grid[row+1][col] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow][col] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow][col-1] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow][0] == ALIVE){
                totalALive++;
            }
            if(grid[0][0] == ALIVE){
                totalALive++;
            }
            if(grid[1][0] == ALIVE){
                totalALive++;
            }
            return totalALive;
        }
        //corner 3 bottom left 
        if(row == lastRow && col == 0){
            if(grid[lastRow-1][0] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow-1][1] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow][1] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow][lastCol] == ALIVE){
                totalALive++;
            }
            if(grid[0][0] == ALIVE){
                totalALive++;
            }
            if(grid[0][1] == ALIVE){
                totalALive++;
            }
            if(grid[0][lastCol] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow-1][lastCol] == ALIVE){
                totalALive++;
            }
            return totalALive;
        }
        // corner 4 
        if(row == lastRow && col == lastCol){
            if(grid[lastRow][lastCol-1] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow-1][lastCol-1] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow-1][lastCol] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow][0] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow-1][0] == ALIVE){
                totalALive++;
            }
            if(grid[0][0] == ALIVE){
                totalALive++;
            }
            if(grid[0][lastCol] == ALIVE){
                totalALive++;
            }
            if(grid[0][lastCol-1] == ALIVE){
                totalALive++;
            }
            return totalALive;
        }
        
        // top
        if(row ==0){
            if(grid[row][col-1] == ALIVE){
                totalALive++; 
            }
            if(grid[row][col+1] == ALIVE){
                totalALive++; 
            }
            if(grid[row+1][col-1] == ALIVE){
                totalALive++; 
            }
            if(grid[row+1][col] == ALIVE){
                totalALive++; 
            }
            if(grid[row+1][col+1] == ALIVE){
                totalALive++; 
            }
            if(grid[lastRow][col-1] == ALIVE){
                totalALive++; 
            }
            if(grid[lastRow][col] == ALIVE){
                totalALive++; 
            }
            if(grid[lastRow][col+1] == ALIVE){
                totalALive++; 
            }
            return totalALive;
    }
        // bottom 
        if(row == lastRow){
            if(grid[lastRow][col+1] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow][col-1] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow-1][col] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow-1][col-1] == ALIVE){
                totalALive++;
            }
            if(grid[lastRow-1][col+1] == ALIVE){
                totalALive++;
            }
            if(grid[0][col] == ALIVE){
                totalALive++;
            }
            if(grid[0][col-1] == ALIVE){
                totalALive++;
            }
            if(grid[0][col+1] == ALIVE){
                totalALive++;
            }
            return totalALive;
        }
        // left 
        if(col == 0 ){
            if(grid[row-1][col] == ALIVE){
                totalALive++; 
            }
            if(grid[row+1][col] == ALIVE){
                totalALive++; 
            }
            if(grid[row][col+1] == ALIVE){
                totalALive++; 
            }
            if(grid[row-1][col+1] == ALIVE){
                totalALive++; 
            }
            if(grid[row+1][col+1] == ALIVE){
                totalALive++; 
            }
            if(grid[row][lastCol] == ALIVE){
                totalALive++; 
            }
            if(grid[row-1][lastCol] == ALIVE){
                totalALive++; 
            }
            if(grid[row+1][lastCol] == ALIVE){
                totalALive++; 
            }
            return totalALive++;
        }
        // right 
        if(col == lastCol){
            if(grid[row][lastCol-1] == ALIVE){
                totalALive++; 
            }
            if(grid[row-1][lastCol] == ALIVE){
                totalALive++; 
            }
            if(grid[row+1][lastCol] == ALIVE){
                totalALive++; 
            }
            if(grid[row-1][lastCol-1] == ALIVE){
                totalALive++; 
            }
            if(grid[row+1][lastCol-1] == ALIVE){
                totalALive++; 
            }
            if(grid[row][0] == ALIVE){
                totalALive++; 
            }
            if(grid[row-1][0] == ALIVE){
                totalALive++; 
            }
            if(grid[row+1][0] == ALIVE){
                totalALive++; 
            }
            return totalALive;
        }
        
        // Middle of Grid 
        //top left
        if(row>=1 && col>=1){
        if(grid[row-1][col-1] == ALIVE){
            totalALive++;
        // top middle 
        }if(grid[row-1][col] == ALIVE){
            totalALive++; 
        }
        // top right 
        if(grid[row-1][col+1] == ALIVE){
            totalALive++;
        }
        //middle left 
        if(grid[row][col-1] == ALIVE){
            totalALive++; 
        }
        // middle right
        if(grid[row][col+1] == ALIVE){
            totalALive++;
        }
        // botttom left 
        if(grid[row+1][col-1] == ALIVE){
            totalALive++; 
        }
        //bottom middle
        if(grid[row+1][col] == ALIVE){
            totalALive++; 
        }
        //bottom right 
        if(grid[row+1][col+1] == ALIVE){
            totalALive++; 
        }
        return totalALive;
    } else{
        return totalALive;
    }
        // update this line, provided so that code compiles
    
}

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        // WRITE YOUR CODE HERE
        boolean[][] newGrid = new boolean[grid.length][grid[0].length]; 

        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length; j++){
                if(numOfAliveNeighbors(i,j) <=1){
                    if(grid[i][j] == ALIVE)
                    newGrid[i][j] = DEAD;
                    totalAliveCells--; 
                } 
                if(numOfAliveNeighbors(i,j)==3){
                    if(grid[i][j] == DEAD){
                    newGrid[i][j] = ALIVE;
                    totalAliveCells++;
                    }
                }
                if(numOfAliveNeighbors(i,j) == 2 || numOfAliveNeighbors(i,j) == 3){
                    if(grid[i][j] == ALIVE){
                        newGrid[i][j] = ALIVE; 

                    }
                }
                if(numOfAliveNeighbors(i,j) >= 4){
                    newGrid[i][j] = DEAD; 
                    totalAliveCells--; 
                }
            }
        
    }
        return newGrid;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        // WRITE YOUR CODE HERE
        boolean[][] newGrid = computeNewGrid();
        for(int i = 0; i < grid.length; i++){
            for(int j  = 0; j < grid[i].length; j++){
                grid[i][j] = newGrid[i][j];
            }
        } 
         
        
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        // WRITE YOUR CODE HERE
        while(n>0){
        boolean[][] newGrid = computeNewGrid();
        for(int i = 0; i < grid.length; i++){
            for(int j  = 0; j < grid[i].length; j++){
                grid[i][j] = newGrid[i][j];
            }
        } 
        n--;
    }
       
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {
        // WRITE YOUR CODE HERE
         
       
        WeightedQuickUnionUF com = new WeightedQuickUnionUF(grid.length, grid[0].length);
        ArrayList <Integer> groups = new ArrayList<>();
        int lastRow = grid.length-1;
        int lastCol = grid[0].length-1; 
         // corner 1  top left 
         for(int row = 0; row<grid.length; row++){
            for(int col =0; col<grid[0].length; col++ ){
               
         // corner 1
         if(grid[row][col] == ALIVE){
           if(row == 0 && col ==0 ){
            if(grid[row][col+1] == ALIVE){
                com.union(row, col, row, col+1);
            } 
            if(grid[row+1][col]==ALIVE){
                com.union(row, col, row+1, col);
            } 
            if(grid[row+1][col+1]){
                com.union(row, col, row+1, col+1);
            } 
            if(grid[lastRow][col] == ALIVE){
                com.union(row, col, lastRow, col);
            } 
            if(grid[lastRow][col+1] == ALIVE){
                com.union(row, col, lastRow, col+1);
            } 
            if(grid[row][lastCol] == ALIVE){
                com.union(row, col, row, lastCol);
            }
            if(grid[row+1][lastCol] == ALIVE){
                com.union(row, col, row+1, lastCol);
            } 
            if(grid[lastRow][lastCol] == ALIVE){
                com.union(row, col, lastRow, lastCol);
            } 
            
        } //corner 2 top right
        else if(row == 0 && col == lastCol){
            if(grid[row][col-1] == ALIVE){
                com.union(row, col, row, col-1);
            }
            if(grid[row+1][col-1] == ALIVE){
                com.union(row, col, row+1, col-1);
            }
            if(grid[row+1][col] == ALIVE){
                com.union(row, col, row+1, col);
            }
            if(grid[lastRow][col] == ALIVE){
                com.union(row, col, lastRow, col);
            }
            if(grid[lastRow][col-1] == ALIVE){
                com.union(row, col, lastRow, col-1);
            }
            if(grid[lastRow][0] == ALIVE){
                com.union(row, col, lastRow, 0);
            }
            if(grid[0][0] == ALIVE){
                com.union(row, col, 0, 0);
            }
            if(grid[1][0] == ALIVE){
                com.union(row, col, 1, 0);
            }
          
        }else
        //corner 3 bottom left 
          
    
        if(row == lastRow && col == 0){
            if(grid[lastRow-1][0] == ALIVE){
                com.union(row, col, lastRow-1, 0);
            }
            if(grid[lastRow-1][1] == ALIVE){
                com.union(row, col, lastRow-1, 1);
            }
            if(grid[lastRow][1] == ALIVE){
                com.union(row, col, lastRow, 1);
            }
            if(grid[lastRow][lastCol] == ALIVE){
                com.union(row, col, lastRow, lastCol);
            }
            if(grid[0][0] == ALIVE){
                com.union(row, col, 0, 0);
                
            }
            if(grid[0][1] == ALIVE){
                com.union(row, col, 0, 1);
            }
            if(grid[0][lastCol] == ALIVE){
                com.union(row, col, 0, lastCol);
            }
            if(grid[lastRow-1][lastCol] == ALIVE){
                com.union(row, col, lastRow-1, lastCol);
            }
            
        }
        // corner 4 
           else if(row == lastRow && col == lastCol){
            if(grid[lastRow][lastCol-1] == ALIVE){
                com.union(row, col, lastRow, lastCol-1);
            }
            if(grid[lastRow-1][lastCol-1] == ALIVE){
                com.union(row, col, lastRow-1, lastCol-1);
            }
            if(grid[lastRow-1][lastCol] == ALIVE){
                com.union(row, col, lastRow-1, lastCol);
            }
            if(grid[lastRow][0] == ALIVE){
                com.union(row, col, lastRow, 0);
            }
            if(grid[lastRow-1][0] == ALIVE){
                com.union(row, col, lastRow-1, 0);
            }
            if(grid[0][0] == ALIVE){
                com.union(row, col, 0, 0);
            }
            if(grid[0][lastCol] == ALIVE){
                com.union(row, col, 0, lastCol);
            }
            if(grid[0][lastCol-1] == ALIVE){
                com.union(row, col, 0, lastCol-1);
            }
           
        }
        // top
    else if(row == 0 && !(col == 0 || col == lastCol)){
            if(grid[row][col-1] == ALIVE){
                com.union(row, col, row, col-1);
            }
            if(grid[row][col+1] == ALIVE){
                com.union(row, col, row, col+1);
            }
            if(grid[row+1][col-1] == ALIVE){
                com.union(row, col, row+1, col-1);
            }
            if(grid[row+1][col] == ALIVE){
                com.union(row, col, row+1, col);
            }
            if(grid[row+1][col+1] == ALIVE){
                com.union(row, col, row+1, col+1);
            }
            if(grid[lastRow][col-1] == ALIVE){
                com.union(row, col, lastRow, col-1);
            }
            if(grid[lastRow][col] == ALIVE){
                com.union(row, col, lastRow, col);
            }
            if(grid[lastRow][col+1] == ALIVE){
                com.union(row, col, lastRow, col+1);
            }
        
    }
        // bottom 
        else if(row == lastRow && !(col==0 && col == lastCol)){
            if(grid[lastRow][col+1] == ALIVE){
                com.union(row, col, lastRow, col+1);
            }
            if(grid[lastRow][col-1] == ALIVE){
                com.union(row, col, lastRow, col-1);
            }
            if(grid[lastRow-1][col] == ALIVE){
                com.union(row, col, lastRow-1, col); //
            }
            if(grid[lastRow-1][col-1] == ALIVE){
                com.union(row, col, lastRow-1, col-1); // 
            }
            if(grid[lastRow-1][col+1] == ALIVE){
                com.union(row, col, lastRow-1, col+1); //
            }
            if(grid[0][col] == ALIVE){
                com.union(row, col, 0, col);
            }
            if(grid[0][col-1] == ALIVE){
                com.union(row, col, 0, col-1);
            }
            if(grid[0][col+1] == ALIVE){
                com.union(row, col, 0, col+1);
            }
            
        }
        // left 
    else if(col == 0 && !(row == 0 || row == lastRow)){
            if(grid[row-1][col] == ALIVE){
                com.union(row, col, row-1, col);
            }
            if(grid[row+1][col] == ALIVE){
                com.union(row, col, row+1, col);
            }
            if(grid[row][col+1] == ALIVE){
                com.union(row, col, row, col+1);
            }
            if(grid[row-1][col+1] == ALIVE){
                com.union(row, col, row-1, col+1);
            }
            if(grid[row+1][col+1] == ALIVE){
                com.union(row, col, row+1, col+1);
            }
            if(grid[row][lastCol] == ALIVE){
                com.union(row, col, row, lastCol);
            }
            if(grid[row-1][lastCol] == ALIVE){
                com.union(row, col, row-1, lastCol);
            }
            if(grid[row+1][lastCol] == ALIVE){
                com.union(row, col, row+1, lastCol); 
            }

        }
        // right 
     else if(col == lastCol && !(row == 0 || row == lastRow)){
            if(grid[row][lastCol-1] == ALIVE){
                com.union(row, col, row, lastCol-1);
            }
            if(grid[row-1][lastCol] == ALIVE){
                com.union(row, col, row-1, lastCol);
            }
            if(grid[row+1][lastCol] == ALIVE){
                com.union(row, col, row+1, lastCol);
            }
            if(grid[row-1][lastCol-1] == ALIVE){
                com.union(row, col, row-1, lastCol-1);
            }
            if(grid[row+1][lastCol-1] == ALIVE){
                com.union(row, col, row+1, lastCol-1);
            }
            if(grid[row][0] == ALIVE){
                com.union(row, col, row, 0);
            }
            if(grid[row-1][0] == ALIVE){
                com.union(row, col, row-1, 0);
            }
            if(grid[row+1][0] == ALIVE){
                com.union(row, col, row+1, 0);
            }
        }
        // Middle of Grid 
        //top left
        else if(row>=1 && col>=1 && ( row<lastRow && col<lastCol)){
        if(grid[row-1][col-1] == ALIVE){
            com.union(row, col, row-1, col-1);
        // top middle 
        }if(grid[row-1][col] == ALIVE){
            com.union(row, col, row-1, col);
        }
        // top right 
        if(grid[row-1][col+1] == ALIVE){
            com.union(row, col, row-1, col+1);
        }
        //middle left 
        if(grid[row][col-1] == ALIVE){
            com.union(row, col, row, col+1);
        }
        // middle right
        if(grid[row][col+1] == ALIVE){
            com.union(row, col, row, col+1);
        }
        // botttom left 
        if(grid[row+1][col-1] == ALIVE){
            com.union(row, col, row+1, col-1);
        }
        //bottom middle
        if(grid[row+1][col] == ALIVE){
            com.union(row, col, row+1, col);
        }
        //bottom right 
        if(grid[row+1][col+1] == ALIVE){
            com.union(row, col, row+1, col+1);
             }
         }
        }    
    }
    }
   
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length; j++){
                if(grid[i][j] == ALIVE){
                    int r  = com.find(i,j);
                    if(!groups.contains(r)){
                        groups.add(r);
                    }
                }
            }
        }
    

      // update this line, provided so that code compiles
    return groups.size();
    
}
}
    
