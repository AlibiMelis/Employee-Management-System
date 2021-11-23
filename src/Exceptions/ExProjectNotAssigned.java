package src.Exceptions;
public class ExProjectNotAssigned extends Exception {
    public ExProjectNotAssigned() {
        super("Project assignment has not been started!");
    }
    public ExProjectNotAssigned(String pName) {
        super("Assignment of project " + pName + " has not been started!");
    }
}
