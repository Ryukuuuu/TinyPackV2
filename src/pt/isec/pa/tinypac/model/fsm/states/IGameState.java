package pt.isec.pa.tinypac.model.fsm.states;

public interface IGameState {

    boolean toWaitingForStart();
    boolean toNormalGame();
    boolean toInvincibleGame();
    boolean toEndGame();
}
