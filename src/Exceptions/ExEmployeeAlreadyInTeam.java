package src.Exceptions;
public class ExEmployeeAlreadyInTeam extends Exception {
    public ExEmployeeAlreadyInTeam() {
        super("Employee already belongs to a team!");
    }
    public ExEmployeeAlreadyInTeam(String eName, String tName) {
        super("Employee " + eName + " already belongs to team " + tName + "!");
    }
}
