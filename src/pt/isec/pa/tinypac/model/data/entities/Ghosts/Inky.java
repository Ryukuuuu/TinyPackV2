package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;


public class Inky extends GhostWithObjective  implements Serializable {
    private final char symbol='I';

    private final String[] objectives= {"dr","dl","tr","tl"};

    public Inky(Environment environment,int y, int x){
        super(environment,(Element)environment.getElement(y,x));
        setObjectives(objectives);
    }

    @Override
    public char getSymbol(){
        if(this.getVulnerable()){
            return this.getScaredSymbol();
        }
        return this.symbol;
    }
}
