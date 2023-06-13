package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

abstract public class GameStateAdapter implements IGameState{

    protected GameContext context;
    protected EnvironmentManager environmentManager;

    protected GameStateAdapter(EnvironmentManager environmentManager,GameContext context){
        this.context=context;
        this.environmentManager=environmentManager;
    }

    protected void changeState(GameState state){context.changeState(state.createState(environmentManager,context));}


    @Override
    public boolean environmentInit(){return false;}
    @Override
    public boolean pacmanAlive(){return false;}
    @Override
    public boolean gameOver(){return false;}
    @Override
    public boolean levelOver(){return false;}
    @Override
    public boolean superBallActive(){return false;}
    @Override
    public boolean gotInput(){return false;}
    @Override
    public boolean evolve(long currentTime){return false;}
    @Override
    public boolean pausedGame(){return false;}

}
