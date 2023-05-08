package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

public class FruitSpawn extends Element {
    private final char symbol='F';
    public FruitSpawn(int y,int x){super(y,x);}
    @Override
    public char getSymbol(){return symbol;}
}
