package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

import java.io.Serializable;

public class Wall extends Element  implements Serializable {
    private final char symbol = 'x';

    public Wall(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
