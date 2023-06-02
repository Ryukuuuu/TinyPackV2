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

    public Pinky(Environment environment,int y,int x,Element starting){
        super(environment,(Element)environment.getElement(y,x));
    }


    public void setCheckPoint(){checkPoint++;}
    public int getCheckPoint(){return checkPoint;}

    public String getCurrentObjective(){return objectives[checkPoint];}

    public double getTolerance(){return tolerance;}

    private Position getObjective(){
        String currentObjective = objectives[checkPoint];
        switch (currentObjective){
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
        return null;
    }

    private double getDistanceFromObjective(Position currentPos){
        String currentObjective = objectives[checkPoint];

        switch (currentObjective){
            case "tr"->{
                return calculator.distanceBetweenPoints(currentPos,new Position(0,environment.getWidth()));
            }
            case "dr"->{
                return calculator.distanceBetweenPoints(currentPos,new Position(environment.getHeight(),environment.getWidth()));
            }
            case "tl"->{
                return calculator.distanceBetweenPoints(currentPos,new Position(0,0));
            }
            case "dl"->{
                return calculator.distanceBetweenPoints(currentPos,new Position(environment.getHeight(),0));
            }
        }
        return 0;
    }

    private boolean checkIfCheckPointReached(double currentDistanceFromObjective){
        double distanceRequired = environment.getWidth()-(environment.getWidth()*tolerance);
        System.out.println("Distance required to checkPoint-> " + distanceRequired);

        if(currentDistanceFromObjective<distanceRequired){
            checkPoint++;
            return true;
        }
        return false;
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
        Position currentPos = environment.getElementPosition(this);
        double currentDistanceFromObjective=getDistanceFromObjective(currentPos);
        ArrayList<Position> possiblePos = getPossibleMoves(this,currentDistanceFromObjective,getObjective());
        if(possiblePos.size()==0){
            System.out.println("FDS");
        }
        if(possiblePos.size()==1){
            move(currentPos,possiblePos.get(0));
        }
        else{
            move(currentPos,getBestMove(possiblePos,getObjective(),currentDistanceFromObjective));
        }
        //checkIfCheckPointReached(currentDistanceFromObjective);
        return true;
    }
}
