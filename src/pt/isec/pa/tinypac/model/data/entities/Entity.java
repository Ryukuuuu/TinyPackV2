package pt.isec.pa.tinypac.model.data.entities;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

public abstract class Entity extends Element {
    protected Environment environment;
    private Element inventory;

    protected Entity(Environment environment,int y,int x,Element startingElement){
        super(y,x);
        this.inventory=startingElement;
        this.environment=environment;
    }

    public void setInventory(Element element){this.inventory=element;}
    public Element getInventory(){return inventory;}

}
