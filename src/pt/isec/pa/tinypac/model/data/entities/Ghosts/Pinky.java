package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;

public class Pinky extends GhostWithObjective implements Serializable {
    private final char symbol='P';
    private final String[] objectives = {"tr","dr","tl","dl"};

    public Pinky(Environment environment,int y,int x){
        super(environment,(Element)environment.getElement(y,x));
        setObjectives(objectives);
    }

    @Override
    public char getSymbol() {
        if (this.getVulnerable()) {
            return this.getScaredSymbol();
        }
        return this.symbol;
    }
}
