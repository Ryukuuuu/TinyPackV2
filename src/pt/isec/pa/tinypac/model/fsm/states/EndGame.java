package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

import java.io.Serializable;

public class EndGame extends GameStateAdapter implements Serializable {

    public EndGame(EnvironmentManager environmentManager, GameContext gameContext){super(environmentManager,gameContext);}

    @Override
    public GameState getState(){
        return GameState.END_GAME;
    }
}
