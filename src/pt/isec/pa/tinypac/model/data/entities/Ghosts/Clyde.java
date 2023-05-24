package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Position;

public class Clyde extends Ghost{
    private final char symbol='c';

    public Clyde(Environment environment, int y, int x, Element startingElement){super(environment,y,x,startingElement);}


    @Override
    public Position chooseNextPosition(Position currentPos){
        return new Position(0,0);
    }
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
