package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

import java.io.Serializable;

public class SuperBall extends Element  implements Serializable {
    private final char symbol='O';
    public SuperBall(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
