package pt.isec.pa.tinypac.model.data.entities;

public interface ICommand {
    boolean execute();
    boolean undo();
}
