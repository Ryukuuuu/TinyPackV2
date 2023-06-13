package pt.isec.pa.tinypac.ui.gui;

import javafx.scene.layout.*;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.ui.gui.ressources.css.CSSManager;

public class RootPane extends BorderPane {
    ModelManager modelManager;

    BorderPane startMenu;


    public RootPane(ModelManager modelManager){
        this.modelManager=modelManager;
        //modelManager.start();
        createViews();
    }

    private void createViews(){
        startMenu = new StartMenu(modelManager);
        startMenu.setVisible(true);
        this.setCenter(startMenu);
    }

    private void registerHandlers(){

    }
}
