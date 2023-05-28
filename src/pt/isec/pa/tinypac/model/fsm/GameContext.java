package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameEngine.IGameEngine;
import pt.isec.pa.tinypac.gameEngine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.states.IGameState;
import pt.isec.pa.tinypac.model.fsm.states.WaitingForStart;

public class GameContext implements IGameEngineEvolve {
    EnvironmentManager environmentManager;
    private IGameState state;

    public GameContext(){
        environmentManager=new EnvironmentManager();
        state= new WaitingForStart(environmentManager,this);
    }
    public EnvironmentManager getEnvironmentManager() {
        return environmentManager;
    }

    public void setState(IGameState state){this.state=state;}

    @Override
    public void evolve(IGameEngine gameEngine,long currentTime){
        environmentManager.evolve(currentTime);
    }
}
