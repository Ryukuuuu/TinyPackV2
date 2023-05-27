package pt.isec.pa.tinypac.model.data.entities;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.blocks.*;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.Ghost;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

public class PacMan extends Entity{
    private final char symbol='C';
    private int rotation;   //0->Neutral|1->left|2->up|3->right|4->down


    public PacMan(Environment environment,int y, int x,Element startingElement){
        super(environment,y,x,startingElement);
    }

    public int getRotation(){return this.rotation;}
    public void setRotation(int rotation){
        this.rotation=rotation;
    }


    public void die(){
        environment.addElement(this.getInventory(),this.getY(),this.getX());
    }


    private void move(Position currentPos,int y,int x){
        Element elem=(Element)environment.getElement(y,x);
        if(!(elem instanceof Wall) && !(elem instanceof GhostPortal)) {
            environment.addElement((Element) this.getInventory(), currentPos.y(), currentPos.x());
            this.setInventory(elem);
            environment.addElement(this, y, x);
            this.setX(x);
            this.setY(y);
            return;
        }
        if(this.getInventory() instanceof Warp){
            Element e = environment.getWarpPair((Warp)this.getInventory());
            environment.addElement((Element)this.getInventory(),currentPos.y(), currentPos.x());
            this.setInventory(e);
            environment.addElement(this,e.getY(),e.getX());
            this.setY(e.getY());
            this.setX(e.getX());
        }
    }


    @Override
    public char getSymbol(){return symbol;}

    public boolean evolve(){
        Position currentPos = this.getXY();
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
