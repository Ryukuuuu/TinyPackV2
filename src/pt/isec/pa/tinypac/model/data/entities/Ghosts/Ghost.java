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
    //private ArrayList<Position> posRecord = new ArrayList<>();

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

    public void move(Position currentPos,Position nextPos){
        Element elem=(Element) environment.getElement(nextPos.y(), nextPos.x());
        environment.addElement((Element) this.getInventory(), currentPos.y(), currentPos.x());
        this.setInventory(elem);
        environment.addElement(this, nextPos.y(), nextPos.x());
        this.setX(nextPos.x());
        this.setY(nextPos.y());
    }

    public ArrayList<Integer> getPossibleMoves(int previousRotation){
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        Position currentPos = this.getXY();
        char elem;
        //left
        elem = environment.getElement(currentPos.y(), currentPos.x()-1).getSymbol();
        if(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y' && previousRotation!=1){
            possibleMoves.add(1);
        }
        //right
        elem = environment.getElement(currentPos.y(), currentPos.x()+1).getSymbol();
        if(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y' && previousRotation!=3){
            possibleMoves.add(3);
        }
        //up
        elem = environment.getElement(currentPos.y()-1, currentPos.x()).getSymbol();
        if(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y' && previousRotation!=2){
            possibleMoves.add(2);
        }
        //down
        elem = environment.getElement(currentPos.y()+1, currentPos.x()).getSymbol();
        if(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y' && previousRotation!=4){
            possibleMoves.add(4);
        }
        System.out.println("Pssible Moves-> " + possibleMoves);
        return possibleMoves;
    }

    abstract public Position chooseNextPosition(Position currentPos);

    abstract public boolean evolve();
    public boolean undoEvolve(int y,int x){
        Position previous = new Position(y,x);
        move(this.getXY(),previous);
        return true;
    };
}
