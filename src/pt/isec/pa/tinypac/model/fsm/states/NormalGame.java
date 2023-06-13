package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public class NormalGame extends GameStateAdapter{

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
    public boolean pausedGame(){
        if(environmentManager.getPausedGame()){
            changeState(GameState.PAUSE_GAME);
            environmentManager.setGotInput(false);
        }
        return true;
    }

    @Override
    public boolean evolve(long currentTime){
        environmentManager.evolve(currentTime);
        pacmanAlive();
        gameOver();
        pausedGame();
        superBallActive();
        levelOver();
        return true;
    }


    @Override
    public GameState getState(){return GameState.NORMAL_GAME;}
}
