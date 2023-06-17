package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

import java.io.Serializable;

public class PacManSpawn extends Element  implements Serializable {
    private final char symbol='M';
    public PacManSpawn(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
