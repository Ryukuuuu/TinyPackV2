package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public class EndGame extends GameStateAdapter{

    public EndGame(EnvironmentManager environmentManager, GameContext gameContext){super(environmentManager,gameContext);}
}