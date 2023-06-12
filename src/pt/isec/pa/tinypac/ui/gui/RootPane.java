package pt.isec.pa.tinypac.ui.gui;

import javafx.scene.layout.*;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.ui.gui.ressources.css.CSSManager;

public class RootPane extends BorderPane {
    ModelManager modelManager;

    BorderPane startMenu;
    BorderPane Top5Menu;
    StackPane stackPane;

    public RootPane(ModelManager modelManager){
        this.modelManager=modelManager;
        modelManager.start();
        createViews();
    }

    private void createViews(){
        CSSManager.applyCSS(this,"styles.css");
        modelManager.start();
        stackPane = new StackPane(
                new gameUI(modelManager)
        );
        setCenter(stackPane);
    }

}
