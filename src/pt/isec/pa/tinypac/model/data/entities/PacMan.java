package pt.isec.pa.tinypac.model.data.entities;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.blocks.*;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.Ghost;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;

public class PacMan extends Entity implements Serializable {
    private final char symbol='C';
    private boolean alive=true;


    public PacMan(Environment environment,int y, int x,Element startingElement){
        super(environment,startingElement,0);
    }

    public boolean getAlive(){return alive;}
    public void setAlive(boolean alive){this.alive=alive;}

    public void die(){
        //Position currentPos = environment.getElementPosition(this);
        //environment.addElement(this.getInventory(),currentPos.y(),currentPos.x());
        setAlive(false);
        setSpawned(false);
        System.out.println("\n-------------------------------\nPAcman died\n-------------------------------\n");
    }


    private void move(Position currentPos,int y,int x){
        Element elem=(Element)environment.getElement(y,x);
        if(!(elem instanceof Wall) && !(elem instanceof GhostPortal)) {
            environment.addElement((Element) this.getInventory(), currentPos.y(), currentPos.x());
            this.setInventory(elem);
            environment.addElement(this, y, x);
            return;
        }
        if(this.getInventory() instanceof Warp){
            Element e = environment.getWarpPair((Warp)this.getInventory());
            Position ePosition = environment.getElementPosition(e);
            environment.addElement(this.getInventory(),currentPos.y(), currentPos.x());
            this.setInventory(e);
            environment.addElement(this,ePosition.y(),ePosition.x());
        }
    }


    @Override
    public char getSymbol(){return symbol;}

    public boolean evolve(){
        Position currentPos = environment.getElementPosition(this);
        if(currentPos==null){
            return false;
        }
        int x=currentPos.x();
        int y=currentPos.y();
        switch (this.getRotation()){
            case 1-> x--;   //left
            case 2-> y--;   //up
            case 3-> x++;   //right
            case 4-> y++;   //down
            case 0 -> {     //neutral
                return true;
            }
        }
        if(this.getSpawned()){
            move(currentPos,y,x);
        }
        return true;
    }
}
