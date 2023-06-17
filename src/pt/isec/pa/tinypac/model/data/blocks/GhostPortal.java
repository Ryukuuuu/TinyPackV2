package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

import java.io.Serializable;

public class GhostPortal extends Element  implements Serializable {
    private final char symbol='Y';
    public GhostPortal(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
