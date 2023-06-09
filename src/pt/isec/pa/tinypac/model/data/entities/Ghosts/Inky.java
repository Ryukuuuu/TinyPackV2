package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;


public class Inky extends Ghost{
    private final char symbol='I';

    private final String[] objectives= {"dr","dl","tr","tl"};

    private Calculator calculator = new Calculator();
    private int checkPoint=0;
    private double tolerance=0.75;

    public Inky(Environment environment,int y, int x,Element startingElement){
        super(environment,(Element)environment.getElement(y,x),1);
    }

    public void setCheckPoint(int checkPoint){this.checkPoint=checkPoint;}
    public int getCheckPoint(){return checkPoint;}
    public void increaseCheckPoint(){
        checkPoint++;
        if(checkPoint==4){
            checkPoint=0;
        }
    }

    public String getCurrentObjective(){return objectives[checkPoint];}
    public double getTolerance(){return tolerance;}

    private Position getObjective(){
        switch (getCurrentObjective()){
            case "tr"->{
                return new Position(0, environment.getWidth());
            }
            case "dr"->{
                return new Position(environment.getHeight(),environment.getWidth());
            }
            case "tl"->{
                return new Position(0,0);
            }
            case "dl"->{
                return new Position(environment.getHeight(),0);
            }
        }
        return null;}

    @Override
    public char getSymbol(){
        if(this.getVulnerable()){
            return this.getScaredSymbol();
        }
        return this.symbol;
    }

    @Override
    public boolean evolve(){
        ArrayList<Position> possibleMoves=getPossiblePositions(this,this.getObjective());


        return true;
    }
    @Override
    public boolean undoEvolve(int y,int x){
        return true;
    }
}
