package pt.isec.pa.tinypac.gameEngine;

public class TinyPAcMain {
    public static void main(String[] args) {
        IGameEngine gameEngine = new GameEngine();
        TestClient client = new TestClient();
        gameEngine.registerClient(client);
        gameEngine.start(500);
        gameEngine.waitForTheEnd();
    }
}