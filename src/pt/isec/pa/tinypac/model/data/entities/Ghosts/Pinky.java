package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;

public class Pinky extends Ghost{
    private final char symbol='P';

    private final String[] objectives = {"tr","dr","tl","dl"};

    private Position lastPosition;
    private Calculator calculator=new Calculator();
    private int checkPoint=0; //Number of checkPoints reached

    private double tolerance=0.15;  //Distance from objective to validate it
    private int stuckCounter=0;

    private double distanceFromObjective=0;

    public Pinky(Environment environment,int y, int x,Element starting){
        super(environment,y,x,(Element)environment.getElement(y,x));
    }


    public void setCheckPoint(){checkPoint++;}
    public int getCheckPoint(){return checkPoint;}

    public String getCurrentObjective(){return objectives[checkPoint];}

    public double getTolerance(){return tolerance;}

    private double getDistanceFromObjective(Position currentPos){
        String currentObjective = objectives[checkPoint];
        switch (currentObjective){
            case "tr"->{
                return calculator.distanceBetweenPoints(this.getXY(),new Position(0,environment.getWidth()));
            }
            case "dr"->{
                return calculator.distanceBetweenPoints(this.getXY(),new Position(environment.getHeight(),environment.getWidth()));
            }
            case "tl"->{
                return calculator.distanceBetweenPoints(this.getXY(),new Position(0,0));
            }
            case "dl"->{
                return calculator.distanceBetweenPoints(this.getXY(),new Position(environment.getHeight(),0));
            }
        }
        return 0;
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
        Position nextPos;
        ArrayList<Position> possiblePos = getPossibleMoves();
        distanceFromObjective=getDistanceFromObjective(this.getXY());
        System.out.println("Distance from objective-> " + distanceFromObjective);
        return true;
    }
}
