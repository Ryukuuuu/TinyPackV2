package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

public class PacManSpawn extends Element {
    private final char symbol='M';
    public PacManSpawn(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
