package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.entities.Entity;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Position;

public abstract class Ghost extends Entity {

    private boolean vulnerable=false;
    public Ghost(Environment environment, int y, int x, Element startingElement){
        super(environment,y,x,startingElement);
    }

    public boolean getVulnerable(){return vulnerable;}
    public void setVulnerable(boolean vulnerable){this.vulnerable=vulnerable;}
    public void changeVulnerable(){vulnerable=!vulnerable;}

    public void gotoPortal(){
        Position pos = environment.getGhostPortal();
        Element portal = (Element) environment.getElement(pos.y(),pos.x());

        environment.addElement(this.getInventory(),this.getY(),this.getX());
        this.setInventory(portal);
        environment.addElement(this,pos.y(), pos.x());
        this.setY(pos.y());
        this.setX(pos.x());
    }
    abstract public Position chooseNextPosition(Position currentPos);
}
