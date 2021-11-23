package src.Exceptions;
public class ExDateInstanceAlreadyCreated extends Exception {
    public ExDateInstanceAlreadyCreated() {
        super("Cannot create one more system date instance.");
    }
}
