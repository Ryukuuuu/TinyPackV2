package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.blocks.Wall;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;

public class Clyde extends Ghost{
    private final char symbol='c';

    public Clyde(Environment environment, int y, int x){super(environment,(Element) environment.getElement(y,x));}

    private boolean checkForWall(Position currentPos,Position pacmanPos,boolean vertical,boolean direction){

        int x=currentPos.x(), y=currentPos.y();

        if(vertical){
            if(direction){  //down
                for(int i=y;i<pacmanPos.y();i++){
                    if(environment.getElement(i,x) instanceof Wall){
                        //System.out.println("Wall between[DOWN]");
                        return true;
                    }
                }
                return false;
            }
            else{           //up
                for(int i=y;i>pacmanPos.y();i--){
                    if(environment.getElement(i,x) instanceof Wall){
                        //System.out.println("Wall between[UP]");
                        return true;
                    }
                }
                return false;
            }
        }
        else{
            if(direction){  //right
                for(int i=x;i< pacmanPos.x();i++){
                    if(environment.getElement(y,i) instanceof Wall){
                        //System.out.println("Wall between[RIGHT]");
                        return true;
                    }
                }
                return false;
            }
            else{           //left
                for(int i=x;x> pacmanPos.x();i--){
                    if(environment.getElement(y,i) instanceof Wall){
                        //System.out.println("Wall between[LEFT]");
                        return true;
                    }
                }
                return false;
            }
        }
    }

    private boolean checkForPacman(Position currentPos){
        Position pacmanPos = environment.getPosElement('C');
        int x,y;
        //System.out.println("Pacman pos->"+pacmanPos+"\nMy pos->"+currentPos);
        if(pacmanPos==null){
            return false;
        }
        x=currentPos.x()-pacmanPos.x();
        y=currentPos.y()- pacmanPos.y();
        //System.out.println("\n-------------------------------------------\nX=="+x+"\nY=="+y+"\n-------------------------------------------\n");

        if(x==0){//mesma coluna
            if(y<0){
                if(!checkForWall(currentPos,pacmanPos,true,true)){
                    this.setRotation(4);
                    return true;
                }
            }
            else{
                if(!checkForWall(currentPos,pacmanPos,true,false)){
                    this.setRotation(2);
                    return true;
                }
            }
        }
        if(y==0){//Mesma linha
            if(x<0){
                if(!checkForWall(currentPos,pacmanPos,false,true)){
                    this.setRotation(3);
                    return true;
                }
            }
            else{
                if(!checkForWall(currentPos,pacmanPos,false,false)){
                    this.setRotation(1);
                    return true;
                }
            }
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
        ArrayList<Integer> possibleMoves = getPossibleMoves(this.getRotation(),this);
        Position nextPos = getNextPosition();
        Position currentPos = environment.getElementPosition(this);
        if(checkForPacman(currentPos)){ //true se encontrar o pacman
            //System.out.println("Saw pacman\nRotation set to->"+getRotation());
            nextPos = getNextPosition();
        }
        else if(blocked(nextPos) && this.getActive()){
            //System.out.println("\nBLOCKED\n");
            Calculator calc = new Calculator();
            int newRotation = possibleMoves.get(calc.randomNumberBetweenValues(0, possibleMoves.size()-1));
            this.setRotation(newRotation);
            nextPos = getNextPosition();
        }
        //else
        //    System.out.println("-----------------NORMAL MOVE-----------------");

        move(currentPos,nextPos);
        return true;
    }
}
