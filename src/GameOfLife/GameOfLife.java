import java.awt.*;

public class GameOfLife {

    private int width = 600;
    private int height = 600;
    private Cell[][] cells;
    private boolean isRunning = false;
    //size of the cell to be drawn by graphic as rectangle inside the frame
    private static int CELL_WIDTH  = 5;

    //Initialize a pattern as cells on the grid and set neighbours
    public GameOfLife(int[][] pattern) {
        cells = new Cell[width][height];
        for(int x = 0 ; x < width ; x++){
            for(int y = 0 ; y < height ; y++) {
                boolean alive = false;
                for(int[] p : pattern){
                    if(x == p[0] && y == p[1]) {
                        alive = true;
                        break;
                    }
                }
                cells[x][y] = new Cell(alive);
            }
        }

        //loop for grid (width x height)
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++) {
                //set 8 neighbors
                if(i > 0 && j >0) cells[i][j].addNeighbor(cells[i - 1][j - 1]);
                if(j > 0) cells[i][j].addNeighbor(cells[i][j - 1]);
                if(i < width - 1 && j > 0) cells[i][j].addNeighbor(cells[i + 1][j - 1]);

                if(i > 0) cells[i][j].addNeighbor(cells[i - 1][j]);
                if(i < width - 1) cells[i][j].addNeighbor(cells[i + 1][j]);

                if(i > 0 && j < height - 1) cells[i][j].addNeighbor(cells[i - 1][j + 1]);
                if(j < height - 1) cells[i][j].addNeighbor(cells[i][j + 1]);
                if(i < width - 1 && j < height - 1) cells[i][j].addNeighbor(cells[i + 1][j + 1]);
            }
        }
    }
    //Will be called from the main class every certain miliseconds
    public void tick() {
        if(isRunning){
            //count alive neighbors on every cell
            for(int i = 0 ; i < cells.length ; i++){
                for(int j = 0 ; j < height ; j++) {
                    cells[i][j].checkNeighbors();
                }
            }
            for(int i = 0 ; i < cells.length ; i++){
                for(int j = 0 ; j < height ; j++) {
                      cells[i][j].updateState();
                }
            }
        }
    }

    public void stop() {
        isRunning = false;
    }
    //draw cells to the grid based on the X Y position
    public void draw(Graphics g) {
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++) {
                if (cells[i][j].isAlive()) {
                   g.setColor(Color.BLACK);
                   g.fillRect(i * CELL_WIDTH, j * CELL_WIDTH, CELL_WIDTH, CELL_WIDTH);
                }
            }
        }
    }

    public void start() {
        isRunning = true;
    }
}
