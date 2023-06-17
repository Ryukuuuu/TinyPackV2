package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.states.GameState;
import pt.isec.pa.tinypac.ui.gui.ressources.ImageLoader;

public class MazeUI extends BorderPane {
    ModelManager modelManager;
    GridPane maze;

    public MazeUI(ModelManager modelManager){
        this.modelManager=modelManager;

        createViews();
        registerHandlers();
    }

    private void createViews(){
        showMaze();
        showUI();
    }

    private void showMaze(){
        char[][] mazeChar = modelManager.getMaze();
        if(mazeChar == null){
            return;
        }
        maze = new GridPane();
        getChildren().clear();
        if(maze == null){
            return;
        }
        for(int i=0;i< mazeChar[0].length;i++){
            for(int j=0;j<mazeChar.length;j++){
                if(mazeChar[j][i]=='x'){
                    Rectangle wall = new Rectangle(15,15);
                    wall.setFill(Color.DARKBLUE);
                    maze.add(wall,i,j);
                }
                if(mazeChar[j][i]=='o'){
                    Circle ball = new Circle(3,Color.WHITE);
                    maze.add(ball,i,j);
                }
                if(mazeChar[j][i]=='W'){
                    Rectangle wall = new Rectangle(15,15);
                    wall.setFill(Color.ORANGE);
                    maze.add(wall,i,j);
                }
                if(mazeChar[j][i]=='O'){
                    Circle superBall = new Circle(4,Color.LIGHTYELLOW);
                    maze.add(superBall,i,j);
                }
                if(mazeChar[j][i]=='F'){
                    Rectangle fruit;
                    if(modelManager.getSpawnedFruit()){
                        Image image = ImageLoader.getImage("cherry.png");
                        fruit = new Rectangle(15,15,new ImagePattern(image));
                    }
                    else fruit = new Rectangle(15,15,Color.BLACK);

                    maze.add(fruit,i,j);
                }
                if(mazeChar[j][i]=='C'){
                    Image image = ImageLoader.getImage("pacmanWaiting.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    maze.add(rectangle,i,j);
                }
                if(mazeChar[j][i]=='B'){
                    Image image = ImageLoader.getImage("Blinky.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    maze.add(rectangle,i,j);
                }
                if(mazeChar[j][i]=='P'){
                    Image image = ImageLoader.getImage("Pinky.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    maze.add(rectangle,i,j);
                }
                if(mazeChar[j][i]=='I'){
                    Image image = ImageLoader.getImage("Inky.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    maze.add(rectangle,i,j);
                }
                if(mazeChar[j][i]=='c'){
                    Image image = ImageLoader.getImage("Clyde.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    maze.add(rectangle,i,j);
                }
                if(mazeChar[j][i]=='S'){
                    Image image = ImageLoader.getImage("scaredGhost.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    maze.add(rectangle,i,j);
                }
            }
        }
        maze.setAlignment(Pos.CENTER);
        this.setCenter(maze);
    }
    private void showUI(){
        VBox topContainer = new VBox();

        HBox lives = new HBox();
        for(int i=0;i<modelManager.getLives();i++) {
            ImageView imageView = new ImageView(ImageLoader.getImage("pacmanRight.png"));
            imageView.setFitHeight(25);
            imageView.setFitWidth(25);
            lives.getChildren().add(imageView);
        }

        Label time = new Label(modelManager.getCurrentTimeStr());
        time.setAlignment(Pos.CENTER);
        time.setTextFill(Color.WHITE);
        time.setMinWidth(10);

        Label level = new Label("Level: " + modelManager.getLevel());
        level.setAlignment(Pos.CENTER_RIGHT);
        level.setTextFill(Color.WHITE);
        level.setMinWidth(10);

        topContainer.getChildren().add(lives);
        topContainer.getChildren().add(time);
        topContainer.getChildren().add(level);
        topContainer.setAlignment(Pos.CENTER);
        this.setTop(topContainer);
    }
    private void registerHandlers(){
        modelManager.addPropertyChangeListenner(ModelManager.PROP_UIUPDATE,evt -> {
            Platform.runLater(this::showMaze);
            Platform.runLater(this::showUI);
        });
        modelManager.addPropertyChangeListenner(ModelManager.PROP_STATE,evt -> {
            Platform.runLater(this::update);
        });
    }

    private void update(){
        this.getChildren().clear();
        setVisible(modelManager.getState() != GameState.PAUSE_GAME && modelManager.getState() != GameState.END_GAME);

        if(isVisible()){
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
