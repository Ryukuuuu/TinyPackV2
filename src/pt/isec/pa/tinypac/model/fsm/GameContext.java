package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.states.GameState;
import pt.isec.pa.tinypac.model.fsm.states.IGameState;

import java.io.Serializable;

public class GameContext implements Serializable{
    EnvironmentManager environmentManager;
    private IGameState state;

    public GameContext(){
        environmentManager=new EnvironmentManager();
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
    public boolean gotInput(boolean input){return state.gotInput(input);}
    public boolean levelOver(){return state.levelOver();}
    public boolean pausedGame(boolean pause){return state.pausedGame(pause);}
    public boolean changePacmanRotation(int rotation){return state.changePacmanRotation(rotation);}
    public int getLives(){return environmentManager.getLives();}
    public char[][] getMaze(){return environmentManager.getEnvironment();}
    public boolean getSpawnedFruit(){return environmentManager.getSpawnedFruit();}
    public String[] getTop5(){return environmentManager.getTop5();}
    public long getCurrentTime(){return environmentManager.getCurrentTime();}
    public int getLevel(){return environmentManager.getLevel();}
    public int getScore(){return environmentManager.getScore();}
    public void evolve(long currentTime){
        state.evolve(currentTime);
        //System.out.println("CurrentState-> "+ getState());
    }
}
