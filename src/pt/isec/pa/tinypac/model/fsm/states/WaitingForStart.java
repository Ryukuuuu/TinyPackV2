package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public class WaitingForStart extends GameStateAdapter{

    public WaitingForStart(EnvironmentManager environmentManager, GameContext gameContext){super(environmentManager,gameContext);}

    @Override
    public boolean environmentInit(){
        if(!environmentManager.getEnvironemntInit()){
            environmentManager.init();
        }
        return true;
    }

    @Override
    public boolean gotInput(){
        if(environmentManager.getGotInput()){
            changeState(GameState.NORMAL_GAME);
        }
        return true;
    }
    @Override
    public boolean evolve(long currentTime){
        environmentInit();
        gotInput();
        return true;
    }
    @Override
    public GameState getState(){return GameState.WAITING_FOR_START;}
}
