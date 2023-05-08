package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

public class GhostSpawn extends Element {
    private final char symbol='y';
    public GhostSpawn(int y,int x){super(y,x);}
    @Override
    public char getSymbol(){return symbol;}
}
