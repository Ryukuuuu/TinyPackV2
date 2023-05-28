package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public class NormalGame extends GameStateAdapter{

    public NormalGame(EnvironmentManager environmentManager, GameContext gameContext){super(environmentManager,gameContext);}

}
