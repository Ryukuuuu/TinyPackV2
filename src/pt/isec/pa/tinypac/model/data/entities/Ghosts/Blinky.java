package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.entities.Entity;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Position;

public class Blinky extends Ghost{
    private final char symbol= 'B';
    private int orientation=0;

    public Blinky(Environment environment, int y, int x, Element startingElement){
        super(environment,y,x,startingElement);
    }

    @Override
    public Position chooseNextPosition(Position currentPos){
        return new Position(0,0);
    }
    @Override
    public char getSymbol(){return this.symbol;}

    @Override
    public void evolve(){}
}
