package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public enum GameState {

    WAITING_FOR_START,NORMAL_GAME,INVINCIBLE_GAME,PAUSE_GAME,END_GAME;

    public IGameState createState(EnvironmentManager environmentManager, GameContext gameContext){
        return switch (this){
            case WAITING_FOR_START -> new WaitingForStart(environmentManager,gameContext);
            case NORMAL_GAME -> new NormalGame(environmentManager,gameContext);
            case INVINCIBLE_GAME -> new InvincibleGame(environmentManager,gameContext);
            case PAUSE_GAME -> new PauseGameState(environmentManager,gameContext);
            case END_GAME -> new EndGame(environmentManager,gameContext);
        };
    }
}
