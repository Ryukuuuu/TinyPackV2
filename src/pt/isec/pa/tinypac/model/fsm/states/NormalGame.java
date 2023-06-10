package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public class NormalGame extends GameStateAdapter{

    public NormalGame(EnvironmentManager environmentManager, GameContext gameContext){super(environmentManager,gameContext);}

    @Override
    public boolean toInvincibleGame(){
        setState(GameState.INVINCIBLE_GAME);
        return true;
    }

    @Override
    public boolean toPause(){
        setState(GameState.PAUSE_GAME);
        return true;
    }

    @Override
    public boolean toEndGame(){
        setState(GameState.END_GAME);
        return true;
    }
}
