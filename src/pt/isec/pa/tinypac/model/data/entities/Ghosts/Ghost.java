package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.entities.Entity;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;

public abstract class Ghost extends Entity {

    private final char scaredGhost = 'S';   //if vulnerable==true use this
    private boolean vulnerable=false;
    private boolean spawned=false;
    private ArrayList<Position> posRecord = new ArrayList<>();

    public Ghost(Environment environment, int y, int x, Element startingElement){
        super(environment,y,x,startingElement);
    }

    public boolean getVulnerable(){return vulnerable;}
    public void setVulnerable(boolean vulnerable){this.vulnerable=vulnerable;}
    public void changeVulnerable(){vulnerable=!vulnerable;}
    public char getScaredSymbol(){return this.scaredGhost;}
    public boolean getSpawned(){return spawned;}
    public void setSpawned(boolean spawned){this.spawned=spawned;}
    public void changeSpawned(){this.spawned = !this.spawned;}

    public ArrayList<Position> getPosRecord(){return posRecord;}

    private void addMove(Position pos){posRecord.add(pos);}

    private void removeMove(Position pos){
        posRecord.remove(pos);
    }

    public void gotoPortal(){
        Position pos = environment.getGhostPortal();
        if(pos == null){
            return;
        }
        Element portal = (Element) environment.getElement(pos.y(),pos.x());

        environment.addElement(this.getInventory(),this.getY(),this.getX());
        this.setInventory(portal);
        environment.addElement(this,pos.y(), pos.x());
        this.setY(pos.y());
        this.setX(pos.x());
        this.setSpawned(true);
    }

    abstract public Position chooseNextPosition(Position currentPos);

    abstract public boolean evolve(Position currentPos,int y,int x);
    abstract public boolean undoEvolve(Position currentPos,int y,int x);
}
