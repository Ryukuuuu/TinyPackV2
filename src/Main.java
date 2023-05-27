import pt.isec.pa.tinypac.gameEngine.GameEngine;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.data.blocks.PacManSpawn;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.ui.TinyPacLanternaUi;
import pt.isec.pa.tinypac.utils.Position;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException{
        GameContext gameContext = new GameContext();
        TinyPacLanternaUi lanternaUi = new TinyPacLanternaUi(gameContext);
        GameEngine gameEngine = new GameEngine();
        gameEngine.registerClient(lanternaUi);
        gameEngine.registerClient(gameContext);
        gameEngine.start(500);

        gameEngine.waitForTheEnd();

        gameEngine.unregisterClient(gameContext);
        gameEngine.unregisterClient(lanternaUi);
    }
}