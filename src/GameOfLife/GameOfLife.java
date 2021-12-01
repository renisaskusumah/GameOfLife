package GameOfLife;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameOfLife {

    private int width = 600;
    private int height = 600;
    private List<Cell> cells;
    private boolean isRunning = false;
    private int CELL_WIDTH  = 10;

    public GameOfLife(int[][] p) {
        cells = new ArrayList<Cell>();
        for(int i = 0 ; i < p.length ; i++){
            Cell cell = new Cell(new int[]{p[i][0] * CELL_WIDTH,p[i][1]* CELL_WIDTH}, true);
            cells.add(cell);
        }
        updateGeneration();
    }

    private void assignNeighbors(Cell cell){
        int n = 1;
        for(int nX = cell.getPosition()[0] - CELL_WIDTH; nX <= cell.getPosition()[0] + CELL_WIDTH ; nX+= CELL_WIDTH){
            for(int nY = cell.getPosition()[1] - CELL_WIDTH  ; nY <= cell.getPosition()[1] + CELL_WIDTH ; nY+= CELL_WIDTH){
                if(n!= 5){
                    int index = -1;
                    for(Cell c : cells){
                        if(c.getPosition()[0] == nX && c.getPosition()[1] == nY ){
                            index  = cells.indexOf(c);
                            cell.addNeighbor(c);
                            break;
                        }
                    }
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

    public void updateGeneration(){
        int size  = cells.size();
        for(int i = 0 ; i < size;i++){
            if(cells.get(i).getTotalNeighbor() == 0) {
                assignNeighbors(cells.get(i));
            }
        }

    }

    public void tick() {
        if(isRunning){
            updateGeneration();
            for(Cell cell : cells){
                cell.checkNeighbors();
            }
            for(Cell cell : cells){
                cell.updateState();
            }
        }
    }

    public void stop() {
        isRunning = false;
    }

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
