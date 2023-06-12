package pt.isec.pa.tinypac.ui.gui;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.ui.gui.ressources.ImageLoader;


public class gameUI extends GridPane {

    ModelManager modelManager;
    public gameUI(ModelManager modelManager){
        this.modelManager=modelManager;

        createViews();
    }

    private void createViews(){
        char[][] maze = modelManager.getEnvironmentManager().getEnvironment();

        for(int i=0;i< maze[0].length;i++){
            for(int j=0;j<maze.length;j++){
                if(maze[j][i]=='x'){
                    Rectangle wall = new Rectangle(15,15);
                    wall.setFill(Color.DARKBLUE);

                    this.add(wall,i,j);
                }
                if(maze[j][i]=='o'){
                    Circle ball = new Circle(3,Color.WHITE);
                    this.add(ball,i,j);
                }
                if(maze[j][i]=='W'){
                    Rectangle wall = new Rectangle(15,15);
                    wall.setFill(Color.ORANGE);
                    this.add(wall,i,j);
                }
                if(maze[j][i]=='O'){
                    Circle superBall = new Circle(4,Color.LIGHTYELLOW);
                    this.add(superBall,i,j);
                }
                if(maze[j][i]=='F'){
                    Image image = ImageLoader.getImage("cherry.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    this.add(rectangle,i,j);
                }
                if(maze[j][i]=='C'){
                    Image image = ImageLoader.getImage("pacmanWaiting.png");
                    Rectangle rectangle = new Rectangle(15,15,new ImagePattern(image));
                    this.add(rectangle,i,j);
                }
            }
        }
    }
}


