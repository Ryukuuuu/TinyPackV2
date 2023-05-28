package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public class WaitingForStart extends GameStateAdapter{

    public WaitingForStart(EnvironmentManager environmentManager, GameContext gameContext){super(environmentManager,gameContext);}

    @Override
    public boolean toNormalGame(){
        setState(GameState.NORMAL_GAME);
        return true;
    }
}
