package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

public class Warp extends Element {
    private final char symbol='W';

    public Warp(){super();}

    @Override
    public char getSymbol(){return symbol;}
}
