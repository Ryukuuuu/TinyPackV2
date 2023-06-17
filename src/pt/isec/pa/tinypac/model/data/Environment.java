package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.blocks.*;
import pt.isec.pa.tinypac.model.data.entities.Entity;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.*;
import pt.isec.pa.tinypac.model.data.entities.PacMan;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.Maze;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Environment implements Serializable {
    private int height,width;
    private int score=0;
    private long lastBallEatenTimer;
    private long fruitEatTimer;
    private int nrBallsEaten=0;
    private Maze maze;
    private List<Entity> entities = new ArrayList<>();

    private GhostManager blinkyManager;
    private GhostManager pinkyManager;
    private GhostManager inkyManager;
    private GhostManager clydeManager;


    public Environment(int height,int width){
        this.height=height;
        this.width=width;
        this.maze=new Maze(height,width);
    }

    public long getLastBallEatenTimer(){return lastBallEatenTimer;}
    public void setLastBallEatenTimer(long currentTime){lastBallEatenTimer=currentTime;}
    public long getFruitEatTimer(){return fruitEatTimer;}
    public void setFruitEatTimer(long fruitEatTimer){this.fruitEatTimer=fruitEatTimer;}
    public int getHeight(){return height;}
    public int getWidth(){return width;}
    public int getNrBallsEaten(){return nrBallsEaten;}

    public char[][] getMaze(){
        if(maze==null){
            return null;
        }
        return maze.getMaze();
    }

    public void addElement(IMazeElement element,int y,int x){maze.set(y,x,element);}

    public Element getElementByPos(char elementSymbol,int y,int x){
        IMazeElement elem;
        elem=maze.get(y,x);
        if(elem.getSymbol() == elementSymbol){
            return (Element) elem;
        }
        return null;
    }

    public Position getElementPosition(Element elem){
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                if(maze.get(y,x) == elem){
                    return new Position(y,x);
                }
            }
        }
        return null;
    }

    public Position getPosElement(char elementSymbol){
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                if(maze.get(y,x).getSymbol()==elementSymbol){
                    return new Position(y,x);
                }
            }
        }
        return null;
    }

    public IMazeElement getElement(int y,int x){
        return maze.get(y,x);
    }

    public boolean environmentHasBalls(){
        return (getPosElement('o') == null) && (getPosElement('O')==null);
    }

    public void printEnvironment(){
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                System.out.print(maze.get(i,j).getSymbol());
            }
            System.out.println();
        }
    }

    public Position[] getGhostSpawn(){
        Position[] arr = new Position[2];
        IMazeElement elem;
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                elem = maze.get(y,x);
                if(elem instanceof GhostSpawn){
                    if(arr[0]==null){
                        arr[0] = new Position(y,x);
                    }
                    else{
                        arr[1] = new Position(y,x);
                    }
                }
            }
        }
        return arr;
    }

    public Position getGhostPortal(){return getPosElement('Y');}

    public void addEntity(Entity entity){
        entities.add(entity);
        entity.setSpawned(true);
    }

    public PacMan getPacman(){
        for(Entity ent:entities){
            if(ent instanceof PacMan)
                return (PacMan)ent;
        }
        return null;
    }
    public void setPacmanDirection(int dir){
        getPacman().setRotation(dir);
    }

    public int getScore(){
        return this.score;
    }

    public List<Entity> getEntities(){return entities;}

    public Element getWarpPair(Warp currentWarp){
        Element elem;
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                elem = (Element) getElement(y,x);
                if(elem instanceof Warp && !elem.equals(currentWarp)){
                    return elem;
                }
            }
        }
        return null;
    }

    public void setupGhostManager(Ghost ghost){
        //System.out.println("Blinky Manager SETUPPED");
        if(ghost instanceof Blinky){
            blinkyManager=new GhostManager(ghost);
        }
        if(ghost instanceof Pinky){
            pinkyManager=new GhostManager(ghost);
        }
        if(ghost instanceof Inky){
            inkyManager=new GhostManager(ghost);
        }
        if(ghost instanceof Clyde){
            clydeManager=new GhostManager(ghost);
        }
    }

    public boolean spawnGhost(){
        for(Entity e: entities){
            if(e instanceof Ghost && !((Ghost) e).getActive()){
                if(((Ghost) e).getEaten()){

                }
                else{
                    ((Ghost) e).gotoPortal();
                    ((Ghost) e).setSpawned(true);
                    ((Ghost) e).setActive(true);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean respawnGhost(Ghost ghost){
        Calculator calc = new Calculator();
        Position[] pos = this.getGhostSpawn();
        int y,x;

        do {
            y = calc.randomNumberBetweenValues(pos[0].y(), pos[1].y());
            x = calc.randomNumberBetweenValues(pos[0].x(), pos[1].x());
        }while(this.getElement(y,x).getSymbol() != 'y');

        ghost.setVulnerable(false);
        ghost.setActive(false);
        this.addElement(ghost,y,x);

        return true;
    }

    private boolean managePacManInventory(PacMan pacman,long currentTime){
        IMazeElement inventory=pacman.getInventory();
        Position p = this.getPosElement('C');
        if(inventory instanceof Ball){
            score+=1;
            nrBallsEaten++;
            pacman.setInventory(new Blank());
        }
        else if(inventory instanceof SuperBall){
            score+=5;
            nrBallsEaten++;
            pacman.setInventory(new Blank());
            setLastBallEatenTimer(currentTime);
        }
        else if(inventory instanceof Ghost){
            //((Ghost) inventory).die(p,currentTime);     //DEBUG
            //pacman.setInventory(new Blank());           //DEBUG
            if(((Ghost) inventory).getVulnerable()){
                ((Ghost) inventory).die(p,currentTime);
                score+=5;
                pacman.setInventory(new Blank());
            }
            else{
                ((Ghost) inventory).setInInventory(true);
                pacman.die();

                return false;
            }
        }
        else if(inventory instanceof FruitSpawn){
            if(((FruitSpawn) inventory).getSpawned()){
                score+=15;
                setFruitEatTimer(currentTime);
                ((FruitSpawn) inventory).setSpawned(false);
            }
        }
        return true;
    }
    private boolean manageGhostInventory(Ghost ghost){
        IMazeElement inventory = ghost.getInventory();
        Position ghostPosition = this.getPosElement(ghost.getSymbol());
        if(inventory instanceof PacMan){
            if(ghost.getVulnerable()){
                addElement(inventory,ghostPosition.y(), ghostPosition.x());
                ghost.setActive(false);
                ghost.setSpawned(false);
            }
            else{
                ((PacMan) inventory).die();
                ghost.setInventory(new Blank());
            }
        }
        if(inventory instanceof Ghost){
            System.out.println("Setting inInventory to true");
            ((Ghost) inventory).setActive(false);
            ((Ghost) inventory).setInInventory(true);
        }
        return true;
    }

    private void moveAllEntities(long currentTime){
        for(Entity ent:entities){
            if(ent instanceof PacMan){
                if(!((PacMan) ent).evolve()){
                    return;
                }
            }
            if(ent instanceof Pinky && ((Pinky) ent).getActive() && !((Pinky) ent).getInInventory()){
                if(ent.getVulnerable() && pinkyManager.hasUndo())
                    pinkyManager.undo();
                else
                    pinkyManager.evolve();
            }
            if(ent instanceof Inky && ((Inky) ent).getActive() && !((Inky) ent).getInInventory()){
                if(ent.getVulnerable() && inkyManager.hasUndo())
                    inkyManager.undo();
                else
                    inkyManager.evolve();
            }
            if(ent instanceof Clyde && ((Clyde) ent).getActive() && !((Clyde) ent).getInInventory()){
                if(ent.getVulnerable() && clydeManager.hasUndo())
                    clydeManager.undo();
                else
                    clydeManager.evolve();
            }
            if(ent instanceof Blinky && ((Blinky) ent).getActive() && !((Blinky) ent).getInInventory()){
                if(ent.getVulnerable() && blinkyManager.hasUndo())
                    blinkyManager.undo();
                else
                    blinkyManager.evolve();
            }
        }
    }
    private void checkAllEntitiesInventory(long currentTime){
        for(Entity ent : entities){
            if(ent instanceof PacMan)
                managePacManInventory((PacMan)ent,currentTime);
            else{
                manageGhostInventory((Ghost)ent);
            }
        }
    }

    public void scareGhosts(){
        for(Entity ent : entities){
            if(ent instanceof Ghost){
                ent.setVulnerable(true);
                ((Ghost) ent).setRespawned(false);
            }
        }
    }
    public void setGhostsToNormal(){
        for(Entity ent : entities){
            if(ent instanceof Ghost){
                ent.setVulnerable(false);
                ((Ghost) ent).setRespawned(false);
            }
        }
    }

    public boolean evolve(long currentTime){
        //Se isto retornar -1 é que o PacMan morreu portanto é preciso mudar o estado e isso tudo
        moveAllEntities(currentTime);
        checkAllEntitiesInventory(currentTime);
        return true;
    }
}
