package src.Exceptions;
public class ExEmployeeAlreadyExists extends Exception {
    public ExEmployeeAlreadyExists() {
        super("Employee name already exists!");
    }
    public ExEmployeeAlreadyExists(String eName) {
        super("Employee " + eName + " already exists!");
    }
}
