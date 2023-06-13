package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameEngine.IGameEngine;
import pt.isec.pa.tinypac.gameEngine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.states.GameState;
import pt.isec.pa.tinypac.model.fsm.states.IGameState;
import pt.isec.pa.tinypac.model.fsm.states.WaitingForStart;

public class GameContext {
    EnvironmentManager environmentManager;
    private IGameState state;

    public GameContext(){
        environmentManager=new EnvironmentManager();
        //state= new WaitingForStart(environmentManager,this);
        state = GameState.WAITING_FOR_START.createState(environmentManager,this);
    }
    public EnvironmentManager getEnvironmentManager() {
        return environmentManager;
    }
    public GameState getState(){return state.getState();}

    public void changeState(IGameState state){this.state=state;}

    public boolean pacmanAlive(){return state.pacmanAlive();}
    public boolean gameOver(){return state.gameOver();}
    public boolean superBallActive(){return state.superBallActive();}
    public boolean gotInput(){return state.gotInput();}
    public boolean levelOver(){return state.gotInput();}
    public boolean pausedGame(){return state.pausedGame();}

    public void evolve(long currentTime){
        state.evolve(currentTime);
        //System.out.println("CurrentState-> "+ getState());
    }
}
