package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.entities.Entity;
import pt.isec.pa.tinypac.model.data.entities.PacMan;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;

public abstract class Ghost extends Entity {

    private final char scaredGhost = 'S';   //if vulnerable==true use this
    private boolean vulnerable=false;
    private boolean active=false;
    //private ArrayList<Position> posRecord = new ArrayList<>();

    public Ghost(Environment environment, Element startingElement){
        super(environment,startingElement);
    }

    public boolean getVulnerable(){return vulnerable;}
    public void setVulnerable(boolean vulnerable){this.vulnerable=vulnerable;}
    public void changeVulnerable(){vulnerable=!vulnerable;}
    public char getScaredSymbol(){return this.scaredGhost;}
    public boolean getActive(){return active;}
    public void setActive(boolean active){this.active=active;}
    public void changeActive(){this.active = !this.active;}

    public void gotoPortal(){
        Position pos = environment.getGhostPortal();
        //System.out.println("\n\nGhost Portal->"+pos);
        Position ghostPos = environment.getPosElement(this.getSymbol());
        if(pos == null){
            return;
        }
        Element portal = (Element) environment.getElement(pos.y(),pos.x());

        environment.addElement(this.getInventory(),ghostPos.y(),ghostPos.x());
        this.setInventory(portal);
        environment.addElement(this,pos.y(), pos.x());
        this.setActive(true);
    }

    public void die(){
        this.changeVulnerable();
        this.changeActive();
    }

    public boolean move(Position currentPos,Position nextPos){
        Element elem=(Element) environment.getElement(nextPos.y(), nextPos.x());


        environment.addElement(this.getInventory(), currentPos.y(), currentPos.x());
        this.setInventory(elem);
        environment.addElement(this, nextPos.y(), nextPos.x());
        return true;
    }

    public ArrayList<Integer> getPossibleMoves(int previousRotation,Ghost ghost){
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        Position currentPos = environment.getPosElement(ghost.getSymbol());
        char elem;
        //left
        elem = environment.getElement(currentPos.y(), currentPos.x()-1).getSymbol();
        if(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y' && previousRotation!=3){
            possibleMoves.add(1);
        }
        //right
        elem = environment.getElement(currentPos.y(), currentPos.x()+1).getSymbol();
        if(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y' && previousRotation!=1){
            possibleMoves.add(3);
        }
        //up
        elem = environment.getElement(currentPos.y()-1, currentPos.x()).getSymbol();
        if(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y' && previousRotation!=4){
            possibleMoves.add(2);
        }
        //down
        elem = environment.getElement(currentPos.y()+1, currentPos.x()).getSymbol();
        if(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y' && previousRotation!=2){
            possibleMoves.add(4);
        }
        //System.out.println("Pssible Moves-> " + possibleMoves);
        return possibleMoves;
    }
    public ArrayList<Position> getPossibleMoves(Ghost ghost,double distance){
        ArrayList<Position> possibleMoves = new ArrayList<>();
        Position currentPos = environment.getPosElement(ghost.getSymbol());
        char elem;
        double newPosDistance;
        //System.out.println("\n\nCurrent Position->"+currentPos);
        for(int i=0;i<4;i++){
            switch (i){
                case 0->{   //left
                    elem = environment.getElement(currentPos.y(), currentPos.x()-1).getSymbol();

                    if(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y'){
                        possibleMoves.add(new Position(currentPos.y(), currentPos.x()-1));
                    }
                }
                case 1->{   //right
                    elem = environment.getElement(currentPos.y(), currentPos.x()+1).getSymbol();
                    if(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y'){
                        possibleMoves.add(new Position(currentPos.y(), currentPos.x()+1));
                    }
                }
                case 2->{   //up
                    elem = environment.getElement(currentPos.y()-1, currentPos.x()).getSymbol();
                    System.out.println("UP element->" +elem);
                    if(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y'){
                        possibleMoves.add(new Position(currentPos.y()-1, currentPos.x()));
                    }
                }
                case 3->{   //down
                    elem = environment.getElement(currentPos.y()+1, currentPos.x()).getSymbol();
                    System.out.println("Down element->"+elem);
                    if(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y'){
                        possibleMoves.add(new Position(currentPos.y()+1, currentPos.x()));
                    }
                }
            }
        }
        //System.out.println("Possible Moves: "+possibleMoves);
        return possibleMoves;
    }

    abstract public boolean evolve();
    public boolean undoEvolve(int y,int x){
        Position previous = new Position(y,x);
        return true;
    };
}
