package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public class PauseGameState extends GameStateAdapter{

    public PauseGameState(EnvironmentManager environmentManager, GameContext gameContext){super(environmentManager,gameContext);}

    @Override
    public boolean toNormalGame(){
        setState(GameState.NORMAL_GAME);
        return true;
    }

    @Override
    public boolean toInvincibleGame(){
        setState(GameState.INVINCIBLE_GAME);
        return true;
    }
}
