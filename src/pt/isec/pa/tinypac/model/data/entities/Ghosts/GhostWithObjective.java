package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GhostWithObjective extends Ghost implements Serializable {

    private String[] objectives;
    private final double tolerance=0.75;
    private Calculator calculator=new Calculator();
    private int checkPoint=0;

    public GhostWithObjective(Environment environment, Element starting){
        super(environment,starting);
    }

    public void setObjectives(String[] objectives){
        this.objectives=objectives;
    }

    public double getTolerance(){return tolerance;}

    public double getDistanceFromObjective(Position currentPos,String[] objectives){
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

    public String getCurrentObjective(){return objectives[checkPoint];}
    public Position getObjective(){
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
        return null;
    }

    public void increaseCheckPoint(){
        checkPoint++;
        if(checkPoint==4){
            checkPoint=0;
        }
    }

    public boolean checkIfCheckPointReached(double currentDistanceFromObjective,double tolerance){
        double distanceRequired = environment.getWidth()-(environment.getWidth()*tolerance);
        //System.out.println("Distance required to checkPoint-> " + distanceRequired);
        return currentDistanceFromObjective<distanceRequired;
    }

    public int calculateNextRotation(Position currentPos,Position nextPos){
        int y,x;

        y = currentPos.y() - nextPos.y();
        x = currentPos.x() - nextPos.x();

        switch (y){
            case 1-> {
                return 2;
            }
            case -1->{
                return 4;
            }
            default -> {
            }
        }
        switch (x){
            case 1->{
                return 1;
            }
            case -1->{
                return 3;
            }
        }
        return 0;
    }
    @Override
    public boolean evolve(){
        ArrayList<Position> possibleMoves= getPossiblePositions(this,this.getObjective());
        Position nextPos=null;
        Position currentPos=environment.getElementPosition(this);
        if(possibleMoves.size()==0){
            reverseRotation(this.getRotation());
        }
        else if(possibleMoves.size()==1){
            nextPos=possibleMoves.get(0);
            this.setRotation(calculateNextRotation(currentPos,nextPos));
        }
        else{
            nextPos = getBestMoveFromArray(possibleMoves,this.getObjective());
            this.setRotation(calculateNextRotation(currentPos,nextPos));
        }
        if(nextPos!=null)
            move(currentPos,nextPos);

        if(checkIfCheckPointReached(getDistanceFromObjective(currentPos,objectives),getTolerance())){
            increaseCheckPoint();
        }
        return true;
    }
}
