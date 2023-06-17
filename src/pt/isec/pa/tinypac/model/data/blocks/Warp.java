package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

import java.io.Serializable;

public class Warp extends Element  implements Serializable {
    private final char symbol='W';

    public Warp(){super();}

    @Override
    public char getSymbol(){return symbol;}
}
