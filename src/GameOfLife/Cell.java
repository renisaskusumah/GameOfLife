package GameOfLife;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private CellState state;
    private int[] position;
    private int numberOfAliveNeighbours = 0;
    private List<Cell> neighbours;

    public Cell(int[] position, boolean isAlive){
        this.position = position;
        state = (isAlive) ? CellState.ALIVE : CellState.DEAD;
        neighbours = new ArrayList<Cell>();
    }

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

    public boolean isAlive(){
        return (state == CellState.ALIVE);
    }

    public void checkNeighbors(){
        numberOfAliveNeighbours = 0;
        for(Cell n : neighbours){
            if(n.isAlive()) numberOfAliveNeighbours += 1;
        }
    }

    public int[] getPosition(){
        return position;
    }

    public void addNeighbor(Cell c){
        neighbours.add(c);
    }

    public int getTotalNeighbor(){
        return neighbours.size();
    }

}
