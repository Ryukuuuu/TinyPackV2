package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

public class Ball extends Element {
    private final char symbol='o';
    public Ball(int y,int x){super(y,x);}
    @Override
    public char getSymbol(){return symbol;}
}
