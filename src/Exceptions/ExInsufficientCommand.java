package src.Exceptions;
public class ExInsufficientCommand extends Exception {
    public ExInsufficientCommand() {
        super("Insufficient command arguments.");
    }
}
