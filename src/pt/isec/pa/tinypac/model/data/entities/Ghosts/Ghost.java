package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.entities.Entity;
import pt.isec.pa.tinypac.model.data.entities.PacMan;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;

public abstract class Ghost extends Entity {

    private final char scaredGhost = 'S';   //if vulnerable==true use this
    private boolean vulnerable=false;
    private boolean active=false;
    //private ArrayList<Position> posRecord = new ArrayList<>();

    public Ghost(Environment environment, Element startingElement,int rotation){
        super(environment,startingElement,rotation);
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
    public ArrayList<Position> getPossiblePositions(Ghost ghost,Position objective){
        ArrayList<Position> possibleMoves = new ArrayList<>();
        Position currentPos = environment.getPosElement(ghost.getSymbol());
        Position newPos;
        Calculator calc=new Calculator();
        IMazeElement elem;
        char elemChar;
        double newPosDistance;

        //System.out.println("\n\nCurrent Position->"+currentPos);
        for(int i=0;i<4;i++){
            switch (i){
                case 0->{   //left
                    elem = environment.getElement(currentPos.y(), currentPos.x()-1);
                    elemChar = elem.getSymbol();
                    newPos = new Position(currentPos.y(), currentPos.x()-1);
                    newPosDistance = calc.distanceBetweenPoints(newPos,objective);
                    if(elemChar != 'x' && elemChar != 'W' && elemChar != 'Y' && elemChar != 'y' && !(elem instanceof Ghost)  && this.getRotation()!=3){
                        possibleMoves.add(new Position(currentPos.y(), currentPos.x()-1));
                    }
                }
                case 1->{   //right
                    elem = environment.getElement(currentPos.y(), currentPos.x()+1);
                    elemChar = elem.getSymbol();
                    newPos = new Position(currentPos.y(), currentPos.x()+1);
                    newPosDistance = calc.distanceBetweenPoints(newPos,objective);
                    if(elemChar != 'x' && elemChar != 'W' && elemChar != 'Y' && elemChar != 'y' && !(elem instanceof Ghost) && this.getRotation()!=1){
                        possibleMoves.add(new Position(currentPos.y(), currentPos.x()+1));
                    }
                }
                case 2->{   //up
                    elem = environment.getElement(currentPos.y()-1, currentPos.x());
                    elemChar = elem.getSymbol();
                    newPos = new Position(currentPos.y()-1, currentPos.x());
                    newPosDistance = calc.distanceBetweenPoints(newPos,objective);
                    if(elemChar != 'x' && elemChar != 'W' && elemChar != 'Y' && elemChar != 'y' && !(elem instanceof Ghost) && this.getRotation()!=4){
                        possibleMoves.add(new Position(currentPos.y()-1, currentPos.x()));
                    }
                }
                case 3->{   //down
                    elem = environment.getElement(currentPos.y()+1, currentPos.x());
                    elemChar = elem.getSymbol();
                    System.out.println("Down element->"+elem.getSymbol());
                    newPos = new Position(currentPos.y()+1, currentPos.x());
                    newPosDistance = calc.distanceBetweenPoints(newPos,objective);
                    if(elemChar != 'x' && elemChar != 'W' && elemChar != 'Y' && elemChar != 'y' && !(elem instanceof Ghost) && this.getRotation()!=2){
                        possibleMoves.add(new Position(currentPos.y()+1, currentPos.x()));
                    }
                }
            }
        }
        System.out.println("Possible Moves: "+possibleMoves);
        return possibleMoves;
    }

    public Position getBestMove(ArrayList<Position> possiblePositions,Position objective,double currentDistance){
        Calculator calc = new Calculator();
        double distance1=currentDistance,distance2,bestDistance;
        Position bestPos=null;

        bestDistance=calc.distanceBetweenPoints(possiblePositions.get(0),objective);
        for(int i=0;i<possiblePositions.size();i++){
            distance2 = calc.distanceBetweenPoints(possiblePositions.get(i),objective);
            if(distance1>distance2){
                bestPos = possiblePositions.get(i);
            }
        }
        if(bestPos == null){
            //System.out.println("Best Position in ArrayList-> " + getBestMoveFromArray(possiblePositions,objective));
            return getBestMoveFromArray(possiblePositions,objective);
        }
        return bestPos;
    }

    public Position getBestMoveFromArray(ArrayList<Position> possiblePos,Position objective){
        double bestDistance=0,distance;
        Position bestPos=null;
        Calculator calc = new Calculator();

        for(int i=0;i<possiblePos.size();i++){
            if(bestPos==null){
                bestPos = possiblePos.get(i);
                bestDistance = calc.distanceBetweenPoints(bestPos,objective);
                System.out.println("------------------------\nbesDistance initialized\n-------------------");
            }
            else{
                distance=calc.distanceBetweenPoints(possiblePos.get(i),objective);
                if(distance<bestDistance){
                    System.out.println("Found a best distance");
                    bestPos = possiblePos.get(i);
                    bestDistance = calc.distanceBetweenPoints(bestPos,objective);
                }
            }
        }
        return bestPos;
    }

    public Position getNextPosition(){
        Position currentPos = environment.getElementPosition(this);
        int x=currentPos.x();
        int y=currentPos.y();
        switch (this.getRotation()){
            case 1-> x--;   //left
            case 2-> y--;   //up
            case 3-> x++;   //right
            case 4-> y++;   //down
            case 0 -> {     //neutral
                return currentPos;
            }
        }
        return new Position(y,x);
    }

    public boolean blocked(Position nextPos){
        char elem = environment.getElement(nextPos.y(), nextPos.x()).getSymbol();
        //System.out.println("Next element-> " + elem);
        return !(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y');
    }

    public int calculateNextRotation(Position currentPos,Position nextPos){
        int y,x;

        y = currentPos.y() - nextPos.y();
        x = currentPos.x() - nextPos.x();

        switch (y){
            case 1-> {
                System.out.println("Rotating up");
                return 2;
            }
            case -1->{
                System.out.println("Rotating down");
                return 4;
            }
            default -> {
                System.out.println("Not rotating up/down");
            }
        }
        switch (x){
            case 1->{
                System.out.println("Rotating left");
                return 1;
            }
            case -1->{
                System.out.println("Rotating right");
                return 3;
            }
            default -> {
                System.out.println("Not rotating left/right");
            }
        }
        System.out.println("ERROR[calculateNextRotation]");
        return 0;
    }

    public boolean checkIfCheckPointReached(double currentDistanceFromObjective,double tolerance){
        double distanceRequired = environment.getWidth()-(environment.getWidth()*tolerance);
        //System.out.println("Distance required to checkPoint-> " + distanceRequired);
        return currentDistanceFromObjective<distanceRequired;
    }

    abstract public boolean evolve();
    public boolean undoEvolve(int y,int x){
        Position previous = new Position(y,x);
        return true;
    };
}
