package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.states.GameState;
import pt.isec.pa.tinypac.model.fsm.states.PauseGameState;

public class PauseUI extends BorderPane {
    ModelManager modelManager;
    BorderPane container;
    VBox menu;
    Label label;
    HBox btnContainer;
    Button continueBtn,exitBtn,saveBtn;


    public PauseUI(ModelManager modelManager){
        this.modelManager=modelManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createUI(){
        container = new BorderPane();
        label = new Label("Pause:");
        label.setMinWidth(10);
        label.setTextFill(Color.WHITE);

        continueBtn = new Button("Continue");
        continueBtn.setMinWidth(10);

        exitBtn = new Button("Exit");
        exitBtn.setMinWidth(10);

        saveBtn = new Button("Save");
        saveBtn.setMinWidth(10);

        btnContainer = new HBox(continueBtn,saveBtn,exitBtn);

        menu = new VBox(label,btnContainer);
        container.setCenter(menu);
        this.setCenter(container);
    }

    private void createViews(){
        createUI();
    }

    private void registerHandlers(){
        continueBtn.setOnAction(event->{
            System.out.println("EVENT");
            modelManager.pauseGame(false);
        });
        exitBtn.setOnAction(evt->{
            Platform.exit();
        });
        modelManager.addPropertyChangeListenner(ModelManager.PROP_UIUPDATE,evt->{
            Platform.runLater(this::createUI);
        });
        modelManager.addPropertyChangeListenner(ModelManager.PROP_STATE,evt->{
            update();
        });
    }

    private void update(){
        if(modelManager.getState() != GameState.PAUSE_GAME){
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
