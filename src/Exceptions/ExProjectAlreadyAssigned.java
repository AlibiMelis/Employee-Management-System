package src.Exceptions;
public class ExProjectAlreadyAssigned extends Exception {
    public ExProjectAlreadyAssigned() {
        super("Project has been assigned or completed!");
    }
    public ExProjectAlreadyAssigned(String pName, String tName, String date) {
        super("Project " + pName + " has been assigned to team " + tName + " on " + date + " already!");
    }
}
