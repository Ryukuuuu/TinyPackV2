package pt.isec.pa.tinypac.model.data.entities;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;

public abstract class Entity extends Element implements Serializable {
    protected Environment environment;
    private Element inventory;

    private boolean spawned;

    private int rotation;

    private boolean vulnerable;

    protected Entity(Environment environment,Element startingElement,int rotation){
        super();
        this.inventory=startingElement;
        this.environment=environment;
        this.rotation=rotation;
        vulnerable=true;
    }

    public void setInventory(Element element){this.inventory=element;}
    public Element getInventory(){return inventory;}

    public boolean getSpawned(){return spawned;}
    public void setSpawned(boolean spawned){this.spawned=spawned;}

    public void setRotation(int rotation){this.rotation=rotation;}
    public int getRotation(){return this.rotation;}

    public void setVulnerable(boolean vulnerable){this.vulnerable=vulnerable;}
    public boolean getVulnerable(){return vulnerable;}
}
