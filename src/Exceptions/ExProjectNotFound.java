package src.Exceptions;
public class ExProjectNotFound extends Exception {
    public ExProjectNotFound() {
        super("Project not found.");
    }
    public ExProjectNotFound(String pName) {
        super("Project " + pName + " is not found!");
    }
}
