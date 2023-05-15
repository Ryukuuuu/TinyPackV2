package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Position;



public class Inky extends Ghost{
    private final char symbol='I';
    private int rotation;

    public Inky(Environment environment,int y, int x,Element startingElement){
        super(environment,y,x,(Element)environment.getElement(y,x));
    }

    @Override
    public Position chooseNextPosition(Position currentPos){
        return new Position(0,0);
    }

    @Override
    public char getSymbol(){
        if(this.getVulnerable()){
            return this.getScaredSymbol();
        }
        return this.symbol;
    }
    @Override
    public void evolve(){}
}
