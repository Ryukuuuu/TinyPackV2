package pt.isec.pa.tinypac.model.data.entities.Ghosts.Command;

import pt.isec.pa.tinypac.model.data.entities.Ghosts.Ghost;

public class UndoEvolve extends CommandAdapter{

    public UndoEvolve(Ghost receiver){
        super(receiver);
    }

    @Override
    public boolean execute(){return true;}
    @Override
    public boolean undo(){return true;}
}
