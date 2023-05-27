package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.blocks.*;
import pt.isec.pa.tinypac.model.data.entities.Entity;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.Ghost;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.GhostManager;
import pt.isec.pa.tinypac.model.data.entities.PacMan;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.Maze;
import pt.isec.pa.tinypac.utils.Calculator;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Environment {
    private int height,width;
    private int score=0;
    private Maze maze;
    private List<Entity> entities = new ArrayList<>();

    private GhostManager blinkyManager;

    public Environment(int height,int width){
        this.height=height;
        this.width=width;
        this.maze=new Maze(height,width);
    }

    public int getHeight(){return height;}
    public int getWidth(){return width;}

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

    private PacMan getPacman(){
        for(Entity ent:entities){
            if(ent instanceof PacMan)
                return (PacMan)ent;
        }
        return null;
    }
    public void setPacmanDirection(int dir){
        getPacman().setRotation(dir);
    }

    /*public int getScore(){
        return getPacman().getScore();
    }*/

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
        // Adicionar os outros;
    }

    public boolean spawnGhost(){
        for(Entity e: entities){
            if(e instanceof Ghost && !((Ghost) e).getActive()){
                ((Ghost) e).gotoPortal();
                ((Ghost) e).setSpawned(true);
                ((Ghost) e).setActive(true);
                return true;
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

    private boolean managePacManInventory(PacMan pacman){
        IMazeElement inventory=pacman.getInventory();
        if(inventory instanceof Ball){
            score+=1;
            pacman.setInventory(new Blank(pacman.getY(),pacman.getX()));
        }
        else if(inventory instanceof SuperBall){
            score+=5;
            pacman.setInventory(new Blank(pacman.getY(),pacman.getX()));
        }
        else if(inventory instanceof Ghost){
            if(!((Ghost) inventory).getVulnerable()){
                score+=5;
                pacman.setInventory(new Blank(pacman.getY(),pacman.getX()));
            }
            else{
                pacman.setSpawned(false);
                addElement(inventory,pacman.getY(),pacman.getX());
                return false;
            }
        }
        return true;
    }
    private boolean manageGhostInventory(Ghost ghost){
        IMazeElement inventory = ghost.getInventory();
        if(inventory instanceof PacMan){
            if(ghost.getVulnerable()){
                addElement(inventory,ghost.getY(),ghost.getX());
                ghost.setActive(false);
                ghost.setSpawned(false);
            }
            else{
                ((PacMan) inventory).setSpawned(false);
                ghost.setInventory(new Blank(ghost.getY(), ghost.getX()));
                //o jogo para
            }
        }
        return true;
    }
    public int checkAllEntitiesInventory(){
        for(Entity ent:entities){
            if(ent instanceof PacMan){
                if(managePacManInventory((PacMan)ent)){
                    //terminar o jogo
                    //retornar false
                }
            }
            if(ent instanceof Ghost){
                if(manageGhostInventory((Ghost)ent)){
                    //terminar o jogo
                    //retornar false
                }
            }
        }
        return 0;
    }

    public boolean evolve(){
        //Se isto retornar -1 é que o PacMan morreu portanto é preciso mudar o estado e isso tudo
        checkAllEntitiesInventory();
        for(Entity ent:entities){
            if(ent instanceof PacMan){
                if(!((PacMan) ent).evolve()){
                    return false;
                }
            }
            if(ent instanceof Blinky && ((Blinky) ent).getActive()){
                //System.out.println("BLINKY-> " + ((Blinky) ent).getActive());
                if(((Blinky) ent).getVulnerable() && blinkyManager.hasUndo())
                    blinkyManager.undo();
                else
                    blinkyManager.evolve();
                //System.out.println("Evolve[EnvManager]");
            }
        }
        return true;
    }
}
