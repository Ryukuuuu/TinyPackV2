package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

public class GhostPortal extends Element {
    private final char symbol='Y';
    public GhostPortal(int y,int x){super(y,x);}
    @Override
    public char getSymbol(){return symbol;}
}
