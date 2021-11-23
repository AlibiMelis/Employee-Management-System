package src.Exceptions;
public class ExTeamNotFound extends Exception {
    public ExTeamNotFound() {
        super("Team not found.");
    }
    public ExTeamNotFound(String tName) {
        super("Team " + tName + " is not found!");
    }
}
