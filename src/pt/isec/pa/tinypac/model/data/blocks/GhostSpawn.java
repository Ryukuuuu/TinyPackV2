package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

public class GhostSpawn extends Element {
    private final char symbol='y';
    public GhostSpawn(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
