package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.tinypac.model.ModelManager;

public class StartMenu extends BorderPane {

    ModelManager modelManager;
    Button btnStart,btnExit,btnTop5;
    Label label;

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

    private void registerHandles(){
        btnStart.setOnAction(event ->{
            modelManager.start();
        });
        btnTop5.setOnAction(event -> {
           //to top5
        });
        btnExit.setOnAction(event -> {
            Platform.exit();
        });
    }
    private void update(){
        this.setVisible(false);
    }
}
