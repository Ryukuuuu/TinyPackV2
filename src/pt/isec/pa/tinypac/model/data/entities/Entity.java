package pt.isec.pa.tinypac.model.data.entities;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

public abstract class Entity extends Element {
    protected Environment environment;
    private Element inventory;

    private boolean spawned;

    protected Entity(Environment environment,Element startingElement){
        super();
        this.inventory=startingElement;
        this.environment=environment;
    }

    public void setInventory(Element element){this.inventory=element;}
    public Element getInventory(){return inventory;}

    public boolean getSpawned(){return spawned;}
    public void setSpawned(boolean spawned){this.spawned=spawned;}
}
