package pt.isec.pa.tinypac.ui.gui;

import javafx.scene.layout.*;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.ui.gui.ressources.css.CSSManager;

public class RootPane extends BorderPane {
    ModelManager modelManager;
    BorderPane startMenu;
    StackPane game;


    public RootPane(ModelManager modelManager){
        this.modelManager=modelManager;
        createViews();
    }

    private void createViews(){
        game = new StackPane(
                new MazeUI(modelManager),
                new PauseUI(modelManager)
        );
        game.setVisible(false);
        this.setCenter(game);
        startMenu = new StartMenu(modelManager);
        startMenu.setVisible(true);
        this.setCenter(startMenu);
    }
}
