package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

import java.io.Serializable;

public class NormalGame extends GameStateAdapter implements Serializable {

    public NormalGame(EnvironmentManager environmentManager, GameContext gameContext){super(environmentManager,gameContext);}

    @Override
    public boolean pacmanAlive(){
        if(!environmentManager.getPacmanAlive()){
            if(environmentManager.getLives()>0)
                changeState(GameState.WAITING_FOR_START);
            else
                changeState(GameState.END_GAME);
        }
        return true;
    }

    @Override
    public boolean gameOver(){
        if(environmentManager.getGameOver()){
            changeState(GameState.END_GAME);
        }
        return true;
    }

    @Override
    public boolean levelOver(){
        if(environmentManager.getLevelOver()){
            changeState(GameState.WAITING_FOR_START);
        }
        return true;
    }

    @Override
    public boolean superBallActive(){
        if(environmentManager.getSuperBallEaten()){
            changeState(GameState.INVINCIBLE_GAME);
        }
        return true;
    }
    @Override
    public boolean pausedGame(boolean pause){
        if(pause){
            changeState(GameState.PAUSE_GAME);
        }
        return true;
    }
    @Override
    public boolean changePacmanRotation(int rotation){
        environmentManager.changePacmanRotation(rotation);
        return true;
    }

    @Override
    public boolean evolve(long currentTime){
        environmentManager.evolve(currentTime);
        pacmanAlive();
        gameOver();
        superBallActive();
        levelOver();
        return true;
    }


    @Override
    public GameState getState(){return GameState.NORMAL_GAME;}
}
