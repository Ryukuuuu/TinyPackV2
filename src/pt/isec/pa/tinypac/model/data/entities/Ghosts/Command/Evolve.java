package pt.isec.pa.tinypac.model.data.entities.Ghosts.Command;

import pt.isec.pa.tinypac.model.data.entities.Ghosts.Ghost;
import pt.isec.pa.tinypac.utils.Position;

public class Evolve extends CommandAdapter{

    private Position currentPos;
    private int y;
    private int x;

    public Evolve(Ghost receiver){
        super(receiver);
        this.currentPos=receiver.getXY();
    }

    @Override
    public boolean execute(){
        //System.out.println("Execute[Evolve]");
        return receiver.evolve();}
    @Override
    public boolean undo(){return receiver.undoEvolve(y,x);}
}
