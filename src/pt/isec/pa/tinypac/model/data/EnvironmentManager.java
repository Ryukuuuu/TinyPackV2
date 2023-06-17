package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.blocks.*;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.Inky;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.Pinky;
import pt.isec.pa.tinypac.model.data.entities.PacMan;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class EnvironmentManager {
        private static final String FILE_PATH = "src/pt/isec/pa/tinypac/utils/level";
        final String PATH_TOP5="src/pt/isec/pa/tinypac/utils/top5.txt";
        private int currentLevel=1;
        private int lastLevel=1;
        private int score;
        private int lives;
        private int ghostSpawned=0;
        private int ghostEaten=0;
        private long startingTime=0;
        private int nrBalls;
        private long runTime;
        private Environment environment;

        private int debugCounter=0;

        //Control vars
        private boolean superBallEaten=false;
        private boolean pacmanAlive=true;
        private boolean gameOver = false;
        private boolean levelOver=false;
        private boolean gotInput=false;
        private boolean environemntInit=false;
        private boolean pausedGame = false;

        private boolean spawnedFruit=false;


        public EnvironmentManager(){
            /*reset();*/
        }
        public boolean getSuperBallEaten(){return superBallEaten;}
        public void setSuperBallEaten(boolean superBallEaten){this.superBallEaten=superBallEaten;}
        public boolean getPacmanAlive(){return pacmanAlive;}
        public void setPacmanAlive(boolean pacmanAlive){this.pacmanAlive=pacmanAlive;}
        public boolean getGameOver(){return gameOver;}
        public void setGameOver(boolean gameOver){this.gameOver=gameOver;}
        public boolean getLevelOver(){return levelOver;}
        public void setLevelOver(boolean levelOver){this.levelOver=levelOver;}
        public boolean getGotInput(){return gotInput;}
        public void setGotInput(boolean gotInput){this.gotInput=gotInput;}
        public boolean getEnvironemntInit(){return environemntInit;}
        public void setEnvironmentInit(boolean environemntInit){this.environemntInit=environemntInit;}
        public boolean getPausedGame(){return pausedGame;}
        public void setPausedGame(boolean pausedGame){this.pausedGame=pausedGame;}
        public boolean getSpawnedFruit(){return spawnedFruit;}
        public void setSpawnedFruit(boolean spawnedFruit){this.spawnedFruit=spawnedFruit;}
        public int getGhostSpawned(){return ghostSpawned;}
        public void setGhostSpawned(int ghostSpawned){this.ghostSpawned=ghostSpawned;}
        public int getGhostEaten(){return ghostEaten;}
        public void setGhostEaten(int ghostEaten){this.ghostEaten=ghostEaten;}
        public int getLives(){return lives;}
        public long getCurrentTime(){return runTime;}
        public int getLevel(){return currentLevel;}

        private String createPath(int level){
            String path = FILE_PATH;
            if(level<10){
                path+="0";
            }
            path+=level+".txt";
            return path;
        }
        public String[] getTop5(){
            File file = new File(PATH_TOP5);
            String[] top = new String[6];

            if(!file.exists()){
                return null;
            }
            try {
                int i=0;
                Scanner sc = new Scanner(file);
                while(sc.hasNext()){
                    String line = sc.nextLine();
                    top[i] = line;
                    i++;
                }
            }catch (IOException e){
                System.out.println("Error");
            }
            return top;
        }


        private Element createElement(char symbol, int y, int x){
            switch (symbol){
                case 'x'->{
                    return new Wall();
                }
                case 'W'->{
                    return new Warp();
                }
                case 'o'->{
                    return new Ball();
                }
                case 'F'->{
                    return new FruitSpawn();
                }
                case 'M'->{
                    return new PacManSpawn();
                }
                case 'O'->{
                    return new SuperBall();
                }
                case 'Y'->{
                    return new GhostPortal();
                }
                case 'y'->{
                    return new GhostSpawn();
                }
                default -> {
                    return new Blank();
                }
            }
        }

        private Environment loadEnvironment(){
            int row=0;
            String path=createPath(currentLevel);
            Environment newEnvironment = new Environment(31,29);
            nrBalls=0;
            debugCounter=0;
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
                System.out.println("Loading->" + file.getName());
                while(sc.hasNext()){
                    String line = sc.nextLine();
                    //System.out.println("Readed: " + line);
                    for(int column=0;column<line.length();column++){
                        if(line.charAt(column) == 'o' || line.charAt(column) == 'O')
                            nrBalls++;
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
                        Blinky blinky = new Blinky(environment,y,x);
                        environment.addElement(blinky,y,x);
                        environment.addEntity(blinky);
                        environment.setupGhostManager(blinky);
                    }
                    //Spawn Pinky
                    case 1->{
                        Pinky pinky = new Pinky(environment,y,x);
                        environment.addElement(pinky,y,x);
                        environment.addEntity(pinky);
                        environment.setupGhostManager(pinky);
                    }
                    //Spawn Inky
                    case 2->{
                        Inky inky = new Inky(environment,y,x);
                        environment.addElement(inky,y,x);
                        environment.addEntity(inky);
                        environment.setupGhostManager(inky);
                    }
                    //Spawn Clyde
                    case 3->{
                        Clyde clyde = new Clyde(environment,y,x);
                        environment.addElement(clyde,y,x);
                        environment.addEntity(clyde);
                        environment.setupGhostManager(clyde);
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
        public void init(){
            lives=3;
            setupLevel();
            setEnvironmentInit(true);
        }

        public void changePacmanRotation(int rotation){
            environment.setPacmanDirection(rotation);
        }
        public long getRunTime(){return runTime;}
        public String getRunTimeStr(){return Long.toString(runTime);}

        private long calcRunTime(long currentTime){
            return (currentTime-startingTime)/1000000000;
        }
        private long timePassed(long currentTime,long reference){return (currentTime-reference)/1000000000;}

        public void updateEnvironmentData(long currentTime) {
            setPacmanAlive(checkIfPacmanAlive());
            if (!pacmanAlive && lives > 0) {
                lives--;
                setupLevel();
            }
            setGameOver(lives == 0 || currentLevel>20);
            setSuperBallEaten(checkForInvincible(currentTime));
            setLevelOver(nrBalls==environment.getNrBallsEaten());
        }

        private boolean checkIfPacmanAlive(){
            PacMan pac = environment.getPacman();
            boolean k = pac.getAlive();
            //System.out.println(k);
            return k;
        }
        public boolean checkForInvincible(long currentTime){
            long time = timePassed(currentTime,environment.getLastBallEatenTimer());
            //System.out.println("Time passed since las superball -> " + time);
            return time<5;
        }
        private void spawnFruit(long curretTime){
            Position pos = environment.getPosElement('F');
            if(pos==null){
                return;
            }
            IMazeElement element = environment.getElement(pos.y(), pos.x());
            if(!(element instanceof FruitSpawn)){
                return;
            }
            if(timePassed(curretTime,environment.getFruitEatTimer())>10){
                ((FruitSpawn) element).setSpawned(true);
                setSpawnedFruit(true);
            }
            else {
                setSpawnedFruit(false);
            }
        }

        public void evolve(long currentTime){
            if(debugCounter==0){
                startingTime = currentTime;
                environment.setFruitEatTimer(startingTime-10);
            }
            runTime=calcRunTime(currentTime);
            if(runTime>3){
                environment.spawnGhost();
            }
            debugCounter++;
            if(environment==null)
                return;
            spawnFruit(currentTime);
            updateEnvironmentData(currentTime);
            if(levelOver){
                currentLevel++;
                setupLevel();
            }
            if(superBallEaten){
                environment.scareGhosts();
            }
            else{
                environment.setGhostsToNormal();
            }
            environment.evolve(currentTime);
        }
}

