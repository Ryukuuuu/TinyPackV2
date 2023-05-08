package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.blocks.GhostSpawn;
import pt.isec.pa.tinypac.model.data.entities.Entity;
import pt.isec.pa.tinypac.model.data.entities.PacMan;
import pt.isec.pa.tinypac.model.data.maze.Element;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.Maze;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Environment {
    private int height,width;
    private Maze maze;
    private List<Entity> entities = new ArrayList<>();

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

    public void addElement(Element element,int y,int x){maze.set(y,x,element);}

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

    public int getScore(){
        return getPacman().getScore();
    }

    public List<Entity> getEntities(){return entities;}

    public boolean evolve(){
        //System.out.println("Evolving[Env]");
        for(Entity ent:entities){
            if(ent instanceof PacMan){
                ent.evolve();
            }
        }
        return true;
    }
}
