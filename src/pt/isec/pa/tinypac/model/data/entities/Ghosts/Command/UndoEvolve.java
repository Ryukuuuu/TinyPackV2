package pt.isec.pa.tinypac.model.data.entities.Ghosts.Command;

import pt.isec.pa.tinypac.model.data.entities.Ghosts.Ghost;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;

public class UndoEvolve extends CommandAdapter implements Serializable {

    private Position currentPos;
    private int y;
    private int x;
    public UndoEvolve(Ghost receiver,int y,int x){
        super(receiver);
        this.y=y;
        this.x=x;
    }

    @Override
    public boolean execute(){return receiver.undoEvolve(y,x);}
    @Override
    public boolean undo(){return receiver.evolve();}
}
