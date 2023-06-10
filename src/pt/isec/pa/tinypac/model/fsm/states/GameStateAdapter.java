package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public class GameStateAdapter implements IGameState{

    protected GameContext context;
    protected EnvironmentManager environmentManager;

    protected GameStateAdapter(EnvironmentManager environmentManager,GameContext context){
        this.context=context;
        this.environmentManager=environmentManager;
    }

    protected void setState(GameState state){context.setState(state.createState(environmentManager,context));}

    @Override
    public boolean toWaitingForStart(){return false;}
    @Override
    public boolean toNormalGame(){return false;}
    @Override
    public boolean toInvincibleGame(){return false;}
    @Override
    public boolean toPause(){return false;}
    @Override
    public boolean toEndGame(){return false;}
}
