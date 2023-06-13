package pt.isec.pa.tinypac.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.tinypac.model.ModelManager;

import java.awt.*;

public class Top5UI extends BorderPane {
    ModelManager modelManager;
    BorderPane startMenu;
    javafx.scene.control.Label[] labels;
    String[] top;
    VBox vBox;

    javafx.scene.control.Button btnBack;

    public Top5UI(ModelManager modelManager){
        this.modelManager = modelManager;

        createViews();
        registerHandles();
    }

    private void showTop5(){
        top = modelManager.getEnvironmentManager().getTop5();
        vBox = new VBox();
        for(int i=0;i<top.length;i++){
            javafx.scene.control.Label newLabel = new javafx.scene.control.Label(top[i]);
            vBox.getChildren().add(newLabel);
        }
        btnBack = new Button("To start Menu...");
        btnBack.setMinWidth(100);

        vBox.getChildren().add(btnBack);

        vBox.setAlignment(Pos.CENTER);
        this.setCenter(vBox);
    }

    private void createStartMenu(){
        startMenu=new StartMenu(modelManager);
        startMenu.setVisible(true);
        this.setCenter(startMenu);
    }

    private void createViews(){
        showTop5();
    }

    private void registerHandles(){
        btnBack.setOnAction(event->{
            createStartMenu();
        });
    }
}
