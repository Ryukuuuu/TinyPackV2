package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

public class Wall extends Element {
    private final char symbol = 'x';

    public Wall(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
