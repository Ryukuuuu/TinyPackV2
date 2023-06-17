package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

import java.io.Serializable;

public class WaitingForStart extends GameStateAdapter implements Serializable {

    public WaitingForStart(EnvironmentManager environmentManager, GameContext gameContext){super(environmentManager,gameContext);}

    @Override
    public boolean environmentInit(){
        if(!environmentManager.getEnvironemntInit()){
            environmentManager.init();
        }
        return true;
    }

    @Override
    public boolean gotInput(boolean input){
        if(input){
            changeState(GameState.NORMAL_GAME);
        }
        return true;
    }
    @Override
    public boolean evolve(long currentTime){
        environmentInit();
        //gotInput();
        return true;
    }
    @Override
    public GameState getState(){return GameState.WAITING_FOR_START;}
}
