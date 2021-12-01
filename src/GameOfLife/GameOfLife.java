import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
                cells[x][y] = new Cell(false);
            }
        }
        for(int[] p : pattern){
            cells[p[0]][p[1]] = new Cell(true);
        }
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++) {
                //set neighbors
                int n = 1;
                for(int x = i - 1; x <= i + 1 ; x++) {
                    for (int y = j - 1; y <= j + 1; y++) {
                        if (n != 5 && x >= 0 && y >= 0 && x < width && y < height) {
                            cells[i][j].addNeighbor(cells[x][y]);
                        }
                        n++;
                    }
                }
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
