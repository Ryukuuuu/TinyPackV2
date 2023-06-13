package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.states.GameState;
import pt.isec.pa.tinypac.ui.gui.ressources.ImageLoader;


public class gameUI extends BorderPane {

    ModelManager modelManager;

    GridPane mazeUI;
    public gameUI(ModelManager modelManager){
        this.modelManager=modelManager;
        createViews();
        registerHandlers();
    }

    private void createViews(){
        showMaze();
        showUI();
    }

    private void showUI(){
        HBox lives = new HBox();
        for(int i=0;i<modelManager.getEnvironmentManager().getLives();i++) {
            ImageView imageView = new ImageView(ImageLoader.getImage("pacmanRight.png"));
            imageView.setFitHeight(25);
            imageView.setFitWidth(25);
            lives.getChildren().add(imageView);
        }

        this.getChildren().add(lives);

    }

    private void showMaze(){
        char[][] maze = modelManager.getEnvironmentManager().getEnvironment();
        mazeUI = new GridPane();
        getChildren().clear();
        if(maze == null){
            return;
        }
        for(int i=0;i< maze[0].length;i++){
            for(int j=0;j<maze.length;j++){
                if(maze[j][i]=='x'){
                    Rectangle wall = new Rectangle(15,15);
                    wall.setFill(Color.DARKBLUE);
                    mazeUI.add(wall,i,j);
                }
                if(maze[j][i]=='o'){
                    Circle ball = new Circle(3,Color.WHITE);
                    mazeUI.add(ball,i,j);
                }
                if(maze[j][i]=='W'){
                    Rectangle wall = new Rectangle(15,15);
                    wall.setFill(Color.ORANGE);
                    mazeUI.add(wall,i,j);
                }
                if(maze[j][i]=='O'){
                    Circle superBall = new Circle(4,Color.LIGHTYELLOW);
                    mazeUI.add(superBall,i,j);
                }
                if(maze[j][i]=='F'){
                    Image image = ImageLoader.getImage("cherry.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    mazeUI.add(rectangle,i,j);
                }
                if(maze[j][i]=='C'){
                    Image image = ImageLoader.getImage("pacmanWaiting.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    mazeUI.add(rectangle,i,j);
                }
                if(maze[j][i]=='B'){
                    Image image = ImageLoader.getImage("Blinky.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    mazeUI.add(rectangle,i,j);
                }
                if(maze[j][i]=='P'){
                    Image image = ImageLoader.getImage("Pinky.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    mazeUI.add(rectangle,i,j);
                }
                if(maze[j][i]=='I'){
                    Image image = ImageLoader.getImage("Inky.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    mazeUI.add(rectangle,i,j);
                }
                if(maze[j][i]=='c'){
                    Image image = ImageLoader.getImage("Clyde.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    mazeUI.add(rectangle,i,j);
                }
            }
        }
        mazeUI.setAlignment(Pos.CENTER);
        this.setCenter(mazeUI);
    }

    private void registerHandlers(){
        modelManager.addPropertyChangeListenner(ModelManager.PROP_UIUPDATE,evt -> {
            Platform.runLater(this::showMaze);
            Platform.runLater(this::showUI);
        });
        modelManager.addPropertyChangeListenner(ModelManager.PROP_STATE,evt -> {
            Platform.runLater(this::update);
        });
        this.setOnKeyPressed(event -> {
            modelManager.getEnvironmentManager().setGotInput(true);
            switch(event.getCode()){
                case LEFT-> modelManager.getEnvironmentManager().changePacmanDirection(1);
                case UP-> modelManager.getEnvironmentManager().changePacmanDirection(2);
                case RIGHT-> modelManager.getEnvironmentManager().changePacmanDirection(3);
                case DOWN-> modelManager.getEnvironmentManager().changePacmanDirection(4);
                case P -> {
                    modelManager.getEnvironmentManager().setPausedGame(true);
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


