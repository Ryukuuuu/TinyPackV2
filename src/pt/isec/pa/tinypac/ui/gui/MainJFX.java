package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.ModelManager;



public class MainJFX extends Application {

    ModelManager modelManager;

    @Override
    public void init() throws Exception{
        super.init();
        modelManager = new ModelManager();
        //modelManager.start();
    }

    @Override
    public void start(Stage stage) throws Exception{
        RootPane root = new RootPane(modelManager);
        Scene scene = new Scene(root,700,700,Color.BLACK);

        scene.setUserData(modelManager);

        stage.setScene(scene);
        stage.setTitle("TinyPac");
        stage.setMinWidth(700);
        stage.setMinHeight(400);
        stage.show();
    }



}
