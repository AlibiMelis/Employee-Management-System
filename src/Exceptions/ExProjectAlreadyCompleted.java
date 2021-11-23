package src.Exceptions;
public class ExProjectAlreadyCompleted extends Exception {
    public ExProjectAlreadyCompleted() {
        super("Project has been completed!");
    }
    public ExProjectAlreadyCompleted(String pName) {
        super("Project " + pName + " has been marked as completed before!");
    }
}
