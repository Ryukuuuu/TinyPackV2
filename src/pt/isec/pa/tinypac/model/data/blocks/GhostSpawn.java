package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

import java.io.Serializable;

public class GhostSpawn extends Element  implements Serializable {
    private final char symbol='y';
    public GhostSpawn(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
