package src.Exceptions;
public class ExTeamAlreadyExists extends Exception {
    public ExTeamAlreadyExists() {
        super("Team name already exists!");
    }
    public ExTeamAlreadyExists(String tName) {
        super("Team " + tName + " already exists!");
    }
}
