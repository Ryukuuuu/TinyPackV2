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
    private boolean active=false;
    private boolean eaten=false;
    private boolean inInventory=false;
    private boolean respawned=false;

    public Ghost(Environment environment, Element startingElement){
        super(environment,startingElement,2);
        setVulnerable(false);
    }

    public char getScaredSymbol(){return this.scaredGhost;}
    public boolean getActive(){return active;}
    public void setActive(boolean active){this.active=active;}
    public void changeActive(){this.active = !this.active;}
    public boolean getInInventory(){return inInventory;}
    public void setInInventory(boolean inInventory){this.inInventory=inInventory;}
    public boolean getEaten(){return eaten;}
    public void setEaten(boolean eaten){this.eaten=eaten;}
    public boolean isRespawned(){return respawned;}
    public void setRespawned(boolean respawned){this.respawned=respawned;}
    public void gotoPortal(){
        Position pos = environment.getGhostPortal();
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

    public void die(Position currentPos,long currentTime){
        this.setVulnerable(false);
        this.setActive(false);
        this.setEaten(true);
    }

    public boolean move(Position currentPos,Position nextPos){
        Element elem=(Element) environment.getElement(nextPos.y(), nextPos.x());
        environment.addElement(this.getInventory(), currentPos.y(), currentPos.x());
        this.setInventory(elem);
        if(elem instanceof Ghost)
            ((Ghost) elem).setActive(true);
        environment.addElement(this, nextPos.y(), nextPos.x());
        return true;
    }

    public ArrayList<Integer> getPossibleMoves(int previousRotation,Ghost ghost){
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        Position currentPos = environment.getPosElement(ghost.getSymbol());
        IMazeElement element;
        char elem;
            //left
            element = environment.getElement(currentPos.y(), currentPos.x() - 1);
            elem = element.getSymbol();
            if (elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y' && previousRotation != 3 && !(element instanceof Ghost)) {
                possibleMoves.add(1);
            }
            //right
            element = environment.getElement(currentPos.y(), currentPos.x() + 1);
            elem = environment.getElement(currentPos.y(), currentPos.x() + 1).getSymbol();
            if (elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y' && previousRotation != 1 && !(element instanceof Ghost)) {
                possibleMoves.add(3);
            }
            //up
            element = environment.getElement(currentPos.y() - 1, currentPos.x());
            elem = environment.getElement(currentPos.y() - 1, currentPos.x()).getSymbol();
            if (elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y' && previousRotation != 4 && !(element instanceof Ghost)) {
                possibleMoves.add(2);
            }
            //down
            element = environment.getElement(currentPos.y() + 1, currentPos.x());
            elem = environment.getElement(currentPos.y() + 1, currentPos.x()).getSymbol();
            if (elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y' && previousRotation != 2 && !(element instanceof Ghost)) {
                possibleMoves.add(4);
            }
            //System.out.println("Pssible Moves-> " + possibleMoves);
            if (possibleMoves.size() == 0) {
                possibleMoves.add(reverseRotation(previousRotation));
            }

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
                    //System.out.println("Down element->"+elem.getSymbol());
                    newPos = new Position(currentPos.y()+1, currentPos.x());
                    newPosDistance = calc.distanceBetweenPoints(newPos,objective);
                    if(elemChar != 'x' && elemChar != 'W' && elemChar != 'Y' && elemChar != 'y' && !(elem instanceof Ghost) && this.getRotation()!=2){
                        possibleMoves.add(new Position(currentPos.y()+1, currentPos.x()));
                    }
                }
            }
        }
        //System.out.println("Possible Moves: "+possibleMoves);
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
                //System.out.println("------------------------\nbesDistance initialized\n-------------------");
            }
            else{
                distance=calc.distanceBetweenPoints(possiblePos.get(i),objective);
                if(distance<bestDistance){
                    //System.out.println("Found a best distance");
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

    public int reverseRotation(int rotation){
        switch (rotation){
            case 1->{return 3;}
            case 2->{return 4;}
            case 3->{return 1;}
            case 4->{return 2;}
        }
        return 0;
    }

    public boolean blocked(Position nextPos){
        char elem = environment.getElement(nextPos.y(), nextPos.x()).getSymbol();
        //System.out.println("Next element-> " + elem);
        return !(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y');
    }


    abstract public boolean evolve();
    public boolean undoEvolve(int y,int x){
        Position previous = new Position(y,x);
        return true;
    };
}
