package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;

public class Pinky extends Ghost{
    private final char symbol='P';

    private final String[] destination = {"topRight","downRight","topLeft","downLeft"};
    private int checkPoint=0; //Number of checkPoints reached

    private double tolerance=0.15;  //Distance from objectiv to validate it

    public Pinky(Environment environment,int y, int x,Element starting){super(environment,y,x,(Element)environment.getElement(y,x));}

    public void setCheckPoint(){checkPoint++;}
    public int getCheckPoint(){return checkPoint;}



    @Override
    public char getSymbol() {
        if (this.getVulnerable()) {
            return this.getScaredSymbol();
        }
        return this.symbol;
    }

    @Override
    public boolean evolve(){
        return true;
    }
    @Override
    public boolean undoEvolve(int y,int x){
        return true;
    }
}
