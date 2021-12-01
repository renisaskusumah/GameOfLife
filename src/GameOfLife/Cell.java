package GameOfLife;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private CellState state;
    private int[] position;
    private int numberOfAliveNeighbours = 0;
    private List<Cell> neighbours;

    //Cell can be constructed as ALIVE or DEAD
    public Cell(int[] position, boolean isAlive){
        this.position = position;
        state = (isAlive) ? CellState.ALIVE : CellState.DEAD;
        neighbours = new ArrayList<Cell>();
    }

    //update the state based on Game of Life rules
    public void updateState() {
        if(state == CellState.ALIVE){
            if (numberOfAliveNeighbours < 2 || numberOfAliveNeighbours > 3) {
                state = CellState.DEAD;
                neighbours.clear();
            } else if (numberOfAliveNeighbours == 2 || numberOfAliveNeighbours == 3){
                state = CellState.ALIVE;
            }
        }else{
            if(numberOfAliveNeighbours == 3){
                state = CellState.ALIVE;
            }
        }
    }

    //return cell state
    public boolean isAlive(){
        return (state == CellState.ALIVE);
    }

    //record number of alive neighbors
    public void checkNeighbors(){
        numberOfAliveNeighbours = 0;
        for(Cell n : neighbours){
            if(n.isAlive()) numberOfAliveNeighbours += 1;
        }
    }

    //return the X and Y position of the cell
    public int[] getPosition(){
        return position;
    }

    //register neighbor to the cell
    public void addNeighbor(Cell c){
        neighbours.add(c);
    }

    //return number of neighbors
    public int getTotalNeighbor(){
        return neighbours.size();
    }

}
