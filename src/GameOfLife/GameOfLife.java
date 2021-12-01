package GameOfLife;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameOfLife {

    private int width = 600;
    private int height = 600;
    private List<Cell> cells;
    private boolean isRunning = false;
    //size of the cell to be drawn by graphic as rectangle inside the frame
    private static int CELL_WIDTH  = 10;

    //Initialize a pattern as cells on the grid and set neighbours
    public GameOfLife(int[][] p) {
        cells = new ArrayList<Cell>();
        for(int i = 0 ; i < p.length ; i++){
            Cell cell = new Cell(new int[]{p[i][0] * CELL_WIDTH,p[i][1]* CELL_WIDTH}, true);
            cells.add(cell);
        }
        updateGeneration();
    }

    /*Generate neighbor for certain cell
    *  XXX
    *  XOX
    *  XXX
    * */
    private void assignNeighbors(Cell cell){
        int n = 1;
        /*set up position for 3x3 grid
        * nX = X coordinate of neighbor
        * nY = Y coordinate of neighbor
        * n = index of the neighbour. 1 Cell has 8 neighbours
        * */
        for(int nX = cell.getPosition()[0] - CELL_WIDTH; nX <= cell.getPosition()[0] + CELL_WIDTH ; nX+= CELL_WIDTH){
            for(int nY = cell.getPosition()[1] - CELL_WIDTH  ; nY <= cell.getPosition()[1] + CELL_WIDTH ; nY+= CELL_WIDTH){
                //no need to generate 5th neighbor because the cell itself existed already
                if(n!= 5){
                    int index = -1;
                    //check is the neighbor is already existed in the game
                    for(Cell c : cells){
                        if(c.getPosition()[0] == nX && c.getPosition()[1] == nY ){
                            index  = cells.indexOf(c);
                            cell.addNeighbor(c);
                            break;
                        }
                    }
                    //create new if the cell object for neighbor doesn't exist in the cells List
                    if(index == -1 && nX >= 0  && nY >= 0 && nX <= width && nY <= height){
                        Cell neighbor = new Cell(new int[]{nX, nY} , false);
                        cell.addNeighbor(neighbor);
                        cells.add(neighbor);
                    }
                }
                n+=1;
            }
        }
    }

    /*
    * Expand cells regeneration for further checking of dead cells to be alived based on the neighbours condition
    * */
    private void updateGeneration(){
        int size  = cells.size();
        for(int i = 0 ; i < size;i++){
            if(cells.get(i).getTotalNeighbor() == 0) {
                assignNeighbors(cells.get(i));
            }
        }
    }


    //Will be called from the main class every certain miliseconds
    public void tick() {
        if(isRunning){
            updateGeneration();
            //count alive neighbors on every cell
            for(Cell cell : cells){
                cell.checkNeighbors();
            }
            //implement rules to update state of cell (ALIVE / DEAD)
            for(Cell cell : cells){
                cell.updateState();
            }
        }
    }

    public void stop() {
        isRunning = false;
    }

    //draw cells to the grid based on the X Y position
    public void draw(Graphics g) {
        for(Cell cell : cells){
            if(cell.isAlive()){
                g.setColor(Color.BLACK);
                g.fillRect(cell.getPosition()[0], cell.getPosition()[1], 10, 10);
            }
        }
    }

    public void start() {
        isRunning = true;
    }
}
