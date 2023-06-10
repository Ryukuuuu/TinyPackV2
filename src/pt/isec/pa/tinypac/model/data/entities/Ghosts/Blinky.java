package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.entities.Entity;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

public class Blinky extends Ghost{
    private final char symbol= 'B';

    public Blinky(Environment environment, int y, int x){
        super(environment,(Element) environment.getElement(y,x));
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
        if(this.getActive()) {
            ArrayList<Integer> possibleMoves = getPossibleMoves(this.getRotation(), this);
            Position nextPos = getNextPosition();
            Position currentPos = environment.getElementPosition(this);
            if (blocked(nextPos)) {
                Calculator calc = new Calculator();
                int newRotation;
                newRotation = possibleMoves.get(calc.randomNumberBetweenValues(0, possibleMoves.size() - 1));   //calc.randomNumberBetweenValues(1,5);
                this.setRotation(newRotation);
                nextPos = getNextPosition();
            }
            move(currentPos, nextPos);
        }
        return true;
        //return false;
    }
}
