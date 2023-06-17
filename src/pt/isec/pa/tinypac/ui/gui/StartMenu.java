package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;

public class StartMenu extends BorderPane {

    ModelManager modelManager;
    Button btnStart,btnExit,btnTop5;
    Label label;
    BorderPane top5;
    BorderPane game;

    public StartMenu(ModelManager modelManager){
        this.modelManager = modelManager;
        createViews();
        registerHandles();
    }

    private void createViews(){
        btnStart = new Button("Start");
        btnStart.setMinWidth(100);
        btnTop5 = new Button("Top 5");
        btnTop5.setMinWidth(100);
        btnExit = new Button("Exit");
        btnExit.setMinWidth(100);
        VBox vbox = new VBox(btnStart,btnTop5,btnExit);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        this.setCenter(vbox);
    }

    private void createTop5(){
        top5 = new Top5UI(modelManager);
        top5.setVisible(true);
        this.setCenter(top5);
    }

    private void createGameUI(){
        modelManager.start();
        game = new gameUI(modelManager);
        game.setVisible(true);
        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK,null,null);
        game.setBackground(new Background(backgroundFill));
        this.setCenter(game);
    }

    private void registerHandles(){
        btnStart.setOnAction(event ->{
            createGameUI();
        });
        btnTop5.setOnAction(event -> {
            this.getChildren().clear();
            createTop5();
        });
        btnExit.setOnAction(event -> {
            Platform.exit();
        });
    }
}
