package pt.isec.pa.tinypac.model.data.entities.Ghosts;

import pt.isec.pa.tinypac.model.data.entities.Ghosts.Command.CommandAdapter;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.Command.CommandManager;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.Command.Evolve;
import pt.isec.pa.tinypac.model.data.entities.Ghosts.Command.UndoEvolve;
import pt.isec.pa.tinypac.utils.Position;

public class GhostManager {
    private Ghost ghost;
    private CommandManager cm;

    public GhostManager(Ghost ghost){
        this.ghost=ghost;
        this.cm = new CommandManager();
    }

    public boolean evolve(){
        //System.out.println("Evolve[GM]");
        return cm.invokeCommand(new Evolve(ghost));
    }

    public boolean UndoEvolve(int y,int x){return cm.invokeCommand(new UndoEvolve(ghost,y,x));}

    public boolean hasUndo(){return cm.hasUndo();}
    public boolean undo(){return cm.undo();}

    public boolean hasRedo(){return cm.hasRedo();}
    public boolean redo(){return cm.redo();}
}