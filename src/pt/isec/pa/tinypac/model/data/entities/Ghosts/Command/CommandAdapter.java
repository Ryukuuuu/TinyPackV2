package pt.isec.pa.tinypac.model.data.entities.Ghosts.Command;

import pt.isec.pa.tinypac.model.data.entities.Ghosts.Ghost;

import java.io.Serializable;

public abstract class CommandAdapter implements ICommand, Serializable {
    protected Ghost receiver;

    protected CommandAdapter(Ghost receiver){this.receiver=receiver;}
}
