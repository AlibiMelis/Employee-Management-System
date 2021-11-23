package src;
import src.Exceptions.ExInsufficientCommand;

public interface Command {
    public void execute(String cmdParts[]) throws ExInsufficientCommand;
}
