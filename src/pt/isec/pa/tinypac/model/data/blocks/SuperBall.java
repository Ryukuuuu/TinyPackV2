package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

public class SuperBall extends Element {
    private final char symbol='O';
    public SuperBall(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
