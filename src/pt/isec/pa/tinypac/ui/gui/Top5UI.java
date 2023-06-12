package pt.isec.pa.tinypac.ui.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.tinypac.model.ModelManager;

import java.awt.*;

public class Top5UI extends BorderPane {
    ModelManager modelManager;
    Label[] labels;
    String[] top;

    public Top5UI(ModelManager modelManager){
        this.modelManager = modelManager;

        createViews();
    }

    private void createViews(){

    }
}
