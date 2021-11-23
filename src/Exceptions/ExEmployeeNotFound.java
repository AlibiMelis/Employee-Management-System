package src.Exceptions;
public class ExEmployeeNotFound extends Exception {
    public ExEmployeeNotFound() {
        super("Employee not found.");
    }
    public ExEmployeeNotFound(String eName) {
        super("Employee " + eName + " is not found!");
    }
}
