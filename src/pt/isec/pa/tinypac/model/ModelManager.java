package pt.isec.pa.tinypac.model;

import pt.isec.pa.tinypac.gameEngine.GameEngine;
import pt.isec.pa.tinypac.gameEngine.IGameEngine;
import pt.isec.pa.tinypac.gameEngine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.states.GameState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelManager implements IGameEngineEvolve {
    public static final String PROP_STATE = "_state_";
    public static final String PROP_UIUPDATE = "_ui_";
    public static final String PROP_PACMAN_DEAD = "_pacman_";
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
        gameEngine.start(100);
    }


    public EnvironmentManager getEnvironmentManager(){
        return fsm.getEnvironmentManager();
    }

    public boolean pacmanAlive(){
        boolean ret=fsm.pacmanAlive();
        pcs.firePropertyChange(PROP_STATE,null,null);
        return ret;
    }

    public boolean gameOver(){
        boolean ret=fsm.gameOver();
        pcs.firePropertyChange(PROP_STATE,null,null);
        pcs.firePropertyChange(PROP_UIUPDATE,null,null);
        return ret;
    }

    public boolean superBallActive(){
        boolean ret=fsm.superBallActive();
        pcs.firePropertyChange(PROP_STATE,null,null);
        pcs.firePropertyChange(PROP_UIUPDATE,null,null);
        return ret;
    }

    public boolean gotInput(){
        boolean ret=fsm.gotInput();
        pcs.firePropertyChange(PROP_STATE,null,null);
        return ret;
    }

    public boolean levelOver(){
        boolean ret=fsm.levelOver();
        pcs.firePropertyChange(PROP_STATE,null,null);
        pcs.firePropertyChange(PROP_UIUPDATE,null,null);
        return ret;
    }

    public boolean pausedGame(){
        boolean ret=fsm.pausedGame();
        pcs.firePropertyChange(PROP_STATE,null,null);
        pcs.firePropertyChange(PROP_UIUPDATE,null,null);
        return ret;
    }

    public GameState getState(){return fsm.getState();}

    @Override
    public void evolve(IGameEngine gameEngine,long currentTime){
        fsm.evolve(currentTime);
        pcs.firePropertyChange(PROP_UIUPDATE,null,null);
        System.out.println("\n\n----- "+fsm.getState()+"\n");
    }
}
