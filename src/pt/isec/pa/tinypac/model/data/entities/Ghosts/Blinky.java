package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.entities.Entity;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;

public class Blinky extends Ghost{
    private final char symbol= 'B';
    private int rotation=0;

    public Blinky(Environment environment, int y, int x, Element startingElement){
        super(environment,y,x,startingElement);
        rotation=1;
    }

    public void setRotation(int rotation){
        this.rotation=rotation;
    }
    public int getRotation(){return this.rotation;}
    private Position getNextPosition(){
        Position currentPos = this.getXY();
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

    private boolean blocked(Position nextPos){
        char elem = environment.getElement(nextPos.y(), nextPos.x()).getSymbol();
        //System.out.println("Next element-> " + elem);
        return !(elem != 'x' && elem != 'W' && elem != 'Y' && elem != 'y');
    }

    @Override
    public char getSymbol() {
        if (this.getVulnerable()) {
            return this.getScaredSymbol();
        }
        return this.symbol;
    }

    @Override
    public boolean evolve(){
        ArrayList<Integer> possibleMoves = getPossibleMoves(this.getRotation());

        Position nextPos = getNextPosition();

        if(blocked(nextPos) && this.getSpawned()){
            Calculator calc = new Calculator();
            int newRotation;

            newRotation = possibleMoves.get(calc.randomNumberBetweenValues(0, possibleMoves.size()));   //calc.randomNumberBetweenValues(1,5);
            this.setRotation(newRotation);
            System.out.println("DEBUG rotation: " + rotation);
            nextPos=getNextPosition();
        }
        if(!move(this.getXY(),nextPos))
            return false;
        return true;
        //return false;
    }
}
