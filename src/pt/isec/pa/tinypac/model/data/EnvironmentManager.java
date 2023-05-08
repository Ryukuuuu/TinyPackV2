package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.gameEngine.IGameEngine;
import pt.isec.pa.tinypac.gameEngine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.blocks.*;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.*;
import pt.isec.pa.tinypac.model.data.entities.PacMan;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Thread.sleep;


public class EnvironmentManager{

    private static final String FILE_PATH = "src/pt/isec/pa/tinypac/utils/level";
    private int currentLevel=1;
    private int lastLevel=1;
    private int score;
    private int ballCounter=0;
    private int lives;

    private int activeGhosts=0;
    private long startingTime=0;
    private long runTime;
    private Environment environment;


    private int debugCounter=0;

    public EnvironmentManager(){
        reset();
    }
    private String createPath(int level){
        String path = FILE_PATH;
        if(level<10){
            path+="0";
        }
        path+=level+".txt";
        return path;
    }
    private Element createElement(char symbol,int y,int x){
        switch (symbol){
            case 'x'->{
                return new Wall(y,x);
            }
            case 'W'->{
                return new Warp(y,x);
            }
            case 'o'->{
                ballCounter++;
                return new Ball(y,x);
            }
            case 'F'->{
                return new FruitSpawn(y,x);
            }
            case 'M'->{
                return new PacManSpawn(y,x);
            }
            case 'O'->{
                ballCounter++;
                return new SuperBall(y,x);
            }
            case 'Y'->{
                return new GhostPortal(y,x);
            }
            case 'y'->{
                return new GhostSpawn(y,x);
            }
            default -> {
                return new Blank(y,x);
            }
        }
    }

    private Environment loadEnvironment(){
        int row=0;
        String path=createPath(currentLevel);
        Environment newEnvironment = new Environment(31,29);

        File file=new File(path);
        if(!file.exists()){
            path=createPath(lastLevel);
            file=new File(path);
            System.out.println("File not found");
        }
        else{
            //System.out.println("File found");
            lastLevel=currentLevel;
        }
        try{
            Scanner sc = new Scanner(file);
            //System.out.println("Loading->" + file.getName());
            while(sc.hasNext()){
                String line = sc.nextLine();
                //System.out.println("Readed: " + line);
                for(int column=0;column<line.length();column++){
                    Element newElement=createElement(line.charAt(column),row,column);
                    //maze.set(row,column,newTile);
                    newEnvironment.addElement(newElement,row,column);
                    //  System.out.println("Tile added at [" + row + "," + column + "]");
                }
                row++;
            }
        }catch (FileNotFoundException e){
            //System.out.println("Error file not found\n");
        }
        return newEnvironment;
    }

    public char[][] getEnvironment(){
        if(environment==null){
            return null;
        }
        return environment.getMaze();
    }

    public Environment getEnvironmentDebug(){
        if(environment==null){
            System.out.println("Vazio");
            return null;
        }
        return environment;
    }

    public void spawnPacMan(){
        Position pos = environment.getPosElement('M');
        //System.out.println(pos);
        if(pos!=null){
            PacMan pacman = new PacMan(environment,pos.y(),pos.x(),(Element)environment.getElement(pos.y(),pos.x()));
            environment.addElement(pacman,pos.y(),pos.x());
            environment.addEntity(pacman);
        }
    }
    public void spawnGhosts(){
        //ArrayList<Position> possiblePos=environment.getGhostSpawnPos();
        //Position position;
        int y,x;
        Position[] pos;
        pos = environment.getGhostSpawn();
        Calculator calc=new Calculator();

        for(int i=0;i<4;i++){
            //environment.printEnvironment();
            do {
                y = calc.randomNumberBetweenValues(pos[0].y(), pos[1].y());
                x = calc.randomNumberBetweenValues(pos[0].x(), pos[1].x());
                //System.out.println("{Y=" + y + ",X=" + x + "};");
            }while(environment.getElement(y,x).getSymbol() != 'y');
            switch (i){
                //Spawn Blinky
                case 0->{
                    Blinky blinky = new Blinky(environment,y,x,(Element)environment.getElement(y,x));
                    environment.addElement(blinky,y,x);
                    environment.addEntity(blinky);
                }
                //Spawn Pinky
                case 1->{
                    Pinky pinky = new Pinky(environment,y,x,(Element)environment.getElement(y,x));
                    environment.addElement(pinky,y,x);
                    environment.addEntity(pinky);
                }
                //Spawn Inky
                case 2->{
                    Inky inky = new Inky(environment,y,x,(Element)environment.getElement(y,x));
                    environment.addElement(inky,y,x);
                    environment.addEntity(inky);
                }
                //Spawn Clyde
                case 3->{
                    Clyde clyde = new Clyde(environment,y,x,(Element)environment.getElement(y,x));
                    environment.addElement(clyde,y,x);
                    environment.addEntity(clyde);
                }
            }
        }
    }

    public void setupLevel(){
        environment=loadEnvironment();
        spawnPacMan();
        spawnGhosts();
    }

    public void reset(){
        lives=3;
        currentLevel=1;
        lastLevel=1;
        setupLevel();
    }

    public void changePacmanDirection(int dir){
        environment.setPacmanDirection(dir);
    }


    public void evolve(){
        //System.out.println("Evolving[EnvManager]");
        System.out.println("Debug counter: "+debugCounter++);
        if(environment==null)
            return;
        if(!environment.evolve());
            //gameEngine.stop();
        System.out.println("Score: "+environment.getScore());
    }
}