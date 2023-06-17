package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

import java.io.Serializable;

public class Blank extends Element  implements Serializable {
    private final char symbol=' ';
    public Blank(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
