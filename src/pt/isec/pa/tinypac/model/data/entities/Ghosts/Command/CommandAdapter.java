package pt.isec.pa.tinypac.model.data.entities.Ghosts.Command;

import pt.isec.pa.tinypac.model.data.entities.Ghosts.Ghost;

public abstract class CommandAdapter implements ICommand{
    protected Ghost receiver;

    protected CommandAdapter(Ghost receiver){this.receiver=receiver;}
}
