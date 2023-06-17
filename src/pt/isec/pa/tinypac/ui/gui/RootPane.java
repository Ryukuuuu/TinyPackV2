package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.ui.gui.ressources.css.CSSManager;

public class RootPane extends BorderPane {
    ModelManager modelManager;
    //BorderPane startMenu;
    StackPane stackPane;


    public RootPane(ModelManager modelManager){
        this.modelManager=modelManager;
        createViews();
        registerHandlers();
    }

    private void showMenu(){
        StartMenu startMenu = new StartMenu(modelManager);
        this.setCenter(startMenu);
        startMenu.setVisible(true);
    }

    private void showGame(){
        stackPane = new StackPane(
                new MazeUI(modelManager),
                new PauseUI(modelManager)
        );
        this.setCenter(stackPane);
        this.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
        stackPane.setVisible(true);
    }

    private void createViews(){
        showMenu();
    }

    private void registerHandlers(){
        modelManager.addPropertyChangeListenner(ModelManager.PROP_START,evt ->{Platform.runLater(this::showGame);});
    }

    private void update(){}
}
