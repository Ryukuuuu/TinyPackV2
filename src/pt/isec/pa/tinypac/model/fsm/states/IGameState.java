package pt.isec.pa.tinypac.model.fsm.states;

public interface IGameState {

    boolean environmentInit();
    boolean evolve(long currentTime);
    boolean pacmanAlive();
    boolean gameOver();
    boolean superBallActive();
    boolean gotInput();
    boolean levelOver();
    boolean pausedGame();

    GameState getState();
}
