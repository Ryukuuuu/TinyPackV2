package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;

public class Pinky extends Ghost{
    private final char symbol='P';

    private final String[] objectives = {"tr","dr","tl","dl"};

    private Calculator calculator=new Calculator();
    private int checkPoint=0; //Number of checkPoints reached

    private double tolerance=0.75;  //Distance from objective to validate it

    public Pinky(Environment environment,int y,int x,Element starting){
        super(environment,(Element)environment.getElement(y,x),1);
    }


    public void setCheckPoint(int checkPoint){this.checkPoint = checkPoint;}
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



    @Override
    public char getSymbol() {
        if (this.getVulnerable()) {
            return this.getScaredSymbol();
        }
        return this.symbol;
    }

    @Override
    public boolean evolve(){
        ArrayList<Position> possibleMoves= getPossiblePositions(this,this.getObjective());
        Position nextPos=null;
        Position currentPos=environment.getElementPosition(this);
        if(possibleMoves.size()==0){
            System.out.println("Inverter a marcha gl");
            //TO DO
        }
        else if(possibleMoves.size()==1){
            nextPos=possibleMoves.get(0);
            this.setRotation(calculateNextRotation(currentPos,nextPos));
            System.out.println("Only 1 possible way");
        }
        else{
            nextPos = getBestMoveFromArray(possibleMoves,this.getObjective());
            this.setRotation(calculateNextRotation(currentPos,nextPos));
            System.out.println("Choosing from more then 1 pos");
        }
        if(nextPos!=null)
            move(currentPos,nextPos);

        if(checkIfCheckPointReached(getDistanceFromObjective(currentPos),tolerance)){
            increaseCheckPoint();
        }
        return true;
    }
}
