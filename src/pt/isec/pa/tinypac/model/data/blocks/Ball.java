package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

public class Ball extends Element {
    private final char symbol='o';
    public Ball(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
