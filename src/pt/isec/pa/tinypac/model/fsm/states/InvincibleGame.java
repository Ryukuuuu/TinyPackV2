package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public class InvincibleGame extends GameStateAdapter{

    public InvincibleGame(EnvironmentManager environmentManager, GameContext gameContext){super(environmentManager,gameContext);}


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
        if(!environmentManager.getSuperBallEaten()){
            changeState(GameState.NORMAL_GAME);
        }
        return true;
    }
    @Override
    public boolean pausedGame(){
        if(environmentManager.getPausedGame()){
            changeState(GameState.PAUSE_GAME);
        }
        return true;
    }
    @Override
    public boolean evolve(long currentTime){
        environmentManager.evolve(currentTime);
        gameOver();
        levelOver();
        pausedGame();
        superBallActive();
        return true;
    }

    @Override
    public GameState getState(){return GameState.INVINCIBLE_GAME;}
}