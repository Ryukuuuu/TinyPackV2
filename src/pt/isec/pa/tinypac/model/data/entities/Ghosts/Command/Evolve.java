package pt.isec.pa.tinypac.model.data.entities.Ghosts.Command;

import pt.isec.pa.tinypac.model.data.entities.Ghosts.Ghost;
import pt.isec.pa.tinypac.utils.Position;

public class Evolve extends CommandAdapter{

    private Position currentPos;
    private int y;
    private int x;

    public Evolve(Ghost receiver, Position currentPos, int y, int x){
        super(receiver);
        this.currentPos=currentPos;
        this.y=y;
        this.x=x;
    }

    @Override
    public boolean execute(){return receiver.evolve(currentPos,y,x);}
    @Override
    public boolean undo(){return true;}
}
