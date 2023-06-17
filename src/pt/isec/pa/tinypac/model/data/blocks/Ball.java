package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

import java.io.Serializable;

public class Ball extends Element  implements Serializable {
    private final char symbol='o';
    public Ball(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
