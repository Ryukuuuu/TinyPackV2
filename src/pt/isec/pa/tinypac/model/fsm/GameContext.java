package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameEngine.IGameEngine;
import pt.isec.pa.tinypac.gameEngine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;

public class GameContext implements IGameEngineEvolve {
    EnvironmentManager environmentManager;

    public GameContext(){
        environmentManager=new EnvironmentManager();
    }

    public EnvironmentManager getEnvironmentManager() {
        return environmentManager;
    }

    @Override
    public void evolve(IGameEngine gameEngine,long currentTime){
        environmentManager.evolve(currentTime);
    }
}
