package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

public class Blank extends Element {
    private final char symbol=' ';
    public Blank(int y,int x){super(y,x);}
    @Override
    public char getSymbol(){return symbol;}
}
