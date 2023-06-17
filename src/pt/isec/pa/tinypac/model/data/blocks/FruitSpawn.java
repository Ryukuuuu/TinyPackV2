package pt.isec.pa.tinypac.model.data.blocks;

import pt.isec.pa.tinypac.model.data.maze.Element;

public class FruitSpawn extends Element {
    private final char symbol='F';

    private boolean spawned=false;

    public boolean getSpawned(){return spawned;}
    public void setSpawned(boolean spawned){this.spawned=spawned;}

    public FruitSpawn(){super();}
    @Override
    public char getSymbol(){return symbol;}
}
