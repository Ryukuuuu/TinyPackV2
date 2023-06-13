package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public class PauseGameState extends GameStateAdapter{

    public PauseGameState(EnvironmentManager environmentManager, GameContext gameContext){super(environmentManager,gameContext);}

    @Override
    public boolean gotInput(){
        if(environmentManager.getGotInput()){
            if(environmentManager.getSuperBallEaten())
                changeState(GameState.INVINCIBLE_GAME);
            else
                changeState(GameState.NORMAL_GAME);
            environmentManager.setPausedGame(false);
        }
        return true;
    }
    @Override
    public boolean evolve(long currentTime){
        gotInput();
        return true;
    }
    @Override
    public GameState getState(){return GameState.PAUSE_GAME;}
}
