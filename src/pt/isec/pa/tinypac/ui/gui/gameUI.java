package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.states.GameState;


public class gameUI extends BorderPane {
    ModelManager modelManager;
    StackPane stackPane;
    public gameUI(ModelManager modelManager){
        this.modelManager=modelManager;
        //maze = new mazeUI(modelManager);
        //pause = new PauseUI(modelManager);
        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        stackPane = new StackPane(
                new MazeUI(modelManager),
                new PauseUI(modelManager)
        );
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setVisible(true);
        this.setCenter(stackPane);
    }


    private void registerHandlers(){
        modelManager.addPropertyChangeListenner(ModelManager.PROP_STATE,evt -> {
            Platform.runLater(this::update);
        });
        this.setOnKeyPressed(event -> {
            modelManager.gotInput(true);
            switch(event.getCode()){
                case LEFT-> modelManager.changePacmanRotation(1);
                case UP-> modelManager.changePacmanRotation(2);
                case RIGHT-> modelManager.changePacmanRotation(3);
                case DOWN-> modelManager.changePacmanRotation(4);
                case P -> {
                    modelManager.pauseGame(true);
                }
            }
        });
    }
    private void update(){
        if(modelManager.getState() != GameState.END_GAME){
            setManaged(true);
            setDisable(false);
            requestFocus();
        }
        else{
            setManaged(false);
            setDisable(true);
            setFocused(false);
        }
    }
}


