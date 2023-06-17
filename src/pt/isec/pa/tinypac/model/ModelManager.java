package pt.isec.pa.tinypac.model;

import pt.isec.pa.tinypac.gameEngine.GameEngine;
import pt.isec.pa.tinypac.gameEngine.IGameEngine;
import pt.isec.pa.tinypac.gameEngine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.states.GameState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;

public class ModelManager implements IGameEngineEvolve {
    public static final String SAVEPATH = "src/pt/isec/pa/tinypac/utils/save";
    public static final String PROP_STATE = "_state_";
    public static final String PROP_UIUPDATE = "_ui_";
    public static final String PROP_START = "_start_";
    private final GameContext fsm;
    GameEngine gameEngine;
    PropertyChangeSupport pcs;

    public ModelManager(){
        this.gameEngine = new GameEngine();
        gameEngine.registerClient(this);
        fsm = new GameContext();
        pcs = new PropertyChangeSupport(fsm);
    }

    public void addClient(String property, PropertyChangeListener listener){
        pcs.addPropertyChangeListener(property,listener);
    }

    public void removeClient(String property,PropertyChangeListener listener){
        pcs.removePropertyChangeListener(property,listener);
    }

    public void addPropertyChangeListenner(String property,PropertyChangeListener listener){pcs.addPropertyChangeListener(listener);}


    public void start(){
        pcs.firePropertyChange(PROP_START,null,null);
        gameEngine.start(100);
    }
    public void stop(){gameEngine.stop();}


    public GameState getState(){return fsm.getState();}

    public void changePacmanRotation(int rotation){fsm.changePacmanRotation(rotation);}
    public void gotInput(boolean input){fsm.gotInput(input);}
    public void pauseGame(boolean pause){
        if(fsm.pausedGame(pause))
            pcs.firePropertyChange(PROP_STATE,null,null);
    }
    public int getLives(){ return fsm.getLives();}
    public char[][] getMaze(){return fsm.getMaze();}
    public boolean getSpawnedFruit(){return fsm.getSpawnedFruit();}
    public String[] getTop5(){return fsm.getTop5();}
    public long getCurrentTime(){return fsm.getCurrentTime();}
    public String getCurrentTimeStr(){return Long.toString(fsm.getCurrentTime());}
    public int getLevel(){return fsm.getLevel();}
    public int getScore(){return fsm.getScore();}


    @Override
    public void evolve(IGameEngine gameEngine,long currentTime){
        fsm.evolve(currentTime);
        pcs.firePropertyChange(PROP_UIUPDATE,null,null);
        System.out.println("\n\n----- "+fsm.getState()+"\n");
    }
}
