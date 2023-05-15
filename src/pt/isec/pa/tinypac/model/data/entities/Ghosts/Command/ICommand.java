package pt.isec.pa.tinypac.model.data.entities.Ghosts.Command;

public interface ICommand {
    boolean execute();
    boolean undo();
}
