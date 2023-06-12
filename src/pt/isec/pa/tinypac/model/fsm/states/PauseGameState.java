package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public class PauseGameState extends GameStateAdapter{

    public PauseGameState(EnvironmentManager environmentManager, GameContext gameContext){super(environmentManager,gameContext);}


    @Override
    public GameState getState(){return GameState.PAUSE_GAME;}
}
